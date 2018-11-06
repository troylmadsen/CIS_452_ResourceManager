package com.madsen.view;

import javax.swing.*;
import java.awt.*;

/**
 * Panel to display all processes in the simulation.
 */
public class ProcessesPanel extends JPanel {

    /** Preferred dimensions */
    private static final int PREF_HEIGHT = 400;
    private static final int PREF_WIDTH = 800;

    /** Gap between displayed processes */
    private static final int GAP = 5;

    /** Panel to display processes */
    private JPanel panel;

    /**
     * Constructs a panel to display all processes in the simulation.
     *
     * @param processes Number of processes.
     */
    public ProcessesPanel(int processes) {
        super();
//        super(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
//                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Set up this panel
        this.setPreferredSize(new Dimension(PREF_WIDTH,PREF_HEIGHT));
        this.setLayout(new BorderLayout(0,0));

        // Add label to panel
        JLabel label = new JLabel("Processes", SwingConstants.CENTER);
        label.setPreferredSize(new Dimension(this.getPreferredSize().width,
                20));
        this.add(label, BorderLayout.PAGE_START);

        // Construct a scrollable panel to display processes
        JScrollPane scrollPane = new JScrollPane(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(PREF_WIDTH,PREF_HEIGHT));
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);
        panel = new JPanel();
        panel.setBackground(new Color(150,150,150));
//        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setLayout(new FlowLayout(FlowLayout.LEFT,0,GAP));
        panel.setPreferredSize(new Dimension(ProcessPanel.PREF_WIDTH,
                (ProcessPanel.PREF_HEIGHT + GAP) * processes + GAP));
        scrollPane.setViewportView(panel);
        this.add(scrollPane,BorderLayout.CENTER);

        for (int i = 0; i < processes; i++) {
            addProcess("Process " + i);
        }
    }

    /**
     * Add new process to panel.
     *
     * @param name Name of the new process to add.
     */
    private void addProcess(String name) {
        panel.add(new ProcessPanel(name));
    }

}
