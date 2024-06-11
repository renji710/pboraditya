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
import java.util.List;

public abstract class BasePage {

    protected String title;
    protected int width;
    protected HLine hline;
    protected Space space;
    protected Label label;
    protected List<Component> components;

    public BasePage(String title, int width) {
        this.title = title;
        this.width = width;
        this.hline = new HLine(width);
        this.space = new Space(width);
        this.label = new Label(title.toUpperCase(), width);
        this.components = new ArrayList<>();
    }

    public void draw() {
        drawHeader();
        drawContent();
        drawFooter();
        new MainPage("Main Page", width).draw();
    }

    public void drawHeader() {
        hline.draw();
        space.draw();
        label.draw();
        space.draw();
        hline.draw();
    }

    public abstract void drawContent();

    public void drawFooter() {
        hline.draw();
    }
}
