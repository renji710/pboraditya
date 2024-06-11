/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package modul4_modul5;

/**
 *
 * @author TIO
 */
public class Space implements Component {

    private int width;

    public Space(int width) {
        this.width = width;
    }

    @Override
    public void draw() {
        StringBuilder space = new StringBuilder();
        for (int i = 0; i < width; i++) {
            space.append(" ");
        }
        System.out.println("|" + space + "|");
    }
}
