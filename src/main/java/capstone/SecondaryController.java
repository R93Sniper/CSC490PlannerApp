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
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.Initializable;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;

/**
 *
 * @author Omar Muy Wahab Quazi
 */
public class SecondaryController implements Initializable {

    @FXML
    private AnchorPane PACA, bodyfatA, BMIA, waterA, calorieA, calorieKG, calorieLB, BMILB, BMIKG, BFKGA;

    @FXML
    private TextField cageTF1, cweightTF1, cheightTF1, cageTF2, cweightTF2, cheightFEET, cheightIN;

    @FXML
    private TextField waterTF; // For water

    @FXML
    private Button btnAdd; //PAC

    @FXML
    private TextField KGageTF, KGheightTF, KGweightTF, LBageTF, LBFEET, LBINCH, LBweightBMI; // For BMI Calculations
    @FXML
    private TextField BFAGETF, BFWEIGHTTF, BFHEIGHTTF, BFinchesTF, BFNECKTF, BFWAISTTF, BFHIPSTF;
    @FXML
    private TextField pacaWTF, pacaMINTF;
    @FXML
    private Label usL1, usL2, femaleHL; // Labels for USBodyFAT
    @FXML
    private Text pacaRT;
    @FXML
    private ChoiceBox Wcb1, cbActivity; // Choice box for water & PAC
    private DailyExerciseConnector dc;
    private ArrayList<String> activityList;

    @FXML
    private RadioButton Wlbrb, Wkgrb, pacakgRB, pacalbRB; // Water radio buttons & PAC
    @FXML
    private RadioButton BFMALERB, BFFEMALERB; // Radio buttons for BodyFat

    @FXML
    private ToggleButton CKGB, CLB, calKGM, calKGFM, cMALELB, cFEMALELB; //Calorie Pane
    @FXML
    private ToggleButton bmiFEMALE, bmiMALE, bmiMALE2, bmiFEMALE2; // BMI ToggleBs
    @FXML
    private ToggleButton BFmetric, BFus, BFMALETB, BFFEMALETB; // BodyFat ToggleButtons

    public double metValue;
    private String activity = "";
    private Text resultT;

    private TextInputDialog d = new TextInputDialog();
    private Alert a = new Alert(Alert.AlertType.INFORMATION);
    private int ex1 = 400; // For calorie
    private int ex2 = 800; // For calorie

    private int oz = 12; // Water Calculations.... each 30 mins...
    private int weight; // For Water Calc usage...
    private String[] Wactivity = {"30 minutes", "45 minutes", "1 hour", "1 1/2 hours", "2 hours"};
    private UserProfileModel user = UserProfileModel.getInstance();

    private int pacaW;
    private double pacaT;

    @FXML
    private void goBack() throws IOException {
        UserProfileModel usr = UserProfileModel.getInstance();
        if (!usr.getUserName().equals("")) {
            App.setRoot("UserHome");
        } else {
            App.setRoot("Landing");
        }
    }

    @FXML
    private void switchToCalorie() throws IOException {
        // App.setRoot("calorieCalc");
        hideALL();
        calorieA.setVisible(true);
        calorieA.setDisable(false);
    }

    @FXML
    private void switchToWater() throws IOException {
        //App.setRoot("waterCalc");
        //Iterate over it's nodes... 
        hideALL();
        waterA.setDisable(false);
        waterA.setVisible(true);
    }

    @FXML
    private void switchToBMI() throws IOException {
        // App.setRoot("bmiCalc");
        hideALL();
        BMIA.setVisible(true);
        BMIA.setDisable(false);

    }

    @FXML
    private void switchToBodyFat() throws IOException {
        //App.setRoot("bodyfatCalc");
        hideALL();
        bodyfatA.setVisible(true);
        bodyfatA.setDisable(false);

    }

    @FXML
    private void switchToPhysicalActivty() throws IOException {
        //App.setRoot("physicalActivityCalc");
        hideALL();
        PACA.setVisible(true);
        PACA.setDisable(false);

    }

    private void hideALL() {
        calorieA.setVisible(false);
        calorieA.setDisable(true);
        resetCal();
        waterA.setVisible(false);
        waterA.setDisable(true);
        resetWater();
        BMIA.setVisible(false);
        BMIA.setDisable(true);
        resetBMI();
        bodyfatA.setVisible(false);
        bodyfatA.setDisable(true);
        resetBF();
        PACA.setVisible(false);
        PACA.setDisable(true);
        resetPAC();
    }

    private void hideSmallAnchor() {
        CKGB.setSelected(false);
        CLB.setSelected(false);
        calorieKG.setVisible(false);
        calorieKG.setDisable(true);
        resetKGCalc();
        calorieLB.setVisible(false);
        calorieLB.setDisable(true);
        resetLBCalc();

        BMIKG.setVisible(false);
        BMIKG.setDisable(true);
        resetBMIKG();
        BMILB.setVisible(false);
        BMILB.setDisable(true);
        resetBMILB();

        BFKGA.setVisible(false);
        BFKGA.setDisable(true);
        resetBodyFatKG();

    }

    private void resetCal() {
        for (Node node : calorieA.getChildren()) {
            if (node instanceof TextField) {
                ((TextField) node).setText("");
            }
            if (node instanceof ToggleButton) {
                ((ToggleButton) node).setSelected(false);
            }

        }
    }

    private void resetWater() {
        for (Node node : waterA.getChildren()) {
            if (node instanceof TextField) {
                ((TextField) node).setText("");
            }
            if (node instanceof ToggleButton) {
                ((ToggleButton) node).setSelected(false);
            }
            if (node instanceof RadioButton) {
                ((RadioButton) node).setSelected(false);
            }
            if (node instanceof ChoiceBox) {
                ((ChoiceBox) node).valueProperty().set(null); // Clears combo value but not list
            }
        }
    }

    private void resetBMI() {
        for (Node node : BMIA.getChildren()) {
            if (node instanceof TextField) {
                ((TextField) node).setText("");
            }
            if (node instanceof ToggleButton) {
                ((ToggleButton) node).setSelected(false);
            }
        }
    }

    private void resetBF() {
        for (Node node : bodyfatA.getChildren()) {
            if (node instanceof TextField) {
                ((TextField) node).setText("");
            }
            if (node instanceof ToggleButton) {
                ((ToggleButton) node).setSelected(false);
            }
        }
    }

    private void resetPAC() {
        for (Node node : PACA.getChildren()) {
            if (node instanceof TextField) {
                ((TextField) node).setText("");
            }
            if (node instanceof ToggleButton) {
                ((ToggleButton) node).setSelected(false);
            }
            if(node instanceof RadioButton){
                ((RadioButton) node).setSelected(false);
            }
            if (node instanceof ChoiceBox) {
                ((ChoiceBox) node).valueProperty().set(null); // Clears combo value but not list
            }
        }
    }

    private void resetKGCalc() {
        for (Node node : calorieKG.getChildren()) {
            if (node instanceof TextField) {
                ((TextField) node).setText("");
            }
            if (node instanceof ToggleButton) {
                ((ToggleButton) node).setSelected(false);
            }
        }
    }

