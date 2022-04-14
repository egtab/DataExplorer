package com.oop_assignment;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class MyGUI implements ActionListener {
    // GUI attributes
    JFrame mainFrame, resultsFrame, loginFrame;
    JComboBox<String> jcBoxAge, jcBoxGender, jcBoxFactor;
    JButton loginButton, submitButton, closeButton, showButton;
    JLabel label1, label2, userLabel, passLabel;
    JPanel labelPanel, fieldPanel, jcBoxPanel, buttonPanel, resultsTablePanel;
    JTextField unameField;
    JPasswordField passField;

    // JTable attributes
    DefaultTableModel defaultTableModel;
    JTable resultsTable;
    String[][] resultTableArray;
    String[] tableColumns = {"Age Group", "Gender", "Factor Description", "Value"};
    JScrollPane scrollPane;

    // create DBConnect object
    DBConnect db1 = new DBConnect();

    // String array for query options
    String[] ageOptions = {"18 - 24 years", "25 - 34 years", "35 - 44 years", "45 - 54 years", "55 - 64 years", "65 - 74 years", "75 years and over"};
    String[] genderOptions = {"Female", "Male"};
    String[] factorOptions = {"Better value", "Better access to services", "More enjoyment using services",
                              "Ease of use of services", "More direct routes", "Closer stops to my destinations",
                              "Improvements in disability access", "No alternative methods to travel", "Shorter journey times",
                              "More reliable journey times", "More reliable timetables", "Greater frequency of service",
                              "To help improve the environment", "None of these reasons"};


    // Class constructor - create GUI frame and components
    public MyGUI() {
        // Call login frame
        loginFrame();

        // Create JFrame
        mainFrame = new JFrame("Data Explorer");
        mainFrame.setSize(1000,700);
        mainFrame.setLayout(new FlowLayout());

        // Create a panel and set layout
        jcBoxPanel = new JPanel();
        buttonPanel = new JPanel();


        // Create labels
        label1 = new JLabel("Select");

        // Create jcbox
        jcBoxAge = new JComboBox<>(ageOptions);
        jcBoxAge.setBounds(80, 50, 120, 20);
        jcBoxGender = new JComboBox<>(genderOptions);
        jcBoxGender.setBounds(80, 50, 120, 20);
        jcBoxFactor = new JComboBox<>(factorOptions);
        jcBoxFactor.setBounds(80, 50, 120, 20);


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
        jcBoxPanel.add(label1);
        jcBoxPanel.add(jcBoxAge);
        jcBoxPanel.add(jcBoxGender);
        jcBoxPanel.add(jcBoxFactor);

        // add components to panel 2
        buttonPanel.add(submitButton);
        buttonPanel.add(showButton);
        buttonPanel.add(closeButton);

        // add panels to frame and make visible
        mainFrame.add(jcBoxPanel);
        mainFrame.add(buttonPanel);
    } // end MyGUI()

    public void loginFrame() {
        loginFrame = new JFrame("User Details");
        loginFrame.setSize(300, 200);
        loginFrame.setLayout(new BorderLayout(5, 5));
        labelPanel = new JPanel();
        fieldPanel = new JPanel();
        labelPanel.setLayout(new GridLayout(0, 1, 2, 2));
        fieldPanel.setLayout(new GridLayout(0, 1, 2, 2));


        // User Panel components
        userLabel = new JLabel("Username", SwingConstants.RIGHT);
        passLabel = new JLabel("Password", SwingConstants.RIGHT);
        unameField = new JTextField();
        unameField.setBounds(80, 50, 120, 20);

        passField = new JPasswordField();
        passField.setBounds(80, 50, 120, 20);

        loginButton = new JButton("Login");
        loginButton.addActionListener(this);

        labelPanel.add(userLabel);
        labelPanel.add(passLabel);
        fieldPanel.add(unameField);
        fieldPanel.add(passField);
        loginFrame.add(loginButton, BorderLayout.SOUTH);

        loginFrame.add(labelPanel, BorderLayout.WEST);
        loginFrame.add(fieldPanel, BorderLayout.CENTER);
        loginFrame.setVisible(true);

    } // end loginFrame()




    public void resultsFrame() {
        resultsFrame = new JFrame("Results");
        resultsFrame.setLayout(new FlowLayout());
        resultsFrame.setSize(700, 600);

        resultsTablePanel = new JPanel();

        // JTable
        resultsTable = new JTable(resultTableArray ,tableColumns);

        scrollPane = new JScrollPane(resultsTable);
        scrollPane.setPreferredSize(new Dimension(500, 400));

        // Set width of the table columns
        TableColumnModel columnModel = resultsTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(125);
        columnModel.getColumn(1).setPreferredWidth(125);
        columnModel.getColumn(2).setPreferredWidth(200);
        columnModel.getColumn(3).setPreferredWidth(50);



        resultsTablePanel.add(scrollPane);
        resultsFrame.add(resultsTablePanel);
        resultsFrame.setVisible(true);


        for (String[] row : resultTableArray) {
            System.out.println();

            for (String x : row){
                System.out.print(x + " ");

            }

        }

    } // end resultsFrame()

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String uname = unameField.getText();
            String password = new String(passField.getPassword());

            db1.connectToDB(uname, password);
            loginFrame.setVisible(false);
            mainFrame.setVisible(true);


        }
        else if (e.getSource() == submitButton) {
            String genderOption = jcBoxGender.getItemAt(jcBoxGender.getSelectedIndex());
            String ageOption = jcBoxAge.getItemAt(jcBoxAge.getSelectedIndex());

            try {
                db1.createQuery(genderOption, ageOption);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }


        }
        else if (e.getSource() == showButton) {
            String genderOption = jcBoxGender.getItemAt(jcBoxGender.getSelectedIndex());
            String ageOption = jcBoxAge.getItemAt(jcBoxAge.getSelectedIndex());

            resultTableArray = new String[14][4];
            try {
                resultTableArray = db1.createResultsTable(genderOption, ageOption);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            resultsFrame();



        }
        else if (e.getSource() == closeButton) {
            try {
                db1.closeConnection();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        }

    } // end actionPerformed()

} // end class MyGUI
