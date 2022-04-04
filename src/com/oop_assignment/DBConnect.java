package com.oop_assignment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBConnect {

    public static void main(String[] args) {
        // Load the db driver
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");   // load the driver
            System.out.println("driver loaded");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not load the driver");
        }

        // dB name and credentials. These will be specific to your server etc
        // for your assignment, you would have imported your dataset into a dB.
        /*
        String servername = "";
        String portnumber = "";
        String sid = "";
        String url = "" + servername + ":" + portnumber + ":" + sid;

        String user, pass;
        user = readEntry("userid  : ");
        pass = readEntry("password: ");
        System.out.println(url);
        */
        try {
            String connectionUrl = "jdbc:sqlserver://localhost:1433;databaseName=DataExplorer;encrypt=true;trustServerCertificate=true;user=Eira;password=LuckyDay;";

            // Connect to the database, passing the credentials
            Connection conn = DriverManager.getConnection(connectionUrl);
            System.out.println ("after connection");

            // Then, get a statement object (which will be used to execute queries, I/U/D etc)
            Statement stmt = conn.createStatement ();  // now have a mechanism to run SQL statements

            ResultSet rset = stmt.executeQuery("select Age_Group, Factor_Description, Gender, Value from CommutingReasons where Age_Group = '18 - 24 years';");
            while (rset.next()) {
                System.out.println(rset.getString(1) + " " + rset.getString(2)
                +" "+rset.getString(3)+" "+rset.getString(4));
            }


            stmt.close();  // close the statement object
            conn.close();   // close the connection
            System.out.println("Connection closed");

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

/*
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

 */

}




