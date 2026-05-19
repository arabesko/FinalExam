/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package finalexam;
import java.util.Scanner;
import java.sql.*;

/**
 *
 * @author arabesko
 */
public class FinalExam {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Utilities myUtilities = new Utilities();
        Scanner myScan = new Scanner(System.in);
        Connection DB = myUtilities.dataBaseConnection(); //Utilities, menu, SQL functions
        String promp = ""; //Promp SQL instrucction
        String exitPromp = ""; //Promp for the result of the SQL
        ResultSet rs; //Result or the Query
        
        //Variable por number input
        int numInput = 0;
        
        int inputUser = 0; //Variable of the menu selected
        
        //Matrix for the options of the menu
        String[] menu = {"Numer of bookings by Customer", 
                         "Numbers of booking of all the Customers",
                          "",
                          "",
                          "",
                          "",
                          "Exit"};
        
        //Promp of the menu
        String textMenu = "\n\n"
                      + "***** Select a valid option ******"
                      + "\nOption 1 - " + menu[0]
                      + "\nOption 2 - " + menu[1]
                      + "\nOption 3 - " + menu[2]
                      + "\nOption 4 - " + menu[3]
                      + "\nOption 5 - " + menu[4]
                      + "\nOption 6 - " + menu[5]
                      + "\nOption 7 - " + menu[6]
                      + "\n\n";
        
        while (inputUser != 7){
            inputUser = myUtilities.myMenu(textMenu);
            
            try{
                
            
                switch(inputUser){
                    case 1 -> {
                        System.out.println("\n\n" + "************ " + menu[0] + " ************");
                        numInput = myUtilities.numericInput("\n Input the client id \n");
                        
                        //Promp of the Query manu option 1
                        promp = "SELECT cust.cust_id, cust.name, COUNT(book.cust_id) AS countCustomer "
                                + "FROM booking book "
                                + "JOIN infoCustomers cust "
                                + "ON book.cust_id = cust.cust_id "
                                + "WHERE cust.cust_id = " + numInput + ";";

                        rs = myUtilities.dbResult(DB, promp); //Query result
                        
                        //Read the only record
                        if (rs.next()){
                            int id = rs.getInt("cust_id");
                            String name = rs.getString("name");
                            int countBooking = rs.getInt("countCustomer");
                            
                            if (countBooking > 0){
                                System.out.println("The client id: " + id + " - " + name + " have " + countBooking + " bookings");
                            } else {
                                System.out.println("They are not records with this client id");
                            }
                        }

                        System.out.println("\n\n" + "**************************************");
                    }
                    case 2 -> {
                        
                        System.out.println("\n\n" + "************ " + menu[1] + " ************");

                        //Promp of the Query manu option 1
                        promp = "SELECT cust.cust_id, cust.name, COUNT(book.cust_id) AS countCustomer "
                                + "FROM booking book "
                                + "JOIN infoCustomers cust "
                                + "ON book.cust_id = cust.cust_id "
                                + "GROUP BY cust.cust_id, cust.name "
                                + "ORDER BY countCustomer DESC;";
                        
                        rs = myUtilities.dbResult(DB, promp); //Query result
                        
                        //Bucle to show de result of the Query
                        System.out.printf("%-15s %-20s %-15s%n", "ID", "NAME", "TOTAL BOOKING");
                        while (rs.next()) {
                            int id = rs.getInt("cust_id");
                            String name = rs.getString("name");
                            int total = rs.getInt("countCustomer");

                            System.out.printf("%-15d %-20s %-15d%n", id, name, total);
                        }
                        System.out.println("\n\n" + "**************************************");
                    }
                    
                    case 3 -> {
                        System.out.println("\n\n" + "************ " + menu[2] + " ************");
                    }
                    case 4 -> {
                        System.out.println("\n\n" + "************ " + menu[3] + " ************");
                    }
                    case 5 -> {
                        System.out.println("\n\n" + "************ " + menu[4] + " ************");
                    }
                    case 6 -> {
                        System.out.println("\n\n" + "************ " + menu[5] + " ************");
                    }
                    case 7 -> {
                        System.out.println("\n\n" + "************ " + menu[6] + " ************");
                        System.out.println("See you soon, bye");
                        System.out.println("\n\n");
                    }
                }
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
        
        
    }
    
}
