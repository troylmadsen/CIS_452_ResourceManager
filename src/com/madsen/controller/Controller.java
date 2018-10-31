package com.madsen.controller;

import com.madsen.model.Model;
import com.madsen.view.View;

import javax.swing.*;
import java.io.File;

/**
 * Controls the operation of Resource Manager.
 */
public class Controller {

    /** Name of the program to display */
    private final String PROGRAM_NAME = "Resource Manager";

    /** Model containing the data of Resource Manager. */
    private Model model;

    /** View handling the display of Resource Manager. */
    private View view;

    /**
     * Constructs all necessary pieces of Resource Manager.
     */
    public Controller() {
        // Initialize view
        view = new View(PROGRAM_NAME);

        //FIXME Let the user tell the program to choose a file
        // Continually attempt to get a file from the user
//        File file;
//        while (file == null) {
//            file = getInputFile();
//        }

//        model = new Model();
    }

    /**
     * Retrieves input file to be parsed by Resource Manager for simulation.
     *
     * @return File to be parsed by Resource Manager.
     */
    private File getInputFile () {
        // Create file chooser and set it to only select files
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        // Attempt to get a file from the user
        File file = null;
        chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        if (chooser.showOpenDialog(view) == JFileChooser.APPROVE_OPTION) {
            file = chooser.getSelectedFile();
        }

        return file;
    }

}