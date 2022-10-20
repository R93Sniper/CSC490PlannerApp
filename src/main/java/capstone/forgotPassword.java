package capstone;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;


import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

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

    dataConnector theDB = dataConnector.getInstance();
    UserProfileModel theModel = UserProfileModel.getInstance();

    @FXML
    private Label secQues1;
    @FXML
    private Label secQues2;
    @FXML
    private Label secQues3;
    @FXML
    private TextField secAns1;
    @FXML
    private TextField secAns2;
    @FXML
    private TextField secAns3;
    @FXML
    private Button btnOk;
    @FXML
    private Button btnOk2;
    @FXML
    private TextField textNewPassword;
    @FXML
    private Label labelErrorMsg;
    @FXML
    private Label labelEnterPW;
    
    String [] answerArray = new String[3];

    @FXML
    public void initialize() {
        loadScene();

    }
    
    @FXML
    public void loadScene(){
       // UserProfileModel theModel = UserProfileModel.getInstance();
        ResultSet result = theDB.getResult(theModel.getUserName(), "User_Profile");
        int id1 = -1;
        int id2 = -1;
        int id3 = -1;
     
        try {
            while (result.next()) {
                id1 = result.getInt("SecurityQ1_id");
                id2 = result.getInt("SecurityQ2_id");
                id3 = result.getInt("SecurityQ3_id");
                answerArray[0] = result.getString("SecurityA1");
                answerArray[1]= result.getString("SecurityA2");
                answerArray[2] = result.getString("SecurityA3");
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(forgotPassword.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("Q1 = "+id1+" , Q2 = "+id2+" , Q3 = "+ id3);
        String str1 = theDB.getSecq(id1);
        String str2 = theDB.getSecq(id2);
        String str3 = theDB.getSecq(id3);
        
        System.out.println("questions= "+str1+" , "+str2+" , "+str3+" , ");
                

        if (id1 != -1) {
            secQues1.setText(theDB.getSecq(id1));
        }
        if (id2 != -1) {
            secQues2.setText(theDB.getSecq(id2));
        }
        if (id3 != -1) {
            secQues3.setText(theDB.getSecq(id3));
        }

        
    }

      
    @FXML
    private void validateSecAnswers() throws IOException {
        
        boolean check1 = answerArray[0].equals(secAns1.getText());
        boolean check2 = answerArray[1].equals(secAns2.getText());
        boolean check3 = answerArray[2].equals(secAns3.getText());
        //validate answers
        if(!check1){
            secAns1.setText("Wrong Answer");
        }
        if(!check2){
            secAns2.setText("Wrong Answer");
        }
        if(!check3){
            secAns3.setText("Wrong Answer");
        }
        if(check1 && check2 && check3){
            labelEnterPW.setVisible(true);
            btnOk.setDisable(true);
            btnOk2.setVisible(true);
            textNewPassword.setVisible(true);
        }
        //App.setRoot("login");
    }
    
    @FXML
    private void saveNewPassword() throws IOException, NoSuchAlgorithmException {
    
        String str = theDB.returnHashPassword(textNewPassword.getText());
        //check if its a valid password, then save it in the DB
        //DBColumn d;
<<<<<<< HEAD
        theDB.updateColumn("User_Profile", theModel.getUserName(), str, DBCol.User_Password.toString());
        App.setRoot("LoginScreen");
=======
        theDB.updateColumn("User_Profile", theModel.getUserName(), str, DB_Col.User_Password.toString());
        App.setRoot("login");
>>>>>>> origin/Sprint2-DietApi
    }

   
}
