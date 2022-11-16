/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capstone;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
    private Alert a = new Alert(AlertType.INFORMATION);

    private LocalDate todayDate = LocalDate.now();
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    //User profile to preload certain things. 
    private UserProfileModel usr = UserProfileModel.getInstance();

    private String gender;
    private double height;
    private int age;

    @FXML
    private void goBack() throws IOException {
        App.setRoot("UserHome");

    }

    @FXML
    public void initialize() {
        //userDB = dataConnector.getInstance();
        usr = UserProfileModel.getInstance();
        if (!usr.getUserName().equals("")) {
            loadGender(); //Need gender for calories
            loadHeightData(); //Height to be computed
            loadUserAge(); // Age for calories
        }
    }

    @FXML
    public void loadGender() {
        if (usr.getGender() != null) {
            if (usr.getGender().equals("Male")) {
                gender = "Male";
            }
            if (usr.getGender().equals("Female")) {
                gender = "Female";
            }
        }
    }

    @FXML
    public void loadHeightData() {
        if (usr.getHeight() != null) {
            String[] hArr = usr.getHeight().split("-");
            String ft = hArr[0];
            String in = hArr[1];
            convertToCM(ft, in);
        }
    }

    private void convertToCM(String x, String y) {
        double feet = Integer.parseInt(x);
        double inches = Integer.parseInt(y);
        feet = Math.round(feet * 30.48); // Converted to cm
        inches = Math.round(inches * 2.54); // Converted to cm
        height = feet + inches;
    }

    @FXML
    public void loadUserAge() {
        if (usr.getBirthDate() != null) {
            if (getAge() > -1) {
                age = getAge();
            } else {
                System.out.println("Birth Age is greater than Today's date so Can't calc");
            }
        }
    }

    private int getAge() {
        int returnAge = -1;
        LocalDate todayDate = LocalDate.now();
        String[] bDate = usr.getBirthDate().split("-");
        int bDay = Integer.valueOf(bDate[1]);
        int bMonth = Integer.valueOf(bDate[0]);
        int bYear = Integer.valueOf(bDate[2]);
        int todayDay = todayDate.getDayOfMonth();
        int todayMonth = todayDate.getMonthValue();
        int todayYear = todayDate.getYear();
        if (bYear <= todayYear) {
            returnAge = todayYear - bYear;
        }
        System.out.println("bdate = " + usr.getBirthDate());
        System.out.println("todaydate = " + todayDate.toString());
        if (bMonth >= todayMonth) {
            System.out.println("minus 1");
            returnAge -= 1;
            if (bMonth == todayMonth && bDay < todayDay) {
                System.out.println("plus 1");
                returnAge += 1;
            }
        }

        return returnAge;
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
        rbGain.setDisable(false);
        rbLose.setDisable(false);
        rbMain.setDisable(false);
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
        //Redo the saves
        g1 = Integer.parseInt(currentTF.getText());
        g2 = Integer.parseInt(targetTF.getText());
        LocalDate tDate = targetDate.getValue(); // This should get the date
        // how to save target date?
        saveWeightGoal(g1, g2, tDate); // SAVES TO DB
        howMuchCalories(g2);
    }

    // Wishing to lose weight
    @FXML
    private void runLoss() {
        notEmpty();
        int l1 = Integer.parseInt(currentTF.getText());
        int l2 = Integer.parseInt(targetTF.getText());
        compareValues(l1, l2); // Checked if empty

        l1 = Integer.parseInt(currentTF.getText());
        l2 = Integer.parseInt(targetTF.getText());
        LocalDate tDate = targetDate.getValue();
        saveWeightGoal(l1, l2, tDate); // SAVES TO DB
        howMuchCalories(l2);
    }

    // Wishing to maintain weight
    @FXML
    private void maintain() {
        // current and target must be the same
        notEmpty();
        int m1 = Integer.parseInt(currentTF.getText());
        int m2 = Integer.parseInt(targetTF.getText());
        compareValues(m1, m2); // Checks for empties

        m1 = Integer.parseInt(currentTF.getText());
        m2 = Integer.parseInt(targetTF.getText());
        LocalDate tDate = targetDate.getValue();
        saveWeightGoal(m1, m2, tDate); // SAVES TO DB
        howMuchCalories(m2);
    }

    @FXML
    private void runSize() {
        notEmpty();
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

        notEmpty();

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

        if (s1 != 0 && s2 != 0) {
            if (s1 < s2) {
                System.out.println("Get Stronger..");
            } else {
                System.out.println("ERROR, TARGET MAX needs to be bigger than CURRENT MAX \n" + "CURRENT MAX value: " + s1);
                d.setHeaderText("ERROR, TARGET MAX needs to be bigger than CURRENT MAX \n" + "CURRENT MAX value: " + s1 + "\n" + "Enter for TARGET MAX");
                d.setContentText("TARGET MAX: ");
                Optional<String> result = d.showAndWait();
                if (result.isPresent()) {
                    s2 = Integer.parseInt(result.get());
                }

            }
        } else if (s1 == 0 && s2 != 0 || s1 != 0 && s2 == 0) {
            System.out.println("Either of these fields cannot be empty.. ");
            return;
        } else if (s1 == 0 && s2 == 0) {
            System.out.println("These were left on 0 on purpose..");
        }

        if (s3 != 0 && s4 != 0) {
            if (s3 < s4) {
                System.out.println("Get Stronger..");
            } else {
                System.out.println("ERROR, TARGET MAX needs to be bigger than CURRENT MAX \n" + "CURRENT MAX value: " + s3);
                d.setHeaderText("ERROR, TARGET MAX needs to be bigger than CURRENT MAX \n" + "CURRENT MAX value: " + s3 + "\n" + "Enter for TARGET MAX");
                d.setContentText("TARGET MAX: ");
                Optional<String> result = d.showAndWait();
                if (result.isPresent()) {
                    s4 = Integer.parseInt(result.get());
                }

            }
        } else if (s3 == 0 && s4 != 0 || s3 != 0 && s4 == 0) {
            System.out.println("Either of these fields cannot be empty.. ");
            return;
        } else if (s3 == 0 && s4 == 0) {
            System.out.println("These were left on 0 on purpose..");
        }

        if (s5 != 0 && s6 != 0) {
            if (s5 < s6) {
                System.out.println("Get Stronger..");
            } else {
                System.out.println("ERROR, TARGET MAX needs to be bigger than CURRENT MAX \n" + "CURRENT MAX value: " + s5);
                d.setHeaderText("ERROR, TARGET MAX needs to be bigger than CURRENT MAX \n" + "CURRENT MAX value: " + s5 + "\n" + "Enter for TARGET MAX");
                d.setContentText("TARGET MAX: ");
                Optional<String> result = d.showAndWait();
                if (result.isPresent()) {
                    s6 = Integer.parseInt(result.get());
                }

            }
        } else if (s5 == 0 && s6 != 0 || s5 != 0 && s6 == 0) {
            System.out.println("Either of these fields cannot be empty.. ");
            return;
        } else if (s5 == 0 && s6 == 0) {
            System.out.println("These were left on 0 on purpose..");
        }

        if (s7 != 0 && s8 != 0) {
            if (s7 < s8) {
                System.out.println("Get Stronger..");
            } else {
                System.out.println("ERROR, TARGET MAX needs to be bigger than CURRENT MAX \n" + "CURRENT MAX value: " + s7);
                d.setHeaderText("ERROR, TARGET MAX needs to be bigger than CURRENT MAX \n" + "CURRENT MAX value: " + s7 + "\n" + "Enter for TARGET MAX");
                d.setContentText("TARGET MAX: ");
                Optional<String> result = d.showAndWait();
                if (result.isPresent()) {
                    s8 = Integer.parseInt(result.get());
                }

            }
        } else if (s7 == 0 && s8 != 0 || s7 != 0 && s8 == 0) {
            System.out.println("Either of these fields cannot be empty.. ");
            return;
        } else if (s7 == 0 && s8 == 0) {
            System.out.println("These were left on 0 on purpose..");
        }

        if (s9 != 0 && s10 != 0) {
            if (s9 < s10) {
                System.out.println("Get Stronger..");
            } else {
                System.out.println("ERROR, TARGET MAX needs to be bigger than CURRENT MAX \n" + "CURRENT MAX value: " + s9);
                d.setHeaderText("ERROR, TARGET MAX needs to be bigger than CURRENT MAX \n" + "CURRENT MAX value: " + s9 + "\n" + "Enter for TARGET MAX");
                d.setContentText("TARGET MAX: ");
                Optional<String> result = d.showAndWait();
                if (result.isPresent()) {
                    s10 = Integer.parseInt(result.get());
                }

            }
        } else if (s9 == 0 && s10 != 0 || s9 != 0 && s10 == 0) {
            System.out.println("Either of these fields cannot be empty.. ");
            return;
        } else if (s9 == 0 && s10 == 0) {
            System.out.println("These were left on 0 on purpose..");
        }

        LocalDate tDate = targetDate.getValue();

        saveStrengthGoal(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, tDate);

    }

    @FXML
    private void compareValues(int x, int y) {
        d.setTitle("Input required fields..");
        a.setTitle("CAUTION");
        //See if current is bigger or target is bigger..
        if (weightRB.isSelected()) {
            // Check if it's empty
            if (x == 0 && y == 0) {
                System.out.println("This is an error, one or more fields are blank.. fill the necessary fields");
                //FIX IT... with correct parameters
                a.setHeaderText("Please fix...");
                a.setContentText("Fix your values for current and target, they can't be 0");
                a.showAndWait();
                d.setHeaderText("ERROR: Current can't be 0");
                d.setContentText("Current: ");
                Optional<String> result = d.showAndWait();
                result.ifPresent(current -> {
                    currentTF.setText(current);
                });
                d.setHeaderText("ERROR: Target can't be 0");
                d.setContentText("Current: ");
                Optional<String> r2 = d.showAndWait();
                result.ifPresent(target -> {
                    targetTF.setText(target);
                });
            } else if (x != 0 && y != 0) {
                System.out.println("Current and target are both filled, congrats..");
                if (rbGain.isSelected()) {
                    if (x > y) {
                        System.out.println("ERROR: target can't be less than current due to GAIN button");
                        //System.exit(1); // Terminates unsuccessfully
                        a.setHeaderText("Please fix...");
                        a.setContentText("Fix your target weight value, it can't be less than current due to GAIN option");
                        a.showAndWait();
                        d.setHeaderText("ERROR: Target can't be less than current");
                        d.setContentText("Target: ");
                        Optional<String> result = d.showAndWait();
                        result.ifPresent(target -> {
                            targetTF.setText(target);
                        });
                    } else if (y > x) {
                        System.out.println("Target checks out, it's greater than current");
                    } else if (x == y) {
                        System.out.println("Can't be the same..");
                        a.setHeaderText("Please fix...");
                        a.setContentText("Fix your target weight value, it can't be the same as current due to GAIN option");
                        a.showAndWait();
                        d.setHeaderText("ERROR: Target can't be the same as current");
                        d.setContentText("Target: ");
                        Optional<String> result = d.showAndWait();
                        result.ifPresent(target -> {
                            targetTF.setText(target);
                        });
                    }
                } else if (rbLose.isSelected()) {
                    if (x > y) {
                        System.out.println("Target is less than current, checks out");
                    } else if (y > x) {
                        System.out.println("ERROR: target can't be MORE than current due to LOSS button");
                        a.setHeaderText("Please fix...");
                        a.setContentText("Fix your target weight value, it can't be MORE than current due to LOSE option");
                        a.showAndWait();
                        d.setHeaderText("ERROR: Target can't be more than current");
                        d.setContentText("Target: ");
                        Optional<String> result = d.showAndWait();
                        result.ifPresent(target -> {
                            targetTF.setText(target);
                        });
                    } else if (x == y) {
                        System.out.println("Can't be the same..");
                        a.setHeaderText("Please fix...");
                        a.setContentText("Fix your target weight value, it can't be MORE than current due to LOSE option");
                        a.showAndWait();
                        d.setHeaderText("ERROR: Target can't be the same as current");
                        d.setContentText("Target: ");
                        Optional<String> result = d.showAndWait();
                        result.ifPresent(target -> {
                            targetTF.setText(target);
                        });
                    }
                } else if (rbMain.isSelected()) {
                    if (x > y) {
                        System.out.println("error, fields not the same, MAINTAIN = SAME");
                        //System.exit(1); // Terminates unsuccessfully
                        a.setHeaderText("Please fix...");
                        a.setContentText("Fix your target weight value, it has to be the same as current due to MAINTAIN option");
                        a.showAndWait();
                        d.setHeaderText("ERROR: Target can't be more/less than current");
                        d.setContentText("Target: ");
                        Optional<String> result = d.showAndWait();
                        result.ifPresent(target -> {
                            targetTF.setText(target);
                        });
                    } else if (y > x) {
                        System.out.println("ERROR, fields not the same.. MAINTAIN = SAME");
                        a.setHeaderText("Please fix...");
                        a.setContentText("Fix your target weight value, it has to be the same as current due to MAINTAIN option");
                        a.showAndWait();
                        d.setHeaderText("ERROR: Target can't be more/less than current");
                        d.setContentText("Target: ");
                        Optional<String> result = d.showAndWait();
                        result.ifPresent(target -> {
                            targetTF.setText(target);
                        });
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
                    return; // Get out
                }
            } else if (x == 0 && y == 0) {
                System.out.println("Intentionally left 0");
            } else if (x == 0 && y != 0 || x != 0 && y == 0) {
                System.out.println("Either of these fields cannot be 0 ");
                a.setHeaderText("NO VALUE CAN BE 0");
                a.setContentText("CHANGE YOUR VALUES");
                return;
                /**
                 * THIS IS SUPPOSE TO MAKE THE VALUE INTO X THEN PASSED, doesn't
                 * work..
                 *
                 * if (x == 0) { // Do this if x is 0 d.setHeaderText("ERROR:
                 * value 0"); d.setContentText("Current: "); Optional<String>
                 * result = d.showAndWait(); result.ifPresent(current -> { x =
                 * Integer.valueOf(current); System.out.println("Saved"); }); }
                 * else if (y == 0) { // Do this if y is 0
                 * d.setHeaderText("ERROR: value can't be 0");
                 * d.setContentText("Target: "); Optional<String> result =
                 * d.showAndWait(); result.ifPresent(target -> { x =
                 * Integer.parseInt(target); System.out.println("target result
                 * is saved to targetTF"); }); }
                 *
                 */
            }

        }
    }

    @FXML
    private void loseSize() {
        //What to do to lose Size
        System.out.println("lose size");
    }

    @FXML
    private void gainSize() {
        //What to do to gain size
        System.out.println("Gain size");
    }

    @FXML
    private void notEmpty() {
        d.setTitle("Input required field/s..");
        // This methods checks the string to make sure it's not null... NULL ONLY, 0 is in later methods
        if (weightRB.isSelected()) {
            if (rbGain.isSelected() || rbLose.isSelected() || rbMain.isSelected()) {
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
            // We are assuming if both current and target are left blank, DON'T NEED THEM

            // NECK PART
            if (neckTF.getText().isEmpty() && neckg.getText().isEmpty()) {
                // Both empty
                System.out.println("neck was left empty.");
                //set value to 0 since we need string
                neckTF.setText("0");
                neckg.setText("0");
            } else if (neckTF.getText().isEmpty() && !neckg.getText().isEmpty()) {
                // Current is empty, target isn't
                // Get c neck value
                d.setHeaderText("Enter for current neck size");
                d.setContentText("Neck: ");
                Optional<String> result = d.showAndWait();
                result.ifPresent(cneck -> {
                    neckTF.setText(cneck);
                });
                System.out.println("This should save current");
            } else if (!neckTF.getText().isEmpty() && neckg.getText().isEmpty()) {
                //Target is empty, current isn't
                // Get t neck value
                d.setHeaderText("Enter for target neck size");
                d.setContentText("Neck: ");
                Optional<String> result = d.showAndWait();
                result.ifPresent(tneck -> {
                    neckg.setText(tneck);
                });
                System.out.println("This should save target");
            } else if (!neckTF.getText().isEmpty() && !neckg.getText().isEmpty()) {
                //Both neck sizes are there...
                System.out.println("Neck sizes are not empty.");
            }
            //ARMS PART
            if (armsTF.getText().isEmpty() && armsg.getText().isEmpty()) {
                // Both arms are empty...
                System.out.println("arms was left empty.");
                armsTF.setText("0");
                armsg.setText("0");
            } else if (!armsTF.getText().isEmpty() && !armsg.getText().isEmpty()) {
                // Both arms are there...
                System.out.println("arms wasn't left empty.");
            } else if (armsTF.getText().isEmpty() && !armsg.getText().isEmpty()) {
                //current arm is empty, target is there
                System.out.println("Get current arm");
                d.setHeaderText("Enter for current arm size");
                d.setContentText("Arm: ");
                Optional<String> result = d.showAndWait();
                result.ifPresent(carm -> {
                    armsTF.setText(carm);
                });
                System.out.println("This should save current");
            } else if (!armsTF.getText().isEmpty() && armsg.getText().isEmpty()) {
                // Current is there, target is empty...
                //Get target arm size
                d.setHeaderText("Enter for target arm size");
                d.setContentText("Arm: ");
                Optional<String> result = d.showAndWait();
                result.ifPresent(tarm -> {
                    armsg.setText(tarm);
                });
                System.out.println("This should save target");
            }
            // WAIST PART
            if (waistTF.getText().isEmpty() && waistg.getText().isEmpty()) {
                //Both are left empty
                System.out.println("Waist was left empty....");
                waistTF.setText("0");
                waistg.setText("0");
            } else if (!waistTF.getText().isEmpty() && !waistg.getText().isEmpty()) {
                //Both are NOT empty
                System.out.println("Waist is there..");
            } else if (waistTF.getText().isEmpty() && !waistg.getText().isEmpty()) {
                //Current is empty, target isn't
                d.setHeaderText("Enter for Current waist size");
                d.setContentText("Waist: ");
                Optional<String> result = d.showAndWait();
                result.ifPresent(twaist -> {
                    waistTF.setText(twaist);
                });
                System.out.println("This should save target");
            } else if (!waistTF.getText().isEmpty() && waistg.getText().isEmpty()) {
                //Current is empty, target is NOT
                d.setHeaderText("Enter for Target waist size");
                d.setContentText("Waist: ");
                Optional<String> result = d.showAndWait();
                result.ifPresent(cwaist -> {
                    waistg.setText(cwaist);
                });
                System.out.println("This should save current");
            }
            //HIPS PART
            if (hipsTF.getText().isEmpty() && hipsg.getText().isEmpty()) {
                //HIPS LEFT EMPTY
                System.out.println("hips was left empty.");
                hipsTF.setText("0");
                hipsg.setText("0");
            } else if (!hipsTF.getText().isEmpty() && !hipsg.getText().isEmpty()) {
                //HIPS LEFT FILLED
                System.out.println("hips was isn't empty.");
            } else if (hipsTF.getText().isEmpty() && !hipsg.getText().isEmpty()) {
                //Current is empty
                System.out.println("current hips is empty.");
                d.setHeaderText("Enter for current hip size");
                d.setContentText("Hip: ");
                Optional<String> result = d.showAndWait();
                result.ifPresent(chip -> {
                    hipsTF.setText(chip);
                });
                System.out.println("This should save current");
            } else if (!hipsTF.getText().isEmpty() && hipsg.getText().isEmpty()) {
                //Target is empty
                System.out.println("target hips is empty.");
                d.setHeaderText("Enter for target hip size");
                d.setContentText("Hip: ");
                Optional<String> result = d.showAndWait();
                result.ifPresent(thip -> {
                    hipsg.setText(thip);
                });
                System.out.println("This should save target");
            }
            //LEGS PART
            if (legsTF.getText().isEmpty() && legsg.getText().isEmpty()) {
                System.out.println("legs was left empty.");
                legsTF.setText("0");
                legsg.setText("0");
            } else if (!legsTF.getText().isEmpty() && !legsg.getText().isEmpty()) {
                System.out.println("legs wasn't left empty.");
            } else if (legsTF.getText().isEmpty() && !legsg.getText().isEmpty()) {
                System.out.println("current leg was left empty.");
                //Current is empty
                System.out.println("current leg is empty.");
                d.setHeaderText("Enter for current leg size");
                d.setContentText("Leg: ");
                Optional<String> result = d.showAndWait();
                result.ifPresent(cleg -> {
                    legsTF.setText(cleg);
                });
                System.out.println("This should save current");
            } else if (!legsTF.getText().isEmpty() && legsg.getText().isEmpty()) {
                System.out.println("target leg was left empty.");
                System.out.println("target legs is empty.");
                d.setHeaderText("Enter for target leg size");
                d.setContentText("Leg: ");
                Optional<String> result = d.showAndWait();
                result.ifPresent(tleg -> {
                    legsg.setText(tleg);
                });
                System.out.println("This should save target");
            }
        } else if (strengthRB.isSelected()) {
            // Check empties for strength
            //BENCH
            if (benchTF.getText().isEmpty() && benchg.getText().isEmpty()) {
                System.out.println("Bench was left empty.");
                benchTF.setText("0");
                benchg.setText("0");
            } else if (!benchTF.getText().isEmpty() && !benchg.getText().isEmpty()) {
                System.out.println("Bench wasn't left empty.");
            } else if (benchTF.getText().isEmpty() && !benchg.getText().isEmpty()) {
                System.out.println("Current bench was left empty.");
                d.setHeaderText("Enter for current MAX bench");
                d.setContentText("Bench: ");
                Optional<String> result = d.showAndWait();
                result.ifPresent(cbench -> {
                    benchTF.setText(cbench);
                });
                System.out.println("This should save current");
            } else if (!benchTF.getText().isEmpty() && benchg.getText().isEmpty()) {
                System.out.println("Target bench was left empty.");
                d.setHeaderText("Enter for target MAX bench");
                d.setContentText("Bench: ");
                Optional<String> result = d.showAndWait();
                result.ifPresent(tbench -> {
                    benchg.setText(tbench);
                });
                System.out.println("This should save target");
            }

            //SHOULDER PRESS
            if (spressTF.getText().isEmpty() && spressg.getText().isEmpty()) {
                System.out.println("Shoulder press was left empty.");
                spressTF.setText("0");
                spressg.setText("0");
            } else if (!spressTF.getText().isEmpty() && !spressg.getText().isEmpty()) {
                System.out.println("Shoulder press wasn't left empty.");
            } else if (spressTF.getText().isEmpty() && !spressg.getText().isEmpty()) {
                System.out.println("Current shoulder press was left empty.");
                d.setHeaderText("Enter for current MAX shoulder press");
                d.setContentText("Shoulder Press: ");
                Optional<String> result = d.showAndWait();
                result.ifPresent(cspress -> {
                    spressTF.setText(cspress);
                });
                System.out.println("This should save current");
            } else if (!spressTF.getText().isEmpty() && spressg.getText().isEmpty()) {
                System.out.println("Target shoulder press was left empty.");
                d.setHeaderText("Enter for target MAX shoulder press");
                d.setContentText("Shoulder Press: ");
                Optional<String> result = d.showAndWait();
                result.ifPresent(tspress -> {
                    spressg.setText(tspress);
                });
                System.out.println("This should save target");
            }

            //DEADLIFT
            if (deadliftTF.getText().isEmpty() && deadliftg.getText().isEmpty()) {
                System.out.println("Deadlift was left empty.");
                deadliftTF.setText("0");
                deadliftg.setText("0");
            } else if (!deadliftTF.getText().isEmpty() && !deadliftg.getText().isEmpty()) {
                System.out.println("Deadlift wasn't left empty.");
            } else if (deadliftTF.getText().isEmpty() && !deadliftg.getText().isEmpty()) {
                System.out.println("Current deadlift was left empty.");
                d.setHeaderText("Enter for current MAX Deadlift");
                d.setContentText("Deadlift: ");
                Optional<String> result = d.showAndWait();
                result.ifPresent(cdeadlift -> {
                    deadliftTF.setText(cdeadlift);
                });
                System.out.println("This should save current");
            } else if (!deadliftTF.getText().isEmpty() && deadliftg.getText().isEmpty()) {
                System.out.println("Target deadlift was left empty.");
                d.setHeaderText("Enter for target MAX deadlift");
                d.setContentText("Deadlift: ");
                Optional<String> result = d.showAndWait();
                result.ifPresent(tdeadlift -> {
                    deadliftg.setText(tdeadlift);
                });
                System.out.println("This should save target");
            }

            //SQUATs
            if (squatsTF.getText().isEmpty() && squatsg.getText().isEmpty()) {
                System.out.println("Squats was left empty.");
                squatsTF.setText("0");
                squatsg.setText("0");
            } else if (!squatsTF.getText().isEmpty() && !squatsg.getText().isEmpty()) {
                System.out.println("Squats wasn't left empty.");
            } else if (squatsTF.getText().isEmpty() && !squatsg.getText().isEmpty()) {
                System.out.println("Current squats was left empty.");
                d.setHeaderText("Enter for current MAX Squats");
                d.setContentText("Squats: ");
                Optional<String> result = d.showAndWait();
                result.ifPresent(csquats -> {
                    squatsTF.setText(csquats);
                });
                System.out.println("This should save current");
            } else if (!squatsTF.getText().isEmpty() && squatsg.getText().isEmpty()) {
                System.out.println("Target squats was left empty.");
                d.setHeaderText("Enter for target MAX Squats");
                d.setContentText("Squats: ");
                Optional<String> result = d.showAndWait();
                result.ifPresent(tsquats -> {
                    squatsg.setText(tsquats);
                });
                System.out.println("This should save target");
            }

            // LEG PRESS
            if (legpressTF.getText().isEmpty() && legpressg.getText().isEmpty()) {
                System.out.println("Leg Press was left empty.");
                legpressTF.setText("0");
                legpressg.setText("0");
            } else if (!legpressTF.getText().isEmpty() && !legpressg.getText().isEmpty()) {
                System.out.println("Leg Press wasn't left empty.");
            } else if (legpressTF.getText().isEmpty() && !legpressg.getText().isEmpty()) {
                System.out.println("Current leg Press was left empty.");
                d.setHeaderText("Enter for current MAX Leg Press");
                d.setContentText("Leg Press: ");
                Optional<String> result = d.showAndWait();
                result.ifPresent(clpress -> {
                    legpressTF.setText(clpress);
                });
                System.out.println("This should save current");
            } else if (!legpressTF.getText().isEmpty() && legpressg.getText().isEmpty()) {
                System.out.println("Target leg Press was left empty.");
                d.setHeaderText("Enter for target MAX Leg Press");
                d.setContentText("Leg Press: ");
                Optional<String> result = d.showAndWait();
                result.ifPresent(tlpress -> {
                    legpressg.setText(tlpress);
                });
                System.out.println("This should save target");
            }

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
        goal.setSizeGoal(String.valueOf(n2), String.valueOf(a2), String.valueOf(w2), String.valueOf(h2), String.valueOf(l2),
                String.valueOf(n1), String.valueOf(a1), String.valueOf(w1), String.valueOf(h1), String.valueOf(l1), "Size",
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

    private void howMuchCalories(int x) {
        //We have our target weight x which will be converted to KG from LB.
        if (gender.equals("Male")) {
            if (metricRB.isSelected()) {
                // KG. leave alone and calc MALE           
                double res = (10 * x) + (6.25 * height) - (5 * age) + 5;
                double DCI = Math.round(res) + 300; // THIS IS THE DAILY CALORIE INTAKE.
                a.setTitle("Daily Calorie Intake (M)");
                a.setHeaderText("Here are your results");
                a.setContentText("Your daily calorie intake is.. " + DCI);
                a.showAndWait();
            } else if (usRB.isSelected()) {
                // US Pounds.. convert. 
                double w = x / 2.205;
                double res = 10*w + 6.25*height - 5*age + 5;
                double DCI = Math.round(res) + 300; // THIS IS THE DAILY CALORIE INTAKE.
                a.setTitle("Daily Calorie Intake (M)");
                a.setHeaderText("Here are your results");
                a.setContentText("Your daily calorie intake is.. " + DCI);
                a.showAndWait();
            } else {
                a.setTitle("CSC490 - Fitness Planner ");
                a.setHeaderText("CAUTION");
                a.setContentText("PLEASE SELECT A UNIT OF MEASURE");
                a.showAndWait();
            }
        } else if (gender.equals("Female")) {
            if (metricRB.isSelected()) {
                // KG. leave alone and calc FEMALE           
                double res = (10 * x) + (6.25 * height) - (5 * age) - 161;
                double DCI = Math.round(res) + 300; // THIS IS THE DAILY CALORIE INTAKE.
                a.setTitle("Daily Calorie Intake (F)");
                a.setHeaderText("Here are your results");
                a.setContentText("Your daily calorie intake is.. " + DCI);
                a.showAndWait();
            } else if (usRB.isSelected()) {
                // US Pounds.. convert. 
                double w = x / 2.205;
                double res = (10 * w) + (6.25 * height) - (5 * age) - 161;
                double DCI = Math.round(res) + 300; // THIS IS THE DAILY CALORIE INTAKE.
                a.setTitle("Daily Calorie Intake (F) ");
                a.setHeaderText("Here are your results");
                a.setContentText("Your daily calorie intake is.. " + DCI);
                a.showAndWait();
            } else {
                a.setTitle("CSC490 - Fitness Planner ");
                a.setHeaderText("CAUTION");
                a.setContentText("PLEASE SELECT A UNIT OF MEASURE");
                a.showAndWait();
            }
        }
    }
    @FXML
    private void only1RB(){
        if(rbGain.isSelected()){
            rbLose.setDisable(true);
            rbMain.setDisable(true);
        }
        else if(rbLose.isSelected()) {
            rbGain.setDisable(true);
            rbMain.setDisable(true);
        }
        else if(rbMain.isSelected()){
            rbGain.setDisable(true);
            rbLose.setDisable(true);
        }
    }
}