    private void resetLBCalc() {
        for (Node node : calorieLB.getChildren()) {
            if (node instanceof TextField) {
                ((TextField) node).setText("");
            }
            if (node instanceof ToggleButton) {
                ((ToggleButton) node).setSelected(false);
            }
        }
    }

    @FXML
    private void calorieKGCalc() {
        // if KG button is selected..
        hideSmallAnchor();
        calorieKG.setDisable(false);
        calorieKG.setVisible(true);
        // Turn on calorie kg formats
    }

    @FXML
    private void calorieLBCalc() {
        //If LB button is selected/toggled...
        hideSmallAnchor();
        calorieLB.setDisable(false);
        calorieLB.setVisible(true);
        //Turn on anchorpane for calorie
    }

    @FXML
    private void calculateCalKG() {
        a.setTitle("490 Fitness Planner");
        d.setTitle("490 Fitness Planner");
        int age;
        int kg;
        int cm;
        // If male selected then do...
        if (calKGM.isSelected()) {
            if (!cageTF1.getText().isEmpty()) {
                age = Integer.parseInt(cageTF1.getText()); // Get AGE value to int

                // Done with Age TextField now to Weight field... KG_
                if (!cweightTF1.getText().isEmpty()) {
                    kg = Integer.parseInt(cweightTF1.getText());

                    // Done with weight, onto height
                    if (!cheightTF1.getText().isEmpty()) {
                        cm = Integer.parseInt(cheightTF1.getText());
                        // Time to calculate, age (int), kg (int), cm (int)
                        calculateCalorieMale(age, kg, cm);
                    } else if (cheightTF1.getText().isEmpty()) {
                        d.setHeaderText("Hey!, fill in your height");
                        d.setContentText("Height : ");
                        Optional<String> result = d.showAndWait();
                        if (result.isPresent()) {
                            cheightTF1.setText(result.toString());
                        }
                    }
                } else if (cweightTF1.getText().isEmpty()) {
                    d.setHeaderText("Hey!, fill in your weight");
                    d.setContentText("Weight : ");
                    Optional<String> result = d.showAndWait();
                    if (result.isPresent()) {
                        cweightTF1.setText(result.toString());
                    }
                }
            } else if (cageTF1.getText().isEmpty()) {
                d.setHeaderText("Hey!, fill in your age");
                d.setContentText("Age : ");
                Optional<String> result = d.showAndWait();
                if (result.isPresent()) {
                    cageTF1.setText(result.toString());
                }
            }

        } // else if Female is selected then...
        else if (calKGFM.isSelected()) {
            if (!cageTF1.getText().isEmpty()) {
                age = Integer.parseInt(cageTF1.getText()); // Convert
                // Done with Age TextField now to Weight field... KG_

                if (!cweightTF1.getText().isEmpty()) {
                    kg = Integer.parseInt(cweightTF1.getText()); // Convert

                    // Done with weight, onto height
                    if (!cheightTF1.getText().isEmpty()) {
                        cm = Integer.parseInt(cheightTF1.getText());
                        // Time to calculate, age (int), kg (int), cm (int)
                        calculateCalorieFemale(age, kg, cm);
                    } else if (cheightTF1.getText().isEmpty()) {
                        d.setHeaderText("Hey!, fill in your height");
                        d.setContentText("Height : ");
                        Optional<String> result = d.showAndWait();
                        if (result.isPresent()) {
                            cheightTF1.setText(result.toString());
                        }
                    }
                } else if (cweightTF1.getText().isEmpty()) {
                    d.setHeaderText("Hey!, fill in your weight");
                    d.setContentText("Weight : ");
                    Optional<String> result = d.showAndWait();
                    if (result.isPresent()) {
                        cweightTF1.setText(result.toString());
                    }
                }
            } else if (cageTF1.getText().isEmpty()) {

            }
        } else {
            // MUST SELECT A BUTTON FEMALE OR MALE
            a.setHeaderText("CAUTION");
            a.setContentText("Please choose a gender, Male or Female...");
            a.showAndWait();
        }
    }

    //FOR LB (Pounds)
    @FXML
    private void CalculateLBCalc() {
        a.setTitle("490 Fitness Planner");
        d.setTitle("490 Fitness Planner");
        int age;
        int lb;
        int feet;
        int in;
        double kg1;
        double cm1;
        int kg;
        int cm;
        if (cMALELB.isSelected()) {
            if (!cageTF2.getText().isEmpty()) {
                age = Integer.parseInt(cageTF2.getText()); // Get AGE value to int

                // Done with Age TextField now to Weight field... LBS, then convert
                if (!cweightTF2.getText().isEmpty()) {
                    lb = Integer.parseInt(cweightTF2.getText()); // This is in LB..
                    kg1 = lb / 2.205;
                    kg = (int) kg1;
                    // Done with weight, onto height
                    if (!cheightFEET.getText().isEmpty() && !cheightIN.getText().isEmpty()) {
                        feet = Integer.parseInt(cheightFEET.getText());
                        in = Integer.parseInt(cheightIN.getText());
                        double feetconv = feet * 30.48; // Convert feet to CM
                        double inconv = in * 2.54; // Convert inch to cm
                        cm1 = feetconv + inconv;
                        cm = (int) cm1;
                        // Time to calculate, age (int), kg (int), cm (int)
                        calculateCalorieMale(age, kg, cm);
                    } else if (cheightFEET.getText().isEmpty() && cheightIN.getText().isEmpty()) {
                        d.setHeaderText("Hey!, fill in your heights!");
                        d.setContentText("Height(Feet) : ");
                        Optional<String> result = d.showAndWait();
                        if (result.isPresent()) {
                            cheightFEET.setText(result.toString());
                        }
                        d.setHeaderText("Hey!, fill in your heights!");
                        d.setContentText("Height(Inches) : ");
                        Optional<String> result2 = d.showAndWait();
                        if (result2.isPresent()) {
                            cheightIN.setText(result.toString());
                        }
                    }
                } else if (cweightTF2.getText().isEmpty()) {
                    d.setHeaderText("Hey!, fill in your weight");
                    d.setContentText("Weight : ");
                    Optional<String> result = d.showAndWait();
                    if (result.isPresent()) {
                        cweightTF2.setText(result.toString());
                    }
                }
            } else if (cageTF2.getText().isEmpty()) {
                d.setHeaderText("Hey!, fill in your age");
                d.setContentText("Age : ");
                Optional<String> result = d.showAndWait();
                if (result.isPresent()) {
                    cageTF2.setText(result.toString());
                }
            }
        } else if (cFEMALELB.isSelected()) {
            if (!cageTF2.getText().isEmpty()) {
                age = Integer.parseInt(cageTF2.getText()); // Get AGE value to int

                // Done with Age TextField now to Weight field... LBS, then convert
                if (!cweightTF2.getText().isEmpty()) {
                    lb = Integer.parseInt(cweightTF2.getText()); // This is in LB..
                    kg1 = lb * 2.205;
                    kg = (int) kg1;
                    // Done with weight, onto height
                    if (!cheightFEET.getText().isEmpty() && !cheightIN.getText().isEmpty()) {
                        feet = Integer.parseInt(cheightFEET.getText());
                        in = Integer.parseInt(cheightIN.getText());
                        double feetconv = feet * 30.48; // Convert feet to CM
                        double inconv = in * 2.54; // Convert inch to cm
                        cm1 = feetconv + inconv;
                        cm = (int) cm1;
                        // Time to calculate, age (int), kg (int), cm (int)
                        calculateCalorieMale(age, kg, cm);
                    } else if (cheightFEET.getText().isEmpty() && cheightIN.getText().isEmpty()) {
                        d.setHeaderText("Hey!, fill in your heights!");
                        d.setContentText("Height(Feet) : ");
                        Optional<String> result = d.showAndWait();
                        if (result.isPresent()) {
                            cheightFEET.setText(result.toString());
                        }
                        d.setHeaderText("Hey!, fill in your heights!");
                        d.setContentText("Height(Inches) : ");
                        Optional<String> result2 = d.showAndWait();
                        if (result2.isPresent()) {
                            cheightIN.setText(result.toString());
                        }
                    }
                } else if (cweightTF2.getText().isEmpty()) {
                    d.setHeaderText("Hey!, fill in your weight");
                    d.setContentText("Weight : ");
                    Optional<String> result = d.showAndWait();
                    if (result.isPresent()) {
                        cweightTF2.setText(result.toString());
                    }
                }
            } else if (cageTF2.getText().isEmpty()) {
                d.setHeaderText("Hey!, fill in your age");
                d.setContentText("Age : ");
                Optional<String> result = d.showAndWait();
                if (result.isPresent()) {
                    cageTF2.setText(result.toString());
                }
            }
        } else {
            // MUST SELECT A BUTTON FEMALE OR MALE
            a.setHeaderText("CAUTION");
            a.setContentText("Please choose a gender, Male or Female...");
            a.showAndWait();
        }
    }

