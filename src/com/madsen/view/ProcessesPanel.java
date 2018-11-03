package com.madsen.view;

import javax.swing.*;
import java.awt.*;

public class ProcessesPanel extends JScrollPane {

    public ProcessesPanel(int processes) {
        super(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Construct a scrollable panel to display processes
        JPanel panel = new JPanel();
        panel.setBackground(new Color(150,150,150));
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        this.setViewportView(panel);
        this.setPreferredSize(new Dimension(800,400));
        this.getVerticalScrollBar().setUnitIncrement(10);

        for (int i = 0; i < processes; i++) {
            if (i != 0) {
                panel.add(Box.createRigidArea(new Dimension(0,5)));
            }
            //FIXME Add a name to the process being added
            ProcessPanel p = new ProcessPanel("Process " + i);
            panel.add(p);
        }
    }

}
