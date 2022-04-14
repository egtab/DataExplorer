package com.oop_assignment;

import java.io.IOException;
import java.sql.*;
import java.util.Arrays;

public class DBConnect {

    // Database name and login information
    String serverName = "localhost";
    String portNumber = "1433";
    String sid = "DataExplorer";
    String url = "jdbc:sqlserver://" + serverName + ":" + portNumber + ";databaseName=" + sid + ";encrypt=true;" + "trustServerCertificate=true;";
    String user, pass;
    ResultSet rset;

    // SQL Objects
    Connection conn;
    Statement stmt;
    String qry;
    String[][] resultArray;

    public DBConnect() {
        // Load the db driver
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            System.out.println("driver loaded");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not load the driver");
        }

    } // end DBConnect()

    public void connectToDB(String user, String pass) {
        // Connect to the database, passing the credentials
        try {

            conn = DriverManager.getConnection(url, user, pass);
            System.out.println ("after connection");


        } catch (Exception e) {
            e.printStackTrace();
        }

    } // end connectToDB()

    public void createQuery(String queryOption, String queryOption2) throws SQLException {
        try {
            // Then, get a statement object (which will be used to execute queries, I/U/D etc)
            stmt = conn.createStatement ();  // now have a mechanism to run SQL statements
            qry = "select Age_Group, Gender, Factor_Description, Value from CommutingReasons where Gender = '"+queryOption+"' AND Age_Group = '"+queryOption2+"';";
            rset = stmt.executeQuery(qry);

            while (rset.next()) {
                System.out.println(rset.getString(1) + " " +rset.getString(2) + " " +rset.getString(3)+" "+rset.getString(4));

            }


            // okay plan - pass selected columns from GUI to here, create query statement that will be saved to string?
            // or result set, hmm
            // then got to showResults() method that will be called from GUI button that will show results
            // will display in terminal for now, but hey, baby steps



        } catch (SQLException e) {
                e.printStackTrace();
        }
        stmt.close();

    } // end createQuery()

    public String[][] createResultsTable(String queryOption, String queryOption2) throws SQLException {
        try {

            stmt = conn.createStatement ();  // now have a mechanism to run SQL statements
            qry = "select Age_Group, Gender, Factor_Description, Value from CommutingReasons where Gender = '"+queryOption+"' AND Age_Group = '"+queryOption2+"';";
            rset = stmt.executeQuery(qry);

            ResultSetMetaData metaData = rset.getMetaData();
            int numColumns = metaData.getColumnCount();
            int numRows = 14;
            resultArray = new String[numRows][numColumns];
            int i = 0;

            while (rset.next()) {
                for (int j = 0; j < numColumns; j++) {
                    resultArray[i][j] = rset.getString(j+1);
                }

                i++;


                /*
                System.out.println(rset.getString(1) + " " +rset.getString(2) + " " +rset.getString(3)+" "+rset.getString(4));

                int row, col;
                row = 14;
                col = 4;
                String age = rset.getString(1);
                String gender = rset.getString(2);
                String factor = rset.getString(3);
                String value = rset.getString(4);

                String[][] newRow = new String[row][col];




                System.out.println(age);
                System.out.println(gender);
                System.out.println(factor);
                System.out.println(value);

                 */
                // return string, assign each array index to a new variable
                // to be passed to the resultsFrame method
                // to add a new row to the

            }

            for (String[] row : resultArray) {
                System.out.println();

                for (String x : row){
                    System.out.print(x + " ");

                }

            }



            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        stmt.close();
        return resultArray;


    } // end createResultsTable()



    public void closeConnection() throws SQLException {
        conn.close();   // close the connection
        System.out.println("Connection closed");
        System.exit(0);
    } // end closeConnection()


} // end class DBConnect




