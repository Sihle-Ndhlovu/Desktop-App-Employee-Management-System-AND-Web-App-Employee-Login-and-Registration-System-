/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package employeeapp;

import java.time.LocalDate;

/**
 *
 * @author Dineo
 */
public class Employee {
    private String empId;
    private String name;
    private String surname;
    private LocalDate dob;
    private String gender;
    private String contact;
    private String email;
    private String departmentId; //FK in Employee table to link the Employee table and Departments table
    private String roleId; //FK in Employee table to link the Employee table to Roles table
    private String empType;
     
    
    public Employee(String empId, String name, String surname, LocalDate dob, String gender, String contact, String email, String departmentId,String roleId,String empType)
    {
        this.empId = empId;
        this.name = name;
        this.surname = surname;
        this.dob = dob;
        this.gender = gender;
        this.contact = contact;
        this.email = email;
        this.departmentId = departmentId;
        this.roleId = roleId;
        this.empType = empType;
        
    }
    
    public String getEmpId()
    {
        return empId;
    }
    public String getName()
    {
        return name;
    }
    public String getSurname()
    {
        return surname;
    }
    public LocalDate getDOB()
    {
        return dob;
    }
    public String getGender()
    {
        return gender;
    }
    public String getContact()
    {
        return contact;
    }
    public String getEmail()
    {
        return email;
    }
     public String getDepartmentId()
    {
        return departmentId;
    }
      public String getRoleId()
    {
        return roleId;
    }
    public String getEmpType()
    {
        return empType;
    }
    
    
    
    public void setEmpId(String empId)
    {
        this.empId = empId;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public void setSurname(String surname)
    {
        this.surname = surname;
    }
    public void setDOB(LocalDate dob)
    {
        this.dob = dob;
    }
    public void setGender(String gender)
    {
        this.gender = gender;
    }
    public void setContact(String contact)
    {
        this.contact = contact;
    }
    public void setEmail(String email)
    {
        this.email = email;
    }
    public void setDepartmentId(String departmentId)
    {
        this.departmentId = departmentId;
    }
    public void setRoleId(String RoleId)
    {
        this.roleId = RoleId;
    }
   public void setEmpType(String empType)
    {
        this.empType = empType;
    } 
   
}
