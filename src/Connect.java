import java.sql.*;
public class Connect {
    public static Connection Con;
    public static void Connection() {
        try {
            String userName = "";
            String password = "";
            String url = "jdbc:mysql://localhost/complete_bd";
            Class.forName("com.mysql.jdbc.Driver").newInstance();//инициализация драйвера
            Con = DriverManager.getConnection(url, userName, password);
        } catch (Exception e) {
            System.err.println("Cannot connect to database server");
            e.printStackTrace();
        }
    }
}