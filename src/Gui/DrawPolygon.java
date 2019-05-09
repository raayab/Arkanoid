package Gui;

import java.awt.Graphics;
import java.awt.Polygon;

class DrawPolygon implements Draw {
    Polygon polygon;

    DrawPolygon(Polygon polygon) {
        this.polygon = polygon;
    }

    public void draw(Graphics g) {
        g.drawPolygon(this.polygon);
    }
}
