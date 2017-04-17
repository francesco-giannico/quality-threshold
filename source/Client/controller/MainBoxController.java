package controller;

import java.io.IOException;
import java.net.Socket;

import data.OutputTO;
import view.AlertView;
import model.CommunicationException;
import model.DatabaseException;
import model.ServerCommunication;
import model.chart.BarChartRender;
import model.chart.ChartException;
import model.chart.ScatterChartRender;
import model.table.ParsedOutput;
import model.table.TableData;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * <p>Permette l'iterazione con l'utente: qualsiasi evento viene gestito richiedendo dei servizi al model e restituendo i risultati nell'interfaccia grafica.</p>
 */
public class MainBoxController {

	/**
	 * <p>Rappresenta il raggio inserito dall'utente nell'interfaccia grafica</p>
	 */
	@FXML
	private TextField radiusTextField;
	/**
	 * <p>Numero di clusters ottenuti dalla computazione del server</p>
	 */
	@FXML
	private TextField numClustersTextField;
	/**
	 * <p>Visualizzazione a schede con due schede: la prima è chiamata "Database" e mostra le tabelle del database.
	 * <p>La seconda mostra i file salvati presenti nella cartella /dmp</p>
	 */
	@FXML
	private TabPane sourceTabPane;
	/**
	 * <p>Pulsante per eseguire da lato server la computazione dalla tabella o dal file selezionato.</p>
	 */
	@FXML
	private Button mineButton;
	/**
	 * <p>Nomi degli attributi: Utile per la coordinata X nel grafico </p>
	 */
	@FXML
	private ChoiceBox<String> xAxisChoice;
	/**
	 * <p>Nomi degli attributi: Utile per la coordinata Y nel grafico </p>
	 */
	@FXML
	private ChoiceBox<String> yAxisChoice;
	/**
	 * <p>Lista delle tabelle presenti nel database del server e selezionabili</p>
	 */
	@FXML
	private ListView<String> tableListView;
	/**
	 * <p>Lista dei file presenti sul server e selezionabili </p>
	 */
	@FXML
	private ListView<String> fileListView;
	/**
	 * <p>Contiene tutti i valori di tutte le tuple in tutti gli attributi (Tabella Dinamica)</p>
	 */
	@FXML
	private TableView<ParsedOutput> outputTableView;
	/**
	 * <p>Contiene le due choicebox dei parametri X e Y per il grafico</p>
	 */
	@FXML
	private AnchorPane columnsAnchorPane;
	/**
	 * <p> HBox in cui inserire lo scatterChart </p>
	 */
	@FXML
	private HBox scatterChartHBox;	
	/**
	 * <p> VBox in cui inserire il diagramma a barre </p>
	 */
	@FXML
	private VBox barChartVBox;
	/**
	 * <p>{@link ServerCommunication}</p>
	 */
	private ServerCommunication serverCommunication;
	
	/**
	 * <p> Oggetto per il rendering dello scatterChart </p>
	 */
	private ScatterChartRender scatterChartRendering;
	
	/**
	 * <p> Oggetto per il rendering dell'istogramma </p>
	 */
	private BarChartRender barChartRendering;

	/**
	 * <p>Ascoltatore per il pulsante di mining</p>
	 */
	private class MineButtonListener implements EventHandler<ActionEvent> {  
		/**
		 * <p>Ascoltatore per la prima  colonna di attributi</p>
		 */
		private AxisChoiceListener xAxisChoiceListener = new AxisChoiceListener(yAxisChoice);
		/**
		 * <p>Ascoltatore per la seconda colonna di attributi</p>
		 */
		private AxisChoiceListener yAxisChoiceListener = new AxisChoiceListener(xAxisChoice);

