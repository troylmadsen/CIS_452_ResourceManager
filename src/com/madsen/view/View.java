package com.madsen.view;

import javax.swing.*;
import java.io.File;

/**
 * Class responsible for managing all display of the Resource Manager.
 */
public class View extends JFrame {

    /** Simulation panel currently displayed */
    private SimulationPanel panel;

    /**
     * Constructs a View object with the given name in the title bar.
     *
     * @param name Name to display in the title bar.
     */
    public View(String name) {
        super(name);

        // Set up base properties
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        // Add menu bar
        this.setJMenuBar(constructMenuBar());

        // Add empty simulation panel
        panel = new SimulationPanel(0,0);
        this.add(panel);

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
        JMenu menuFile = new JMenu("New");
        menuBar.add(menuFile);

        // Build menuExit item
        JMenu menuExit = new JMenu("Exit");
        menuBar.add(menuExit);

        return menuBar;
    }

    public void createSimulation(int processes, int resources) {
        this.remove(panel);
        panel = new SimulationPanel(processes,resources);
        this.add(panel);
        this.pack();
    }

    /**
     * Retrieves input file to be parsed by Resource Manager for simulation.
     *
     * @return File to be parsed by Resource Manager.
     */
    public File getInputFile () {
        // Create file chooser and set it to only select files
        JFileChooser chooser = new JFileChooser(new File(System.getProperty("user.dir")));
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        // Attempt to get a file from the user
        File file = null;
//        chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            file = chooser.getSelectedFile();
        }

        return file;
    }

}
