/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capstone;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.Region;

/**
 *
 * @author Omar Muy
 */
public class bodyFatCalculator {

    @FXML
    private RadioButton rbMale, rbFemale, rbMetric, rbUS;
    @FXML
    private TextField ageTF, weightTF, feetTF, inchesTF, centTF, waistTF, neckTF, hipTF;
    @FXML
    private Label aLabel, hipID;

    private Alert a = new Alert(AlertType.INFORMATION);
    private Alert b = new Alert(AlertType.INFORMATION);
    private UserProfileModel usr = UserProfileModel.getInstance();

    @FXML
    public void initialize(){
    
            if (!usr.getUserName().equals("")) {
            loadGender();
            loadHeightData();
            loadUserAge();
        }
        
    }
    
     @FXML
    public void loadHeightData() {
        if (usr.getHeight() != null) {
            String[] height = usr.getHeight().split("-");
            String ft = height[0];
            String in = height[1];
            feetTF.setText(ft);
            inchesTF.setText(in);
        }
    }

    @FXML
    private void loadUserAge() {
        if (usr.getBirthDate() != null) {
            if (usr.getAge() > -1) {
                ageTF.setText(String.valueOf(usr.getAge()));
            } else {
                System.out.println("Birth Age is greater than Today's date so Can't calc");
            }
        }
    }
    
    @FXML
    private void loadGender() {
        if (usr.getGender() != null) {
            if (usr.getGender().equals("Male")) {
                rbMale.setSelected(true);
            }
            if (usr.getGender().equals("Female")) {
                rbFemale.setSelected(true);
            }
        }
    }
    
    @FXML
    private void switchToCalc() throws IOException {
        App.setRoot("Calculators");
    }

