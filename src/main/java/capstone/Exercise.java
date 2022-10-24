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
public class Exercise {
    
    private String id="";
    private String name="";
    private String bodyPart="";
    private String equipment="";
    private String gifURL="";
    private String target="";
    
    
    public String getId(){ return id;}
    public String getName(){ return  name;}
    public String getBodyPart(){ return bodyPart;}
    public String getEquipment(){return equipment;}
    public String getGifURL(){ return gifURL;}
    public String getTarget(){ return target;}
    
    public void setId(String x){ id = x;}
    public void setName(String x){ name = x;}
    public void setBodyPart(String x){ bodyPart = x;}
    public void setEquipment(String x){ equipment = x;}
    public void setGifURL(String x){ gifURL = x;}
    public void setTarget(String x){ target = x;}
    
    
    public String toString(){
    return "name= "+name+"   ,body part= "+bodyPart;
    }
    
}
