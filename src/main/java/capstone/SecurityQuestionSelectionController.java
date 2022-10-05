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
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author jesus
 */
public class SecurityQuestionSelectionController implements Initializable {

    @FXML
    private Button btnOkay;

    @FXML
    private ChoiceBox<String> choiceBoxQ1;
    @FXML
    private ChoiceBox<String> choiceBoxQ2;
    @FXML
    private ChoiceBox<String> choiceBoxQ3;

    @FXML
    private TextField textAnswerQ1;
    @FXML
    private TextField textAnswerQ2;
    @FXML
    private TextField textAnswerQ3;
    
    @FXML
    private Label labelErrorMsg;
    
    ArrayList<String> questionList = new ArrayList<>();
    ArrayList<Integer> questionListIds = new ArrayList<>();
    ArrayList<Integer> selectedQIds = new ArrayList<>();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        selectedQIds.add(-1);
        selectedQIds.add(-1);
        selectedQIds.add(-1);
      
        loadChoiceBox();
        choiceBoxQ1.setItems(FXCollections.observableArrayList(questionList));
        choiceBoxQ1.getSelectionModel().selectedIndexProperty()
                .addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue ov, Number value, Number new_value) { 
                        selectedQIds.set(0, questionListIds.get(new_value.intValue()) );
                    }
                });
        choiceBoxQ2.setItems(FXCollections.observableArrayList(questionList));
        choiceBoxQ2.getSelectionModel().selectedIndexProperty()
                .addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue ov, Number value, Number new_value) {
                        selectedQIds.set(1, questionListIds.get(new_value.intValue()));
                    }
                });
        choiceBoxQ3.setItems(FXCollections.observableArrayList(questionList));
        choiceBoxQ3.getSelectionModel().selectedIndexProperty()
                .addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue ov, Number value, Number new_value) {
                        selectedQIds.set(2, questionListIds.get(new_value.intValue()));
                    }
                });
    }

    @FXML
    private void loadChoiceBox() {
        ResultSet allSecQs = dataConnector.getInstance().getAllSecQs();

        try {
            while (allSecQs.next()) {
                questionList.add(allSecQs.getString("SecQ_desc"));
                questionListIds.add(allSecQs.getInt("SecQ_id"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(SecurityQuestionSelectionController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void onOkayPressed() throws IOException {
        
        String userName = UserProfileModel.getInstance().getUserName();
        System.out.println("username from SecQsSelection = "+ userName);
        boolean allQuestionsChosen = true;
        for(int i=0; i<selectedQIds.size(); i++){
            if(selectedQIds.get(i) < 0)
            {
                allQuestionsChosen = false;
            }
            System.out.println("id value= "+selectedQIds.get(i));
               
        }
       
        if ( allQuestionsChosen && !textAnswerQ1.getText().equals("") && !textAnswerQ2.getText().equals("") && !textAnswerQ3.getText().equals("")) {

            dataConnector.getInstance().updateColumn("User_Profile", userName, textAnswerQ1.getText(), DBCol.SecurityA1.toString());
            dataConnector.getInstance().updateUserSecQID(userName, selectedQIds.get(0), DBCol.SecurityQ1_id.toString());

            dataConnector.getInstance().updateColumn("User_Profile", userName, textAnswerQ2.getText(), DBCol.SecurityA2.toString());
            dataConnector.getInstance().updateUserSecQID(userName, selectedQIds.get(1), DBCol.SecurityQ2_id.toString());

            dataConnector.getInstance().updateColumn("User_Profile", userName, textAnswerQ3.getText(), DBCol.SecurityA3.toString());
            dataConnector.getInstance().updateUserSecQID(userName, selectedQIds.get(2), DBCol.SecurityQ3_id.toString());
 
            App.setRoot("login");
        } else {
                labelErrorMsg.setText("Must Select and Answer 3 Security Questions");   
        }

    }

}
