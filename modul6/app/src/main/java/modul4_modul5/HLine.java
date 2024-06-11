/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package modul4_modul5;

/**
 *
 * @author TIO
 */
public class HLine implements Component {

    private final int width;

    public HLine(int width) {
        this.width = width;
    }

    @Override
    public void draw() {
        StringBuilder line = new StringBuilder();
        for (int i = 0; i < width; i++) {
            line.append("=");
        }
        System.out.println("+" + line + "+");
    }
}
