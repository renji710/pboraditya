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
import Entitas.Pemesanan;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PemesananDAO {
    private static final String TABLE_NAME = "pemesanan";

    // Method untuk membuat pemesanan baru
    public boolean tambahPemesanan(Pemesanan pemesanan) throws SQLException {
        String sql = "INSERT INTO " + TABLE_NAME +
                " (id_perjalanan, id_akun, jumlah_tiket, total_harga, status) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnect.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, pemesanan.getIdPerjalanan());
            stmt.setInt(2, pemesanan.getIdAkun());
            stmt.setInt(3, pemesanan.getJumlahTiket());
            stmt.setBigDecimal(4, pemesanan.getTotalHarga());
            stmt.setString(5, pemesanan.getStatus());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    // Method untuk mendapatkan semua data pemesanan
    public List<Pemesanan> getAllPemesanan() throws SQLException {
        String sql = "SELECT * FROM " + TABLE_NAME;
        List<Pemesanan> daftarPemesanan = new ArrayList<>();

        try (Connection conn = DBConnect.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Pemesanan pemesanan = new Pemesanan();
                pemesanan.setIdPemesanan(rs.getInt("id_pemesanan"));
                pemesanan.setIdPerjalanan(rs.getInt("id_perjalanan"));
                pemesanan.setIdAkun(rs.getInt("id_akun"));
                pemesanan.setJumlahTiket(rs.getInt("jumlah_tiket"));
                pemesanan.setTotalHarga(rs.getBigDecimal("total_harga"));
                pemesanan.setTanggalPemesanan(rs.getTimestamp("tanggal_pemesanan"));
                pemesanan.setStatus(rs.getString("status")); // Ambil status

                daftarPemesanan.add(pemesanan);
            }
        }
        return daftarPemesanan;
    }

    // Method untuk mendapatkan pemesanan berdasarkan ID Pemesanan
    public Pemesanan getPemesananById(int idPemesanan) throws SQLException {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id_pemesanan = ?";

        try (Connection conn = DBConnect.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPemesanan);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Pemesanan pemesanan = new Pemesanan();
                    pemesanan.setIdPemesanan(rs.getInt("id_pemesanan"));
                    pemesanan.setIdPerjalanan(rs.getInt("id_perjalanan"));
                    pemesanan.setIdAkun(rs.getInt("id_akun"));
                    pemesanan.setJumlahTiket(rs.getInt("jumlah_tiket"));
                    pemesanan.setTotalHarga(rs.getBigDecimal("total_harga"));
                    pemesanan.setTanggalPemesanan(rs.getTimestamp("tanggal_pemesanan"));
                    pemesanan.setStatus(rs.getString("status")); // Ambil status

                    return pemesanan;
                }
            }
        }
        return null; // Jika tidak ditemukan
    }

    // Method untuk mendapatkan pemesanan berdasarkan ID Akun
    public List<Pemesanan> getPemesananByAkunId(int idAkun) throws SQLException {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id_akun = ?";
        List<Pemesanan> daftarPemesanan = new ArrayList<>();

        try (Connection conn = DBConnect.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idAkun);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Pemesanan pemesanan = new Pemesanan();
                    pemesanan.setIdPemesanan(rs.getInt("id_pemesanan"));
                    pemesanan.setIdPerjalanan(rs.getInt("id_perjalanan"));
                    pemesanan.setIdAkun(rs.getInt("id_akun"));
                    pemesanan.setJumlahTiket(rs.getInt("jumlah_tiket"));
                    pemesanan.setTotalHarga(rs.getBigDecimal("total_harga"));
                    pemesanan.setTanggalPemesanan(rs.getTimestamp("tanggal_pemesanan"));
                    pemesanan.setStatus(rs.getString("status")); // Ambil status

                    daftarPemesanan.add(pemesanan);
                }
            }
        }
        return daftarPemesanan;
    }

    // Method untuk mengupdate data pemesanan (termasuk status)
    public boolean updatePemesanan(Pemesanan pemesanan) throws SQLException {
        String sql = "UPDATE " + TABLE_NAME + " SET id_perjalanan = ?, id_akun = ?, jumlah_tiket = ?, total_harga = ?, status = ? WHERE id_pemesanan = ?";

        try (Connection conn = DBConnect.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, pemesanan.getIdPerjalanan());
            stmt.setInt(2, pemesanan.getIdAkun());
            stmt.setInt(3, pemesanan.getJumlahTiket());
            stmt.setBigDecimal(4, pemesanan.getTotalHarga());
            stmt.setString(5, pemesanan.getStatus());
            stmt.setInt(6, pemesanan.getIdPemesanan());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    // Method untuk menghapus data pemesanan
    public boolean hapusPemesanan(int idPemesanan) throws SQLException {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE id_pemesanan = ?";

        try (Connection conn = DBConnect.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPemesanan);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
    
    public List<Pemesanan> getPemesananByTanggal(Date tanggal) throws SQLException {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE DATE(tanggal_pemesanan) = ?";
        List<Pemesanan> daftarPemesanan = new ArrayList<>();

        try (Connection conn = DBConnect.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, tanggal);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Pemesanan pemesanan = new Pemesanan();
                    pemesanan.setIdPemesanan(rs.getInt("id_pemesanan"));
                    pemesanan.setIdPerjalanan(rs.getInt("id_perjalanan"));
                    pemesanan.setIdAkun(rs.getInt("id_akun"));
                    pemesanan.setJumlahTiket(rs.getInt("jumlah_tiket"));
                    pemesanan.setTotalHarga(rs.getBigDecimal("total_harga"));
                    pemesanan.setTanggalPemesanan(rs.getTimestamp("tanggal_pemesanan"));
                    pemesanan.setStatus(rs.getString("status")); // Ambil status

                    daftarPemesanan.add(pemesanan);
                }
            }
        }
        return daftarPemesanan;
    }

    public List<Pemesanan> getPemesananByBulan(int bulan, int tahun) throws SQLException {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE MONTH(tanggal_pemesanan) = ? AND YEAR(tanggal_pemesanan) = ?";
        List<Pemesanan> daftarPemesanan = new ArrayList<>();

        try (Connection conn = DBConnect.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, bulan);
            stmt.setInt(2, tahun);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Pemesanan pemesanan = new Pemesanan();
                    pemesanan.setIdPemesanan(rs.getInt("id_pemesanan"));
                    pemesanan.setIdPerjalanan(rs.getInt("id_perjalanan"));
                    pemesanan.setIdAkun(rs.getInt("id_akun"));
                    pemesanan.setJumlahTiket(rs.getInt("jumlah_tiket"));
                    pemesanan.setTotalHarga(rs.getBigDecimal("total_harga"));
                    pemesanan.setTanggalPemesanan(rs.getTimestamp("tanggal_pemesanan"));
                    pemesanan.setStatus(rs.getString("status")); // Ambil status

                    daftarPemesanan.add(pemesanan);
                }
            }
        }
        return daftarPemesanan;
    }
}
