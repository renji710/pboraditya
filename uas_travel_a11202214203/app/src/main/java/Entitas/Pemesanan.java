/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package Entitas;

/**
 *
 * @author TIO
 */
import java.math.BigDecimal;
import java.sql.Timestamp;

public class Pemesanan {
    private int idPemesanan;
    private int idPerjalanan;
    private int idAkun;
    private int jumlahTiket;
    private BigDecimal totalHarga;
    private Timestamp tanggalPemesanan; // Menggunakan java.sql.Timestamp
    private String status;

    // Constructor kosong
    public Pemesanan() {
    }

    // Constructor dengan parameter (tanpa idPemesanan dan tanggalPemesanan)
    public Pemesanan(int idPerjalanan, int idAkun, int jumlahTiket, BigDecimal totalHarga, String status) {
        this.idPerjalanan = idPerjalanan;
        this.idAkun = idAkun;
        this.jumlahTiket = jumlahTiket;
        this.totalHarga = totalHarga;
        this.status = status;
    }

    // Getter dan Setter
    public int getIdPemesanan() {
        return idPemesanan;
    }

    public void setIdPemesanan(int idPemesanan) {
        this.idPemesanan = idPemesanan;
    }

    public int getIdPerjalanan() {
        return idPerjalanan;
    }

    public void setIdPerjalanan(int idPerjalanan) {
        this.idPerjalanan = idPerjalanan;
    }

    public int getIdAkun() {
        return idAkun;
    }

    public void setIdAkun(int idAkun) {
        this.idAkun = idAkun;
    }

    public int getJumlahTiket() {
        return jumlahTiket;
    }

    public void setJumlahTiket(int jumlahTiket) {
        this.jumlahTiket = jumlahTiket;
    }

    public BigDecimal getTotalHarga() {
        return totalHarga;
    }

    public void setTotalHarga(BigDecimal totalHarga) {
        this.totalHarga = totalHarga;
    }

    public Timestamp getTanggalPemesanan() {
        return tanggalPemesanan;
    }

    public void setTanggalPemesanan(Timestamp tanggalPemesanan) {
        this.tanggalPemesanan = tanggalPemesanan;
    }
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Pemesanan{" +
                "idPemesanan=" + idPemesanan +
                ", idPerjalanan=" + idPerjalanan +
                ", idAkun=" + idAkun +
                ", jumlahTiket=" + jumlahTiket +
                ", totalHarga=" + totalHarga +
                ", tanggalPemesanan=" + tanggalPemesanan +
                ", status=" + status +
                '}';
    }
}
