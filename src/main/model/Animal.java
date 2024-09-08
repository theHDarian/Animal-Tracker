package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

// Represents an animal with a name, position, description, and sighting history
public class Animal implements persistence.Output {

    protected String name;
    protected double positionx;
    protected double positiony;

    protected final Habitat habitat;

    protected String description;
    protected List<Sighting> sightings = new ArrayList<Sighting>();

    private String species;

    public Animal(String species, String name,
                  double positionx, double positiony, String description, Habitat habitat) {
        this.name = name;
        this.positionx = positionx;
        this.positiony = positiony;
        this.description = description;
        this.habitat = habitat;
        this.species = species;
    }

    //requires: positionx and positiony are within the habitat's bounds, date and time are formatted correctly
    // EFFECTS: creates a new animal of the specified type and adds it to the habitat, with a sighting
    public static void tag(String species, String name, double posx,
                           double posy, String description, String date, Habitat habitat) {
        if (posx < 0 || posy < 0 || posx > habitat.getXsize() || posy > habitat.getYsize()) {
            throw new IllegalArgumentException();
        }
        Animal newanimal = updateSpecies(species, new Animal(species,name, posx, posy, description, habitat));
        newanimal.addSighting(new Sighting(
                newanimal, posx, posy, date, "Initial Sighting of " + name + ", " + description));
        habitat.addAnimal(newanimal);
        EventLog.getInstance().logEvent(new Event("Animal " + newanimal.getName() + " tagged!"));
    }

    //EFFECTS: returns a new animal of the specified species, otherwise returns a generic animal
    public static Animal updateSpecies(String species, Animal animal) {
        Animal newanimal;
        switch (species.toLowerCase()) {
            case "wolf":
                newanimal =
                        new Wolf(animal.getName(), animal.getPositionx(),
                                animal.getPositiony(), animal.getDescription(), animal.habitat);
                break;
            case "deer":
                newanimal =
                        new Deer(animal.getName(), animal.getPositionx(),
                                animal.getPositiony(), animal.getDescription(), animal.habitat);
                break;
            default:
                newanimal = animal;
        }
        return newanimal;
    }

    //REQUIRES: positionx and positiony are within the habitat's bounds
    //MODIFIES: this
    //EFFECTS: adds a new sighting to the animal's sighting history,
    // and updates the animal's position if the sighting is the latest
    public void addSighting(double positionx, double positiony, String date, String time, String description) {
        this.addSighting(new Sighting(this, positionx, positiony, date, time, description));
        EventLog.getInstance().logEvent(new Event("Sighting of " + this.getName() + " added!"));
    }

    public void addSighting(double positionx, double positiony, String date, String description) {
        this.addSighting(new Sighting(this, positionx, positiony, date, description));
        EventLog.getInstance().logEvent(new Event("Sighting of " + this.getName() + " added!"));
    }

    public void addSighting(Sighting sighting) {
        if (sighting.getPositionx() < 0 || sighting.getPositiony()  < 0
                || sighting.getPositionx()  > habitat.getXsize() || sighting.getPositiony() > habitat.getYsize()) {
            throw new IllegalArgumentException("Animal is not in habitat!");
        } else if (sighting.getAnimal() != this) {
            throw new IllegalArgumentException("Sighting is not of this animal!");
        }
        this.sightings.add(sighting);
        Collections.sort(sightings);
        if (sightings.size() > 1) {
            this.positionx = sightings.get(sightings.size() - 1).getPositionx();
            this.positiony = sightings.get(sightings.size() - 1).getPositiony();
        }
    }

    public void addSighting(List<Sighting> sightings) {
        for (Sighting s : sightings) {
            this.addSighting(s);
            EventLog.getInstance().logEvent(new Event("Sighting of " + this.getName() + " added!"));
        }
    }

    public List<Sighting> seeSightingHistory() {
        EventLog.getInstance().logEvent(new Event("Sighting history of " + this.getName() + " viewed!"));
        return new ArrayList<Sighting>(this.sightings);
    }

    public String getName() {
        return this.name;
    }

    public double getPositionx() {
        return this.positionx;
    }

    public double getPositiony() {
        return this.positiony;
    }

    public String getDescription() {
        return this.description;
    }

    public String getSpecies() {
        return this.species;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    //effects: creates a json object representing this animal
    //implementation based on example given on edx
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("species", this.getSpecies());
        json.put("positionx", positionx);
        json.put("positiony", positiony);
        json.put("description", description);
        json.put("sightings", new JSONArray(sightings.stream().map(Sighting::toJson).collect(Collectors.toList())));
        return json;
    }
    //effects: creates a json array representing sightings of this animal

//    JSONArray sightingsToJson() {
//        JSONArray jsonArray = new JSONArray();
//
//        for (Sighting s : sightings) {
//            jsonArray.put(s.toJson());
//        }
//
//        return jsonArray;
//    }
}
