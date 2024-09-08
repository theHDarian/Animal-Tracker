package ui;

import model.Habitat;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

// Represents a window that allows the user to save the habitat to a file
public class SaveToFile extends JFrame implements ActionListener {

    private final Habitat habitat;

    public SaveToFile(Habitat habitat) {
        super("Animal Tracker");
        this.habitat = habitat;
    }

    // EFFECTS: displays a window with a text field for the user to enter a file name and saves the habitat to that file
    @Override
    public void actionPerformed(ActionEvent e) {
        JTextField savename = new JTextField();
        Object[] message = {
                "Enter Save file name:", savename,
        };
        if (JOptionPane.showConfirmDialog(null, message,
                "Save", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                JsonWriter writer = new JsonWriter(savename.getText());
                writer.open();
                writer.write(habitat);
                writer.close();
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(null, "Unable to write to file, please try again.");
            }
        }

    }
}
