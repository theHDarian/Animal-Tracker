package ui;

import model.Habitat;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.stream.Collectors;


// Represents a window that allows the user to view all animals in the habitat
public class ViewAnimal extends JFrame implements ActionListener {
    private JList list1;
    private JPanel panel1;
    private Habitat habitat;

    public ViewAnimal(Habitat habitat) {
        super("Animal Tracker");
        setContentPane(panel1);
        setSize(900, 400);
        setVisible(true);
        this.habitat = habitat;
    }

    // EFFECTS: displays all animals in habitat
    @Override
    public void actionPerformed(ActionEvent e) {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        if (habitat.getAllAnimals().isEmpty()) {
            listModel.addElement("No animals in habitat");
        } else {
            listModel.addAll(habitat.getAllAnimals().stream()
                    .map(a -> a.getName() + ", " + a.getDescription()).collect(Collectors.toList()));
        }
        list1.setModel(listModel);
    }
}
