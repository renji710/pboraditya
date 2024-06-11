/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package dao;

import entities.UserData;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import modul9_10.DBConnect;

public class UserDaoSQLite implements UserDAO {
    private Connection conn;

    public UserDaoSQLite() {
        try {
            this.conn = DBConnect.connect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int register(String username, String password, String fullname) {
        int id = 0;
        String query = "INSERT INTO userdata (username, password, fullname) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            UserData newUser = new UserData(username, password, fullname);
            stmt.setString(1, newUser.username);
            stmt.setString(2, newUser.getPassword());
            stmt.setString(3, newUser.fullname);
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
    public UserData login(String username, String password) {
        UserData user = null;
        String query = "SELECT * FROM userdata WHERE username = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet result = stmt.executeQuery();
            if (result.next() && UserData.checkPassword(password, result.getString("password"))) {
                user = new UserData(
                    result.getInt("id"),
                    result.getString("username"),
                    result.getString("password"),
                    result.getString("fullname")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public int update(int id, String fullname) {
        String query = "UPDATE userdata SET fullname = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, fullname);
            stmt.setInt(2, id);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int update(int id, String password, String fullname) {
        String query = "UPDATE userdata SET fullname = ?, password = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            UserData newUser = new UserData("", password, fullname);
            stmt.setString(1, newUser.fullname);
            stmt.setString(2, newUser.getPassword());
            stmt.setInt(3, id);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int delete(int id) {
        String query = "DELETE FROM userdata WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
