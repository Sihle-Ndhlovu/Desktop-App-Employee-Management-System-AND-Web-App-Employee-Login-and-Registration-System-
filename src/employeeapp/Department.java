/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package employeeapp;
/**
 *
 * @author ndlov
 */
public class Department {
    private String depID;
    private String depName;
    private String depLocation;

    public Department(String depID, String depName, String depLocation) {
        this.depID = depID;
        this.depName = depName;
        this.depLocation = depLocation;
    }

    // Getters
    public String getDepID() {
        return depID;
    }

    public String getDepName() {
        return depName;
    }

    public String getDepLocation() {
        return depLocation;
    }
}