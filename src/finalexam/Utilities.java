/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package finalexam;

import java.util.Scanner;
import java.sql.*;



/**
 *
 * @author arabesko
 */
public class Utilities {
    public Connection dataBaseConnection(){
        //Method to conect whit the Data Base
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            //Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cctdatabase", "root", "root");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/carSystem", "root", "root");
            
            System.out.println("Connected to DB...XD");
            return con;
             
        } catch (SQLException e){
            System.out.println("SQL Error --> ");
            System.out.println(e.getMessage());
            System.out.println(e.getSQLState());
        } catch (ClassNotFoundException e){
            System.out.println("Class error --" + e.getMessage());
        }
        return null;
    }
    
    public int myMenu(String prompt){
        //Method to do the menu with the options
        Scanner myScan = new Scanner(System.in);
        int userInput=0;
        boolean valid = false;
        
        do {
            System.out.println(prompt);
            System.out.print("Input de option: ");
            try{
                userInput = myScan.nextInt();
                if(userInput >= 1 && userInput <=7){
                   valid = true;
                } else {
                    //If the user input negative number o different to the list
                    valid = false;
                    System.out.println("Please input a valid option. try again");
                }
            } catch (Exception e){
                //If the usea input text or decimal numbers
                valid = false;
                System.out.println("Please input a valid option, try again");
                myScan.next();
            }
        } while (valid == false);
        return userInput;
    }
    
    public int numericInput(String prompt){
        Scanner myScan = new Scanner(System.in);
        int userInput=0;
        boolean valid = false;
        
        do {
            System.out.print(prompt);
            try{
                userInput = myScan.nextInt();
                if(userInput > 0){
                   valid = true;
                } else {
                    //If the user input negative number
                    valid = false;
                    System.out.println("The customer id must to be a positive number. try again");
                }
            } catch (Exception e){
                //If the usea iinput text or decimal numbers
                valid = false;
                System.out.println("Please input a valid option, try again");
                myScan.next();
            }
        } while (valid == false);
        return userInput;
    }
    
    public ResultSet dbResult(Connection db, String sqlPromp){
        try{
            Statement stmt = db.createStatement();
            return stmt.executeQuery(sqlPromp);
        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
        
    }
    
}