		/**Invia la richiesta al server per l'output. A seconda della scheda selezionata (Database o file) chiama il metodo relativo 
		 * @param e il pulsante "mine" è stato premuto
		 */
		@Override 
	    public void handle(ActionEvent e){
			columnsAnchorPane.setVisible(false);
			xAxisChoice.getSelectionModel().selectedItemProperty().removeListener(xAxisChoiceListener);
			yAxisChoice.getSelectionModel().selectedItemProperty().removeListener(yAxisChoiceListener);
			TableData table = null;
			OutputTO output;
	        try{
	    		ObservableList<String> attributes;
		    	if(sourceTabPane.getSelectionModel().getSelectedItem().getText().equals("Table")){
		    		if (tableListView.getSelectionModel().getSelectedIndex() < 0)
		    			throw new ControllerException("No table selected!");
		    		String selectedDB = tableListView.getSelectionModel().getSelectedItem();
		    		output = serverCommunication.learningFromDBAction(selectedDB, Double.parseDouble(radiusTextField.getText()));	    		

		    	} else {
		    		if (fileListView.getSelectionModel().getSelectedIndex()<0) {
		    			throw new ControllerException("No file selected!");
		    		}
		    		String selectedFile = fileListView.getSelectionModel().getSelectedItem();
		    		output = serverCommunication.learningFromFileAction(selectedFile);	    		
		    	}
	    		attributes = FXCollections.observableList(output.getAttributeNames());
	    		
	    		table = new TableData(output);	
	    		if (attributes.size() > 1) { 
	    			scatterChartRendering = new ScatterChartRender(output, scatterChartHBox);
			    	setUpColumns(attributes);
					int x = xAxisChoice.getSelectionModel().getSelectedIndex();
					int y = yAxisChoice.getSelectionModel().getSelectedIndex();
		    		scatterChartRendering.showChart(x, y);
		    		columnsAnchorPane.setVisible(true);
				} else {
					new AlertView("Insufficient columns to plot scatter chart", AlertType.WARNING);
					scatterChartHBox.getChildren().clear();
				}
	    		barChartRendering = new BarChartRender(output, barChartVBox);
	    		barChartVBox.getScene().getWindow().setHeight(720);
	    		numClustersTextField.setText(Integer.toString(table.getRows().size()));
		    	outputTableView.getColumns().clear();
		    	outputTableView.setItems(table.getRows());
		    	outputTableView.getColumns().addAll(table.getColumns());
		    	outputTableView.getSelectionModel().select(0);

	       } catch (DatabaseException ex) {
	    	   new AlertView(ex.getMessage(), AlertType.WARNING);
	       } catch (ControllerException ex) {
	    	   new AlertView(ex.getMessage(), AlertType.ERROR);
	       } catch (ChartException ex) {
	    	   new AlertView("Cannot load chart", AlertType.ERROR);
	       } catch (CommunicationException ex) {
	    	   new AlertView(ex.getMessage(), AlertType.ERROR);
	       }
		}
		/**
		 * <p>Imposta gli attributi nelle choiceBox</p>
		 * <p>Non permette di selezionare due attributi uguali per la coordinata X e Y </p>
		 * <p>Associa gli ascoltatori alle due choiceBox</p>
		 * @param attributes Attributi con cui riempire le choicebox
		 */
		private void setUpColumns(ObservableList<String> attributes) {
			xAxisChoice.setVisible(true);
			xAxisChoice.setItems(attributes);
	    	xAxisChoice.getSelectionModel().select(0);
			xAxisChoice.getSelectionModel().selectedItemProperty().addListener(xAxisChoiceListener);
			yAxisChoice.setVisible(true);
			yAxisChoice.setItems(attributes);
	    	yAxisChoice.getSelectionModel().select(1);
			yAxisChoice.getSelectionModel().selectedItemProperty().addListener(yAxisChoiceListener);
		}

	}
	/**
	 * <p> Ascoltatore per il cambio di tab </p>
	 */
	private class TabChangeListener implements ChangeListener<Number>{
		/**
		 * <p>nomi delle tabelle del database oppure nomi dei files</p>
		 */
		private ObservableList<String> sourceNames = null;
		/**
		 * <p>Inizializza l'ascoltatore a null </p>
		 */
		private TabChangeListener() {
    		changed(null, null, 0);		
		}
		/**
		 * <p>Quando viene selezionato il pannello "Table" : Richiede il servizio al serverCommunication per settare il nome delle tabelle ed imposta il raggio di default a 1.00</p>
		 * <p>Quando viene selezionato il pannello "File" : Richiede il servizio al serverCommunication per settare i nomi dei file da poter selezionare e rende non settabile la TextField del raggio</p>
		 */
		@Override
	    public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
	    	try {
		    	if ((Integer) newValue == 0) {
		    		sourceNames = FXCollections.observableList(serverCommunication.getTableNames());
		    		tableListView.setItems(sourceNames);
		    		radiusTextField.setText("1.00");
					radiusTextField.setEditable(true);
		        } else if ((Integer) newValue == 1) {
					sourceNames = FXCollections.observableList(serverCommunication.getFileNames());
					fileListView.setItems(sourceNames);
					radiusTextField.setText("");
					radiusTextField.setEditable(false);
		        }
	        } catch (DatabaseException e) {
	        	new AlertView(e.toString(), AlertType.WARNING);
	        } catch (CommunicationException e) {
	        	new AlertView(e.getMessage(), AlertType.ERROR);
	        }
	    }
	} 
	/**
	 * <p>Ascoltatore per le choiceBox</p>
	 */
	private class AxisChoiceListener implements ChangeListener<String> {
		/**
		 * <p>ChoiceBox a cui associare l'ascoltatore</p>
		 */
		private ChoiceBox<String> c1;
		/**
		 * <p>Inizializza la choicebox di classe</p>
		 * @param c1 choiceBox a cui associare l'ascoltatore
		 */
		private AxisChoiceListener(ChoiceBox<String> c1) {
			this.c1 = c1;
		}
		/**
		 * <p>1) Se i valori inseriti sono uguali li scambia </p>
		 * <p>2) Aggiorna il grafico basandosi sulle nuove coordinate inserite</p>
		 */
		@Override
	    public void changed(ObservableValue<? extends String> ov, String oldValue, String newValue) {
			if(newValue.equals(c1.getSelectionModel().getSelectedItem())) 
				c1.getSelectionModel().select(oldValue);
			else{
				int x = xAxisChoice.getSelectionModel().getSelectedIndex();
				int y = yAxisChoice.getSelectionModel().getSelectedIndex();
				try {
					scatterChartRendering.showChart(x, y);
				} catch (ChartException e) {
					new AlertView("Cannot load scatter chart", AlertType.ERROR);
				}
			}
		}
	}
	
	/**
	 * Ascoltatore per tableListView 
	 */
	private class TableListViewListener implements ChangeListener<ParsedOutput> {

		/**
		 * <p>Funzione chiamata ad ogni selezione di un elemento nella tabella, crea un barChart con le distanze delle tuple dal 
		 * centroide selezionato in tabella</p>
		 */
		@Override
		public void changed(ObservableValue<? extends ParsedOutput> arg0, ParsedOutput arg1, ParsedOutput arg2) {
    		if (arg2 != null) {
	    		try {
					barChartRendering.showChart(Integer.parseInt(arg2.getParsedRow(0).getValue()));
				} catch (NumberFormatException | ChartException e) {
					new AlertView("Cannot load bar chart.", AlertType.ERROR);
				}
			}
		}
	}

	/**
	 * <p> Riferisce al serverCommunication che la connessione deve essere chiusa </p>
	 * @throws ClassNotFoundException {@link ClassNotFoundException}
	 * @throws IOException			  {@link IOException}
	 */
	public void close() throws ClassNotFoundException, IOException {
		serverCommunication.closeSignal();
	}
	/**
	 * <p> Inizializzazione del controller del main box con setting di ascoltatori e inizializzazione della comunicazione col server <p>
	 * @param socket				  {@link Socket}
	 * @throws ClassNotFoundException {@link ClassNotFoundException}
	 * @throws IOException			  {@link IOException}
	 */
	public void initMainBox(Socket socket) throws ClassNotFoundException, IOException {
		try {
			serverCommunication = new ServerCommunication(socket);
			mineButton.setOnAction(new MineButtonListener());
			sourceTabPane.getSelectionModel().selectedIndexProperty().addListener(new TabChangeListener());
			outputTableView.getSelectionModel().selectedItemProperty().addListener(new TableListViewListener());
		} catch (CommunicationException e) {
			new AlertView(e.getMessage(), AlertType.ERROR);
		}
	}
}
