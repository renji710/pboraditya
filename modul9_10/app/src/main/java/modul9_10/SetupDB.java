/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package modul9_10;

/**
 *
 * @author TIO
 */
import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

public class SetupDB {
    public static void setup() {
        String[] sqlStatements = {
            "CREATE TABLE IF NOT EXISTS folder (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "name TEXT);",
            
            "CREATE TABLE IF NOT EXISTS userdata (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "username TEXT, " +
            "password TEXT, " +
            "fullname TEXT);",
            
            "CREATE UNIQUE INDEX IF NOT EXISTS user_username_IDX " +
            "ON userdata (username);",
            
            "CREATE TABLE IF NOT EXISTS passwordstore (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "name TEXT, " +
            "username TEXT, " +
            "password TEXT, " +
            "hashkey TEXT, " +
            "score REAL, " +
            "category INTEGER, " +
            "user_id INTEGER, " +
            "folder_id INTEGER, " +
            "CONSTRAINT passwordstore_user_FK FOREIGN KEY (user_id) REFERENCES userdata(id) " +
            "ON DELETE RESTRICT ON UPDATE RESTRICT, " +
            "CONSTRAINT passwordstore_folder_FK FOREIGN KEY (folder_id) REFERENCES folder(id) " +
            "ON DELETE SET NULL ON UPDATE SET NULL);",
            
            "CREATE TABLE IF NOT EXISTS additional (" +
            "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
            "entry_key TEXT, " +
            "entry_value TEXT, " +
            "is_password INTEGER, " +
            "password_id INTEGER, " +
            "CONSTRAINT additional_passwordstore_FK FOREIGN KEY (password_id) REFERENCES passwordstore(id) " +
            "ON DELETE CASCADE ON UPDATE CASCADE);"
        };

        try (Connection conn = DBConnect.connect()) {
            Statement stmt = conn.createStatement();
            for (String sql : sqlStatements) {
                stmt.execute(sql);
            }
            System.out.println("Tabel-tabel berhasil dibuat atau sudah ada.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        setup();
    }
}
