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
public class ExerciseApiConnector {
    
    private final String urlStr = "https://exercisedb.p.rapidapi.com/exercises/bodyPart/chest";
    private final String apiHost = "exercisedb.p.rapidapi.com";
    private final String apiKey = "8660709c02msh2c56c97518d8565p104f75jsnd8ee709be868";
    
    public static void main(String[] args){
    ExerciseApiConnector con = new ExerciseApiConnector();
    String z = con.getJSONFromAPI("chest");
    Exercise[] ex = con.parseJSON(z);
    for(int i=0; i<ex.length;++i){
    System.out.println("["+i+"] "+ex[i].getName());
    }
    
    
 
    }
    
    
    public String getJSONFromAPI(String x)
    {

        //String GET_URL = "https://calorieninjas.p.rapidapi.com/v1/nutrition?query=fish";  
        String str = "https://exercisedb.p.rapidapi.com/exercises/bodyPart/";
        String queryStr = str+x;   
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
        
    public Exercise[] parseJSON(String result){
        //if ever run into problem with dependencies not being visible, jsut right click project, and click "Build with Dependencies"
        //Gson json = new Gson(result);
        //JSONObject jsonObj= new JSONObject(result);
        JSONArray jArray = new JSONArray(result);
    
        int size = jArray.length();
    
        Exercise[] items = new Exercise[size];
        for(int i=0; i< size; i++)
        {
            Exercise ex = new Exercise();
            JSONObject obj = (JSONObject) jArray.get(i);
            String id = obj.getString("id");
            String name = obj.getString("name");
            String bodypart = obj.getString("bodyPart");
            String equipment = obj.getString("equipment");
            String gifUrl = obj.getString("gifUrl");
            String target = obj.getString("target");
            //System.out.println("object contains= "+obj.toString());
            ex.setId(id);
            ex.setName(name);
            ex.setEquipment(equipment);
            ex.setBodyPart(bodypart);
            ex.setTarget(target);
            ex.setGifURL(gifUrl);
            items[i] = ex;
        }
        return items;
    }
    
    
}
