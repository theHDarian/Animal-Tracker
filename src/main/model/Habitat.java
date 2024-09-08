package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Output;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// Represents a habitat that many animals live in
public class Habitat implements Output {
    private String name;
    private String description;
    private double xsize;
    private double ysize;
    private List<Animal> animals = new ArrayList<Animal>();

    public Habitat(String name, String description, double xsize, double ysize) {
        this.name = name;
        this.description = description;
        this.xsize = xsize;
        this.ysize = ysize;
    }

    public double getXsize() {
        return xsize;
    }

    public double getYsize() {
        return ysize;
    }

    public void addAnimal(Animal animal) {
        this.animals.add(animal);
    }

    public void removeAnimal(Animal animal) {
        animals.remove(animal);
    }

    public List<Animal> getAllAnimals() {
        EventLog.getInstance().logEvent(new Event("Viewed all animals in habitat"));
        return new ArrayList<>(animals);
    }

    //requires: name is a valid animal name, names are unique
    //effects: returns the animal with the specified name, or null if no such animal exists
    public Animal searchAnimal(String name) {
        for (Animal a : animals) {
            if (a.getName().equals(name)) {
                return a;
            }
        }
        return null;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("description", description);
        json.put("xsize", xsize);
        json.put("ysize", ysize);
        json.put("animals", new JSONArray(animals.stream().map(Animal::toJson).collect(Collectors.toList())));
        return json;
    }
}
