package map.family.familymapclient.model;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import map.family.familymapclient.memberobjects.Auth;
import map.family.familymapclient.memberobjects.Event;
import map.family.familymapclient.memberobjects.Person;
import map.family.familymapclient.memberobjects.User;

public class Model {
    /**
     * Singleton instance of the Model class
     */
    private static Model instance = null;
    /**
     * Auth token of the user
     */
    private Auth userAuthToken;
    /**
     * List of relatives of the user
     */
    private ArrayList<Person> persons;
    /**
     * List of life events of the relatives of the user
     */
    private ArrayList<Event> events;
    /**
     * Map that maps events with their corresponding person
     */
    private HashMap<Event,Person> eventPersonMap;
    /**
     * Maps the types of events to their respective map marker color
     */
    private HashMap<String, Float> eventTypeColors;
    /**
     * Maps events to their respective marker
     */
    private HashMap<Marker, Event> eventMarkers;
    /**
     * Default color used for markers
     */
    private final Float DEFAULT_MARKER_COLOR = BitmapDescriptorFactory.HUE_RED;
    /**
     * The event that is currently being analyzed by the user
     */
    private Event currentEvent;
    /**
     * The person that is currently being analyzed by the user
     */
    private Person currentPerson;
    /*
    people and events for logged-in user's family tree
	sorted list of events for each person
	list of children for each person
	event types
	event type colors
	paternal ancestors
	maternal ancestors
	Settings (2.5%)
    Filter (2.1%)
    Search (1.8%)
     */

    /**
     * Singleton function for getting the singleton instance of this class
     * @return instance of the Model class
     */
    public static Model getInstance() {
        if (instance == null) {
            instance = new Model();
        }
        return instance;
    }

    /**
     * Constructor to initialize lists and maps
     */
    private Model() {
        eventTypeColors = new HashMap<>();
        eventMarkers = new HashMap<>();
        eventPersonMap = new HashMap<>();
        persons = new ArrayList<>();
        events = new ArrayList<>();
        setEventTypeColor("Birth", BitmapDescriptorFactory.HUE_CYAN);//Set as constants or get from a variable
        setEventTypeColor("Baptism", BitmapDescriptorFactory.HUE_GREEN);
        setEventTypeColor("Marriage", BitmapDescriptorFactory.HUE_MAGENTA);
        setEventTypeColor("Death", BitmapDescriptorFactory.HUE_ORANGE);
    }

    /**
     * Gets a list of children given the id of the parent
     * @param parentId Person id of the parent
     * @param isFather true if the id corresponds to the father.  False if it corresponds to the mother
     * @return ArrayList of Person objects representing the children
     */
    public List<Person> getChildren(String parentId, boolean isFather) {
        ArrayList<Person> children = new ArrayList<>();
        if (isFather) {
            for (Person person : persons) {
                if (person.getFather().equals(parentId)) {
                    children.add(person);
                }
            }
        }
        else {
            for (Person person : persons) {
                if (person.getMother().equals(parentId)) {
                    children.add(person);
                }
            }
        }
        return children;
    }

    /**
     * Gets a list of events corresponding to a person
     * @param personId Person id of the individual we want the events of
     * @return ArrayList of Event objects representing the events
     */
    public List<Event> getEvents(String personId) {
        ArrayList<Event> events = new ArrayList<>();
        for (Event event : events) {
            if (event.getPerson().equals(personId)) {
                events.add(event);
            }
        }
        return events;
    }

    /**
     * Sets the color of the map markers for a given event
     * @param eventType string representation of an event type
     * @param color A color from the BitmapDescriptorFactory
     */
    public void setEventTypeColor(String eventType, Float color) {
        eventTypeColors.put(eventType, color);
    }

    /**
     * Get a marker color given the event type
     * @param eventType String representing the event type
     * @return Float representing a color from the BitmapDescriptorFactory
     */
    public Float getEventTypeColor(String eventType) {
        if (eventTypeColors.containsKey(eventType)) {
            return eventTypeColors.get(eventType);
        }
        else {
            return DEFAULT_MARKER_COLOR;
        }
    }

    /**
     * Updates the map of events and persons with the current collection of persons and events
     */
    public void updateEventPersonMap() {
        for(Event event: events) {
            for(Person person: persons) {
                if (person.getPersonID().equals(event.getPerson())) {
                    eventPersonMap.put(event, person);
                    break;
                }
            }
        }
    }

    /**
     * Gets the person to which an event belongs to
     * @param event Event object that we want to find the person for
     * @return Person object representing the person of the corresponding event
     */
    public Person getPersonFromEvent(Event event) {
        return eventPersonMap.get(event);
    }

    /**
     * Put an event/marker relationship into a map
     * @param event Event that you would like to place in the map
     * @param marker Marker that corresponds to event
     */
    public void setEventMarker(Event event, Marker marker) {
        eventMarkers.put(marker, event);
    }

    /**
     * Gets an event from eventMarkers given the marker
     * @param marker Marker that corresponds to the desired event
     * @return Event that corresponds to the marker that is passed into the function
     */
    public Event getEventFromMarker(Marker marker) {
        if (eventMarkers.containsKey(marker)) {
            return eventMarkers.get(marker);
        }
        else {
            return null;
        }
    }

    /**
     * Verifies whether the model class contains an auth token
     * @return Boolean indicating whether an auth token exists in the model class
     */
    public boolean authTokenExists() {
        if (userAuthToken == null) {
            return false;
        }
        return true;
    }

    public Auth getUserAuthToken() {
        return userAuthToken;
    }

    public void setUserAuthToken(Auth userAuthToken) {
        this.userAuthToken = userAuthToken;
    }

    public ArrayList<Person> getPersons() {
        return persons;
    }

    public void setPersons(ArrayList<Person> persons) {
        this.persons = persons;
    }

    public void addPerson(Person person) {
        this.persons.add(person);
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }

    public void addEvent(Event event) {
        this.events.add(event);
    }

    public Event getCurrentEvent() {
        return currentEvent;
    }

    public void setCurrentEvent(Event currentEvent) {
        this.currentEvent = currentEvent;
    }

    public Person getCurrentPerson() {
        return currentPerson;
    }

    public void setCurrentPerson(Person currentPerson) {
        this.currentPerson = currentPerson;
    }
}
