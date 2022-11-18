/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capstone;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import static javafx.scene.paint.Color.color;
import static javafx.scene.paint.Color.color;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author jesus
 */
public class ProgressReportController {

    @FXML
    private ComboBox monthsCB;
    @FXML
    private AnchorPane mainA, weightReportA;
    @FXML
    private LineChart weightChart;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private CategoryAxis xAxis;
    private ProgressCardConnector pc;
    private UserProfileModel usr;
    ArrayList<String> months;
    int selectedMonth = 0;
    private UserGoalsConnector goals = new UserGoalsConnector();
    private CategoryAxis x;
    private NumberAxis y;
    private LineChart<String, Number> lineChart;
    private boolean hasWeightGoal = false;

    /**
     * Initializes the controller class.
     */
    @FXML
    public void initialize() {
        usr = UserProfileModel.getInstance();
        pc = new ProgressCardConnector();
        mainA.setStyle("-fx-background-color: BEIGE");
        weightChart.setVisible(false);
        loadMonths();
        //weightReportA.setVisible(false);
        //setupLineChart();
    }

    @FXML
    public void onGoBack() throws IOException {
        App.setRoot("UserHome");
    }

    @FXML
    private void loadMonths() {
        months = new ArrayList<>(Arrays.asList(
                "",
                "January",
                "Febuary",
                "March",
                "April",
                "May",
                "June",
                "July",
                "August",
                "September",
                "October",
                "November",
                "December"
        ));
        monthsCB.setItems(FXCollections.observableArrayList(months));
        monthsCB.getSelectionModel().selectedIndexProperty()
                .addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue ov, Number value, Number new_value) {
                        selectedMonth = new_value.intValue();
                        try {
                            showWeightReport();
                        } catch (SQLException ex) {
                            Logger.getLogger(ProgressReportController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });

    }

    @FXML
    public void showWeightReport() throws SQLException {
        showGraphInNewWindow();

    }

