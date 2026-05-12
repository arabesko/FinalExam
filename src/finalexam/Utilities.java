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
