package mining;
import data.Data;
import data.Tuple;

/**
 * <p>Classe che include l'implementazione dell’algoritmo QT</p>
 */
public class QTMiner {
	
	/**
	 * <p> Elemento C di tipo ClusterSet </p>
	 */
	private ClusterSet C;
	
	/**
	 * <p> Rappresenta il raggio dei cluster </p>
	 */
	private double radius;
	
	/**
	 * <p>Crea l'oggetto ClusterSet riferito da C e inizializza radius con il parametro passato come input</p>
	 * @param radius rappresenta il  raggio dei cluster di tipo double
	 */
	public QTMiner(double radius) {
		C = new ClusterSet();
		this.radius = radius;
	}
	
	/**
	 * <p>Restituisce il {@link ClusterSet}</p>
	 * @return C
	 */
	public ClusterSet getC() {
		return C;
	}
	/**
	 * <p>Esegue il seguente algoritmo </p>
	 * <p>1. Costruisce un cluster per ciascuna tupla non ancora clusterizzata, includendo nel cluster i punti (non ancora clusterizzati in alcun altro cluster) che ricadano nel vicinato sferico della tuple avente raggio radius</p>
	 * <p>2. Salva il candidato cluster più popoloso e rimuove tutti punti di tale cluster dall'elenco delle tuple ancora da clusterizzare</p>
	 * <p>3. Ritorna al passo 1 finchè ci sono ancora tuple da assegnare ad un cluster</p>
	 * @param data Insieme di tuple 
	 * @return Intero che rappresenta il numero di cluster scoperti
	 * @throws ClusteringRadiusException {@link ClusteringRadiusException}
	 */
	public int compute(Data data) throws ClusteringRadiusException {
		int numClusters = 0;
		boolean isClustered[] = new boolean[data.getNumberOfExamples()];	// IsClustered è un vettore grande quanto il numero di tuple in data
		for (int i = 0; i < isClustered.length; i++) 	// Vengono settati a falso
			isClustered[i] = false;
		int countClustered = 0; 
		while(countClustered != data.getNumberOfExamples()) {
			// Ricerca cluster più popoloso
			Cluster c = buildCandidateCluster(data, isClustered);
			C.add(c);
			numClusters++;
			
			// Rimuove Tuple clusterizzate da dataset
			for (Integer i:c) {
				isClustered[i] = true;
			}
			countClustered += c.getSize();
		}
		if (numClusters==1)
			throw new ClusteringRadiusException("Only 1 cluster");
		return numClusters;
	}
	
	/**
	 * <p>Costruisce un cluster per ciascuna tupla di data non ancora clusterizzata in un cluster di C e restituisce il cluster candidato più popoloso</p>
	 * @param data insiene di tuple da raggruppare in cluster
	 * @param isClustered[] informazione booleana sullo stato di clusterizzazione di una tupla (per esempio isClustered[i]=false se la tupla i-esima di data non è ancora assegnata ad alcun cluster di C, true altrimenti)
	 * @return Cluster più popoloso 
	 */
	private Cluster buildCandidateCluster(Data data, boolean isClustered[]) {
		Cluster candidate = null;
		for (int i = 0; i < data.getNumberOfExamples(); i++) {
			if (isClustered[i]) continue;				
			Tuple t = data.getItemSet(i);
			Cluster c = new Cluster(t);
			for (int j = 0; j < data.getNumberOfExamples(); j++) {
				if (isClustered[j]) 
					continue;
				Tuple t2 = data.getItemSet(j);
				if (t.getDistance(t2) <= radius) {
					c.addData(j);
				}
			}
			if (candidate == null)	candidate = c;
			else if (c.getSize() > candidate.getSize()) candidate = c;
		}
		return candidate;
	}

	/**
	 * <p>Chiama il toString di {@link ClusterSet} per C</p>
	 * @return Cluster 
	 */	@Override
	public String toString(){
		return C.toString();
	}

}
