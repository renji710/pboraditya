/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package UTS_Perpustakaan;

/**
 *
 * @author TIO
 */
public class Anggota {
    private final String kode;
    private final String nama;
    private final String kontak;

    public Anggota(String kode, String nama, String kontak) {
        this.kode = kode;
        this.nama = nama;
        this.kontak = kontak;
    }

    public String laporan() {
        return "[" + kode + "] " + nama + " (" + kontak + ")";
    }

}
