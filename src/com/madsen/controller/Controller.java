package com.madsen.controller;

import com.madsen.model.Model;
import com.madsen.model.Process;
import com.madsen.model.Resource;
import com.madsen.view.View;
import org.jetbrains.annotations.Contract;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Controls the operation of Resource Manager.
 */
public class Controller implements MenuListener {

    /** Boolean controlling if this runs with preemptive behavior. */
    private static final boolean PREEMPTIVE = false;

    /** Default delay time between commands in seconds. */
    private static final int DELAY = 1;

    /** Name of the program to display. */
    private static final String PROGRAM_NAME = "Resource Manager";

    /** Model containing the data of Resource Manager. */
    private Model model;

    /** View handling the display of Resource Manager. */
    private View view;

    /** Number of processes in the current simulation. */
    private int processes;

    /** Number of resources in the current simulation. */
    private int resources;

    /** List of commands process/resource commands to operate. */
    private LinkedList<String> commands;

    /**
     * Constructs all necessary pieces of Resource Manager.
     */
    public Controller() {
        // Wait for view to be initialized
        try {
            SwingUtilities.invokeAndWait(() -> view = new View(PROGRAM_NAME));
        } catch (InterruptedException | InvocationTargetException e) {
            e.printStackTrace();
            System.exit(1);
        }

        // Listen for GUI actions
        for (int i = 0; i < this.view.getJMenuBar().getMenuCount(); i++) {
            this.view.getJMenuBar().getMenu(i).addMenuListener(this);
        }

        // Get a file from the user
        File file = this.view.getInputFile();

        // Exit if no file selected
        if (file == null) {
            System.exit(0);
        }

        // Parse file, create and run new simulation
        parseFile(file);
        this.view.createSimulation(this.processes,this.resources);
        this.model = new Model(this.processes, this.resources);
        runSimulation(Controller.DELAY);
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

    /**
     * Runs the simulation by executing all parsed commands with given delay
     * between each command.
     *
     * @param delay Duration in seconds to wait between commands.
     */
    private void runSimulation(int delay) {
        try {
            // Pause before starting commands
            Thread.sleep(1000);

            // Execute all commands
            boolean running = true;
            int i;
            String c = null;
            Scanner scanner;
            String pName = null;
            String action = null;
            String rName = null;
            Process p;
            while (true) {
                // Select next operable command
                i = 0;
                while (true) {
                    if (i >= this.commands.size()) {
                        running = false;
                        break;
                    }
                    c = this.commands.get(i);

                    // Check that next command is operable
                    scanner = new Scanner(c);
                    pName = scanner.next();
                    action = scanner.next();
                    rName = scanner.next();
                    p = this.model.getProcess(pName);
                    if (p.isRunning() && action.equals("requests")) {
                        // Valid requests command, operate on it
                        this.commands.remove(i);
                        break;
                    }
                    else if (p.isRunning() && action.equals("releases")){
                        // Valid releases command, operate on it
                        this.commands.remove(i);
                        break;
                    }

                    i++;
                }

                // Simulation finished
                if (!running) {
                    break;
                }

                // Print out command being executed
                System.out.println(c);

                // Resolve action of command
                switch (action) {
                    case "requests":
                        actionRequests(pName,rName);
                        break;
                    case "releases":
                        actionReleases(pName,rName);
                        break;
                }

                // Resolve requests
                resolveRequests();

                // Pause between each command
                Thread.sleep(delay * 1000);
            }

            // Output final system status
            ArrayList<String> deadlocked = new ArrayList<>();
            for (Process l: this.model.getProcesses()) {
                if (l.isDeadlocked()) {
                    deadlocked.add(l.getName());
                }
            }
            if (deadlocked.size() > 0) {
                System.out.print("System in deadlock: " + deadlocked.get(0));
                for (int j = 1; j < deadlocked.size(); j++) {
                    System.out.print(", " + deadlocked.get(j));
                }
                System.out.println();
            }
            else {
                System.out.println("System in normal condition");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the specified process to the blocked state.
     *
     * @param p Process to set in blocked state.
     */
    private void setBlocked(Process p) {
        if (p != null && !p.isBlocked()) {
            p.setBlocked();
            this.view.setBlocked(p.getName());
            System.out.println(p.getName() + " blocked");
        }
    }

    /**
     * Sets the specified process to the deadlocked state.
     *
     * @param p Process to set in deadlocked state.
     */
    private void setDeadlocked(Process p) {
        if (p != null && !p.isDeadlocked()) {
            p.setDeadlocked();
            this.view.setDeadlocked(p.getName());
            System.out.println(p.getName() + " deadlocked");
        }
    }

    /**
     * Sets the specified process to the running state.
     *
     * @param p Process to set in running state.
     */
    private void setRunning(Process p) {
        if (p != null && !p.isRunning()) {
            p.setRunning();
            this.view.setRunning(p.getName());
            System.out.println(p.getName() + " running");
        }
    }

    /**
     * Process pName requests resource rName.
     *
     * @param pName Requesting process.
     * @param rName Requested resource.
     */
    private void actionRequests(String pName, String rName) {
        this.model.requestResource(pName,rName);
    }

    /**
     * Process rName releases resource rName.
     *
     * @param pName Releasing process.
     * @param rName Released resource.
     */
    private void actionReleases(String pName, String rName) {

        this.model.releaseResource(pName,rName);
        this.view.freeResource(pName, rName);
    }

    /**
     * Resolves pending resource requests by either allocating a resource or
     * blocking a process.
     */
    private void resolveRequests() {
        // Temporary resource holder
        Resource r;

        // Check each process
        for (Process p: this.model.getProcesses()) {
            // Only processes requesting resources are of concern
            r = p.getRequested();
            if (r != null && !p.isDeadlocked()) {
                //TODO implement deadlock avoidance here


                // Give the resource to the requesting program if it is free
                if (r.getOwner() == null) {
                    setRunning(p);
                    allocateResource(p, r);
                }
                else {
                    // Process blocked
                    setBlocked(p);

                    // Check for deadlock
                    if (checkDeadlock(p, p) && PREEMPTIVE) {
                        preempt(p);
                    }
                }
            }
        }
    }

    /**
     * Adds resource r to process p.
     *
     * @param p Process receiving resource.
     * @param r Resource being given.
     */
    private void allocateResource(Process p, Resource r) {
        if (p != null && r != null) {
            System.out.println("Allocating " + r.getName() + " to " + p.getName());
            p.addResource(r);
            p.setRequested(null);
            r.setOwner(p);
            view.allocateResource(p.getName(), r.getName());
        }
    }

    /**
     * Recursively checks for deadlock along the process-resource graph.
     *
     * @param p Process to check for deadlock state.
     * @param start Starting process to check to cycle.
     * @return Whether process is in deadlock.
     */
    @Contract("null, _ -> false; !null, null -> false")
    private boolean checkDeadlock(Process p, Process start) {
        if (p == null || start == null) {
            return false;
        }

        // If a process is not blocked there can be no deadlock
        if (!p.isBlocked()) {
//            setBlocked(p);
            return false;
        }

        /*
        If the owner of the resource we are requesting is the start process,
        we are in deadlock
        */
        else if (p.getRequested().getOwner() == start) {
            setDeadlocked(p);
            return true;
        }

        // Follow graph to next node
        boolean isDeadlock = checkDeadlock(p.getRequested().getOwner(), start);

        // If process is in deadlock, change its state to deadlocked
        if (isDeadlock) {
            setDeadlocked(p);
        }
//        else {
//            setBlocked(p);
//        }

        return isDeadlock;
    }

    /**
     * Preempts the resource requested by process p from its current owner po
     * and gives it to process p. po is then placed back into command queue to
     * simulate it requesting the resource again.
     *
     * @param p Process requesting a resource.
     */
    private void preempt(Process p) {
        if (p == null) return;

        // Process originally owning resource
        Process po = p.getRequested().getOwner();

        // Resource being preempted
        Resource r = p.getRequested();

        // Preempt resource and give it to p
        System.out.println("Preempting resource " + r.getName() + " from "
                + po.getName());
        actionReleases(p.getName(),r.getName());
        allocateResource(p, r);
        setRunning(p);

        // Requeue po requesting resource before next po command
        for (int i = 0; i < this.commands.size(); i++) {
            if (this.commands.get(i).startsWith(po.getName())
                    || i == this.commands.size() - 1) {
                this.commands.add(i,po.getName() + " requests "
                        + po.getRequested().getName());
                checkDeadlock(po,po);
                actionRequests(po.getName(),r.getName());
                break;
            }
        }
    }

    /**
     * Invoked when a menu is selected.
     *
     * @param e a MenuEvent object
     */
    @Override
    public void menuSelected(MenuEvent e) {
        for (int i = 0; i < this.view.getJMenuBar().getMenuCount(); i++) {
            if (this.view.getJMenuBar().getMenu(i).getName().equals("Exit")) {
                System.exit(0);
                break;
            }
        }
    }

    /**
     * Invoked when the menu is deselected.
     *
     * @param e a MenuEvent object
     */
    @Override
    public void menuDeselected(MenuEvent e) {

    }

    /**
     * Invoked when the menu is canceled.
     *
     * @param e a MenuEvent object
     */
    @Override
    public void menuCanceled(MenuEvent e) {

    }
}