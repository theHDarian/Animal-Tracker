package ui;

import model.EventLog;
import model.Habitat;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

// Represents a window that allows the user to interact with the habitat
public class GUI extends JFrame {
    private JButton addAnimalButton;
    private JButton viewSightingsButton;
    private JButton viewAnimalsButton;
    private JButton addSightingButton;
    private JPanel mainpanel;
    private JButton saveToFileButton;
    private JPanel imgpanel;
    private JLabel imglabel;

    public GUI(Habitat habitat) throws IOException {
        super("Animal Tracker");
        setContentPane(mainpanel);
        setSize(800, 550);
        setVisible(true);
        createUIComponents(habitat);
        imglabel.setIcon(new ImageIcon("./data/anms.jpg"));
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                EventLog.getInstance().forEach(e -> System.out.println(e.getDescription() + " at " + e.getDate()));
                System.exit(0);
            }
        });
    }

    // EFFECTS: creates the buttons and their actions
    private void createUIComponents(Habitat habitat) {
        addAnimalButton.addActionListener(e -> {
            AddAnimal addAnimal = new AddAnimal(habitat);
            addAnimal.actionPerformed(e);
        });
        viewAnimalsButton.addActionListener(e -> {
            ViewAnimal viewAnimal = new ViewAnimal(habitat);
            viewAnimal.actionPerformed(e);
        });
        addSightingButton.addActionListener(e -> {
            AddSighting addSighting = new AddSighting(habitat);
            addSighting.actionPerformed(e);
        });
        viewSightingsButton.addActionListener(e -> {
            ViewSightings viewSightings = new ViewSightings(habitat);
            viewSightings.actionPerformed(e);
        });
        saveToFileButton.addActionListener(e -> {
            SaveToFile saveToFile = new SaveToFile(habitat);
            saveToFile.actionPerformed(e);
        });
    }
}


