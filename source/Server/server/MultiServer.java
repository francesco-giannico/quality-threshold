package server;
import java.net.*;
import java.io.*;

/**
 * <p>Classe che implementa un multiserver in grado di gestire più client</p>
 */
class MultiServer {
	
	/**
	 * <p> intero indicante la porta del server </p>
	 */
	private int PORT = 8080;
	
	/**
	 * <p>Costruttore della classe. Chiama run() e acquisisce la porta</p>
	 * @param port intero rappresentante la porta
	 * @throws IOException  {@link IOException}k
	 */ 
	private MultiServer(int port) throws IOException{
		PORT = port;
		run();
	}
	
	/**
	 * <p>avvia un client per ogni richiesta ricevuta tramite socket</p>
	 * @throws IOException {@link IOException}
	 */
	private void run() throws IOException {
		ServerSocket s = new ServerSocket(PORT);
		System.out.println("Server Started");
		Socket socket = null;
		try {
			while (true) {
				socket = s.accept(); 
				new ServeOneClient(socket);
			}
		} catch (IOException e) {
			System.err.println("Server error, closing server...");
		} finally {
			System.out.println("Connection with: "+socket+" closed.");
			socket.close();
			s.close();
		}
	}
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		File file = new File("./dmp");
		if (!file.exists()) {
			file.mkdirs();
		}
		
		try {
			MultiServer ms;
			if (args.length == 0)
				ms = new MultiServer(8080);
			else {
				ms = new MultiServer(Integer.parseInt(args[0]));
			}
		} catch (NumberFormatException e) {
			System.err.println("Port is invalid");
		} catch (IOException e) {
			System.err.println("Server already running");
		} 
	}
}
