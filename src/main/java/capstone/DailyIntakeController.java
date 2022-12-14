/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capstone;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author jesus
 */
public class DailyIntakeController {
    @FXML
    private ListView listView;
    @FXML
    private Label labelCalories;
    @FXML
    private Label labelCarbs;
   @FXML
    private Label labelFats;
    @FXML 
    private Label labelProtein;
    @FXML
    private TextField tfCal;
    @FXML
    private TextField tfCarb;
    @FXML
    private TextField tfPro;
    @FXML
    private TextField tfFat;
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfFoodItem;
    @FXML
    private RadioButton rbFoodApi;
    @FXML
    private RadioButton rbUserInput;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnAdd2;
    @FXML
    private Label labeltext;
    
    ObservableList<String> obsList = FXCollections.observableArrayList();
    FoodAPIConnector foodApi = new FoodAPIConnector();
    //DailyIntakeConnector 
    FoodLogDataConnector foodLog = new FoodLogDataConnector();
    DailyIntakeConnector intakeDC = new DailyIntakeConnector();
    ArrayList<Integer> foodLogIds = new ArrayList<>();
    ArrayList<FoodItem> itemsInListView = new ArrayList<>();
    private UserProfileModel usr = UserProfileModel.getInstance();
    private int dailyIntakeID = 0;
    private double total_Cals = 0;
    private double total_Carbs = 0;
    private double total_Fats = 0;
    private double total_Protein = 0;
    private DecimalFormat df = new DecimalFormat("0.00");
    private ProgressCardConnector pcTable = new ProgressCardConnector();
    

    /**
     * Initializes the controller class.
     */
    @FXML
    public void initialize() {
        rbFoodApi.setSelected(true);
        onUseAPIselected();
        dailyIntakeID = usr.getDailyIntakeId();
        loadItemList();
        loadTotalValues();

    }

    @FXML
    private void onGoBack() throws IOException {
        App.setRoot("progressCard");
    }

    //this method will save the DailyIntake data in the database and update the DailyIntake row
    @FXML
    public void onSave() throws IOException {
        double totalCal = 0.0;
        double totalCarbs = 0.0;
        double totalProtein = 0.0;
        double totalFats = 0.0;
        for (FoodItem item : itemsInListView) {
            totalCal += item.getCalories();
            totalCarbs += item.getCarbs();
            totalProtein += item.getProtein();
            totalFats += item.getFats();
        }
        
        String strFoodLogIds="";
        if(foodLogIds.size()>0){
            strFoodLogIds = foodLogIds.get(0).toString();
            for (int i = 1; i < foodLogIds.size(); i++) {
            strFoodLogIds += "-" + foodLogIds.get(i).toString();
            }
        }
        total_Cals = totalCal;
        total_Carbs = totalCarbs;
        total_Fats = totalFats;
        total_Protein = totalProtein;
        loadTotalValues();
        
        if (dailyIntakeID == 0) {
            intakeDC.userDailyIntake(strFoodLogIds, String.valueOf(df.format(totalCal)), String.valueOf(df.format(totalCarbs)),
                    String.valueOf(df.format(totalProtein)), String.valueOf(df.format(totalFats)));
            dailyIntakeID = intakeDC.getLastRow("Daily_Intake_Cards");
            usr.setDailyIntakeId(dailyIntakeID);
        } else { //update the dailyIntake row
            intakeDC.updateCaloriesTotal(dailyIntakeID, String.valueOf(df.format(totalCal)));
            intakeDC.updateCarbsTotal(dailyIntakeID, String.valueOf(df.format(totalCarbs)));
            intakeDC.updateFatsTotal(dailyIntakeID, String.valueOf(df.format(totalFats)));
            intakeDC.updateProteinTotal(dailyIntakeID, String.valueOf(df.format(totalProtein)));
            intakeDC.updateFoodLogIds(dailyIntakeID, strFoodLogIds);
        }
        
        String msg ="";
        if (usr.getProgressCardId() == 0 ||usr.getProgressCardId()==-1) {
            msg = msg + "WARNING!: Need to Create and Save a Progress Card,"
                    + "\nOtherwise this DailyIntakeCard will be lost and,"
                    + "\nNot be saved to the Profile when User logs out.";
            makeAlert(msg);
            onGoBack();
        }else{ 
            msg = "This DailyIntakeCard Saved!! \n";
            pcTable.updateDailyIntakeID(usr.getProgressCardId(), String.valueOf(dailyIntakeID));
               
            makeAlert(msg);
            getCalorieTarget();
        }  
      
       
    }

