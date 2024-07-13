/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package Entitas;

/**
 *
 * @author TIO
 */
public class Akun {
    private int idAkun;
    private String nama;
    private String email;
    private String password;
    private String noTelepon;
    private int jenisAkun; // 0: Admin, 1: User Biasa

    // Constructor kosong
    public Akun() {
    }

    // Constructor dengan parameter (tanpa idAkun, karena biasanya auto-increment)
    public Akun(String nama, String email, String password, String noTelepon, int jenisAkun) {
        this.nama = nama;
        this.email = email;
        this.password = password;
        this.noTelepon = noTelepon;
        this.jenisAkun = jenisAkun;
    }

    // Getter dan Setter
    public int getIdAkun() {
        return idAkun;
    }

    public void setIdAkun(int idAkun) {
        this.idAkun = idAkun;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNoTelepon() {
        return noTelepon;
    }

    public void setNoTelepon(String noTelepon) {
        this.noTelepon = noTelepon;
    }

    public int getJenisAkun() {
        return jenisAkun;
    }

    public void setJenisAkun(int jenisAkun) {
        this.jenisAkun = jenisAkun;
    }

    public boolean isAdmin() {
        return jenisAkun == 0;
    }

    @Override
    public String toString() {
        return "Akun{" +
                "idAkun=" + idAkun +
                ", nama='" + nama + '\'' +
                ", email='" + email + '\'' +
                ", noTelepon='" + noTelepon + '\'' +
                ", jenisAkun=" + jenisAkun +
                '}';
    }
}
