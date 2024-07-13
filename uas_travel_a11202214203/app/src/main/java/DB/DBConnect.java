/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package DB;

/**
 *
 * @author TIO
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {
    private static final String URL = "jdbc:mysql://localhost:3306/tiket_travel";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static Connection instance;

    private DBConnect() {
        // Private constructor to prevent instantiation
    }

    public static Connection connect() throws SQLException {
        if (instance == null || instance.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                instance = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Koneksi ke MySQL berhasil.");
            } catch (SQLException e) {
                System.out.println("Error koneksi ke database: " + e.getMessage());
            } catch (ClassNotFoundException e) {
                System.out.println("Error memuat driver MySQL: " + e.getMessage());
            }
        }
        return instance;
    }
}
