package capstone;

import java.io.IOException;
import java.time.LocalDate;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.text.Text;

// @Author Omar Muy
public class CalculatorFunctions {

    @FXML
    private ToggleButton mb1, fb1, kgtb, lbtb;
    @FXML
    private TextField tf1, hf1, af1, feetTF, inchesTF;
    @FXML
    private Text tr1;
    @FXML
    private Button cb1, wcb;
    @FXML
    private ChoiceBox choiceb1;

    private int m = 400;
    private int g = 800;
    private UserProfileModel usr = UserProfileModel.getInstance();

    @FXML
    public void initialize() {
        //userDB = dataConnector.getInstance();
        usr = UserProfileModel.getInstance();
        if (!usr.getUserName().equals("")) {
            loadGender();
            loadHeightData();
            loadUserAge();
        }

    }
    
    @FXML
    private void loadGender(){
        if(usr.getGender() != null)
        {
            if (usr.getGender().equals("Male")) 
            {
                mb1.setSelected(true);
            }
            if (usr.getGender().equals("Female")) 
            {
                fb1.setSelected(true);
            }
        }
    }

    @FXML
    public void loadHeightData(){
        if (usr.getHeight() != null) 
        {
            String[] height = usr.getHeight().split("-");
            String ft = height[0];
            String in = height[1];
            feetTF.setText(ft);
            inchesTF.setText(in);
        }
    }

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    @FXML
    private void switchToHome() throws IOException {
        
        if (usr.getUserName().equals("")) {
            App.setRoot("primary");
        }else{
        App.setRoot("home");
        }
    }

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }

    // Water Switch
    @FXML
    private void waterCalc() throws IOException {
        App.setRoot("watercalc");
    }

    @FXML
    private void calculateCaloriesButton() throws IOException {

        if (mb1.isSelected()) {
            System.out.println("Male was selected");
            if (af1.getText() != null) {
                // Make sure it's not null. then convert 
                int age = Integer.parseInt(af1.getText());
                System.out.println("age isn't null: " + age);
                if (age != 0 && age < 100) {
                    //System.out.println("\nyour age is" + age);
                    if (tf1.getText() != null) {
                        // Make sure weight isn't null then convert
                        int weight = Integer.parseInt(tf1.getText());
                        System.out.println("weight isn't null: " + weight);
                        // Now to make weight making sure it's lbs or kg
                        if (weight > 0 && kgtb.isSelected()) {
                            // KG SELECTION
                            // Check textField for isEMPTY()
                            if (hf1.getText().isEmpty()) {
                                // This is where feet and inches must be converted to cm
                                int h1 = Integer.parseInt(feetTF.getText()); // Feet
                                int h2 = Integer.parseInt(inchesTF.getText()); // inches

                                if (h1 > 0 && h2 >= 0) { // h1 feet must be > 0 but inches can be 0 or greater
                                    double c1 = h1 * 30.48; // Converting feet to cm
                                    double c2 = h2 * 2.54; //Convert inches to cm
                                    double newHeight = c1 + c2; // add them both up

                                    double res = 10 * weight + 6.25 * newHeight - 5 * age + 5;
                                    long goal = Math.round(res);
                                    long goal2 = Math.round(res) + m;
                                    long goal3 = Math.round(res) + g;
                                    String result = Double.toString(res);
                                    //tr1.setText("\n" + result + " calories needed");
                                    System.out.println(res + " calories");
                                    Alert a = new Alert(AlertType.INFORMATION);
                                    a.setHeaderText("Here are your results");
                                    a.setContentText("To achieve weight loss by roughly 1 lb/ 2kg a week you must consume a total of " + goal
                                            + " calories/day. \n To maintain your weight you must consume a total of " + goal2
                                            + " calories/day \n To gain muscle mass you must consume a total of " + goal3 + " calories/day");
                                    a.showAndWait();

                                } else {
                                    System.out.println("Height error, height must be greater than 0");
                                }
                            } else if (feetTF.getText().isEmpty() && inchesTF.getText().isEmpty()) {
                                // HF is now CM only. 
                                int height = Integer.parseInt(hf1.getText()); // CM string to CM intt
                                System.out.println("height isn't null" + height);

                                if (height > 0) {
                                    //this happens if cm textField is inputted
                                    double res = 10 * weight + 6.25 * height - 5 * age + 5;
                                    long goal = Math.round(res);
                                    long goal2 = Math.round(res) + m;
                                    long goal3 = Math.round(res) + g;
                                    String result = Double.toString(res);
                                    //tr1.setText("\n" + result + " calories needed");
                                    System.out.println(res + " calories");
                                    Alert a = new Alert(AlertType.INFORMATION);
                                    a.setHeaderText("Here are your results");
                                    a.setContentText("To achieve weight loss by roughly 1 lb/ 2kg a week you must consume a total of " + goal
                                            + " calories/day. \n To maintain your weight you must consume a total of " + goal2
                                            + " calories/day \n To gain muscle mass you must consume a total of " + goal3 + " calories/day");
                                    a.showAndWait();

                                }

                            } else {
                                System.out.println("\nHeight must be included please.");
                            }
                        } // THIS IS STILL MENS CALCULATIONS JUST USING LBS RATHER THAN KG. 
                        else if (weight > 0 && lbtb.isSelected()) {
                            // LBS SELECTION
                            double nweight = weight / 2.205; // THIS IS THE CONVERTED WEIGHT
                            if (hf1.getText().isEmpty()) {

                                // This is where feet and inches must be converted to cm
                                int h1 = Integer.parseInt(feetTF.getText()); // Feet
                                int h2 = Integer.parseInt(inchesTF.getText()); // inches

                                if (h1 > 0 && h2 >= 0) { // h1 feet must be > 0 but inches can be 0 or greater
                                    double c1 = h1 * 30.48; // Converting feet to cm
                                    double c2 = h2 * 2.54; //Convert inches to cm
                                    double newHeight = c1 + c2; // add them both up

                                    double res = 10 * nweight + 6.25 * newHeight - 5 * age + 5;
                                    long goal = Math.round(res);
                                    long goal2 = Math.round(res) + m;
                                    long goal3 = Math.round(res) + g;
                                    String result = Double.toString(res);
                                    //tr1.setText("\n" + result + " calories needed");
                                    System.out.println(res + " calories");
                                    Alert a = new Alert(AlertType.INFORMATION);
                                    a.setHeaderText("Here are your results");
                                    a.setContentText("To achieve weight loss by roughly 1 lb/ 2kg a week you must consume a total of " + goal
                                            + " calories/day. \n To maintain your weight you must consume a total of " + goal2
                                            + " calories/day \n To gain muscle mass you must consume a total of " + goal3 + " calories/day");
                                    a.showAndWait();
                                }

                            } // POUNDS AND FEET AND INCHES
                            else if (feetTF.getText().isEmpty() && inchesTF.getText().isEmpty()) {
                                //double nweight = weight / 2.205;
                                int height = Integer.parseInt(hf1.getText());
                                System.out.println("\nHeight is: " + height);
                                if (height > 0) {
                                    //this happens if it's LBs and CM and it's a male and age is given......
                                    double res = 10 * nweight + 6.25 * height - 5 * age + 5;
                                    long goal = Math.round(res);
                                    long goal2 = Math.round(res) + m;
                                    long goal3 = Math.round(res) + g;
                                    System.out.println(res + " calories");
                                    //System.out.printf("%2.2", res);
                                    String result = Double.toString(res);
                                    //tr1.setText("\n" + result + " calories needed");
                                    Alert a = new Alert(AlertType.INFORMATION);
                                    a.setHeaderText("Here are your results");
                                    a.setContentText("To achieve weight loss by roughly 1 lb/ 2kg a week you must consume a total of " + goal
                                            + " calories/day. \n To maintain your weight you must consume a total of " + goal2
                                            + " calories/day \n To gain muscle mass you must consume a total of " + goal3 + " calories/day");
                                    a.showAndWait();
                                }
                            } else {
                                System.out.println("Height error, must be greater than 0");
                            }
                        } else {
                            System.out.println("Weight cannot be under 0");
                        }
                    }
                }
            } else {
                System.out.println("\nPlease enter your age");
            }
        } // This happens if FEMALE togglebutton is on <<<<<<<<<<<<<<<<< FEMALE >>>>>>>>>>>>>>>>>>>>>>>>>>>>
        else if (fb1.isSelected()) {
            if (af1.getText() != null) {
                // Make sure it's not null. then convert 
                int age = Integer.parseInt(af1.getText());
                if (age != 0 && age < 100) {
                    System.out.println("\nyour age is" + age);
                    if (tf1.getText() != null) {
                        // Make sure weight isn't null then convert
                        int weight = Integer.parseInt(tf1.getText());
                        System.out.println("\nWeight is this: " + weight);
                        // Now to make weight making sure it's lbs or kg
                        if (weight > 0 && kgtb.isSelected()) {
                            if (hf1.getText().isEmpty()) {
                                //int height = Integer.parseInt(hf1.getText());
                                //System.out.println("\nHeight is: " + height);
                                int h1 = Integer.parseInt(feetTF.getText()); // Feet
                                int h2 = Integer.parseInt(inchesTF.getText()); // inches
                                if (h1 > 0 && h2 >= 0) {
                                    double c1 = h1 * 30.48;// convert feet to cm
                                    double c2 = h2 * 2.52;
                                    double r = c1 + c2; // Total in CM

                                    double res = 10 * weight + 6.25 * r - 5 * age - 161;
                                    long goal = Math.round(res);
                                    long goal2 = Math.round(res) + m;
                                    long goal3 = Math.round(res) + g;
                                    //System.out.printf("%2.2", res);
                                    String result = Double.toString(res);
                                    //tr1.setText("\n" + result + " calories needed");
                                    System.out.println(res + " calories");
                                    Alert a = new Alert(AlertType.INFORMATION);
                                    a.setHeaderText("Here are your results");
                                    a.setContentText("To achieve weight loss by roughly 1 lb/ 2kg a week you must consume a total of " + goal
                                            + " calories/day. \n To maintain your weight you must consume a total of " + goal2
                                            + " calories/day \n To gain muscle mass you must consume a total of " + goal3 + " calories/day");
                                    a.showAndWait();

                                }
                            } else if (feetTF.getText().isEmpty() && inchesTF.getText().isEmpty()) {
                                int height = Integer.parseInt(hf1.getText());
                                System.out.println("\nHeight is: " + height);
                                if (height > 0) {
                                    //this happens if it's both KG and CM and it's a FEMALE and age is given......
                                    double res = 10 * weight + 6.25 * height - 5 * age - 161;
                                    long goal = Math.round(res);
                                    long goal2 = Math.round(res) + m;
                                    long goal3 = Math.round(res) + g;
                                    //System.out.printf("%2.2", res);
                                    String result = Double.toString(res);
                                    //tr1.setText("\n" + result + " calories needed");
                                    System.out.println(res + " calories");
                                    Alert a = new Alert(AlertType.INFORMATION);
                                    a.setHeaderText("Here are your results");
                                    a.setContentText("To achieve weight loss by roughly 1 lb/ 2kg a week you must consume a total of " + goal
                                            + " calories/day. \n To maintain your weight you must consume a total of " + goal2
                                            + " calories/day \n To gain muscle mass you must consume a total of " + goal3 + " calories/day");
                                    a.showAndWait();
                                } else {
                                    System.out.println("Height error");
                                }
                            } else {
                                System.out.println("\nHeight must be included please.");
                            }
                        } // THIS IS STILL MENS CALCULATIONS JUST USING LBS RATHER THAN KG. 
                        else if (weight > 0 && lbtb.isSelected()) {
                            double nweight = weight / 2.205; // THIS IS THE CONVERTED WEIGHT
                            if (feetTF.getText().isEmpty() && inchesTF.getText().isEmpty()) {
                                int height = Integer.parseInt(hf1.getText());
                                System.out.println("\nHeight is: " + height);
                                if (height > 0) {
                                    //this happens if it's LBs and CM and it's a male and age is given......
                                    double res = 10 * nweight + 6.25 * height - 5 * age - 161;
                                    long goal = Math.round(res);
                                    long goal2 = Math.round(res) + m;
                                    long goal3 = Math.round(res) + g;
                                    //System.out.printf("%2.2", res);
                                    String result = Double.toString(res);
                                    //tr1.setText("\n" + result + " calories needed");
                                    System.out.println(res + " calories");
                                    Alert a = new Alert(AlertType.INFORMATION);
                                    a.setHeaderText("Here are your results");
                                    a.setContentText("To achieve weight loss by roughly 1 lb/ 2kg a week you must consume a total of " + goal
                                            + " calories/day. \n To maintain your weight you must consume a total of " + goal2
                                            + " calories/day \n To gain muscle mass you must consume a total of " + goal3 + " calories/day");
                                    a.showAndWait();
                                } else {
                                    System.out.println("Height error");
                                }
                            } else if (hf1.getText().isEmpty()) {
                                //lbs is selected and it's feet/inches
                                int h1 = Integer.parseInt(feetTF.getText());
                                int h2 = Integer.parseInt(inchesTF.getText());
                                if (h1 > 0 && h2 >= 0) {
                                    double c1 = h1 * 30.48;
                                    double c2 = h2 * 2.52;
                                    double r = c1 + c2;

                                    double res = (10 * nweight) + (6.25 * r) - (5 * age) - 161;
                                    long goal = Math.round(res);
                                    long goal2 = Math.round(res) + m;
                                    long goal3 = Math.round(res) + g;
                                    String result = Double.toString(res);
                                    //r1.setText("\n" + result + " calories needed");
                                    System.out.println(res + " calories");
                                    Alert a = new Alert(AlertType.INFORMATION);
                                    a.setHeaderText("Here are your results");
                                    a.setContentText("To achieve weight loss by roughly 1 lb/ 2kg a week you must consume a total of " + goal
                                            + " calories/day. \n To maintain your weight you must consume a total of " + goal2
                                            + " calories/day \n To gain muscle mass you must consume a total of " + goal3 + " calories/day");
                                    a.showAndWait();
                                }
                            }
                        } else {
                            System.out.println("Weight cannot be under 0");
                        }
                    }
                }
            } else {
                System.out.println("\nPlease enter your age");
            }
        } else if (!fb1.isSelected() && !mb1.isSelected()) {
            System.out.println("\nPlease choose a gender");

        }
    }

    @FXML // Resets Calorie Calculator
    private void reCalculateButton() {
        //Reset everything
        mb1.setSelected(false);
        fb1.setSelected(false);
        kgtb.setSelected(false);
        lbtb.setSelected(false);

        tf1.clear();
        af1.clear();
        hf1.clear();
        tr1.setText("");
        feetTF.clear();
        inchesTF.clear();
        System.out.println("Calculator has been reset");

    }

    @FXML
    private void loadUserAge() 
    {
        if(usr.getBirthDate() != null)
        {
        if (getAge() > -1) {
            af1.setText(String.valueOf(getAge()));
        } else {
            System.out.println("Birth Age is greater than Today's date so Can't calc");
        }
        }

    }

    @FXML
    private void loadUserHeight() {
        UserProfileModel usr = UserProfileModel.getInstance();

    }

    private int getAge() {
        int returnAge = -1;
        LocalDate todayDate = LocalDate.now();
        String[] bDate = usr.getBirthDate().split("-");
        int bDay = Integer.valueOf(bDate[1]);
        int bMonth = Integer.valueOf(bDate[0]);
        int bYear = Integer.valueOf(bDate[2]);
        int todayDay = todayDate.getDayOfMonth();
        int todayMonth = todayDate.getMonthValue();
        int todayYear = todayDate.getYear();
        if (bYear <= todayYear) {
            returnAge = todayYear - bYear;
        }
        System.out.println("bdate = " + usr.getBirthDate());
        System.out.println("todaydate = " + todayDate.toString());
        if (bMonth >= todayMonth) {
            System.out.println("minus 1");
            returnAge -= 1;
            if (bMonth == todayMonth && bDay < todayDay) {
                System.out.println("plus 1");
                returnAge += 1;
            }
        }

        return returnAge;
    }

}
