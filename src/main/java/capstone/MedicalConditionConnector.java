package capstone;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Simranjit
 */
public class MedicalConditionConnector extends dataConnector {

    /**
     * medicalName:
     *
     * @param id
     * @return
     */
    public String medicalName(int id) {
        String str = "";
        String tableName = "Medical_Condition";
        ResultSet result = null;
        try {
            Statement stmt = conn.createStatement();
            result = stmt.executeQuery("SELECT * FROM " + tableName + " WHERE ID=" + id);
            while (result.next()) {
                str = result.getString("Medical_Name");
            }
        } catch (SQLException e) {
        }
        return str;
    }

    public String getDescription(int id) {
        String str = "";
        String tableName = "Medical_Condition";
        ResultSet result = null;
        try {
            Statement stmt = conn.createStatement();
            result = stmt.executeQuery("SELECT * FROM " + tableName + " WHERE ID=" + id);
            while (result.next()) {
                str = result.getString("Description");
            }
        } catch (SQLException e) {
        }
        return str;
    }

    public String dietRestrictions(int id) {
        String str = "";
        String tableName = "Medical_Condition";
        ResultSet result = null;
        try {
            Statement stmt = conn.createStatement();
            result = stmt.executeQuery("SELECT * FROM " + tableName + " WHERE ID=" + id);
            while (result.next()) {
                str = result.getString("Diet_Restrictions");
            }
        } catch (SQLException e) {
        }
        return str;
    }

    public String excerciseRestrictions(int id) {
        String str = "";
        String tableName = "Medical_Condition";
        ResultSet result = null;
        try {
            Statement stmt = conn.createStatement();
            result = stmt.executeQuery("SELECT * FROM " + tableName + " WHERE ID=" + id);
            while (result.next()) {
                str = result.getString("Excercise_Restrictions");
            }
        } catch (SQLException e) {
        }
        return str;
    }
}
