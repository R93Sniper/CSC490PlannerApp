/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capstone;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Omar Muy
 */
public class goalCard {

    @FXML
    private AnchorPane weightA, sizeA, strengthA;
    @FXML
    private Label tdLabel, L2;
    @FXML
    private DatePicker targetDate;
    @FXML
    private RadioButton weightRB, sizeRB, strengthRB, rbGain, rbLose, rbMain, metricRB, usRB;

    @FXML
    private TextField targetTF, currentTF, armsTF, armsg, waistTF, waistg, hipsTF, hipsg, neckTF, neckg, legsTF, legsg, benchTF, benchg, deadliftTF, deadliftg, squatsTF,
            squatsg, legpressTF, legpressg, spressTF, spressg;

    //Planner DB
    protected Connection conn;
    protected PreparedStatement preparedStatement;

    private static dataConnector instance = null;
    private final String accessConnStr = "jdbc:ucanaccess://.//SeniorProjectDB.accdb";
    private final String connectionStr = "jdbc:sqlserver://fitnessappserver.database.windows.net:1433;"
            + "database=FitnessAppDB;"
            + "user=alvaj29@fitnessappserver;"
            + "password=Seniorproject1;"
            + "encrypt=true;"
            + "trustServerCertificate=false;"
            + "hostNameInCertificate=*.database.windows.net;"
            + "loginTimeout=30;";

    protected goalCard() {
        getConnectionDB();
    }

