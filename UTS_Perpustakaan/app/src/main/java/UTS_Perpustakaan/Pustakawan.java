/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package UTS_Perpustakaan;

/**
 *
 * @author TIO
 */

import java.util.ArrayList;

public class Pustakawan {
    private boolean loggedIn;
    private final ArrayList<Koleksi> koleksiPerpus;
    private final ArrayList<Anggota> anggotaPerpus;

    public Pustakawan() {
        loggedIn = false;
        koleksiPerpus = new ArrayList<>();
        anggotaPerpus = new ArrayList<>();
    }
    
    public void logIn(String nip, String nama) {
        loggedIn = true;
    }
    
    public void signOut() {
        loggedIn = false;
    }
    
    public void inputKoleksi(String judul, String penulis, int jml) {
        if (!loggedIn) {
            System.out.println("============================================================");
            System.out.println("Anda harus login untuk menginput koleksi");
            System.out.println("============================================================");
            return;
        }
        Koleksi koleksi = new Koleksi(judul, penulis, jml);
        koleksiPerpus.add(koleksi);
    }
    
    public void inputAnggota (String kode, String nama, String kontak){
        if (!loggedIn) {
            System.out.println("============================================================");
            System.out.println("Anda harus login untuk menginput koleksi");
            System.out.println("============================================================");
            return;
        }
        Anggota anggota = new Anggota (kode, nama, kontak);
        anggotaPerpus.add(anggota);
    }
    
    public void cetakListKoleksi(){
        System.out.println("============================================================");
        System.out.println(":::::: Daftar Buku yang Tersimpan dalam Perpustakaan ::::::");
        System.out.println("============================================================");
        
        for (int i = 0; i < koleksiPerpus.size(); i++) {
            Koleksi koleksi = koleksiPerpus.get(i);
            System.out.println((i + 1) + ". " + koleksi.laporan());
        }
        System.out.println("============================================================");
    }
    
    public void cetakListAnggota(){
        System.out.println("============================================================");
        System.out.println("::::: Daftar Anggota yang Tersimpan pada Perpustakaan :::::");
        System.out.println("============================================================");
        
        for (int i = 0; i < anggotaPerpus.size(); i++) {
            Anggota anggota = anggotaPerpus.get(i);
            System.out.println((i + 1) + ". " + anggota.laporan());
        }
        System.out.println("============================================================");
    }
    

    

    
}
