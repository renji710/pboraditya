/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package modul_3;

import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author TIO
 */
public final class PasswordStore {
    
    public String name, username;
    private String password, hashkey;
    private double score;
    private int category;
    
    public static final int UNCATEGORIZED = 0;
    public static final int CAT_WEBAPP = 1;
    public static final int CAT_MOBILEAPP = 2;
    public static final int CAT_OTHER = 3;

    public PasswordStore(String name, String username, String plainPass){
        this(name, username, plainPass, UNCATEGORIZED);
        
    }
    
    public PasswordStore(String name, String username, String plainPass, int category){
        try {
            this.hashkey = Encryptor.generateKey();
        } catch (NoSuchAlgorithmException ex) {
            System.err.println("!!!ERROR!!!");
            System.err.println("Info: " + ex.getMessage());
        }
        this.name = name;
        this.username = username;
        setPassword(plainPass);
        setCategory(category);
        
    }
    
    public void setPassword(String plainPass){
        try {
            this.password = Encryptor.encrypt(plainPass, hashkey);
            calculateScore(plainPass);
        } catch (NoSuchAlgorithmException ex) {
            System.err.println("!!!ERROR!!!");
            System.err.println("Info: " + ex.getMessage());
        } catch (Exception ex) {
            Logger.getLogger(PasswordStore.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public String getPassword() {
        try {
            return Encryptor.decrypt(password, hashkey);
        } catch (NoSuchAlgorithmException ex) {
            System.err.println("!!!ERROR!!!");
            System.err.println("Info: " + ex.getMessage());
            return null;
        } catch (Exception ex) {
            Logger.getLogger(PasswordStore.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public void setCategory(int category){
        if (category >= 0 && category <= 3) {
            this.category = category;
        } else {
            this.category = UNCATEGORIZED;
        }
        
    }
    
    public String getCategory(){
        return switch (category) {
            case UNCATEGORIZED -> "Belum terkategori";
            case CAT_WEBAPP -> "Aplikasi web";
            case CAT_MOBILEAPP -> "Aplikasi mobile";
            case CAT_OTHER -> "Akun lainnya";
            default -> "";
        };
  
    }
    
    private void calculateScore(String plainPass){
        int length = plainPass.length();
        if (length > 15) {
            this.score = 10;
        } else {
            this.score = (length / 15.0) * 10;
        }
        
    }
    
    @Override
    public String toString() {
        return "Username: " + username + "\n"
             + "Password (encrypted): " + password + "\n"
             + "Hashkey: " + hashkey + "\n"
             + "Kategori: " + getCategory() + "\n"
             + "Score: " + score;
        
    }

}
