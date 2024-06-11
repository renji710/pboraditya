/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package dao;

import entities.Folder;
import entities.PasswordStore;
import entities.UserData;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modul9_10.DBConnect;

public class PasswordStoreDaoSQLite implements PasswordStoreDAO {
    private Connection conn;

    public PasswordStoreDaoSQLite() {
        try {
            this.conn = DBConnect.connect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int addPassword(PasswordStore newPassword, UserData user) {
        int id = 0;
        String query = "INSERT INTO passwordstore (name, username, password, hashkey, score, category, user_id, folder_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, newPassword.name);
            stmt.setString(2, newPassword.username);
            stmt.setString(3, newPassword.getEncPassword());
            stmt.setString(4, newPassword.hashkey);
            stmt.setDouble(5, newPassword.getScore());
            stmt.setInt(6, newPassword.getCategoryCode());
            stmt.setInt(7, user.id);
            stmt.setInt(8, newPassword.folder != null ? newPassword.folder.id : null);
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
    public ArrayList<PasswordStore> listPassword(UserData user) {
        ArrayList<PasswordStore> passwords = new ArrayList<>();
        String query = "SELECT * FROM passwordstore WHERE user_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, user.id);
            ResultSet result = stmt.executeQuery();
            while (result.next()) {
                passwords.add(new PasswordStore(
                    result.getInt("id"),
                    result.getString("name"),
                    result.getString("username"),
                    result.getString("password"),
                    result.getString("hashkey"),
                    result.getDouble("score"),
                    result.getInt("category"),
                    new Folder(result.getInt("folder_id"), result.getString("name"))
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return passwords;
    }

    @Override
    public ArrayList<PasswordStore> findPassword(String name, UserData user) {
        ArrayList<PasswordStore> passwords = new ArrayList<>();
        String query = "SELECT * FROM passwordstore WHERE name LIKE ? AND user_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, "%" + name + "%");
            stmt.setInt(2, user.id);
            ResultSet result = stmt.executeQuery();
            while (result.next()) {
                passwords.add(new PasswordStore(
                    result.getInt("id"),
                    result.getString("name"),
                    result.getString("username"),
                    result.getString("password"),
                    result.getString("hashkey"),
                    result.getDouble("score"),
                    result.getInt("category"),
                    new Folder(result.getInt("folder_id"), result.getString("name"))
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return passwords;
    }

    @Override
    public int updatePass(PasswordStore changedPass) {
        String query = "UPDATE passwordstore SET name = ?, username = ?, password = ?, hashkey = ?, score = ?, category = ?, folder_id = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, changedPass.name);
            stmt.setString(2, changedPass.username);
            stmt.setString(3, changedPass.getEncPassword());
            stmt.setString(4, changedPass.hashkey);
            stmt.setDouble(5, changedPass.getScore());
            stmt.setInt(6, changedPass.getCategoryCode());
            stmt.setInt(7, changedPass.folder != null ? changedPass.folder.id : null);
            stmt.setInt(8, changedPass.id);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int deletePass(PasswordStore deletedPass) {
        String query = "DELETE FROM passwordstore WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, deletedPass.id);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
