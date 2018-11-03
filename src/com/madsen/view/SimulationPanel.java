package com.madsen.view;

import javax.swing.*;
import java.awt.*;

public class SimulationPanel extends JPanel {

    public SimulationPanel(int processes, int resources) {
        super();

        //FIXME check that processes and resources are valid

        // Set the color of this panel
        this.setBackground(new Color(150,150,150));

        // Set size of this panel
        this.setPreferredSize(new Dimension(800, 600));

        // Set the layout of this panel
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        // Add a panel to display processes
        ProcessesPanel processesPanel = new ProcessesPanel(processes);
        this.add(processesPanel);

        // Add a panel to display resources
        ResourcesPanel resourcesPanel = new ResourcesPanel(resources);
        this.add(resourcesPanel);

        //FIXME add ResourcesPanel
    }

}
