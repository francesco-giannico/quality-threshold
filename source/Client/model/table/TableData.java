package model.table;

import java.util.List;

import data.OutputTO;
import data.TupleTO;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;
/**
 * <p>Costuisce la tabella che verrà visualizzata a video</p>
 */
public class TableData {
	/**
	 * <p>Tuple scomposte per attributo</p>
	 */
	private ObservableList<ParsedOutput> rows;

	/**
	 * Lista dei nomi degli attributi
	 */
	private List<String> attributes;
	/**
	 * <p>Inizializza e popola rows splittando per ogni cluster gli attributi di ogni sua tupla e quindi aggiungendoli in rows , vedi {@link ParsedOutput}</p>
	 * @param output Risultato del calcolo dei cluster
	 */
	public TableData(OutputTO output) {
		List<TupleTO> centroids = output.getCentroids();
		attributes = output.getAttributeNames();
		rows = FXCollections.observableArrayList();
    	for(int i = 0; i < centroids.size(); i++){
    		rows.add(new ParsedOutput(i+1, centroids.get(i)));
    	}
    	
	}
	/**
	 * <p>Ottine le colonne in modo dinamico ed inoltre crea un binding con il valore che sarà contenuto in ogni colonna , vedi {@link ParsedOutput} </p>
	 * @return colonne
	 */
	@SuppressWarnings("unchecked")
	public TableColumn<ParsedOutput,String>[] getColumns() {
    	TableColumn<ParsedOutput,String>[] columns = new TableColumn[this.attributes.size() + 1]; 
    	for(int columnIndex = 0; columnIndex < this.attributes.size() + 1; columnIndex++){
    		final int i = columnIndex;
    		if(columnIndex == 0)
    			columns[0] = new TableColumn<ParsedOutput,String>("Index");
    		else {
    			columns[columnIndex] =  new TableColumn<ParsedOutput,String>(this.attributes.get(columnIndex - 1));
    		}
    		columns[columnIndex].setCellValueFactory(new Callback<CellDataFeatures<ParsedOutput,String>, ObservableValue<String>>(){
    			public ObservableValue<String> call(CellDataFeatures<ParsedOutput,String> p){
    				return p.getValue().getParsedRow(i);
    			}
    		});
    	}
    	return columns;
	}
	/**
	 * <p>Restituisce l'attributo di classe rappresente  le righe scomposte</p>
	 * @return righe della tabella
	 */
	public ObservableList<ParsedOutput> getRows() {
		return rows;
	}
	
}
