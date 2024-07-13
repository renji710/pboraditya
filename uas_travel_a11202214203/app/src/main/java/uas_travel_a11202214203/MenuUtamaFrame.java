/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package uas_travel_a11202214203;

/**
 *
 * @author TIO
 */
import DAO.PerjalananDAO;
import Entitas.Akun;
import Entitas.Perjalanan;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class MenuUtamaFrame extends JFrame {
    private Akun akun;
    private PerjalananDAO perjalananDAO;

    private JLabel lblNamaUser;
    private JTextField txtAsal;
    private JTextField txtTujuan;
    private JTextField txtTanggal;
    private JTextField txtHarga;
    private JTextField txtStok;
    private JButton btnTambah;
    private JButton btnEdit;
    private JButton btnHapus;
    private JButton btnCari;
    private JButton btnManage;
    private JTable tblPerjalanan;
    private DefaultTableModel tableModel;

    public MenuUtamaFrame() {
        perjalananDAO = new PerjalananDAO();
        initializeComponents();
    }

    private void initializeComponents() {
        setTitle("Mblandang - Menu Utama");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        // Panel utama dengan BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20)); // Margin atas lebih kecil

        // Panel navbar dengan BorderLayout
        JPanel navbarPanel = new JPanel(new BorderLayout());
        navbarPanel.setBackground(Color.LIGHT_GRAY); // Warna latar belakang navbar
        navbarPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Padding navbar

        // Judul aplikasi di kiri
        JLabel lblJudul = new JLabel("Mblandang");
        lblJudul.setFont(new Font("Arial", Font.BOLD, 20));
        navbarPanel.add(lblJudul, BorderLayout.WEST);

        // Nama user di kanan
        lblNamaUser = new JLabel(""); // Label untuk nama user, dikosongkan dulu
        lblNamaUser.setFont(new Font("Arial", Font.PLAIN, 16));
        navbarPanel.add(lblNamaUser, BorderLayout.EAST);

        mainPanel.add(navbarPanel, BorderLayout.NORTH); // Tambahkan navbar ke bagian atas

        // Panel konten dengan BorderLayout
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Padding konten

        // Panel atas untuk input dan tombol
        JPanel topPanel = new JPanel(new BorderLayout());

        //Judul "Home" dan "Daftar Perjalanan"
        JLabel lbltexthome = new JLabel("Home");
        lbltexthome.setFont(new Font("Arial", Font.BOLD, 20));
        lbltexthome.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        topPanel.add(lbltexthome, BorderLayout.NORTH);

        JLabel lblDaftarPerjalanan = new JLabel("Daftar perjalanan yang tersedia:");
        lblDaftarPerjalanan.setFont(new Font("Arial", Font.PLAIN, 16));
        lblDaftarPerjalanan.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        topPanel.add(lblDaftarPerjalanan, BorderLayout.CENTER);

        // Panel tombol dengan FlowLayout
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 20));
        btnTambah = new JButton("Tambah");
        btnTambah.addActionListener(e -> tambahPerjalanan());
        buttonPanel.add(btnTambah);

        btnEdit = new JButton("Edit");
        btnEdit.addActionListener(e -> editPerjalanan());
        buttonPanel.add(btnEdit);

        btnHapus = new JButton("Hapus");
        btnHapus.addActionListener(e -> hapusPerjalanan());
        buttonPanel.add(btnHapus);

        btnCari = new JButton("Cari");
        btnCari.addActionListener(e -> cariPerjalanan());
        buttonPanel.add(btnCari);

        topPanel.add(buttonPanel, BorderLayout.SOUTH);
        contentPanel.add(topPanel, BorderLayout.NORTH);

        // Tabel untuk menampilkan data perjalanan
        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Asal");
        tableModel.addColumn("Tujuan");
        tableModel.addColumn("Tanggal");
        tableModel.addColumn("Harga");
        tableModel.addColumn("Stok Tiket");

        tblPerjalanan = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tblPerjalanan);
        
        // Panel untuk tombol "Pesan Tiket"
        JButton btnPesanTiket = new JButton("Pesan Tiket");
        btnPesanTiket.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bukaPemesananFrame();
            }
        });
        
        // Panel untuk tombol "Manage" di kiri bawah
        btnManage = new JButton("Manage");
        btnManage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bukaManageFrame();
            }
        });
        
        // Panel gabungan untuk kedua tombol
        JPanel lowbuttonPanel = new JPanel(new BorderLayout());
        lowbuttonPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
        lowbuttonPanel.add(btnManage, BorderLayout.WEST); 
        lowbuttonPanel.add(btnPesanTiket, BorderLayout.EAST);
        
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        contentPanel.add(lowbuttonPanel, BorderLayout.SOUTH);

        mainPanel.add(contentPanel, BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);
    }
    
    private void bukaPemesananFrame() {
        PemesananFrame pemesananFrame = new PemesananFrame();
        pemesananFrame.tampilkanMenu(akun); 
        this.dispose();
    }
    
    private void bukaManageFrame() {
        ManageFrame manageFrame = new ManageFrame();
        manageFrame.tampilkanMenu(akun); 
        this.dispose(); 
    }

    public void tampilkanMenu(Akun akun) {
        this.akun = akun;
        lblNamaUser.setText(akun.getNama());
        muatDataPerjalanan();

        // Kontrol akses untuk tombol admin
        if (akun.isAdmin()) {
            cekStokTiket();
            btnTambah.setVisible(true);
            btnEdit.setVisible(true);
            btnHapus.setVisible(true);
            btnManage.setVisible(true);
        } else {
            btnTambah.setVisible(false);
            btnEdit.setVisible(false);
            btnHapus.setVisible(false);
            btnManage.setVisible(false);
        }
    }

    private void muatDataPerjalanan() {
        try {
            List<Perjalanan> daftarPerjalanan = perjalananDAO.getAllPerjalanan();
            tableModel.setRowCount(0); // Bersihkan tabel
            for (Perjalanan perjalanan : daftarPerjalanan) {
                tableModel.addRow(new Object[]{
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
    
    private void cekStokTiket() {
        try {
            List<Perjalanan> daftarPerjalanan = perjalananDAO.getAllPerjalanan();
            StringBuilder pesanNotifikasi = new StringBuilder("Peringatan: Stok tiket hampir habis!\n\n");
            boolean adaStokKurang = false;

            for (Perjalanan perjalanan : daftarPerjalanan) {
                if (perjalanan.getStokTiket() < 10) {
                    adaStokKurang = true;
                    pesanNotifikasi.append("ID: ")
                            .append(perjalanan.getIdPerjalanan())
                            .append("\nAsal: ")
                            .append(perjalanan.getAsal())
                            .append("\nTujuan: ")
                            .append(perjalanan.getTujuan())
                            .append("\nStok: ")
                            .append(perjalanan.getStokTiket())
                            .append("\n\n");
                }
            }

            if (adaStokKurang) {
                JOptionPane.showMessageDialog(this, pesanNotifikasi.toString(), "Peringatan Stok Tiket", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
            JOptionPane.showMessageDialog(this, "Gagal mengambil data perjalanan!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void tambahPerjalanan() {
        // Buat custom dialog untuk form tambah
        JDialog tambahDialog = new JDialog(this, "Tambah Perjalanan", true);
        tambahDialog.setSize(400, 350);
        tambahDialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Komponen form tambah
        JLabel lblAsal = new JLabel("Asal:");
        JTextField txtAsalTambah = new JTextField(20);
        JLabel lblTujuan = new JLabel("Tujuan:");
        JTextField txtTujuanTambah = new JTextField(20);
        JLabel lblTanggal = new JLabel("Tanggal (YYYY-MM-DD):");
        JTextField txtTanggalTambah = new JTextField(20);
        JLabel lblHarga = new JLabel("Harga:");
        JTextField txtHargaTambah = new JTextField(20);
        JLabel lblStok = new JLabel("Stok Tiket:");
        JTextField txtStokTambah = new JTextField(20);
        JButton btnSimpan = new JButton("Simpan");
        JButton btnBatal = new JButton("Batal");

        // Atur posisi komponen menggunakan GridBagConstraints
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        tambahDialog.add(lblAsal, gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        tambahDialog.add(txtAsalTambah, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        tambahDialog.add(lblTujuan, gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        tambahDialog.add(txtTujuanTambah, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        tambahDialog.add(lblTanggal, gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        tambahDialog.add(txtTanggalTambah, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        tambahDialog.add(lblHarga, gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        tambahDialog.add(txtHargaTambah, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        tambahDialog.add(lblStok, gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        tambahDialog.add(txtStokTambah, gbc);

        // Panel untuk tombol
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(btnBatal);
        buttonPanel.add(btnSimpan);
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.EAST;
        tambahDialog.add(buttonPanel, gbc);

        // ActionListener untuk tombol Simpan
        btnSimpan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Ambil data dari form tambah
                    String asal = txtAsalTambah.getText();
                    String tujuan = txtTujuanTambah.getText();
                    Date tanggal = Date.valueOf(txtTanggalTambah.getText());
                    BigDecimal harga = new BigDecimal(txtHargaTambah.getText());
                    int stok = Integer.parseInt(txtStokTambah.getText());

                    // Buat objek Perjalanan baru
                    Perjalanan perjalananBaru = new Perjalanan(asal, tujuan, tanggal, harga, stok);
                    perjalananDAO.tambahPerjalanan(perjalananBaru);

                    muatDataPerjalanan(); // Refresh tabel
                    JOptionPane.showMessageDialog(MenuUtamaFrame.this,
                            "Perjalanan berhasil ditambahkan!",
                            "Sukses", JOptionPane.INFORMATION_MESSAGE);
                    tambahDialog.dispose(); // Tutup dialog tambah
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(tambahDialog,
                            "Format input salah!",
                            "Error", JOptionPane.ERROR_MESSAGE);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(tambahDialog,
                            "Tanggal tidak valid!",
                            "Error", JOptionPane.ERROR_MESSAGE);
                } catch (SQLException ex) {
                    System.err.println("Error: " + ex.getMessage());
                    JOptionPane.showMessageDialog(tambahDialog,
                            "Gagal menambahkan perjalanan!",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // ActionListener untuk tombol Batal
        btnBatal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tambahDialog.dispose(); // Tutup dialog tambah
            }
        });

        tambahDialog.setLocationRelativeTo(this);
        tambahDialog.setVisible(true);
    }

    private void editPerjalanan() {
        int selectedRow = tblPerjalanan.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih perjalanan yang akan diedit!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int idPerjalanan = (int) tblPerjalanan.getValueAt(selectedRow, 0);
            Perjalanan perjalanan = perjalananDAO.getPerjalananById(idPerjalanan);

            if (perjalanan != null) {
                
                JDialog editDialog = new JDialog(this, "Edit Perjalanan", true);
                editDialog.setSize(400, 350);
                editDialog.setLayout(new GridBagLayout());
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(10, 10, 10, 10);

                // Komponen form edit
                JLabel lblAsal = new JLabel("Asal:");
                JTextField txtAsalEdit = new JTextField(perjalanan.getAsal(), 20);
                JLabel lblTujuan = new JLabel("Tujuan:");
                JTextField txtTujuanEdit = new JTextField(perjalanan.getTujuan(), 20);
                JLabel lblTanggal = new JLabel("Tanggal (YYYY-MM-DD):");
                JTextField txtTanggalEdit = new JTextField(perjalanan.getTanggal().toString(), 20);
                JLabel lblHarga = new JLabel("Harga:");
                JTextField txtHargaEdit = new JTextField(perjalanan.getHarga().toString(), 20);
                JLabel lblStok = new JLabel("Stok Tiket:");
                JTextField txtStokEdit = new JTextField(String.valueOf(perjalanan.getStokTiket()), 20);
                JButton btnSimpan = new JButton("Simpan");
                JButton btnBatal = new JButton("Batal");

                // Atur posisi komponen menggunakan GridBagConstraints
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.anchor = GridBagConstraints.WEST;
                editDialog.add(lblAsal, gbc);
                gbc.gridx = 1;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                editDialog.add(txtAsalEdit, gbc);

                gbc.gridx = 0;
                gbc.gridy = 1;
                gbc.anchor = GridBagConstraints.WEST;
                editDialog.add(lblTujuan, gbc);
                gbc.gridx = 1;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                editDialog.add(txtTujuanEdit, gbc);

                gbc.gridx = 0;
                gbc.gridy = 2;
                gbc.anchor = GridBagConstraints.WEST;
                editDialog.add(lblTanggal, gbc);
                gbc.gridx = 1;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                editDialog.add(txtTanggalEdit, gbc);

                gbc.gridx = 0;
                gbc.gridy = 3;
                gbc.anchor = GridBagConstraints.WEST;
                editDialog.add(lblHarga, gbc);
                gbc.gridx = 1;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                editDialog.add(txtHargaEdit, gbc);

                gbc.gridx = 0;
                gbc.gridy = 4;
                gbc.anchor = GridBagConstraints.WEST;
                editDialog.add(lblStok, gbc);
                gbc.gridx = 1;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                editDialog.add(txtStokEdit, gbc);

                // Panel untuk tombol
                JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));           
                buttonPanel.add(btnBatal);
                buttonPanel.add(btnSimpan);
                gbc.gridx = 1;
                gbc.gridy = 5;
                gbc.anchor = GridBagConstraints.EAST;
                editDialog.add(buttonPanel, gbc);

                // ActionListener untuk tombol Simpan
                btnSimpan.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            // Ambil data dari form edit
                            String asalBaru = txtAsalEdit.getText();
                            String tujuanBaru = txtTujuanEdit.getText();
                            Date tanggalBaru = Date.valueOf(txtTanggalEdit.getText());
                            BigDecimal hargaBaru = new BigDecimal(txtHargaEdit.getText());
                            int stokBaru = Integer.parseInt(txtStokEdit.getText());

                            // Konfirmasi edit
                            int confirm = JOptionPane.showConfirmDialog(editDialog,
                                    "Apakah Anda yakin ingin menyimpan perubahan?",
                                    "Konfirmasi Edit",
                                    JOptionPane.YES_NO_OPTION);

                            if (confirm == JOptionPane.YES_OPTION) {
                                // Update data perjalanan
                                perjalanan.setAsal(asalBaru);
                                perjalanan.setTujuan(tujuanBaru);
                                perjalanan.setTanggal(tanggalBaru);
                                perjalanan.setHarga(hargaBaru);
                                perjalanan.setStokTiket(stokBaru);

                                perjalananDAO.updatePerjalanan(perjalanan);
                                muatDataPerjalanan(); // Refresh tabel
                                JOptionPane.showMessageDialog(MenuUtamaFrame.this,
                                        "Perjalanan berhasil diedit!",
                                        "Sukses", JOptionPane.INFORMATION_MESSAGE);
                                editDialog.dispose(); // Tutup dialog edit
                            }
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(editDialog,
                                    "Format input salah!",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                        } catch (IllegalArgumentException ex) {
                            JOptionPane.showMessageDialog(editDialog,
                                    "Tanggal tidak valid!",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                        } catch (SQLException ex) {
                            System.err.println("Error: " + ex.getMessage());
                            JOptionPane.showMessageDialog(editDialog,
                                    "Gagal mengedit perjalanan!",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });

                // ActionListener untuk tombol Batal
                btnBatal.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        editDialog.dispose(); // Tutup dialog edit
                    }
                });

                editDialog.setLocationRelativeTo(this); // Tengahkan dialog edit
                editDialog.setVisible(true); // Tampilkan dialog edit
            } else {
                JOptionPane.showMessageDialog(this, "Perjalanan tidak ditemukan!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
            JOptionPane.showMessageDialog(this, "Gagal mengedit perjalanan!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void hapusPerjalanan() {
        int selectedRow = tblPerjalanan.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih perjalanan yang akan dihapus!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int idPerjalanan = (int) tblPerjalanan.getValueAt(selectedRow, 0);

            // Tampilkan dialog konfirmasi
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Apakah Anda yakin ingin menghapus perjalanan ini?",
                    "Konfirmasi Hapus",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                perjalananDAO.hapusPerjalanan(idPerjalanan);
                muatDataPerjalanan(); // Refresh tabel
                JOptionPane.showMessageDialog(this, "Perjalanan berhasil dihapus!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
            JOptionPane.showMessageDialog(this, "Gagal menghapus perjalanan!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cariPerjalanan() {
        JDialog cariDialog = new JDialog(this, "Cari Perjalanan", true);
        cariDialog.setSize(300, 100);
        cariDialog.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20));

        // Komponen pencarian
        JTextField txtCari = new JTextField(20);
        JButton btnCariPerjalanan = new JButton("Cari");

        cariDialog.add(txtCari);
        cariDialog.add(btnCariPerjalanan);

        // ActionListener untuk tombol Cari
        btnCariPerjalanan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String keyword = txtCari.getText();

                try {
                    List<Perjalanan> hasilPencarian = perjalananDAO.cariPerjalanan(keyword);
                    tableModel.setRowCount(0);
                    for (Perjalanan perjalanan : hasilPencarian) {
                        tableModel.addRow(new Object[]{
                                perjalanan.getIdPerjalanan(),
                                perjalanan.getAsal(),
                                perjalanan.getTujuan(),
                                perjalanan.getTanggal(),
                                perjalanan.getHarga(),
                                perjalanan.getStokTiket()
                        });
                    }
                    cariDialog.dispose(); // Tutup dialog pencarian
                } catch (SQLException ex) {
                    System.err.println("Error: " + ex.getMessage());
                    JOptionPane.showMessageDialog(cariDialog,
                            "Gagal mencari perjalanan!",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cariDialog.setLocationRelativeTo(this);
        cariDialog.setVisible(true);
    }
}
