/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package modul4_modul5;

public class App {
    public static void main(String[] args) {
        
        DataPassword.loadCSVData();
        String title = "Main Page";
        int width = 80;

        MainPage mainPage = new MainPage(title, width);

        mainPage.draw();
        
        DataPassword.saveCSVData();
        /*
        new HLine(50).draw();
        new Space(50).draw();
        new Label("Uji coba komponen yang dibuat", 50).draw();
        new Space(50).draw();
        new HLine(50).draw();
        new Space(50).draw();
        Input input = new Input("Masukkan nama");
        Input input2 = new Input("Usia anda");
        input.draw();
        String nama = input.getValue();
        input2.draw();
        int usia = input2.getValueInt();
        new Space(50).draw();
        String[] pilihan = {"Pilihan 1", "Pilihan 2", "Pilihan 3", "Pilihan 4"};
        SelectInput pilSelect = new SelectInput("Inputkan pilihan anda:", pilihan, 50);
        pilSelect.draw();
        int value = pilSelect.getValue();
        new Space(50).draw();
        new HLine(50).draw();
        new Space(50).draw();
        new Label("Nama anda: " + nama, 50).draw();
        new Label("Usia anda: " + usia, 50).draw();
        new Label("Anda memilih menu: " + value, 50).draw();
        new Space(50).draw();
        new HLine(50).draw();
        */
        
    }
}