    //Method to fine calories based on parameters given... MALE
    private void calculateCalorieMale(int age, int kg, int cm) {
        //Calculate for males using
        // Mifflin-St Jeor formula
        // BMR = 10W + 6.25H - 5A + 5
        double res = (10 * kg) + (6.25 * cm) - (5 * age) + 5; // This is BMR, add 400 to it....
        long target1 = Math.round(res);
        long target2 = Math.round(res) + ex1;
        long target3 = Math.round(res) + ex2;
        a.setTitle("490 Calorie Calculator");
        a.setHeaderText("Here are your results...");
        a.setContentText("To achieve weight loss by roughly 1 lb/ 2kg a week you must consume a total of " + target1
                + " calories/day. \n To maintain your weight you must consume a total of " + target2
                + " calories/day \n To gain muscle mass you must consume a total of " + target3 + " calories/day");
        a.showAndWait();
    }

    //Method to fine calories based on parameters given... FEMALE
    private void calculateCalorieFemale(int age, int kg, int cm) {
        // Calculate for females using Mifflin-St Jeor formula
        // BMR = 10W + 6.25H - 5A - 161
        double res = (10 * kg) + (6.25 * cm) - (5 * age) - 161; // This is BMR, add 400 to it....
        long target1 = Math.round(res);
        long target2 = Math.round(res) + ex1;
        long target3 = Math.round(res) + ex2;
        a.setTitle("490 Calorie Calculator");
        a.setHeaderText("Here are your results...");
        a.setContentText("To achieve weight loss by roughly 1 lb/ 2kg a week you must consume a total of " + target1
                + " calories/day. \n To maintain your weight you must consume a total of " + target2
                + " calories/day \n To gain muscle mass you must consume a total of " + target3 + " calories/day");
        a.showAndWait();
    }

    //Preload Choicebox functionality
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Wcb1.getItems().addAll(Wactivity); //Water 

        if (user.getUserName().equals("")) {
            btnAdd.setVisible(false);
        }
        if (!user.getUserName().equals("")) {
            loadGender();
            loadHeightData();
            loadUserAge();
        }
        activityList = new ArrayList();
        dc = new DailyExerciseConnector();
        try {
            setupActivityList();
        } catch (SQLException ex) {
            Logger.getLogger(PhysicalActivityCalcController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    @FXML
    public void loadHeightData(){
        if (user.getHeight() != null) {
            String[] height = user.getHeight().split("-");
            String ft = height[0]; // How to acclimate this..
            String in = height[1];
            //BFHEIGHTTF.setText(ft);
            BFinchesTF.setText(in);
        }
    }
    @FXML
    private void loadGender() {
        if (user.getGender() != null) {
            if (user.getGender().equals("Male")) {
                BFMALETB.setSelected(true);
                calKGM.setSelected(true);
                bmiMALE.setSelected(true);
                cMALELB.setSelected(true);
                bmiMALE2.setSelected(true);
            }
            if (user.getGender().equals("Female")) {
                BFFEMALETB.setSelected(true);
                calKGFM.setSelected(true);
                bmiFEMALE.setSelected(true);
                cFEMALELB.setSelected(true);
                bmiFEMALE2.setSelected(true);
            }
        }
    }
    @FXML
    private void loadUserAge() {
        if (user.getBirthDate() != null) {
            if (user.getAge() > -1) {
                BFAGETF.setText(String.valueOf(user.getAge()));
                KGageTF.setText(String.valueOf(user.getAge()));
                LBageTF.setText(String.valueOf(user.getAge()));
                cageTF1.setText(String.valueOf(user.getAge()));
                cageTF2.setText(String.valueOf(user.getAge()));
            } else {
                System.out.println("Birth Age is greater than Today's date so Can't calc");
            }
        }
    }
    

    @FXML
    public void setupActivityList() throws SQLException {
        ResultSet result = dc.getAllActivities();

        while (result.next()) {
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
                        System.out.println("index = " + num.intValue()
                                + "\n ActivityName= " + activity
                                + "\n MET value= " + metValue);
                    }
                });

    }

    @FXML
    public void onAddToExerciseCard() throws IOException {

        user.caloriesBurned = pacaRT.getText();
        user.physicalActivity = activity;
        user.duration = pacaMINTF.getText();
        App.setRoot("ExerciseCard");
    }

