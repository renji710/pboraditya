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
import Entitas.Akun;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class LoginFrame extends JFrame {

    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnRegister;
    private AkunDAO akunDAO;

    public LoginFrame() {
        akunDAO = new AkunDAO();

        setTitle("Mblandang");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        // Panel utama dengan BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Margin

        // Judul aplikasi
        JLabel lblJudul = new JLabel("Mblandang");
        lblJudul.setFont(new Font("Baltica", Font.BOLD, 36));
        lblJudul.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        lblJudul.setHorizontalAlignment(JLabel.CENTER);
        mainPanel.add(lblJudul, BorderLayout.NORTH);

        // Panel input dengan GridBagLayout
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 15, 5); // Padding antar komponen

        // Label Email
        JLabel lblEmail = new JLabel("Email:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        inputPanel.add(lblEmail, gbc);

        // Text Field Email
        txtEmail = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        inputPanel.add(txtEmail, gbc);

        // Label Password
        JLabel lblPassword = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        inputPanel.add(lblPassword, gbc);

        // Text Field Password
        txtPassword = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        inputPanel.add(txtPassword, gbc);

        mainPanel.add(inputPanel, BorderLayout.CENTER);

        // Panel tombol dengan FlowLayout
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 95, 15));

        // Tombol Register
        btnRegister = new JButton("Register");
        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tampilkanFormRegistrasi();
            }
        });
        buttonPanel.add(btnRegister);

        // Tombol Login
        btnLogin = new JButton("Login");
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });
        buttonPanel.add(btnLogin);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    private void login() {
        String email = txtEmail.getText();
        String password = new String(txtPassword.getPassword());

       
        try {
            Akun akun = akunDAO.getAkunByEmail(email);
            if (akun != null && akun.getPassword().equals(password)) { // Ganti `password` dengan `hashedPassword`
                // Login berhasil
                JOptionPane.showMessageDialog(this, "Login berhasil!", "Sukses", JOptionPane.INFORMATION_MESSAGE);

                // Buka MenuUtamaFrame (sesuai dengan jenis akun)
                if (akun.isAdmin()) {
                    MenuUtamaFrame menuAdmin = new MenuUtamaFrame();
                    menuAdmin.tampilkanMenu(akun); // Berikan data akun ke MenuUtamaFrame
                    this.dispose();
                } else {
                    MenuUtamaFrame menuAdmin = new MenuUtamaFrame();
                    menuAdmin.tampilkanMenu(akun); // Berikan data akun ke MenuUtamaFrame
                    this.dispose();
                }
            } else {
                // Login gagal
                JOptionPane.showMessageDialog(this, "Email atau password salah!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            System.err.println("Error saat login: " + e.getMessage());
            JOptionPane.showMessageDialog(this, "Terjadi error saat login!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void tampilkanFormRegistrasi() {
        // Buat custom dialog untuk form registrasi
        JDialog registerDialog = new JDialog(this, "Registrasi Akun", true);
        registerDialog.setSize(400, 300);
        registerDialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Komponen form registrasi
        JLabel lblNama = new JLabel("Nama:");
        JTextField txtNama = new JTextField(20);
        JLabel lblEmail = new JLabel("Email:");
        JTextField txtEmailReg = new JTextField(20);
        JLabel lblPassword = new JLabel("Password:");
        JPasswordField txtPasswordReg = new JPasswordField(20);
        JLabel lblNoTelepon = new JLabel("No. Telepon:");
        JTextField txtNoTelepon = new JTextField(20);
        JButton btnRegisterAkun = new JButton("Register");
        JButton btnBatal = new JButton("Batal");

        // Atur posisi komponen menggunakan GridBagConstraints
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        registerDialog.add(lblNama, gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        registerDialog.add(txtNama, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        registerDialog.add(lblEmail, gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        registerDialog.add(txtEmailReg, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        registerDialog.add(lblPassword, gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        registerDialog.add(txtPasswordReg, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        registerDialog.add(lblNoTelepon, gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        registerDialog.add(txtNoTelepon, gbc);

        // Panel untuk tombol
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(btnBatal);
        buttonPanel.add(btnRegisterAkun);
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        registerDialog.add(buttonPanel, gbc);

        // ActionListener untuk tombol Register
        btnRegisterAkun.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String nama = txtNama.getText();
                    String email = txtEmailReg.getText();
                    String password = new String(txtPasswordReg.getPassword());
                    String noTelepon = txtNoTelepon.getText();

                    // Buat objek Akun baru (jenis akun default user biasa = 1)
                    Akun akunBaru = new Akun(nama, email, password, noTelepon, 1);

                    akunDAO.tambahAkun(akunBaru);

                    JOptionPane.showMessageDialog(registerDialog,
                            "Registrasi berhasil!",
                            "Sukses", JOptionPane.INFORMATION_MESSAGE);
                    registerDialog.dispose(); // Tutup dialog registrasi
                } catch (SQLException ex) {
                    System.err.println("Error: " + ex.getMessage());
                    JOptionPane.showMessageDialog(registerDialog,
                            "Gagal melakukan registrasi!",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // ActionListener untuk tombol Batal
        btnBatal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerDialog.dispose(); // Tutup dialog registrasi
            }
        });

        registerDialog.setLocationRelativeTo(this);
        registerDialog.setVisible(true);
    }

}
