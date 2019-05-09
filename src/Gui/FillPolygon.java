package Gui;


import java.awt.Graphics;
import java.awt.Polygon;

class FillPolygon implements Draw {
    Polygon polygon;

    FillPolygon(Polygon polygon) {
        this.polygon = polygon;
    }

    public void draw(Graphics g) {
        g.fillPolygon(this.polygon);
    }
}