    @FXML
    private void calculateBodyFat() throws IOException {
        if (rbMetric.isSelected()) {
            //Metric Time

            if (rbMale.isSelected()) {
                //Unit is selected and Male
                if (ageTF.getText() != null) {
                    // Get age since it's not null
                    int age = Integer.parseInt(ageTF.getText());
                    if (age != 0 && age < 100) {
                        //We have Metric, Male and Age, now to use Weight in KG
                        double weight = Double.parseDouble(weightTF.getText());
                        // weight is now saved, now time for neck and waist
                        double neck = Double.parseDouble(neckTF.getText());
                        double waist = Double.parseDouble(waistTF.getText());
                        // Now we have neck and waist time for height
                        double height = Double.parseDouble(centTF.getText());
                        // height in CM
                        //METRIC conversion
                        //Based on US Navy method
                        double bfp1 = 495;
                        double bfp2 = 1.0324 - 0.19077 * Math.log10(waist - neck);
                        double bfp3 = 0.15456 * Math.log10(height);
                        double sub = 450;
                        // Start adding
                        double bfp4 = bfp2 + bfp3;
                        double bfp5 = bfp1 / bfp4;
                        double BFP = bfp5 - sub;
                        // Now to format it
                        String format = String.format("%.1f", BFP);
                        //double BFP = ((495) / 1.0324 - 0.19077 * Math.log10(waist - neck)
                        //        + 0.15456 * Math.log10(height) ) - 450;
                        System.out.println("Body Fat Percentage is " + format);
                        aLabel.setText("BFP = " + format);
                        // Send it for alerts
                        output(BFP, format);
                        jackPollard(age);
                    }
                } else {
                    //Error check for age
                    a.setHeaderText("Warning");
                    a.setContentText("Please give an age");
                    a.showAndWait();
                }
            } else if (rbFemale.isSelected()) {
                // Metric and Female
                if (ageTF.getText() != null) {
                    // Get age since it's not null
                    int age = Integer.parseInt(ageTF.getText());
                    if (age != 0 && age < 100) {
                        //We have Metric, Male and Age, now to use Weight in KG
                        double weight = Double.parseDouble(weightTF.getText());
                        // weight is now saved, now time for neck and waist
                        double neck = Double.parseDouble(neckTF.getText());
                        double waist = Double.parseDouble(waistTF.getText());
                        // Now we have neck and waist time for height
                        double height = Double.parseDouble(centTF.getText());
                        // now for females they have hips and is used to calculate
                        double hip = Double.parseDouble(hipTF.getText());
                        // height in CM
                        //METRIC conversion
                        //Based on US Navy method for Females
                        double bfp1 = 495;
                        double bfp2 = 1.29579 - 0.35004 * Math.log10(waist + hip - neck);
                        double bfp3 = 0.22100 * Math.log10(height);
                        double sub = 450;
                        // Start adding
                        double bfp4 = bfp2 + bfp3;
                        double bfp5 = bfp1 / bfp4;
                        double BFP = bfp5 - sub;
                        // Now to format it
                        String format = String.format("%.1f", BFP);
                        //double BFP = ((495) / 1.0324 - 0.19077 * Math.log10(waist - neck)
                        //        + 0.15456 * Math.log10(height) ) - 450;
                        System.out.println("Body Fat Percentage is " + format);
                        aLabel.setText("BFP = " + format);
                        output(BFP, format);
                        jackPollard(age);
                    }
                } else {
                    //Error check for age
                    a.setHeaderText("Warning");
                    a.setContentText("Please give an age");
                    a.showAndWait();
                }
            } else {
                //Error check for gender
                a.setHeaderText("WARNING");
                a.setContentText("Please enter a gender");
                a.showAndWait();
            }
        } else if (rbUS.isSelected()) {
            // US Unit time

            if (rbMale.isSelected()) {
                if (ageTF.getText() != null) {
                    // Get age since it's not null
                    int age = Integer.parseInt(ageTF.getText());
                    if (age != 0 && age < 100) {
                        //We have US unit, Male and Age, now to use Weight 
                        double weight = Double.parseDouble(weightTF.getText());
                        // weight is now saved, now time for neck and waist
                        double neck = Double.parseDouble(neckTF.getText());
                        double waist = Double.parseDouble(waistTF.getText());
                        // Now we have neck and waist time for height
                        double h1 = Double.parseDouble(feetTF.getText());
                        double h2 = Double.parseDouble(inchesTF.getText());
                        // convert feet to inches
                        double c1 = h1 * 12;
                        double height = c1 + h2; //Adding inches of feet and inches 
                        //double height = Double.parseDouble(centTF.getText());
                        // height in inches
                        //US conversion
                        //Based on US Navy method
                        double bfp2 = 86.010 * Math.log10(waist - neck);
                        double bfp3 = 70.041 * Math.log10(height);
                        double BFP = (bfp2 - bfp3) + 38.76; // End it.
                        double test = (86.010 * Math.log10(waist - neck)) - (70.041 * Math.log10(height)) + 36.76;
                        // Now to format it
                        String format = String.format("%.1f", BFP);
                        //double BFP = ((495) / 1.0324 - 0.19077 * Math.log10(waist - neck)
                        //        + 0.15456 * Math.log10(height) ) - 450;
                        System.out.println("Body Fat Percentage is " + format);
                        aLabel.setText("BFP = " + format);
                        output(BFP, format);
                        jackPollard(age);
                        
                    }
                } else {
                    //Error check for age
                    a.setHeaderText("Warning");
                    a.setContentText("Please give an age");
                    a.showAndWait();
                }
            } else if (rbFemale.isSelected()) {
                if (ageTF.getText() != null) {
                    // Get age since it's not null
                    int age = Integer.parseInt(ageTF.getText());
                    if (age != 0 && age < 100) {
                        //We have US unit, Female and Age, now to use Weight 
                        double weight = Double.parseDouble(weightTF.getText());
                        // weight is now saved, now time for neck and waist
                        double neck = Double.parseDouble(neckTF.getText());
                        double waist = Double.parseDouble(waistTF.getText());
                        // We also need their hips
                        double hips = Double.parseDouble(hipTF.getText());

                        // Now we have neck and waist time for height
                        double h1 = Double.parseDouble(feetTF.getText());
                        double h2 = Double.parseDouble(inchesTF.getText());

                        // convert feet to inches
                        double c1 = h1 * 12;
                        double height = c1 + h2; //Adding inches of feet and inches 
                        //double height = Double.parseDouble(centTF.getText());
                        // height in inches
                        //US conversion
                        //Based on US Navy method for women
                        double bfp2 = 163.205 * Math.log10(waist + hips - neck);
                        double bfp3 = 97.684 * (Math.log10(height));
                        double BFP = (bfp2 - bfp3) - 78.387; // End it.
                        //double test = (86.010 * Math.log10(waist - neck)) - (70.041 * Math.log10(height)) + 36.76;
                        // Now to format it
                        String format = String.format("%.1f", BFP);
                        //double BFP = ((495) / 1.0324 - 0.19077 * Math.log10(waist - neck)
                        //        + 0.15456 * Math.log10(height) ) - 450;
                        System.out.println("Body Fat Percentage is " + format);
                        aLabel.setText("BFP = " + format);
                        output(BFP, format);
                        jackPollard(age);
                    }
                } else {
                    //Error check for age
                    a.setHeaderText("Warning");
                    a.setContentText("Please give an age");
                    a.showAndWait();
                }

            } else {
                a.setHeaderText("WARNING");
                a.setContentText("Please enter a gender");
                a.showAndWait();
            }
        } else {
            // Error check for unit of scale
            a.setHeaderText("Warning");
            a.setContentText("Please set your unit of scale");
            a.showAndWait();

        }
    }

