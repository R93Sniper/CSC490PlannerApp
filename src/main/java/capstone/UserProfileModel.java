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

    private String fullName="";
    private String firstName="";
    private String lastName="";
    private String userName="";
    private String password="";
    private String email="";
    private String phoneNum="";
    private String address="";
    private String gender="";
    private String height="";

    public static UserProfileModel getInstance() {
        if (instance == null) {
            instance = new UserProfileModel();
        }

        return instance;
    }

    public String getFullName() {
        return fullName;
    }
    public String getFirstName() {
        return firstName;
    }public String getLastName() {
        return lastName;
    }
    public String getUserName() {
        return userName;
    }
    public String getPassword() {
        return password;
    }
    public String getEmail() {
        return email;
    }
    public String getPhoneNum() {
        return phoneNum;
    }
    public String getAddress() {
        return address;
    }
    public String getGender() {
        return gender;
    }
    public String getHeight() {
        return height;
    }

    public void setFullName(String name) {
        fullName = name;
    }
    public void setFirstName(String name) {
        firstName = name;
    }
    public void setLastName(String name) {
        lastName = name;
    }
    public void setUserName(String name) {
        userName = name;
    }
    public void setPassword(String pw) {
        password = pw;
    }
    public void setEmail(String e) {
        email = e;
    }
    public void setPhoneNum(String num) {
        phoneNum = num;
    }
    public void setAddress(String adr) {
        address = adr;
    }
    public void setGender(String g) {
        gender = g;
    }
    public void setHeight(String h) {
        height = h;
    }

    public void resetModel() {
        instance = null;
    }

}
