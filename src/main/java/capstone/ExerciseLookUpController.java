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
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

/**
 * FXML Controller class
 *
 * @author jesus
 */
public class ExerciseLookUpController {

    @FXML
    private MenuItem item0;
    @FXML
    private MenuItem item1;
    @FXML
    private MenuItem item2;
    @FXML
    private MenuItem item3;
    @FXML
    private MenuItem item4;
    @FXML
    private MenuItem item5;
    @FXML
    private MenuItem item6;
    @FXML
    private MenuItem item7;
    @FXML
    private MenuItem item8;
    @FXML
    private MenuItem item9;
    @FXML
    private MenuItem item10;
    @FXML
    private MenuItem item11;
    @FXML
    private MenuItem item12;
    @FXML
    private MenuItem item13;
    @FXML
    private MenuItem item14;
    @FXML
    private MenuItem item15;
    @FXML
    private MenuItem item16;
    @FXML
    private MenuItem item17;
    @FXML
    private MenuItem item18;
    @FXML
    private MenuItem item19;

    private MenuItem[] menuItems;
    @FXML
    private ChoiceBox<String> cbEquipment;

    @FXML
    private ListView lvExercises;
    ObservableList<String> obsList = FXCollections.observableArrayList();
    ExerciseDetailModel detailModel = ExerciseDetailModel.getInstance();

    private int itemsRowSelected = -1;
    private String itemIdSelected = "-1";
    Exercise[] items;
    //List<Exercise> exerciseList = new ArrayList();

    ArrayList<String> equipmentList;
    
    ExerciseApiConnector api = new ExerciseApiConnector();

    @FXML
    public void initialize() {
        setArrayLists();
        loadChoiceBox();
        menuItems = new MenuItem[]{item0, item1, item2, item3, item4, item5, item6,
             item7, item8, item9, item10, item11, item12, item13, item14,
             item15, item16, item17, item18, item19};

        setUpMenuItemListener();
    }

    @FXML
    public void goBack() throws IOException {
        App.setRoot("UserHome");
    }

    private void setArrayLists() {

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
                        
                        //onRunAPI();
                        filterListByEquipment(equipmentList.get(new_value.intValue()));
                    }
                });

    }


    public void setUpMenuItemListener() {
        int i = 0;
        for (MenuItem item : menuItems) {
            item.setOnAction(event -> {
                runApi(item.getText());
            });
            i++;
        }

    }

    @FXML
    public void runApi(String queryVal) {
        String filterType = "target";
        queryVal = queryVal.toLowerCase();
        if (queryVal.equals("all")) {
            queryVal = "";
            filterType = "";
        }
        if (queryVal.equals("chest")) {
            queryVal = "pectorals";
        }
        if (queryVal.equals("neck")) {
            queryVal = "levator scapulae";
        }
        if (queryVal.equals("shoulders")) {
            queryVal = "delts";
        }

        //System.out.println("in the onRUNAPI method(str), queryVal="+queryVal);
        //obsList.clear();
        String result = api.getJSONFromAPI(filterType, queryVal); ///get all exercises
        items = api.parseJSON(result);

        int i = 0;
        for (Exercise item : items) {
            //System.out.println("["+i+"] "+item.getName());
            obsList.add("[" + i + "] " + item.getName());
            i++;
        }
        lvExercises.setItems(obsList);
        lvExercises.getSelectionModel().selectedIndexProperty()
                .addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue ov, Number value, Number new_value) {
                        System.out.println("index= "+new_value.intValue());
                        itemsRowSelected = new_value.intValue(); //obsList index
                        itemIdSelected = items[itemsRowSelected].getId();
                        try {
                            onViewDetailExercise();
                        } catch (IOException ex) {
                            Logger.getLogger(ExerciseLookUpController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });

    }



    @FXML
    public void filterListByEquipment(String equip) 
    {
        if (obsList.isEmpty()) {
            return;
        }

        obsList.clear();
        
        int i = 0;
        List <Exercise> newItems = new ArrayList<>();
        for (Exercise item : items) {
            //System.out.println("["+i+"] "+item.getName());
            if(item.getEquipment().equals(equip)){
                obsList.add("[" + i + "] " + item.getName());
                newItems.add(item);
                i++; 
            }      
        }

        //new items array//
        int j=0;
        items = new Exercise[newItems.size()];
        for(Exercise ex: newItems){
            items[j] = ex;
            j++;
        }
        lvExercises.setItems(obsList);
        lvExercises.getSelectionModel().selectedIndexProperty()
                .addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue ov, Number value, Number new_value) {
                        //System.out.println("index= "+new_value.intValue());
                        itemsRowSelected = new_value.intValue(); //index of trimmed obsList
                        try {
                            onViewDetailExercise();
                        } catch (IOException ex) {
                            Logger.getLogger(ExerciseLookUpController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
        
        
        
    }

    
    
    @FXML
    public void onViewDetailExercise() throws IOException {
        detailModel.name = items[itemsRowSelected].getName();
        detailModel.name =  items[itemsRowSelected].getName();
        detailModel.gifURL = items[itemsRowSelected].getGifURL();
        detailModel.bodyPart = items[itemsRowSelected].getBodyPart();
        detailModel.targetedMuscle = items[itemsRowSelected].getTarget();
        detailModel.equipment = items[itemsRowSelected].getEquipment();

        App.setRoot("ExerciseDetail");

    }

    @FXML
    public void onReset() {
        obsList.clear();
        lvExercises.setItems(obsList);
       

    }
    
    
    
    }
