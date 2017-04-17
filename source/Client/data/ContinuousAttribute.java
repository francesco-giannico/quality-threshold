package data;
/**
 * <p>Questa classe concreta estende la classe Attribute e modella un attributo continuo.</p>
 * <p> Include i metodi per la normalizzazione del dominio dell'attributo nell'intervallo [0,1] al fine da rendere confrontabili attributi aventi domini diversi </p>
 * */
@SuppressWarnings("serial")
public class ContinuousAttribute extends Attribute {
	
	/** <p>rappresenta il secondo estremo dell'intervallo </p>*/
	private double max;
	/** <p>rappresenta il primo  dell'intervallo </p>*/
	private double min; 
	
	/** 
	 * <p> ContinuosAttribute è il costruttore della classe ContinuosAttribute che è sottoclasse di Attribute cui scopo è quello di :</p>
	 * <p>settare gli intervalli del dominio  avendo precedentemente invocato il costruttore della superclasse Attribute.</p>
	 * @param name  rappresenta il nome dell'attributo ricevuto in input di tipo stringa
	 * @param index  rappresenta l' identificativo numerico ricevuto in input di tipo intero
	 * @param min  rappresenta il primo  estremo dell'intervallo ricevuto in input di tipo double
	 * @param max rappresenta il secondo estremo dell'intervallo ricevuto in input di tipo double
	 */
	ContinuousAttribute(String name, int index, double min, double max) {
		super(name, index); //viene invocato il costruttore della superclasse rispetto a ContinuousAttribute
		this.max = max;
		this.min = min;
	}
	
	/**
	 * <p> Comportamento: Calcola e restituisce il valore normalizzato del parametro passato in input. La normalizzazione ha come codominio l'intervallo [0,1].</p>
	 * <p>la normalizzazione avviene usando questa formula : <b> v'=(v-min)/(max-min) </b> </p>
	 * @param v è il valore dell'attributo da normalizzare
	 * @return il valore normalizzato
	 */
	double getScaledValue(double v) {
		return (v-min)/(max-min);
	}

}
