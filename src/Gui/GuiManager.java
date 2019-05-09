package Gui;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class GuiManager{
    private KeyboardSensor keyboardSensor;
    private DialogManager dialogManager;
    private DrawSurface surface;
    private JFrame frame;
    private JPanel mainPanel;
    private JPanel drawingPanel;
    private int width;
    private int height;

    public GuiManager(String title, int width, int height) {
        this.width = width;
        this.height = height;
        this.frame = new JFrame(title);
        this.mainPanel = new JPanel();
        this.drawingPanel = new GuiManager.GuiManagerPanel();
        this.frame.setDefaultCloseOperation(3);
        this.frame.setSize(width, height);
        this.frame.setResizable(false);
        this.frame.setVisible(true);
        this.frame.setIgnoreRepaint(true);
        this.frame.setContentPane(this.mainPanel);
        this.frame.setLayout(new BorderLayout());
        this.mainPanel.add(this.drawingPanel, "Center");
        this.mainPanel.setPreferredSize(new Dimension(width, height));
        this.frame.pack();
        this.dialogManager = new DialogManager(this.frame, width, height);
        this.keyboardSensor = new KeyboardSensor(this.frame);
        this.surface = new DrawSurface(this.width, this.height);
    }

    public DialogManager getDialogManager() {
        return this.dialogManager;
    }

    public DrawSurface getDrawSurface() {
        return new DrawSurface(this.width, this.height);
    }

    public void show(DrawSurface surface) {
        final DrawSurface DrawSurface = (DrawSurface)surface;
        if (DrawSurface.isRendered()) {
            throw new DrawSurfaceAlreadyRenderedException("You can not show the same draw surface twice, you must request a new one each time using getDrawSurface()");
        } else {
            DrawSurface.setRendered(true);
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    GuiManager.this.frame.createBufferStrategy(2);
                    BufferStrategy bf = GuiManager.this.frame.getBufferStrategy();
                    Graphics graphics = bf.getDrawGraphics();
                    GuiManager.this.surface = DrawSurface;
                    GuiManager.this.frame.paint(graphics);
                    bf.show();
                }
            });
        }
    }

    public KeyboardSensor getKeyboardSensor() {
        return this.keyboardSensor;
    }

    public void close() {
        this.frame.dispatchEvent(new WindowEvent(this.frame, 201));
    }

    private class GuiManagerPanel extends JPanel {
        private GuiManagerPanel() {
        }

        public void paint(Graphics g) {
            if (GuiManager.this.surface != null) {
                GuiManager.this.surface.paint(g);
            }

        }
    }
}

