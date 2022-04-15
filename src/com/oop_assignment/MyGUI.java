package com.oop_assignment;

// Imported packages
import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import org.jfree.chart.*;


/**
 * This class creates the GUI and calls methods from the DBConnect class
 */
public class MyGUI implements ActionListener {
    // JFrames
    JFrame mainFrame, resultsFrame, loginFrame, chartFrame;

    // Login frame attributes
    JLabel loginLabel, userLabel, passLabel;
    JTextField unameField;
    JPasswordField passField;
    JButton loginButton;
    JPanel loginPanel1, loginPanel2, loginPanel3, loginPanel4;

    // Main frame attributes
    JComboBox<String> jcBoxAge, jcBoxGender, jcBoxAge2, jcBoxGender2;
    JButton compareButton, closeButton, showButton;
    JLabel titleLabel, filterLabel, compareLabel;
    JPanel descriptionPanel, instructionsPanel, jcBoxPanel, jcBoxPanel2, jcBoxPanel3, buttonPanel;

    // Results frame attributes
    JTable resultsTable;
    String[][] resultTableArray;
    String[] tableColumns = {"Age Group", "Gender", "Factor Description", "Value"};
    JPanel resultsTablePanel;
    JScrollPane scrollPane;

    // Chart frame attributes
    JFreeChart resultBarChart;
    ChartPanel chartPanel;

    // create DBConnect object
    DBConnect db1 = new DBConnect();

    // String array for query options
    String[] ageOptions = {"18 - 24 years", "25 - 34 years", "35 - 44 years", "45 - 54 years", "55 - 64 years", "65 - 74 years", "75 years and over"};
    String[] genderOptions = {"Female", "Male"};


    /**
     * Class constructor - creates main GUI frame and components
     */
    public MyGUI() {
        // Call login frame
        loginFrame();

        // Create JFrame
        mainFrame = new JFrame("Data Explorer");
        mainFrame.setSize(1000, 600);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setLocationRelativeTo(null);


        // Description panel components
        descriptionPanel = new JPanel();
        titleLabel = new JLabel("2019 Factors that would encourage greater usage of public transport");
        titleLabel.setFont(new Font("Verdana",Font.BOLD,20));
        descriptionPanel.add(titleLabel);

        instructionsPanel = new JPanel();
        ImageIcon icon = new ImageIcon("images/description.png");
        instructionsPanel.add(new JLabel(icon));


        // JCBox Panel components
        jcBoxPanel = new JPanel();
        jcBoxPanel.setLayout(new GridLayout(2, 1, 5, 20));
        filterLabel = new JLabel("Create table for: ");
        jcBoxPanel2 = new JPanel();
        jcBoxAge = new JComboBox<>(ageOptions);
        jcBoxAge.setBounds(80, 50, 120, 20);
        jcBoxGender = new JComboBox<>(genderOptions);
        jcBoxGender.setBounds(80, 50, 120, 20);

        jcBoxPanel3 = new JPanel();
        compareLabel = new JLabel("Compare with:");
        jcBoxAge2 = new JComboBox<>(ageOptions);
        jcBoxAge2.setBounds(80, 50, 120, 20);
        jcBoxGender2 = new JComboBox<>(genderOptions);
        jcBoxGender2.setBounds(80, 50, 120, 20);

        jcBoxPanel2.add(filterLabel);
        jcBoxPanel2.add(jcBoxAge);
        jcBoxPanel2.add(jcBoxGender);
        jcBoxPanel3.add(compareLabel);
        jcBoxPanel3.add(jcBoxAge2);
        jcBoxPanel3.add(jcBoxGender2);
        jcBoxPanel.add(jcBoxPanel2);
        jcBoxPanel.add(jcBoxPanel3);


        // Button panel components
        buttonPanel = new JPanel();

        showButton = new JButton("Show results table");
        showButton.setBounds(100, 100, 90, 20);
        showButton.addActionListener(this);

        compareButton = new JButton("Compare");
        compareButton.setBounds(100, 100, 90, 20);
        compareButton.addActionListener(this);

        closeButton = new JButton("Close");
        closeButton.setBounds(100, 100, 90, 20);
        closeButton.addActionListener(this);

        // add components to button panel
        buttonPanel.add(showButton);
        buttonPanel.add(compareButton);
        buttonPanel.add(closeButton);


        // add panels to main frame
        mainFrame.add(descriptionPanel, BorderLayout.NORTH);
        mainFrame.add(jcBoxPanel, BorderLayout.EAST);
        mainFrame.add(instructionsPanel, BorderLayout.WEST);
        mainFrame.add(buttonPanel, BorderLayout.SOUTH);

    } // end MyGUI()


