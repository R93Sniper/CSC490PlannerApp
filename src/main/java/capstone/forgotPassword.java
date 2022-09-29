package capstone;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * Forgot Password Class
 *
 * Driver class for forgot password screen
 *
 * @author Wahab Quazi
 *         Nick Andrle
 */
public class forgotPassword {

    @FXML
    private Text secQues1;
    private Text secQues2;
    private Text secQues3;
    private TextField secAns1;
    private TextField secAns2;
    private TextField secAns3;

    /**
     * Validate Security Questions
     * 
     * Checks the 3 security questions against the database
     * @return True if all 3 questions match
     *         False if any question is incorrect
     */
    @FXML
    private boolean validateQuestions() {
        //TODO: CONNECT TO DATABASE AND VALIDATE
        return false;
    }

    /**
     * Go back Function
     * 
     * Get us back to the login screen
     * @throws IOException 
     */
    @FXML
    private void goBack() throws IOException {
        App.setRoot("login");
    }

    
    /**
     * Connect to Database
     * 
     * Connects to the database and returns connection
     */
    private void connectToDatabase(){
    //TODO: REPLACE VOID WITH CONNECTION
    //TODO: CONNECT TO DATABASE PROPERLY
    }
}
