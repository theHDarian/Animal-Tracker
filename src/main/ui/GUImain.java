package ui;

import model.Habitat;
import persistence.JsonReader;

import javax.swing.*;
import java.io.IOException;

public class GUImain {

    // EFFECTS: displays a window with a text field for the user to enter a file
    // name and loads the habitat from that file, or creates a new habitat then displays the main menu

    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public static void main(String[] args) {
        while (true) {
            JTextField savename = new JTextField();
            Object[] message = {
                    "Enter Save file name:", savename,
            };
            int n = JOptionPane.showConfirmDialog(null, message, "Load Save", JOptionPane.OK_CANCEL_OPTION);
            if (n == JOptionPane.OK_OPTION) {
                try {
                    new GUI(new JsonReader(savename.getText()).read());
                    break;
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "Unable to read from file, please try again.");
                    continue;
                }
            }
            if (n == JOptionPane.CANCEL_OPTION) {
                JTextField habitatname = new JTextField();
                JTextField habitatdescription = new JTextField();
                JTextField xsize = new JTextField();
                JTextField ysize = new JTextField();
                Object[] habitatmessage = {
                        "Enter Habitat name:", habitatname,
                        "Enter Habitat description:", habitatdescription,
                        "Enter Habitat x size:", xsize,
                        "Enter Habitat y size:", ysize,
                };
                int m = JOptionPane.showConfirmDialog(null, habitatmessage, "Create Habitat",
                        JOptionPane.OK_CANCEL_OPTION);
                if (m == JOptionPane.OK_OPTION) {
                    try {
                        new GUI(new Habitat(habitatname.getText(), habitatdescription.getText(),
                                Double.parseDouble(xsize.getText()), Double.parseDouble(ysize.getText())));
                        break;
                    } catch (IllegalArgumentException | IOException exception) {
                        JOptionPane.showMessageDialog(null, "Invalid input, please try again.");
                        continue;
                    }
                }
                System.exit(0);
            }
        }
    }
}
