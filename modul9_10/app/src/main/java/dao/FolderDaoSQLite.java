/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package dao;

import entities.Folder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modul9_10.DBConnect;

public class FolderDaoSQLite implements FolderDAO {
    private Connection conn;

    public FolderDaoSQLite() {
        try {
            this.conn = DBConnect.connect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int createFolder(String foldername) {
        int id = 0;
        String query = "INSERT INTO folder (name) VALUES (?)";
        try (PreparedStatement stmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, foldername);
            stmt.executeUpdate();
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                id = generatedKeys.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public ArrayList<Folder> listAllFolders() {
        ArrayList<Folder> folders = new ArrayList<>();
        String query = "SELECT * FROM folder";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet result = stmt.executeQuery();
            while (result.next()) {
                folders.add(new Folder(result.getInt("id"), result.getString("name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return folders;
    }
}
