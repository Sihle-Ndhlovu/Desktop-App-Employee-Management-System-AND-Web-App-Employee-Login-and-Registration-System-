/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package employeeapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;


/**
 *
 * @author Dineo
 */
public class DBConnection {
    private static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    
    private static final String JDBC_URL = "jdbc:derby:EmployeeDB;create=true";
    
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
    
    public void CreateTable(){
        try{
            String query = "CREATE TABLE tblEmployee("
                    + "EmployeeID VARCHAR(20)NOT NULL PRIMARY KEY,"
                    + "Name VARCHAR(50), "
                    + "Surname VARCHAR(50), "
                    + "DateofBirth DATE, "
                    + "Gender VARCHAR(20), "
                    + "Contact VARCHAR(50), "
                    + "Email VARCHAR(50),"
                    + "DepartmentId VARCHAR(20),"
                    + "RoleId VARCHAR(20),"
                    + "EmployeeType VARCHAR(20))";
            this.con.createStatement().execute(query);
            System.out.println("Table Created");
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }
    
    public void dropTable(){
        try{
            String query = "DROP TABLE tblEmployee";
            this.con.createStatement().execute(query);
            System.out.println("Table Dropped");
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    
    public void insertEmployees(Employee employee) throws SQLException
    {
        String query = "INSERT INTO tblEmployee(EmployeeID,Name, Surname, DateofBirth, Gender, Contact, Email, DepartmentId, RoleId, EmployeeType)"
                + " VALUES(?,?,?,?,?,?,?,?,?,?)";
        
        try(PreparedStatement pstate = con.prepareStatement(query))
        {
            pstate.setString(1, employee.getEmpId());
            pstate.setString(2, employee.getName());
            pstate.setString(3, employee.getSurname());
            
            LocalDate localDate = employee.getDOB();
            java.sql.Date sqlDOB = java.sql.Date.valueOf(localDate);
            pstate.setDate(4, sqlDOB);
            
            pstate.setString(5, employee.getGender());
            pstate.setString(6, employee.getContact());
            pstate.setString(7, employee.getEmail());
            pstate.setString(8, employee.getDepartmentId());
            pstate.setString(9, employee.getRoleId());
            pstate.setString(10, employee.getEmpType());
            
            pstate.executeUpdate();
        }catch(SQLException ex)
        {
            System.out.println("Error " + ex.getMessage());
            ex.printStackTrace();
        }   
        
    }
    
    public List<Employee> getEmployees() throws SQLException
    {
        List<Employee> employees = new ArrayList<>();
        String query = "SELECT * FROM tblEmployee";
        
        try(Statement state = con.createStatement(); ResultSet result = state.executeQuery(query))
        {
            while(result.next()){
                String empID = result.getString("EmployeeID");
                String name = result.getString("Name");
                String surname = result.getString("Surname");
                LocalDate dob = result.getDate("DateofBirth").toLocalDate();
                String gender = result.getString("Gender");
                String contact = result.getString("Contact");
                String email = result.getString("Email");
                String depID = result.getString("DepartmentID");
                String roleID = result.getString("RoleID");
                String empType = result.getString("EmployeeType");
                
                Employee employee = new Employee(empID, name, surname, dob, gender, contact, email, depID, roleID, empType);
                employees.add(employee);
            }
            
        }
        return employees;
        
    }
    
    public void updateEmployee(Employee employee) throws SQLException {
    String sql = "UPDATE employees SET name = ?, surname = ?, dob = ?, gender = ?, contact = ?, email = ?, dep_id = ?, role_id = ?, emp_type = ? WHERE emp_id = ?";
    try (PreparedStatement pstate = con.prepareStatement(sql)) {
        pstate.setString(1, employee.getName());
        pstate.setString(2, employee.getSurname());
        pstate.setDate(3, java.sql.Date.valueOf(employee.getDOB()));
        pstate.setString(4, employee.getGender());
        pstate.setString(5, employee.getContact());
        pstate.setString(6, employee.getEmail());
        pstate.setString(7, employee.getDepartmentId());
        pstate.setString(8, employee.getRoleId());
        pstate.setString(9, employee.getEmpType());
        pstate.setString(10, employee.getEmpId());
        pstate.executeUpdate(); 
    }
}
    public void deleteEmployee( String empID) throws SQLException {
        String query = "DELETE FROM employee WHERE emp_id=?";
            try (Connection conn = DriveManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                    PreparedStatement pstate = conn.prepareStatement(query)) 
            { pstate.setString(1, empID);
              pstate.executeUpdate();}
    }
}
