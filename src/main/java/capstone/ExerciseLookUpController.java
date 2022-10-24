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
import java.util.ResourceBundle;
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
    private ChoiceBox<String> cbExercise;
    @FXML
    private ChoiceBox<String> cbBodyPart;
    @FXML
    private ChoiceBox<String> cbTarget;
    @FXML
    private ListView lvExercises;
    ObservableList<String> obsList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        loadChoiceBox();

    }

    @FXML
    public void goBack() throws IOException {
        App.setRoot("UserHome");
    }
    
    @FXML
    public void onRunAPI(){
    
        ExerciseApiConnector api = new ExerciseApiConnector();
        String result = api.getJSONFromAPI("chest");
        Exercise[] items = api.parseJSON(result);
        int i=0;
        for (Exercise item : items) {
            System.out.println("["+i+"] "+item.getName());
            obsList.add(item.getName());
            i++;
        }
       
        lvExercises.setItems(obsList);
    
    }

    @FXML
    private void loadChoiceBox() {
        ArrayList<String> bodyTypeList = new ArrayList<>(Arrays.asList("back", "cardio",
                "chest", "lower arms", "lower legs", "neck", "shoulders", "upper arms",
                "upper legs", "wasit"));
        ArrayList<String> targetList = new ArrayList<>(Arrays.asList(
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

        ArrayList<String> equipmentList = new ArrayList<>(Arrays.asList(
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
        //ArrayList<String> medicalList = new ArrayList<>(Arrays.asList("Diabetes", "Asthma", "Low Blood Pressure", "ACL tear"));

        cbExercise.setItems(FXCollections.observableArrayList(equipmentList));
        cbExercise.getSelectionModel().selectedIndexProperty()
                .addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue ov, Number value, Number new_value) {

                    }
                });

        cbTarget.setItems(FXCollections.observableArrayList(targetList));
        cbTarget.getSelectionModel().selectedIndexProperty()
                .addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue ov, Number value, Number new_value) {

                    }
                });

        cbBodyPart.setItems(FXCollections.observableArrayList(bodyTypeList));
        cbBodyPart.getSelectionModel().selectedIndexProperty()
                .addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue ov, Number value, Number new_value) {
                        //System.out.println("gender selected is = " + genderList.get(new_value.intValue()));
                        //String gender = genderList.get(new_value.intValue());
                        //instanceUser.setGender(gender);
                        //labelGender.setText(gender);
                        //genderSelected = true;
                    }
                });

    }

}
