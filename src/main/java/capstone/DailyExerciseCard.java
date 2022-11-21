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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
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
    ArrayList<ExerciseItem> exerciseList = new ArrayList<>();
    UserProfileModel user = UserProfileModel.getInstance();
    DailyExerciseConnector dc = new DailyExerciseConnector();
    ArrayList<Integer> exLogIds = new ArrayList<>();
    ExerciseDetailModel exDetailModel = ExerciseDetailModel.getInstance();
    private ProgressCardConnector pcTable = new ProgressCardConnector();
    
    @FXML
    public void initialize() {
        
        this.loadExerciseListView();
       
        if(checkPhysicalActivity()==false){
        
        String modelName = exDetailModel.name;
        if (!modelName.equals("")) {
            tfName.setText(exDetailModel.name);
            if (exDetailModel.targetedMuscle.equals("cardiovascular system")) {
                rbCardio.setSelected(true);
                onCardioSelected(); 
            } else {
                rbWeightLifting.setSelected(true);
                onWeightLiftingSelected();
            }
            exDetailModel.resetModel();
            
        }else{ //else if modelName is empty then by default do this
            rbCardio.setSelected(true);
            onCardioSelected();
        }
        }

    }
    
    @FXML
    private boolean checkPhysicalActivity(){
        onCardioSelected();
        String activity = user.physicalActivity;
        if(!activity.equals("")){
        tfName.setText(activity);
        tfCaloriesOut.setText(user.caloriesBurned);
        tfDuration.setText(user.duration);
        user.resetPhysicalActivity();
        return true;
        }
        return false;
    }
    
    @FXML
    private void pac() throws IOException{
        App.setRoot("physicalActivityCalc");
    }

    @FXML
    public void loadExerciseListView() {
        
        String logIds = dc.getExerciseLogIds(user.getDailyExerciseId());
        if (!logIds.equals("")) {
            String[] idArray = logIds.split("-");
            for (int i = 0; i < idArray.length; i++) {
                ExerciseItem item = dc.getExerciseLogRow(Integer.valueOf(idArray[i]));
                if (item != null) {
                    String str = "";
                    str = "[" + obsList.size() + "] " + item.name;
                    if(item.sets.equals("")){
                    str += item.distance.equals("") ?"\n Duration: "+item.duration: 
                    "\n Distance: " +item.distance;                    
                    str += item.caloriesBurned.equals("") ? "" : "\n Calories Burned: "+ item.caloriesBurned;
                    
                    }else{
                    str += item.sets.equals("")? "" : "\n Sets: "+ item.sets 
                          +"\n Reps: " + item.reps
                          +"\n WeightLifted: "+ item.weightLifted; 
                    }                          
                    obsList.add(str);      
                    exerciseList.add(item);
                    exLogIds.add(Integer.valueOf(idArray[i]));
                }
                    
                }

            }
            listView.setItems(obsList);
        }
    

    @FXML
    public void onGoBack() throws IOException {
        App.setRoot("progresscard");
    }

    @FXML
    public void onSave() throws IOException {
        String strIds = "";
        if (exLogIds.size() > 0) {
            strIds = exLogIds.get(0).toString();
            for (int i = 1; i < exLogIds.size(); i++) {
                strIds += "-" + exLogIds.get(i).toString();
            }
        }
        if (user.getDailyExerciseId() == 0 || user.getDailyExerciseId()== -1) {
            //insert new row in table
            dc.insertNewDailyExerciseCard(strIds);
            //get the id of last inserted row and store it in the userProfileModel, as well as the progress card
            int eid = dc.getLastRow("Daily_Exercise_Cards");
            user.setDailyExerciseId(eid);
        } else {
            //update row in table
            dc.updateExerciseLogIds(user.getDailyExerciseId(), strIds);
        }
        String msg ="";
        if (user.getProgressCardId() == 0 ||user.getProgressCardId()==-1) {
            msg = msg + "WARNING!: Need to Create and Save a Progress Card,"
                    + "\nOtherwise this DailyExerciseCard will be lost and,"
                    + "\nNot be saved to Profile, when User logs out.";
            makeAlert(msg);
            onGoBack();
        }else{ 
            msg = "This DailyExerciseCard Saved!! \n";
            makeAlert(msg);
            pcTable.updateDailyExerciseID(user.getProgressCardId(), String.valueOf(user.getDailyExerciseId()));
        } 

    }

    @FXML
    public void onAdd() {

        String name = tfName.getText() == null ? "" : tfName.getText();
        if (name.equals("")) {
            makeAlert("Text field for Exercise Name is Empty!!");
            return;
        }
        String distance ="";
        String duration="";
        String reps = "";
        String sets ="";
        String weightLifted="";
        String caloriesBurned="";
        String str ="";                
        str = "[" + obsList.size() + "] " + tfName.getText();
        caloriesBurned = tfCaloriesOut.getText() == null ? "" : tfCaloriesOut.getText();
        if (rbCardio.isSelected()) {
            distance = tfDistance.getText() == null ? "" : tfDistance.getText();
            duration = tfDuration.getText() == null ? "" : tfDuration.getText();
            //check if any of the textfileds are null and if so return and sent an alert out
            if (distance.equals("") && duration.equals("")) {
                makeAlert("Need to fill out distance or duration!");
                return;
            }
            dc.insertNewExerciseLogCardio(name, duration, distance, caloriesBurned);
            exLogIds.add(dc.getLastRow("ExerciseLog"));
            tfDistance.setText("");
            tfDuration.setText("");
            str += distance.equals("") ?"\n Duration: "+duration: 
                    "\n Distance: " +distance;
                                  
            str += caloriesBurned.equals("") ? "" : "\n Calories Burned: "+ caloriesBurned;

        } else {
            reps = tfReps.getText() == null ? "" : tfReps.getText();
            sets = tfSets.getText() == null ? "" : tfSets.getText();
            weightLifted = tfWeight.getText() == null ? "" : tfWeight.getText();

            //check if any of the textfileds are null and if so return and sent an alert out
            if (reps.equals("") || sets.equals("") || weightLifted.equals("")) {
                makeAlert("Need to fill out input for: reps, set, weightLifted");
                return;
            }
            dc.insertNewExerciseLogWeightLifting(name, weightLifted, sets, reps);
            exLogIds.add(dc.getLastRow("ExerciseLog"));
            tfReps.setText("");
            tfSets.setText("");
            tfWeight.setText("");
            exDetailModel.resetModel();
                                   
            str += sets.equals("")? "" : "\n Sets: "+ sets 
                          +"\n Reps: " + reps
                          +"\n WeightLifted: "+ weightLifted; 
        }
                           
        obsList.add(str);
        listView.setItems(obsList);
        tfName.setText("");

    }

    @FXML
    public void onCardioSelected() {
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
    public void onWeightLiftingSelected() {
        rbWeightLifting.setSelected(true);
        tfWeight.setVisible(true);
        tfReps.setVisible(true);
        tfSets.setVisible(true);

        rbCardio.setSelected(false);
        tfDuration.setVisible(false);
        tfDistance.setVisible(false);
        tfCaloriesOut.setVisible(false);

    }

    public void makeAlert(String alertText) {

        Alert a = new Alert(AlertType.INFORMATION);
        a.setTitle("Daily Exercise Card");
        a.setHeaderText(alertText);
        a.show();

    }

    @FXML
    public void onExerciseLookup() throws IOException {

        App.setRoot("ExerciseLookUp");
    }
    
}
