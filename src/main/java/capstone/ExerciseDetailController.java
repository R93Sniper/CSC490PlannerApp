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
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author jesus
 */
public class ExerciseDetailController{
    @FXML
    private Label labelTarget;
    @FXML
    private Label labelEquipment;
    @FXML
    private Label labelBodyPart;
         
    @FXML
    private Label labelName;
    @FXML
    private ImageView imgExercise;
    ExerciseDetailModel theModel = ExerciseDetailModel.getInstance();
    
    
    @FXML
    public void initialize(){
    labelName.setText(theModel.name.toUpperCase());
    labelBodyPart.setText("Body Part: "+theModel.bodyPart);
    labelEquipment.setText("Equipment: "+theModel.equipment);
    labelTarget.setText("Muscle Targeted: "+ theModel.targetedMuscle);

    Image img = new Image(theModel.gifURL);
    imgExercise.setImage(img);
    }
  
    @FXML
    public void onGoBack() throws IOException{
       // theMod
        App.setRoot("ExerciseLookUp");
    }
    
}
