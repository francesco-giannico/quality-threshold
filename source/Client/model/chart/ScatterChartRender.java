package model.chart;

import java.util.ArrayList;
import java.util.List;

import data.Attribute;
import data.ContinuousAttribute;
import data.DiscreteAttribute;
import data.OutputTO;
import data.TupleTO;
import javafx.collections.FXCollections;
import javafx.scene.chart.Axis;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.Chart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Pane;

/**
 * <p> Classe che estende chartRendering e che modella il rendering di uno scatter chart </p>
 */
public class ScatterChartRender extends ChartRendering{

	/**
	 * {@link OutputTO} 
	 */
	private OutputTO clusterMap;
	
	/**
	 * <p>Lista di attributi su cui opera lo scatter chart</p>
	 */
	private List<Attribute> attributes;
	
	/**
	 * Inizializza il render dello scatterChart con l'outputTO ed il pane in cui andrà il grafico 
	 * @param clusterMap {@link OutputTO}
	 * @param parent    Pane che conterrà il grafico
	 */
	public ScatterChartRender(OutputTO clusterMap, Pane parent) {
		super(parent);
		this.clusterMap = clusterMap;
		this.attributes = clusterMap.getAttributes();
		param = 2;
	}


	/**
	 * <p> Crea e restituisce lo scatter chart con gli eventuali attributi indicati dai parametri x e y
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected Chart getChart(Integer x, Integer y) {
		ScatterChart chart;
		chart = new ScatterChart(getScatterChartAxis(x), getScatterChartAxis(y));
		chart.setTitle("Scatter chart with clustered tuples");
		chart.getData().addAll(getClusterSeries(x, y));
		chart.setPrefSize(1000, 500);
		return chart;	
	}

	/**
	 * Divide in series i vari cluster con i dati delle relative tuple
	 * @param x indice dell'asse x
	 * @param y indice dell'asse y
	 * @return lista di series da inserire nel chart
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List<XYChart.Series> getClusterSeries(int x, int y) {
		List<XYChart.Series> listSeries = new ArrayList<XYChart.Series>();
		int i = 1;
		for (Integer c: clusterMap.keySet()) {
			XYChart.Series series  = new XYChart.Series<>();
			series.setName("Cluster: " + i);
			for (TupleTO t: clusterMap.get(c)) {
				XYChart.Data chartData = new XYChart.Data();
				chartData.setXValue(getValue(t, x));
				chartData.setYValue(getValue(t, y));
				series.getData().add(chartData);
			}
			listSeries.add(series);
			i++;
		}
		return listSeries;
	}
	
	/**
	 * Crea l'asse relativo all'attributo ottenuto dall'indice passato in input
	 * @param index indice dell'attributo da cui costruire l'asse
	 * @return asse relativa all'attributo
	 */
	@SuppressWarnings("rawtypes")
	private Axis getScatterChartAxis(int index) {
		Axis axis;
		if (attributes.get(index) instanceof ContinuousAttribute) {
			axis = new NumberAxis();
		} else {
			DiscreteAttribute discreteAxis = (DiscreteAttribute)  attributes.get(index);
			List<String> discreteValues = new ArrayList<String>();
			for(String s:discreteAxis) 
				discreteValues.add(s);
			axis = new CategoryAxis(FXCollections.observableList(discreteValues));
		}
		axis.setLabel(attributes.get(index).getName());
		axis.setAutoRanging(true);
		return axis;
	}
	
	/**
	 * Ottiene il valore della tupla relativa all'attributo passato in input
	 * @param t tupla da cui ottenere il valore
	 * @param index indice dell'attributo di cui ottenere il valore
	 * @return Object rappresentante il valore assunto dalla tupla nell'attributo
	 */
	private Object getValue(TupleTO t, int index) {
		if (attributes.get(index) instanceof DiscreteAttribute) {
			return (String)t.get(index).getValue();
		} else {
			return (Double)t.get(index).getValue();
		}
	}

}
