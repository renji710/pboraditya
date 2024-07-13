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
import java.sql.SQLException;
import java.sql.Statement;

public class DBSetup {
    public static void createTables() {
        String sqlPerjalanan = "CREATE TABLE IF NOT EXISTS perjalanan (\n"
                + " id_perjalanan INT AUTO_INCREMENT PRIMARY KEY,\n"
                + " asal VARCHAR(255) NOT NULL,\n"
                + " tujuan VARCHAR(255) NOT NULL,\n"
                + " tanggal DATE NOT NULL,\n"
                + " harga DECIMAL(10,2) NOT NULL,\n"
                + " stok_tiket INT NOT NULL\n"
                + ")";

        // Tabel akun (diganti dari tabel pelanggan)
        String sqlAkun = "CREATE TABLE IF NOT EXISTS akun (\n"
                + " id_akun INT AUTO_INCREMENT PRIMARY KEY,\n"
                + " nama VARCHAR(255) NOT NULL,\n"
                + " email VARCHAR(255) UNIQUE NOT NULL,\n" // Email harus unik
                + " password VARCHAR(255) NOT NULL,\n"
                + " no_telepon VARCHAR(15),\n"
                + " jenis_akun TINYINT NOT NULL DEFAULT 1" // 0: Admin, 1: User Biasa 
                + ")";

        String sqlPemesanan = "CREATE TABLE IF NOT EXISTS pemesanan (\n"
                + " id_pemesanan INT AUTO_INCREMENT PRIMARY KEY,\n"
                + " id_perjalanan INT NOT NULL,\n"
                + " id_akun INT NOT NULL, " // Menggunakan id_akun dari tabel akun
                + " jumlah_tiket INT NOT NULL,\n"
                + " total_harga DECIMAL(10,2) NOT NULL,\n"
                + " tanggal_pemesanan TIMESTAMP DEFAULT CURRENT_TIMESTAMP,\n"
                + " status VARCHAR(15) NOT NULL DEFAULT 'Belum Dibayar', \n"
                + " FOREIGN KEY (id_perjalanan) REFERENCES perjalanan(id_perjalanan),\n"
                + " FOREIGN KEY (id_akun) REFERENCES akun(id_akun)" // Referensi ke tabel akun
                + ")";

        try (Connection conn = DBConnect.connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sqlPerjalanan);
            stmt.execute(sqlAkun);
            stmt.execute(sqlPemesanan);
            System.out.println("Tabel-tabel berhasil dibuat atau sudah ada.");
        } catch (SQLException e) {
            System.out.println("Error saat membuat tabel: " + e.getMessage());
        }
    }
}
