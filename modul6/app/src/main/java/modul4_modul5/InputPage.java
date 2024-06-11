/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package modul4_modul5;

/**
 *
 * @author TIO
 */
public class InputPage extends BasePage {

    public InputPage(String title, int width) {
        super(title, width);
    }

    @Override
    public void drawContent() {
        Input inputTitle = new Input("Judul Password");
        Input inputUsername = new Input("Username");
        Input inputPassword = new Input("Password");
        String[] categories = {"Belum terkategori", "Aplikasi Web", "Aplikasi Mobile", "Akun Lainnya"};
        SelectInput categoryInput = new SelectInput("Kategori:", categories, this.width);

        this.space.draw();
        this.label.text = "Inputan Password Baru";
        this.label.draw();
        this.space.draw();

        inputTitle.draw();
        String title = inputTitle.getValue();

        inputUsername.draw();
        String username = inputUsername.getValue();

        inputPassword.draw();
        String password = inputPassword.getValue();

        categoryInput.draw();
        int categoryIndex = categoryInput.getValue() - 1;

        int category;
        switch (categoryIndex) {
            case 1:
                category = PasswordStore.CAT_WEBAPP;
                break;
            case 2:
                category = PasswordStore.CAT_MOBILEAPP;
                break;
            case 3:
                category = PasswordStore.CAT_OTHER;
                break;
            default:
                category = PasswordStore.UNCATEGORIZED;
        }

        PasswordStore newPassword = new PasswordStore(title, username, password, category);

        DataPassword.passData.add(newPassword);

        this.space.draw();
        this.label.text = "Input password berhasil dibuat";
        this.label.draw();
        this.space.draw();
    }
}
