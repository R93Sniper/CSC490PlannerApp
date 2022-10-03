package capstone;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.text.Text;

public class SecondaryController {
    @FXML 
    private ToggleButton mb1, fb1, kgtb, lbtb, itb, cmtb;
    @FXML 
    private TextField tf1, hf1, af1;
    @FXML 
    private Text tr1;
    @FXML
    private Button cb1; 
    
    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
    @FXML
    private void switchToHome() throws IOException {
        App.setRoot("home");
    }
    
    @FXML
    private void calculateCaloriesButton() throws IOException {
        
        if(mb1.isSelected() ){
            System.out.println("Male was selected");
            if(af1.getText() != null){
                // Make sure it's not null. then convert 
                int age = Integer.parseInt(af1.getText());
                System.out.println("age isn't null: " + age);
                if(age != 0 && age < 100 ){ 
                    //System.out.println("\nyour age is" + age);
                    if(tf1.getText() != null){
                        
                        // Make sure weight isn't null then convert
                        int weight = Integer.parseInt(tf1.getText());
                        System.out.println("weight isn't null: " + weight);
                        //System.out.println("\nWeight is this: " + weight);
                        // Now to make weight making sure it's lbs or kg
                        if(weight > 0 && kgtb.isSelected()){
                            // KG SELECTION
                            if(hf1.getText() != null){
                                
                                int height = Integer.parseInt(hf1.getText());
                                System.out.println("height isn't null" + height);
                              
                                if(height > 0 && cmtb.isSelected()){
                                    //this happens if it's both KG and CM and it's a male and age is given......
                                    double res = 10*weight +6.25*height-5*age+5;
                                    //System.out.printf("%2.2", res);
                                    String result = Double.toString(res);
                                    tr1.setText("\n" + result + " calories needed"); 
                                    System.out.println(res + " calories");
                                }
                                else if(height > 0 && itb.isSelected()){
                                    // This scenario happens when its KG but inches is used and its a male and age is given....
                                    double nheight = height*2.54;
                                    // Now to calculate since it's been converted
                                    double res = 10*weight + 6.25*nheight-5*age+5;
                                    System.out.println(res + " calories");
                                    
                                    String result = Double.toString(res);
                                    tr1.setText("\n" + result + " calories needed");
                                    
                                }
                                else{
                                    System.out.println("Height error");
                                }
                            }
                            else{
                                System.out.println("\nHeight must be included please.");
                            }
                        }
                        // THIS IS STILL MENS CALCULATIONS JUST USING LBS RATHER THAN KG. 
                        else if(weight > 0 && lbtb.isSelected()){
                            // LBS SELECTION
                            double nweight = weight / 2.205; // THIS IS THE CONVERTED WEIGHT
                            if(hf1.getText() != null){
                                int height = Integer.parseInt(hf1.getText());
                                System.out.println("\nHeight is: " + height);
                                if(height > 0 && cmtb.isSelected()){
                                    //this happens if it's LBs and CM and it's a male and age is given......
                                    double res = 10*nweight +6.25*height-5*age+5;
                                    System.out.println(res + " calories");
                                    //System.out.printf("%2.2", res);
                                    String result = Double.toString(res);
                                    tr1.setText("\n" + result + " calories needed"); 
                       
                                }
                                else if(height > 0 && itb.isSelected()){
                                    // This scenario happens when its BOTH KG and INCHES are used and its a male and age is given....
                                    double nheight = height*2.54;
                                    // Now to calculate since it's been converted
                                    double res = 10*nweight + 6.25*nheight-5*age+5;
                                    System.out.println(res + " calories");
                                    //System.out.printf("%2.2", res);
                                    String result = Double.toString(res);
                                    tr1.setText("\n" + result + " calories needed");
                                   
                                }
                                else{
                                    System.out.println("Height error");
                                }
                            }
                        }
                        else {
                            System.out.println("Weight cannot be under 0");
                        }
                    }
                }
            }
            else {
                System.out.println("\nPlease enter your age");
            }
        }
        // This happens if FEMALE togglebutton is on
        else if(fb1.isSelected()){
            if(af1.getText() != null){
                // Make sure it's not null. then convert 
                int age = Integer.parseInt(af1.getText());
                if(age != 0 && age < 100){
                    System.out.println("\nyour age is" + age);
                    if(tf1.getText() != null){
                        // Make sure weight isn't null then convert
                        int weight = Integer.parseInt(tf1.getText());
                        System.out.println("\nWeight is this: " + weight);
                        // Now to make weight making sure it's lbs or kg
                        if(weight > 0 && kgtb.isSelected()){
                            if(hf1.getText() != null){
                                int height = Integer.parseInt(hf1.getText());
                                System.out.println("\nHeight is: " + height);
                                if(height > 0 && cmtb.isSelected()){
                                    //this happens if it's both KG and CM and it's a FEMALE and age is given......
                                    double res = 10*weight +6.25*height-5*age-161;
                                    //System.out.printf("%2.2", res);
                                    String result = Double.toString(res);
                                    tr1.setText("\n" + result + " calories needed"); 
                                    System.out.println(res + " calories");
                                }
                                else if(height > 0 && itb.isSelected()){
                                    // This scenario happens when its KG but inches is used and its a FEMALE and age is given....
                                    double nheight = height*2.54;
                                    // Now to calculate since it's been converted
                                    double res = 10*weight + 6.25*nheight-5*age-161;
                                    //System.out.printf("%2.2", res);
                                    String result = Double.toString(res);
                                    tr1.setText("\n" + result + " calories needed");
                                    System.out.println(res + " calories");
                                }
                                else{
                                    System.out.println("Height error");
                                }
                            }
                            else{
                                System.out.println("\nHeight must be included please.");
                            }
                        }
                        // THIS IS STILL MENS CALCULATIONS JUST USING LBS RATHER THAN KG. 
                        else if(weight > 0 && lbtb.isSelected()){
                            double nweight = weight / 2.205; // THIS IS THE CONVERTED WEIGHT
                            if(hf1.getText() != null){
                                int height = Integer.parseInt(hf1.getText());
                                System.out.println("\nHeight is: " + height);
                                if(height > 0 && cmtb.isSelected()){
                                    //this happens if it's LBs and CM and it's a male and age is given......
                                    double res = 10*nweight +6.25*height-5*age-161;
                                    //System.out.printf("%2.2", res);
                                    String result = Double.toString(res);
                                    tr1.setText("\n" + result + " calories needed"); 
                                    System.out.println(res + " calories");
                                }
                                else if(height > 0 && itb.isSelected()){
                                    // This scenario happens when its BOTH KG and INCHES are used and its a male and age is given....
                                    double nheight = height*2.54;
                                    // Now to calculate since it's been converted
                                    double res = 10*nweight + 6.25*nheight-5*age-161;
                                    //System.out.printf("%2.2", res);
                                    String result = Double.toString(res);
                                    tr1.setText("\n" + result + " calories needed");
                                    System.out.println(res + " calories");
                                }
                                else{
                                    System.out.println("Height error");
                                }
                            }
                        }
                        else {
                            System.out.println("Weight cannot be under 0");
                        }
                    }
                }
            }
            else {
                System.out.println("\nPlease enter your age");
            }
        }
        else if (!fb1.isSelected() && !mb1.isSelected()){
            System.out.println("\nPlease choose a gender");
            
        }
    }
    @FXML 
    private void reCalculateButton(){
        //Reset everything
        mb1.setSelected(false);
        fb1.setSelected(false);
        kgtb.setSelected(false);
        lbtb.setSelected(false);
        itb.setSelected(false);
        cmtb.setSelected(false);
        
        tf1.clear();
        af1.clear();
        hf1.clear();
        tr1.setText("");
        System.out.println("ToggleButtons have been reset");
        
    }
}