/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.nyc;

import java.awt.Dimension;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.nyc.model.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnAdiacenti"
    private Button btnAdiacenti; // Value injected by FXMLLoader

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnCreaLista"
    private Button btnCreaLista; // Value injected by FXMLLoader

    @FXML // fx:id="cmbProvider"
    private ComboBox<Provider> cmbProvider; // Value injected by FXMLLoader

    @FXML // fx:id="cmbQuartiere"
    private ComboBox<City> cmbQuartiere; // Value injected by FXMLLoader

    @FXML // fx:id="txtMemoria"
    private TextField txtMemoria; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader
    
    @FXML // fx:id="clQuartiere"
    private TableColumn<?, ?> clQuartiere; // Value injected by FXMLLoader
 
    @FXML // fx:id="clDistanza"
    private TableColumn<City, Dimension> clDistanza; // Value injected by FXMLLoader
    
    @FXML // fx:id="tblQuartieri"
    private TableView<Adiacenza> tblQuartieri; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	Provider provider= cmbProvider.getValue();
    	if(provider==null) {
    		txtResult.appendText("Inserire un provider!");
    		return;
    	}
    	
    	model.creaGrafo(provider);
    	if(model.modelloCreato()==true) {
    		cmbQuartiere.getItems().addAll(model.getQuartieriGrafo());
    		txtResult.appendText("Grafo creato correttamente!"+"\n");
    		txtResult.appendText("Numero vertici: "+model.getNumeroVertici()+"\n");
    		txtResult.appendText("Numero archi: "+model.getNumeroArchi()+"\n");
    	}
    	
    }

    @FXML
    void doQuartieriAdiacenti(ActionEvent event) {
    	
    	if(cmbQuartiere.getValue()==null) {
    		txtResult.appendText("Errore: seleziona un quartiere\n");
    		return;
    	}
    	
    	if(model.modelloCreato()==true) {
    		
    		List<Adiacenza> quartieriCollegati = model.elencoQuartieriCollegati(cmbQuartiere.getValue());
    		Collections.sort(quartieriCollegati);
    		
    		//tblQuartieri.getItems().addAll(quartieriCollegati);
    		//tblQuartieri.setItems(FXCollections.observableList(quartieriCollegati));
    		
    		
    		txtResult.appendText("I quartieri vicini a "+cmbQuartiere.getValue()+ "sono: "+"\n");
    		for(Adiacenza aa : quartieriCollegati) {
    			txtResult.appendText(aa.toString());
    		}
    		
    	}
    	else {
    		txtResult.setText("Devi prima create il grafo!");
    	}
    	
    }

    @FXML
    void doSimula(ActionEvent event) {

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnAdiacenti != null : "fx:id=\"btnAdiacenti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaLista != null : "fx:id=\"btnCreaLista\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbProvider != null : "fx:id=\"cmbProvider\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbQuartiere != null : "fx:id=\"cmbQuartiere\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtMemoria != null : "fx:id=\"txtMemoria\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert clDistanza != null : "fx:id=\"clDistanza\" was not injected: check your FXML file 'Scene.fxml'.";
        assert clQuartiere != null : "fx:id=\"clQuartiere\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	cmbProvider.getItems().addAll(model.getAllProvider());
    }

}
