package model;

import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

// Represents a wolf
public class Wolf extends Animal {


    public Wolf(String name, double positionx, double positiony, String description, Habitat habitat) {
        super("Wolf", name, positionx, positiony, description, habitat);
    }
}
