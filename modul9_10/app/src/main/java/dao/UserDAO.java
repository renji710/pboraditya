/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package dao;

import entities.UserData;

/**
 *
 * @author TIO
 */
public interface UserDAO {
    int register(String username, String password, String fullname);
    UserData login(String username, String password);
    int update(int id, String fullname);
    int update(int id, String password, String fullname);
    int delete(int id);
}
