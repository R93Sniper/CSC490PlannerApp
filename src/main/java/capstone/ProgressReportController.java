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
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
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
import javafx.scene.paint.Color;
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
    private BarChart<String,Number> barChart;
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
        //loadMonths();
        monthsCB.setVisible(false);
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
        if (selectedMonth == 0) {
            pIds = pc.getAllProgressIds(usr.getUserName());
        } else {
            String startDate = String.valueOf(selectedMonth) + "/01/" + LocalDate.now().getYear();
            String endDate = String.valueOf(selectedMonth) + "/31/" + LocalDate.now().getYear();
            System.out.println("startDate=" + startDate + "  ,endDate=" + endDate);
            pIds = pc.getAllProgressIds(usr.getUserName(), startDate, endDate);
        }

        double upperBound = 0;
        double lowerBound = 999;
        double w = 0;
        Data<String, Number> data;

        ResultSet result = goals.getLastGoal("Weight", usr.getUserName());

        double startWeight = -1;
        double endWeight = -1;
        if (result != null) {
            hasWeightGoal = true;
            String initialWeight = result.getString("Initial_Weight");
            startWeight = Double.valueOf(initialWeight);
            endWeight = Double.valueOf(result.getString("Target_Weight"));
            String dateCreated = result.getString("Date_Created");
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
        if (hasWeightGoal) {
            addTargetDataPoint();
        }

        if (pIds.size() > 0) {
            y.setAutoRanging(false);
            double lowestBound = lowerBound;
            double highestBound = upperBound;
            if (startWeight < lowestBound) {
                lowestBound = startWeight;
            }
            if (endWeight < lowestBound) {
                lowestBound = endWeight;
            }
            if (startWeight > highestBound) {
                highestBound = startWeight;
            }
            if (endWeight > highestBound) {
                highestBound = endWeight;
            }
            y.setLowerBound((lowestBound - 20.0));
            y.setUpperBound((highestBound + 20.0));
        } else {
            if (hasWeightGoal) {
                if (endWeight > startWeight) {
                    y.setUpperBound(endWeight + 20.0);
                    y.setLowerBound(startWeight - 20.0);
                } else {
                    y.setUpperBound(startWeight + 20.0);
                    y.setLowerBound(endWeight - 20.0);
                }

            } else {
                y.setUpperBound(200.0);
                y.setLowerBound(100.0);
            }

            y.setAutoRanging(true);
        }

        y.setTickUnit(10.0);
        y.setForceZeroInRange(false);

        showNewScene("Weight Progress Report");
    }

    @FXML
    private void createSizeGraph() throws SQLException {
        boolean hasSizeGoal = false;
        //Defining the x an y axes
        x = new CategoryAxis();
        y = new NumberAxis();
        //Setting labels for the axes
        x.setLabel("Date");
        y.setLabel("Size Measurements (in.)");
        //Creating a chart
        lineChart = new LineChart<>(x, y);
        lineChart.setTitle("Daily Size Progress");

        //Preparing the data points for the series1
        Series<String, Number> seriesNeck = new Series<>();
        Series<String, Number> seriesArm = new Series<>();
        Series<String, Number> seriesWaist = new Series<>();
        Series<String, Number> seriesHips = new Series<>();
        Series<String, Number> seriesLegs = new Series<>();
        Series<String, Number> seriesStart = new Series<>();
        seriesStart.setName("Goal Created");
        seriesNeck.setName("Neck");
        seriesArm.setName("Arm");
        seriesWaist.setName("Waist");
        seriesHips.setName("Hips");
        seriesLegs.setName("Legs");
        ArrayList<Integer> pIds;
        if (selectedMonth == 0) {
            pIds = pc.getAllProgressIds(usr.getUserName());
        } else {
            String startDate = String.valueOf(selectedMonth) + "/01/" + LocalDate.now().getYear();
            String endDate = String.valueOf(selectedMonth) + "/31/" + LocalDate.now().getYear();
            System.out.println("startDate=" + startDate + "  ,endDate=" + endDate);
            pIds = pc.getAllProgressIds(usr.getUserName(), startDate, endDate);
        }

        double upperBound = 0;
        double lowerBound = 999;

        ResultSet result = goals.getLastGoal("Size", usr.getUserName());
            String finalNeck="";
            String finalArm="";
            String finalWaist="";
            String finalHips="";
            String finalLegs="";
            String targetDate="";
        if (result != null) {
            SizeGoalsConnector goalConn = new SizeGoalsConnector();
            String dateCreated = result.getString("Date_Created");
            targetDate = result.getString("Target_Date");
            hasSizeGoal = true;
            String sizeGoalID = result.getString("SizeGoal_id");
            int sID = Integer.valueOf(sizeGoalID);
            ResultSet res = goalConn.getRow(sID);
            String initialNeck = res.getString("Neck_Initial");
            String initialArm = res.getString("Arms_Initial");
            String initialWaist = res.getString("Waist_Initial");
            String initialHips = res.getString("Hips_Initial");
            String initialLegs = res.getString("Legs_Initial");
            finalNeck = res.getString("Neck_Target");
            finalArm = res.getString("Arms_Target");
            finalWaist = res.getString("Waist_Target");
            finalHips = res.getString("Hips_Target");
            finalLegs = res.getString("Legs_Target");
            
            String str = "Initial: "+initialNeck;
            seriesNeck = addButtonToSeries(dateCreated, initialNeck, str, seriesNeck);
            str =  "Initial: "+initialArm;
            seriesArm = addButtonToSeries(dateCreated, initialArm, str, seriesArm);
            str =  "Initial: "+initialWaist;
            seriesWaist = addButtonToSeries(dateCreated, initialWaist, str, seriesWaist);
            str =  "Initial: "+initialHips;
            seriesHips = addButtonToSeries(dateCreated, initialHips, str, seriesHips);
            str =  "Initial: "+initialLegs;
            seriesLegs = addButtonToSeries(dateCreated, initialLegs, str, seriesLegs);
        }

        DailyMeasurementsConnector mConn = new DailyMeasurementsConnector();
        int mID = 0;
        double neck = 0;
        double arm = 0;
        double waist = 0;
        double hips = 0;
        double legs = 0;
        String date="";
        for (Integer i : pIds) {        
            mID = Integer.valueOf(pc.getSizeID(i));
            neck = mConn.getNeckSize(mID);
            arm = mConn.getArmsSize(mID);
            waist = mConn.getWaistSize(mID);
            hips = mConn.getHipsSize(mID);
            legs = mConn.getLegsSize(mID);

            upperBound = chooseLarger(neck, arm, waist, hips, legs, upperBound);
            lowerBound = chooseSmaller(neck, arm, waist, hips, legs, lowerBound);
            date = pc.getDateOfCard(i);
    
            seriesNeck = addButtonToSeries(date, String.valueOf(neck), String.valueOf(neck), seriesNeck);
            seriesArm = addButtonToSeries(date, String.valueOf(arm), String.valueOf(arm), seriesArm);
            seriesWaist = addButtonToSeries(date, String.valueOf(waist), String.valueOf(waist), seriesWaist);
            seriesHips = addButtonToSeries(date, String.valueOf(hips), String.valueOf(hips), seriesHips);  
            seriesLegs = addButtonToSeries(date, String.valueOf(legs), String.valueOf(legs), seriesLegs);
        }

        if (hasSizeGoal) {
              String str = "Goal: "+finalNeck;    
            seriesNeck = addButtonToSeries(targetDate, finalNeck, str, seriesNeck);
            str = "Goal: "+finalArm;
            seriesArm = addButtonToSeries(targetDate, finalArm, str, seriesArm);
            str = "Goal: "+finalWaist;
            seriesWaist = addButtonToSeries(targetDate, finalWaist, str, seriesWaist);
            str = "Goal: "+finalHips;
            seriesHips = addButtonToSeries(targetDate, finalHips, str, seriesHips);
            str = "Goal: "+finalLegs;
            seriesLegs = addButtonToSeries(targetDate, finalLegs, str, seriesLegs);
        }
        
        lineChart.getData().addAll(seriesNeck, seriesArm, seriesWaist, seriesHips, seriesLegs);
        y.setLowerBound((lowerBound - 5.0));
        y.setUpperBound((upperBound + 5.0));

        y.setTickUnit(10.0);
        y.setForceZeroInRange(false);

        showNewScene("Size Progress Report");
    }
    
    private Series addButtonToSeries(String x, String y, String btnStr, Series s){
     Data<String, Number> data;
     data = new Data<>(x, Double.valueOf(y));
     data.setNode(new Button(btnStr));
     
     if(btnStr.contains("Initial")){ 
                 data.getNode().addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    System.out.println("Hello World, data node clicked");
                    //circle.setFill(Color.DARKSLATEBLUE);  
                }
            });
     }
     
     s.getData().add(data);
    
     return s;
    }
    

    public void showNewScene(String title) {
        Stage stage = new Stage();
        //Creating a stack pane to hold the chart
        StackPane pane = new StackPane(lineChart);
        pane.setPadding(new Insets(15, 15, 15, 15));
        pane.setStyle("-fx-background-color: BEIGE");
        //Setting the Scene
        Scene scene = new Scene(pane, 1200, 900);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }
    
        public void showNewSceneBarchart(String title) {
        Stage stage = new Stage();
        //Creating a stack pane to hold the chart
        StackPane pane = new StackPane(barChart);
        pane.setPadding(new Insets(15, 15, 15, 15));
        pane.setStyle("-fx-background-color: BEIGE");
        //Setting the Scene
        Scene scene = new Scene(pane, 1200, 900);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }
    
    
    
       @FXML
    private void createStrengthGraph() throws SQLException {
        boolean hasStrengthGoal = false;
        //Defining the x an y axes
        x = new CategoryAxis();
        y = new NumberAxis();
        //Setting labels for the axes
        x.setLabel("Date");
        y.setLabel("Weight Lifted (lbs.)");
        //Creating a chart
        lineChart = new LineChart<>(x, y);
        lineChart.setTitle("Daily Strength Progress");

        //Preparing the data points for the series1
        Series<String, Number> seriesBench = new Series<>();
        Series<String, Number> seriesDeadlift = new Series<>();
        Series<String, Number> seriesSquats = new Series<>();
        Series<String, Number> seriesLegPress = new Series<>();
        Series<String, Number> seriesShoulders = new Series<>();
        Series<String, Number> seriesStart = new Series<>();
        seriesStart.setName("Goal Created");
        seriesBench.setName("BenchPress");
        seriesDeadlift.setName("Deadlift");
        seriesSquats.setName("Squats");
        seriesLegPress.setName("Leg Press");
        seriesShoulders.setName("Shoulder Press");
        
        
        ArrayList<Integer> pIds;
        if (selectedMonth == 0) {
            pIds = pc.getAllProgressIds(usr.getUserName());
        } else {
            String startDate = String.valueOf(selectedMonth) + "/01/" + LocalDate.now().getYear();
            String endDate = String.valueOf(selectedMonth) + "/31/" + LocalDate.now().getYear();
            System.out.println("startDate=" + startDate + "  ,endDate=" + endDate);
            pIds = pc.getAllProgressIds(usr.getUserName(), startDate, endDate);
        }

        double upperBound = 0;
        double lowerBound = 999;

        ResultSet result = goals.getLastGoal("Strength", usr.getUserName());
            String finalBench="";
            String finalDeadlift="";
            String finalSquats="";
            String finalLegPress="";
            String finalShoulders="";
            String targetDate="";
        if (result != null) {
            SizeGoalsConnector goalConn = new SizeGoalsConnector();
            String dateCreated = result.getString("Date_Created");
            targetDate = result.getString("Target_Date");
            hasStrengthGoal = true;
            String sGoalID = result.getString("StrengthGoal_id");
            int sID = Integer.valueOf(sGoalID);
            ResultSet res = goalConn.getRow(sID);
            String initialBench = res.getString("BenchPress_InitialMax");
            String initialDeadlift = res.getString("Deadlift_InitialMax");
            String initialSquats = res.getString("Squats_InitialMax");
            String initialLegPress = res.getString("LegPress_InitialMax");
            String initialShoulder = res.getString("ShoulderPress_InitialMax");
            finalBench = res.getString("BenchPress_Target");
            finalDeadlift = res.getString("Deadlift_Target");
            finalSquats = res.getString("Squats_Target");
            finalLegPress = res.getString("LegPress_Target");
            finalShoulders = res.getString("ShoulderPress_Target");
            
            String str = "Initial: "+initialBench;
            seriesBench = addButtonToSeries(dateCreated, initialBench, str, seriesBench);
            str =  "Initial: "+initialDeadlift;
            seriesDeadlift = addButtonToSeries(dateCreated, initialDeadlift, str, seriesDeadlift);
            str =  "Initial: "+initialSquats;
            seriesSquats = addButtonToSeries(dateCreated, initialSquats, str, seriesSquats);
            str =  "Initial: "+initialLegPress;
            seriesLegPress = addButtonToSeries(dateCreated, initialLegPress, str, seriesLegPress);
            str =  "Initial: "+initialShoulder;
            seriesShoulders = addButtonToSeries(dateCreated, initialShoulder, str, seriesShoulders);
        }

        DailyStrengthCardsConnector mConn =  new DailyStrengthCardsConnector();
        int mID = 0;
        double bench = 0;
        double deadlift = 0;
        double squats = 0;
        double legpress = 0;
        double shoulderpress = 0;
        String date="";
        for (Integer i : pIds) {        
            mID = Integer.valueOf(pc.getSizeID(i));
            bench = mConn.getBenchpressMax(mID);
            deadlift = mConn.getDeadliftMax(mID);
            squats = mConn.getSquatsMax(mID);
            legpress = mConn.getLegpressMax(mID);
            shoulderpress = mConn.getShoulderpressMax(mID);

            upperBound = chooseLarger(bench, squats, deadlift, legpress, shoulderpress, upperBound);
            lowerBound = chooseSmaller(bench, squats, deadlift, legpress, shoulderpress, lowerBound);
            date = pc.getDateOfCard(i);
    
            seriesBench = addButtonToSeries(date, String.valueOf(bench), String.valueOf(bench), seriesBench);
            seriesDeadlift = addButtonToSeries(date, String.valueOf(deadlift), String.valueOf(deadlift), seriesDeadlift);
            seriesSquats = addButtonToSeries(date, String.valueOf(squats), String.valueOf(squats), seriesSquats);
            seriesLegPress = addButtonToSeries(date, String.valueOf(legpress), String.valueOf(legpress), seriesLegPress);
            seriesShoulders = addButtonToSeries(date, String.valueOf(shoulderpress), String.valueOf(shoulderpress), seriesShoulders);
        } 
       

        if (hasStrengthGoal) {
            String str = "Goal: "+finalBench;    
            seriesBench = addButtonToSeries(targetDate, finalBench, str, seriesBench);
            str = "Goal: "+finalDeadlift;
            seriesDeadlift = addButtonToSeries(targetDate, finalDeadlift, str, seriesDeadlift);
            str = "Goal: "+finalSquats;
            seriesSquats = addButtonToSeries(targetDate, finalSquats, str, seriesSquats);
            str = "Goal: "+finalLegPress;
            seriesLegPress = addButtonToSeries(targetDate, finalLegPress, str, seriesLegPress);
            str = "Goal: "+finalShoulders;
            seriesShoulders = addButtonToSeries(targetDate, finalShoulders, str, seriesShoulders);
 
        }
        
        lineChart.getData().addAll(seriesBench, seriesDeadlift, seriesSquats, seriesLegPress, seriesShoulders);
        
        y.setLowerBound((lowerBound - 5.0));
        y.setUpperBound((upperBound + 5.0));

        y.setTickUnit(10.0);
        y.setForceZeroInRange(false);

        showNewScene("Strength Progress Report");
    }
    
    
    
    

    private double chooseLarger(double a, double b, double c, double d, double e, double m) {
        double max = Math.max(a, b);
        max = Math.max(max, c);
        max = Math.max(max, d);
        max = Math.max(max, e);
        max = Math.max(max, m);
        return max;
    }

    private double chooseSmaller(double a, double b, double c, double d, double e, double m) {
        double min = Math.min(a, b);
        min = Math.min(min, c);
        min = Math.min(min, d);
        min = Math.min(min, e);
        min = Math.min(min, m);
        return min;
    }
    
        private double chooseMin(double a, double b, double c, double m) {
        double min = Math.min(a, b);
        min = Math.min(min, c);
        min = Math.min(min, m);
        return min;
    }
        
    private double chooseMax(double a, double b, double c, double m) {
        double max = Math.max(a, b);
        max = Math.min(max, c);
        max = Math.min(max, m);
        return max;
    }
    
    

    private void addTargetDataPoint() throws SQLException {

        Series<String, Number> series2 = new Series<>();
        series2.setName("Final Day to reach Goal");
        ResultSet result = goals.getLastGoal("Weight", usr.getUserName());
        String str = "";
        if (result != null) {
            String targetWeight = result.getString("Target_Weight");
            String targetDate = result.getString("Target_Date");
            str = "**Goal= " + result.getString("Goal_Type") + ": " + targetWeight + " lbs " + " By " + result.getString("Target_Date") + " **";
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
        if (!series2.getData().isEmpty()) {
            lineChart.getData().add(series2);
        }

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

        yAxis.setUpperBound(upperBound);
        yAxis.setLowerBound(lowerBound);

        //series.getData().add(new XYChart.Data("day 1", 180));
        //Setting the name to the line (series)
        series.setName("Day of Progress Card");
        //Setting the data to Line chart
        weightChart.getData().add(series);
        xAxis.setLabel("Progress Dayzzz");
    }
    
    @FXML
    public void createDietBarGrpah(){
        
        x = new CategoryAxis();
        y = new NumberAxis();
        barChart =  new BarChart<String,Number>(x,y);
        
        barChart.setTitle("Daily Calorie Intake vs. Outake");
        x.setLabel("Date");       
        y.setLabel("Calories");
 
        double upperBound = 0;
        double lowerBound = 999;
        
        XYChart.Series series1 = new XYChart.Series();
        XYChart.Series series2 = new XYChart.Series();
        XYChart.Series series3 = new XYChart.Series();
        series1.setName("Intake");
        series2.setName("Total");
        series3.setName("Outake");
        
        ArrayList<Integer> pIds;
        if (selectedMonth == 0) {
            pIds = pc.getAllProgressIds(usr.getUserName());
        } else {
            String startDate = String.valueOf(selectedMonth) + "/01/" + LocalDate.now().getYear();
            String endDate = String.valueOf(selectedMonth) + "/31/" + LocalDate.now().getYear();
            System.out.println("startDate=" + startDate + "  ,endDate=" + endDate);
            pIds = pc.getAllProgressIds(usr.getUserName(), startDate, endDate);
        }
    
        DailyIntakeConnector dConn =  new DailyIntakeConnector();
        DailyExerciseConnector eConn = new DailyExerciseConnector();
        int dID = 0;
        String in = "";
        String out = "";
        String total = "";
        double larger;
        double smaller;
        int eID = 0;
        double diff = 0;

        String date="";
        for (Integer i : pIds) {
            
            if(i==0){ continue;}
            
            String p = pc.getDailyIntakeID(i);
            if(p.equals("")|| p.equals("0")){continue;}
            
            dID = Integer.valueOf(p);
            in = dConn.getTotalCalories(dID);
            
            String e = pc.getDailyExerciseID(i);
            eID = Integer.valueOf(e);
            if(eID==0){ out = "0";}
            else{
            out = eConn.getCaloriesOut(eID);
            }
            
            diff = Double.valueOf(in) - Double.valueOf(out);
            total = String.valueOf(diff);
            

            upperBound = chooseMax(Double.valueOf(in), Double.valueOf(out), Double.valueOf(total), upperBound);
            lowerBound = chooseMin(Double.valueOf(in), Double.valueOf(out), Double.valueOf(total), lowerBound);
            date = pc.getDateOfCard(i);
            series1 = addButtonToSeries(date, in, in, series1);
            series2 = addButtonToSeries(date, total,total , series2);
            series3 = addButtonToSeries(date, out, out, series3);
          
        } 
           
        barChart.getData().addAll(series1, series2, series3);
   
        this.showNewSceneBarchart("Diet Progress Report");

    
    
    }
    

    public static void main(String[] args) {
        ProgressReportController pc = new ProgressReportController();

    }
}
