package com.madsen.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ResourcesPanel extends JScrollPane {

    /** Panel to display resources */
    private JPanel panel;

    public ResourcesPanel(int resources) {
        super(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Set up this panel
        this.setPreferredSize(new Dimension(800, 200));
        this.getVerticalScrollBar().setUnitIncrement(10);

        // Constructs a scrollable panel to display resources
        panel = new JPanel();
        panel.setPreferredSize(new Dimension(800,600));
        panel.setBorder(new EmptyBorder(0,0,0,18));
//        panel.setBackground(new Color(150,150,150));
        panel.setBackground(Color.WHITE);
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));

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
