package com.madsen.view;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * Panel to display all information about a single process.
 */
public class ProcessPanel extends JPanel {

    /** Colors to display states of process execution */
    public static final Color BLOCKED = new Color(255,255,0);
    public static final Color DEADLOCKED = new Color(255,0,0);
    public static final Color RUNNING = new Color(50,130,50);

    /** Height of a ProcessPanel */
    public static final int PREF_HEIGHT = 100;

    /** Width of a ProcessPanel */
    public static final int PREF_WIDTH = 780;

    /** Panel responsible for displaying resources */
    private JPanel resources;

    /** Unique color of this process */
    private Color col;

    /**
     * Constructs a new object for the process panel with all of the necessary
     * components to display process execution and resources held.
     *
     * @param name Name of the process being created.
     */
    public ProcessPanel(String name) {
        super();

        // Set the name of this panel
        this.setName(name);

        // Set the color of this panel
        Random r = new Random();
        this.col = new Color(r.nextInt(256), r.nextInt(256),
                r.nextInt(256));

        // Create a border around this panel
        this.setBorder(BorderFactory.createLineBorder(this.col,3));

        // Set the size of this panel
        this.setPreferredSize(new Dimension(PREF_WIDTH, PREF_HEIGHT));

        // Setup the layout of this panel
        this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        this.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Setup the label of this panel
        JPanel processLabel = new JPanel();
        processLabel.setBackground(ProcessPanel.RUNNING);
        processLabel.setPreferredSize(new Dimension(100, 100));
        processLabel.setLayout(new GridBagLayout());
        JLabel label = new JLabel(name);
        processLabel.add(label);
        this.add(processLabel);

        // Setup the resource tracking of this process
        this.resources = new JPanel();
        this.resources.setPreferredSize(new Dimension(674,100));
        this.resources.setLayout(new FlowLayout(FlowLayout.LEFT));
        //FIXME remove
//        for (int i = 0; i < 20; i++) {
//            addResource("Test " + i);
//        }
        this.add(this.resources);
    }

    /**
     * Adds the specified resource name to the list of held resources.
     *
     * @param name Name of the resource to add.
     */
    public void addResource(String name) {
        this.resources.add(new ResourcePanel(name));
    }

    /**
     * Removes the specified resource name from the list of held resources.
     *
     * @param name Name of the resource to remove.
     * @return Whether a resource was removed.
     */
    public boolean removeResource(String name) {
        Component res[] = this.resources.getComponents();
        for (int i = 0; i < res.length; i++) {
            if (res[i].getName().equals(name)) {
                this.resources.remove(res[i]);
                return true;
            }
        }

        return false;
    }

}
