/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package DAO;

/**
 *
 * @author TIO
 */
import DB.DBConnect;
import Entitas.Akun;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AkunDAO {
    private static final String TABLE_NAME = "akun";

    // Method untuk menambahkan akun baru (dengan perubahan untuk mendapatkan generated key)
    public int tambahAkun(Akun akun) throws SQLException {
        String sql = "INSERT INTO " + TABLE_NAME +
                " (nama, email, password, no_telepon, jenis_akun) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnect.connect();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, akun.getNama());
            stmt.setString(2, akun.getEmail());
            stmt.setString(3, akun.getPassword()); // Pastikan password sudah di-hash
            stmt.setString(4, akun.getNoTelepon());
            stmt.setInt(5, akun.getJenisAkun());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedId = generatedKeys.getInt(1);
                        akun.setIdAkun(generatedId); // Set ID akun yang di-generate
                        return generatedId; // Kembalikan ID akun
                    } else {
                        throw new SQLException("Gagal mengambil ID akun: tidak ada ID yang dihasilkan.");
                    }
                }
            } else {
                throw new SQLException("Gagal menambahkan akun.");
            }
        }
    }

    // Method untuk mendapatkan data akun berdasarkan email (untuk login)
    public Akun getAkunByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE email = ?";

        try (Connection conn = DBConnect.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Akun akun = new Akun();
                    akun.setIdAkun(rs.getInt("id_akun"));
                    akun.setNama(rs.getString("nama"));
                    akun.setEmail(rs.getString("email"));
                    akun.setPassword(rs.getString("password"));
                    akun.setNoTelepon(rs.getString("no_telepon"));
                    akun.setJenisAkun(rs.getInt("jenis_akun"));
                    return akun;
                }
            }
        }
        return null; // Jika akun tidak ditemukan
    }
    
    // Method untuk mendapatkan data akun berdasarkan ID
    public Akun getAkunById(int idAkun) throws SQLException {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id_akun = ?";

        try (Connection conn = DBConnect.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idAkun);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Akun akun = new Akun();
                    akun.setIdAkun(rs.getInt("id_akun"));
                    akun.setNama(rs.getString("nama"));
                    akun.setEmail(rs.getString("email"));
                    akun.setPassword(rs.getString("password")); 
                    akun.setNoTelepon(rs.getString("no_telepon"));
                    akun.setJenisAkun(rs.getInt("jenis_akun"));
                    return akun;
                }
            }
        }
        return null; // Jika akun tidak ditemukan
    }

    // Method untuk mendapatkan semua data akun (hanya untuk admin)
    public List<Akun> getAllAkun() throws SQLException {
        String sql = "SELECT * FROM " + TABLE_NAME;
        List<Akun> daftarAkun = new ArrayList<>();

        try (Connection conn = DBConnect.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Akun akun = new Akun();
                akun.setIdAkun(rs.getInt("id_akun"));
                akun.setNama(rs.getString("nama"));
                akun.setEmail(rs.getString("email"));
                akun.setNoTelepon(rs.getString("no_telepon"));
                akun.setJenisAkun(rs.getInt("jenis_akun"));

                daftarAkun.add(akun);
            }
        }
        return daftarAkun;
    }

    // Method untuk mengupdate data akun 
    public boolean updateAkun(Akun akun) throws SQLException {
        String sql = "UPDATE " + TABLE_NAME + " SET nama = ?, email = ?, no_telepon = ?, jenis_akun = ? WHERE id_akun = ?";

        try (Connection conn = DBConnect.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, akun.getNama());
            stmt.setString(2, akun.getEmail());
            stmt.setString(3, akun.getNoTelepon());
            stmt.setInt(4, akun.getJenisAkun());
            stmt.setInt(5, akun.getIdAkun());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    // Method untuk mengupdate password akun (perlu penanganan khusus untuk keamanan)
    public boolean updatePassword(int idAkun, String newPassword) throws SQLException {
        String sql = "UPDATE " + TABLE_NAME + " SET password = ? WHERE id_akun = ?";

        try (Connection conn = DBConnect.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newPassword);
            stmt.setInt(2, idAkun);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    // Method untuk menghapus akun
    public boolean hapusAkun(int idAkun) throws SQLException {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE id_akun = ?";

        try (Connection conn = DBConnect.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idAkun);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
}
