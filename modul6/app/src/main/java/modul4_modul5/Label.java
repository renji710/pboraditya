/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package modul4_modul5;

/**
 *
 * @author TIO
 */
public class Label implements Component {

    private int width;
    String text;

    public Label(String text, int width) {
        this.text = text;
        this.width = width;
    }

    @Override
    public void draw() {
        StringBuilder label = new StringBuilder();
        int availableWidth = width - 2;

        int textLength = Math.min(text.length(), availableWidth);

        String printedText = text.substring(0, textLength);

        label.append(" ").append(printedText);

        for (int i = printedText.length() + 1; i < width; i++) {
            label.append(" ");
        }

        System.out.println("|" + label + "|");
    }
}
