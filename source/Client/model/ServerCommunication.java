package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

import data.OutputTO;
/**
 * <p>Classe che modella la comunicazione del client col server</p>
 */
public class ServerCommunication {
	/**
	 * <p>Stream in output</p>
	 */
	private ObjectOutputStream out;
	/**
	 * <p>Stream di input</p>
	 */
	private ObjectInputStream in; 
	
	/**
	 * <p>Inizializza le socket della classe prendendo gli stream di input e output dalla socket ricevuta in input</p>
	 * @param socket {@link Socket}
	 * @throws CommunicationException {@link CommunicationException}
	 */
	public ServerCommunication(Socket socket) throws CommunicationException {
		try
		{
			this.out = new ObjectOutputStream(socket.getOutputStream());
			this.in = new ObjectInputStream(socket.getInputStream());
		} catch(IOException e){
			throw new CommunicationException("Unable to instantiate connection with server.");
		}
	}
	/**
	 * <p>Ottiene i nomi delle tabelle presenti nel database</p>
	 * @return nomi delle tabelle 
	 * @throws DatabaseException {@link DatabaseException}
	 */
	@SuppressWarnings("unchecked")
	public List<String> getTableNames() throws DatabaseException {
		List<String> sourceNames;
		try{
			out.writeObject(4);
			sourceNames = (List<String>) in.readObject();
			String message = (String) in.readObject();
			if (!message.equals("OK")) {
				throw new CommunicationException(message);
			}
		} catch (Exception e) {
			throw new DatabaseException("Cannot fetch table names from database");
		}
		return sourceNames;
	}
	/**
	 * <p>Ottiene i nomi dei file presenti nella apposita cartella /dmp</p>
	 * @return nomi dei files
	 * @throws CommunicationException {@link CommunicationException}
	 */
	@SuppressWarnings("unchecked")
	public List<String> getFileNames() throws CommunicationException {
		List<String> sourceNames;
		try{
			out.writeObject(5);
			sourceNames = (List<String>) in.readObject();
			String message = (String) in.readObject();
			if (!message.equals("OK")) {
				throw new CommunicationException(message);
			}
		} catch (Exception e) {
			throw new CommunicationException("Cannot fetch file names from server");
		}
		return sourceNames;
	}

	
	/**
	 * <p>Si occupa dell'acquisizione delle informazioni dal database</p>
	 * @param  tabName  nome tabella
	 * @param  radius  raggio 
	 * @throws CommunicationException {@link CommunicationException}
	 * @return Stringa  rappresentante il risultato della computazione 
	 */
	public OutputTO learningFromDBAction(String tabName, Double radius)throws CommunicationException{
		String message = null;
		OutputTO output = null;

		try {
			if(radius <= 0) {
				throw new NumberFormatException("Radius <= 0");
			}
			out.writeObject(1);
			out.writeObject(radius);
			out.writeObject(tabName);
			
			message = (String) in.readObject();
			if(!message.equals("OK")) {
				throw new DatabaseException(message);
			}
			output = (OutputTO) in.readObject();
			message = (String) in.readObject();
			if(!message.equals("OK")) {
				throw new CommunicationException(message);
			}
			// Salvataggio
			out.writeObject(2);
			message = (String) in.readObject();
			if(!message.equals("OK")) {	
				throw new CommunicationException(message);
			}
		} catch (CommunicationException e) {
			throw new CommunicationException(message);
		} catch (NumberFormatException e) {
			throw new CommunicationException(e.toString());
		} catch (IOException | ClassNotFoundException e) {
			throw new DatabaseException("Cannot read from database");
		}
		return output;
	}
	
	/**
	 * <p>Si occupa dell'acquisizione delle informazioni da file</p>
	 * @param fileName nome del file da cui leggere 
	 * @throws CommunicationException {@link CommunicationException}
	 * @return  risultato della computazione da file
	 */
	public OutputTO learningFromFileAction(String fileName) throws CommunicationException {
		OutputTO output = null;
		try {
			
			out.writeObject(3);
			out.writeObject(fileName);
			output = (OutputTO) in.readObject();
			if(!((String) in.readObject()).equals("OK")) {
				throw new CommunicationException("Cannot load from file");
			}
		} catch (CommunicationException e) {
			throw new CommunicationException(e.getMessage());
		} catch (Exception e) {
			throw new CommunicationException("Cannot load from file");
		}
		return output;
	}

	/**
	 * <p>Segnale per la chiusura della connessione</p>
	 * @throws IOException 			   {@link IOException}
	 * @throws ClassNotFoundException  {@link ClassNotFoundException}
	 */
	public void closeSignal() throws IOException, ClassNotFoundException{
		out.writeObject(999);
		if(in.readObject().equals("OK")) {
			return;
		}
	}
	
}
