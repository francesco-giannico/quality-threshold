package data;

import java.io.Serializable;
/**
 * Classe modellante la singola tupla da manipolare nel grafico
 */
@SuppressWarnings("serial")
public class TupleTO implements Serializable {
	/**
	 * Array di item di cui è costituita la tupla
	 */
	private ItemTO[] tuple;
	
	/**
	 * Distanza dal relativo centroide
	 */
	private double centroidDistance;
	
	/**
	 * Costruttore della tupla, alloca lo spazio per l'array di tuple e inizializza ogni 
	 * elemento dell'array con un Item vuoto.
	 * @param size Numero di attributi da cui è costituita ogni singola tupla
	 */
	TupleTO(int size) {
		tuple = new ItemTO[size];
		for (int i = 0; i < size; i++) {
			tuple[i] = new ItemTO();
		}
	}
	
	/**
	 * Getter per l'item alla posizione i
	 * @param i indice del valore da ottenere
	 * @return valore di cui il chiamante del metodo è interessato
	 */
	public ItemTO get(int i) {
		return tuple[i];
	}
	
	/**
	 * Funzione per recuperare la dimensione della tupla 
	 * @return Numero di valori nella tupla
	 */
	public int size() {
		return tuple.length;
	}
	
	/**
	 * Setter per la distanza della tupla dal centroide
	 * @param distance distanza dal centroide da settare
	 */
	public void setCentroidDistance(double distance) {
		centroidDistance = distance;
	}
	
	/**
	 * Getter per la distanza della tupla dal centroide
	 * @return distanza della tupla dal centroide 
	 */
	public double getCentroidDistance() {
		return centroidDistance;
	}
}
 