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
    ArrayList<Integer> medConSelected = new ArrayList<>();
   
    
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
        App.setRoot("Profile");
    }

    @FXML
    private void onSave(ActionEvent event) {
        
       String str = listView.getItems().toString();
        System.out.println(str);
        
        UserProfileModel md = UserProfileModel.getInstance();
        
        dataConnector dc = dataConnector.getInstance();
        
        MedicalConditionConnector mdc = new MedicalConditionConnector();
        for(int i = 0; i <= medList.size(); i++){
            
            String medCon = mdc.medicalName(i);
            
            if(str.contains(medCon)){
                medConSelected.add(i);
            }
            
        }
        medConSelected.remove(0);
        
        String finalMedConditions = medConSelected.toString();
        
        dc.writeMedicalConditions(finalMedConditions, md.getUserName());
        
        
        
    }

    @FXML
    private void AddtoListView(ActionEvent event) {
        
        ObservableList<String> li = listView.getItems();
        
        
        
        
        if(!li.contains(choiceBox.getValue())){
            //maybe make an alert? Stop that add
            li.add(choiceBox.getValue());
        }
        
        
        
    }

    @FXML
    private void clearListView(ActionEvent event) {
        ObservableList<String> li = listView.getItems();
        li.clear();
        
    }
    
}
