package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.net.Socket;
import controller.ConnectionSettingsController;
import controller.MainBoxController;

/**
 *	<p> Classe che si occupa della creazione dell'interfaccia principale </p>
 */
@SuppressWarnings("serial")
public class Main extends FXApplet{
	 /**
	 *	<p> Controller dell'interfaccia principale </p>
	 */
	private MainBoxController mbController = new MainBoxController();
	
	/**
	 *	<p> Controller dell'interfaccia relativa alla connessione </p>
	 */
	private ConnectionSettingsController csController = new ConnectionSettingsController();
	
	/**
	 *	<p> Socket per la connessione con il server </p>
	 */
	private Socket socket = null; 
  
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void initFX() throws IOException { // Invocato nel thread di JavaFX
	    	initConnectionForm();
	    }
	
	 /**
	  *	<p> Funzione di servizio che contiene la procedura per visualizzare l'interfaccia relativa alla connessione con il server </p>
	 */
	private void initConnectionForm() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/view/ConnectionSettings.fxml"));
			AnchorPane connectionForm = loader.load();
			Scene scene = new Scene(connectionForm);
			csController = loader.getController();
			csController.setApplication(this);
			fxPanel.setScene(scene);
			fxPanel.setVisible(true);
		} catch(Exception e) {
			new AlertView( e.toString(), AlertType.ERROR);
		}
		
	}
	/**
	 *	<p> Si occupa di modeficare l'interfaccia dopo la connessione con il server </p>
	 */
	public void setApplicationForm(){
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/view/MainBox.fxml"));
			AnchorPane mainBox = loader.load();
			Scene scene = new Scene(mainBox);
			mbController = loader.getController();
			mbController.initMainBox(socket);
			fxPanel.setVisible(false);
			fxPanel.setScene(scene);
			fxPanel.setVisible(true);
		} catch (Exception e2) {
			new AlertView("Cannot load application form.", AlertType.ERROR);
		}
	}

	/**
	 * <p> Effettua una chiusura delle connessioni se è attiva </p>
	 */
	@Override
	public void destroy(){
		try
		{	
			if(socket != null){
				mbController.close();
				socket.close();
			} 
			stop();
		} catch (Exception e1) {
			new AlertView("No connection opened", AlertType.ERROR);
		}
	}

	/**
	 *<p> Imposta la socket </p>
	 *@param socket {@link Socket}
	 */
	public void setSocket(Socket socket) {
		this.socket = socket;
	}	   

}

