package data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Classe modellante l'associazione di cluster e tuple
 */
@SuppressWarnings("serial")
public class OutputTO implements Serializable {
	/**
	 * Lista degli attributi
	 */
	private List<Attribute> attributes;
	/**
	 * Map contenente l'intero relativo all'id del cluster con la relativa lista di tuple
	 */
	private Map<Integer, List<TupleTO>> map = new HashMap<Integer, List<TupleTO>>();
	
	/**
	 * Lista di stringhe rappresentanti i nomi degli attributi
	 */
	private List<String> attributeNames;


	
	
	/**
	 * <p> Crea una lista contenente tutte le {@link TupleTO} contenute in ogni cluster </p>
	 * @return lista contenente tutte le tuple 
	 */
	private List<TupleTO> getAllTuples() {
		ArrayList<TupleTO> tuples = new ArrayList<TupleTO>();
		Set<Integer> keys = map.keySet();
		for(Integer k:keys) {
			for (TupleTO t:map.get(k)) {
				tuples.add(t);
			}
		}
		return tuples;
	}
	
	/**
	 * Setta la lista degli attributi nell'output e inizializza la lista dei nomi degli attributi
	 * @param attributes Attributi da aggiungere all'output
	 */
	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
		attributeNames = new ArrayList<String>();
		for (Attribute a:attributes) {
			attributeNames.add(a.getName());
		}
		
	}
	
	
	/**
	 * Restituisce la lista di tuple associata al cluster i
	 * @param i Cluster di cui ottenere la lista di tuple
	 * @return Lista di tuple relative al cluster passato in input
	 */
	public List<TupleTO> get(Integer i) {
		return map.get(i);
	}
	
	/**
	 * Inserisce la coppia id cluster e lista di tuple all'interno di map
	 * @param i ID del cluster 
	 * @param list Lista di tuple associate a i
	 */
	public void put (Integer i, List<TupleTO> list) {
		map.put(i, list);
	}

	/**
	 * Permette di ottenere l'insieme degli ID dei cluster mappati
	 * @return Insieme di interi rappresentante gli ID dei cluster
	 */
	public Set<Integer> keySet() {
		return map.keySet();
	}
	
	
	/**
	 * Getter per la lista degli attributi
	 * @return lista di attributi presente nel risultato
	 */
	public List<Attribute> getAttributes() {
		return attributes;
	}
	
	/**
	 * Funzione che inserisce in una lista i nomi degli attributi
	 * @return Lista di stringhe rappresentanti i nomi degli attributi
	 */
	public List<String> getAttributeNames() {
		return attributeNames;
	}
	
	/**
	 * Funzione che permette di ottenere la lista dei centroidi dell'output
	 * @return Lista dei centroidi
	 */
	public List<TupleTO> getCentroids() {
		List <TupleTO> centroids = new ArrayList<TupleTO>();
		for (TupleTO t:getAllTuples()) {
			if (t.getCentroidDistance() == 0) {
				centroids.add(t);
			}
		}
		return centroids;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public String toString() {
		return map.toString();
	}
}