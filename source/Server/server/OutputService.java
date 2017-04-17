package server;


import java.util.ArrayList;
import mining.Cluster;
import mining.QTMiner;
import data.Data;
import data.OutputTO;
import data.TupleTO;

/**
 * <p> Classe modellante il servizio di manipolazione dei dati in output dal lato server e in input dal lato client</p>
 * <p> consiste in una rifattorizzazione delle informazioni fornite da data e qtminer </p>
 */
class OutputService {
	/**
	 * Output da manipolare
	 */
	private OutputTO output;

	/**
	 * <p> Inizializza l'OutputTO a partire da data e qtminer passati in input al costruttore</p>
	 * @param data {@link data} Struttura da cui recuperare lista di tuple e schema
	 * @param qtminer {@link QTMiner} Struttura da cui recuperare la mappa di cluster e tuple
	 */
	OutputService(Data data, QTMiner qtminer) {
		output = new OutputTO();
		
		int clusterId = 1;
		for (Cluster c:qtminer.getC()) {
			output.put(clusterId, new ArrayList<TupleTO>());
			for (Integer i:c) {
				TupleTO t = data.getItemSet(i).toTupleTO();
				t.setCentroidDistance(c.getCentroid().getDistance(data.getItemSet(i)));
				output.get(clusterId).add(t);
			}
			clusterId++;
		}
		output.setAttributes(data.getAttributeSchema());
	}
	

	
	/**
	 * <p> Restituisce l'{@link OutputTO} elaborato dal costruttore</p>
	 * @return OutputTO rappresentante il TransferObject dell'output da lato server
	 */
	OutputTO getOutputTO() { 
		return output;
	}

}
