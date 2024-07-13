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
import Entitas.Perjalanan;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PerjalananDAO {
    private static final String TABLE_NAME = "perjalanan";

    // Method untuk menambahkan perjalanan baru (dengan perubahan untuk mendapatkan generated key)
    public int tambahPerjalanan(Perjalanan perjalanan) throws SQLException {
        String sql = "INSERT INTO " + TABLE_NAME + " (asal, tujuan, tanggal, harga, stok_tiket) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnect.connect();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, perjalanan.getAsal());
            stmt.setString(2, perjalanan.getTujuan());
            stmt.setDate(3, perjalanan.getTanggal());
            stmt.setBigDecimal(4, perjalanan.getHarga());
            stmt.setInt(5, perjalanan.getStokTiket());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedId = generatedKeys.getInt(1);
                        perjalanan.setIdPerjalanan(generatedId);
                        return generatedId;
                    } else {
                        throw new SQLException("Gagal mengambil ID perjalanan: tidak ada ID yang dihasilkan.");
                    }
                }
            } else {
                throw new SQLException("Gagal menambahkan perjalanan.");
            }
        }
    }

    // Method untuk mendapatkan semua data perjalanan
    public List<Perjalanan> getAllPerjalanan() throws SQLException {
        String sql = "SELECT * FROM " + TABLE_NAME;
        List<Perjalanan> daftarPerjalanan = new ArrayList<>();

        try (Connection conn = DBConnect.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Perjalanan perjalanan = new Perjalanan();
                perjalanan.setIdPerjalanan(rs.getInt("id_perjalanan"));
                perjalanan.setAsal(rs.getString("asal"));
                perjalanan.setTujuan(rs.getString("tujuan"));
                perjalanan.setTanggal(rs.getDate("tanggal"));
                perjalanan.setHarga(rs.getBigDecimal("harga"));
                perjalanan.setStokTiket(rs.getInt("stok_tiket"));

                daftarPerjalanan.add(perjalanan);
            }
        }
        return daftarPerjalanan;
    }

    // Method untuk mencari perjalanan berdasarkan asal dan tujuan
    public List<Perjalanan> cariPerjalanan(String keyword) throws SQLException {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE asal LIKE ? OR tujuan LIKE ?";
        List<Perjalanan> daftarPerjalanan = new ArrayList<>();

        try (Connection conn = DBConnect.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + keyword + "%"); 
            stmt.setString(2, "%" + keyword + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Perjalanan perjalanan = new Perjalanan();
                    perjalanan.setIdPerjalanan(rs.getInt("id_perjalanan"));
                    perjalanan.setAsal(rs.getString("asal"));
                    perjalanan.setTujuan(rs.getString("tujuan"));
                    perjalanan.setTanggal(rs.getDate("tanggal"));
                    perjalanan.setHarga(rs.getBigDecimal("harga"));
                    perjalanan.setStokTiket(rs.getInt("stok_tiket"));

                    daftarPerjalanan.add(perjalanan);
                }
            }
        }
        return daftarPerjalanan;
    }

    // Method untuk mendapatkan perjalanan berdasarkan ID
    public Perjalanan getPerjalananById(int idPerjalanan) throws SQLException {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id_perjalanan = ?";

        try (Connection conn = DBConnect.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPerjalanan);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Perjalanan perjalanan = new Perjalanan();
                    perjalanan.setIdPerjalanan(rs.getInt("id_perjalanan"));
                    perjalanan.setAsal(rs.getString("asal"));
                    perjalanan.setTujuan(rs.getString("tujuan"));
                    perjalanan.setTanggal(rs.getDate("tanggal"));
                    perjalanan.setHarga(rs.getBigDecimal("harga"));
                    perjalanan.setStokTiket(rs.getInt("stok_tiket"));
                    return perjalanan;
                }
            }
        }
        return null; // Jika tidak ditemukan
    }

    // Method untuk mengupdate data perjalanan
    public boolean updatePerjalanan(Perjalanan perjalanan) throws SQLException {
        String sql = "UPDATE " + TABLE_NAME + " SET asal = ?, tujuan = ?, tanggal = ?, harga = ?, stok_tiket = ? WHERE id_perjalanan = ?";

        try (Connection conn = DBConnect.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, perjalanan.getAsal());
            stmt.setString(2, perjalanan.getTujuan());
            stmt.setDate(3, perjalanan.getTanggal());
            stmt.setBigDecimal(4, perjalanan.getHarga());
            stmt.setInt(5, perjalanan.getStokTiket());
            stmt.setInt(6, perjalanan.getIdPerjalanan());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    // Method untuk menghapus data perjalanan
    public boolean hapusPerjalanan(int idPerjalanan) throws SQLException {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE id_perjalanan = ?";

        try (Connection conn = DBConnect.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPerjalanan);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
}
