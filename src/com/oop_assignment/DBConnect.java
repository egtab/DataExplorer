package com.oop_assignment;

import java.awt.*;
import java.io.IOException;
import java.sql.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DBConnect {
    // GUI attributes
    JFrame frame;
    JComboBox<String> jcbox;
    JButton closeButton;

    String[] queryOptions = {"Gender", "Factor_Description", "Age_Group"};

    public DBConnect() {
        // Load the db driver
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            System.out.println("driver loaded");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not load the driver");
        }

        // Database name and login information
        String servername = "localhost";
        String portnumber = "1433";
        String sid = "DataExplorer";
        String url = "jdbc:sqlserver://" + servername + ":" + portnumber + ";databaseName=" + sid + ";encrypt=true;" + "trustServerCertificate=true;";

        String user, pass;
        user = readEntry("userid  : ");
        pass = readEntry("password: ");
        System.out.println(url);

        try {

            // Connect to the database, passing the credentials
            Connection conn = DriverManager.getConnection(url, user, pass);

            System.out.println ("after connection");

            // GUI idk if this is meant to go here tbh
            frame = new JFrame();
            jcbox = new JComboBox<>(queryOptions);
            closeButton = new JButton("Close");
            jcbox.setBounds(80, 50, 120, 20);
            closeButton.setBounds(100, 100, 90, 20);

            // add GUI components to frame
            frame.setSize(500,500);
            frame.setLayout(new FlowLayout());
            frame.add(jcbox);
            frame.add(closeButton);
            frame.setVisible(true);




            /*
            // Then, get a statement object (which will be used to execute queries, I/U/D etc)
            Statement stmt = conn.createStatement ();  // now have a mechanism to run SQL statements

            String qry = readEntry("Enter query: ");

            ResultSet rset = stmt.executeQuery(qry);
            while (rset.next()) {
                System.out.println(rset.getString(1) + " " + rset.getString(2)
                        +" "+rset.getString(3)+" "+rset.getString(4)
                        +" "+rset.getString(5)+" "+rset.getString(6));
            }
            */
            /*
            ResultSet rset = stmt.executeQuery("select Age_Group, Factor_Description, Gender, Value from CommutingReasons where Age_Group = '18 - 24 years';");
            while (rset.next()) {
                System.out.println(rset.getString(1) + " " + rset.getString(2)
                +" "+rset.getString(3)+" "+rset.getString(4));
            }
            */


            //stmt.close();  // close the statement object
            conn.close();   // close the connection
            System.out.println("Connection closed");


        } catch (Exception e) {
            e.printStackTrace();
        }

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



}




