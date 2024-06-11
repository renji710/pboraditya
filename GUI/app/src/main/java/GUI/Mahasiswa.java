/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package GUI;

public class Mahasiswa {
    private String nim;
    private String nama;
    private int sksTotal;
    private double ipk;

    public Mahasiswa(String nim, String nama, int sksTotal, double ipk) {
        this.nim = nim;
        this.nama = nama;
        this.sksTotal = sksTotal;
        this.ipk = ipk;
    }

    public String getNim() {
        return nim;
    }

    public String getNama() {
        return nama;
    }

    public int getSksTotal() {
        return sksTotal;
    }

    public double getIpk() {
        return ipk;
    }

    @Override
    public String toString() {
        return "| " + nim + " | " + nama + " ".repeat(20 - nama.length()) + " | "
            + sksTotal + " ".repeat(3 - Integer.toString(sksTotal).length()) + " | "
            + ipk + " ".repeat(4 - Double.toString(ipk).length()) + " |";
    }
}