    @FXML
    public void showGraphInNewWindow() throws SQLException {

        //Defining the x an y axes
        x = new CategoryAxis();
        y = new NumberAxis();
        //Setting labels for the axes
        x.setLabel("Date");
        y.setLabel("Weight (lbs)");
        //Creating a chart
        lineChart = new LineChart<>(x, y);
        
        //Preparing the data points for the series1
        Series<String, Number> series = new Series<>();
        Series<String, Number> seriesStart = new Series<>();
        ArrayList<Integer> pIds;
        if(selectedMonth==0){
             pIds = pc.getAllProgressIds(usr.getUserName());
        }else{
            String startDate = String.valueOf(selectedMonth)+"/01/"+LocalDate.now().getYear();
            String endDate = String.valueOf(selectedMonth)+"/31/"+LocalDate.now().getYear();
            System.out.println("startDate="+startDate +"  ,endDate="+endDate);
            pIds = pc.getAllProgressIds(usr.getUserName(), startDate, endDate);
        }
         
        double upperBound = 0;
        double lowerBound = 999;
        double w = 0;
        Data<String, Number> data;
        
        ResultSet result = goals.getLastGoal("Weight", usr.getUserName());
        
        double startWeight = -1;
        double endWeight = -1;
        if(result !=null){
            hasWeightGoal = true;
        String initialWeight =  result.getString("Initial_Weight");
        startWeight = Double.valueOf(initialWeight);
        endWeight = Double.valueOf(result.getString("Target_Weight"));
        String dateCreated =  result.getString("Date_Created");
        data = new Data<>(dateCreated, Double.valueOf(initialWeight));
        Button btn = new Button(String.valueOf(initialWeight));
        //btn.setStyle("-fx-background-color: #00ff00");
        data.setNode(btn);       
        data.getNode().addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    System.out.println("Hello World, data node clicked");
                    //circle.setFill(Color.DARKSLATEBLUE);  
                }
            });
        seriesStart.getData().add(data);
        //series.getData().add(data);
        lineChart.getData().add(seriesStart);
        }
        
        for (Integer i : pIds) {
            w = pc.getWeight(i);
            if (w > upperBound) {
                upperBound = w;
            }
            if (w < lowerBound) {
                lowerBound = w;
            }
            String date = pc.getDateOfCard(i);
            data = new Data<>(date, w);
            data.setNode(new Button(String.valueOf(w)));
 
            data.getNode().addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    System.out.println("Hello World, data node clicked");
                    //circle.setFill(Color.DARKSLATEBLUE);  
                }
            });

            series.getData().add(data);
            //System.out.println("series node = "+series.getNode().toString());
        }
       seriesStart.setName("Goal Created");
       series.setName("Day of Progress Card");
       
       lineChart.getData().add(series);
       if(hasWeightGoal)
            addTargetDataPoint();

        if (pIds.size() > 0) {
            y.setAutoRanging(false);
            double lowestBound = lowerBound;
            double highestBound = upperBound;
            if(startWeight < lowestBound){
                lowestBound = startWeight;
            }
            if(endWeight < lowestBound){
                lowestBound = endWeight;
            }
           if(startWeight > highestBound){
                highestBound = startWeight;
            }
            if(endWeight > highestBound){
                highestBound = endWeight;
            }        
            y.setLowerBound((lowestBound - 20.0));
            y.setUpperBound((highestBound + 20.0));
        } else {
            if(hasWeightGoal){
                if(endWeight > startWeight){
                    y.setUpperBound(endWeight +20.0);
                    y.setLowerBound(startWeight - 20.0);
                }else{
                    y.setUpperBound(startWeight +20.0);
                    y.setLowerBound(endWeight - 20.0);
                }
            
            }else{      
            y.setUpperBound(200.0);
            y.setLowerBound(100.0);
            }
                   
            y.setAutoRanging(true);
        }

        y.setTickUnit(10.0);
        y.setForceZeroInRange(false);

       showNewScene();
    }
        
        
        
        
      public void showNewScene(){  
          Stage stage = new Stage();
        //Creating a stack pane to hold the chart
        StackPane pane = new StackPane(lineChart);
        pane.setPadding(new Insets(15, 15, 15, 15));
        pane.setStyle("-fx-background-color: BEIGE");
        //Setting the Scene
        Scene scene = new Scene(pane, 595, 350);
        stage.setTitle("Weight Progress Report");
        stage.setScene(scene);
        stage.show();

    }
    
    private void addTargetDataPoint() throws SQLException{
    
        Series<String, Number> series2 = new Series<>();
        series2.setName("Final Day to reach Goal");
        ResultSet result = goals.getLastGoal("Weight", usr.getUserName());
        String str ="";
        if(result !=null){
        String targetWeight = result.getString("Target_Weight");
        String targetDate = result.getString("Target_Date");
         str = "**Goal= " + result.getString("Goal_Type")+": "+targetWeight+" lbs " + " By " + result.getString("Target_Date")+" **";
        Data data = new Data<>(targetDate, Double.valueOf(targetWeight));
        Button btn = new Button(targetWeight);
        //btn.setStyle("-fx-background-color: #30D5C8");
        data.setNode(btn);       
        data.getNode().addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    System.out.println("Hello World, data node clicked");
                    //circle.setFill(Color.DARKSLATEBLUE);  
                }
            });
        series2.getData().add(data);
        }
        if(!series2.getData().isEmpty())
            lineChart.getData().add(series2);
        
        
        lineChart.setTitle(str);

    }
    
    @FXML
    private void setupLineChart() {
        yAxis.setTickUnit(10.0);

        yAxis.setLabel("Weight (lbs)");
        xAxis.setLabel("Days");
        this.setDataToGraph();
    }

    /*
    * Currently the graph plots all data points for existing progressCards on one grpah,
    * a possible enhancement would be to seperate the graphs by months..
    *
    * Perhaps may need to load ghraph in its own scene, because getting the gategories lumped into one
     */
    @FXML
    private void setDataToGraph() {
        //Preparing the data points for the line
        XYChart.Series series = new XYChart.Series();
        ArrayList<Integer> pIds = pc.getAllProgressIds(usr.getUserName());
        double upperBound = 0;
        double lowerBound = 999;
        double w = 0;
        for (Integer i : pIds) {
            w = pc.getWeight(i);
            if (w > upperBound) {
                upperBound = w;
            }
            if (w < lowerBound) {
                lowerBound = w;
            }
            series.getData().add(new XYChart.Data(pc.getDateOfCard(i), w));

        }

        yAxis.setUpperBound(upperBound);
        yAxis.setLowerBound(lowerBound);

        //series.getData().add(new XYChart.Data("day 1", 180));
        //Setting the name to the line (series)
        series.setName("Day of Progress Card");
        //Setting the data to Line chart
        weightChart.getData().add(series);
        xAxis.setLabel("Progress Dayzzz");
    }

    
    
    public static void main(String[] args) {
        ProgressReportController pc = new ProgressReportController();

    }
}
