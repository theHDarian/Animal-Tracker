package ui;

import model.Habitat;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeParseException;

// represents a window that allows the user to add a sighting to an animal
public class AddSighting implements ActionListener {
    private final Habitat habitat;
    private JTextField animalNameTextField;
    private JTextField xposition;
    private JTextField dateTextField;
    private JTextField yposition;
    private JTextField descriptionTextField;

    public AddSighting(Habitat habitat) {
        this.habitat = habitat;
    }

    // EFFECTS: displays a window with text fields
    // for the user to enter the sighting's information and adds the sighting to the animal
    @Override
    public void actionPerformed(ActionEvent e) {
        Object[] message = {
                "Animal Name:", animalNameTextField,
                "Position X:", xposition,
                "Position Y:", yposition,
                "Date and time of sighting (yyyy-mm-dd hh:mm):", dateTextField,
                "Description:", descriptionTextField,
        };
        if (JOptionPane.showConfirmDialog(null, message, "Add Sighting",
                JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                habitat.searchAnimal(animalNameTextField.getText()).addSighting(Double.parseDouble(xposition.getText()),
                        Double.parseDouble(yposition.getText()),
                        dateTextField.getText(), descriptionTextField.getText());
            } catch (IllegalArgumentException exception) {
                JOptionPane.showMessageDialog(null, "Out of range, please try again.");
            } catch (NullPointerException exception) {
                JOptionPane.showMessageDialog(null, "Animal not found, please try again.");
            } catch (DateTimeParseException exception) {
                JOptionPane.showMessageDialog(null, "Invalid date format, please try again.");
            }
        }
    }
}
