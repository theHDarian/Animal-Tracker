package persistence;

import model.Habitat;
import org.json.JSONWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

// Represents a writer that writes JSON representation to file, based on JsonSerializationDemo
public class JsonWriter {
    private PrintWriter writer;
    private String destination;

    public JsonWriter(String destination) {
        this.destination = "./data/" + destination + ".json";
    }

    // EFFECTS: creates a writer that will write to destination
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // EFFECTS: writes JSON representation of habitat to file
    public void write(Habitat habitat) {
        writer.print(habitat.toJson().toString());
    }

    //Effects: closes writer
    public void close() {
        writer.close();
    }

}
