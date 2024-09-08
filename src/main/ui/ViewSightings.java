package ui;

import model.Habitat;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Represents a window that allows the user to view all sightings of an animal
public class ViewSightings extends JFrame implements ActionListener {
    private final Habitat habitat;
    JTextField animalName;
    private JList list1;
    private JPanel panel1;

    public ViewSightings(Habitat habitat) {
        super("Animal Tracker");
        setContentPane(panel1);
        setSize(900, 400);
        setVisible(true);
        this.habitat = habitat;
    }

    //EFFECTS: displays all sightings of animal with name animalName
    @Override
    public void actionPerformed(ActionEvent e) {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        if (habitat.getAllAnimals().isEmpty()) {
            listModel.addElement("No animals in habitat");
        } else {
            try {
                listModel.addAll(habitat
                        .searchAnimal(JOptionPane.showInputDialog("Enter animal name")).seeSightingHistory()
                        .stream().map(sighting -> sighting.toString()).collect(java.util.stream.Collectors.toList()));
            } catch (NullPointerException exception) {
                JOptionPane.showMessageDialog(null, "Animal not found, please try again.");
            } catch (IllegalArgumentException exception) {
                JOptionPane.showMessageDialog(null, "Out of range, please try again.");
            }
        }
        list1.setModel(listModel);
    }
}
