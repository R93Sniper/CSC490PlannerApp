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
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Omar Muy
 */
public class goalCard {

    @FXML
    private AnchorPane main, weightA, sizeA, strengthA;
    @FXML
    private Label tdLabel, L2;
    @FXML
    private DatePicker targetDate;
    @FXML
    private RadioButton weightRB, sizeRB, strengthRB, rbGain, rbLose, rbMain, metricRB, usRB;

    @FXML
    private TextField targetTF, currentTF, armsTF, armsg, waistTF, waistg, hipsTF, hipsg, neckTF, neckg, legsTF, legsg, benchTF, benchg, deadliftTF, deadliftg, squatsTF,
            squatsg, legpressTF, legpressg, spressTF, spressg;

    private TextInputDialog d = new TextInputDialog();
    private TextInputDialog e = new TextInputDialog();
    private LocalDate todayDate = LocalDate.now();
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

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

    @FXML
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
    @FXML
    private void runGains() {
        notEmpty();
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
    @FXML
    private void runLoss() {
        int l1 = Integer.parseInt(currentTF.getText());
        int l2 = Integer.parseInt(targetTF.getText());
        compareValues(l1, l2); // Checked if empty
        LocalDate tDate = targetDate.getValue();
        saveWeightGoal(l1, l2, tDate); // SAVES TO DB
    }

    // Wishing to maintain weight
    @FXML
    private void maintain() {
        // current and target must be the same
        int m1 = Integer.parseInt(currentTF.getText());
        int m2 = Integer.parseInt(targetTF.getText());
        compareValues(m1, m2); // Checks for empties
        LocalDate tDate = targetDate.getValue();
        saveWeightGoal(m1, m2, tDate); // SAVES TO DB
    }

    @FXML
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
        saveSizeGoal(n1, n2, a1, a2, w1, w2, h1, h2, l1, l2, tDate);
    }

    @FXML
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

