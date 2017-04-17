package data;


/**
 * <p> Classe che modella una coppia (ContinuousAttribute, Double) </p>
 */

class ContinuousItem extends Item {
	
	/**
	 * <p> Memorizza in un oggetto di tipo Item una coppia del tipo (ContinuousAttribute, double)</p>
	 * <p>richiamando il costruttore della superclasse Item </p>
	 * @param attribute Attributo continuo 
	 * @param value Valore reale
	 */
	ContinuousItem(ContinuousAttribute attribute, Double value) {
		super(attribute, value); 
	}
		 
	/**
	 * {@inheritDoc}
	 */
	double distance(Object a) {
		ContinuousItem c_a = (ContinuousItem) a;
		return Math.abs((((ContinuousAttribute)this.getAttribute()).getScaledValue((double)this.getValue()) - ((ContinuousAttribute)c_a.getAttribute()).getScaledValue((double)c_a.getValue())));
	}

}
