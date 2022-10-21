/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capstone;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author jesus
 */
public class DailyIntakeController implements Initializable {
    
    @FXML
    private ListView listView;
    ObservableList<String> obsList = FXCollections.observableArrayList();
    FoodAPIConnector foodApi = new FoodAPIConnector();
    //DailyIntakeConnector 
    FoodLogDataConnector foodLog = new FoodLogDataConnector();
    DailyIntakeConnector intakeDC = new DailyIntakeConnector();
    ArrayList<Integer> foodLogIds = new ArrayList<>();
    ArrayList<FoodItem> itemsInListView = new ArrayList<>();
    @FXML
    private TextField tfFoodItem;
    private UserProfileModel usr = UserProfileModel.getInstance();
    private int dailyIntakeID = usr.getDailyIntakeId();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML    
    public void initialize() {
        
    }
    
    @FXML
    private void onGoBack() throws IOException {        
        App.setRoot("progressCard");
    }

    //this method will save the DailyIntake data in the database
    @FXML
    public void onSave() {
        int totalCal = 0;
        int totalCarbs = 0;
        int totalProtein = 0;
        int totalFats = 0;        
        for (FoodItem item : itemsInListView) {
            totalCal += item.getCalories();
            totalCarbs += item.getCarbs();
            totalProtein += item.getProtein();
            totalFats += item.getFats();
        }
        String strFoodLogIds = foodLogIds.get(0).toString();
        for(int i=1; i<foodLogIds.size(); i++){
        strFoodLogIds += "-"+foodLogIds.get(i).toString();
        }
        if(dailyIntakeID==0){
        intakeDC.userDailyIntake(strFoodLogIds, String.valueOf(totalCal), String.valueOf(totalCarbs), String.valueOf(totalProtein), String.valueOf(totalFats));
        dailyIntakeID = intakeDC.getLastRow("Daily_Intake_Cards");
        usr.setDailyIntakeId(dailyIntakeID);
        }else{ //update the dailyIntake row
        intakeDC.updateCaloriesTotal(dailyIntakeID, String.valueOf(totalCal));
        intakeDC.updateCarbsTotal(dailyIntakeID, String.valueOf(totalCarbs));
        intakeDC.updateFatsTotal(dailyIntakeID, String.valueOf(totalFats));
        intakeDC.updateProteinTotal(dailyIntakeID, String.valueOf(totalProtein));
        intakeDC.updateFoodLogIds(dailyIntakeID, strFoodLogIds);
        }
    }

    // this method will add the food items to the list view and to the foodLog DB
    @FXML
    public void onAddFoodItem() {
        FoodItem[] foodArray = foodApi.parseJSON(foodApi.getJSONFromAPI(tfFoodItem.getText()));
        for (FoodItem item : foodArray) {
            obsList.add(item.toString());
            itemsInListView.add(item);
            foodLog.insertNewFoodLog(item.getName(), String.valueOf(item.getCalories()), String.valueOf(item.getCarbs()), String.valueOf(item.getFats()),
                    String.valueOf(item.getProtein()), String.valueOf(item.getServingSize()));
            foodLogIds.add(foodLog.getLastRow());
        }
       
        listView.setItems(obsList);
        tfFoodItem.setText("");
        
    }
    
}