    @FXML
    private void waterButtonCalc() {
        // pounds first
        a.setTitle("Water Calculator");
        a.setHeaderText("Based on your input...");
        if (!waterTF.getText().isEmpty() && Wlbrb.isSelected() && Wcb1.getValue() == null) {
            weight = Integer.parseInt(waterTF.getText());
            long l = Math.round(weight * .75); // Round so nearest oz. 
            a.setContentText("You'd get a more accurate measurement if you include activity time\n But you roughly need around " + l + " oz. daily.\n");
            a.showAndWait();
        } else if (!waterTF.getText().isEmpty() && Wlbrb.isSelected() && Wcb1.getValue() != null) {
            if (Wcb1.getValue() == "30 minutes") {
                // Each 30 min add 12 oz.
                weight = Integer.parseInt(waterTF.getText()); // Convert to int
                long l = Math.round(weight * .75) + oz;
                a.setContentText("You'd roughly need around " + l + " oz. daily for 30 min of physical activity.\n");
                a.showAndWait();
            } else if (Wcb1.getValue() == "45 minutes") {
                weight = Integer.parseInt(waterTF.getText()); // Convert to int
                long l = Math.round(weight * .75 + oz * 1.5);
                a.setContentText("You'd roughly need around " + l + " oz. daily for 45 min of physical activity.\n");
                a.showAndWait();
            } else if (Wcb1.getValue() == "1 hour") {
                weight = Integer.parseInt(waterTF.getText()); // Convert to int
                long l = Math.round(weight * .75 + oz * 2);
                a.setContentText("You'd roughly need around " + l + " oz. daily for 60 min of physical activity.\n");
                a.showAndWait();
            } else if (Wcb1.getValue() == "1 1/2 hours") {
                weight = Integer.parseInt(waterTF.getText()); // Convert to int
                long l = Math.round(weight * .75 + oz * 3);
                a.setContentText("You'd roughly need around " + l + " oz. daily for 90 min of physical activity.\n");
                a.showAndWait();
            } else if (Wcb1.getValue() == "2 hours") {
                weight = Integer.parseInt(waterTF.getText()); // Convert to int
                long l = Math.round(weight * .75 + oz * 4);
                a.setContentText("You'd roughly need around " + l + " oz. daily for 120 min of physical activity.\n");
                a.showAndWait();
            }
        } else if (waterTF.getText().isEmpty()) {
            a.setHeaderText("CAUTION");
            a.setContentText("Please enter your weight...");
            a.showAndWait();
            d.setTitle("Water Calc");
            d.setHeaderText("Please enter your weight");
            d.setContentText("Weight: ");
            Optional<String> result = d.showAndWait();
            if (result.isPresent()) {
                waterTF.setText(result.toString());
            }
        } else if (!Wlbrb.isSelected() && !Wkgrb.isSelected()) {
            a.setHeaderText("CAUTION");
            a.setContentText("Please choose a unit of measure for weight...");
            a.showAndWait();
        } else if (!waterTF.getText().isEmpty() && Wkgrb.isSelected() && Wcb1.getValue() == null) {
            weight = Integer.parseInt(waterTF.getText());
            long l = Math.round(weight * 2.2); // convert to lbs
            long m = Math.round(l * .75); // now to OZ.

            a.setHeaderText("Water calculation results...");
            a.setContentText("You need about " + m + "oz. daily\n");
            a.showAndWait();
        } else if (!waterTF.getText().isEmpty() && Wkgrb.isSelected() && Wcb1.getValue() != null) {
            if (Wcb1.getValue() == "30 minutes") {
                weight = Integer.parseInt(waterTF.getText()); // This has the # in KG
                long l = Math.round(weight * 2.2); // converts to LBS
                long m = Math.round(l * .75) + oz; // Converts to OZ. 
                a.setHeaderText("Water calculation results...");
                a.setContentText("You need about " + m + "oz. daily\n");
                a.showAndWait();
            } else if (Wcb1.getValue() == "45 minutes") {
                weight = Integer.parseInt(waterTF.getText()); // This has the # in KG
                long l = Math.round(weight * 2.2); // converts to LBS
                long m = Math.round(l * .75 + oz * 1.5); // Converts to OZ. 
                a.setHeaderText("Water calculation results...");
                a.setContentText("You need about " + m + "oz. daily\n");
                a.showAndWait();
            } else if (Wcb1.getValue() == "1 hour") {
                weight = Integer.parseInt(waterTF.getText()); // This has the # in KG
                long l = Math.round(weight * 2.2); // converts to LBS
                long m = Math.round(l * .75) + oz * 2; // Converts to OZ.
                a.setHeaderText("Water calculation results...");
                a.setContentText("You need about " + m + "oz. daily\n");
                a.showAndWait();
            } else if (Wcb1.getValue() == "1 1/2 hours") {
                weight = Integer.parseInt(waterTF.getText()); // This has the # in KG
                long l = Math.round(weight * 2.2); // converts to LBS
                long m = Math.round(l * .75) + oz * 3; // Converts to OZ. 
                a.setHeaderText("Water calculation results...");
                a.setContentText("You need about " + m + "oz. daily\n");
                a.showAndWait();
            } else if (Wcb1.getValue() == "2 hours") {
                weight = Integer.parseInt(waterTF.getText()); // This has the # in KG
                long l = Math.round(weight * 2.2); // converts to LBS
                long m = Math.round(l * .75) + oz * 4; // Converts to OZ. 
                a.setHeaderText("Water calculation results...");
                a.setContentText("You need about " + m + "oz. daily\n");
                a.showAndWait();
            }
        }

    }

    @FXML
    private void openBMILB() {
        // Turn on BMI lb anchorpane
        hideSmallAnchor();
        BMILB.setDisable(false);
        BMILB.setVisible(true);
    }

    @FXML
    private void openBMIKG() {
        // Turn on BMI KG Anchorpane
        hideSmallAnchor();
        BMIKG.setDisable(false);
        BMIKG.setVisible(true);
    }

    private void resetBMIKG() {
        for (Node node : BMIKG.getChildren()) {
            if (node instanceof TextField) {
                ((TextField) node).setText("");
            }
            if (node instanceof ToggleButton) {
                ((ToggleButton) node).setSelected(false);
            }
        }
    }

    private void resetBMILB() {
        for (Node node : BMILB.getChildren()) {
            if (node instanceof TextField) {
                ((TextField) node).setText("");
            }
            if (node instanceof ToggleButton) {
                ((ToggleButton) node).setSelected(false);
            }
        }
    }

    @FXML
    private void calculateBMILB() {
        if (bmiMALE2.isSelected() || bmiFEMALE2.isSelected()) {
            doBMILB();
        } else {
            a.setHeaderText("ERROR");
            a.setContentText("PLEASE CHOOSE A GENDER");
        }
    }

    @FXML
    private void calculateBMIKG() {
        if (bmiMALE.isSelected() || bmiFEMALE.isSelected()) {
            doBMIKG();
        } else {
            a.setHeaderText("ERROR");
            a.setContentText("PLEASE CHOOSE A GENDER");
        }
    }

