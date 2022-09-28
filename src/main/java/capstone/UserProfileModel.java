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
public class UserProfileModel {
    
    private static UserProfileModel instance = null;
    
    private String fullName;
    private String userName;
    private String password;
    
    public static UserProfileModel getInstance(){
        if(instance == null)
            instance = new UserProfileModel();
        
        return instance;
    }
    
    public String getFullName(){
        return fullName;
    }
    public String getUserName(){
        return userName;
    }
    public String getPassword(){
        return password;
    }
    
    public void setFullName(String name){
        fullName = name;
    }
    public void setUserName(String name){
        userName = name;
    }
    public void setPassword(String pw){
        password = pw;
    }
    
    public void resetModel(){
        instance = null;
    }
    
    
  
    
    
}
