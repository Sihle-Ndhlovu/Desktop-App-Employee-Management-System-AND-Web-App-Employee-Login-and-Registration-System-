/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package employeeapp;

/**
 *
 * @author Dineo
 */
public class EmployeeApp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ClassNotFoundException {
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Dashboard().setVisible(true);
                DBConnection db = new DBConnection();
                try{
                    db.connect();
                    
                }catch(ClassNotFoundException ex){
                    ex.printStackTrace();
                } 
            }
        });
    }
}
