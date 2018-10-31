package com.madsen.view;

import javax.swing.*;

public class View extends JFrame {

    /**
     * Constructs a View object with the given name in the title bar.
     *
     * @param name Name to display in the title bar.
     */
    public View(String name) {
        // Set up base properties
        this.setName(name);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add menu bar
        this.setJMenuBar(constructMenuBar());

        // Add an empty simulation panel
        this.add(new SimulationPanel(0, 0));

        // Display the window
        this.pack();
        this.setVisible(true);
    }

    /**
     * Constructs the menu bar of the program.
     *
     * @return JMenuBar containing all submenus.
     */
    private JMenuBar constructMenuBar() {
        // Create menu bar
        JMenuBar menuBar = new JMenuBar();

        // Build menuFile item
        JMenu menuFile = new JMenu("File");
        menuBar.add(menuFile);

        // Build menuExit item
        JMenu menuExit = new JMenu("Exit");
        menuBar.add(menuExit);

        return menuBar;
    }

}
