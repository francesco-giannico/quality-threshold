package controller;

/** Classe modellante un'eccezione lanciata nel caso di un errore da parte del controller */ 
@SuppressWarnings("serial")
class ControllerException extends Exception {
	/** 
	 * Costruttore stringa di Exception
	 *  @param s messaggio dell'eccezione 
	 *   */
	ControllerException(String s){super(s);}
}
