package capstone;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Data Connector object
 *
 * This acts as an intermediate between the database and the code
 * All major functions that called against the database backend go here
 * This should be a pure retrieval based object, no logic
 * Logic should be handled in the classes where needed
 *
 * @author Wahab Quazi
 *         -----------
 *         -----------
 */
public class dataConnector {

    public dataConnector() {
    }

    /**
     * Connect to Database function
     *
     * Uses JDBC to connect to the database
     */
    public void connectToDatabase() {
        String myDB = "";
        Connection myConnection = null;
        try {
            //TODO: Replace this with the correct database name
            myDB = "jdbc:ucanaccess://.//--.accdb"; 
            myConnection = DriverManager.getConnection(myDB);
        } catch (SQLException ex) {
            System.out.println("Cannot connect to Database");
        }
    }
}
