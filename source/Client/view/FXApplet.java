package view;

import java.io.IOException;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.Alert.AlertType;

import javax.swing.JApplet;
import javax.swing.SwingUtilities;
/**
 *  <p>Rende possibile la visualizzazione di un interfaccia sviluppata con JAVAFX come applet.</p>
 */
@SuppressWarnings("serial")
abstract class FXApplet extends JApplet{
	/**
	 *  <p>Pannello principale dell'interfaccia sviluppata in JAVAFX </p>
	 */
	protected JFXPanel fxPanel;
	/**
	 *  <p>Inizializza JFXPanel</p>
	 *  @throws IOException {@link IOException}
	 */
	protected abstract void initFX() throws IOException;

	/**
	 *  <p>Invocato quando l'applet è caricata</p>
	 */
	 @Override
	public final void init() { 
	    SwingUtilities.invokeLater(new Runnable() {
	        @Override
	        public void run() {
	            initSwing();
	        }
	    });
	    setSize(1280,720);
	}
	 /**
	  *  <p>Invocato nel thread delle swing </p>
	  */
	 private void initSwing() { 
	        fxPanel = new JFXPanel();
	        add(fxPanel);

	        Platform.runLater(new Runnable() {
	            @Override
	            public void run() {
	                try {
						initFX();
					} catch (IOException e) {
						new AlertView("Failed to execute applet.", AlertType.ERROR);
					}
	            }
	        });
	    }
}
