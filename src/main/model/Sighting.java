package model;

import org.json.JSONObject;
import persistence.Output;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//represents a sighting of an animal at a certain time and place
public class Sighting implements Comparable<Sighting>, Output {
    private Animal animal;
    private double positionx;

    private double positiony;

    private LocalDateTime date;
    private String description;

    private final DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    //requires: date and time are formatted correctly: yyyy-mm-dd, hh:mm
    public Sighting(Animal animal, double positionx, double positiony, String date, String time, String description) {
        this.animal = animal;
        this.positionx = positionx;
        this.positiony = positiony;
        this.date = LocalDateTime.parse(date + "T" + time);
        this.description = description;
    }

    //requires: dateandtime is formatted correctly: yyyy-mm-dd hh:mm

    public Sighting(Animal animal, double positionx, double positiony, String dateandtime, String description) {
        this.animal = animal;
        this.positionx = positionx;
        this.positiony = positiony;
        this.date = LocalDateTime.parse(dateandtime, format);
        this.description = description;
    }

    public double getPositionx() {
        return positionx;
    }

    public double getPositiony() {
        return positiony;
    }

    public LocalDateTime getDateAndTime() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public Animal getAnimal() {
        return animal;
    }

    //effects: more recent sightings are considered greater
    @Override
    public int compareTo(Sighting othersighting) {
        return this.getDateAndTime().compareTo(othersighting.getDateAndTime());
    }

    @Override
    public String toString() {
        return "Sighting of " + animal.getName() + ", at (" + positionx + ", " + positiony + "), "
                + "on " + date.format(format) + ":\n" + description;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("animal", animal);
        json.put("positionx", positionx);
        json.put("positiony", positiony);
        json.put("date", date.format(format));
        json.put("description", description);
        return json;
    }
}
