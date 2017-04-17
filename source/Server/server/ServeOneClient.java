package server;
import mining.*;
import data.*;

import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

import database.DbAccess;

/**
 * <p>Classe che modella un singolo client</p>
 */
class ServeOneClient extends Thread {
	
	/**
	 * <p>Socket per la connessione al server</p>
	 */
	private Socket socket;
	
	/**
	 * <p>Stream di input</p>
	 */
	private ObjectInputStream in;
	
	/**
	 * <p>Stream di output</p>
	 */
	private ObjectOutputStream out;
	
	/**
	 * <p>Oggetto della classe QTMiner</p>
	 */
	private QTMiner qtminer;
	
	/**
	 * <p>Costruttore del client che inizializza la socket e gli stream e chiama start()</p>
	 * @param s Socket utilizzata per inizializzare
	 * @throws IOException {@link IOException}
	 */
	ServeOneClient(Socket s) throws IOException {
		socket = s;
		in = new ObjectInputStream(socket.getInputStream());
		out = new ObjectOutputStream(socket.getOutputStream());
		start();
	}
	
	@Override
	public void run() {
		Data data = null;
		DbAccess db = new DbAccess();
		String tableName = null;
		Double radius = null;
		OutputService output = null;
		ObjectInputStream load = null;
		do {
			Integer inRead = 0;
			try {
			inRead = (Integer) in.readObject();
				if (inRead == 999) {
					out.writeObject("OK");
					break;
				}
			} catch (Exception e) {
				System.err.println(e);
			}
			switch(inRead){
				case 1:
					// Database 2
					try {
						try {
							radius = ((Double) in.readObject());
							tableName = ((String) in.readObject());
							data = new Data(tableName);
							qtminer = new QTMiner(radius);
							qtminer.compute(data);
							output = new OutputService(data, qtminer);
							out.writeObject("OK");
							out.writeObject(output.getOutputTO());
							out.writeObject("OK");
						} catch (ClusteringRadiusException e) {
							System.err.println(e.toString() + " with " + socket);
							out.writeObject("Warning: Only one cluster");
						} catch (Exception e) {
							System.err.println(e.toString() + " with " + socket);
							out.writeObject("Cannot compute");
						}
					} catch (IOException e) {
						System.err.println(e);
					}
					break;
					
				case 2:
					// Salvataggio su file
					try {
						try {
							String saveName = ".//dmp//" + tableName + "_" + Double.toString(radius) + ".dmp";
							ObjectOutputStream save = new ObjectOutputStream(new FileOutputStream(saveName));
							save.writeObject(output.getOutputTO());
							save.close();
							out.writeObject("OK");
						} catch (IOException e) {
							System.err.println(e.toString() + " with " + socket);
							out.writeObject("Cannot save File");
						} 
					} catch (IOException e) {
						System.err.println(e);
					}
					break;
					
				case 3:
					// Caricamento da file
					try {
						try {
							String fileName = (String) in.readObject();//prende dal client il nome del file cliccato
							load = new ObjectInputStream(new FileInputStream(".//dmp//"+ fileName));
							out.writeObject((OutputTO) load.readObject());
							out.writeObject("OK");
						} catch (IOException | ClassNotFoundException e) {
							System.err.println(e.toString() + " with " + socket);
							out.writeObject("Error: cannot read file");
						}
					} catch (IOException e) {
						System.err.println(e);
					}
						
					break;
					
				case 4:
					// Richiesta dei nomi delle tabelle
					try {
						try {
							out.writeObject(db.tableNameList());
							out.writeObject("OK");
						} catch (Exception e) {
							System.err.println(e.toString() + " with " + socket);
							out.writeObject("Error: cannot find table names");
						}
					} catch (IOException e) {
						System.err.println(e);					
					}
					break;
					
				case 5:
					// Richiesta nomi files
					try {
						try {
							out.writeObject(fileNameList());
							out.writeObject("OK");
						} catch (IOException e) {
							System.err.println(e.toString() + " with " + socket);
							out.writeObject("Error: cannot find filenames");
						}
					} catch (IOException e) {
						System.err.println(e);
					}
					break;
					
				default:
					try {
						out.writeObject("Error");
					} catch (IOException e) {
						System.err.println(e);
					}
					break;
			}
		} while (true);
		try {
			System.out.println("Closing connection with " + socket);
			socket.close();
		} catch (IOException e) {

		}
		

	}
	
	
	/**
	 * <p>Rappresenta la lista dei file</p>
	 * @return lista di stringhe rappresentate i nomi dei file
	 */
	private List<String> fileNameList() {
		//crea la cartella dmp se non esiste 
		File file = new File("./dmp");		
		if (!file.exists()) {
			file.mkdirs();
		}
		String[] fileArray = file.list(new FileInfoFilter(".dmp"));
		
		List<String> fileList = new ArrayList<String>();
		for (String s:fileArray) {
			fileList.add(s);
		}
		return fileList;
	}
	
	
	/**
	 * <p>Filtra sul file</p>
	 */
	private class FileInfoFilter implements FilenameFilter {
		/**
		 * <p>Filtro</p>
		 */
		private String filter;
		/**
		 * <p>Inizializza filter</p>
		 * @param filter rappresenta il filtro su cui scandire tutti i file
		 */
		private FileInfoFilter(String filter) { 
			this.filter=filter;
		}
		
		/**
		 * <p>Permette di scandire i file che rispettano il filtro</p>
		 * @return vero se il file rispetta il filtro , falso altrimenti
		 */
		@Override
		public boolean accept(File dir, String name) {
			String f = new File(name).getName();
			return f.indexOf(filter) != -1;
		}
	}
}


