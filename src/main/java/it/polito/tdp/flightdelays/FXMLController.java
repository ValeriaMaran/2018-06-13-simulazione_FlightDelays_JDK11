package it.polito.tdp.flightdelays;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import com.sun.javafx.binding.StringFormatter;

import it.polito.tdp.flightdelays.db.FlightDelaysDAO;
import it.polito.tdp.flightdelays.model.Adiacenze;
import it.polito.tdp.flightdelays.model.Airline;
import it.polito.tdp.flightdelays.model.Airport;
import it.polito.tdp.flightdelays.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea txtResult;

    @FXML
    private ComboBox<String> cmbBoxLineaAerea;

    @FXML
    private Button caricaVoliBtn;

    @FXML
    private TextField numeroPasseggeriTxtInput;

    @FXML
    private TextField numeroVoliTxtInput;

    @FXML
    void doCaricaVoli(ActionEvent event) {
    	txtResult.clear();
    	String compagnia = cmbBoxLineaAerea.getValue();
    	if(compagnia == null) {
    		txtResult.appendText("prima di creare un grafo devi selezionare la compagnia aerea");
    	}
    	else {
    		try{
    			String[] Confronto = compagnia.split("-");
	    		String id = Confronto[0]; 
	    		String nome = Confronto[1]; 
	    		model.CreaGrafo(id);
    		}
    		catch(Exception e) {
    			txtResult.appendText("errore nella compagnia aerea");
    			return;
    		}
    		
    	}
    	List<Adiacenze> adiacenze = model.getRottePeggiori();
    	for(Adiacenze a : adiacenze) {
    		txtResult.appendText(a.toStringRottePeggiori());
    	}
    	
    	txtResult.appendText("il grafo è stato creato correttamente e contiene "+ model.getVertexNumber()+" vertici e " +model.getEdgesNumber()+" archi");
    
    }

    @FXML
    void doSimula(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'FlightDelays.fxml'.";
        assert cmbBoxLineaAerea != null : "fx:id=\"cmbBoxLineaAerea\" was not injected: check your FXML file 'FlightDelays.fxml'.";
        assert caricaVoliBtn != null : "fx:id=\"caricaVoliBtn\" was not injected: check your FXML file 'FlightDelays.fxml'.";
        assert numeroPasseggeriTxtInput != null : "fx:id=\"numeroPasseggeriTxtInput\" was not injected: check your FXML file 'FlightDelays.fxml'.";
        assert numeroVoliTxtInput != null : "fx:id=\"numeroVoliTxtInput\" was not injected: check your FXML file 'FlightDelays.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
		cmbBoxLineaAerea.getItems().addAll(model.getMappaLinee());
	}
}
