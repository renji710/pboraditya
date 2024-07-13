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
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.awt.Font;

public class ManageFrame extends JFrame {

    private Akun akun;
    private AkunDAO akunDAO;
    private PemesananDAO pemesananDAO;
    private PerjalananDAO perjalananDAO;

    private JLabel lblNamaUser;
    private JTable tblUser;
    private DefaultTableModel tableModelUser;
    private JTable tblPemesanan;
    private DefaultTableModel tableModelPemesanan;
    private JButton btnTambahUser;
    private JButton btnEditUser;
    private JButton btnHapusUser;
    private JButton btnLaporanHarian;
    private JButton btnLaporanBulanan;

    public ManageFrame() {
        akunDAO = new AkunDAO();
        pemesananDAO = new PemesananDAO();
        perjalananDAO = new PerjalananDAO();

        setTitle("Mblandang - Manajemen");
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

        // Panel untuk tabel user dan tombol
        JPanel userPanel = new JPanel(new BorderLayout());
        
        JPanel topPanel = new JPanel(new BorderLayout());

        //Judul "Home" dan "Daftar Perjalanan"
        JLabel lbltexthome = new JLabel("Manage");
        lbltexthome.setFont(new Font("Arial", Font.BOLD, 20));
        lbltexthome.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        topPanel.add(lbltexthome, BorderLayout.NORTH);

        JLabel lblDaftarPerjalanan = new JLabel("Data user dan data pemesanan:");
        lblDaftarPerjalanan.setFont(new Font("Arial", Font.PLAIN, 16));
        topPanel.add(lblDaftarPerjalanan, BorderLayout.CENTER);
        userPanel.add(topPanel, BorderLayout.NORTH);

        // Panel tombol user dengan FlowLayout
        JPanel userButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 15));
        btnTambahUser = new JButton("Tambah");
        btnTambahUser.addActionListener(e -> tambahUser());
        userButtonPanel.add(btnTambahUser);

        btnEditUser = new JButton("Edit");
        btnEditUser.addActionListener(e -> editUser());
        userButtonPanel.add(btnEditUser);

        btnHapusUser = new JButton("Hapus");
        btnHapusUser.addActionListener(e -> hapusUser());
        userButtonPanel.add(btnHapusUser);
        userPanel.add(userButtonPanel, BorderLayout.CENTER);

        // Tabel user
        tableModelUser = new DefaultTableModel();
        tableModelUser.addColumn("ID");
        tableModelUser.addColumn("Nama");
        tableModelUser.addColumn("Email");
        tableModelUser.addColumn("No. Telepon");
        tableModelUser.addColumn("Jenis Akun");
        tblUser = new JTable(tableModelUser);
        JScrollPane scrollPaneUser = new JScrollPane(tblUser);
        scrollPaneUser.setPreferredSize(new Dimension(800, 150)); // Atur tinggi tabel
        userPanel.add(scrollPaneUser, BorderLayout.SOUTH);

        contentPanel.add(userPanel, BorderLayout.NORTH);

        // Tabel untuk menampilkan semua pemesanan
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
        tblPemesanan.setPreferredScrollableViewportSize(new Dimension(800, 200)); // Lebar tabel
        JScrollPane scrollPanePemesanan = new JScrollPane(tblPemesanan);
        contentPanel.add(scrollPanePemesanan, BorderLayout.CENTER);

        // Panel tombol laporan dengan FlowLayout
        JPanel laporanButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 15));
        btnLaporanHarian = new JButton("Laporan Harian");
        btnLaporanHarian.addActionListener(e -> generateLaporanHarian());
        laporanButtonPanel.add(btnLaporanHarian);

        btnLaporanBulanan = new JButton("Laporan Bulanan");
        btnLaporanBulanan.addActionListener(e -> generateLaporanBulanan());
        laporanButtonPanel.add(btnLaporanBulanan);
        
        JButton btnHome = new JButton("Home");
        btnHome.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                kembaliKeMenuUtama();
            }
        });
        laporanButtonPanel.add(btnHome);

        contentPanel.add(laporanButtonPanel, BorderLayout.SOUTH);

        mainPanel.add(contentPanel, BorderLayout.CENTER);
        add(mainPanel);
        setVisible(true);
    }

    public void tampilkanMenu(Akun akun) {
        this.akun = akun;
        lblNamaUser.setText(akun.getNama());
        muatDataUser();
        muatDataPemesanan();
        aturVisibilitasTombol();
    }
    
    private void kembaliKeMenuUtama() {
        MenuUtamaFrame menuUtamaFrame = new MenuUtamaFrame();
        menuUtamaFrame.tampilkanMenu(akun);
        this.dispose();
    }

    private void muatDataUser() {
        try {
            List<Akun> daftarUser = akunDAO.getAllAkun();
            tableModelUser.setRowCount(0);
            for (Akun user : daftarUser) {
                tableModelUser.addRow(new Object[]{
                        user.getIdAkun(),
                        user.getNama(),
                        user.getEmail(),
                        user.getNoTelepon(),
                        user.getJenisAkun() == 0 ? "Admin" : "User Biasa"
                });
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
            JOptionPane.showMessageDialog(this, "Gagal memuat data user!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void muatDataPemesanan() {
        try {
            List<Pemesanan> daftarPemesanan = pemesananDAO.getAllPemesanan();
            tableModelPemesanan.setRowCount(0);
            for (Pemesanan pemesanan : daftarPemesanan) {
                Perjalanan perjalanan = perjalananDAO.getPerjalananById(pemesanan.getIdPerjalanan());
                Akun user = akunDAO.getAkunById(pemesanan.getIdAkun()); // Ambil data user

                if (perjalanan != null && user != null) {
                    tableModelPemesanan.addRow(new Object[]{
                            pemesanan.getIdPemesanan(),
                            user.getNama(),
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

    private void aturVisibilitasTombol() {
        // Hanya admin yang bisa mengelola user
        btnTambahUser.setVisible(akun.isAdmin());
        btnEditUser.setVisible(akun.isAdmin());
        btnHapusUser.setVisible(akun.isAdmin());
    }

    private void tambahUser() {
        // Buat custom dialog untuk form tambah user
        JDialog tambahDialog = new JDialog(this, "Tambah User", true);
        tambahDialog.setSize(400, 300);
        tambahDialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding

        // Komponen form tambah
        JLabel lblNama = new JLabel("Nama:");
        JTextField txtNamaTambah = new JTextField(20);
        JLabel lblEmail = new JLabel("Email:");
        JTextField txtEmailTambah = new JTextField(20);
        JLabel lblPassword = new JLabel("Password:");
        JPasswordField txtPasswordTambah = new JPasswordField(20); // Gunakan JPasswordField untuk password
        JLabel lblNoTelp = new JLabel("No. Telepon:");
        JTextField txtNoTelpTambah = new JTextField(20);
        JLabel lblJenisAkun = new JLabel("Jenis Akun:");
        String[] jenisAkunOptions = {"Admin", "User Biasa"};
        JComboBox<String> cbJenisAkun = new JComboBox<>(jenisAkunOptions); // Gunakan JComboBox untuk jenis akun
        JButton btnSimpan = new JButton("Simpan");
        JButton btnBatal = new JButton("Batal");

        // Atur posisi komponen menggunakan GridBagConstraints
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        tambahDialog.add(lblNama, gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        tambahDialog.add(txtNamaTambah, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        tambahDialog.add(lblEmail, gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        tambahDialog.add(txtEmailTambah, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        tambahDialog.add(lblPassword, gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        tambahDialog.add(txtPasswordTambah, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        tambahDialog.add(lblNoTelp, gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        tambahDialog.add(txtNoTelpTambah, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        tambahDialog.add(lblJenisAkun, gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        tambahDialog.add(cbJenisAkun, gbc);

        // Panel untuk tombol
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(btnSimpan);
        buttonPanel.add(btnBatal);
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
                    String nama = txtNamaTambah.getText();
                    String email = txtEmailTambah.getText();
                    String password = new String(txtPasswordTambah.getPassword()); // Ambil password dari JPasswordField
                    String noTelp = txtNoTelpTambah.getText();
                    int jenisAkun = cbJenisAkun.getSelectedIndex(); // Ambil index yang dipilih dari JComboBox

                    // Buat objek Akun baru
                    Akun akunBaru = new Akun(nama, email, password, noTelp, jenisAkun);
                    akunDAO.tambahAkun(akunBaru);

                    muatDataUser(); // Refresh tabel user
                    JOptionPane.showMessageDialog(ManageFrame.this,
                            "User berhasil ditambahkan!",
                            "Sukses", JOptionPane.INFORMATION_MESSAGE);
                    tambahDialog.dispose(); // Tutup dialog tambah
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(tambahDialog,
                            "Format input salah!",
                            "Error", JOptionPane.ERROR_MESSAGE);
                } catch (SQLException ex) {
                    System.err.println("Error: " + ex.getMessage());
                    JOptionPane.showMessageDialog(tambahDialog,
                            "Gagal menambahkan user!",
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

    private void editUser() {
        int selectedRow = tblUser.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih user yang akan diedit!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int idUser = (int) tblUser.getValueAt(selectedRow, 0);
            Akun user = akunDAO.getAkunById(idUser);

            if (user != null) {
                // Buat custom dialog untuk form edit user
                JDialog editDialog = new JDialog(this, "Edit User", true);
                editDialog.setSize(400, 300);
                editDialog.setLayout(new GridBagLayout());
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(10, 10, 10, 10); // Padding

                // Komponen form edit
                JLabel lblNama = new JLabel("Nama:");
                JTextField txtNamaEdit = new JTextField(user.getNama(), 20);
                JLabel lblEmail = new JLabel("Email:");
                JTextField txtEmailEdit = new JTextField(user.getEmail(), 20);
                JLabel lblPassword = new JLabel("Password:");
                JPasswordField txtPasswordEdit = new JPasswordField(20); // Gunakan JPasswordField 
                JLabel lblNoTelp = new JLabel("No. Telepon:");
                JTextField txtNoTelpEdit = new JTextField(user.getNoTelepon(), 20);
                JLabel lblJenisAkun = new JLabel("Jenis Akun:");
                String[] jenisAkunOptions = {"Admin", "User Biasa"};
                JComboBox<String> cbJenisAkun = new JComboBox<>(jenisAkunOptions); 
                cbJenisAkun.setSelectedIndex(user.getJenisAkun()); // Set jenis akun yang dipilih
                JButton btnSimpan = new JButton("Simpan");
                JButton btnBatal = new JButton("Batal");

                // Atur posisi komponen menggunakan GridBagConstraints
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.anchor = GridBagConstraints.WEST;
                editDialog.add(lblNama, gbc);
                gbc.gridx = 1;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                editDialog.add(txtNamaEdit, gbc);

                gbc.gridx = 0;
                gbc.gridy = 1;
                gbc.anchor = GridBagConstraints.WEST;
                editDialog.add(lblEmail, gbc);
                gbc.gridx = 1;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                editDialog.add(txtEmailEdit, gbc);

                gbc.gridx = 0;
                gbc.gridy = 2;
                gbc.anchor = GridBagConstraints.WEST;
                editDialog.add(lblPassword, gbc);
                gbc.gridx = 1;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                editDialog.add(txtPasswordEdit, gbc);

                gbc.gridx = 0;
                gbc.gridy = 3;
                gbc.anchor = GridBagConstraints.WEST;
                editDialog.add(lblNoTelp, gbc);
                gbc.gridx = 1;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                editDialog.add(txtNoTelpEdit, gbc);

                gbc.gridx = 0;
                gbc.gridy = 4;
                gbc.anchor = GridBagConstraints.WEST;
                editDialog.add(lblJenisAkun, gbc);
                gbc.gridx = 1;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                editDialog.add(cbJenisAkun, gbc);

                // Panel untuk tombol
                JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                buttonPanel.add(btnSimpan);
                buttonPanel.add(btnBatal);
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
                            String nama = txtNamaEdit.getText();
                            String email = txtEmailEdit.getText();
                            String password = new String(txtPasswordEdit.getPassword()); // Ambil password dari JPasswordField
                            String noTelp = txtNoTelpEdit.getText();
                            int jenisAkun = cbJenisAkun.getSelectedIndex(); // Ambil index yang dipilih

                            // Update data user
                            user.setNama(nama);
                            user.setEmail(email);
                            
                            if (!password.isEmpty()) {
                                user.setPassword(password);
                            }
                            user.setNoTelepon(noTelp);
                            user.setJenisAkun(jenisAkun);

                            akunDAO.updateAkun(user);
                            muatDataUser(); // Refresh tabel user
                            JOptionPane.showMessageDialog(ManageFrame.this,
                                    "User berhasil diedit!",
                                    "Sukses", JOptionPane.INFORMATION_MESSAGE);
                            editDialog.dispose(); // Tutup dialog edit
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(editDialog,
                                    "Format input salah!",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                        } catch (SQLException ex) {
                            System.err.println("Error: " + ex.getMessage());
                            JOptionPane.showMessageDialog(editDialog,
                                    "Gagal mengedit user!",
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

                editDialog.setLocationRelativeTo(ManageFrame.this); // Tengahkan dialog edit
                editDialog.setVisible(true); // Tampilkan dialog edit
            } else {
                JOptionPane.showMessageDialog(this, "User tidak ditemukan!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
            JOptionPane.showMessageDialog(this, "Gagal mengedit user!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void hapusUser() {
        int selectedRow = tblUser.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih user yang akan dihapus!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int idUser = (int) tblUser.getValueAt(selectedRow, 0);

            // Tampilkan dialog konfirmasi
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Apakah Anda yakin ingin menghapus user ini?",
                    "Konfirmasi Hapus",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                akunDAO.hapusAkun(idUser);
                muatDataUser(); // Refresh tabel user
                JOptionPane.showMessageDialog(this, "User berhasil dihapus!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
            JOptionPane.showMessageDialog(this, "Gagal menghapus user!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void generateLaporanHarian() {
        try {
            LocalDate currentDate = LocalDate.now();
            Date tanggal = Date.valueOf(currentDate);
            List<Pemesanan> pemesananHarian = pemesananDAO.getPemesananByTanggal(tanggal);

            if (pemesananHarian.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tidak ada pemesanan pada tanggal " + currentDate, "Informasi", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("laporan_harian_" + currentDate + ".pdf"));
            document.open();

            com.itextpdf.text.Font fontJudul = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            com.itextpdf.text.Font fontNormal = FontFactory.getFont(FontFactory.HELVETICA, 12);

            Paragraph judul = new Paragraph("Mblandang - Laporan Penjualan Harian", fontJudul);
            judul.setAlignment(Element.ALIGN_CENTER);
            document.add(judul);
            document.add(new Paragraph("Tanggal: " + currentDate, fontNormal));
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(7); // 7 kolom
            table.setWidthPercentage(100);
            table.addCell(new Phrase("ID Pemesanan", fontNormal));
            table.addCell(new Phrase("Nama", fontNormal));
            table.addCell(new Phrase("Asal", fontNormal));
            table.addCell(new Phrase("Tujuan", fontNormal));
            table.addCell(new Phrase("Tanggal", fontNormal));
            table.addCell(new Phrase("Jumlah Tiket", fontNormal));
            table.addCell(new Phrase("Total Harga", fontNormal));

            BigDecimal totalPendapatan = BigDecimal.ZERO;
            for (Pemesanan pemesanan : pemesananHarian) {
                Perjalanan perjalanan = perjalananDAO.getPerjalananById(pemesanan.getIdPerjalanan());
                if (perjalanan != null) {
                    table.addCell(new Phrase(String.valueOf(pemesanan.getIdPemesanan()), fontNormal));
                    table.addCell(new Phrase(akunDAO.getAkunById(pemesanan.getIdAkun()).getNama(), fontNormal));
                    table.addCell(new Phrase(perjalanan.getAsal(), fontNormal));
                    table.addCell(new Phrase(perjalanan.getTujuan(), fontNormal));
                    table.addCell(new Phrase(perjalanan.getTanggal().toString(), fontNormal));
                    table.addCell(new Phrase(String.valueOf(pemesanan.getJumlahTiket()), fontNormal));
                    table.addCell(new Phrase("Rp " + pemesanan.getTotalHarga(), fontNormal));
                    totalPendapatan = totalPendapatan.add(pemesanan.getTotalHarga());
                }
            }
            document.add(table);
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Total Pendapatan: Rp " + totalPendapatan, fontNormal));

            document.close();

            JOptionPane.showMessageDialog(this,
                    "Laporan harian berhasil dibuat: laporan_harian_" + currentDate + ".pdf",
                    "Sukses", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            System.err.println("Error saat membuat laporan harian: " + e.getMessage());
            JOptionPane.showMessageDialog(this, "Gagal membuat laporan harian!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void generateLaporanBulanan() {
        try {
            LocalDate currentDate = LocalDate.now();
            int bulan = currentDate.getMonthValue();
            int tahun = currentDate.getYear();
            List<Pemesanan> pemesananBulanan = pemesananDAO.getPemesananByBulan(bulan, tahun);

            if (pemesananBulanan.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tidak ada pemesanan pada bulan " + bulan + "/" + tahun, "Informasi", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("laporan_bulanan_" + bulan + "_" + tahun + ".pdf"));
            document.open();

            com.itextpdf.text.Font fontJudul = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            com.itextpdf.text.Font fontNormal = FontFactory.getFont(FontFactory.HELVETICA, 12);

            Paragraph judul = new Paragraph("Mblandang - Laporan Penjualan Bulanan", fontJudul);
            judul.setAlignment(Element.ALIGN_CENTER);
            document.add(judul);
            document.add(new Paragraph("Bulan: " + bulan + "/" + tahun, fontNormal));
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(7); // 7 kolom
            table.setWidthPercentage(100);
            table.addCell(new Phrase("ID Pemesanan", fontNormal));
            table.addCell(new Phrase("Nama", fontNormal));
            table.addCell(new Phrase("Asal", fontNormal));
            table.addCell(new Phrase("Tujuan", fontNormal));
            table.addCell(new Phrase("Tanggal", fontNormal));
            table.addCell(new Phrase("Jumlah Tiket", fontNormal));
            table.addCell(new Phrase("Total Harga", fontNormal));

            BigDecimal totalPendapatan = BigDecimal.ZERO;
            for (Pemesanan pemesanan : pemesananBulanan) {
                Perjalanan perjalanan = perjalananDAO.getPerjalananById(pemesanan.getIdPerjalanan());
                if (perjalanan != null) {
                    table.addCell(new Phrase(String.valueOf(pemesanan.getIdPemesanan()), fontNormal));
                    table.addCell(new Phrase(akunDAO.getAkunById(pemesanan.getIdAkun()).getNama(), fontNormal));
                    table.addCell(new Phrase(perjalanan.getAsal(), fontNormal));
                    table.addCell(new Phrase(perjalanan.getTujuan(), fontNormal));
                    table.addCell(new Phrase(perjalanan.getTanggal().toString(), fontNormal));
                    table.addCell(new Phrase(String.valueOf(pemesanan.getJumlahTiket()), fontNormal));
                    table.addCell(new Phrase("Rp " + pemesanan.getTotalHarga(), fontNormal));
                    totalPendapatan = totalPendapatan.add(pemesanan.getTotalHarga());
                }
            }
            document.add(table);
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Total Pendapatan: Rp " + totalPendapatan, fontNormal));

            document.close();

            JOptionPane.showMessageDialog(this,
                    "Laporan bulanan berhasil dibuat: laporan_bulanan_" + bulan + "_" + tahun + ".pdf",
                    "Sukses", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            System.err.println("Error saat membuat laporan bulanan: " + e.getMessage());
            JOptionPane.showMessageDialog(this, "Gagal membuat laporan bulanan!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
