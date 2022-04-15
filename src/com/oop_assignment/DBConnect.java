package com.oop_assignment;

// Imported packages
import java.sql.*;
import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;


/**
 * This class connects to the database server and executes the SQL queries
 */
public class DBConnect {

    // Database details and login information
    String serverName = "localhost";
    String portNumber = "1433";
    String sid = "DataExplorer";
    String url = "jdbc:sqlserver://" + serverName + ":" + portNumber + ";databaseName=" + sid + ";encrypt=true;" + "trustServerCertificate=true;";

    // SQL Objects
    Connection conn;
    Statement stmt;
    String qry, qry2;
    String[][] resultArray;
    JFreeChart BarChartObject;
    ResultSet rSet, rSet2;


    /**
     * Class constructor - loads the database driver
     */
    public DBConnect() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            System.out.println("driver loaded");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not load the driver");

        } // end try.. catch

    } // end DBConnect()

    /**
     * Method that passes the username and password entered from the
     * GUI class and connects to the database
     *
     * @param user the user's login username
     * @param pass the user's login password
     * @return a boolean stating whether login was successful or not
     */
    public int connectToDB(String user, String pass) {
        // Connect to the database, passing the credentials
        int flag = 0;
        try {

            conn = DriverManager.getConnection(url, user, pass);
            System.out.println ("after connection");
            flag = 1;

        } catch (Exception e) {
            e.printStackTrace();

        } // end try.. catch

        return flag;

    } // end connectToDB()


    /**
     * Method that creates a bar chart comparing two different groups of people based on the
     * factors that encourage them to take public transport
     * Takes query options from the GUI class in order to complete SQL query statement
     * Used JFreechart
     * Source used to help create Bar Chart: https://www.tutorialspoint.com/jfreechart/jfreechart_database_interface.htm
     *
     * @param queryOption first query option
     * @param queryOption2 second query option
     * @param queryOption3 third query option
     * @param queryOption4 fourth query option
     *
     * @return JFreeChart that is displayed in the GUI class
     */
    public JFreeChart createQuery(String queryOption, String queryOption2, String queryOption3, String queryOption4) throws SQLException {
        try {

            // SQL variables that allow us to make queries to the database
            // The first query that is made
            stmt = conn.createStatement ();
            qry = "select Factor_Description, Value from CommutingReasons where Gender = '"+queryOption+"' AND Age_Group = '"+queryOption2+"';";
            rSet = stmt.executeQuery(qry);


            DefaultCategoryDataset dataset = new DefaultCategoryDataset();

            // Adds the results from the first query to the dataset
            while (rSet.next()) {
                String factor = rSet.getString("FACTOR_DESCRIPTION");
                float value = Float.parseFloat(rSet.getString("VALUE"));
                dataset.addValue(value,queryOption +" aged "+queryOption2, factor);
            }

            // The second query that is made
            qry2 = "select Factor_Description, Value from CommutingReasons where Gender = '"+queryOption3+"' AND Age_Group = '"+queryOption4+"';";
            rSet2 = stmt.executeQuery(qry2);

            // Adds the results from the second query to the dataset
            while (rSet2.next()) {
                String factor = rSet2.getString("FACTOR_DESCRIPTION");
                float value = Float.parseFloat(rSet2.getString("VALUE"));
                dataset.addValue(value,queryOption3 +" aged " + queryOption4, factor);
            }


            // Create bar chart
            BarChartObject = ChartFactory.createBarChart(
                "2019 Factors that would encourage greater use of public transport",
                "Factor Description",
                "Percentage",
                dataset,
                PlotOrientation.HORIZONTAL,
                true, true, false);

            rSet.close();
            rSet2.close();

        } catch (SQLException e) {
                e.printStackTrace();
        }

        stmt.close();

        return BarChartObject;

    } // end createQuery()


    /**
     * Method that returns a 2d array to the GUI class to create the JTable
     * or query results
     * Source used to help load result set into a 2d array: https://stackoverflow.com/questions/24547406/resultset-into-2d-array
     *
     * @param queryOption first query option
     * @param queryOption2 second query option
     *
     * @return returns 2d string array to create JTable in the GUI class
     */
    public String[][] createResultsTable(String queryOption, String queryOption2) throws SQLException {
        try {
            // Query that takes the queryOption parameters from teh GUI class
            stmt = conn.createStatement ();
            qry = "select Age_Group, Gender, Factor_Description, Value from CommutingReasons where Gender = '"+queryOption+"' AND Age_Group = '"+queryOption2+"';";
            rSet = stmt.executeQuery(qry);

            // Populating the 2d array with the rows from the
            // result set
            ResultSetMetaData metaData = rSet.getMetaData();
            int numColumns = metaData.getColumnCount();
            int numRows = 14;
            resultArray = new String[numRows][numColumns];
            int i = 0;

            while (rSet.next()) {
                for (int j = 0; j < numColumns; j++) {
                    resultArray[i][j] = rSet.getString(j+1);
                }

                i++;

            } // end while loop

            rSet.close();

            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        stmt.close();

        return resultArray;

    } // end createResultsTable()


    /**
     * Method that closes the connection to the database
     * and also ends the program
     */
    public void closeConnection() throws SQLException {
        conn.close();   // close the connection
        System.out.println("Connection closed");
        System.exit(0);

    } // end closeConnection()

} // end class DBConnect




