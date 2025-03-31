package BDAccess;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class db_access {
    private static final String URL = "jdbc:postgresql://86.19.219.159:5432/itticketing";
    private static final String USER = "samuel";
    private static final String PASSWORD = "QwErTy1243!";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            if (conn != null && conn.isValid(2)) {
                System.out.println("Connected to PostgreSQL successfully!");
            } else {
                System.out.println("Failed to connect to PostgreSQL!");
            }
        } catch (SQLException e) {
            System.out.println("Connection failed!" + e);
            e.printStackTrace();
        }
    }
}
