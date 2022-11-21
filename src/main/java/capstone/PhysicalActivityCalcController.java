/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capstone;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Jesus & Omar
 */
public class PhysicalActivityCalcController {

    @FXML
    private ChoiceBox cbActivity;
    private DailyExerciseConnector dc;
    private ArrayList<String> activityList;
    
    @FXML
    private Button btnAdd;
    @FXML
    private TextField wTF, timeTF;
    @FXML
    private Text resultT;
    @FXML
    private RadioButton kgRB, lbRB;
    
    public double metValue;
    private String activity="";
    private TextInputDialog d = new TextInputDialog();
    private Alert a = new Alert(Alert.AlertType.INFORMATION);
    
    public int w;
    public double t;
    private UserProfileModel user = UserProfileModel.getInstance();
    /**
     * Initializes the controller class.
     */
    @FXML
    public void initialize() {
        
        if(user.getUserName().equals(""))
            btnAdd.setVisible(false);
        
        activityList = new ArrayList();
        dc = new DailyExerciseConnector();
        try {
            setupActivityList();
        } catch (SQLException ex) {
            Logger.getLogger(PhysicalActivityCalcController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    public void goBack() throws IOException{
        App.setRoot("Calculators");
    }

    @FXML
    public void setupActivityList() throws SQLException {
        ResultSet result = dc.getAllActivities();
        
        while (result.next()){
            String str = result.getString("Activity_Name");
            activityList.add(str);
        }

        cbActivity.setItems(FXCollections.observableArrayList(activityList));
        cbActivity.getSelectionModel().selectedIndexProperty()
                .addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> ov, Number t, Number num) {
                        activity = activityList.get(num.intValue());
                        metValue = dc.getMETValue(activity);
                        System.out.println("index = " +num.intValue()
                                            +"\n ActivityName= "+ activity
                                            +"\n MET value= "+ metValue);
                    }
                });

    }
    
    @FXML
    public void getResults(){
        makeSureNotEmpty();
        w = Integer.parseInt(wTF.getText());
        t = Double.parseDouble(timeTF.getText());
        // Formula is (Min - (MET x 3.5 x Weight) ) / 200
        checkForZero(w, t);
        // Shouldn't be null or 0
        if(kgRB.isSelected()){
            // Do formula
            Double res = (t * (metValue * 3.5 * w) / 200);
            Long result = Math.round(res);
            System.out.println(result);
            resultT.setText(result.toString() + " Calories");
        }
        else if(lbRB.isSelected()){
            // Convert
            double cW = w / 2.205;
            Double res = (t * (metValue * 3.5 * cW)/ 200);
            Long result = Math.round(res);
            System.out.println(result);
            resultT.setText(result.toString() + " Calories");
            
        }
    }
    
    public void makeSureNotEmpty(){
        if(wTF.getText().isEmpty()){
            d.setTitle("Physical Activity Calculator");
            d.setHeaderText("Please add weight information");
            d.setContentText("Weight : ");
            Optional<String> res = d.showAndWait();
            res.ifPresent(weight ->{
                wTF.setText(weight);
            });
        }
        if(timeTF.getText().isEmpty()){
            d.setTitle("Physical Activity Calculator");
            d.setHeaderText("Please add amount of minutes done");
            d.setContentText("Minutes : ");
            Optional<String> res = d.showAndWait();
            res.ifPresent(minutes -> {
                timeTF.setText(minutes);
            });
        }
    }
    public void checkForZero(int x, double y){
        if(x == 0){
            d.setTitle("Physical Activity Calculator");
            d.setHeaderText("Please enter weight, cant be 0");
            d.setContentText("Weight : ");
            Optional<String> res = d.showAndWait();
            res.ifPresent(weight -> {
                w = Integer.parseInt(weight);
            });
        }
        if(y == 0 || y == 0.0){
            d.setTitle("Physical Activity Calculator");
            d.setHeaderText("Please enter time, cant be 0");
            d.setContentText("Time : ");
            Optional<String> res = d.showAndWait();
            res.ifPresent(time -> {
                t = Double.parseDouble(time);
            });
        }
    }
    @FXML
    private void reset(){
        wTF.setText("");
        timeTF.setText("");
        kgRB.setSelected(false);
        lbRB.setSelected(false);
        resultT.setText("");
        
        
    }
    
    @FXML
    public void onAddToExerciseCard() throws IOException{
        
        user.caloriesBurned = resultT.getText();
        user.physicalActivity = activity;
        user.duration = timeTF.getText();
        App.setRoot("ExerciseCard");
    }
}
