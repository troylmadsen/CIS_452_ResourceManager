package com.madsen.view;

import javax.swing.*;
import java.awt.*;

public class ResourcesPanel extends JScrollPane {

    /** Number of columns of the grid to display resources in */
    private static final int COLS = 8;

    /** Gap between displayed resources */
    private static final int GAP = 5;

    /** Panel to display resources */
    private JPanel panel;

    public ResourcesPanel(int resources) {
        super(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Set up this panel
        this.setPreferredSize(new Dimension(800, 200));
        this.getVerticalScrollBar().setUnitIncrement(10);

        // Constructs a scrollable panel to display resources
        panel = new JPanel();
        panel.setBackground(Color.WHITE);
        int rows = (int)Math.ceil(resources / (double)COLS);
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, GAP, GAP));
        panel.setPreferredSize(new Dimension(800,(ResourcePanel.HEIGHT + GAP) * rows + GAP));

        // Add resources panel to this panel
        this.setViewportView(panel);


        // Add resources to this panel
        for (int i = 0; i < resources; i++) {
            addResource("Resource " + i);
        }
    }

    private void addResource(String name) {
        panel.add(new ResourcePanel(name));
    }

}
