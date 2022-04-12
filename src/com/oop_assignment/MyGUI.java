package com.oop_assignment;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class MyGUI implements ActionListener {
    // GUI attributes
    JFrame mainFrame, resultsFrame;
    JComboBox<String> jcBox1, jcBoxAge, jcBoxGender, jcBoxFactor;
    JButton submitButton, closeButton, showButton;
    JLabel label1, label2;
    JPanel p1, p2, p3;

    DefaultTableModel defaultTableModel;
    JTable resultsTable;

    DBConnect db1 = new DBConnect();
    String newQuery;

    // String array for query options
    String[] queryOptions = {"Gender", "Factor_Description", "Age_Group"};
    String[] ageOptions = {"18 - 24 years", "25 - 34 years", "35 - 44 years", "45 - 54 years", "55 - 64 years", "65 - 74 years", "75 years and over"};
    String[] genderOptions = {"Female", "Male"};
    String[] factorOptions = {"Better value", "Better access to services", "More enjoyment using services",
                              "Ease of use of services", "More direct routes", "Closer stops to my destinations",
                              "Improvements in disability access", "No alternative methods to travel", "Shorter journey times",
                              "More reliable journey times", "More reliable timetables", "Greater frequency of service",
                              "To help improve the environment", "None of these reasons"};


    // Class constructor - create GUI frame and components
    public MyGUI() {
        mainFrame = new JFrame("Data Explorer");
        mainFrame.setSize(500,500);
        mainFrame.setLayout(new FlowLayout());

        // Create a panel and set layout
        p1 = new JPanel();
        p1.setLayout(new FlowLayout());

        p2 = new JPanel();

        // Create jcbox
        jcBox1 = new JComboBox<>(queryOptions);
        jcBox1.setBounds(80, 50, 120, 20);
        jcBoxAge = new JComboBox<>(ageOptions);
        jcBoxAge.setBounds(80, 50, 120, 20);
        jcBoxGender = new JComboBox<>(genderOptions);
        jcBoxGender.setBounds(80, 50, 120, 20);
        jcBoxFactor = new JComboBox<>(factorOptions);
        jcBoxFactor.setBounds(80, 50, 120, 20);

        // Create labels
        label1 = new JLabel("Select");
        label2 = new JLabel();

        // Create buttons
        submitButton = new JButton("Submit");
        submitButton.setBounds(100, 100, 90, 20);
        submitButton.addActionListener(this);

        closeButton = new JButton("Close connection");
        closeButton.setBounds(100, 100, 90, 20);
        closeButton.addActionListener(this);

        showButton = new JButton("Show results");
        showButton.setBounds(100, 100, 90, 20);
        showButton.addActionListener(this);


        // add GUI components to panel
        // add components to panel 1
        p1.add(label1);
        p1.add(jcBox1);
        p1.add(jcBoxAge);
        p1.add(jcBoxGender);
        p1.add(jcBoxFactor);

        // add components to panel 2
        p2.add(submitButton);
        p2.add(label2);
        p2.add(showButton);
        p2.add(closeButton);

        // add panels to frame and make visible
        mainFrame.add(p1);
        mainFrame.add(p2);
        mainFrame.setVisible(true);
    }

    public void resultsFrame() {
        resultsFrame = new JFrame("Results");
        resultsFrame.setLayout(new FlowLayout());
        resultsFrame.setSize(500, 500);

        p3 = new JPanel();

        // JTable
        defaultTableModel = new DefaultTableModel();
        resultsTable = new JTable(defaultTableModel);
        defaultTableModel.addColumn("Age Group");
        defaultTableModel.addColumn("Gender");
        defaultTableModel.addColumn("Factor");
        defaultTableModel.addColumn("Value");

        p3.add(resultsTable);

        resultsFrame.add(p3);
        resultsFrame.setVisible(true);



    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            String optionSelected = jcBox1.getItemAt(jcBox1.getSelectedIndex());
            String optionSelected2 = jcBoxGender.getItemAt(jcBoxGender.getSelectedIndex());


            db1.createQuery(optionSelected, optionSelected2);


        }
        else if (e.getSource() == showButton) {
            String genderOption = jcBoxGender.getItemAt(jcBoxGender.getSelectedIndex());
            String ageOption = jcBoxAge.getItemAt(jcBoxAge.getSelectedIndex());

            //resultsFrame();


            db1.createResultsTable(genderOption, ageOption);


        }
        else if (e.getSource() == closeButton) {
            try {
                db1.closeConnectionAndStatement();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        }
    }

}
