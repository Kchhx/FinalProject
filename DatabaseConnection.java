import java.sql.*;


public class DatabaseConnection{

    public static Connection getConnection() throws Exception {

        String url = "jdbc:mysql://localhost:3306/myhotel";
        String username = "root";
        String password = "";
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return connection;
    }

}
