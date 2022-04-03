package com.oop_assignment;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class Connect {
    public Connect(){

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc_practice", "root", "LuckyDay3020");

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select * from people");

            while (resultSet.next()) {
                System.out.println(resultSet.getString("firstname"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
