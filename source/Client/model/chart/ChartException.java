package model.chart;

/** Classe modellante un'eccezione sollevata in caso di problemi nel rendering del grafico */
@SuppressWarnings("serial")
public class ChartException extends Exception{
	/** 
	 * @param s messaggio dell'eccezione 
	 */
	ChartException(String s) {super(s);}
}
