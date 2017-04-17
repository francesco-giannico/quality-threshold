package model.table;

import data.TupleTO;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 * <p> Classe per permettere il parsing di un output per poterlo inserire nella tableView</p>
 */
public class ParsedOutput {
	/**
	 * <p>Contiene le righe scomposte per colonne</p>
	 */
	private ObservableList<ParsedRow> parsedRows = FXCollections.observableArrayList();

	/**
	 * <p>Aggiunge tuple a ParsedRows</p>
	 * @param index Indice della tupla da aggiungere
	 * @param row riga con tutti gli elementi di una tupla
	 */
	ParsedOutput(int index, TupleTO row){
		parsedRows.add(new ParsedRow(Integer.toString(index)));
		for(int cell = 0 ; cell < row.size(); cell++){
			parsedRows.add(new ParsedRow(row.get(cell).getValue().toString()));
		}
	}
	/**
	 * <p>Utile per il binding delle colonne</p>
	 * @param pos intero rappresentante la posizione in colonna 
	 * @return {@link StringProperty}
	 */
	public StringProperty getParsedRow(int pos){
		return parsedRows.get(pos).getNameCodeProperty();
	}
	
	/**
	 * <p>Tupla scomposta in colonne</p>
	 */
	private class ParsedRow {
		/**
		 * <p>Codice di colonna</p>
		 */
		private SimpleStringProperty nameCode;
		/**
		 * <p>Inizializza il nameCode </p>
		 * @param nameCode Stringa rappresentante il codice della colonna
		 */
		private ParsedRow(String nameCode){
			this.nameCode = new SimpleStringProperty(nameCode);
		}
		/**
		 * <p>Restituisce il nameCode</p>
		 * @return nameCode {@link StringProperty}
		 */
		private StringProperty getNameCodeProperty(){
			return nameCode;
		}
	}
}