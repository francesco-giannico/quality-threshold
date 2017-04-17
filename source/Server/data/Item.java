package data;


/**
 * <p>Modella un generico item (coppia attributo-valore, per esempio Outlook=”Sunny”).</p>
 */
abstract class Item {
	
	/**
	 * <p>Attributo coinvolto nell'item</p>
	 */
	private Attribute attribute;	
	/**
	 * <p>Valore assegnato all'attributo</p>
	 */
	private Object value;			
	
	/**
	 * <p>Inizializza i valori dei membri attributi</p>
	 * @param attribute attributo 
	 * @param value valore dell'attributo
	 */
	protected Item(Attribute attribute, Object value) {
		this.attribute = attribute;
		this.value = value;	
	}
	
	/**
	 * <p>Restituisce l'attributo coinvolto nell'item</p>
	 * @return restituisce l'attributo incapsulato nell'oggetto
	 */
	Attribute getAttribute() {
		return attribute;
	}
	/**
	 * <p>Restituisce il valore assegnato all'attributo</p>
	 * @return restituisce il valore incapsulato nell'oggetto
	 */
	Object getValue() {
		return value;
	}
	
	/**
	 * <p>Chiama il toString di {@link Object} </p>
	 * @return Stringa rappresentante il value dell'attributo
	 */
	@Override
	public String toString() {
		return value.toString();
	}
	
	/**
	 *<p> L'implementazione sarà diversa per ogni Item discreto e item continuo </p>
	 *<p> ritorna la distanza tra l'oggetto implicito a quello passato in input. </p>
	 * @param a tupla 
	 * @return double che rappresenta la distanza da oggetto implicito a quello passato
	 */
	abstract double distance(Object a);
}
