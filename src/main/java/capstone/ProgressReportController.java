/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capstone;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author jesus
 */
public class ProgressReportController {

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

    /**
     * Initializes the controller class.
     */
    @FXML
    public void initialize() {
        usr = UserProfileModel.getInstance();
        pc = new ProgressCardConnector();
        mainA.setStyle("-fx-background-color: BEIGE");
        weightChart.setVisible(false);
        //weightReportA.setVisible(false);
        //setupLineChart();
    }

    @FXML
    public void onGoBack() throws IOException {
        App.setRoot("UserHome");
    }

    @FXML
    public void showWeightReport() {
        showGraphInNewWindow();

        //  weightChart.setVisible(true);
        //weightReportA.setVisible(true);
        // setupLineChart();
    }

    @FXML
    public void showGraphInNewWindow() {

        Stage stage = new Stage();
        //Defining the x an y axes
        CategoryAxis x = new CategoryAxis();
        NumberAxis y = new NumberAxis();
        //Setting labels for the axes
        x.setLabel("Date");
        y.setLabel("Weight (lbs)");
        //Creating a chart
        LineChart<String, Number> lineChart = new LineChart<>(x, y);
        //Preparing the data points for the series1
        Series<String, Number> series = new Series<>();

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
            String date = pc.getDateOfCard(i);
            Data<String, Number> data = new Data<>(date, w);
            data.setNode(new Button(String.valueOf(w)));
            System.out.println("nodeProperty= " + data.getNode().toString());
            
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
        
        if(pIds.size()>0){
        y.setAutoRanging(false);
        y.setLowerBound((lowerBound-20.0));
        y.setUpperBound((upperBound+20.0));
        }else{
        y.setAutoRanging(true);    
        y.setUpperBound(upperBound);
        y.setLowerBound(lowerBound);
        }

        y.setTickUnit(10.0);
        y.setForceZeroInRange(false);
      
        //Setting the name 
        series.setName("Day of Progress Card");

        //Setting the data to area chart
        lineChart.getData().addAll(series);
        //Creating a stack pane to hold the chart
        StackPane pane = new StackPane(lineChart);
        pane.setPadding(new Insets(15, 15, 15, 15));
        pane.setStyle("-fx-background-color: BEIGE");
        //Setting the Scene
        Scene scene = new Scene(pane, 595, 350);
        stage.setTitle("Line Chart");
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    private void setupLineChart() {
        yAxis.setTickUnit(10.0);
        //yAxis.setUpperBound(240.0);
        //yAxis.setLowerBound(150.0);
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
