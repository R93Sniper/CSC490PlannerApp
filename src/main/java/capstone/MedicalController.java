/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package capstone;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Nick
 */
public class MedicalController implements Initializable {

    @FXML
    private Button btnSave;
    @FXML
    private Button AddButton;
    @FXML
    private Button clearButton;
    @FXML
    private Button backButton;
    @FXML
    private ChoiceBox<?> choiceBox;
    @FXML
    private ListView<?> listView;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void onGoBack(ActionEvent event) {
    }

    @FXML
    private void onSave(ActionEvent event) {
    }

    @FXML
    private void AddtoListView(ActionEvent event) {
        
        
    }

    @FXML
    private void clearListView(ActionEvent event) {
        
        
    }
    
}
