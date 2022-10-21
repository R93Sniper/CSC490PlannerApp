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
import javafx.scene.layout.Region;

/**
 *
 * @author Omar Muy
 */
public class bmiCalculator {

    @FXML
    private TextField ageTF, feetTF, inchesTF, cmTF, lbsTF, kgTF;
    @FXML
    private RadioButton rbMale, rbFemale;
    @FXML
    private Label answerLabel;

    private Alert a = new Alert(AlertType.INFORMATION);
    private UserProfileModel usr = UserProfileModel.getInstance();

    @FXML
    public void initialize() {

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
    private void switchToCalculators() throws IOException {
        App.setRoot("Calculators");
    }

    @FXML
    private void getBMI() throws IOException {
        if (ageTF.getText() != null) {
            System.out.println("Age is given");
            int age = Integer.parseInt(ageTF.getText()); // Age is saved
            if (age != 0 && age < 100) {
                //if age isn't 0 and is less than 100 do..
                if (rbMale.isSelected() || rbFemale.isSelected()) {
                    System.out.println("Gender is given");
                    if (cmTF.getText().isEmpty()) {
                        // This means feet and inches are given
                        int ft = Integer.parseInt(feetTF.getText());
                        int in = Integer.parseInt(inchesTF.getText());
                        // Values for feet and inches are saved. 
                        // Now we must convert to meters. 
                        if (ft > 0 && in >= 0) {
                            double c1 = ft / 3.281; // This converts feet to meters
                            double c2 = in / 39.37; // This converts inches to meters. 
                            double height = c1 + c2; // Add both up
                            // Now I have age, gender and height, time for KG
                            if (kgTF.getText().isEmpty()) {
                                // DO LBS calc
                                int w = Integer.parseInt(lbsTF.getText());
                                double weight = w / 2.205; // This converts LBS to KG
                                // now we have all requirements for calculation
                                double result = weight / (height * height);
                                String format = String.format("%.1f", result);
                                System.out.println("BMI is " + format);
                                answerLabel.setText("Your BMI is: " + format);
                                // Now divide by age and BMI. 
                                if (age >= 20) {
                                    // If age is 20 or more then run the dialog pane of statistics
                                    // AKA BMI Score separation
                                    output(result, format);

                                } else if (age < 20) {
                                    // Run informative WHO dialog to inform user. 
                                    a.setHeaderText("CAUTION");
                                    a.setContentText("Check the World Health Organization website for a more accurate BMI based on your age/gender");
                                    a.showAndWait();
                                }

                            } else if (lbsTF.getText().isEmpty()) {
                                // DO KG calc
                                int weight = Integer.parseInt(kgTF.getText());
                                double result = weight / (height * height);
                                String format = String.format("%.1f", result);
                                System.out.println("BMI is " + format);
                                answerLabel.setText("Your BMI is: " + format);
                                // Now divide by age and BMI. 
                                if (age >= 20) {
                                    // If age is 20 or more then run the dialog pane of statistics
                                    // AKA BMI Score separation
                                    output(result, format);
                                } else if (age < 20) {
                                    // Run informative WHO dialog to inform user. 
                                    a.setHeaderText("CAUTION");
                                    a.setContentText("Check the World Health Organization website for a more accurate BMI based on your age/gender");
                                    a.showAndWait();
                                }
                            }
                        }
                    } else if (feetTF.getText().isEmpty() && inchesTF.getText().isEmpty()) {
                        // This means cm is given... 
                        double cm = Integer.parseInt(cmTF.getText());
                        double height = cm / 100.0; // Converts cm to meters and saves it into height.
                        // Calc for KG or Lbs now
                        if (kgTF.getText().isEmpty()) {
                            // DO LBS
                            int w = Integer.parseInt(lbsTF.getText());
                            double weight = w / 2.205; // This converts LBS to KG
                            // now we have all requirements for calculation
                            double result = weight / (height * height);
                            String format = String.format("%.1f", result);
                            System.out.println("BMI is " + format);
                            answerLabel.setText("Your BMI is: " + format);
                            // Now divide by age and BMI. 
                            if (age >= 20) {
                                // If age is 20 or more then run the dialog pane of statistics
                                // AKA BMI Score separation
                                output(result, format);
                            } else if (age < 20) {
                                // Run informative WHO dialog to inform user. 
                                a.setHeaderText("CAUTION");
                                a.setContentText("Check the World Health Organization website for a more accurate BMI based on your age/gender");
                                a.showAndWait();
                            }
                        } else if (lbsTF.getText().isEmpty()) {
                            // DO KG
                            int weight = Integer.parseInt(kgTF.getText());
                            double result = weight / (height * height);
                            String format = String.format("%.1f", result);
                            System.out.println("BMI is " + format);
                            answerLabel.setText("Your BMI is: " + format);
                            // Now divide by age and BMI. 
                            if (age >= 20) {
                                // If age is 20 or more then run the dialog pane of statistics
                                // AKA BMI Score separation
                                output(result, format);
                            } else if (age < 20) {
                                // Run informative WHO dialog to inform user. 
                                a.setHeaderText("CAUTION");
                                a.setContentText("Check the World Health Organization website for a more accurate BMI based on your age/gender");
                                a.showAndWait();
                            }
                        }
                    }
                } // The else below goes off the button selection
                else {
                    a.setHeaderText("CAUTION");
                    a.setContentText("You must select a gender in order to calculate your BMI");
                    a.showAndWait();
                }
            }
        }
    }

    @FXML
    private void resetBMICalc() {
        ageTF.clear();
        rbMale.setSelected(false);
        rbFemale.setSelected(false);
        feetTF.clear();
        inchesTF.clear();
        cmTF.clear();
        kgTF.clear();
        lbsTF.clear();

        answerLabel.setText("");
        System.out.println("Calc has been reset");
    }

    // This will sent informative alerts out.
    private void output(double result, String format) {
        if (result < 16) {
            // This is severe thinness
            a.setHeaderText("CAUTION: SEVERE THINNESS");
            a.setContentText("Your BMI is " + format + ", this BMI is categorizing you as severely thin, consulte with your nutritionist on a proper diet ");
            a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            a.getDialogPane().setMinWidth(Region.USE_PREF_SIZE);
            a.showAndWait();
        } else if (result > 16 && result < 18.5) {
            // thin
            a.setHeaderText("CAUTION: THIN");
            a.setContentText("Your BMI is " + format + ", this BMI is categorizing you as thin based on W.H.O. standards");
            a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            a.getDialogPane().setMinWidth(Region.USE_PREF_SIZE);
            a.showAndWait();
        } else if (result > 18.5 && result < 25) {
            // normal
            a.setHeaderText("CAUTION: NORMAL");
            a.setContentText("Your BMI is " + format + ", this BMI is categorizing you as average/normal.");
            a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            a.getDialogPane().setMinWidth(Region.USE_PREF_SIZE);
            a.showAndWait();

        } else if (result > 25 && result < 30) {
            // overweight
            a.setHeaderText("CAUTION: OVERWEIGHT");
            a.setContentText("Your BMI is " + format + ", this BMI is categorizing you as overweight, be careful of your diet and consider cutting back on some things to maintain a healthy weight.");
            a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            a.getDialogPane().setMinWidth(Region.USE_PREF_SIZE);
            a.showAndWait();
        } else if (result > 30) {
            // Obese
            a.setHeaderText("CAUTION: OBESE");
            a.setContentText("Your BMI is " + format + ", this BMI is categorizing you as being obese, consult with your physician or nutritionist for recommendations on maintaining a healthy lifestyle");
            a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            a.getDialogPane().setMinWidth(Region.USE_PREF_SIZE);
            a.showAndWait();
        }
    }
}
