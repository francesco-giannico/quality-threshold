package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;



import database.TableSchema.Column;
/**
 * <p>Modella l'insieme di transazioni collezionate in una tabella</p>
 * <p>Ogni transazione è modellata dalla classe {@link Example}</p>
*/
public class TableData {

	/**
	 * <p> Oggetto della classe DbAccess</p>
	 */
	private DbAccess db;

	/**
	 * <p>Inizializza il database attributo di classe con quello ricevuto in input</p>
	 * @param db database da cui recuperare le informazioni 
	 */
	public TableData(DbAccess db) {
		this.db=db;
	}

	/**
	 * <p>Ricava lo schema della tabella con nome table</p>
	 * <p>Esegue un'interrogazione al database per estrarre le tuple distinte dalla tabella </p>
	 * <p>Per ogni tupla del resultset si crea un oggetto che è istanza della classe  {@link Example}, </p>
	 * <p>cui riferimento va incluso nella lista da restituire. In particolare per la tupla corrente nel</p>
	 * <p>resultset si estraggonno e li si aggiungono all'oggetto istanza della classe  {@link Example} che si sta costruendo  </p>
	 * @param table Tabella da cui ricavare lo schema 
	 * @return Lista di transazioni distinte memorizzate nella tabella
	 * @throws SQLException  {@link SQLException}
	 * @throws EmptySetException  {@link EmptySetException}
	 */
	public List<Example> getDistinctTransazioni(String table) throws SQLException, EmptySetException{
		 LinkedList<Example> transSet = new LinkedList<Example>();
		 Statement statement;
		TableSchema tSchema=new TableSchema(db,table);
		
		
		String query="select distinct ";
		
		for(int i=0;i<tSchema.getNumberOfAttributes();i++){
			Column c=tSchema.getColumn(i);
			if(i>0)
				query+=",";
			query += c.getColumnName();
		}
		if(tSchema.getNumberOfAttributes()==0)
			throw new SQLException();
		query += (" FROM "+table);
		
		statement = db.getConnection().createStatement();
		ResultSet rs = statement.executeQuery(query);
		boolean empty=true;
		while (rs.next()) {
			empty=false;
			Example currentTuple=new Example();
			for(int i=0;i<tSchema.getNumberOfAttributes();i++)
				if(tSchema.getColumn(i).isNumber())
					currentTuple.add(rs.getDouble(i+1));
				else
					currentTuple.add(rs.getString(i+1));
			transSet.add(currentTuple);
		}
		rs.close();
		statement.close();
		if(empty) throw new EmptySetException();
		
		return transSet;

	}
	/**
	 * <p>Formula ed esegue una interrogazione SQL per </p>
	 * <p>estrarre i valori distinti orinati del parametro column e popolare un insieme da restituire </p>
	 * @param table nome della tabella
	 * @param column nome della colonna
	 * @return insieme di valori distinti ordianati in modalità ascendente che l'attributo identificato da nome column(parametro) assue nella tabella identificata dal nome table(parametro)
	 * @throws SQLException {@link Example}
	 */
	public Set<Object>getDistinctColumnValues(String table,Column column) throws SQLException{
		Set<Object> valueSet = new TreeSet<Object>();
		Statement statement;
		//TableSchema tSchema=new TableSchema(db,table);
		
		
		String query="select distinct ";
		query += column.getColumnName();
		query += (" FROM "+table);
		query += (" ORDER BY " +column.getColumnName());
		
		
		
		statement = db.getConnection().createStatement();
		ResultSet rs = statement.executeQuery(query);
		while (rs.next()) {
				if(column.isNumber())
					valueSet.add(rs.getDouble(1));
				else
					valueSet.add(rs.getString(1));
			
		}
		rs.close();
		statement.close();
		
		return valueSet;

	}
	/**
	 * <p>Formula ed esegue una interrogazione SQL per estrarre il valore aggregato (valore minimo o valore massimo) ercato nella colonna nome column della tabella di nome table</p>
	 * @param table Nome della tabella
	 * @param column nome della colonna
	 * @param aggregate operatore sql di aggregazione(min,max)
	 * @return Aggreato cercato  {@link QUERY_TYPE}
	 * @throws SQLException {@link SQLException}
	 * @throws NoValueException {@link NoValueException}
	 */
	public Object getAggregateColumnValue(String table,Column column,QUERY_TYPE aggregate) throws SQLException,NoValueException{
		Statement statement;
		Object value=null;
		String aggregateOp="";
		
		String query="select ";
		if(aggregate==QUERY_TYPE.MAX)
			aggregateOp+="max";
		else
			aggregateOp+="min";
		query+=aggregateOp+"("+column.getColumnName()+ ") FROM "+table;
		
		
		statement = db.getConnection().createStatement();
		ResultSet rs = statement.executeQuery(query);
		if (rs.next()) {
				if(column.isNumber())
					value=rs.getFloat(1);
				else
					value=rs.getString(1);
			
		}
		rs.close();
		statement.close();
		if(value==null)
			throw new NoValueException("No " + aggregateOp+ " on "+ column.getColumnName());
			
		return value;

	}

}
