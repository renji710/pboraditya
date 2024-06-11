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

public class MainPage extends BasePage {

    private SelectInput pageSelect;

    public MainPage(String title, int width) {
        super(title, width);
        this.components.add(new Label("Selamat datang di aplikasi Password Vault", this.width));
        this.components.add(new Label("Simpan password anda dengan aman di sini", this.width));
        this.components.add(new Space(this.width));
        String[] pages = {"Input Password", "Tampil Password", "Keluar Aplikasi"};
        this.pageSelect = new SelectInput("Pilih halaman berikut:", pages, this.width);
        this.components.add(pageSelect);
    }

    @Override
    public void drawContent() {
        int select;
        for (Component component : this.components) {
            component.draw();
        }
        select = this.pageSelect.getValue() - 1;
        switch (select) {
            case 0 -> {
                drawFooter();
                new InputPage("Inputan Password", this.width).draw();
            }
            case 1 -> {
                drawFooter();
                new ListPasswordPage("List Password Tersimpan", this.width).draw();
            }
            case 2 -> {
                new Label("Menyimpan data ... ...", this.width).draw();
                DataPassword.saveCSVData();
                new Label("Terima kasih telah menggunakan aplikasi", this.width).draw();
                drawFooter();
                System.exit(0);
            }
            default -> {
                new MainPage(this.title, this.width).draw();
            }
        }
    }
}
