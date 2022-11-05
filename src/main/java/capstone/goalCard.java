/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capstone;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Omar Muy
 */
public class goalCard {
    
    @FXML
    private AnchorPane weightA, sizeA, strengthA;
    @FXML
    private Label tdLabel, L2;
    @FXML
    private DatePicker targetDate;
    @FXML
    private RadioButton weightRB, sizeRB, strengthRB, rbGain, rbLose, rbMain, metricRB, usRB;
    
    @FXML
    private TextField targetTF, currentTF, armsTF, waistTF, hipsTF, neckTF, legsTF, benchTF, deadliftTF, squatsTF, legpressTF, spressTF;
    @FXML
    private void goBack() throws IOException{
        App.setRoot("UserHome");
        
    }
    
    @FXML
    private void chosenWeight(){
        //If Radio Button for weight goal was chosen
        tdLabel.visibleProperty().set(true);
        targetDate.visibleProperty().set(true);
        targetDate.setDisable(false);
        // now for weight anchorpane to be visible + not disabled
        weightA.setVisible(true);
        weightA.setDisable(false);
        
        L2.setVisible(true);
        L2.setDisable(false);
        metricRB.setDisable(false);
        usRB.setDisable(false);
        metricRB.setVisible(true);
        usRB.setVisible(true);
        //Now to deselect any other choice. + Clear their fields/anchors
        sizeA.setVisible(false);
        sizeA.setDisable(true);
        sizeRB.setSelected(false);
        strengthA.setVisible(false);
        strengthA.setDisable(true);
        strengthRB.setSelected(false);
    }
    @FXML
    private void chosenSize(){
        //Size goal was chosen
        L2.setVisible(true);
        L2.setDisable(false);
        metricRB.setDisable(false);
        usRB.setDisable(false);
        metricRB.setVisible(true);
        usRB.setVisible(true);
        
        tdLabel.visibleProperty().set(true);
        targetDate.visibleProperty().set(true);
        targetDate.setDisable(false);
        // Size anchor
        sizeA.setVisible(true);
        sizeA.setDisable(false);
        
        //Now to deselect any other choice. + Clear their fields/anchors
        weightA.setVisible(false);
        weightA.setDisable(true);
        weightRB.setSelected(false);
        strengthA.setVisible(false);
        strengthA.setDisable(true);
        strengthRB.setSelected(false);
        
    }
    @FXML
    private void chosenStrength(){
        L2.setVisible(true);
        L2.setDisable(false);
        metricRB.setDisable(false);
        usRB.setDisable(false);
        metricRB.setVisible(true);
        usRB.setVisible(true);
        
        // Strength was chosen
        tdLabel.visibleProperty().set(true);
        targetDate.visibleProperty().set(true);
        targetDate.setDisable(false);
        // Strength goal
        strengthA.setVisible(true);
        strengthA.setDisable(false);
        
        //Now to deselect any other choice. + Clear their fields/anchors
        sizeA.setVisible(false);
        sizeA.setDisable(true);
        sizeRB.setSelected(false);
        weightA.setVisible(false);
        weightA.setDisable(true);
        weightRB.setSelected(false);
    }
    @FXML
    private void whichToSave(){
        if(weightRB.isSelected()){
            runWeight();
        }
        else if(sizeRB.isSelected()){
            runSize();
        }
        else if(strengthRB.isSelected()){
            runStrength();
        }
        else {
            System.out.println("Choose a goal");
        }
    }
    private void runWeight(){
         if(rbGain.isSelected()){
             runGains();
         }
         else if (rbLose.isSelected()){
             runLoss();
         }
         else if(rbMain.isSelected()){
             maintain();
         }
         else {
             System.out.println("Please choose a weight goal");
         }
    }
    
    private void runGains(){
        if(currentTF.getText().isEmpty()){
            //Error
            System.out.println("Error on current TF");
        }
        else {
            if (targetTF.getText().isEmpty()){
                //Error
                System.out.println("Error on TargetTF");
            }
            else {
                // Compare current and target weight
                
            }
        }
    }
    private void runLoss(){
        if(currentTF.getText().isEmpty()){
            //Error
            System.out.println("Error on current TF");
        }
        else {
            if (targetTF.getText().isEmpty()){
                //Error
                System.out.println("Error on TargetTF");
            }
            else {
                // Compare current and target weight
                
            }
        }
    }
    private void maintain(){
        // current and target must be the same
        if(currentTF.getText().isEmpty()){
            //Error
            System.out.println("Error on current TF");
        }
        else {
            if (targetTF.getText().isEmpty()){
                //Error
                System.out.println("Error on TargetTF");
            }
            else {
                // Compare current and target weight
                int x = Integer.parseInt(currentTF.getText());
                int y = Integer.parseInt(targetTF.getText());
                // Add datepicker aspect
            }
        }
    }
    
    private void runSize(){
        if(metricRB.isSelected()){
            if(!neckTF.getText().isEmpty())
            {
                
            }
            else { }
        }
        else if(usRB.isSelected()){
            
        }
        else {
            System.out.println("Choose a unit of measure");
        }
    }
    private void runStrength(){
            int r1 = Integer.parseInt(benchTF.getText());
            int r2 = Integer.parseInt(deadliftTF.getText());
            int r3 = Integer.parseInt(squatsTF.getText());
            int r4 = Integer.parseInt(legpressTF.getText());
            int r5 = Integer.parseInt(spressTF.getText());
        
    }
    
    private void saveGoal(){ 
        // This should save all data from specific goal cards to DB
    }
}
