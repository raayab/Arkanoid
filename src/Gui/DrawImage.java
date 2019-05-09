package Gui;

import java.awt.*;
import java.awt.image.ImageObserver;

public class DrawImage implements Draw {

    private int x;
    private int y;
    private Image image;

    public DrawImage(int x, int y, Image image) {
        this.x = x;
        this.y = y;
        this.image = image;
    }
    @Override
    public void draw(Graphics g) {
        g.drawImage(image,x,y,(ImageObserver)null);
    }
}
