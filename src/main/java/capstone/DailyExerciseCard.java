/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capstone;

import java.io.IOException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

/**
 *
 * @author Wahab Quazi, Jesus Alvarado
 */
public class DailyExerciseCard {

    @FXML
    private TextField tfName;
    @FXML
    private TextField tfWeight;
    @FXML
    private TextField tfDistance;
    @FXML
    private TextField tfCaloriesOut;
    @FXML
    private TextField tfDuration;
    @FXML
    private TextField tfReps;
    @FXML
    private TextField tfSets;
    @FXML
    private ListView listView;
    @FXML
    private RadioButton rbCardio;
    @FXML
    private RadioButton rbWeightLifting;

    private ObservableList<String> obsList = FXCollections.observableArrayList();
    ArrayList<Exercise> exerciseList = new ArrayList<>();
    
    @FXML
    public void initialize() {
        this.onCardioSelected();

    }

    @FXML
    public void onGoBack() throws IOException {
        App.setRoot("progresscard");
    }

    @FXML
    public void onSave() {

    }

    @FXML
    public void onAdd() {
        Exercise ex = new Exercise();
        
        if(rbCardio.isSelected()){
        ex.distance = tfDistance.getText();
        ex.duration = tfDuration.getText();
        ex.caloriesOut = tfCaloriesOut.getText();
        }else{
       
        ex.reps = tfReps.getText();
        ex.sets = tfSets.getText();
        ex.weightLifted = tfWeight.getText();
        }
        
      
                
        
        obsList.add("[" + obsList.size() + "] " + tfName.getText());

        listView.setItems(obsList);
        tfName.setText("");

    }
    
    
    
    @FXML
    public void onCardioSelected(){
    rbWeightLifting.setSelected(false);
    tfWeight.setVisible(false);
    tfReps.setVisible(false);
    tfSets.setVisible(false);
    
    rbCardio.setSelected(true);
    tfDuration.setVisible(true);
    tfDistance.setVisible(true);
    tfCaloriesOut.setVisible(true);
    
    
    }
    
    @FXML
    public void onWeightLiftingSelected(){
    rbWeightLifting.setSelected(true);
    tfWeight.setVisible(true);
    tfReps.setVisible(true);
    tfSets.setVisible(true);
    
    rbCardio.setSelected(false);
    tfDuration.setVisible(false);
    tfDistance.setVisible(false);
    tfCaloriesOut.setVisible(false);
    
    
    }
            

}
