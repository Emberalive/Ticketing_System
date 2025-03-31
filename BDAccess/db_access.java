package BDAccess;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class db_access {
    private static final String URL = "jdbc:postgresql://192.168.0.16:5432/itticketing";
    private static final String USER = "samuel";
    private static final String PASSWORD = "QwErTy1243!";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            if (conn != null) {
                System.out.println("Connected to PostgreSQL successfully!");
            }
        } catch (SQLException e) {
            System.out.println("Connection failed!" + e);
            e.printStackTrace();
        }
    }
}
