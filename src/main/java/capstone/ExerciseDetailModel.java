/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capstone;

/**
 *
 * @author jesus
 */
public class ExerciseDetailModel {
    
    public String name = "";
    public String gifURL = "";
    public String bodyPart ="";
    public String targetedMuscle="";
    public String equipment="";
            
    public static ExerciseDetailModel instance = null;
  
    public String getName(){ return name;}
    
    public static ExerciseDetailModel getInstance(){
    if(instance == null){
    instance = new ExerciseDetailModel();
    }
    return instance;
        
    }
    
    public void resetModel(){
    name="";
    gifURL = "";
    bodyPart ="";
    targetedMuscle="";
    equipment="";
    }
    
    
}
