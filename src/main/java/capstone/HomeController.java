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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author jesus
 */
public class HomeController implements Initializable {

    private UserProfileModel instanceUser;
    dataConnector userDB = null;
    private ResultSet result;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadProfile();
        // TODO
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
    private void loadProfile() {
        //returns resultset matching the given username
        instanceUser = UserProfileModel.getInstance();
        userDB = dataConnector.getInstance();
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
            Logger.getLogger(UserProfileController.class.getName()).log(Level.SEVERE, null, ex);
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

}
