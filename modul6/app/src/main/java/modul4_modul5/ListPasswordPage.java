/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package modul4_modul5;
/**
 *
 * @author TIO
 */
import java.util.ArrayList;

public class ListPasswordPage extends BasePage {

    public ListPasswordPage(String title, int width) {
        super(title, width);
    }

    @Override
    public void drawContent() {
        ArrayList<PasswordStore> passwordList = DataPassword.passData;
        int numPasswords = passwordList.size();

        this.label.text = "Terdapat " + numPasswords + " tersimpan.";
        this.label.draw();
        this.space.draw();

        for (PasswordStore password : passwordList) {
            this.label.text = "| " + padRight(password.name, 20) +
                              "| " + padRight(password.username, 15) +
                              "| " + padRight(password.getCategory(), 15) +
                              "|";
            this.label.draw();
        }
        this.space.draw();
    }

    private String padRight(String s, int length) {
        return String.format("%-" + length + "s", s);
    }
}
