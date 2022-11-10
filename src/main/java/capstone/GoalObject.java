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
public class GoalObject {

    private String goalType = "";
    private String dateTarget = "";
    private String dateCreated = "";
    private String weightInitial = "";

    private String weightTarget = "";
    private String sizeGoalId = "0";
    private String strengthGoalId = "0";

    private String neckTarget = "";
    private String neckCurrent = "";

    private String armsTarget = "";
    private String armsCurrent = "";

    private String waistTarget = "";
    private String waistCurrent = "";

    private String hipsTarget = "";
    private String hipsCurrent = "";

    private String legsTarget = "";
    private String legsCurrent = "";

    private String benchPressTargetMax = "";
    private String benchPressCurrentMax = "";

    private String deadliftTargetMax = "";
    private String deadliftCurrentMax = "";

    private String squatsTargetMax = "";
    private String squatsCurrentMax = "";

    private String legPressTargetMax = "";
    private String legPressCurrentMax = "";

    private String shoulderPressTargetMax = "";
    private String shoulderPressCurrentMax = "";

    public void setWeightGoal(String Goal_Type, String initialWeight, String Target_Weight, String Target_Date, String Date_Created) {
        goalType = Goal_Type;
        dateTarget = Target_Date;
        weightTarget = Target_Weight;
        weightInitial = initialWeight;
        dateCreated = Date_Created;
    }

    public void setSizeGoal(String Neck_Target, String Arms_Target, String Waist_Target, String Hips_Target, String Legs_Target,
            String neckInitial, String armsInitial, String waistInitial, String hipsInitial, String legsInitial,
            String goalType, String targetDate, String dateCreated) {
        
        neckTarget = Neck_Target;
        neckCurrent = neckInitial;
        armsTarget = Arms_Target;
        armsCurrent = armsInitial;
        waistTarget = Waist_Target;
        waistCurrent = waistInitial;
        hipsTarget = Hips_Target;
        hipsCurrent = hipsInitial;
        legsTarget = Legs_Target;
        legsCurrent = legsInitial;
        this.goalType = goalType;
        this.dateTarget = targetDate;
        this.dateCreated = dateCreated;
    }

    public void setStrengthGoal(String BenchPress_Target, String DeadLift_Target, String Squats_Target, String LegPress_Target, String ShoulderPress_Target,
            String BenchPress_current, String DeadLift_current, String Squats_current, String LegPress_current, String ShoulderPress_current,
            String goalType, String targetDate, String dateCreated) {
        this.goalType = goalType;
        this.dateTarget = targetDate;
        this.dateCreated = dateCreated;

        benchPressTargetMax = BenchPress_Target;
        benchPressCurrentMax = BenchPress_current;

        deadliftTargetMax = DeadLift_Target;
        deadliftCurrentMax = DeadLift_current;
        squatsTargetMax = Squats_Target;
        squatsCurrentMax = Squats_current;

        legPressTargetMax = LegPress_Target;
        legPressCurrentMax = LegPress_current;
        shoulderPressTargetMax = ShoulderPress_Target;
        shoulderPressCurrentMax = ShoulderPress_current;

    }

    public String getGoalType() {
        return goalType;
    }

    public void setGoalType(String goalType) {
        this.goalType = goalType;
    }

    public String getDateTarget() {
        return dateTarget;
    }

