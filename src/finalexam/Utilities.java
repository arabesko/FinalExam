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
    public void dataBaseConnection(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cctdatabase", "root", "root");
            
            System.out.println("Connected to DB...XD");
             
        } catch (SQLException e){
            System.out.println("SQL Error --> ");
            System.out.println(e.getMessage());
            System.out.println(e.getSQLState());
        } catch (ClassNotFoundException e){
            System.out.println("Class error --" + e.getMessage());
        }
    }
    
    public int myMenu(String prompt){
        Scanner myScan = new Scanner(System.in);
        int userInput=0;
        boolean valid = false;
        
        do {
            System.out.println(prompt);
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
                //If the usea iinput text or decimal numbers
                valid = false;
                System.out.println("Please input a valid option, try again");
                myScan.next();
            }
        } while (valid == false);
        return userInput;
    }
    
}
