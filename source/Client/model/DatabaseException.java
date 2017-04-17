package model;

/**
 * Classe modellante un'eccezione lanciata in caso di errore nella comunicazione col database
 */
@SuppressWarnings("serial")
public class DatabaseException extends CommunicationException{
	/** Costruttore stringa di CommunicationException
	 * @param s messaggio dell'eccezione 
	 */
	DatabaseException(String s) {super(s);}
}
