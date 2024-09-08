package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class AnimalTest {
    Habitat habitat;
//    private Event e1;
//    private Event e2;
//    private Event e3;
//
//    private Event e;
//    private Date d;
    @BeforeEach
    void setUp() {
    habitat = new Habitat("Habitat", "Habitat", 100, 100);
    }

    @Test
    void testsaveandload() throws Exception{
        Animal.tag("wolf", "wolf1", 50, 50, "wolf", "2000-01-01 00:00", habitat);
        Animal.tag("deer", "deer1", 0, 0, "deer", "2000-01-01 00:00", habitat);
        Animal animal =  habitat.getAllAnimals().get(0);
        assertEquals("wolf1", animal.getName());
        JsonWriter out = new JsonWriter("./testsave.json");
        out.open();
        out.write(habitat);
        out.close();
        habitat = new JsonReader("./testsave.json").read();
        assertEquals(2, habitat.getAllAnimals().size());
        animal =  habitat.getAllAnimals().get(0);
        assertEquals("wolf1", animal.getName());
        assertEquals("Sighting of wolf1, at (50.0, 50.0), on 2000-01-01 00:00:\n" +
                "Initial Sighting of wolf1, wolf", animal.seeSightingHistory().get(0).toString());
        animal =  habitat.getAllAnimals().get(1);
        animal.setDescription("deer2");
        assertEquals("deer2", animal.getDescription());
    }

    @Test
    void tag() throws ClassNotFoundException {
        Animal.tag("wolf", "wolf1", 50, 50, "wolf", "2000-01-01 00:00", habitat);
       assertEquals(1, habitat.getAllAnimals().size());
       Animal animal =  habitat.getAllAnimals().get(0);
         assertEquals("wolf1", animal.getName());
            assertEquals("2000-01-01T00:00", animal.seeSightingHistory().get(0).getDateAndTime().toString());
            assertEquals("Initial Sighting of wolf1, wolf", animal.seeSightingHistory().get(0).getDescription());
    }

    @Test
    void testfindanimal(){
        Animal.tag("wolf", "wolf1", 50, 50, "wolf", "2000-01-01 00:00", habitat);
        Animal animal =  habitat.getAllAnimals().get(0);
        assertEquals(animal, habitat.searchAnimal("wolf1"));
        assertEquals(null, habitat.searchAnimal("wolf2"));
    }

    @Test
    void tagfordeer(){
        Animal.tag("deer", "deer1", 0, 0, "deer", "2000-01-01 00:00", habitat);
        assertEquals(1, habitat.getAllAnimals().size());
        Animal animal =  habitat.getAllAnimals().get(0);
        assertEquals("deer1", animal.getName());
        assertEquals("2000-01-01T00:00", animal.seeSightingHistory().get(0).getDateAndTime().toString());
        assertEquals("Initial Sighting of deer1, deer", animal.seeSightingHistory().get(0).getDescription());
    }

    @Test
    void addSighting() {
        Animal.tag("wolf", "wolf1", 50, 50, "wolf", "2000-01-01 00:00", habitat);
        Animal animal =  habitat.getAllAnimals().get(0);
        animal.addSighting(new Sighting(animal, 75, 25, "2000-01-02 08:00", "wolf1 has moved northwest"));
        assertEquals(2, animal.seeSightingHistory().size());
        animal.addSighting(55, 35, "2000-01-02", "06:00", "wolf1 seems to have taken a break at this location");
        animal.addSighting(55, 35, "2000-01-02 07:00", "wolf1 seems to have taken a break at this location");
        assertEquals(55, animal.seeSightingHistory().get(1).getPositionx());
        assertEquals(50, animal.seeSightingHistory().get(0).getPositiony());
        assertEquals(25, animal.getPositiony());
        assertEquals(75, animal.getPositionx());
        ArrayList<Sighting>  sightings = new ArrayList<>();
        sightings.add(new Sighting(animal, 75, 25, "2000-01-02", "10:00", "wolf1 has moved northwest"));
        sightings.add(new Sighting(animal, 75, 25, "2000-01-02 12:00", "wolf1 has moved northwest"));
        animal.addSighting(sightings);
        assertEquals(6, animal.seeSightingHistory().size());


    }

    @Test
    void Testaddfailcases() {
        try {
            Animal.tag("deer", "deer1", 220, 0, "deer", "2000-01-01 00:00", habitat);
                  fail();
        } catch (IllegalArgumentException e) {
            //expected
        }
        try {
            Animal.tag("deer", "deer1", 0, 990, "deer", "2000-01-01 00:00", habitat);
            fail();
        } catch (IllegalArgumentException e) {
            //expected
        }
        try {
            Animal.tag("deer", "deer1", -220, 0, "deer", "2000-01-01 00:00", habitat);
            fail();
        } catch (IllegalArgumentException e) {
            //expected
        }
        try {
            Animal.tag("deer", "deer1", 0, -990, "deer", "2000-01-01 00:00", habitat);
            fail();
        } catch (IllegalArgumentException e) {
            //expected
        }
        try {
            Animal.tag("deer", "deer1", -10, -990, "deer", "2000-01-01 00:00", habitat);
            fail();
        } catch (IllegalArgumentException e) {
            //expected
        }
        try {
            Animal.tag("deer", "deer1", 1000, 990, "deer", "2000-01-01 00:00", habitat);
            fail();
        } catch (IllegalArgumentException e) {
            //expected
        }
        try {
            Animal.tag("deer", "deer1", 0, 0, "deer", "2000-01-01 00:00", habitat);
            Animal animal =  habitat.getAllAnimals().get(0);
            animal.addSighting(new Sighting(new Animal( "wolf", "wolf",  50, 50, "wolf", habitat), 75, 25, "2000-01-02", "08:00", "wolf1 has moved northwest"));
             fail();
        } catch (IllegalArgumentException e) {
            //expected
        }
        try {
            Animal.tag("deer", "deer1", 0, 0, "deer", "2000-01-01 00:00", habitat);
            Animal animal =  habitat.getAllAnimals().get(0);
            animal.addSighting(300, 999, "2000-01-02", "08:00", "wolf1 has moved northwest");
            fail();
        } catch (IllegalArgumentException e) {
            //expected
        }
        try {
            Animal.tag("deer", "deer1", 0, 0, "deer", "2000-01-01 00:00", habitat);
            Animal animal =  habitat.getAllAnimals().get(0);
            animal.addSighting(0, 999, "2000-01-02", "08:00", "wolf1 has moved northwest");
            fail();
        } catch (IllegalArgumentException e) {
            //expected
        }
        try {
            Animal.tag("deer", "deer1", 0, 0, "deer", "2000-01-01 00:00", habitat);
            Animal animal =  habitat.getAllAnimals().get(0);
            animal.addSighting(300, 9, "2000-01-02", "08:00", "wolf1 has moved northwest");
            fail();
        } catch (IllegalArgumentException e) {
            //expected
        }
        try {
            Animal.tag("deer", "deer1", 0, 0, "deer", "2000-01-01 00:00", habitat);
            Animal animal =  habitat.getAllAnimals().get(0);
            animal.addSighting(-300, -999, "2000-01-02", "08:00", "wolf1 has moved northwest");
            fail();
        } catch (IllegalArgumentException e) {
            //expected
        }
        try {
            Animal.tag("deer", "deer1", 0, 0, "deer", "2000-01-01 00:00", habitat);
            Animal animal =  habitat.getAllAnimals().get(0);
            animal.addSighting(-300, 9, "2000-01-02", "08:00", "wolf1 has moved northwest");
            fail();
        } catch (IllegalArgumentException e) {
            //expected
        }
        try {
            Animal.tag("deer", "deer1", 0, 0, "deer", "2000-01-01 00:00", habitat);
            Animal animal =  habitat.getAllAnimals().get(0);
            animal.addSighting(3, -9, "2000-01-02", "08:00", "wolf1 has moved northwest");
            fail();
        } catch (IllegalArgumentException e) {
            //expected
        }
    }

    @Test
    void testhabitat() throws ClassNotFoundException {
        Animal.tag("DRAGON!", "coolboi", 50, 50, "sadly dragons are too cool for this program", "2000-01-01 00:00", habitat);
        Animal animal =  habitat.getAllAnimals().get(0);
        assertEquals("coolboi", animal.getName());
        habitat.removeAnimal(animal);
        assertEquals(0, habitat.getAllAnimals().size());
    }
//    @BeforeEach
//    public void loadEvents() {
//        e1 = new Event("A1");
//        e2 = new Event("A2");
//        e3 = new Event("A3");
//        EventLog el = EventLog.getInstance();
//        el.logEvent(e1);
//        el.logEvent(e2);
//        el.logEvent(e3);
//    }
//
//    @Test
//    public void testLogEvent() {
//        ArrayList<Event> l = new ArrayList<Event>();
//
//        EventLog el = EventLog.getInstance();
//        for (Event next : el) {
//            l.add(next);
//        }
//
//        assertTrue(l.contains(e1));
//        assertTrue(l.contains(e2));
//        assertTrue(l.contains(e3));
//    }
//
//    @Test
//    public void testClear() {
//        EventLog el = EventLog.getInstance();
//        el.clear();
//        assertEquals("Event log cleared.", el.iterator().next().getDescription());
//    }
//
//    @BeforeEach
//    public void runBefore() {
//        e = new Event("Sensor open at door");   // (1)
//        d = Calendar.getInstance().getTime();   // (2)
//    }
//
//    @Test
//    public void testEvent() {
//        assertEquals("Sensor open at door", e.getDescription());
//        assertEquals(d.toString(), e.getDate().toString());
//        assertNotEquals(e.hashCode(), new Event("Sensor open at door").hashCode());
//        assertFalse(e.equals("Sensor open at door"));
//        assertFalse(e.equals(null));
//    }
//
//    @Test
//    public void testToString() {
//        assertEquals(d.toString() + "\n" + "Sensor open at door", e.toString());
//    }
//

}