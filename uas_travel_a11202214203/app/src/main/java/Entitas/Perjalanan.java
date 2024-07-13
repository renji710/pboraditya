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
import java.sql.Date;

public class Perjalanan {
    private int idPerjalanan;
    private String asal;
    private String tujuan;
    private Date tanggal;
    private BigDecimal harga;
    private int stokTiket;

    // Constructor kosong
    public Perjalanan() {
    }

    // Constructor dengan parameter
    public Perjalanan(String asal, String tujuan, Date tanggal, BigDecimal harga, int stokTiket) {
        this.asal = asal;
        this.tujuan = tujuan;
        this.tanggal = tanggal;
        this.harga = harga;
        this.stokTiket = stokTiket;
    }

    // Getter dan Setter untuk semua atribut
    public int getIdPerjalanan() {
        return idPerjalanan;
    }

    public void setIdPerjalanan(int idPerjalanan) {
        this.idPerjalanan = idPerjalanan;
    }

    public String getAsal() {
        return asal;
    }

    public void setAsal(String asal) {
        this.asal = asal;
    }

    public String getTujuan() {
        return tujuan;
    }

    public void setTujuan(String tujuan) {
        this.tujuan = tujuan;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public BigDecimal getHarga() {
        return harga;
    }

    public void setHarga(BigDecimal harga) {
        this.harga = harga;
    }

    public int getStokTiket() {
        return stokTiket;
    }

    public void setStokTiket(int stokTiket) {
        this.stokTiket = stokTiket;
    }

    @Override
    public String toString() {
        return "Perjalanan{" +
                "idPerjalanan=" + idPerjalanan +
                ", asal='" + asal + '\'' +
                ", tujuan='" + tujuan + '\'' +
                ", tanggal=" + tanggal +
                ", harga=" + harga +
                ", stokTiket=" + stokTiket +
                '}';
    }
}
