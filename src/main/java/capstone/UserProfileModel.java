/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capstone;

import java.time.LocalDate;

/**
 *
 * @author jesus
 */
public class UserProfileModel {

    private static UserProfileModel instance = null;

    private String fullName = "";
    private String firstName = "";
    private String lastName = "";
    private String userName = "";
    private String password = "";
    private String email = "";
    private String bodyType = "";
    private String gender = "";
    private String height = "";
    private String birthDate = "";
    private int progressCardId = 0;
    private static int dailyIntakeId = 0;
    private static int dailyExerciseId = 0;
    public String physicalActivity = "";
    public String caloriesBurned = "";
    public String duration = "";
    public int counter = 0;

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
    }

    public String getLastName() {
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

    public String getBodyType() {
        return bodyType;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getGender() {
        return gender;
    }

    public String getHeight() {
        return height;
    }

    public int getProgressCardId() {
        return progressCardId;
    }

    public int getDailyIntakeId() {
        return dailyIntakeId;
    }

    public int getDailyExerciseId() {
        return dailyExerciseId;
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

    public void setBodyType(String bt) {
        bodyType = bt;
    }

    public void setBirthDate(String dob) {
        birthDate = dob;
    }

    public void setGender(String g) {
        gender = g;
    }

    public void setHeight(String h) {
        height = h;
    }

    public void setProgressCardId(int id) {
        progressCardId = id;
    }

    public void setDailyIntakeId(int id) {
        dailyIntakeId = id;
    }

    public void setDailyExerciseId(int id) {
        dailyExerciseId = id;
    }

    public void resetModel() {
        instance = null;
    }

    public int getAge() {
        int returnAge = -1;
        LocalDate todayDate = LocalDate.now();
        if(birthDate==null || birthDate.equals("")) return -1;
        String[] bDate = this.getBirthDate().split("-");
        int bDay = Integer.valueOf(bDate[1]);
        int bMonth = Integer.valueOf(bDate[0]);
        int bYear = Integer.valueOf(bDate[2]);
        int todayDay = todayDate.getDayOfMonth();
        int todayMonth = todayDate.getMonthValue();
        int todayYear = todayDate.getYear();
        if (bYear <= todayYear) {
            returnAge = todayYear - bYear;
        }
        //System.out.println("bdate = " + this.getBirthDate());
        //System.out.println("todaydate = " + todayDate.toString());
        if (bMonth >= todayMonth) {
            //System.out.println("minus 1");
            returnAge -= 1;
            if (bMonth == todayMonth && bDay < todayDay) {
                //System.out.println("plus 1");
                returnAge += 1;
            }
        }

        return returnAge;
    }

    public String getHeightInCM() {

        if (height == null || height.equals("")) {
            return "";
        }
        String[] h = height.split("-");
        if(h.length == 0)
            return "";
        double fCM = Double.valueOf(h[0]);
        double iCM = Double.valueOf(h[1]);

        fCM = fCM * 30.48;
        iCM = iCM * 0.39;
        fCM = fCM + iCM;
        return String.valueOf(fCM);
    }

    public void resetPhysicalActivity() {
        physicalActivity = "";
        caloriesBurned = "";
        duration = "";
    }

}
