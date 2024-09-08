# A wildlife tagging system and simulator

## Introduction
this application aims to provide a user-friendly interface to tag wildlife animals. 
it should be able to:
- represent the environment in which the animals live in
- represent the animals themselves (as objects) and be able to graphically display them and their movements
- support both carnivorous and herbivorous animals
- be able to simulate and predict the movements and population of each type of animal

the application is designed so that it should be of use to both wildlife enthusiasts and researchers, as well as those simply interested in some fun.

this application is born out of my own interest in wildlife and my desire to imagine how wild animals live and interact with each other. I hope that this application will be of interest to others as well.

## User stories
- as a user, I want to be able to tag an animal and specify its habitat (create an animal object of a certain type, and specify its habitat)
- as a user, I want to be able to view a list of all the animals in a specific habitat (view a list of all animal objects with a certain habitat)
- as a user, I want to be able to add new sightings of an animal (update the location of an animal object, and add new sightings to the list of sightings of that animal object)
- as a user, I want to be able to view a list of all the sightings of an animal (view a list of all the locations of an animal object)
##### Phase 2:
- as a user, I want to have the option to save the data of a habitat to a file (save the data of a habitat and all attached all animal objects to a file)
- as a user, I want to have the option to load the data of a habitat from a file (load the data of a habitat and all attached animal objects from a file)


#### Instructions for Grader

- You can generate the first required action related to the user story "adding multiple Xs to a Y" by clicking on the "Add Animal" button once you reach the main window. 
  This will open a new window where you can input the animal's details, Once you click "Add", the animal will be added to the habitat. Alternatively, you can click "Add Sightings" to add a new sighting of an animal.
- You can generate the second required action related to the user story "adding multiple Xs to a Y" by clicking on the "View Animals" button once you reach the main window. 
  This will open a new window where you can view all the animals in the habitat. Alternatively, you can click "View Sightings" to view all the sightings of an animal.
- You can locate my visual component by going to the main window. you will see a image of a map. This is my visual component.
- You can save the state of my application by going to the main window and clicking on the "Save To File" button. This will save the state of the habitat and all attached animal objects to a file in the data folder.
- You can load the state of my application by typing the name of the save file and pressing "OKAY" when prompted during startup.
  This will load the state of the habitat and all attached animal objects from a file in the data folder. press "CANCEL" to create a new habitat instead. press "CANCEL" again will exit the application.

#### Phase 4: Task 2: 

Example output:
```
Animal example tagged! at Wed Nov 29 21:08:05 PST 2023
Viewed all animals in habitat at Wed Nov 29 21:08:07 PST 2023
Viewed all animals in habitat at Wed Nov 29 21:08:07 PST 2023
Sighting of example added! at Wed Nov 29 21:08:34 PST 2023
Viewed all animals in habitat at Wed Nov 29 21:08:36 PST 2023
Sighting history of example viewed! at Wed Nov 29 21:08:39 PST 2023
Animal example2 tagged! at Wed Nov 29 21:09:16 PST 2023
Viewed all animals in habitat at Wed Nov 29 21:09:17 PST 2023
Viewed all animals in habitat at Wed Nov 29 21:09:17 PST 2023
```
Due to the implementation of the code, a single action performed by the user may result in multiple events being logged. For example, the view sighting button will log the event of viewing all animals in the habitat, as well as the event of viewing all the sightings of an animal. This is because the view sighting button will first check all the animals in the habitat, and then view all the sightings of the selected animal.

#### Phase 4: Task 3:
If I have more time, I would refactor the code so that instead of Habitat having a list of animals and each animal having a list of sightings, Habitat would have a map of using animals as key and Lists of sighting as values. This would make it easier to add new sightings to the list of sightings of an animal, as well as make it easier to find the list of sightings of an animal. 
I would also refactor the GUI elements to implement a singleton pattern, so that there is only one instance of each GUI element. This will make it easier to update the GUI elements.