    @FXML
    private void getMetricProperties() {
        rbUS.selectedProperty().set(false);
        weightTF.promptTextProperty().set("kilograms");
        neckTF.promptTextProperty().set("centimeters");
        waistTF.promptTextProperty().set("centimeters");
        feetTF.disableProperty().set(true);
        inchesTF.disableProperty().set(true);
        centTF.disableProperty().set(false);
        // Properties have been set
    }

    @FXML
    private void getUSProperties() {
        rbMetric.selectedProperty().set(false);
        weightTF.promptTextProperty().set("pounds");
        neckTF.promptTextProperty().set("inches");
        waistTF.promptTextProperty().set("inches");
        centTF.disableProperty().set(true);
        feetTF.disableProperty().set(false);
        inchesTF.disableProperty().set(false);
        // Properties have been set
    }

    @FXML
    private void ifChoiceFemale() {
        rbMale.setSelected(false);
        hipID.setDisable(false);
        hipTF.setDisable(false);
        hipID.setOpacity(1);
        hipTF.setOpacity(1);
        if (rbMetric.isSelected()) {
            hipTF.setPromptText("centimeters");
        } else if (rbUS.isSelected()) {
            hipTF.setPromptText("inches");
        }

    }

    private void output(double result, String format) {
        if (rbMale.isSelected()) {
            // in case of Men
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
        } else if (rbFemale.isSelected()) {
            //case for Female
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

    private void jackPollard(int age) {
        if(rbMale.isSelected()){
            if(age <= 20){
                b.setHeaderText("Ideal Body Fat Percentage");
                b.setHeaderText("Your ideal BFP based on gender and age is 8.5% or less. \n This is based on the Jackson & Pollard ideal BFP chart");
                b.getDialogPane().setMinWidth(Region.USE_PREF_SIZE);
                b.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                b.showAndWait();
            }
            else if(age >= 21 && age <= 25){
                b.setHeaderText("Ideal Body Fat Percentage");
                b.setHeaderText("Your ideal BFP based on gender and age is 10.5% or less. \n This is based on the Jackson & Pollard ideal BFP chart ");
                b.getDialogPane().setMinWidth(Region.USE_PREF_SIZE);
                b.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                b.showAndWait();
            }
            else if(age >= 26 && age <= 30){
                b.setHeaderText("Ideal Body Fat Percentage");
                b.setHeaderText("Your ideal BFP based on gender and age is 12.7% or less. \n This is based on the Jackson & Pollard ideal BFP chart");
                b.getDialogPane().setMinWidth(Region.USE_PREF_SIZE);
                b.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                b.showAndWait();
            }
            else if(age >= 31 && age <= 35){
                b.setHeaderText("Ideal Body Fat Percentage");
                b.setHeaderText("Your ideal BFP based on gender and age is 13.7% or less. \n This is based on the Jackson & Pollard ideal BFP chart");
                b.getDialogPane().setMinWidth(Region.USE_PREF_SIZE);
                b.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                b.showAndWait();
            }
            else if(age >= 36 && age <= 40){
                b.setHeaderText("Ideal Body Fat Percentage");
                b.setHeaderText("Your ideal BFP based on gender and age is 15.3% or less. \n This is based on the Jackson & Pollard ideal BFP chart");
                b.getDialogPane().setMinWidth(Region.USE_PREF_SIZE);
                b.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                b.showAndWait();
            }
            else if(age >= 41 && age <= 45){
                b.setHeaderText("Ideal Body Fat Percentage");
                b.setHeaderText("Your ideal BFP based on gender and age is 16.4% or less. \n This is based on the Jackson & Pollard ideal BFP chart ");
                b.getDialogPane().setMinWidth(Region.USE_PREF_SIZE);
                b.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                b.showAndWait();
            }
            else if (age >= 46 && age <= 50){
                b.setHeaderText("Ideal Body Fat Percentage");
                b.setHeaderText("Your ideal BFP based on gender and age is 18.9% or less. \n This is based on the Jackson & Pollard ideal BFP chart ");
                b.getDialogPane().setMinWidth(Region.USE_PREF_SIZE);
                b.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                b.showAndWait();
            }
            else if (age >= 51) {
                // ideal is 20.9%
                b.setHeaderText("Ideal Body Fat Percentage");
                b.setHeaderText("Your ideal BFP based on gender and age is 20.9% or less. \n This is based on the Jackson & Pollard ideal BFP chart");
                b.getDialogPane().setMinWidth(Region.USE_PREF_SIZE);
                b.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                b.showAndWait();
            }
        }
        else if (rbFemale.isSelected()){
            if(age <= 20){
                b.setHeaderText("Ideal Body Fat Percentage");
                b.setHeaderText("Your ideal BFP based on gender and age is 17.7% or less. \n This is based on the Jackson & Pollard ideal BFP chart");
                b.getDialogPane().setMinWidth(Region.USE_PREF_SIZE);
                b.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                b.showAndWait();
            }
            else if(age >= 21 && age <= 25){
                b.setHeaderText("Ideal Body Fat Percentage");
                b.setHeaderText("Your ideal BFP based on gender and age is 18.4% or less. \n This is based on the Jackson & Pollard ideal BFP chart");
                b.getDialogPane().setMinWidth(Region.USE_PREF_SIZE);
                b.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                b.showAndWait();
            }
            else if(age >= 26 && age <= 30){
                b.setHeaderText("Ideal Body Fat Percentage");
                b.setHeaderText("Your ideal BFP based on gender and age is 19.3% or less. \n This is based on the Jackson & Pollard ideal BFP chart");
                b.getDialogPane().setMinWidth(Region.USE_PREF_SIZE);
                b.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                b.showAndWait();
            }
            else if(age >= 31 && age <= 35){
                b.setHeaderText("Ideal Body Fat Percentage");
                b.setHeaderText("Your ideal BFP based on gender and age is 21.5% or less. \n This is based on the Jackson & Pollard ideal BFP chart");
                b.getDialogPane().setMinWidth(Region.USE_PREF_SIZE);
                b.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                b.showAndWait();
            }
            else if(age >= 36 && age <= 40){
                b.setHeaderText("Ideal Body Fat Percentage");
                b.setHeaderText("Your ideal BFP based on gender and age is 22.2% or less. \n This is based on the Jackson & Pollard ideal BFP chart");
                b.getDialogPane().setMinWidth(Region.USE_PREF_SIZE);
                b.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                b.showAndWait();
            }
            else if(age >= 41 && age <= 45){
                b.setHeaderText("Ideal Body Fat Percentage");
                b.setHeaderText("Your ideal BFP based on gender and age is 22.9% or less. \n This is based on the Jackson & Pollard ideal BFP chart");
                b.getDialogPane().setMinWidth(Region.USE_PREF_SIZE);
                b.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                b.showAndWait();
            }
            else if (age >= 46 && age <= 50){
                b.setHeaderText("Ideal Body Fat Percentage");
                b.setHeaderText("Your ideal BFP based on gender and age is 25.2% or less. \n This is based on the Jackson & Pollard ideal BFP chart");
                b.getDialogPane().setMinWidth(Region.USE_PREF_SIZE);
                b.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                b.showAndWait();
            }
            else if (age >= 51) {
                // ideal is 26.3%
                b.setHeaderText("Ideal Body Fat Percentage");
                b.setHeaderText("Your ideal BFP based on gender and age is 26.3% or less. \n This is based on the Jackson & Pollard ideal BFP chart");
                b.getDialogPane().setMinWidth(Region.USE_PREF_SIZE);
                b.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                b.showAndWait();
            }
        }
    }
    
    @FXML
    private void resetBFP(){
        //reset all except ageTF prompt
        rbMale.setSelected(false);
        rbFemale.setSelected(false);
        rbUS.setSelected(false);
        rbMetric.setSelected(false);
        
        ageTF.setText("");
        weightTF.setText("");
        weightTF.setPromptText("");
        feetTF.setText("");
        feetTF.setPromptText("");
        inchesTF.setText("");
        inchesTF.setPromptText("");
        centTF.setText("");
        centTF.setPromptText("");
        waistTF.setText("");
        waistTF.setPromptText("");
        neckTF.setText("");
        neckTF.setPromptText("");
        hipTF.setText("");
        hipTF.setPromptText("");
        hipTF.setOpacity(0);
        hipID.setOpacity(0);
        aLabel.setText("");
                      
    }
    
    @FXML   
    private void ifMale(){
        rbFemale.setSelected(false);
    }
}
