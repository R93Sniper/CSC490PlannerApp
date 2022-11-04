/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package capstone;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
public class MedicalController {

    @FXML
    private Button btnSave;
    @FXML
    private Button AddButton;
    @FXML
    private Button clearButton;
    @FXML
    private Button backButton;
    @FXML
    private ChoiceBox<String> choiceBox;
    @FXML
    private ListView<String> listView;

     ArrayList<String> medList = new ArrayList<>();
    ArrayList<Integer> medIDList = new ArrayList<>();
    
    /**
     * Initializes the controller class.
     */
    
    public void initialize() {
        loadChoiceBox();
        
        choiceBox.setItems(FXCollections.observableArrayList(medList));
        
    }    

   
    private void loadChoiceBox(){
        
        dataConnector mdc = MedicalConditionConnector.getInstance();
        ResultSet allMeds = mdc.getAllMedicalConditions();
        
        try{
            while(allMeds.next()){
                medIDList.add(allMeds.getInt("ID"));
                medList.add(allMeds.getString("Medical_Name"));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        
        
    }
    @FXML
    private void onGoBack(ActionEvent event) throws IOException {
        App.setRoot("UserHome");
    }

    @FXML
    private void onSave(ActionEvent event) {
        
    }

    @FXML
    private void AddtoListView(ActionEvent event) {
        
        ObservableList<String> li = listView.getItems();
        
        li.add(choiceBox.getValue());
        
        
        
        
        
    }

    @FXML
    private void clearListView(ActionEvent event) {
        ObservableList<String> li = listView.getItems();
        li.clear();
        
    }
    
}
