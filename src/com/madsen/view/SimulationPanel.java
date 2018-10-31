package com.madsen.view;

import javax.swing.*;
import java.awt.*;

public class SimulationPanel extends JPanel {

    public SimulationPanel(int processes, int resources) {
        //FIXME resources needed?
        //FIXME check that processes and resources are valid
        //FIXME remove
        processes = 20;

        // Set the color of this panel
        this.setBackground(new Color(150,150,150));

        // Set size of the panel
        this.setPreferredSize(new Dimension(800, 600));

        // Construct a scrollable panel to display processes
        JPanel panel = new JPanel();
        panel.setBackground(new Color(150,150,150));
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        for (int i = 0; i < processes; i++) {
            if (i != 0) {
                panel.add(Box.createRigidArea(new Dimension(0,5)));
            }
            //FIXME Add a name to the process being added
            ProcessPanel p = new ProcessPanel("Process " + i);
            p.setAlignmentX(Component.LEFT_ALIGNMENT);
            panel.add(p);
        }

        // Construct a ScrollPane for scrolling though processes
        JScrollPane scrollPane = new JScrollPane(panel,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(800,600));
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);
        this.add(scrollPane);
    }

}
