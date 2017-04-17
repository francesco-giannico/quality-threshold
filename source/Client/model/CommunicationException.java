package model;

/**
 * <p> Classe modellante un'eccezione sollevata in caso di errore di comunicazione col server</p> 
 */
@SuppressWarnings("serial")
public class CommunicationException extends Exception{
	/** 
	 * Costruttore stringa di exception 
	 * @param s Messaggio dell'eccezione
	 * */
	CommunicationException(String s) {super(s);}
}
