package Gui;

import java.awt.Color;
import java.awt.Graphics;

class SetColor implements Draw {
    Color color;

    public SetColor(Color color) {
        this.color = color;
    }

    public void draw(Graphics g) {
        g.setColor(this.color);
    }
}