    /**
     * Method that prompts user to enter login details
     * to connect to database server
     */
    //
    public void loginFrame() {
        loginFrame = new JFrame("User Details");
        loginFrame.setSize(300, 200);
        loginFrame.setLayout(new BorderLayout(5, 5));
        loginFrame.setLocationRelativeTo(null);

        // Login panel components
        loginPanel1 = new JPanel();
        loginLabel = new JLabel("Please enter login details");
        loginPanel1.add(loginLabel);

        // Label panel components
        loginPanel2 = new JPanel();
        loginPanel2.setLayout(new GridLayout(0, 1, 2, 2));
        userLabel = new JLabel("Username", SwingConstants.RIGHT);
        passLabel = new JLabel("Password", SwingConstants.RIGHT);
        loginPanel2.add(userLabel);
        loginPanel2.add(passLabel);

        // Field panel components
        loginPanel3 = new JPanel();
        loginPanel3.setLayout(new GridLayout(0, 1, 2, 10));
        unameField = new JTextField();
        unameField.setBounds(80, 50, 120, 20);
        passField = new JPasswordField();
        passField.setBounds(80, 50, 120, 20);
        loginPanel3.add(unameField);
        loginPanel3.add(passField);

        // Login button panel
        loginPanel4 = new JPanel();
        loginButton = new JButton("Login");
        loginButton.addActionListener(this);
        loginPanel4.add(loginButton);

        // Add panels to JFrame
        loginFrame.add(loginPanel1, BorderLayout.NORTH);
        loginFrame.add(loginPanel2, BorderLayout.WEST);
        loginFrame.add(loginPanel3, BorderLayout.CENTER);
        loginFrame.add(loginPanel4, BorderLayout.SOUTH);

        loginFrame.setVisible(true);

    } // end loginFrame()

    /**
     * Method that create a JFrame displaying
     * the JTable of the results from the first query
     * Source used to help create JTable from 2d array: https://www.tutorialspoint.com/how-to-set-multidimensional-array-into-jtable-with-java
     */

    public void resultsFrame() {

        // Create and configure results frame
        resultsFrame = new JFrame("Results");
        resultsFrame.setLayout(new FlowLayout());
        resultsFrame.setSize(700, 600);
        resultsFrame.setLocationRelativeTo(null);

        // Create Jpanel
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


        // Add panels to frame and make frame visible
        resultsTablePanel.add(scrollPane);
        resultsFrame.add(resultsTablePanel);
        resultsFrame.setVisible(true);

    } // end resultsFrame()

    /**
     * Method that displays bar chart from
     * the DBConnect class
     */

    public void createChart() {
        chartFrame = new JFrame("Chart");
        chartFrame.setSize(800, 500);
        chartFrame.setLocationRelativeTo(null);

        chartPanel = new ChartPanel(resultBarChart);
        chartFrame.add(chartPanel);
        chartFrame.setVisible(true);

    } // end createChart()


    /**
     * actionPerformed method for different buttons
     *
     * @param e Action Event from pressed button
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String uname = unameField.getText();
            String password = new String(passField.getPassword());

            int flag;
            flag = db1.connectToDB(uname, password);

            if (flag == 1){
                loginFrame.setVisible(false);
                JOptionPane.showMessageDialog(mainFrame, "Connection successful.");
                mainFrame.setVisible(true);
            }
            else {
                JFrame errorFrame = new JFrame();
                JOptionPane.showMessageDialog(errorFrame,"Incorrect login details, please try again");
            } // end inner if.. else

        }
        else if (e.getSource() == compareButton) {
            String genderOption = jcBoxGender.getItemAt(jcBoxGender.getSelectedIndex());
            String ageOption = jcBoxAge.getItemAt(jcBoxAge.getSelectedIndex());
            String genderOption2 = jcBoxGender2.getItemAt(jcBoxGender2.getSelectedIndex());
            String ageOption2 = jcBoxAge2.getItemAt(jcBoxAge2.getSelectedIndex());

            try {
                resultBarChart = db1.createQuery(genderOption, ageOption, genderOption2, ageOption2);
            } catch (SQLException ex) {
                ex.printStackTrace();
            } // end try.. catch

            // Call chart frame
            createChart();

        }
        else if (e.getSource() == showButton) {
            String genderOption = jcBoxGender.getItemAt(jcBoxGender.getSelectedIndex());
            String ageOption = jcBoxAge.getItemAt(jcBoxAge.getSelectedIndex());

            resultTableArray = new String[14][4];
            try {
                resultTableArray = db1.createResultsTable(genderOption, ageOption);
            } catch (SQLException ex) {
                ex.printStackTrace();
            } // end try.. catch

            // Call results frame
            resultsFrame();

        }
        else if (e.getSource() == closeButton) {
            try {
                db1.closeConnection();
            } catch (SQLException ex) {
                ex.printStackTrace();
            } // end try.. catch

        } // end if statement

    } // end actionPerformed()

} // end class MyGUI
