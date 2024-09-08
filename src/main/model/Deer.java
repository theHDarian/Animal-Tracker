package model;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Represents a deer
public class Deer extends Animal {

    public Deer(String name, double positionx, double positiony, String description, Habitat habitat) {
        super("Deer", name, positionx, positiony, description, habitat);
    }
}
