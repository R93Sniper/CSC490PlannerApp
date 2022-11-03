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
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Omar Muy
 */
public class goalCard {
    
    @FXML
    private AnchorPane weightA, sizeA, strengthA;
    @FXML
    private Label tdLabel;
    @FXML
    private DatePicker targetDate;
    @FXML
    private RadioButton weightRB, sizeRB, strengthRB;
    
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
}
