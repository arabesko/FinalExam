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
        
        
        int numInput = 0; //Variable por number input
        int inputUser = 0; //Variable of the menu selected
        
        //Variables to get result of the Query
        int id = 0; //Variable to get the id
        String name = "";
        int counter1 = 0;
        double price1 = 0;
        
        //Matrix for the options of the menu
        String[] menu = {"Numer of bookings by Customer", 
                         "Numbers of booking of all the Customers",
                         "Detail of the booking",
                         "List of bookings and detail with total service price",
                         "LISTADO DE CLIENTES SIN RESERVA",
                         "Ingreso totales por vehiculo",
                         "Ingreso total compañia",
                         "Exit"};
        
        //Promp of the menu
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
                        System.out.println("\n\n" + "************ " + menu[0] + " ************");
                        numInput = myUtilities.numericInput("\nInput the client id: ");
                        
                        //Promp of the Query manu option 1
                        promp = "SELECT cust.cust_id, cust.name, COUNT(book.cust_id) AS countCustomer "
                                + "FROM booking book "
                                + "JOIN infoCustomers cust "
                                + "ON book.cust_id = cust.cust_id "
                                + "WHERE cust.cust_id = " + numInput + ";";

                        rs = myUtilities.dbResult(DB, promp); //Query result
                        
                        //Read the only record
                        if (rs.next()){
                            id = rs.getInt("cust_id");
                            name = rs.getString("name");
                            counter1 = rs.getInt("countCustomer");
                            
                            if (counter1 > 0){
                                System.out.println("The client id: " + id + " - " + name + " have " + counter1 + " bookings");
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
                                + "GROUP BY cust.cust_id "
                                + "ORDER BY cust.name ASC;";
                        
                        rs = myUtilities.dbResult(DB, promp); //Query result
                        
                        //Bucle to show de result of the Query
                        System.out.printf("%-10s %-12s %-15s%n", "CUST ID", "NAME", "TOTAL BOOKING");
                        while (rs.next()) {
                            id = rs.getInt("cust_id");
                            name = rs.getString("name");
                            int total = rs.getInt("countCustomer");

                            System.out.printf("%-10d %-12s %-15d%n", id, name, total);
                        }
                        System.out.println("\n\n" + "**************************************");
                    }
                    case 3 -> {
                        System.out.println("\n\n" + "************ " + menu[2] + " ************");
                        numInput = myUtilities.numericInput("\nInput the booking id: ");
                        promp = "SELECT bo.booking_id, cus.name, car.carType "
                                + "FROM booking bo "
                                + "JOIN infoCustomers cus "
                                + "ON bo.cust_id = cus.cust_id "
                                + "JOIN infoCars car "
                                + "ON bo.car_id = car.car_id "
                                + "WHERE bo.booking_id = " + numInput + ";";
                        
                        rs = myUtilities.dbResult(DB, promp); //Query result
                        
                        if (rs.next()){
                            id = rs.getInt("bo.booking_id");
                            name = rs.getString("cus.name");
                            String car = rs.getString("car.carType");
                            System.out.println("The booking id: " + id + " of the cliente: " + name + " with a car type: " + car + ", have the bellow detail: \n");
                            
                            promp = "SELECT bo.service_id, se.serviceName, se.serviceCharge "
                                    + "FROM detailBooking bo "
                                    + "JOIN services se ON bo.service_id = se.service_id "
                                    + "WHERE bo.booking_id = " + id + ";";
                            
                            rs = myUtilities.dbResult(DB, promp); //Query result

                            System.out.printf("%-5s %-15s %-15s%n", "ID", "SERVICE", "PRICE EU\n");
                            while (rs.next()) {
                                id = rs.getInt("service_id");
                                name = rs.getString("serviceName");
                                price1 = rs.getInt("serviceCharge");
                                System.out.printf("%-5s %-15s %-15s%n", id, name, price1);
                            }
                        } else {
                                System.out.println("They are not records with this client id");
                            }
                    }
                    case 4 -> {
                        System.out.println("\n\n" + "************ " + menu[3] + " ************");
                        promp = "SELECT boo.booking_id, cus.name, COUNT(boo.service_id) AS numServices, SUM(ser.serviceCharge) AS sumTotal"
                                + " FROM detailBooking boo"
                                + " JOIN services ser ON ser.service_id = boo.service_id"
                                + " JOIN booking book ON book.booking_id = boo.booking_id"
                                + " JOIN infoCustomers cus ON cus.cust_id = book.cust_id"
                                + " GROUP BY boo.booking_id"
                                + " ORDER BY cus.name DESC;";
                        
                        rs = myUtilities.dbResult(DB, promp); //Query result
                        
                        counter1 = 0;
                        id = 0;
                        String customer = "";
                        double total = 0;

                        System.out.printf("%-10s %-20s %-15s %-15s%n", "Book ID", "CUSTOMER", "SERVICES", "TOTAL\n");
                        while (rs.next()) {
                            id = rs.getInt("boo.booking_id");
                            customer = rs.getString("cus.name");
                            counter1 = rs.getInt("numServices");
                            total = rs.getInt("sumTotal");

                            System.out.printf("%-10s %-20s %-15s %-15s%n", id, customer, counter1, total);
                        }
                        
                     
                    }
                    case 5 -> {
                        System.out.println("\n\n" + "************ " + menu[4] + " ************");
                    }
                    case 6 -> {
                        System.out.println("\n\n" + "************ " + menu[5] + " ************");
                    }
                    case 7 -> {
                        System.out.println("\n\n" + "************ " + menu[6] + " ************");
                    }
                    case 8 -> {
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
