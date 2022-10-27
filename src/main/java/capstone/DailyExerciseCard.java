/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capstone;

import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

/**
 *
 * @author Wahab Quazi, Jesus Alvarado
 */
public class DailyExerciseCard {
   
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfCaloriesOut;
     @FXML
    private TextField tfDuration;
      @FXML
    private TextField tfReps;
       @FXML
    private TextField tfSets;
              
    private ObservableList<String> obsList = FXCollections.observableArrayList();
    private ListView listView;
    
    
    @FXML
    public void initialize(){
    
    }
    
    
    
    @FXML
    public void onGoBack() throws IOException{ App.setRoot("progresscard");}
    
    @FXML
    public void onSave(){
    
    }
    
    @FXML
    public void onAdd(){
        obsList.add("["+obsList.size()+"] "+tfName.getText());

        listView.setItems(obsList);
        tfName.setText("");
    
    }
    
}
