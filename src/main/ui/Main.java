package ui;

import model.Animal;
import model.Habitat;
import model.Sighting;
import persistence.JsonReader;
import persistence.JsonWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {

    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Habitat habitat;

        System.out.println("Welcome to the Wildlife Tracker!");
        System.out.println("do you want to load a previous save file? (y to load, anything else to start new)");
        String load = input.nextLine();
        if (load.equalsIgnoreCase("y")) {
            while (true) {
                System.out.println("Please enter the name of the file you would like to load:");
                String file = input.nextLine();
                try {
                    habitat = new JsonReader(file).read();
                    break;
                } catch (IOException e) {
                    System.out.println("Unable to read from file: " + file + ", please try again.");
                }
            }
        } else {
            System.out.println("Please enter the name of the habitat you would like to add:");
            String name = input.nextLine();
            System.out.println("Please enter the description of the habitat:");
            String description = input.nextLine();
            System.out.println("Please enter the x size of the habitat:");
            double xsize = input.nextDouble();
            System.out.println("Please enter the y size of the habitat:");
            double ysize = input.nextDouble();
            habitat = new Habitat(name, description, xsize, ysize);
            System.out.println("Habitat created!");
        }
        while (true) {
            System.out.println("Please enter the number of the action you would like to perform:");
            System.out.println("0: Tag a animal and add it to habitat");
            System.out.println("1: View all animals in the habitat");
            System.out.println("2: add a sighting to to an animal");
            System.out.println("3: View all sightings of an animal");
            System.out.println("8: quit without saving");
            System.out.println("9: Save and quit");
            int action = input.nextInt();
            input.nextLine();
            switch (action) {
                case 0:
                    addAnimal(input, habitat);
                    break;
                case 1:
                    viewAnimals(habitat);
                    break;
                case 2:
                    addSighting(input, habitat);
                    break;
                case 3:
                    viewSightings(input, habitat);
                    break;
                case 8:
                    System.exit(0);
                case 9:
                    System.out.println("Please enter a name for the save file:");
                    String file;
                    while (true) {
                        file = input.nextLine();
                        try {
                            JsonWriter out = new JsonWriter(file);
                            out.open();
                            out.write(habitat);
                            out.close();
                            System.out.println("Habitat saved!");
                            break;
                        } catch (FileNotFoundException e) {
                            System.out.println("Unable to write to file: " + file + ", please try again.");
                        }
                    }
                    System.exit(0);
                default:
                    System.out.println("Invalid input, please try again.");
            }
        }
    }

    //EFFECTS: prints all animals in the habitat
    private static void viewAnimals(Habitat habitat) {
        System.out.println("Animals in habitat:");
        habitat.getAllAnimals().stream().forEach(a -> System.out.println(a.getName() + ", " + a.getDescription()));
    }

    //MODIFIES: habitat, animal
    //EFFECTS: adds a new sighting to the animal's sighting history

    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private static void addSighting(Scanner input, Habitat habitat) {
        Animal animal;
        while (true) {
            System.out.println("Please enter the name of the animal you would like to add a sighting to:");
            String name = input.nextLine();
            animal = habitat.searchAnimal(name);
            if (animal == null) {
                System.out.println("Animal not found!");
            } else {
                break;
            }
        }
        System.out.println("Please enter the x position of the animal:");
        double x = input.nextDouble();
        input.nextLine();
        System.out.println("Please enter the y position of the animal:");
        double y = input.nextDouble();
        input.nextLine();
        System.out.println("Please enter a description of the sighting:");
        String description = input.nextLine();
        System.out.println("When was the animal seen? (yyyy-MM-dd HH:mm)");
        String date;
        while (true) {
            try {
                date = input.nextLine();
                animal.addSighting(new Sighting(animal, x, y, date, description));
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format, please try again.");
            }
        }
        System.out.println("Sighting added!");
    }

    //EFFECTS: prints all sightings of an animal
    private static void viewSightings(Scanner input, Habitat habitat) {
        Animal animal;
        while (true) {
            System.out.println("Please enter the name of the animal you would like to view sightings of:");
            String name = input.nextLine();
            animal = habitat.searchAnimal(name);
            if (animal == null) {
                System.out.println("Animal not found!");
            } else {
                break;
            }
        }
        System.out.println("Sightings of " + animal.getName() + ":");
        animal.seeSightingHistory().stream().forEach(a -> System.out.println(a.toString()));
    }

    //Effects: adds an animal to the habitat
    private static void addAnimal(Scanner input, Habitat habitat) {
        System.out.println("Enter the name of the animal you would like to add:");
        String name = input.nextLine();
        System.out.println("Enter the species of the animal:");
        String species = input.nextLine();
        System.out.println("Enter a description of the animal:");
        String description = input.nextLine();
        System.out.println("Please enter the x position of the animal:");
        double x = input.nextDouble();
        input.nextLine();
        System.out.println("Please enter the y position of the animal:");
        double y = input.nextDouble();
        input.nextLine();
        System.out.println("When was the animal first seen? (yyyy-MM-dd HH:mm)");
        trytoadd(species, name, x, y, description, habitat, input);
    }

    //EFFECTS: tries to add an animal to the habitat, if the date is invalid, prompts the user to try again
    private static void trytoadd(String species, String name, double x,
                                 double y, String description, Habitat habitat, Scanner input) {
        String date;
        while (true) {
            try {
                date = input.nextLine();
                Animal.tag(species, name, x, y, description, date, habitat);
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format, please try again.");
            }
        }
        System.out.println("Animal added!");
    }
}
