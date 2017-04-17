package controller;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

import view.AlertView;
import view.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

/**
 *	<p> Classe Controller relativa all'interfaccia di connessione </p>
 */
public class ConnectionSettingsController implements Initializable {
	/**
	 * <p>Casella di testo della porta e dell'indirizzo</p>
	 */
	@FXML
	private TextField portTextField, addressTextField;
	/**
	 * <p>Bottone di connessione</p>
	 */
	@FXML
	private Button connectButton;
	/**
	 * <p>Socket per la connessione</p>
	 */
	private Socket connection;
	/**
	 * <p>Main</p>
	 */
	private Main mainApp;
	
	/**
	 * <p> Listener per il connect button </p>
	 */
	private class ConnectButtonListener implements EventHandler<ActionEvent> {  
		/**
		 * <p> Gestore per l'evento di pressione del pulsante di connessione. Quando viene premuto inizializza la connessione e passa la socket alla main app. </p>
		 */
		@Override 
	    public void handle(ActionEvent e){
			try {
				InetAddress addr = InetAddress.getByName(addressTextField.getText());
				connection = new Socket(addr, Integer.parseInt(portTextField.getText()));
				System.out.println("Connection with " + connection.getInetAddress() + ":" + connection.getPort() + " started.");
				mainApp.setSocket(connection);
				mainApp.setApplicationForm();
			} catch (Exception e1) {
				new AlertView("Connection refused.", AlertType.ERROR);
			}
		}
	}

	/**
	 * <p> Setter dell'applicazione principale </p>
	 * @param main Istanza della main application
	 */
	public void setApplication(Main main) {
		this.mainApp = main;
	}
	
	/**
	 * <p> Connette il Listener del connect button alla relativa componente grafica </p>
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		connectButton.setOnAction(new ConnectButtonListener());
	}
}
