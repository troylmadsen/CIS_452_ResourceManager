package com.madsen.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Random;

public class ResourcePanel extends JPanel {

    /** Height of a ResourcePanel */
    public static final int HEIGHT = 25;

    /** Width of a ResourcePanel */
    public static final int WIDTH = 90;

    /** Unique color of this process */
    private Color col;

    //FIXME info
    public ResourcePanel(String name) {
        super();

        // Set the name of this panel
        this.setName(name);

        // Create a border around this panel
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));

        // Set the size of this panel
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        // Set the color of this panel
        Random r = new Random();
        this.col = new Color(r.nextInt(256), r.nextInt(256),
                r.nextInt(256));
        setFree();

        // Add a label to this panel
        addLabel(name);
    }

    //FIXME info
    private void addLabel(String name) {
        JLabel l = new JLabel(name);
        Color lCol;
        if ((this.col.getRed() + this.col.getGreen() + this.col.getBlue()) / 3 > 128) {
            lCol = Color.BLACK;
        }
        else {
            lCol = Color.WHITE;
        }
        l.setForeground(lCol);
        this.add(l);
    }

    public void setTaken() {
        this.setBackground(new Color(150,150,150));
    }

    public void setFree() {
        this.setBackground(this.col);
    }

}
