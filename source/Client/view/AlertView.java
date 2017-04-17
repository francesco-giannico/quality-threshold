package view;

import javafx.scene.control.Alert;

/**
 * <p>Modella la generica finestra popUp che mostra il  messaggio di errore</p>
 */
public class AlertView {
	/**
	 * <p>Mostra finestra pop-up</p>
	 * @param message Messaggio da visualizzare nella finestra
	 * @param type Tipo di alert da visualizzare
	 */
	public AlertView(String message, Alert.AlertType type) {
		Alert alert = new Alert(type);
		alert.setTitle("A problem has occurred.");
		alert.setContentText(message);
		alert.showAndWait();
	}
}
