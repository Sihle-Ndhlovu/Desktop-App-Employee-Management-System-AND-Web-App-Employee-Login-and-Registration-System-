/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package employeeapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author ndlov
 */
public class DepartmentDB {
    public class DBConnection {
    private static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    
    private static final String JDBC_URL = "jdbc:derby:DepartmentDB;create=true";
    
    Connection con;
    
    public DBConnection(){   
        try {
        connect(); // Ensure connection is established when DBConnection is instantiated
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    }
        
    }
    
    public void connect() throws ClassNotFoundException
    {
        try{
            Class.forName(DRIVER);
            this.con = DriverManager.getConnection(JDBC_URL);
            if(this.con != null)
            {
                System.out.println("Connected to database");
            }
        }catch(SQLException ex)
        {
            ex.printStackTrace();
        }
    }  
}
}
