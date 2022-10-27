/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capstone;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;

/**
 * FXML Controller class
 *
 * @author jesus
 */
public class ExerciseLookUpController {
    
    

    @FXML
    private ChoiceBox<String> cbEquipment;
    @FXML
    private ChoiceBox<String> cbBodyPart;
    @FXML
    private ChoiceBox<String> cbTarget;
    @FXML
    private ListView lvExercises;
    ObservableList<String> obsList = FXCollections.observableArrayList();
    ExerciseDetailModel detailModel = ExerciseDetailModel.getInstance();
   
    private int itemsRowSelected = -1;
    Exercise[] items;
   
    private String filterValue="";  
    private String lastSelectedFilter="";
    private String modelGif="";
    private String modelName="";
    private String modelTargetMuscle="";
    private String modelBodyPart="";
    private String modelEquipment="";
    ArrayList<String> equipmentList;
      ArrayList<String> bodyPartList;
        ArrayList<String> targetList;
    

    @FXML
    public void initialize() {
        setArrayLists();
        loadChoiceBox();

    }

    @FXML
    public void goBack() throws IOException {
        App.setRoot("UserHome");
    }
    
    @FXML
    public void onRunAPI(){
        obsList.clear();
        ExerciseApiConnector api = new ExerciseApiConnector();
        String result = api.getJSONFromAPI(filterValue, lastSelectedFilter);
        items = api.parseJSON(result);
        int i=0;
        for (Exercise item : items) {
            //System.out.println("["+i+"] "+item.getName());
            obsList.add(item.getName());
            i++;
        }
       
        lvExercises.setItems(obsList);
        lvExercises.getSelectionModel().selectedIndexProperty()
                .addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue ov, Number value, Number new_value) {
                           //System.out.println("index= "+new_value.intValue());
                           itemsRowSelected = new_value.intValue();
                        try {
                            onViewDetailExercise();
                        } catch (IOException ex) {
                            Logger.getLogger(ExerciseLookUpController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
       // lvExercises.getSelectionModel().getSelectedItem().
        
    
    }
  
    private void setArrayLists(){
    
             bodyPartList = new ArrayList<>(Arrays.asList("back", "cardio",
                "chest", "lower arms", "lower legs", "neck", "shoulders", "upper arms",
                "upper legs", "waist"));
         targetList = new ArrayList<>(Arrays.asList(
                "abductors",
                "abs",
                "adductors",
                "biceps",
                "calves",
                "cardiovascular system",
                "delts",
                "forearms",
                "glutes",
                "hamstrings",
                "lats",
                "levator scapulae",
                "pectorals",
                "quads",
                "serratus anterior",
                "spine",
                "traps",
                "triceps",
                "upper back"));

        equipmentList = new ArrayList<>(Arrays.asList(
                "assisted",
                "band",
                "barbell",
                "body weight",
                "bosu ball",
                "cable",
                "dumbbell",
                "elliptical machine",
                "ez barbell",
                "hammer",
                "kettlebell",
                "leverage machine",
                "medicine ball",
                "olympic barbell",
                "resistance band",
                "roller",
                "rope",
                "skierg machine",
                "sled machine",
                "smith machine",
                "stability ball",
                "stationary bike",
                "stepmill machine",
                "tire",
                "trap bar",
                "upper body ergometer",
                "weighted",
                "wheel roller"
        ));
    
    }
    
    
    @FXML
    private void loadChoiceBox() {

        
        cbEquipment.setItems(FXCollections.observableArrayList(equipmentList));
        cbEquipment.getSelectionModel().selectedIndexProperty()
                .addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue ov, Number value, Number new_value) {
                           filterValue = equipmentList.get(new_value.intValue());
                           lastSelectedFilter = "equipment";
                           onRunAPI();
                    }
                });

        cbTarget.setItems(FXCollections.observableArrayList(targetList));
        cbTarget.getSelectionModel().selectedIndexProperty()
                .addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue ov, Number value, Number new_value) {
                        filterValue = targetList.get(new_value.intValue());
                        lastSelectedFilter = "target";
                        onRunAPI();
                    }
                });

        cbBodyPart.setItems(FXCollections.observableArrayList(bodyPartList));
        cbBodyPart.getSelectionModel().selectedIndexProperty()
                .addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue ov, Number value, Number new_value) {
                        lastSelectedFilter = "bodyPart";
                        filterValue = bodyPartList.get(new_value.intValue());
                        onRunAPI();
                    }
                });

    }
        
    @FXML
    public void onViewDetailExercise() throws IOException{
    detailModel.name = items[itemsRowSelected].getName();
    detailModel.gifURL = items[itemsRowSelected].getGifURL();
    detailModel.bodyPart = items[itemsRowSelected].getBodyPart();
    detailModel.targetedMuscle = items[itemsRowSelected].getTarget();
    detailModel.equipment = items[itemsRowSelected].getEquipment();

        App.setRoot("ExerciseDetail");
    
    }

}
