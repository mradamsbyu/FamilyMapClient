package map.family.familymapclient.model;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import java.util.ArrayList;
import java.util.HashMap;

import map.family.familymapclient.memberobjects.Auth;
import map.family.familymapclient.memberobjects.Event;
import map.family.familymapclient.memberobjects.Person;
import map.family.familymapclient.memberobjects.User;

public class Model {
    /**
     * Singleton instance of the Model class
     */
    private static Model instance = null;

    private Auth userAuthToken;
    private ArrayList<Person> persons;
    private ArrayList<Event> events;
    //private User user;
    private HashMap<String, Float> eventTypeColors;

    private final Float DEFAULT_MARKER_COLOR = BitmapDescriptorFactory.HUE_RED;

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

    private Model() {
        eventTypeColors = new HashMap<>();
        persons = new ArrayList<>();
        events = new ArrayList<>();
        setEventTypeColor("Birth", BitmapDescriptorFactory.HUE_CYAN);//Set as constants or get from a variable
        setEventTypeColor("Baptism", BitmapDescriptorFactory.HUE_GREEN);
        setEventTypeColor("Marriage", BitmapDescriptorFactory.HUE_MAGENTA);
        setEventTypeColor("Death", BitmapDescriptorFactory.HUE_ORANGE);
    }

    /**
     * Sets the color of the map markers for a given event
     * @param eventType string representation of an event type
     * @param color A color from the BitmapDescriptorFactory
     */
    public void setEventTypeColor(String eventType, Float color) {
        eventTypeColors.put(eventType, color);
    }

    public Float getEventTypeColor(String eventType) {
        if (eventTypeColors.containsKey(eventType)) {
            return eventTypeColors.get(eventType);
        }
        else {
            return DEFAULT_MARKER_COLOR;
        }
    }

    public Auth getUserAuthToken() {
        return userAuthToken;
    }

    public boolean authTokenExists() {
        if (userAuthToken == null) {
            return false;
        }
        return true;
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
}
