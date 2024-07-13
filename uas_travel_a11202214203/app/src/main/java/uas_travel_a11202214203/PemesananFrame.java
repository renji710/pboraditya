/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package uas_travel_a11202214203;

/**
 *
 * @author TIO
 */
import DAO.AkunDAO;
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
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.awt.Font;

public class PemesananFrame extends JFrame {
    private Akun akun;
    private PerjalananDAO perjalananDAO;
    private PemesananDAO pemesananDAO;
    private AkunDAO akunDAO;

    private JLabel lblNamaUser;
    private JTextField txtJumlahTiket;
    private JLabel lblTotalHarga;
    private JButton btnPesan;
    private JButton btnCetakTiket; 
    private JTable tblPerjalanan; 
    private JTable tblPemesanan;
    private DefaultTableModel tableModelPerjalanan;
    private DefaultTableModel tableModelPemesanan;

    public PemesananFrame() {
        perjalananDAO = new PerjalananDAO();
        pemesananDAO = new PemesananDAO();
        akunDAO = new AkunDAO();

        setTitle("Mblandang - Pemesanan Tiket");
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

        // Panel form pemesanan dengan GridBagLayout
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel lbltexthome = new JLabel("Pemesanan Tiket");
        lbltexthome.setFont(new java.awt.Font("Arial", Font.BOLD, 20));
        formPanel.add(lbltexthome, gbc);

        // Label Pilih Perjalanan
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel lblPerjalanan = new JLabel("Pilih Perjalanan:");
        lblPerjalanan.setFont(new java.awt.Font("Arial", Font.PLAIN, 16));
        formPanel.add(lblPerjalanan, gbc);

        // Textfield dan tombol cari
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JTextField txtCariPerjalanan = new JTextField(20);
        formPanel.add(txtCariPerjalanan, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        JButton btnCariPerjalanan = new JButton("Cari");
        btnCariPerjalanan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String keyword = txtCariPerjalanan.getText();
                cariPerjalanan(keyword);
            }
        });
        formPanel.add(btnCariPerjalanan, gbc);

        // Tabel Perjalanan 
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        tableModelPerjalanan = new DefaultTableModel();
        tableModelPerjalanan.addColumn("ID");
        tableModelPerjalanan.addColumn("Asal");
        tableModelPerjalanan.addColumn("Tujuan");
        tableModelPerjalanan.addColumn("Tanggal");
        tableModelPerjalanan.addColumn("Harga");
        tableModelPerjalanan.addColumn("Stok Tiket");
        tblPerjalanan = new JTable(tableModelPerjalanan);
        JScrollPane scrollPanePerjalanan = new JScrollPane(tblPerjalanan);
        scrollPanePerjalanan.setPreferredSize(new Dimension(800, 150));
        formPanel.add(scrollPanePerjalanan, gbc);
        muatDataPerjalanan();

        // Label dan Text Field Jumlah Tiket
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        JLabel lblJumlahTiket = new JLabel("Jumlah Tiket:");
        lblJumlahTiket.setFont(new java.awt.Font("Arial", Font.PLAIN, 16));
        formPanel.add(lblJumlahTiket, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        txtJumlahTiket = new JTextField(10);
        txtJumlahTiket.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                hitungTotalHarga();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                hitungTotalHarga();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                hitungTotalHarga();
            }
        });
        formPanel.add(txtJumlahTiket, gbc);

        // Label Total Harga
        gbc.gridx = 0;
        gbc.gridy = 4;
        JLabel lblTotal = new JLabel("Total Harga:");
        lblTotal.setFont(new java.awt.Font("Arial", Font.PLAIN, 16));
        formPanel.add(lblTotal, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        lblTotalHarga = new JLabel("Rp 0");
        lblTotalHarga.setFont(new java.awt.Font("Arial", Font.PLAIN, 16));
        formPanel.add(lblTotalHarga, gbc);

        // Tombol Pesan
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.EAST;
        btnPesan = new JButton("Pesan");
        btnPesan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pesanTiket();
            }
        });
        formPanel.add(btnPesan, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        JLabel riwayat = new JLabel("Catatan Pemesanan:");
        riwayat.setFont(new java.awt.Font("Arial", Font.PLAIN, 16));
        formPanel.add(riwayat, gbc);

        contentPanel.add(formPanel, BorderLayout.NORTH);

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
        tblPemesanan.setPreferredScrollableViewportSize(new Dimension(800, 200)); 

        // Panel untuk tombol cetak dan kembali dengan BorderLayout
        JPanel buttonPanel = new JPanel(new BorderLayout());

        buttonPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
        // Tombol kembali di kiri (WEST)
        JButton btnKembali = new JButton("Kembali");
        btnKembali.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                kembaliKeMenuUtama();
            }
        });
        buttonPanel.add(btnKembali, BorderLayout.WEST);

        // Tombol Tiket di kanan (EAST)
        JButton btnTiket = new JButton("Checkout");
        btnTiket.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bukaTiketFrame();
            }
        });
        buttonPanel.add(btnTiket, BorderLayout.EAST);


        JScrollPane scrollPane = new JScrollPane(tblPemesanan);
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        add(mainPanel);
        setVisible(true);
    }
    
    private void bukaTiketFrame() {
        TiketFrame tiketFrame = new TiketFrame();
        tiketFrame.tampilkanMenu(akun);
        this.dispose(); 
    }


    private void kembaliKeMenuUtama() {
        MenuUtamaFrame menuUtamaFrame = new MenuUtamaFrame();
        menuUtamaFrame.tampilkanMenu(akun);
        this.dispose();
    }
    

    public void tampilkanMenu(Akun akun) {
        this.akun = akun;
        lblNamaUser.setText(akun.getNama());
        muatDataPemesanan();
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

    private void muatDataPemesanan() {
        try {
            List<Pemesanan> daftarPemesanan = pemesananDAO.getPemesananByAkunId(akun.getIdAkun());
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

    private void hitungTotalHarga() {
        try {
            int selectedRow = tblPerjalanan.getSelectedRow();
            if (selectedRow != -1) {
                BigDecimal harga = new BigDecimal(tblPerjalanan.getValueAt(selectedRow, 4).toString()); // Kolom harga
                int jumlahTiket = Integer.parseInt(txtJumlahTiket.getText());
                if (jumlahTiket > 0) {
                    BigDecimal totalHarga = harga.multiply(new BigDecimal(jumlahTiket));
                    lblTotalHarga.setText("Rp " + totalHarga.toString());
                } else {
                    lblTotalHarga.setText("Rp 0");
                }
            } else {
                lblTotalHarga.setText("Rp 0");
            }
        } catch (NumberFormatException e) {
            lblTotalHarga.setText("Rp 0");
        }
    }

    private void pesanTiket() {
        int selectedRow = tblPerjalanan.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih perjalanan terlebih dahulu!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int idPerjalanan = (int) tblPerjalanan.getValueAt(selectedRow, 0);
            Perjalanan perjalanan = perjalananDAO.getPerjalananById(idPerjalanan);

            if (perjalanan != null) {
                int jumlahTiket = Integer.parseInt(txtJumlahTiket.getText());
                if (jumlahTiket <= 0) {
                    JOptionPane.showMessageDialog(this, "Jumlah tiket harus lebih dari 0!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (jumlahTiket > perjalanan.getStokTiket()) {
                    JOptionPane.showMessageDialog(this, "Stok tiket tidak mencukupi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                BigDecimal totalHarga = perjalanan.getHarga().multiply(new BigDecimal(jumlahTiket));

                // Konfirmasi pemesanan
                int confirm = JOptionPane.showConfirmDialog(this,
                        "Konfirmasi Pemesanan:\n" +
                                "Perjalanan: " + perjalanan.getAsal() + " - " + perjalanan.getTujuan() + "\n" +
                                "Tanggal: " + perjalanan.getTanggal() + "\n" +
                                "Jumlah Tiket: " + jumlahTiket + "\n" +
                                "Total Harga: Rp " + totalHarga + "\n\n" +
                                "Apakah Anda yakin ingin memesan?",
                        "Konfirmasi Pemesanan",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    // Buat objek pemesanan dengan status "Belum Dibayar"
                    Pemesanan pemesanan = new Pemesanan(perjalanan.getIdPerjalanan(), akun.getIdAkun(),
                            jumlahTiket, totalHarga, "Belum Dibayar"); 

                    // Simpan pemesanan ke database
                    pemesananDAO.tambahPemesanan(pemesanan);

                    // Kurangi stok tiket di database
                    perjalanan.setStokTiket(perjalanan.getStokTiket() - jumlahTiket);
                    perjalananDAO.updatePerjalanan(perjalanan);

                    muatDataPerjalanan();
                    muatDataPemesanan();
                    JOptionPane.showMessageDialog(this, "Pemesanan berhasil!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                    
                    // *** Reset form pemesanan ***
                    txtJumlahTiket.setText(""); 
                    lblTotalHarga.setText("Rp 0");
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Format input salah!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
            JOptionPane.showMessageDialog(this, "Gagal melakukan pemesanan!", "Error", JOptionPane.ERROR_MESSAGE);
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
