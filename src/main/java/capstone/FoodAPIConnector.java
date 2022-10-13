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
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author jesus
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
    FoodItem[] temp = parseJSON(getJSONFromAPI("1 pizza coca cola 3 bread sticks 5 buffalo wings"));
    for(int i=0; i< temp.length;i++)
    {
        System.out.println("name: "+temp[i].getName() + " , caloreis: "+temp[i].getCalories());
    }
    
    
    }
    
    public String getJSONFromAPI(String x)
    {
       System.out.println("running method B");
        //String GET_URL = "https://calorieninjas.p.rapidapi.com/v1/nutrition?query=fish";       
        String foodStr = "fish";
        String queryStr = urlStr+ x;
        
        String result = "";
         try {
                URL obj = new URL(queryStr);
		HttpURLConnection con;
       
                con = (HttpURLConnection) obj.openConnection();
     
		con.setRequestMethod("GET");
                con.setRequestProperty("X-RapidAPI-Key", "8660709c02msh2c56c97518d8565p104f75jsnd8ee709be868");
                con.setRequestProperty("X-RapidAPI-Host", "calorieninjas.p.rapidapi.com");
		//con.setRequestProperty("User-Agent", USER_AGENT);
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
			// print result
			//System.out.println(response.toString());
		} else {
			System.out.println("GET request not worked");
		}
                
                  } catch (IOException ex) {
            //Logger.getLogger(NewClass.class.getName()).log(Level.SEVERE, null, ex);
        }
         
         return result;
    }
    
    
    public FoodItem[] parseJSON(String result){
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
            //System.out.println("calories = "+cal);
            foodObj.setCalories(cal);
            foodObj.setCarbs(carbs);
            foodObj.setFats(fats);
            foodObj.setName(name);
            foodObj.setProtein(protein);
            items[i] = foodObj;
        }
        return items;
    }
    
    
    
}
