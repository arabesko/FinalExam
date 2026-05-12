/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package finalexam;

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
        
        myUtilities.dataBaseConnection();
        
        int inputUser = 0;
        
        
        String[] menu = {"Servicios por Cliente", 
                         "Detalle del Booking",
                          "",
                          "",
                          "",
                          "",
                          "Exit"};
        
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
            
            switch(inputUser){
                case 1 -> {
                    System.out.println("\n\n" + "************ " + menu[0] + " ************");
                }
                case 2 -> {
                    System.out.println("\n\n" + "************ " + menu[1] + " ************");
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
        }
        
        
    }
    
}
