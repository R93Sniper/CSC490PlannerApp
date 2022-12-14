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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author jesus
 */
public class HomeController {

    @FXML
    private ListView goalsLV;
    @FXML
    private Label labelCalorieTarget;
    private DateTimeFormatter dt = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private UserProfileModel instanceUser = UserProfileModel.getInstance();
    private dataConnector userDB = dataConnector.getInstance();
    private ResultSet result;
    private ObservableList<String> obsList = FXCollections.observableArrayList();
    private ArrayList<ResultSet> goalResults;
    private UserGoalsConnector goalsDC = new UserGoalsConnector();
    private ProgressCardConnector pc = new ProgressCardConnector();

    /**
     * Initializes the controller class.
     */
    @FXML
    public void initialize() throws ParseException {
        goalResults = new ArrayList();
        loadProfile();
        loadProgressCardData();
        loadGoalsList();
        if(goalResults.isEmpty())
            goalsLV.setVisible(false);
        // TODO
        notificationProgressCard();
    }

    @FXML
    private void loadGoalsList() {
        boolean hasWgoal = false;
        String str = "";
        obsList.add("My Goals: ");
        String[] goalTypes = new String[]{"","Weight", "Size", "Strength"};
        for (int i = 1; i < 4; i++) {
            ResultSet result = goalsDC.getLastGoal(goalTypes[i], instanceUser.getUserName());
            if (result != null) {
                try {
                    str = str + "Goal: " + result.getString("Goal_Type") + " By " + result.getString("Target_Date");
                    if(result.getString("Goal_Type").contains("Weight")){ hasWgoal = true;}
                    obsList.add(str);          
                    str = "";
                    goalResults.add(result);
                } catch (SQLException ex) {
                    Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        goalsLV.setItems(obsList);
        goalsLV.getSelectionModel().selectedIndexProperty()
                .addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue ov, Number value, Number new_value) {
                        System.out.println("index= " + new_value.intValue());
                        int index = new_value.intValue();
                        try {
                            String str = makeGoalAlertMessage(index);
                            if(!str.equals(""))
                                makeAlert(str);
                            
                        } catch (SQLException ex) {
                            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
        if(hasWgoal) { getCalorieTarget();}
        //goalsLV.getSelectionModel().select(-1);
    }

    private String makeGoalAlertMessage(int index) throws SQLException {
            if(index==0)
                return "";
            String msg = "";

            ResultSet res = goalResults.get(index-1);   
            msg += "Target Date: " + res.getString("Target_Date") + " : ";
            if (index == 1) {
                msg += "\nTarget Weight: " + res.getString("Target_Weight") + "(lbs)";
            }
            if (index == 2) {
                String sizeId = res.getString("SizeGoal_id");
                ResultSet sizeRes = goalsDC.getRow(Integer.valueOf(sizeId), "SizeGoals");
                msg += "\nTarget Mesauremnts: ";
                msg += "\nNeck: " + sizeRes.getString("Neck_Target") + " in.";
                msg += "\nArms: " + sizeRes.getString("Arms_Target") + " in.";
                msg += "\nWaist: " + sizeRes.getString("Waist_Target") + " in.";
                msg += "\nHips: " + sizeRes.getString("Hips_Target") + " in.";
                msg += "\nLegs: " + sizeRes.getString("Legs_Target") + " in.";
            }
            if (index == 3) {
                String strengthId = res.getString("StrengthGoal_id");
                ResultSet sizeRes = goalsDC.getRow(Integer.valueOf(strengthId), "StrengthGoals");
                msg += "\nTarget Max Lifts: ";
                msg += "\nBenchPress: " + sizeRes.getString("BenchPress_Target") + " lbs.";
                msg += "\nDeadlift: " + sizeRes.getString("Deadlift_Target") + " lbs.";
                msg += "\nSquats: " + sizeRes.getString("Squats_Target") + " lbs.";
                msg += "\nLegPress: " + sizeRes.getString("LegPress_Target") + " lbs.";
                msg += "\nShoulderPress: " + sizeRes.getString("ShoulderPress_Target") + " lbs.";
            }
            msg += "\nGoal created on: " + res.getString("Date_Created");
            //goalsLV.getSelectionModel().selectFirst();
            return msg ;
}

        @FXML
        private void onLogoutPressed() throws IOException, SQLException {
        //dataConnector.getInstance().closeConnectionDB();
        instanceUser.resetModel();
        App.setRoot("Landing");
    }

    @FXML
        private void onViewProfile() throws IOException {

        App.setRoot("Profile");
    }

    @FXML
        private void onCalcWater() throws IOException {
        App.setRoot("Calculators");
    }

    @FXML
        private void setGoals() throws IOException {
        App.setRoot("goals");
    }

    private void loadProfile() {
        //returns resultset matching the given username

        ResultSet result;
        result = userDB.getResult(instanceUser.getUserName(), "User_Profile");

        int id;
        String tempUserName = "";
        String tempPW = "";
        String tempFirstName = "";
        String tempLastName = "";
        String tempEmail = "";
        String tempBodyType = "";
        String tempGender = "";
        String tempHeight = "";
        String tempDOB = "";

        try {
            while (result.next()) {
                //id = result.getInt("ID");
                tempUserName = result.getString("User_Name");
                tempPW = result.getString("User_Password");
                tempFirstName = result.getString("First_Name");
                tempLastName = result.getString("Last_Name");
                tempEmail = result.getString("Email");
                tempBodyType = result.getString("Body_Type");
                tempGender = result.getString("Gender");
                tempHeight = result.getString("Height");
                tempDOB = result.getString("Date_Of_Birth");

            }
        } catch (SQLException ex) {
            Logger.getLogger(UserProfileController

.class
.getName()).log(Level.SEVERE, null, ex);
        }

        instanceUser.setUserName(tempUserName);
        instanceUser.setGender(tempGender);
        instanceUser.setPassword(tempPW);
        instanceUser.setFirstName(tempFirstName);
        instanceUser.setLastName(tempLastName);
        instanceUser.setEmail(tempEmail);
        instanceUser.setHeight(tempHeight);
        instanceUser.setBodyType(tempBodyType);
        instanceUser.setBirthDate(tempDOB);
    }

    @FXML
        private void goToPCard() throws IOException {
        App.setRoot("progresscard");
    }

    @FXML
        private void onExerciseLookup() throws IOException {
        App.setRoot("ExerciseLookUp");
    }

    private void loadProgressCardData() {

        LocalDate now = LocalDate.now();
        int progressID = pc.getProgressID(instanceUser.getUserName(), dt.format(now));
        System.out.println("Progess Card ID =" + progressID);
        if (progressID != -1) {
            instanceUser.setProgressCardId(progressID);
            String intakeID = pc.getDailyIntakeID(progressID);
            if(intakeID != null)
            {
                instanceUser.setDailyIntakeId(Integer.valueOf(intakeID));
            }
            String exerciseID = pc.getDailyExerciseID(progressID);
            if(exerciseID != null)
            {
                instanceUser.setDailyExerciseId(Integer.valueOf(exerciseID));
            }
            System.out.println("intake Card ID =" + progressID);
            System.out.println("exercise Card ID =" + exerciseID);
        }
        //System.out.println("Progess id of User now =" + progressID);
    }

    @FXML
        private void goToMed(ActionEvent event) throws IOException {
        App.setRoot("MedicalConditions");
    }

    @FXML
        public void goToReports(ActionEvent event) throws IOException {
        App.setRoot("ProgressReport");
    }
    
    public void makeAlert(String alertText) {

        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Home Page");
        a.setHeaderText(alertText);
        a.show();
    }

    @FXML
    public void notificationProgressCard() throws ParseException{
        ProgressCardConnector pc = new ProgressCardConnector();
        LocalDate now = LocalDate.now();
        if(instanceUser.counter>0) return;
        boolean notify = false;
        String userDate = pc.getLastDateOfCard(instanceUser.getUserName());
        if(userDate.equals("")){
            instanceUser.counter = 1;
            makeAlert("No Progress Card found.\n "
                    + "Create a Progres Card now and"
                    + "Start tracking your Progress Today!");
            return;
        }
        
        int userYear =0;
                int userMonth=0;
                int userDay = 0;
        if(userDate.contains("/")){
        String temp[] = userDate.split("/");
        userYear = Integer.parseInt(temp[2]);
        userMonth = Integer.parseInt(temp[0]);
        userDay = Integer.parseInt(temp[1]);
        System.out.println(""+userYear+" ,"+userMonth+" ,"+userDay+" ,");    
        }else{
        String temp[] = userDate.split("-");
       userYear = Integer.parseInt(temp[0]);
        userMonth = Integer.parseInt(temp[1]);
        userDay = Integer.parseInt(temp[2]);
        System.out.println(""+userYear+" ,"+userMonth+" ,"+userDay+" ,");
        
        }    
       
      
           
        //getting localDate
        int localDay = now.getDayOfMonth();
        int localMonth = now.getMonthValue();
        int localYear = now.getYear();
      
        //difference 
        int diffDay = Math.abs(localDay - userDay);
        int diffMonth =  Math.abs(localMonth - userMonth);
        int diffYear =  Math.abs(localYear - userYear);
        if(diffDay > 7 || diffMonth>0 || diffYear>0){
            notify = true;
        }
        
        // will notify if been longer than a week        
        if(notify){
            if(instanceUser.counter==0){
            makeAlert("Update your Progress Card.\n It's been " + diffDay + " days, " + diffMonth + " month and " + diffYear
            + " year, Since Last Progress Card!");
            instanceUser.counter = 1;
            }
            
        }
        else{
            ;
        }
       
       
    }
    
    private void getCalorieTarget() {
        //formula = male : (10 * x) + (6.25 * height) - (5 * age) + 5;   //male 
        //female: double res = (10 * x) + (6.25 * height) - (5 * age) - 161;  //female
        // double res = (10 * x) + (6.25 * height) - (5 * age) - 161;
        //  double DCI = Math.round(res) + 300; // THIS IS THE DAILY CALORIE INTAKE.

        String gender = instanceUser.getGender();
        String height = instanceUser.getHeightInCM();
        int age = instanceUser.getAge();   //could be -1, if not found in profile
        
        if(gender==null || gender.equals("")){
             makeAlert("Please update Gender in Profile to display Calorie Target");
            return;
        }
        
        if(height.equals("")){
            makeAlert("Please update Height in Profile to display Calorie Target");
            return;
        }
        //conver height to cm
       
        if(age ==-1){
            makeAlert("Please update Date of Birth in Profile to display Calorie Target");
            return;
        }
        
        String a = String.valueOf(age);
        String weight = pc.getLastWeight(instanceUser.getUserName());
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
        labelCalorieTarget.setText("Daily Calorie Target = "+result);
        
        
    }
    
    
}
