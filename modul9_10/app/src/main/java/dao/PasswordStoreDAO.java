/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package dao;

import entities.PasswordStore;
import entities.UserData;
import java.util.ArrayList;

public interface PasswordStoreDAO {
    int addPassword(PasswordStore newPassword, UserData user);
    ArrayList<PasswordStore> listPassword(UserData user);
    ArrayList<PasswordStore> findPassword(String name, UserData user);
    int updatePass(PasswordStore changedPass);
    int deletePass(PasswordStore deletedPass);
}
