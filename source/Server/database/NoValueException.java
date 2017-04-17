package database;

/**
 * <p>Eccezione sollevata in caso di assenza di valori nel resultset</p>
 */
@SuppressWarnings("serial")
public class NoValueException extends Exception {
	/**
	 *Costruttore stringa di Exception 
	 * @param s Messaggio dell'eccezione
	 */
	NoValueException(String s) {super(s);}
}
