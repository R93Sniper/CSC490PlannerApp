package capstone;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
    //TODO: REPLACE VOID WITH CONNECTION
    private Connection connectToDatabase() {

        dataConnector dc = new dataConnector();

        Connection c = dc.getConnectionDBoutside();

        return c;

        //TODO: CONNECT TO DATABASE PROPERLY
    }
}
