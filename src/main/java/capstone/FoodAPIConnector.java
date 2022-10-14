/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capstone;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author jesus
 * 
 * For better and more accurate results from the API, when users enters quantity, they need to enter in grams.. could also have a grams 
 * conversion for UI
 */
public class FoodAPIConnector {
    
    private String urlStr = "https://calorieninjas.p.rapidapi.com/v1/nutrition?query=";
    private String apiHost = "calorieninjas.p.rapidapi.com";
    private String apiKey = "8660709c02msh2c56c97518d8565p104f75jsnd8ee709be868";
    
    //for every getRequest of a food item, we want data on name, calories, fats, carbs, 
    public static void main(String[] args){
     FoodAPIConnector api = new FoodAPIConnector();
     //getFoodData();
    }
    
    public FoodAPIConnector(){
        try {
            FoodLogDataConnector log = new FoodLogDataConnector();
            ResultSet result = log.getFoodLogRow(4);
            String name = "";
            while (result.next()) {
                //id = result.getInt("ID");
               name = result.getString("Food_Name");
               System.out.println("name= "+name);
            }
            
            
            
            /*
            FoodItem[] temp = parseJSON(getJSONFromAPI("eggs bacon ham cheese"));
            for (FoodItem temp1 : temp) {
            System.out.println("name: " + temp1.getName() + " , caloreis: " + temp1.getCalories());
            log.insertNewFoodLog(temp1.getName(), temp1.getCalories(), temp1.getCarbs(), temp1.getFats(), temp1.getProtein(), temp1.getServingSize());
            }
        */  } catch (SQLException ex) {
            Logger.getLogger(FoodAPIConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    
    private String getJSONFromAPI(String x)
    {

        //String GET_URL = "https://calorieninjas.p.rapidapi.com/v1/nutrition?query=fish";  
        
        String queryStr = urlStr+ x;    
        String result = "";
         try {
                URL obj = new URL(queryStr);
		HttpURLConnection con;
       
                con = (HttpURLConnection) obj.openConnection();
     
		con.setRequestMethod("GET");
                con.setRequestProperty("X-RapidAPI-Key", apiKey);
                con.setRequestProperty("X-RapidAPI-Host", apiHost);
	
		int responseCode = con.getResponseCode();
		System.out.println("GET Response Code :: " + responseCode);
		if (responseCode == HttpURLConnection.HTTP_OK) { // success
			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));                       
			String inputLine;    
			//StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				//response.append(inputLine);
                                result = result+inputLine;
			}        
			in.close();
		} else {
			System.out.println("GET request not worked");
		}
                
                  } catch (IOException ex) { }
         
         return result;
    }
        
    private FoodItem[] parseJSON(String result){
        //if ever run into problem with dependencies not being visible, jsut right click project, and click "Build with Dependencies"
        //Gson json = new Gson(result);
        JSONObject jsonObj= new JSONObject(result);
        JSONArray jsonArray = jsonObj.getJSONArray("items");
        //JSONObject jsonObj = new JSONObject("df");
        int size = jsonArray.length();
        FoodItem[] items = new FoodItem[size];
        for(int i=0; i< size; i++)
        {
            FoodItem foodObj = new FoodItem();
            //by default the api uses a standard quantity of 100g, will have to add 
            JSONObject obj = (JSONObject) jsonArray.get(i);
            //System.out.println("object contains= "+obj.toString());
            double cal = obj.getDouble("calories");
            double fats = obj.getDouble("fat_total_g");
            double carbs = obj.getDouble("carbohydrates_total_g");
            String name = obj.getString("name");
            double protein = obj.getDouble("protein_g");
            double servingsize = obj.getDouble("serving_size_g");
            //System.out.println("calories = "+cal);
            foodObj.setCalories(cal);
            foodObj.setCarbs(carbs);
            foodObj.setFats(fats);
            foodObj.setName(name);
            foodObj.setProtein(protein);
            foodObj.setServingSize(servingsize);
            items[i] = foodObj;
        }
        return items;
    }
    
    public double convertToGrams(double quantity, String unitType){
        // 1 oz = 28.35 grams
        if(unitType.equals("Oz") || unitType.equals("ounce")){
            return (quantity * 28.35);
        }
        // 1 lb = 453.6 grams
        if(unitType.equals("lb") || unitType.equals("pound")){
            return (quantity * 453.6);
        }
        // 1 l(liter) = 1000 grams
        if(unitType.equals("l") || unitType.equals("liter")){
            return (quantity * 1000);
        }
        if(unitType.equals("c") || unitType.equals("cup")){
            return (quantity * 250);
        }
        
        return -1;
    }
    
}