        saveStrengthGoal(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, tDate);

    }

    @FXML
    private void compareValues(int x, int y) {
        d.setTitle("Input required fields..");
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
                if (x == 0) {
                    // Do this if x is 0
                    d.setHeaderText("ERROR: Current can't be 0");
                    d.setContentText("Current: ");
                    Optional<String> result = d.showAndWait();
                    result.ifPresent(current -> {
                        currentTF.setText(current);
                        System.out.println("Current result is saved to currentTF");
                    }); // LEFT OFF HERE .. DEALING WITH NULLS WHICH IS FINISHED NOW DEAL WITH 0's ...
                } else if (y == 0) {
                    // Do this if y is 0
                }
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
    @FXML
    private void loseSize() {
        System.out.println("lose size");
    }
    @FXML
    private void gainSize() {
        System.out.println("Gain size");
    }
    @FXML
    private void notEmpty() {
        d.setTitle("Input required field/s..");
        // This methods checks the string to make sure it's not null... NULL ONLY, 0 is in later methods
        if (weightRB.isSelected()) {
            if (rbGain.isSelected() || sizeRB.isSelected() || strengthRB.isSelected()) {
                if (currentTF.getText().isEmpty() && targetTF.getText().isEmpty()) {
                    System.out.println("Please enter both fields...");
                    d.setHeaderText("Enter for current weight");
                    d.setContentText("Weight: ");
                    Optional<String> result = d.showAndWait();
                    result.ifPresent(weight -> {
                        currentTF.setText(weight);
                    });
                    d.setHeaderText("Enter for target weight");
                    d.setContentText("Target: ");
                    Optional<String> r = d.showAndWait();
                    r.ifPresent(target -> {
                        targetTF.setText(target);
                    });
                    System.out.println("This should save your responses");
                } else if (currentTF.getText().isEmpty() && !targetTF.getText().isEmpty()) {
                    System.out.println("Please enter for current");
                    d.setHeaderText("Enter for current weight");
                    d.setContentText("Weight: ");
                    Optional<String> result = d.showAndWait();
                    result.ifPresent(current -> {
                        currentTF.setText(current);
                    });
                    System.out.println("This should save current");
                } else if (!currentTF.getText().isEmpty() && targetTF.getText().isEmpty()) {
                    System.out.println("Please enter input for target");
                    d.setHeaderText("Enter for target weight");
                    d.setContentText("Target: ");
                    Optional<String> result = d.showAndWait();
                    result.ifPresent(target -> {
                        targetTF.setText(target);

                    });
                    System.out.println("Target should be saved..");
                }
            }

        } else if (sizeRB.isSelected()) {
            // Check empties for size
        } else if (strengthRB.isSelected()) {
            // Check empties for strength
        }
    }

    @FXML
    private void saveWeightGoal(int x, int y, LocalDate tDate) {
        // This should save all data from specific weight goal card to DB
        String currentWeight = String.valueOf(x);
        String targetWeight = String.valueOf(y);
        String goalType = "";
        if (weightRB.isSelected()) {
            if (y > x) {
                goalType = "Weight-Gain";
            }
            if (y < x) {
                goalType = "Weight-Loss";
            }
            if (y == x) {
                goalType = "Weight-Maintain";
            }
        }
        GoalObject goal = new GoalObject();
        goal.setWeightGoal(goalType, currentWeight, targetWeight, dateFormatter.format(tDate), dateFormatter.format(todayDate));
        UserGoalsConnector connector = new UserGoalsConnector();
        connector.saveWeightGoal(goal);
        System.out.println("savedWeight Successful!");
    }
    @FXML
    private void saveSizeGoal(int n1, int n2, int a1, int a2, int w1, int w2, int h1, int h2, int l1, int l2, LocalDate d) {
        // This should save all data from size current and targets to DB
        // Shoudn't we save current as well??...
        
        GoalObject goal = new GoalObject();
        goal.setSizeGoal(String.valueOf(n2), String.valueOf(a2), String.valueOf(w2), String.valueOf(h2), String.valueOf(l2)
                , String.valueOf(n1), String.valueOf(a1), String.valueOf(w1), String.valueOf(h1), String.valueOf(l1), "Size",
                dateFormatter.format(d), dateFormatter.format(todayDate));
        
        UserGoalsConnector connector = new UserGoalsConnector();
        connector.saveSizeGoal(goal);
        System.out.println("savedSize Successful!");
    }

    @FXML
    private void saveStrengthGoal(int s1, int s2, int s3, int s4, int s5, int s6, int s7, int s8, int s9, int s10, LocalDate tDate) {
        // This should save data to DB
        //Shouldn't we save current as well?.... 
  
        GoalObject goal = new GoalObject();
        goal.setStrengthGoal(String.valueOf(s2), String.valueOf(s4), String.valueOf(s6), String.valueOf(s8), String.valueOf(s10), 
                String.valueOf(s1), String.valueOf(s3), String.valueOf(s5), String.valueOf(s7), String.valueOf(s9), "Strength", dateFormatter.format(tDate), dateFormatter.format(todayDate));
         UserGoalsConnector connector = new UserGoalsConnector();
         connector.saveStrengthGoal(goal);
        
        System.out.println("savedStrength Successful!");
    }

    @FXML
    private void resetButton() {
        // Reset everything
        weightA.setDisable(true);
        weightA.setVisible(false);

        sizeA.setDisable(true);
        sizeA.setVisible(false);

        strengthA.setDisable(true);
        strengthA.setVisible(false);

        targetDate.getEditor().clear();
        targetDate.setValue(null);

        weightRB.setSelected(false);
        sizeRB.setSelected(false);
        strengthRB.setSelected(false);

        for (Node node : weightA.getChildren()) {

            if (node instanceof TextField) {
                // Clear textfield
                ((TextField) node).setText("");
                //((TextField)node).getText().
            } else if (node instanceof RadioButton) {
                // unselect nodes
                ((RadioButton) node).setSelected(false);
            }
        }
        System.out.println("cleaning up nodes from weightA");
        for (Node node : sizeA.getChildren()) {

            if (node instanceof TextField) {
                // Clear textfield
                ((TextField) node).setText("");
                //((TextField)node).getText().
            } else if (node instanceof RadioButton) {
                // unselect nodes
                ((RadioButton) node).setSelected(false);
            }
        }
        System.out.println("cleaning up nodes from sizeA");
        for (Node node : strengthA.getChildren()) {

            if (node instanceof TextField) {
                // Clear textfield
                ((TextField) node).setText("");
                //((TextField)node).getText().
            } else if (node instanceof RadioButton) {
                // unselect nodes
                ((RadioButton) node).setSelected(false);
            }
        }
        System.out.println("cleaning up nodes from strengthA");
    }
}