    private void doBMILB() {
        int age;
        double height;
        int weight;

        if (!LBageTF.getText().isEmpty() && !LBFEET.getText().isEmpty() && !LBINCH.getText().isEmpty() && !LBweightBMI.getText().isEmpty()) {
            // normal age, feet and inch need to be into CM
            age = Integer.parseInt(LBageTF.getText());
            int dummyfeet = Integer.parseInt(LBFEET.getText());
            double convFeet = dummyfeet * 30.48;
            int dummyinch = Integer.parseInt(LBINCH.getText());
            double convInch = dummyinch * 2.54;
            height = (int) convFeet + (int) convInch;
            int dummyWeight = Integer.parseInt(LBweightBMI.getText());
            double convWeight = dummyWeight / 2.205; // LB TO KG
            weight = (int) convWeight;

        } else if (LBageTF.getText().isEmpty()) {
            d.setTitle("BMI Calc");
            d.setHeaderText("Please enter your age");
            d.setContentText("Age: ");
            Optional<String> result = d.showAndWait();
            if (result.isPresent()) {
                LBageTF.setText(result.toString());
            }
        } else if (LBFEET.getText().isEmpty()) {
            d.setTitle("BMI Calc");
            d.setHeaderText("Please enter your height (feet)");
            d.setContentText("Feet: ");
            Optional<String> result = d.showAndWait();
            if (result.isPresent()) {
                LBFEET.setText(result.toString());
            }
        } else if (LBINCH.getText().isEmpty()) {
            d.setTitle("BMI Calc");
            d.setHeaderText("Please enter your height (inch)");
            d.setContentText("Inches: ");
            Optional<String> result = d.showAndWait();
            if (result.isPresent()) {
                LBINCH.setText(result.toString());
            }
        } else if (LBweightBMI.getText().isEmpty()) {
            d.setTitle("BMI Calc");
            d.setHeaderText("Please enter your weight");
            d.setContentText("Weight: ");
            Optional<String> result = d.showAndWait();
            if (result.isPresent()) {
                LBweightBMI.setText(result.toString());
            }
        }
    }

    private void doBMIKG() {
        int age;
        int height;
        int weight;

        if (!KGageTF.getText().isEmpty() && !KGheightTF.getText().isEmpty() && !KGweightTF.getText().isEmpty()) {
            age = Integer.parseInt(KGageTF.getText());
            height = Integer.parseInt(KGheightTF.getText());
            weight = Integer.parseInt(KGweightTF.getText());

            convertToBMI(age, height, weight);

        } else if (KGageTF.getText().isEmpty()) {
            d.setTitle("BMI Calc");
            d.setHeaderText("Please enter your age");
            d.setContentText("Age: ");
            Optional<String> result = d.showAndWait();
            if (result.isPresent()) {
                KGageTF.setText(result.toString());
            }
        } else if (KGheightTF.getText().isEmpty()) {
            d.setTitle("BMI Calc");
            d.setHeaderText("Please enter your height");
            d.setContentText("Height: ");
            Optional<String> result = d.showAndWait();
            if (result.isPresent()) {
                KGheightTF.setText(result.toString());
            }
        } else if (KGweightTF.getText().isEmpty()) {
            d.setTitle("BMI Calc");
            d.setHeaderText("Please enter your weight");
            d.setContentText("Weight: ");
            Optional<String> result = d.showAndWait();
            if (result.isPresent()) {
                KGweightTF.setText(result.toString());
            }
        }

    }

    private void convertToBMI(int age, double height, int weight) {
        //Assuming height is in CM and weight in KG we calculate
        double meter = height / 100; // cm to meter conversion
        double metersq = meter * meter;
        // Now to put the formula together
        double formula = weight / metersq;
        if (age >= 20) {
            bmiOutput(formula);
        } else if (age <= 20) {
            a.setHeaderText("Caution");
            a.setContentText("Check W.H.O. for a more accurate bmi reading for your age group");
            a.showAndWait();
        }
    }

    private void bmiOutput(double formula) {
        int result = (int) formula;
        if (formula < 16) {
            // This is severe thinness
            a.setHeaderText("CAUTION: SEVERE THINNESS");
            a.setContentText("Your BMI is " + result + ", this BMI is categorizing you as severely thin, consulte with your nutritionist on a proper diet ");
            a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            a.getDialogPane().setMinWidth(Region.USE_PREF_SIZE);
            a.showAndWait();
        } else if (formula > 16 && formula < 18.5) {
            // thin
            a.setHeaderText("CAUTION: THIN");
            a.setContentText("Your BMI is " + result + ", this BMI is categorizing you as thin based on W.H.O. standards");
            a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            a.getDialogPane().setMinWidth(Region.USE_PREF_SIZE);
            a.showAndWait();
        } else if (formula > 18.5 && formula < 25) {
            // normal
            a.setHeaderText("CAUTION: NORMAL");
            a.setContentText("Your BMI is " + result + ", this BMI is categorizing you as average/normal.");
            a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            a.getDialogPane().setMinWidth(Region.USE_PREF_SIZE);
            a.showAndWait();

        } else if (formula > 25 && formula < 30) {
            // overweight
            a.setHeaderText("CAUTION: OVERWEIGHT");
            a.setContentText("Your BMI is " + result + ", this BMI is categorizing you as overweight, be careful of your diet and consider cutting back on some things to maintain a healthy weight.");
            a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            a.getDialogPane().setMinWidth(Region.USE_PREF_SIZE);
            a.showAndWait();
        } else if (formula > 30) {
            // Obese
            a.setHeaderText("CAUTION: OBESE");
            a.setContentText("Your BMI is " + result + ", this BMI is categorizing you as being obese, consult with your physician or nutritionist for recommendations on maintaining a healthy lifestyle");
            a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            a.getDialogPane().setMinWidth(Region.USE_PREF_SIZE);
            a.showAndWait();
        }
    }

    @FXML
    private void openBodyFatKG() {
        hideSmallAnchor();
        onlyKGFormat();
    }

    @FXML
    private void openBodyFatLB() {
        hideSmallAnchor();
        onlyLBFormat();
    }

    private void onlyKGFormat() {
        BFus.setSelected(false);
        BFMALETB.setSelected(false);
        BFFEMALETB.setSelected(false);

        BFKGA.setVisible(true);
        BFKGA.setDisable(false);
        BFinchesTF.setDisable(true);
        BFinchesTF.setVisible(false);
        usL1.setVisible(false);
        usL2.setVisible(false);
        femaleHL.setVisible(false);
        BFHIPSTF.setVisible(false);
        BFHIPSTF.setDisable(true);
        BFHEIGHTTF.setPromptText("Enter height (cm)");
        BFWEIGHTTF.setPromptText("Enter weight (kg)");
        BFNECKTF.setPromptText("Enter neck (cm)");
        BFWAISTTF.setPromptText("Enter waist (cm)");

    }

    private void onlyLBFormat() {
        BFmetric.setSelected(false);
        BFMALETB.setSelected(false);
        BFFEMALETB.setSelected(false);

        BFKGA.setVisible(true);
        BFKGA.setDisable(false);
        BFinchesTF.setDisable(false);
        BFinchesTF.setVisible(true);
        usL1.setVisible(true);
        usL2.setVisible(true);
        BFHEIGHTTF.setPromptText("Enter height (feet)");
        BFinchesTF.setPromptText("Height (in)");
        BFWEIGHTTF.setPromptText("Enter weight (lbs)");
        BFNECKTF.setPromptText("Enter neck (inches)");
        BFWAISTTF.setPromptText("Enter waist (inches)");
        femaleHL.setVisible(false);
        BFHIPSTF.setVisible(false);
        BFHIPSTF.setDisable(true);

    }

    @FXML
    private void choiceFemale() {
        BFMALETB.setSelected(false);
        femaleHL.setVisible(true);
        BFHIPSTF.setVisible(true);
        BFHIPSTF.setDisable(false);
        if (BFmetric.isSelected()) {
            BFHIPSTF.setPromptText("Enter hips (cm)");
        } else if (BFus.isSelected()) {
            BFHIPSTF.setPromptText("Enter hips (in)");
        }

    }

