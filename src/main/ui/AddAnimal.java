package ui;

import model.Animal;
import model.Habitat;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeParseException;

// Represents a window that allows the user to add an animal to the habitat
public class AddAnimal implements ActionListener {
    private final Habitat habitat;
    private JTextField name = new JTextField();
    private JTextField species = new JTextField();
    private JTextField positionx = new JTextField();
    private JTextField positiony = new JTextField();
    private JTextField description = new JTextField();
    private JTextField date = new JTextField();

    public AddAnimal(Habitat habitat) {

        this.habitat = habitat;
    }

    // EFFECTS: displays a window with text fields for the user to enter the animal's information and adds the animal

    @Override
    public void actionPerformed(ActionEvent e) {
        Object[] message = {
                "Name:", name,
                "Species:", species,
                "Position X:", positionx,
                "Position Y:", positiony,
                "Description:", description,
                "Date and time of sighting (yyyy-mm-dd hh:mm):", date,
        };
        if (JOptionPane.showConfirmDialog(null, message,
                "Add Animal", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                Animal.tag(species.getText(), name.getText(), Double.parseDouble(positionx.getText()),
                        Double.parseDouble(positiony.getText()), description.getText(), date.getText(), habitat);
            } catch (IllegalArgumentException exception) {
                JOptionPane.showMessageDialog(null, "Out of range, please try again.");
            } catch (DateTimeParseException exception) {
                JOptionPane.showMessageDialog(null, "Invalid date format, please try again.");
            }
        }
    }
}
