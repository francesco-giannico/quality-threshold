package model.chart;

import java.util.List;

import data.OutputTO;
import data.TupleTO;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.Chart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Pane;

/**
 * 
 * <p> Classe che estende chartRendering e che modella il rendering di un barChart </p> 
 *
 */
public class BarChartRender extends ChartRendering{
	
	/**
	 * {@link OutputTO} Output da cui ricavare le informazioni per la costruzione del grafico
	 */
	private OutputTO output;
	
	/**
	 * Costruisce il rendering relativo partendo dal clusterMap
	 * @param output Output da cui ricavare le informazioni per la costruzione del grafico
	 * @param parent Pane in cui verrà renderizzato il grafico 
	 */
	public BarChartRender(OutputTO output, Pane parent) {
		super(parent);
		this.output = output;
		param = 1;
	}


	
	/**
	 * <p> Crea e restituisce il diagramma a barre con le tuple nel cluster passato in input e relative distanze sull'asse y
	 * {@inheritDoc}
	 */
	@Override
	protected Chart getChart(Integer cluster) {
		NumberAxis yAxis = new NumberAxis();
		CategoryAxis xAxis = new CategoryAxis();
		BarChart<String, Number> distanceChart = new BarChart<String, Number>(xAxis, yAxis);
		yAxis.setLabel("Distance");
		xAxis.setLabel("Tuple");
		yAxis.setAutoRanging(true);
		xAxis.setAutoRanging(true);
		XYChart.Series<String, Number> serie = new XYChart.Series<>();
		List<TupleTO> tuples = output.get(cluster);
		if (tuples.size() > 1)
			for (int i = 0; i < tuples.size(); i++) {
				TupleTO t = tuples.get(i);
				if (t.getCentroidDistance()!=0){
					serie.getData().add(new XYChart.Data<String, Number>("Tuple " + Integer.toString(i), t.getCentroidDistance()));
				}
			}
		else {
			serie.getData().add(new XYChart.Data<String, Number>("Centroid", 0));
		}
		
		distanceChart.getData().add(serie);
		distanceChart.setPrefSize(280, 500);
		distanceChart.setLegendVisible(false);
		distanceChart.setTitle("Cluster " + Integer.toString(cluster) + " distances");
		
		return distanceChart;
	}
}
