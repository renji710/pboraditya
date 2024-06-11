/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package modul4_modul5;



/**
 *
 * @author TIO
 */
import java.util.Scanner;

public class Input implements Component {

    private String label;
    private Scanner input;
    private String value;

    public Input(String label) {
        this.label = label;
        this.input = new Scanner(System.in);
    }

    @Override
    public void draw() {
        System.out.print("| " + label + " : ");
    }

    public String getValue() {
        value = input.nextLine();
        return value;
    }

    public int getValueInt() {
        value = input.nextLine();
        return Integer.parseInt(value);
    }

    public double getValueDouble() {
        value = input.nextLine();
        return Double.parseDouble(value);
    }
}
