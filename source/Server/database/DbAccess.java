package database;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * <p> Classe che realizza l'accesso al Database </p>
 */
public class DbAccess {
	/**
	 * <p> Nome del driver associato al connettore MySQL</p>
	 */
	private String DRIVER_CLASS_NAME = "org.gjt.mm.mysql.Driver";
	/**
	 * <p> Database Management System utilizzato </p>
	 */
	private final String DBMS = "jdbc:mysql"; 
	/**
	 * <p> Identificativo del server su cui risiede il Database </p>
	 */
	private final String SERVER= "localhost"; //contiene l’identificativo del server su cui risiede la base di dati (per esempio localhost) 
	/**
	 * <p> Nome del Database </p>
	 */
	private final String DATABASE = "MapDB"; //contiene il nome della base di dati 
	/**
	 * <p> Porta su cui il DBMS MySQL accetta le connessioni </p>
	 */
	private final String PORT="3306"; //La porta su cui il DBMS MySQL accetta le connessioni 
	/**
	 * <p> Nome utente per l'accesso al Database </p>
	 */
	private final String USER_ID = "MapUser";//: contiene il nome dell’utente per l’accesso alla base di dati 
	
	/**
	 * <p> Password di autenticazione per l'utente identificato da USER_ID </p>
	 */
	private final String PASSWORD = "map";//: contiene la password di autenticazione per l’utente identificato da  USER_ID 
	/**
	 * <p> Gestore della connessione </p>
	 */
	private Connection conn;
	
	/**
	 * <p> Impartisce al class loader l'ordine di caricare il Driver MySQL, inizializza la connessione riferita da conn. </p>
	 * @throws DatabaseConnectionException {@link DatabaseConnectionException}
	 *
	 */
	public void initConnection() throws DatabaseConnectionException {
		try {
			Class.forName(DRIVER_CLASS_NAME);
			conn = DriverManager.getConnection(DBMS + "://" + SERVER + ":" + PORT + "/" + DATABASE, USER_ID, PASSWORD);
		} catch (ClassNotFoundException | SQLException e) {
			throw new DatabaseConnectionException();
		} 
		
	}
	
	
	/**
	 * <p> Restituisce il gestore della connessione </p>
	 * @return Gestore della connessione
	 */
	Connection getConnection(){
		return conn;
	}
	
	/**
	 * <p> Chiude la connessione al database </p>
	 * @throws DatabaseConnectionException {@link DatabaseConnectionException}
	 */
	
	public void closeConnection()  throws DatabaseConnectionException{
		try {
			conn.close();
		} catch (SQLException e) {
			throw new DatabaseConnectionException();
		}
	}
	

	/**
	 * <p> Metodo per ottenere i nomi delle tabelle dal database </p>
	 * @return Lista di nomi delle tabelle nel Database
	 * @throws SQLException 				{@link SQLException}
	 * @throws DatabaseConnectionException  {@link  DatabaseConnectionException}
	 */
	public List<String> tableNameList() throws SQLException, DatabaseConnectionException {
		initConnection();
		Statement statement = getConnection().createStatement();
		ResultSet result = statement.executeQuery("SHOW TABLES FROM " + DATABASE+";");
		List<String> tables = new ArrayList<String>();
		while (result.next()) {
			tables.add(result.getString(1));
		}
		result.close();
		statement.close();
		closeConnection();
		return tables;
	}
}
