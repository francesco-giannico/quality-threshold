package data;

import java.io.Serializable;

/**
 * <p> Classe modellante il Transfer Object del generico Item all'interno di una tupla </p>
 */
@SuppressWarnings("serial")
public class ItemTO implements Serializable {
	/**
	 * <p> Valore presente nell'item </p>
	 */
	private Object value;
		
	/**
	 * <p> Getter per il valore dell'item </p>
	 * @return valore dell'Item
	 */
	public Object getValue() {
		return value;
	}

	
	/**
	 * <p> Setter per il valore dell'item </p>
	 * @param value Valore che l'item andrà a rappresentare
	 */
	void setValue(Object value) {
		this.value = value;
	}
}
