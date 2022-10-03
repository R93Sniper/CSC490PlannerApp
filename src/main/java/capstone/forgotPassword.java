package capstone;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    
    @FXML
    private void goBack() throws IOException {
        App.setRoot("login");
    }

   
}
