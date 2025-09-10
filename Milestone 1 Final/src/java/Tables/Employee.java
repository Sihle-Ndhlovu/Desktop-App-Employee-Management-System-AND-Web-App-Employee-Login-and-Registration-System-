package Tables;

public class Employee {
    private int employeeId;
    private String fullName, department, role, email, phone, password;

    // Constructor
    public Employee(String fullName, String department, String role, String email, String phone, String password) {
        this.fullName = fullName;
        this.department = department;
        this.role = role;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    // Getters and Setters
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
}

