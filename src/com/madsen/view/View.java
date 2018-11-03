package com.madsen.view;

import javax.swing.*;

public class View extends JFrame {

    /**
     * Constructs a View object with the given name in the title bar.
     *
     * @param name Name to display in the title bar.
     */
    public View(String name) {
        super(name);

        // Set up base properties
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add menu bar
        this.setJMenuBar(constructMenuBar());

        // Add simulation panel
        //FIXME pass parsed number of processes and resources in
        this.add(new SimulationPanel(20,200));

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