    private void getConnectionDB() {
        try {
            conn = DriverManager.getConnection(accessConnStr);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public void closeConnectionDB() throws SQLException {
        conn.close();
    }

    @FXML
    private void goBack() throws IOException {
        App.setRoot("UserHome");

    }

    @FXML
    private void chosenWeight() {
        //If Radio Button for weight goal was chosen
        tdLabel.visibleProperty().set(true);
        targetDate.visibleProperty().set(true);
        targetDate.setDisable(false);
        // now for weight anchorpane to be visible + not disabled
        weightA.setVisible(true);
        weightA.setDisable(false);

        L2.setVisible(true);
        L2.setDisable(false);
        metricRB.setDisable(false);
        usRB.setDisable(false);
        metricRB.setVisible(true);
        usRB.setVisible(true);
        //Now to deselect any other choice. + Clear their fields/anchors
        sizeA.setVisible(false);
        sizeA.setDisable(true);
        sizeRB.setSelected(false);
        strengthA.setVisible(false);
        strengthA.setDisable(true);
        strengthRB.setSelected(false);
    }

    @FXML
    private void chosenSize() {
        //Size goal was chosen
        L2.setVisible(true);
        L2.setDisable(false);
        metricRB.setDisable(false);
        usRB.setDisable(false);
        metricRB.setVisible(true);
        usRB.setVisible(true);

        tdLabel.visibleProperty().set(true);
        targetDate.visibleProperty().set(true);
        targetDate.setDisable(false);
        // Size anchor
        sizeA.setVisible(true);
        sizeA.setDisable(false);

        //Now to deselect any other choice. + Clear their fields/anchors
        weightA.setVisible(false);
        weightA.setDisable(true);
        weightRB.setSelected(false);
        strengthA.setVisible(false);
        strengthA.setDisable(true);
        strengthRB.setSelected(false);

    }

    @FXML
    private void chosenStrength() {
        L2.setVisible(true);
        L2.setDisable(false);
        metricRB.setDisable(false);
        usRB.setDisable(false);
        metricRB.setVisible(true);
        usRB.setVisible(true);

        // Strength was chosen
        tdLabel.visibleProperty().set(true);
        targetDate.visibleProperty().set(true);
        targetDate.setDisable(false);
        // Strength goal
        strengthA.setVisible(true);
        strengthA.setDisable(false);

        //Now to deselect any other choice. + Clear their fields/anchors
        sizeA.setVisible(false);
        sizeA.setDisable(true);
        sizeRB.setSelected(false);
        weightA.setVisible(false);
        weightA.setDisable(true);
        weightRB.setSelected(false);
    }

    @FXML
    private void whichToSave() {
        if (weightRB.isSelected()) {
            runWeight();
        } else if (sizeRB.isSelected()) {
            runSize();
        } else if (strengthRB.isSelected()) {
            runStrength();
        } else {
            System.out.println("Choose a goal");
        }
    }

    private void runWeight() {
        if (rbGain.isSelected()) {
            runGains();
        } else if (rbLose.isSelected()) {
            runLoss();
        } else if (rbMain.isSelected()) {
            maintain();
        } else {
            System.out.println("Please choose a weight goal");
        }
    }

    // Wishing to gain
    private void runGains() {
        int g1 = Integer.parseInt(currentTF.getText());
        int g2 = Integer.parseInt(targetTF.getText());
        compareValues(g1, g2);
        //Checked if it's empty, check
        // Checked if target is greater than current, check
        // save to DB
        LocalDate tDate = targetDate.getValue(); // This should get the date
        // how to save target date?
        saveWeightGoal(g1, g2, tDate); // SAVES TO DB
    }

    // Wishing to lose weight
    private void runLoss() {
        int l1 = Integer.parseInt(currentTF.getText());
        int l2 = Integer.parseInt(targetTF.getText());
        compareValues(l1, l2); // Checked if empty
        LocalDate tDate = targetDate.getValue();
        saveWeightGoal(l1, l2, tDate); // SAVES TO DB
    }

    // Wishing to maintain weight
    private void maintain() {
        // current and target must be the same
        int m1 = Integer.parseInt(currentTF.getText());
        int m2 = Integer.parseInt(targetTF.getText());
        compareValues(m1, m2); // Checks for empties
        LocalDate tDate = targetDate.getValue();
        saveWeightGoal(m1, m2, tDate); // SAVES TO DB
    }

    private void runSize() {
        // Get my values, these are calculated regardless if all are picked but one must be
        int n1 = Integer.parseInt(neckTF.getText()); // Current neck
        int n2 = Integer.parseInt(neckg.getText()); // Target neck
        int a1 = Integer.parseInt(armsTF.getText()); // Current arms
        int a2 = Integer.parseInt(armsg.getText()); // target arms
        int w1 = Integer.parseInt(waistTF.getText()); // current waist
        int w2 = Integer.parseInt(waistg.getText()); // target waist
        int h1 = Integer.parseInt(hipsTF.getText()); // current hips
        int h2 = Integer.parseInt(hipsg.getText()); // target hips
        int l1 = Integer.parseInt(legsTF.getText()); // current legs
        int l2 = Integer.parseInt(legsg.getText()); // target leg goal

        compareValues(n1, n2); // Neck
        compareValues(a1, a2); // Arms
        compareValues(w1, w2); // Waist
        compareValues(h1, h2); // Hip
        compareValues(l1, l2); //Leg

        LocalDate tDate = targetDate.getValue();
        // Compare values checks if any one is zero, if not then it gets whether its dec or inc
        saveSizeGoal(n2, a2, w2, h2, l2, tDate);
    }

    private void runStrength() {
        int s1 = Integer.parseInt(benchTF.getText());
        int s2 = Integer.parseInt(benchg.getText());

        int s3 = Integer.parseInt(deadliftTF.getText());
        int s4 = Integer.parseInt(deadliftg.getText());

        int s5 = Integer.parseInt(squatsTF.getText());
        int s6 = Integer.parseInt(squatsg.getText());

        int s7 = Integer.parseInt(legpressTF.getText());
        int s8 = Integer.parseInt(legpressg.getText());

        int s9 = Integer.parseInt(spressTF.getText());
        int s10 = Integer.parseInt(spressg.getText());

        compareValues(s1, s2);
        compareValues(s3, s4);
        compareValues(s5, s6);
        compareValues(s7, s8);
        compareValues(s9, s10);
        // Look into compareValues... we need both fields to be filled but if they aren't and one is then perhaps set those fields to 0?
        LocalDate tDate = targetDate.getValue();
        
        saveStrengthGoal(s2, s4, s6, s8, s10, tDate);

    }

    private void compareValues(int x, int y) {
        //See if current is bigger or target is bigger..
        if (weightRB.isSelected()) {
            // Check if it's empty
            if (x == 0 && y == 0) {
                System.out.println("This is an error, one or more fields are blank.. fill the necessary fields");
                //System.exit(1);
                return; // breaks method? 
            } else if (x != 0 && y != 0) {
                System.out.println("Current and target are both filled, congrats..");
                if (rbGain.isSelected()) {
                    if (x > y) {
                        System.out.println("ERROR, target can't be less than current due to GAIN button");
                        //System.exit(1); // Terminates unsuccessfully
                        return;
                    } else if (y > x) {
                        System.out.println("Target checks out, it's greater than current");
                    } else if (x == y) {
                        System.out.println("Can't be the same..");
                        return;
                    }
                } else if (rbLose.isSelected()) {
                    if (x > y) {
                        System.out.println("Target is less than current, checks out");
                    } else if (y > x) {
                        System.out.println("ERROR, target can't be MORE than current due to LOSS button");
                        return;
                    } else if (x == y) {
                        System.out.println("Can't be the same..");
                        return;
                    }
                } else if (rbMain.isSelected()) {
                    if (x > y) {
                        System.out.println("error, fields not the same, MAINTAIN = SAME");
                        //System.exit(1); // Terminates unsuccessfully
                        return;
                    } else if (y > x) {
                        System.out.println("ERROR, fields not the same.. MAINTAIN = SAME");
                        return;
                    } else if (x == y) {
                        System.out.println("CHECKS OUT!, maintain will progress...");
                       
                    }
                }

            }
        } else if (sizeRB.isSelected()) {
            if (x != 0 && y != 0) {
                if (x > y) {
                    loseSize();
                } else if (y > x) {
                    gainSize();
                } else {
                    System.out.println("what is your goal?.. cant be the same..");
                }
            } else if (x == 0 && y != 0 || x != 0 && y == 0) {
                System.out.println("Either of these fields cannot be empty.. ");
                // Pop up to let user change it or remake the field??????? UPDATE THIS..........
            }

        } else if (strengthRB.isSelected()) {
            if (x != 0 && y != 0) {
                if (x < y) {
                    System.out.println("Get Stronger..");
                } else {
                    System.out.println("ERROR, current MAX can't be bigger than target MAX");
                }
            } else if (x == 0 && y != 0 || x != 0 && y == 0) {
                System.out.println("Either of these fields cannot be empty.. ");
                // Pop up to let user change it or remake the field??????? UPDATE THIS.......
            } else if (x == 0 && y == 0) {
                System.out.println("These were left blank on purpose..");
            }

        }
    }

    private void loseSize() {
        System.out.println("lose size");
    }

    private void gainSize() {
        System.out.println("Gain size");
    }


    private void saveWeightGoal(int x, int y, LocalDate d) {
        // This should save all data from specific weight goal card to DB
    }

    private void saveSizeGoal(int n2, int a2, int w2, int h2, int l2, LocalDate d) {
        // This should save all data from size current and targets to DB
        // Shoudn't we save current as well??...
    }

    private void saveStrengthGoal(int s2, int s4, int s6, int s8, int s10, LocalDate tDate) {
        // This should save data to DB
        //Shouldn't we save current as well?.... 
    }
}
