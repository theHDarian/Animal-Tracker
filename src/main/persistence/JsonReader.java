package persistence;

import model.Animal;
import model.Habitat;
import model.Sighting;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;


//reads habitat from JSON data stored in file; based on JsonSerializationDemo
public class JsonReader {
    private String source;

    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads habitat from file and returns it
    public Habitat read() throws IOException {
        JSONObject json = new JSONObject(readFile(source));
        return parseHabitat(json);
    }

    // EFFECTS: reads source file as string
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        Stream<String> stream = Files.lines(Paths.get("./data/" + source + ".json"), StandardCharsets.UTF_8);
        stream.forEach(s -> contentBuilder.append(s));

        return contentBuilder.toString();
    }

    // EFFECTS: parses habitat from JSON object and returns it
    private Habitat parseHabitat(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String description = jsonObject.getString("description");
        double xsize = jsonObject.getDouble("xsize");
        double ysize = jsonObject.getDouble("ysize");
        Habitat habitat = new Habitat(name, description, xsize, ysize);
        addAnimals(habitat, jsonObject);
        return habitat;
    }

    //EFFECTS: adds parsed animals from JSON object to habitat
    private void addAnimals(Habitat habitat, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("animals");
        for (Object json : jsonArray) {
            JSONObject nextAnimal = (JSONObject) json;
            habitat.addAnimal(parseAnimal(habitat, nextAnimal));
        }
    }

    //EFFECTS: parses animal from JSON object and returns it
    private Animal parseAnimal(Habitat habitat, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String species = jsonObject.getString("species");
        String description = jsonObject.getString("description");
        double x = jsonObject.getDouble("positionx");
        double y = jsonObject.getDouble("positiony");
        Animal animal = Animal.updateSpecies(species, new Animal(species, name, x, y, description, habitat));
        addSightings(animal, jsonObject);
        return animal;
    }

    //EFFECTS: adds parsed sightings from JSON object to animal
    private void addSightings(Animal animal, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("sightings");
        for (Object json : jsonArray) {
            JSONObject nextSighting = (JSONObject) json;
            animal.addSighting(parseSighting(animal, nextSighting));
        }
    }

    //EFFECTS: parses sighting from JSON object and returns it

    private Sighting parseSighting(Animal animal, JSONObject jsonObject) {
        String date = jsonObject.getString("date");
        String description = jsonObject.getString("description");
        double x = jsonObject.getDouble("positionx");
        double y = jsonObject.getDouble("positiony");
        return new Sighting(animal, x, y, date, description);
    }
}