    @FXML
    private void choiceMale() {
        //reverse female
        BFFEMALETB.setSelected(false);
        femaleHL.setVisible(false);
        BFHIPSTF.setVisible(false);
        BFHIPSTF.setDisable(true);
    }

    private void resetBodyFatKG() {
        for (Node node : BFKGA.getChildren()) {
            if (node instanceof TextField) {
                ((TextField) node).setText("");
            }
            if (node instanceof ToggleButton) {
                ((ToggleButton) node).setSelected(false);
            }
            if (node instanceof RadioButton) {
                ((RadioButton) node).setSelected(false);
            }
        }
    }

    @FXML
    private void calculateBodyFat() {
        if (BFmetric.isSelected()) {
            if (BFMALETB.isSelected()) {
                runKGMale(); // Without US values
            } else if (BFFEMALETB.isSelected()) {
                runKGFemale();
            }
        } else if (BFus.isSelected()) {
            if (BFMALETB.isSelected()) {
                runLBMale();
            } else if (BFFEMALETB.isSelected()) {
                runLBFemale();
            }
        }
    }

    private void runKGMale() {
        //NO HIPS AND NO US MEASURES, USING KG / CM
        d.setTitle("BodyFat Calculator");
        int age;
        int w;
        int h;
        int neck;
        int waist;
        if (!BFAGETF.getText().isEmpty()) {
            if (!BFWEIGHTTF.getText().isEmpty()) {
                if (!BFHEIGHTTF.getText().isEmpty()) {
                    if (!BFNECKTF.getText().isEmpty()) {
                        if (!BFWAISTTF.getText().isEmpty()) {
                            age = Integer.parseInt(BFAGETF.getText());
                            w = Integer.parseInt(BFWEIGHTTF.getText());
                            h = Integer.parseInt(BFHEIGHTTF.getText());
                            neck = Integer.parseInt(BFNECKTF.getText());
                            waist = Integer.parseInt(BFWAISTTF.getText());

                            calculateBFMale(w, h, neck, waist);
                        } else if (BFWAISTTF.getText().isEmpty()) {
                            d.setHeaderText("ERROR, NEED A Waist SIZE");
                            d.setContentText("Waist: ");
                            Optional<String> result = d.showAndWait();
                            if (result.isPresent()) {
                                BFWAISTTF.setText(result.get());
                            }
                        }
                    } else if (BFNECKTF.getText().isEmpty()) {
                        d.setHeaderText("ERROR, NEED A NECK SIZE");
                        d.setContentText("Neck: ");
                        Optional<String> result = d.showAndWait();
                        if (result.isPresent()) {
                            BFNECKTF.setText(result.get());
                        }
                    }
                } else if (BFHEIGHTTF.getText().isEmpty()) {
                    d.setHeaderText("ERROR, NEED A HEIGHT");
                    d.setContentText("Height: ");
                    Optional<String> result = d.showAndWait();
                    if (result.isPresent()) {
                        BFHEIGHTTF.setText(result.get());
                    }
                }
            } else if (BFWEIGHTTF.getText().isEmpty()) {
                d.setHeaderText("ERROR, NEED A WEIGHT");
                d.setContentText("Weight: ");
                Optional<String> result = d.showAndWait();
                if (result.isPresent()) {
                    BFWEIGHTTF.setText(result.get());
                }
            }
        } else if (BFAGETF.getText().isEmpty()) {
            d.setHeaderText("ERROR, NEED AN AGE");
            d.setContentText("AGE: ");
            Optional<String> result = d.showAndWait();
            if (result.isPresent()) {
                BFAGETF.setText(result.get());
            }
        }
    }

    private void runKGFemale() {
        //This is including measurement of HIPSSSSS
        d.setTitle("BodyFat Calculator");
        int age;
        int w;
        int h;
        int neck;
        int waist;
        int hips;
        if (!BFAGETF.getText().isEmpty()) {
            if (!BFWEIGHTTF.getText().isEmpty()) {
                if (!BFHEIGHTTF.getText().isEmpty()) {
                    if (!BFNECKTF.getText().isEmpty()) {
                        if (!BFWAISTTF.getText().isEmpty()) {
                            if (!BFHIPSTF.getText().isEmpty()) {
                                age = Integer.parseInt(BFAGETF.getText());
                                w = Integer.parseInt(BFWEIGHTTF.getText());
                                h = Integer.parseInt(BFHEIGHTTF.getText());
                                neck = Integer.parseInt(BFNECKTF.getText());
                                waist = Integer.parseInt(BFWAISTTF.getText());
                                hips = Integer.parseInt(BFHIPSTF.getText());

                                calculateBFFemale(w, h, neck, waist, hips);

                            } else if (BFHIPSTF.getText().isEmpty()) {
                                d.setHeaderText("ERROR, NEED A HIP SIZE");
                                d.setContentText("Hips: ");
                                Optional<String> result = d.showAndWait();
                                if (result.isPresent()) {
                                    BFHIPSTF.setText(result.get());
                                }
                            }
                        } else if (BFWAISTTF.getText().isEmpty()) {
                            d.setHeaderText("ERROR, NEED A Waist SIZE");
                            d.setContentText("Waist: ");
                            Optional<String> result = d.showAndWait();
                            if (result.isPresent()) {
                                BFWAISTTF.setText(result.get());
                            }
                        }
                    } else if (BFNECKTF.getText().isEmpty()) {
                        d.setHeaderText("ERROR, NEED A NECK SIZE");
                        d.setContentText("Neck: ");
                        Optional<String> result = d.showAndWait();
                        if (result.isPresent()) {
                            BFNECKTF.setText(result.get());
                        }
                    }
                } else if (BFHEIGHTTF.getText().isEmpty()) {
                    d.setHeaderText("ERROR, NEED A HEIGHT");
                    d.setContentText("Height: ");
                    Optional<String> result = d.showAndWait();
                    if (result.isPresent()) {
                        BFHEIGHTTF.setText(result.get());
                    }
                }
            } else if (BFWEIGHTTF.getText().isEmpty()) {
                d.setHeaderText("ERROR, NEED A WEIGHT");
                d.setContentText("Weight: ");
                Optional<String> result = d.showAndWait();
                if (result.isPresent()) {
                    BFWEIGHTTF.setText(result.get());
                }
            }
        } else if (BFAGETF.getText().isEmpty()) {
            d.setHeaderText("ERROR, NEED AN AGE");
            d.setContentText("AGE: ");
            Optional<String> result = d.showAndWait();
            if (result.isPresent()) {
                BFAGETF.setText(result.get());
            }
        }
    }

