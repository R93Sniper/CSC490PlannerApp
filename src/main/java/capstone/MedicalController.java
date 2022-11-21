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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
        preloadListView();
        loadChoiceBox();
        
        choiceBox.setItems(FXCollections.observableArrayList(medList));
        
    }    

    private void preloadListView(){
        ObservableList<String> li = listView.getItems();
        dataConnector dc = dataConnector.getInstance();
        MedicalConditionConnector mdc = new MedicalConditionConnector();
        
        UserProfileModel upm = UserProfileModel.getInstance();
        String medCons = dc.getMedConFromProfile(upm.getUserName());
        
        char[] arr = medCons.toCharArray();
        if(medCons == null){
            
            //then do nothing
        } else {
            for(int i = 0; i < arr.length; i++){
                int conditionID = Character.getNumericValue(arr[i]);
                
                
                //System.out.println(arr[i]);
                
               if(conditionID != -1){
               li.add(mdc.medicalName(conditionID));
               }
            }
        }
        
        
        
        
        
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
