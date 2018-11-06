package com.madsen.controller;

import com.madsen.model.Model;
import com.madsen.view.View;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.Scanner;

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

    /** Number of processes in the current simulation */
    private int processes;

    /** Number of resources in the current simulation */
    private int resources;

    /** List of commands process/resource commands to operate */
    private LinkedList<String> commands;

    /**
     * Constructs all necessary pieces of Resource Manager.
     */
    public Controller() {
        // Input file to parse
        File file;

        // Wait for view to be initialized
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    view = new View(PROGRAM_NAME);
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            System.exit(1);
        }

        // Get a file from the user
        file = view.getInputFile();

        // Parse file and create new simulation
        if (file != null) {
            parseFile(file);
            view.createSimulation(this.processes,this.resources);
        }
    }

    /**
     * Parses the specified file for simulation parameters and commands.
     *
     * @param file File to parse for simulation parameters and commands.
     */
    private void parseFile(File file) {
        // Clear old simulation parameters and commands
        this.processes = 0;
        this.resources = 0;
        this.commands = new LinkedList<>();

        // Parse file for simulation parameters and commands
        try {
            Scanner scanner = new Scanner(file);
            this.processes = scanner.nextInt();
            scanner.nextLine();
            this.resources = scanner.nextInt();
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                this.commands.addLast(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }



}