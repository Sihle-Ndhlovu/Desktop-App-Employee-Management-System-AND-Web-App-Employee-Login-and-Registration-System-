package employeeapp;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

// Department class
class Department {
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

// Main application class
public class DepartmentApp extends JFrame {

    // ViewDepartment class
    public static class ViewDepartment extends JFrame {
        private JTable departmentTable;
        private DefaultTableModel tableModel;

        public ViewDepartment(List<Department> departments) {
            setTitle("View Departments");
            setSize(600, 400);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLocationRelativeTo(null);
            
            // Panel for the table
            JPanel panel = new JPanel(new BorderLayout());

            // Table column names
            String[] columns = {"Department ID", "Department Name", "Department Location"};
            
            // Prepare data for the table
            Object[][] data = new Object[departments.size()][3];
            for (int i = 0; i < departments.size(); i++) {
                Department dep = departments.get(i);
                data[i][0] = dep.getDepID();
                data[i][1] = dep.getDepName();
                data[i][2] = dep.getDepLocation();
            }

            // Table model
            tableModel = new DefaultTableModel(data, columns);
            departmentTable = new JTable(tableModel);
            JScrollPane scrollPane = new JScrollPane(departmentTable);
            
            panel.add(scrollPane, BorderLayout.CENTER);
            add(panel);
        }

        // Method to show the ViewDepartment frame
        public void showFrame() {
            SwingUtilities.invokeLater(() -> {
                this.setVisible(true);
            });
        }
    }

    // MainPage class
    public DepartmentApp() {
        setTitle("Main Page");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JButton viewDepartmentButton = new JButton("View Departments");
        viewDepartmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create some sample departments
                List<Department> departments = new ArrayList<>();
                departments.add(new Department("101", "IT", "Building A"));
                departments.add(new Department("102", "HR", "Building B"));
                departments.add(new Department("103", "Finance", "Building C"));

                // Open the ViewDepartment frame with the sample data
                ViewDepartment viewDepartment = new ViewDepartment(departments);
                viewDepartment.showFrame();
            }
        });

        add(viewDepartmentButton);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new DepartmentApp().setVisible(true);
        });
    }
}