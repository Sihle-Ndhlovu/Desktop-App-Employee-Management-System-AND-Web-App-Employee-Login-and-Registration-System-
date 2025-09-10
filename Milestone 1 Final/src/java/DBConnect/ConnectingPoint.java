
package DBConnect;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConnectingPoint {
    String url = "jdbc:postgresql://localhost:5432/EmployeeDB";
    String usr = "postgres";
    String password = "12457";
    
    private static final String Driver = "org.postgresql.Driver";
    private Connection con;
    
    public ConnectingPoint(){
    con = null;
    
    }
    public Connection getCon() throws ClassNotFoundException, SQLException {
        try {
            // Load the driver class
            Class.forName(Driver);
            // Get a connection to the database
            con = DriverManager.getConnection(url, usr, password);
            if (con != null) {
                System.out.println("Connected to database");
            }
        } catch (ClassNotFoundException ex) {
            System.out.println("Could not load driver class: " + ex.getMessage());
            throw ex;
        } catch (SQLException ex) {
            System.out.println("Could not connect to database: " + ex.getMessage());
            throw ex; 
        }
        return con;
    }
}
