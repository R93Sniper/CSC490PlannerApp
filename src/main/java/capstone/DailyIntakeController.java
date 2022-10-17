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
    ObservableList<String> obsList = FXCollections.observableArrayList ();
    FoodAPIConnector foodApi = new FoodAPIConnector();
    
    @FXML
    private TextField tfFoodItem;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    @FXML 
    public void initialize(){

    }
    
    @FXML
    private void onGoBack() throws IOException {  
        App.setRoot("progressCard");
    }
    @FXML
    public void onSave(){

        
    }
    @FXML
    public void onAddFoodItem(){
        FoodItem[] foodArray = foodApi.parseJSON(foodApi.getJSONFromAPI(tfFoodItem.getText()));
        for (FoodItem foodArray1 : foodArray) {
            obsList.add(foodArray1.toString());
        }
        //obsList.add(tfFoodItem.getText());
        listView.setItems(obsList);
      
    
    }
    
     
    
}
