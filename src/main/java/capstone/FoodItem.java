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
public class FoodItem {

    private String name;
    //all below are in units of grams
    private double calories;
    private double carbs;
    private double protein;
    private double fats;
    private double servingSize;

    public String getName(){return name;}
    public double getCalories() {return calories;}
    public double getCarbs(){return carbs;}
    public double getProtein(){return protein;}
    public double getFats(){return fats;}
    public double getServingSize(){return servingSize;}

    public void setName(String n){name = n;}
    public void setCalories(double c){calories=c;}
    public void setCarbs(double c){carbs = c;}
    public void setProtein(double p){protein = p;}
    public void setFats(double f){fats = f;}
    public void setServingSize(double s){servingSize = s;}
    
}
