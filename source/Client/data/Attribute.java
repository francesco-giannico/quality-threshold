package data;

import java.io.Serializable;

/**<p> Modella l'entità Attribute</p>
 * <p>Inizializza i valori dei membri name,index </p>
 * */
@SuppressWarnings("serial")
public abstract class Attribute implements Serializable{
	
	/** <p> nome simbolico dell'attributo </p> */
	private String name;
	/** <p> identificativo numerico dell'attributo </p>*/
	private int index;
	
	/**<p>Questo metodo è il costruttore della classe astratta Attribute</p>
	 * <p>Inizializza  i valori  name e index</p>
	 * @param name rappresenta l'input
	 * @param index rappresenta l'input
	 */
	protected Attribute(String name, int index) {
		this.name = name; 		
		this.index = index; 	
	}
	
	/**
	 * <p> Restituisce il nome dell'attributo </p>
	 * @return name è il nome dell'attributo
	 */
	public String getName() {
		return name;
	}

	
	/**
	 * <p>Rappresenta lo stato dell'oggetto</p>
	 * @return String rappresentante il nome e l'indice dell'attribute
	 */
	@Override
	public String toString() {
		return ("Name: " + name + "\nIndex: "+ index);
	}	
}
