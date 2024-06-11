/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package UTS_Perpustakaan;

/**
 *
 * @author TIO
 */
public class Koleksi {
    private final String judul;
    private final String penulis;
    private final int jumlah;

    public Koleksi(String judul, String penulis, int jumlah) {
        this.judul = judul;
        this.penulis = penulis;
        this.jumlah = jumlah;
    }

    public String laporan() {
        return judul + "(" + penulis + ") : " + jumlah;
    }

}
