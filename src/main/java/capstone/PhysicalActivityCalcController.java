/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capstone;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;

/**
 * FXML Controller class
 *
 * @author jesus
 */
public class PhysicalActivityCalcController {

    @FXML
    private ChoiceBox cbActivity;
    private DailyExerciseConnector dc;
    private ArrayList<String> activityList;

    /**
     * Initializes the controller class.
     */
    @FXML
    public void initialize() {
        activityList = new ArrayList();
        dc = new DailyExerciseConnector();
        try {
            setupActivityList();
        } catch (SQLException ex) {
            Logger.getLogger(PhysicalActivityCalcController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
                        String activity = activityList.get(num.intValue());
                        double metValue = dc.getMETValue(activity);
                        System.out.println("index = " +num.intValue()
                                            +"\n ActivityName= "+ activity
                                            +"\n MET value= "+metValue);
                    }
                });

    }

}
