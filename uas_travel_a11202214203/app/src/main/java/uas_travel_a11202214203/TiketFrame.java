/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package uas_travel_a11202214203;

/**
 *
 * @author TIO
 */
import DAO.PemesananDAO;
import DAO.PerjalananDAO;
import Entitas.Akun;
import Entitas.Pemesanan;
import Entitas.Perjalanan;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.util.List;
import java.awt.Font;

public class TiketFrame extends JFrame {
    private Akun akun;
    private PemesananDAO pemesananDAO;
    private PerjalananDAO perjalananDAO;

    private JLabel lblNamaUser;
    private JTable tblPemesanan;
    private DefaultTableModel tableModelPemesanan;
    private DefaultTableModel tableModelPerjalanan;
    private JButton btnBayar;
    private JButton btnBatal;
    private JButton btnCetak;
    private JButton btnBack;

    public TiketFrame() {
        pemesananDAO = new PemesananDAO();
        perjalananDAO = new PerjalananDAO();

        setTitle("Mblandang - Manajemen Tiket");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        // Panel utama dengan BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        // Panel navbar dengan BorderLayout
        JPanel navbarPanel = new JPanel(new BorderLayout());
        navbarPanel.setBackground(Color.LIGHT_GRAY);
        navbarPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Judul aplikasi di kiri
        JLabel lblJudul = new JLabel("Mblandang");
        lblJudul.setFont(new java.awt.Font("Arial", Font.BOLD, 20));
        navbarPanel.add(lblJudul, BorderLayout.WEST);

        // Nama user di kanan
        lblNamaUser = new JLabel("");
        lblNamaUser.setFont(new java.awt.Font("Arial", Font.PLAIN, 16));
        navbarPanel.add(lblNamaUser, BorderLayout.EAST);

        mainPanel.add(navbarPanel, BorderLayout.NORTH);

        // Panel konten dengan BorderLayout
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JPanel topPanel = new JPanel(new BorderLayout());
        
        JLabel lbltexthome = new JLabel("Checkout");
        lbltexthome.setFont(new Font("Arial", Font.BOLD, 20));
        lbltexthome.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        topPanel.add(lbltexthome, BorderLayout.NORTH);
        
        JLabel lblDaftarPerjalanan = new JLabel("Daftar pesanan anda:");
        lblDaftarPerjalanan.setFont(new Font("Arial", Font.PLAIN, 16));
        lblDaftarPerjalanan.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        topPanel.add(lblDaftarPerjalanan, BorderLayout.CENTER);

        // Tabel untuk menampilkan riwayat pemesanan
        tableModelPemesanan = new DefaultTableModel();
        tableModelPemesanan.addColumn("ID Pemesanan");
        tableModelPemesanan.addColumn("Nama");
        tableModelPemesanan.addColumn("Asal");
        tableModelPemesanan.addColumn("Tujuan");
        tableModelPemesanan.addColumn("Tanggal");
        tableModelPemesanan.addColumn("Jumlah Tiket");
        tableModelPemesanan.addColumn("Total Harga");
        tableModelPemesanan.addColumn("Status");

        tblPemesanan = new JTable(tableModelPemesanan);
        tblPemesanan.setPreferredScrollableViewportSize(new Dimension(800, 300));
        JScrollPane scrollPane = new JScrollPane(tblPemesanan);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        // Panel tombol dengan FlowLayout
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 15));
        btnBack = new JButton("Kembali");
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                balikPemesananFrame();
            }
        });
        buttonPanel.add(btnBack);
        
        btnBatal = new JButton("Batalkan");
        btnBatal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                batalkanTiket();
            }
        });
        buttonPanel.add(btnBatal);
        
        btnBayar = new JButton("Bayar");
        btnBayar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bayarTiket();
            }
        });
        buttonPanel.add(btnBayar);

        btnCetak = new JButton("Cetak Tiket");
        btnCetak.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cetakTiket();
            }
        });
        buttonPanel.add(btnCetak);

        contentPanel.add(buttonPanel, BorderLayout.SOUTH);
        contentPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);

        // Inisialisasi tableModelPerjalanan di konstruktor
        tableModelPerjalanan = new DefaultTableModel();
        tableModelPerjalanan.addColumn("ID");
        tableModelPerjalanan.addColumn("Asal");
        tableModelPerjalanan.addColumn("Tujuan");
        tableModelPerjalanan.addColumn("Tanggal");
        tableModelPerjalanan.addColumn("Harga");
        tableModelPerjalanan.addColumn("Stok Tiket");
    }

    public void tampilkanMenu(Akun akun) {
        this.akun = akun;
        lblNamaUser.setText(akun.getNama());
        muatDataPemesanan();
    }
    
    private void balikPemesananFrame() {
        PemesananFrame pemesananFrame = new PemesananFrame();
        pemesananFrame.tampilkanMenu(akun); // Berikan data akun ke PemesananFrame
        this.dispose();
    }

    private void muatDataPemesanan() {
        try {
            List<Pemesanan> daftarPemesanan;
            if (akun.isAdmin()) {
                daftarPemesanan = pemesananDAO.getAllPemesanan();
            } else {
                daftarPemesanan = pemesananDAO.getPemesananByAkunId(akun.getIdAkun());
            }

            tableModelPemesanan.setRowCount(0);
            for (Pemesanan pemesanan : daftarPemesanan) {
                Perjalanan perjalanan = perjalananDAO.getPerjalananById(pemesanan.getIdPerjalanan());
                if (perjalanan != null) {
                    tableModelPemesanan.addRow(new Object[]{
                            pemesanan.getIdPemesanan(),
                            akun.getNama(),
                            perjalanan.getAsal(),
                            perjalanan.getTujuan(),
                            perjalanan.getTanggal(),
                            pemesanan.getJumlahTiket(),
                            pemesanan.getTotalHarga(),
                            pemesanan.getStatus()
                    });
                }
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
            JOptionPane.showMessageDialog(this, "Gagal memuat data pemesanan!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void bayarTiket() {
        int selectedRow = tblPemesanan.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih pemesanan yang akan dibayar!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int idPemesanan = (int) tblPemesanan.getValueAt(selectedRow, 0);
            Pemesanan pemesanan = pemesananDAO.getPemesananById(idPemesanan);

            if (pemesanan != null) {
                // Cek status pemesanan
                if (pemesanan.getStatus().equals("Sudah Dibayar")) {
                    JOptionPane.showMessageDialog(this, "Pemesanan ini sudah dibayar!", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                    return;
                } else if (pemesanan.getStatus().equals("Dibatalkan")) {
                    JOptionPane.showMessageDialog(this, "Pemesanan ini sudah dibatalkan!", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                // Konfirmasi pembayaran dengan nominal
                int confirm = JOptionPane.showConfirmDialog(this,
                        "Konfirmasi Pembayaran:\n" +
                                "ID Pemesanan: " + pemesanan.getIdPemesanan() + "\n" +
                                "Total Harga: Rp " + pemesanan.getTotalHarga() + "\n" + // Tampilkan total harga
                                "Apakah Anda yakin ingin membayar pemesanan ini?",
                        "Konfirmasi Pembayaran",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    // Update status pemesanan
                    pemesanan.setStatus("Sudah Dibayar");
                    pemesananDAO.updatePemesanan(pemesanan);

                    muatDataPemesanan();
                    JOptionPane.showMessageDialog(this, "Pembayaran berhasil!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
            JOptionPane.showMessageDialog(this, "Gagal memproses pembayaran!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void batalkanTiket() {
        int selectedRow = tblPemesanan.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih pemesanan yang akan dibatalkan!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int idPemesanan = (int) tblPemesanan.getValueAt(selectedRow, 0);
            Pemesanan pemesanan = pemesananDAO.getPemesananById(idPemesanan);

            if (pemesanan != null) {
                // Cek status pemesanan
                if (pemesanan.getStatus().equals("Sudah Dibayar")) {
                    JOptionPane.showMessageDialog(this, "Pemesanan yang sudah dibayar tidak dapat dibatalkan!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                    return;
                } else if (pemesanan.getStatus().equals("Dibatalkan")) {
                    JOptionPane.showMessageDialog(this, "Pemesanan ini sudah dibatalkan!", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                // Konfirmasi pembatalan
                int confirm = JOptionPane.showConfirmDialog(this,
                        "Konfirmasi Pembatalan:\n" +
                                "ID Pemesanan: " + pemesanan.getIdPemesanan() + "\n" +
                                "Apakah Anda yakin ingin membatalkan pemesanan ini?",
                        "Konfirmasi Pembatalan",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    // Kembalikan stok tiket
                    Perjalanan perjalanan = perjalananDAO.getPerjalananById(pemesanan.getIdPerjalanan());
                    if (perjalanan != null) {
                        perjalanan.setStokTiket(perjalanan.getStokTiket() + pemesanan.getJumlahTiket());
                        perjalananDAO.updatePerjalanan(perjalanan);
                    }

                    // Update status pemesanan
                    pemesanan.setStatus("Dibatalkan");
                    pemesananDAO.updatePemesanan(pemesanan);

                    muatDataPerjalanan();
                    muatDataPemesanan();
                    JOptionPane.showMessageDialog(this, "Pembatalan berhasil!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
            JOptionPane.showMessageDialog(this, "Gagal memproses pembatalan!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cetakTiket() {
        int selectedRow = tblPemesanan.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih pemesanan yang akan dicetak!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int idPemesanan = (int) tblPemesanan.getValueAt(selectedRow, 0);
            Pemesanan pemesanan = pemesananDAO.getPemesananById(idPemesanan);

            if (pemesanan != null && pemesanan.getStatus().equals("Sudah Dibayar")) {
                Perjalanan perjalanan = perjalananDAO.getPerjalananById(pemesanan.getIdPerjalanan());
                if (perjalanan != null) {
                    // Buat dokumen PDF
                    Document document = new Document();
                    PdfWriter.getInstance(document, new FileOutputStream("tiket.pdf"));
                    document.open();

                    // Tambahkan konten ke dokumen
                    com.itextpdf.text.Font fontJudul = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
                    com.itextpdf.text.Font fontNormal = FontFactory.getFont(FontFactory.HELVETICA, 12);

                    Paragraph judul = new Paragraph("Mblandang - Tiket Perjalanan", fontJudul);
                    judul.setAlignment(Element.ALIGN_CENTER);
                    document.add(judul);
                    document.add(new Paragraph(" ")); // Spasi kosong

                    document.add(new Paragraph("ID Pemesanan: " + pemesanan.getIdPemesanan(), fontNormal));
                    document.add(new Paragraph("Nama Pemesan: " + akun.getNama(), fontNormal));
                    document.add(new Paragraph("Asal: " + perjalanan.getAsal(), fontNormal));
                    document.add(new Paragraph("Tujuan: " + perjalanan.getTujuan(), fontNormal));
                    document.add(new Paragraph("Tanggal: " + perjalanan.getTanggal(), fontNormal));
                    document.add(new Paragraph("Jumlah Tiket: " + pemesanan.getJumlahTiket(), fontNormal));
                    document.add(new Paragraph("Total Harga: Rp " + pemesanan.getTotalHarga(), fontNormal));
                    document.add(new Paragraph("Status: " + pemesanan.getStatus(), fontNormal));

                    document.add(new Paragraph(" ")); // Spasi kosong
                    document.add(new Paragraph("Terima kasih telah menggunakan Mblandang!", fontNormal));

                    document.close();

                    // Tampilkan pesan sukses
                    JOptionPane.showMessageDialog(this,
                            "Tiket berhasil disimpan ke file tiket.pdf", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Tiket hanya bisa dicetak jika statusnya 'Sudah Dibayar'!",
                        "Peringatan", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            System.err.println("Error saat mencetak tiket: " + e.getMessage());
            JOptionPane.showMessageDialog(this, "Gagal mencetak tiket!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void muatDataPerjalanan() {
        try {
            List<Perjalanan> daftarPerjalanan = perjalananDAO.getAllPerjalanan();
            tableModelPerjalanan.setRowCount(0);
            for (Perjalanan perjalanan : daftarPerjalanan) {
                tableModelPerjalanan.addRow(new Object[]{
                        perjalanan.getIdPerjalanan(),
                        perjalanan.getAsal(),
                        perjalanan.getTujuan(),
                        perjalanan.getTanggal(),
                        perjalanan.getHarga(),
                        perjalanan.getStokTiket()
                });
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
            JOptionPane.showMessageDialog(this, "Gagal memuat data perjalanan!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cariPerjalanan(String keyword) {
        try {
            List<Perjalanan> hasilPencarian = perjalananDAO.cariPerjalanan(keyword);
            tableModelPerjalanan.setRowCount(0); // Bersihkan tabel
            for (Perjalanan perjalanan : hasilPencarian) {
                tableModelPerjalanan.addRow(new Object[]{
                        perjalanan.getIdPerjalanan(),
                        perjalanan.getAsal(),
                        perjalanan.getTujuan(),
                        perjalanan.getTanggal(),
                        perjalanan.getHarga(),
                        perjalanan.getStokTiket()
                });
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
            JOptionPane.showMessageDialog(this, "Gagal mencari perjalanan!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