    public void setDateTarget(String dateTarget) {
        this.dateTarget = dateTarget;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getWeightTarget() {
        return weightTarget;
    }

    public void setWeightTarget(String weightTarget) {
        this.weightTarget = weightTarget;
    }

    public String getSizeGoalId() {
        return sizeGoalId;
    }

    public void setSizeGoalId(String sizeGoalId) {
        this.sizeGoalId = sizeGoalId;
    }

    public String getStrengthGoalId() {
        return strengthGoalId;
    }

    public void setStrengthGoalId(String strengthGoalId) {
        this.strengthGoalId = strengthGoalId;
    }

    public String getNeckTarget() {
        return neckTarget;
    }

    public void setNeckTarget(String neckTarget) {
        this.neckTarget = neckTarget;
    }

    public String getNeckCurrent() {
        return neckCurrent;
    }

    public void setNeckCurrent(String neckCurrent) {
        this.neckCurrent = neckCurrent;
    }

    public String getArmsTarget() {
        return armsTarget;
    }

    public void setArmsTarget(String armsTarget) {
        this.armsTarget = armsTarget;
    }

    public String getArmsCurrent() {
        return armsCurrent;
    }

    public void setArmsCurrent(String armsCurrent) {
        this.armsCurrent = armsCurrent;
    }

    public String getWaistTarget() {
        return waistTarget;
    }

    public void setWaistTarget(String waistTarget) {
        this.waistTarget = waistTarget;
    }

    public String getWaistCurrent() {
        return waistCurrent;
    }

    public void setWaistCurrent(String waistCurrent) {
        this.waistCurrent = waistCurrent;
    }

    public String getHipsTarget() {
        return hipsTarget;
    }

    public void setHipsTarget(String hipsTarget) {
        this.hipsTarget = hipsTarget;
    }

    public String getHipsCurrent() {
        return hipsCurrent;
    }

    public void setHipsCurrent(String hipsCurrent) {
        this.hipsCurrent = hipsCurrent;
    }

    public String getLegsTarget() {
        return legsTarget;
    }

    public void setLegsTarget(String legsTarget) {
        this.legsTarget = legsTarget;
    }

    public String getLegsCurrent() {
        return legsCurrent;
    }

    public void setLegsCurrent(String legsCurrent) {
        this.legsCurrent = legsCurrent;
    }

    public String getBenchPressTargetMax() {
        return benchPressTargetMax;
    }

    public void setBenchPressTargetMax(String benchPressTargetMax) {
        this.benchPressTargetMax = benchPressTargetMax;
    }

    public String getBenchPressCurrentMax() {
        return benchPressCurrentMax;
    }

    public void setBenchPressCurrentMax(String benchPressCurrentMx) {
        this.benchPressCurrentMax = benchPressCurrentMx;
    }

    public String getDeadliftTargetMax() {
        return deadliftTargetMax;
    }

    public void setDeadliftTargetMax(String deadliftTargetMax) {
        this.deadliftTargetMax = deadliftTargetMax;
    }

    public String getDeadliftCurrentMax() {
        return deadliftCurrentMax;
    }

    public void setDeadliftCurrentMx(String deadliftCurrentMx) {
        this.deadliftCurrentMax = deadliftCurrentMx;
    }

    public String getSquatsTargetMax() {
        return squatsTargetMax;
    }

    public void setSquatsTargetMax(String squatsTargetMax) {
        this.squatsTargetMax = squatsTargetMax;
    }

    public String getSquatsCurrentMax() {
        return squatsCurrentMax;
    }

    public void setSquatsCurrentMax(String squatsCurrentMx) {
        this.squatsCurrentMax = squatsCurrentMx;
    }

    public String getLegPressTargetMax() {
        return legPressTargetMax;
    }

    public void setLegPressTargetMax(String legPressTargetMax) {
        this.legPressTargetMax = legPressTargetMax;
    }

    public String getLegPressCurrentMax() {
        return legPressCurrentMax;
    }

    public void setLegPressCurrentMax(String legPressCurrentMx) {
        this.legPressCurrentMax = legPressCurrentMx;
    }

    public String getShoulderPressTargetMax() {
        return shoulderPressTargetMax;
    }

    public void setShoulderPressTargetMax(String shoulderPressTargetMax) {
        this.shoulderPressTargetMax = shoulderPressTargetMax;
    }

    public String getShoulderPressCurrentMax() {
        return shoulderPressCurrentMax;
    }

    public void setShoulderPressCurrentMax(String shoulderPressCurrentMx) {
        this.shoulderPressCurrentMax = shoulderPressCurrentMx;
    }
    
    public String getWeightInitial() {
        return weightInitial;
    }

    public void setWeightInitial(String weightInitial) {
        this.weightInitial = weightInitial;
    }

}