    private void runLBMale() {
        d.setTitle("BodyFat Calculator");
        int age;
        int w; // in lbs
        int h; // in feet
        int in; // inches
        int neck; // inches
        int waist; // inches
        if (!BFAGETF.getText().isEmpty()) {
            if (!BFWEIGHTTF.getText().isEmpty()) {
                if (!BFHEIGHTTF.getText().isEmpty()) {
                    if (!BFNECKTF.getText().isEmpty()) {
                        if (!BFWAISTTF.getText().isEmpty()) {
                            if (!BFinchesTF.getText().isEmpty()) {
                                age = Integer.parseInt(BFAGETF.getText());
                                w = Integer.parseInt(BFWEIGHTTF.getText());
                                h = Integer.parseInt(BFHEIGHTTF.getText());
                                in = Integer.parseInt(BFinchesTF.getText());
                                neck = Integer.parseInt(BFNECKTF.getText());
                                waist = Integer.parseInt(BFWAISTTF.getText());

                                w = convertToKG(w); // LB convert to KG
                                h = feetToCM(h); //Feet -> CM
                                in = inchToCM(in); // Inches -> CM
                                h = h + in; // Adding the CM together....

                                neck = inchToCM(neck); // neck in to cm
                                waist = inchToCM(waist); // waist in to cm

                                calculateBFMale(w, h, neck, waist);
                            } else if (BFinchesTF.getText().isEmpty()) {
                                d.setHeaderText("ERROR, NEED A Height (in) SIZE");
                                d.setContentText("Height (in): ");
                                Optional<String> result = d.showAndWait();
                                if (result.isPresent()) {
                                    BFinchesTF.setText(result.get());
                                }
                            }
                        } else if (BFWAISTTF.getText().isEmpty()) {
                            d.setHeaderText("ERROR, NEED A Waist SIZE");
                            d.setContentText("Waist: ");
                            Optional<String> result = d.showAndWait();
                            if (result.isPresent()) {
                                BFWAISTTF.setText(result.get());
                            }
                        }
                    } else if (BFNECKTF.getText().isEmpty()) {
                        d.setHeaderText("ERROR, NEED A NECK SIZE");
                        d.setContentText("Neck: ");
                        Optional<String> result = d.showAndWait();
                        if (result.isPresent()) {
                            BFNECKTF.setText(result.get());
                        }
                    }
                } else if (BFHEIGHTTF.getText().isEmpty()) {
                    d.setHeaderText("ERROR, NEED A HEIGHT");
                    d.setContentText("Height: ");
                    Optional<String> result = d.showAndWait();
                    if (result.isPresent()) {
                        BFHEIGHTTF.setText(result.get());
                    }
                }
            } else if (BFWEIGHTTF.getText().isEmpty()) {
                d.setHeaderText("ERROR, NEED A WEIGHT");
                d.setContentText("Weight: ");
                Optional<String> result = d.showAndWait();
                if (result.isPresent()) {
                    BFWEIGHTTF.setText(result.get());
                }
            }
        } else if (BFAGETF.getText().isEmpty()) {
            d.setHeaderText("ERROR, NEED AN AGE");
            d.setContentText("AGE: ");
            Optional<String> result = d.showAndWait();
            if (result.isPresent()) {
                BFAGETF.setText(result.get());
            }
        }
    }

    private void runLBFemale() {
        d.setTitle("BodyFat Calculator");
        int age;
        int w; // in lbs
        int h; // in feet
        int in; // inches
        int neck; // inches
        int waist; // inches
        int hip; // inches
        if (!BFAGETF.getText().isEmpty()) {
            if (!BFWEIGHTTF.getText().isEmpty()) {
                if (!BFHEIGHTTF.getText().isEmpty()) {
                    if (!BFNECKTF.getText().isEmpty()) {
                        if (!BFWAISTTF.getText().isEmpty()) {
                            if (!BFinchesTF.getText().isEmpty()) {
                                if (!BFHIPSTF.getText().isEmpty()) {
                                    age = Integer.parseInt(BFAGETF.getText());
                                    w = Integer.parseInt(BFWEIGHTTF.getText());
                                    h = Integer.parseInt(BFHEIGHTTF.getText());
                                    in = Integer.parseInt(BFinchesTF.getText());
                                    neck = Integer.parseInt(BFNECKTF.getText());
                                    waist = Integer.parseInt(BFWAISTTF.getText());
                                    hip = Integer.parseInt(BFHIPSTF.getText());

                                    w = convertToKG(w); // LB convert to KG
                                    h = feetToCM(h); //Feet -> CM
                                    in = inchToCM(in); // Inches -> CM
                                    h = h + in; // Adding the CM together....

                                    neck = inchToCM(neck); // neck in to cm
                                    waist = inchToCM(waist); // waist in to cm
                                    hip = inchToCM(hip); // Hips (in) to cm

                                    calculateBFFemale(w, h, neck, waist, hip);
                                } else if (BFHIPSTF.getText().isEmpty()) {
                                    d.setHeaderText("ERROR, NEED A Hips (in) SIZE");
                                    d.setContentText("Hips (in): ");
                                    Optional<String> result = d.showAndWait();
                                    if (result.isPresent()) {
                                        BFHIPSTF.setText(result.get());
                                    }
                                }
                            } else if (BFinchesTF.getText().isEmpty()) {
                                d.setHeaderText("ERROR, NEED A Height (in) SIZE");
                                d.setContentText("Height (in): ");
                                Optional<String> result = d.showAndWait();
                                if (result.isPresent()) {
                                    BFinchesTF.setText(result.get());
                                }
                            }
                        } else if (BFWAISTTF.getText().isEmpty()) {
                            d.setHeaderText("ERROR, NEED A Waist SIZE");
                            d.setContentText("Waist: ");
                            Optional<String> result = d.showAndWait();
                            if (result.isPresent()) {
                                BFWAISTTF.setText(result.get());
                            }
                        }
                    } else if (BFNECKTF.getText().isEmpty()) {
                        d.setHeaderText("ERROR, NEED A NECK SIZE");
                        d.setContentText("Neck: ");
                        Optional<String> result = d.showAndWait();
                        if (result.isPresent()) {
                            BFNECKTF.setText(result.get());
                        }
                    }
                } else if (BFHEIGHTTF.getText().isEmpty()) {
                    d.setHeaderText("ERROR, NEED A HEIGHT");
                    d.setContentText("Height: ");
                    Optional<String> result = d.showAndWait();
                    if (result.isPresent()) {
                        BFHEIGHTTF.setText(result.get());
                    }
                }
            } else if (BFWEIGHTTF.getText().isEmpty()) {
                d.setHeaderText("ERROR, NEED A WEIGHT");
                d.setContentText("Weight: ");
                Optional<String> result = d.showAndWait();
                if (result.isPresent()) {
                    BFWEIGHTTF.setText(result.get());
                }
            }
        } else if (BFAGETF.getText().isEmpty()) {
            d.setHeaderText("ERROR, NEED AN AGE");
            d.setContentText("AGE: ");
            Optional<String> result = d.showAndWait();
            if (result.isPresent()) {
                BFAGETF.setText(result.get());
            }
        }
    }

    private void calculateBFMale(int w, int h, int neck, int waist) {
        //Weight height neck and waist
        // Formula is (495) / (1.0324 - 0.19077) * log10(waist-neck) + (0.15456 * log10(height)  - 450
        double result = ((495) / (1.0324 - 0.19077 * (Math.log10(waist - neck)) + (0.15456 * (Math.log10(h))))) - 450;
        String format = String.format("%.1f", result); // This has the bodyfat %
        bodyFatOutput(result, format);
    }

