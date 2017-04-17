package model.chart;

import javafx.scene.chart.Chart;
import javafx.scene.layout.Pane;


/**
 * 
 * Classe che modella il rendering di un generico grafico in funzione di 1, 2 o 3 parametri
*/

public abstract class ChartRendering{

	/**
	 * <p> Pane in cui verrà renderizzatp il grafico </p>
	 */
	private Pane parent;

	/**
	 * <p> Numero parametri </p>
	 */
	protected int param;
	
	/**
	 * Inizializza parent
	 * @param parent Pane che conterrà il grafico
	 */
	protected ChartRendering(Pane parent) {
		this.parent = parent;
	}

	/** 
	 * Inserisce nel parent il grafico renerizzato ad un parametro
	 * @param x Parametro per la creazione del grafico
	 * @throws ChartException {@link ChartException}
	 */
	public void showChart(Integer x) throws ChartException {
		if (param != 1) {
			throw new ChartException("Wrong number of parameters");
		}
		showChart(getChart(x));
	}
	
	/**
	 * Inserisce nel parent il grafico renerizzato a due parametri
	 * @param x Primo parametro per la creazione del grafico
	 * @param y Secondo parametro per la creazione del grafico
	 * @throws ChartException   {@link ChartException}
	 */
	public void showChart(Integer x, Integer y) throws ChartException {
		if (param != 2) {
			throw new ChartException("Wrong number of parameters");
		}
		showChart(getChart(x, y));
	}
	
	
	/**
	 * Inserisce nel pane passato in input il grafico
	 * @param chart Grafico da inserire
	 */
	private void showChart(Chart chart) {
		parent.getChildren().clear();
		parent.getChildren().add(chart);
	}
	
	/**
	 * Funzione che restituisce un grafico la cui creazione è gestita da un solo intero.
	 * In tal caso la classe che estende effettua override.
	 * @param x Parametro per la creazione del grafico
	 * @return null
	 */
	protected Chart getChart(Integer x) {
		return null;
	}
	
	/**
	 * Funzione che restituisce un grafico la cui creazione è gestita da un solo intero.
	 * In tal caso la classe che estende effettua override.
	 * @param x Primo parametro per la creazione del grafico
	 * @param y Secondo parametro per la creazione del grafico
	 * @return null
	 */
	protected Chart getChart(Integer x, Integer y) {
		return null;
	}
	

}