    // this method will add the food items to the list view and to the foodLog DB
    //, but does not update the dailyItake row, that's the job of the save btn
    @FXML
    public void onAddFoodItem() {
        FoodItem[] foodArray = foodApi.parseJSON(foodApi.getJSONFromAPI(tfFoodItem.getText()));
        for (FoodItem item : foodArray) {
            obsList.add("["+obsList.size()+"] "+item.toString());
            itemsInListView.add(item);
            foodLog.insertNewFoodLog(item.getName(), String.valueOf(item.getCalories()), String.valueOf(item.getCarbs()), String.valueOf(item.getFats()),
                    String.valueOf(item.getProtein()), String.valueOf(item.getServingSize()));
            foodLogIds.add(foodLog.getLastRow());
        }

        listView.setItems(obsList);
        tfFoodItem.setText("");

    }
    @FXML
    public void loadItemList() {
        System.out.println("intake id = " + usr.getDailyIntakeId());
        String foodIds = intakeDC.getFoodLogIds(usr.getDailyIntakeId());
        System.out.println("fodlogid= " + foodIds);
        if (!foodIds.equals("")) {
            String[] idArray = foodIds.split("-");
            for (int i = 0; i < idArray.length; i++) {
                FoodItem item = foodLog.getFoodLogRow(Integer.valueOf(idArray[i]));
                if (item != null) {
                    obsList.add("["+i+"] "+item.toString());
                    itemsInListView.add(item);
                    foodLogIds.add(Integer.valueOf(idArray[i]));
                    total_Cals += item.getCalories();
                    total_Carbs += item.getCarbs();
                    total_Fats += item.getFats();
                    total_Protein += item.getProtein();
                }

            }
            listView.setItems(obsList);
        }

    }
    @FXML
    public void loadTotalValues() {
        DecimalFormat df = new DecimalFormat("0.00");
        labelCalories.setText(String.valueOf(df.format(total_Cals)));
        labelCarbs.setText(String.valueOf(df.format(total_Carbs)));
        labelFats.setText(String.valueOf(df.format(total_Fats)));
        labelProtein.setText(String.valueOf(df.format(total_Protein)));
    }
    @FXML
    public void onAddManualInput(){
        FoodItem item = new FoodItem();
        String calories = tfCal.getText().equals("") ? "0.0" : tfCal.getText();
        tfCal.setText(calories);
        String carbs = tfCarb.getText().equals("") ? "0.0" : tfCarb.getText();
        tfCarb.setText(carbs);
        String fats = tfFat.getText().equals("") ? "0.0" : tfFat.getText();
        tfFat.setText(fats);
        String protein = tfPro.getText().equals("") ? "0.0" : tfPro.getText();
        tfPro.setText(protein);
        
        //Sting name = tfName.
        item.setCalories(Double.parseDouble(calories));
        item.setCarbs(Double.parseDouble(carbs));
        item.setFats(Double.parseDouble(fats));
        item.setProtein(Double.parseDouble(protein));
        item.setName(tfName.getText());
            obsList.add("["+obsList.size()+"] "+item.toString());
            itemsInListView.add(item);
            foodLog.insertNewFoodLog(item.getName(), String.valueOf(item.getCalories()), String.valueOf(item.getCarbs()), String.valueOf(item.getFats()),
                    String.valueOf(item.getProtein()), String.valueOf(item.getServingSize()));
            foodLogIds.add(foodLog.getLastRow());
            listView.setItems(obsList);
            tfCal.setText("");
            tfCarb.setText("");
            tfPro.setText("");
            tfFat.setText("");
            tfName.setText("");
            
    }
    @FXML
    public void onUseAPIselected(){
    rbUserInput.setSelected(false);
    btnAdd.setVisible(true);
    tfFoodItem.setVisible(true);
    labeltext.setVisible(true);
    
    tfCal.setVisible(false);
    tfCarb.setVisible(false);
    tfPro.setVisible(false);
    tfFat.setVisible(false);
    tfName.setVisible(false);
    btnAdd2.setVisible(false);
    
    
    }
    @FXML
    public void onUserInputselected(){
    rbFoodApi.setSelected(false);
    btnAdd.setVisible(false);
    tfFoodItem.setVisible(false);
    labeltext.setVisible(false);
    
    tfCal.setVisible(true);
    tfCarb.setVisible(true);
    tfPro.setVisible(true);
    tfFat.setVisible(true);
    tfName.setVisible(true);
    btnAdd2.setVisible(true);
    
    }
    @FXML
    public void onClear(){
        //need to clear view without saving..
        itemsInListView = new ArrayList<>();
        foodLogIds = new ArrayList<>();
        obsList.clear();
        listView.setItems(obsList);
        total_Cals = 0.0;
        total_Carbs = 0.0;
        total_Fats = 0.0;
        total_Protein = 0.0;
        loadTotalValues();
            
    }
   
    public void makeAlert(String alertText) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Daily Exercise Card");
        a.setHeaderText(alertText);
        a.show();

    }
    
    
   private void getCalorieTarget() {
     
        String gender = usr.getGender();
        if(gender==null || gender.equals("")){
             makeAlert("Please update Gender in Profile to display Calorie Target");
            return;
        }
        String height = usr.getHeightInCM();
        if(height.equals("")){
            makeAlert("Please update Height in Profile to display Calorie Target");
            return;
        }
        //conver height to cm
        int age = usr.getAge();   //could be -1, if not found in profile
        if(age ==-1){
            makeAlert("Please update Date of Birth in Profile to display Calorie Target");
            return;
        }
        
        String a = String.valueOf(age);
        String weight = pcTable.getLastWeight(usr.getUserName());
        if(weight.equals("")){
            makeAlert("Please update Progress Card with Weight to display Calorie Target");
            return;
        }
        
        double kgW = ((Double.valueOf(weight))/2.2) ;
        double result = 0;
        if(gender.equals("Male")){ 
        result = (10* kgW) + (6.25*Double.valueOf(height)) - (5* Double.valueOf(a)) + 5;
        }
        if(gender.equals("Female")){ 
        result = (10* kgW) + (6.25*Double.valueOf(height)) - (5* Double.valueOf(a)) -161;
        }
        
        result = Math.round(result) + 300;
        makeAlert("**Reminder Your Daily Calorie Target is  "+result + " calories!");
        
    }
    
    
}