    private void calculateBFFemale(int w, int h, int neck, int waist, int hip) {
        double result = ((495) / (1.29579 - 0.35004 * (Math.log10(waist + hip - neck)) + (0.22100 * (Math.log10(h))))) - 450;
        String format = String.format("%.1f", result); // This has the bodyfat %
        bodyFatOutput(result, format);
    }

    private void bodyFatOutput(double result, String format) {
        if (BFMALETB.isSelected()) {
            if (result >= 2 && result <= 5) {
                a.setHeaderText("Essential Fat");
                a.setContentText("Your BFP is " + format
                        + ", this signifies that the current fat within your body is essential to maintain normal physiological functions");
                a.getDialogPane().setMinWidth(Region.USE_PREF_SIZE);
                a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                a.showAndWait();
            } else if (result > 5 && result <= 13) {
                a.setHeaderText("Athletes");
                a.setContentText("Your BFP is " + format
                        + ", this signifies that your body fat percentage is within an male athletes range.");
                a.getDialogPane().setMinWidth(Region.USE_PREF_SIZE);
                a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                a.showAndWait();
            } else if (result > 13 && result <= 17) {
                a.setHeaderText("Fitness");
                a.setContentText("Your BFP is " + format
                        + ", this signifies that your BFP is around a male fitness level range.");
                a.getDialogPane().setMinWidth(Region.USE_PREF_SIZE);
                a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                a.showAndWait();
            } else if (result > 17 && result <= 24) {
                a.setContentText("Average");
                a.setContentText("Your BFP is " + format
                        + ", this signifies that your BFP is within the average male range.");
                a.getDialogPane().setMinWidth(Region.USE_PREF_SIZE);
                a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                a.showAndWait();
            } else if (result > 24) {
                a.setHeaderText("Obesity");
                a.setContentText("Your BFP is " + format
                        + ", this signifies that you are obese based on your percentage and must consult with your personal care physican to see possible routes to take to maintain a healthier lifestyle");
                a.getDialogPane().setMinWidth(Region.USE_PREF_SIZE);
                a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                a.showAndWait();
            }
        } else if (BFFEMALETB.isSelected()) {
            if (result >= 10 && result <= 13) {
                a.setHeaderText("Essential Fat");
                a.setContentText("Your BFP is " + format
                        + ", this signifies that the current fat within your body is essential to maintain normal physiological functions");
                a.getDialogPane().setMinWidth(Region.USE_PREF_SIZE);
                a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                a.showAndWait();
            } else if (result > 13 && result <= 20) {
                a.setHeaderText("Athletes");
                a.setContentText("Your BFP is " + format
                        + ", this signifies that your body fat percentage is within a female athletes range.");
                a.getDialogPane().setMinWidth(Region.USE_PREF_SIZE);
                a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                a.showAndWait();
            } else if (result > 20 && result <= 24) {
                a.setHeaderText("Fitness");
                a.setContentText("Your BFP is " + format
                        + ", this signifies that your BFP is around a female fitness level range.");
                a.getDialogPane().setMinWidth(Region.USE_PREF_SIZE);
                a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                a.showAndWait();
            } else if (result > 24 && result <= 31) {
                a.setContentText("Average");
                a.setContentText("Your BFP is " + format
                        + ", this signifies that your BFP is within the average female range.");
                a.getDialogPane().setMinWidth(Region.USE_PREF_SIZE);
                a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                a.showAndWait();
            } else if (result > 32) {
                a.setHeaderText("Obesity");
                a.setContentText("Your BFP is " + format
                        + ", this signifies that you are obese based on your percentage and must consult with your personal care physican to see possible routes to take to maintain a healthier lifestyle");
                a.getDialogPane().setMinWidth(Region.USE_PREF_SIZE);
                a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                a.showAndWait();
            }
        }
    }

    //Converter method
    private static int convertToKG(int w) {
        double dummyW = w / 2.205;
        return (int) dummyW;
    }

    //Converter method feet to cm
    private static int feetToCM(int h) {
        double dummyH = h * 30.48;
        return (int) dummyH;
    }

    //Converter method inches to cm
    private static int inchToCM(int in) {
        double dummyIN = in * 2.54;
        return (int) dummyIN;
    }

    @FXML
    private void calculatePAC() {
        a.setTitle("Physical Activity Calculator");
        makeSureNotEmpty();
        pacaW = Integer.parseInt(pacaWTF.getText());
        pacaT = Double.parseDouble(pacaMINTF.getText());
        // Formula is (Min - (MET x 3.5 x Weight) ) / 200
        checkForZero(pacaW, pacaT);
        // Not null or 0
        if(pacakgRB.isSelected()){
            double res = (pacaT * (metValue * 3.5 * pacaW) / 200);
            int result = (int) res;
            pacaRT.setText(result + " calories");
            a.setHeaderText("Physical Activity Calculations...");
            a.setContentText("Total is: " + result + " calories");
            a.showAndWait();
        } else if (pacalbRB.isSelected()){
            pacaW = convertToKG(pacaW);
            double res = (pacaT * (metValue * 3.5 * pacaW) / 200);
            int result = (int) res;
            pacaRT.setText(result + " calories");
            a.setHeaderText("Physical Activity Calculations...");
            a.setContentText("Total is: " + result + " calories");
            a.showAndWait();
        }
    }

    private void makeSureNotEmpty() {
        if (pacaWTF.getText().isEmpty()) {
            d.setTitle("Physical Activity Calculator");
            d.setHeaderText("Please add weight information");
            d.setContentText("Weight : ");
            Optional<String> res = d.showAndWait();
            res.ifPresent(weightpac -> {
                pacaWTF.setText(weightpac);
            });
        }
        if (pacaMINTF.getText().isEmpty()) {
            d.setTitle("Physical Activity Calculator");
            d.setHeaderText("Please add amount of minutes done");
            d.setContentText("Minutes : ");
            Optional<String> res = d.showAndWait();
            res.ifPresent(minutes -> {
                pacaMINTF.setText(minutes);
            });
        }
    }

    public void checkForZero(int x, double y) {
        if (x == 0) {
            d.setTitle("Physical Activity Calculator");
            d.setHeaderText("Please enter weight, cant be 0");
            d.setContentText("Weight : ");
            Optional<String> res = d.showAndWait();
            res.ifPresent(weight -> {
                pacaW = Integer.parseInt(weight);
            });
        }
        if (y == 0 || y == 0.0) {
            d.setTitle("Physical Activity Calculator");
            d.setHeaderText("Please enter time, cant be 0");
            d.setContentText("Time : ");
            Optional<String> res = d.showAndWait();
            res.ifPresent(time -> {
                pacaT = Double.parseDouble(time);
            });
        }
    }

    @FXML
    private void misterRESET() {
        resetKGCalc();
        resetLBCalc();

        resetWater();

        resetBMILB();
        resetBMIKG();

        resetBodyFatKG();
        
        resetPAC();
    }
}
