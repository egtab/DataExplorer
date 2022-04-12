package com.oop_assignment;

import java.io.IOException;
import java.sql.*;
import java.util.Arrays;

public class DBConnect {

    // Database name and login information
    String servername = "localhost";
    String portnumber = "1433";
    String sid = "DataExplorer";
    String url = "jdbc:sqlserver://" + servername + ":" + portnumber + ";databaseName=" + sid + ";encrypt=true;" + "trustServerCertificate=true;";
    String user, pass;
    ResultSet rset;

    // SQL Objects
    Connection conn;
    Statement stmt;
    String qry;


    public DBConnect() {
        // Load the db driver
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            System.out.println("driver loaded");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not load the driver");
        }


        user = readEntry("userid  : ");
        pass = readEntry("password: ");
        System.out.println(url);

        // Connect to the database, passing the credentials
        try {

            conn = DriverManager.getConnection(url, user, pass);
            System.out.println ("after connection");


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void createQuery(String queryOption, String queryOption2) {
        try {
            // Then, get a statement object (which will be used to execute queries, I/U/D etc)
            stmt = conn.createStatement ();  // now have a mechanism to run SQL statements
            qry = "select " + queryOption + "," + queryOption2+ " from CommutingReasons;";
            rset = stmt.executeQuery(qry);

            while (rset.next()) {
                System.out.println(rset.getString(1) + " " +rset.getString(2));

            }


            // okay plan - pass selected columns from GUI to here, create query statement that will be saved to string?
            // or result set, hmm
            // then got to showResults() method that will be called from GUI button that will show results
            // will display in terminal for now, but hey, baby steps



        } catch (SQLException e) {
                e.printStackTrace();
        }

    }

    public void createResultsTable(String queryOption, String queryOption2) {
        try {

            stmt = conn.createStatement ();  // now have a mechanism to run SQL statements
            qry = "select Age_Group, Gender, Factor_Description, Value from CommutingReasons where Gender = '"+queryOption+"' AND Age_Group = '"+queryOption2+"';";
            rset = stmt.executeQuery(qry);
            while (rset.next()) {
                //System.out.println(rset.getString(1) + " " +rset.getString(2) + " " +rset.getString(3)+" "+rset.getString(4));

                String age = rset.getString(1);
                String gender = rset.getString(2);
                String factor = rset.getString(3);
                String value = rset.getString(4);

                String[][] row = new String[][] {{age, gender, factor, value}};
                System.out.println(Arrays.toString(row));

                // return string, assign each array index to a new variable
                // to be passed to the resultsFrame method
                // to add a new row to the

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }






    public void closeConnectionAndStatement() throws SQLException {
        stmt.close();  // close the statement object
        conn.close();   // close the connection
        System.out.println("Connection closed");
    }


    //readEntry function -- to read input data
    static String readEntry(String prompt)
    {
        try {
            StringBuffer buffer = new StringBuffer();
            System.out.print(prompt);
            System.out.flush();
            int c = System.in.read();
            while (c != '\n' && c != -1) {
                buffer.append((char)c);
                c = System.in.read();
            }
            return buffer.toString().trim();
        }  catch (IOException e) {
            return "";
        }
    }

} // end public class DBConnect




