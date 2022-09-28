/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capstone;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author jesus
 */
public class CreateAccountController{

   UserProfileDataConnector userDB = null;
    
   @FXML
   private TextField textUserName;
   @FXML
   private TextField textPassword;
   @FXML
   private TextField textFullName;
    
    
    
    /**
     * Initializes the controller class.
     */
    public void initialize() {
    userDB = UserProfileDataConnector.getInstance();
    // TODO
    }    
    
    @FXML
    public void createAccount()throws IOException{
  System.out.println("do... hello world.."+ textUserName.getText()+" : " 
            +" : "+textPassword.getText()
            +" : "+ textFullName.getText());
        
    userDB.insertAccountToDB(textUserName.getText(), textPassword.getText() , textFullName.getText());
   
    }
    
}
