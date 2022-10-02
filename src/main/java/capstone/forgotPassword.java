package capstone;

import java.io.IOException;
<<<<<<< HEAD
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
=======
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
>>>>>>> NicksBranch
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * Forgot Password Class
 *
 * Driver class for forgot password screen
 *
 * @author Wahab Quazi Nick Andrle
 */
public class forgotPassword {

    dataConnector theDB = null;
    

    @FXML
    private Text secQues1;
    @FXML
    private Text secQues2;
    @FXML
    private Text secQues3;
    @FXML
    private TextField secAns1;
    @FXML
    private TextField secAns2;
    @FXML
    private TextField secAns3;
    @FXML
    private Button cancelButton;
    @FXML
    private Button validateQuestions;
    @FXML
    private TextField userName;

    @FXML
    public void initialize() {

        theDB = dataConnector.getInstance();
        loadScene();
        //theModel = UserProfileModel.getInstance();
        //secQues1.setText(theDB.getSecq());
        //secQues2.setText(theDB.getSecq(1));
       // secQues3.setText(theDB.getSecq(1));
    }
    
    @FXML
    public void loadScene(){
        UserProfileModel theModel = UserProfileModel.getInstance();
        ResultSet result = theDB.getResult(theModel.getUserName(), "User_Profile");
        int id1 = 0;
        int id2 = 0;
        int id3 = 0;
     
        try {
            while (result.next()) {
                id1 = result.getInt("SecurityQ1_id");
                id2 = result.getInt("SecurityQ2_id");
                id3 = result.getInt("SecurityQ3_id");
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(forgotPassword.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (id1 != 0) {
            secQues1.setText(theDB.getSecq(id1));
        }
        if (id2 != 0) {
            secQues2.setText(theDB.getSecq(id2));
        }
        if (id3 != 0) {
            secQues3.setText(theDB.getSecq(id3));
        }

        
    }

    /**
     * Validate Security Questions
     *
     * Checks the 3 security questions against the database
     *
     * @return True if all 3 questions match False if any question is incorrect
     */
    @FXML
    private boolean validateQuestions() throws SQLException {

        String usernameEntered = userName.getText();

        String userAns1 = secAns1.getText();
        String userAns2 = secAns2.getText();
        String userAns3 = secAns3.getText();

        String secAnswer1 = "";
        String secAnswer2 = "";
        String secAnswer3 = "";

        Connection c = connectToDatabase();

        String tableName = "Accounts";
        String sql = "select * from " + tableName + " WHERE UserName = ?";

        PreparedStatement psmt = c.prepareStatement(sql);
        psmt.setString(1, usernameEntered);

        ResultSet r = psmt.executeQuery();

        while (r.next()) {
            secAnswer1 = r.getString("secAns1");
            secAnswer2 = r.getString("secAns2");
            secAnswer3 = r.getString("secAns3");
        }
        System.out.println(usernameEntered);
        if (validateUsername(usernameEntered)) {

            if (userAns1.equals(secAnswer1) && userAns2.equals(secAnswer2) && userAns3.equals(secAnswer3)) {
                System.out.println("true");
            } 
            
        } else {
                Alert a = new Alert(AlertType.ERROR);
                a.setTitle("Error");
                a.setHeaderText("The User Name you entered is incorrect \n please try again");
                a.showAndWait();
            }
        //TODO: CONNECT TO DATABASE AND VALIDATE
        return false;
    }

    /**
<<<<<<< HEAD
=======
     * method that will return true or false if the user name is not in the
     * database, accepts the username as a parameter
     *
     * @param str
     * @return
     */
    public boolean validateUsername(String str) throws SQLException {

        Connection c = connectToDatabase();

        String tableName = "Accounts";
        String sql = "select UserName from " + tableName + " WHERE UserName = ?";

        PreparedStatement psmt = c.prepareStatement(sql);
        psmt.setString(1, str);

        ResultSet r = psmt.executeQuery();

        return r != null;

    }

    /**
>>>>>>> NicksBranch
     * Go back Function
     *
     * Get us back to the login screen
     *
     * @throws IOException
     */
    private void goBack() throws IOException {
        App.setRoot("login");
    }

    /**
     * Connect to Database
     *
     * Connects to the database and returns connection
     */
<<<<<<< HEAD
    private void connectToDatabase() {
        //TODO: REPLACE VOID WITH CONNECTION
=======
    //TODO: REPLACE VOID WITH CONNECTION
    private Connection connectToDatabase() {

        dataConnector dc = new dataConnector();

        Connection c = dc.getConnectionDBoutside();

        return c;

>>>>>>> NicksBranch
        //TODO: CONNECT TO DATABASE PROPERLY
    }
}
