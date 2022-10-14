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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 *
 * @author Omar Muy
 */
public class waterCalculator implements Initializable {

    
    @FXML 
    private TextField  watertf;
    @FXML 
    private Text tresponse, cupT;
    @FXML
    private Button cb1, wcb; 
    @FXML 
    private RadioButton rb1, rb2;
    @FXML
    private ChoiceBox<String> choicebox1;
    
    private String[] activity = {"30 minutes", "45 minutes", "1 hour", "1 1/2 hours", "2 hours"};
    
    @FXML
    private Label l;
    
    private int weight;
    private int oz = 12; //Each half hour. 
    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("Landing");
    }
    @FXML
    private void switchToHome() throws IOException {
        App.setRoot("UserHome");
    }
    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("Calculators");
    }
    // Water Switch
    @FXML
    private void waterCalc() throws IOException{
        App.setRoot("waterCalc");
    }
    
    // Functionality 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        choicebox1.getItems().addAll(activity);     
        //choicebox1.setOnAction(this::getActivity);
    }
  
    @FXML 
    private void waterButtonFunction() throws IOException{
        // LBS with choicebox1 not having a value
        if(watertf != null && rb1.isSelected() && choicebox1.getValue() == null) {
            //Convert string textfield into int and then do calculations...
            System.out.println("You'd get a more accurate measurement if you include activity time\n");
            weight = Integer.parseInt(watertf.getText()); // This has the # in lbs
            long l = Math.round(weight * .75); //Round it to nearest Oz. 
            Alert a = new Alert(Alert.AlertType.INFORMATION); //Informative
            a.setContentText("You'd get a more accurate measurement if you include activity time");
            a.showAndWait();
            
            tresponse.setText(" You need about " + l + "oz. daily\n" );
            cupT.setText("This is equivalent to roughly " + l/8 + " cups\n");
        }
        else if(watertf != null && rb1.isSelected() && choicebox1.getValue() != null){
            // LBS but choicebox is selected, go through options now...
            if(choicebox1.getValue() == "30 minutes"){
                // Each 30 minutes add 12 ozs. 
                weight = Integer.parseInt(watertf.getText());
                long l = Math.round(weight * .75) + oz; // round to nearest Oz.
                tresponse.setText("You need about " + l + "oz. daily\n");
                cupT.setText("This is equivalent to roughly " + l/8 + " cups\n");
            }
            else if (choicebox1.getValue() == "45 minutes"){
                // Each 30 minutes add 12 ozs. 
                weight = Integer.parseInt(watertf.getText());
                long l = Math.round(weight * .75 + oz*1.5) ; // round to nearest Oz.
                tresponse.setText("You need about " + l + "oz. daily\n");
                cupT.setText("This is equivalent to roughly " + l/8 + " cups\n");
            }
            else if (choicebox1.getValue() == "1 hour"){
                // Each 30 minutes add 12 ozs. 
                weight = Integer.parseInt(watertf.getText());
                long l = Math.round(weight * .75) + oz*2; // round to nearest Oz.
                tresponse.setText("You need about " + l + "oz. daily\n");
                cupT.setText("This is equivalent to roughly " + l/8 + " cups\n");
            }
            else if (choicebox1.getValue() == "1 1/2 hours"){
                // Each 30 minutes add 12 ozs. 
                weight = Integer.parseInt(watertf.getText());
                long l = Math.round(weight * .75) + oz*3; // round to nearest Oz.
                tresponse.setText("You need about " + l + "oz. daily\n");
                cupT.setText("This is equivalent to roughly " + l/8 + " cups\n");
            }
            else if (choicebox1.getValue() == "2 hours"){
                // Each 30 minutes add 12 ozs. 
                weight = Integer.parseInt(watertf.getText());
                long l = Math.round(weight * .75) + oz*4; // round to nearest Oz.
                tresponse.setText("You need about " + l + "oz. daily\n");
                cupT.setText("This is equivalent to roughly " + l/8 + " cups\n");
            }
        }
        
        // KGs 
        else if (watertf != null && rb2.isSelected() && choicebox1.getValue() == null){
            weight = Integer.parseInt(watertf.getText()); // This has the # in KG
            long l = Math.round(weight * 2.2); // converts to LBS
            long m = Math.round(l * .75); // Converts to OZ. 
            
            Alert a = new Alert(Alert.AlertType.INFORMATION); //Informative
            a.setContentText("You'd get a more accurate measurement if you include activity time");
            a.showAndWait();
            tresponse.setText("You need about " + m + "oz. daily\n");
            cupT.setText("This is equivalent to roughly " + m/8 + " cups\n");
            
        }
        // KG's but choicebox is selected.
        else if(watertf != null && rb2.isSelected() && choicebox1.getValue() != null){
            if(choicebox1.getValue() == "30 minutes"){
                weight = Integer.parseInt(watertf.getText()); // This has the # in KG
                long l = Math.round(weight * 2.2); // converts to LBS
                long m = Math.round(l * .75) + oz; // Converts to OZ. 
                tresponse.setText("You need about " + m + "oz. daily\n");
                cupT.setText("This is equivalent to roughly " + m/8 + " cups\n");
            }
            else if(choicebox1.getValue() == "45 minutes"){
                weight = Integer.parseInt(watertf.getText()); // This has the # in KG
                long l = Math.round(weight * 2.2); // converts to LBS
                long m = Math.round(l * .75 + oz*1.5); // Converts to OZ. 
                tresponse.setText("You need about " + m + "oz. daily\n");
                cupT.setText("This is equivalent to roughly " + m/8 + " cups\n");
            }
            else if(choicebox1.getValue() == "1 hour"){
                weight = Integer.parseInt(watertf.getText()); // This has the # in KG
                long l = Math.round(weight * 2.2); // converts to LBS
                long m = Math.round(l * .75) + oz*2; // Converts to OZ.
                tresponse.setText("You need about " + m + "oz. daily\n");
                cupT.setText("This is equivalent to roughly " + m/8 + " cups\n");
            }
            else if(choicebox1.getValue() == "1 1/2 hours"){
                weight = Integer.parseInt(watertf.getText()); // This has the # in KG
                long l = Math.round(weight * 2.2); // converts to LBS
                long m = Math.round(l * .75) + oz*3; // Converts to OZ. 
                tresponse.setText("You need about " + m + "oz. daily\n");
                cupT.setText("This is equivalent to roughly " + m/8 + " cups\n");
            }
            else if(choicebox1.getValue() == "2 hours"){
                weight = Integer.parseInt(watertf.getText()); // This has the # in KG
                long l = Math.round(weight * 2.2); // converts to LBS
                long m = Math.round(l * .75) + oz*4; // Converts to OZ. 
                tresponse.setText("You need about " + m + "oz. daily\n");
                cupT.setText("This is equivalent to roughly " + m/8 + " cups\n");
            }         
        }
        else{
            System.out.println("Please enter a weight & unit of measure\n");
        }
    }
    
    @FXML // Resets all calculations for Water Calc. 
    private void resetWaterCalc(){
        rb1.setSelected(false);
        rb2.setSelected(false);
        watertf.setText("");
        //choicebox1.setValue("");
        choicebox1.getSelectionModel().clearSelection();
    }
    
}
