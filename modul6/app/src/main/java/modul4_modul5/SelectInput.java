/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package modul4_modul5;
import java.util.Scanner;
/**
 *
 * @author TIO
 */
public class SelectInput implements Component {

    private String label;
    private int width;
    private String[] selection;
    private Input input;

    public SelectInput(String label, String[] selection, int width) {
        this.label = label;
        this.width = width;
        this.selection = selection;
        this.input = new Input("Pilihan");
    }

    @Override
    public void draw() {
        System.out.println("| " + label + " ".repeat(width - label.length() - 2) + " |");
        for (int i = 0; i < selection.length; i++) {
            System.out.println("| [" + (i + 1) + "] " + selection[i] + " ".repeat(width - selection[i].length() - 6) + " |");
        }
        input.draw();
        System.out.print(" ");
    }

    public int getValue() {
        int choice;
        do {
            try {
                choice = Integer.parseInt(input.getValue());
                if (choice >= 1 && choice <= selection.length) {
                    break;
                } else {
                    System.out.println("Pilihan tidak valid. Silakan pilih lagi.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Masukkan angka sebagai pilihan.");
            }
        } while (true);
        return choice;
    }
}
