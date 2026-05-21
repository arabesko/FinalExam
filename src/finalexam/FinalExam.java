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
        
        
        int numInput = 0; //Variable for number input
        int inputUser = 0; //Variable of the menu selected
        String asddesc =""; //Variable to filter ASC or DESC
        
        //Variables to get result of the Querys
        int id = 0; //Variable to get the id
        String name = ""; //Variable to save names
        int counter = 0; //Variable to count
        double price = 0; //Variable to save partial prices
        double total = 0; //Varibale to save total operations
        
        //Matrix for the options of the menu
        String[] menu = {"Numer of bookings by Customer", 
                         "Numbers of booking of all the Customers",
                         "Detail of the booking",
                         "List of bookings and detail with total service price",
                         "Total Income by Vehicle",
                         "Total Income of the Company",
                         "Income Statistics for the Period",
                         "Exit"};
        
        //Promp of the menu to use a method
        String textMenu = "\n\n"
                      + "***** SELECT A VALID OPTION ******\n"
                      + "\nOption 1 - " + menu[0]
                      + "\nOption 2 - " + menu[1]
                      + "\nOption 3 - " + menu[2]
                      + "\nOption 4 - " + menu[3]
                      + "\nOption 5 - " + menu[4]
                      + "\nOption 6 - " + menu[5]
                      + "\nOption 7 - " + menu[6]
                      + "\nOption 8 - " + menu[7]
                      + "\n";
        
        while (inputUser != 8){
            inputUser = myUtilities.myMenu(textMenu);
            try{
                switch(inputUser){
                    case 1 -> {
                        System.out.println("\n\n" + "************ " + menu[0] + " ************\n");
                        numInput = myUtilities.numericInput("\nInput the client id: ");
                        
                        //Promp of the Query menu option 1
                        promp = "SELECT cust.cust_id, cust.name, COUNT(book.cust_id) AS countCustomer "
                                + "FROM booking book "
                                + "JOIN infoCustomers cust "
                                + "ON book.cust_id = cust.cust_id "
                                + "WHERE cust.cust_id = " + numInput + ";";

                        rs = myUtilities.dbResult(DB, promp); //Query result
                        
                        //Get the result of the Query
                        if (rs.next()){
                            id = rs.getInt("cust_id");
                            name = rs.getString("name");
                            counter = rs.getInt("countCustomer");
                            
                            if (counter > 0){
                                System.out.println("\nThe client id: " + id + " - " + name + " has " + counter + " bookings");
                            } else {
                                System.out.println("They are not records with this client id");
                            }
                        }
                        System.out.println("\n\n" + "**************************************");
                    }
                    case 2 -> {
                        
                        System.out.println("\n\n" + "************ " + menu[1] + " ************\n");
                        numInput = myUtilities.numericInput("\nEnter the number of records to filter: ");
                        asddesc = myUtilities.stringInput("\nEnter 1 for ascending order or 2 for descending order in the name: ");
                        
                        
                        //Promp of the Query menu option 2
                        promp = "SELECT cust.cust_id, cust.name, COUNT(book.cust_id) AS countCustomer "
                                + "FROM booking book "
                                + "JOIN infoCustomers cust "
                                + "ON book.cust_id = cust.cust_id "
                                + "GROUP BY cust.cust_id "
                                + "ORDER BY cust.name " + asddesc + " "
                                + "LIMIT " + numInput + ";";
                        
                        rs = myUtilities.dbResult(DB, promp); //Query result
                        
                        //Bucle to show de result of the Query
                        System.out.printf("\n%-10s %-12s %-15s%n", "CUST ID", "NAME", "TOTAL BOOKING\n");
                        while (rs.next()) {
                            id = rs.getInt("cust_id");
                            name = rs.getString("name");
                            counter = rs.getInt("countCustomer");
                            System.out.printf("%-10d %-12s %-15d%n", id, name, counter);
                        }
                        System.out.println("\n\n" + "**************************************");
                    }
                    case 3 -> {
                        System.out.println("\n\n" + "************ " + menu[2] + " ************\n");
                        numInput = myUtilities.numericInput("\nInput the booking id: ");
                        
                        //Promp of the Query menu option 3
                        promp = "SELECT bo.booking_id, cus.name, car.carType "
                                + "FROM booking bo "
                                + "JOIN infoCustomers cus "
                                + "ON bo.cust_id = cus.cust_id "
                                + "JOIN infoCars car "
                                + "ON bo.car_id = car.car_id "
                                + "WHERE bo.booking_id = " + numInput + ";";
                        
                        rs = myUtilities.dbResult(DB, promp); //Query result
                        
                        //Get the result of the Query
                        if (rs.next()){
                            id = rs.getInt("bo.booking_id");
                            name = rs.getString("cus.name");
                            String car = rs.getString("car.carType");
                            System.out.println("\nThe booking ID: " + id + " of the cliente: " + name + ", with a " + car + "car type, has following details \n");
                            
                            promp = "SELECT bo.service_id, se.serviceName, se.serviceCharge "
                                    + "FROM detailBooking bo "
                                    + "JOIN services se ON bo.service_id = se.service_id "
                                    + "WHERE bo.booking_id = " + id + ";";
                            
                            rs = myUtilities.dbResult(DB, promp); //Query result

                            System.out.printf("%-5s %-23s %-15s%n", "ID", "SERVICE", "PRICE EU\n");
                            //Bucle to show de result of the Query
                            while (rs.next()) {
                                id = rs.getInt("service_id");
                                name = rs.getString("serviceName");
                                price = rs.getInt("serviceCharge");
                                System.out.printf("%-5s %-23s %-15s%n", id, name, price);
                            }
                        } else {
                                System.out.println("They are not records with this client id");
                            }
                    }
                    case 4 -> {
                        System.out.println("\n\n" + "************ " + menu[3] + " ************\n");
                        numInput = myUtilities.numericInput("\nEnter the number of records to filter: ");
                        asddesc = myUtilities.stringInput("\nEnter 1 for ascending order or 2 for descending order in the name: ");
                        
                        //Promp of the Query menu option 4
                        promp = "SELECT boo.booking_id, cus.name, COUNT(boo.service_id) AS numServices, SUM(ser.serviceCharge) AS sumTotal"
                                + " FROM detailBooking boo"
                                + " JOIN services ser ON ser.service_id = boo.service_id"
                                + " JOIN booking book ON book.booking_id = boo.booking_id"
                                + " JOIN infoCustomers cus ON cus.cust_id = book.cust_id"
                                + " GROUP BY boo.booking_id"
                                + " ORDER BY cus.name " + asddesc
                                + " LIMIT " + numInput + ";";
                        
                        rs = myUtilities.dbResult(DB, promp); //Query result
                        
                        //Bucle to show de result of the Query
                        System.out.printf("\n%-10s %-20s %-15s %-15s%n", "Book ID", "CUSTOMER", "SERVICES", "TOTAL\n");
                        while (rs.next()) {
                            id = rs.getInt("boo.booking_id");
                            name = rs.getString("cus.name");
                            counter = rs.getInt("numServices");
                            total = rs.getInt("sumTotal");

                            System.out.printf("%-10s %-20s %-15s %-15s%n", id, name, counter, total);
                        }
                        
                     
                    }
                    case 5 -> {
                        System.out.println("\n\n" + "************ " + menu[4] + " ************\n");
                        numInput = myUtilities.numericInput("\nEnter the number of records to filter: ");
                        asddesc = myUtilities.stringInput("\nEnter 1 for ascending order or 2 for descending order in the total price: ");
                        
                        //Promp of the Query menu option 5
                        promp = "SELECT car.car_id,car.carType, COALESCE(SUM(ser.serviceCharge), 0) AS totalPerCar"
                                + " FROM infoCars car"
                                + " LEFT JOIN booking boo"
                                + " ON car.car_id = boo.car_id"
                                + " LEFT JOIN detailBooking det"
                                + " ON boo.booking_id = det.booking_id"
                                + " LEFT JOIN services ser"
                                + " ON det.service_id = ser.service_id"
                                + " GROUP BY car.car_id, car.carType"
                                + " ORDER BY totalPerCar " + asddesc
                                + " LIMIT " + numInput + ";";
                        
                        rs = myUtilities.dbResult(DB, promp); //Query result
                        
                        System.out.printf("%-10s %-20s %-15s%n", "CAR ID", "CAR", "TOTAL\n");
                        //Bucle to show de result of the Query
                        while (rs.next()) {
                            id = rs.getInt("car.car_id");
                            name = rs.getString("car.carType");
                            total = rs.getInt("totalPerCar");

                            System.out.printf("%-10s %-20s %-15s%n", id, name, total);
                        }
                    }
                    case 6 -> {
                        System.out.println("\n\n" + "************ " + menu[5] + " ************\n");
                        
                        //Promp of the Query manu option 6
                        promp = "SELECT boo.booking_id, SUM(ser.serviceCharge) AS totalIncomeCompany "
                                + "FROM booking boo "
                                + "JOIN detailBooking det ON boo.booking_id = det.booking_id "
                                + "JOIN services ser ON ser.service_id = det.service_id; ";
                        
                        rs = myUtilities.dbResult(DB, promp); //Query result
                        
                        //Get the result of the Query
                        if (rs.next()){
                            total = rs.getDouble("totalIncomeCompany");
                            System.out.println("\nThe total income for the comany is: " + total);
                        } else {
                            System.out.println("There is no income for the company in the records.");
                        }
                    }
                    case 7 -> {
                        System.out.println("\n\n" + "************ " + menu[6] + " ************\n");
                        
                        //Promp of the Query manu option 7
                        promp = "SELECT "
                                + "MIN(totalPerBooking) As minimo, "
                                + "MAX(totalPerBooking) AS maximo, "
                                + "AVG(totalPerBooking) AS promedio "
                                + "FROM ("
                                + "SELECT boo.booking_id, SUM(ser.serviceCharge) AS totalPerBooking "
                                + "FROM booking boo "
                                + "JOIN detailBooking det ON boo.booking_id = det.booking_id "
                                + "JOIN services ser ON ser.service_id = det.service_id "
                                + "GROUP BY boo.booking_id) AS totalBooking;";
                        
                        rs = myUtilities.dbResult(DB, promp); //Query result
                        
                        //Get the result of the Query
                        if (rs.next()){
                            double minValue = rs.getDouble("minimo");
                            double maxValue = rs.getDouble("maximo");
                            double aveValue = rs.getDouble("promedio");
                            
                            System.out.println("\nThe company had the following statistics: \n");
                            System.out.println("A  minimum income of:  " + minValue);
                            System.out.println("A  maximum income of:  " + maxValue);
                            System.out.println("An average income of:  " + aveValue);
                        }
                    }
                    case 8 -> {
                        //Finishing the software
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
