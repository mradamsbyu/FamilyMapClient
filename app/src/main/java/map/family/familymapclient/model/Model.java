package map.family.familymapclient.model;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;

import map.family.familymapclient.memberobjects.Auth;
import map.family.familymapclient.memberobjects.Event;
import map.family.familymapclient.memberobjects.Person;
import map.family.familymapclient.memberobjects.User;

import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_AZURE;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_BLUE;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_CYAN;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_GREEN;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_MAGENTA;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_ORANGE;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_RED;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_ROSE;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_VIOLET;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_YELLOW;

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
    private ArrayList<Person> persons; //change to set
    /**
     * List of life events of the relatives of the user
     */
    private ArrayList<Event> events; //change to set
    /**
     * Map that maps events with their corresponding person
     */
    private HashMap<Event,Person> eventPersonMap;
    /**
     * All people on the user's father's side of the family
     */
    private HashSet<Person> fathersSide; //set it
    /**
     * All people on the user's mother's side of the family
     */
    private HashSet<Person> mothersSide; //set it
    /**
     * Contains all types of events that exist
     */
    private HashSet<String> eventTypes;
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
    private final Float DEFAULT_MARKER_COLOR = HUE_RED;
    /**
     * The event that is currently being analyzed by the user
     */
    private Event currentEvent;
    /**
     * The person that is currently being analyzed by the user
     */
    private Person currentPerson;
    /**
     * Array of colors that could be applied to the map markers
     */
    private float[] eventColors = {HUE_BLUE, HUE_ORANGE, HUE_GREEN, HUE_RED, HUE_AZURE, HUE_ROSE, HUE_MAGENTA, HUE_VIOLET, HUE_YELLOW, HUE_CYAN};
    /**
     * Keeps track the index of the next color to use for a map marker
     */
    private int eventColorCounter = 0;
    /**
     * Maps an event type to whether this event is filtered
     */
    HashMap<String, Boolean> eventsFiltered;
    /**
     * Is true if the fathers side is filtered
     */
    boolean fatherSideFiltered = false;
    /**
     * Is true if the mothers side is filtered
     */
    boolean motherSideFiltered = false;
    /**
     * Is true if males are filtered
     */
    boolean maleFiltered = false;
    /**
     * Is true if females are filtered
     */
    boolean femaleFiltered = false;

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
     * Sets the model instance to null
     */
    public static void cleanModel() {
        instance = null;
    }

    /**
     * Constructor to initialize lists and maps
     */
    private Model() {
        eventTypeColors = new HashMap<>();
        eventMarkers = new HashMap<>();
        eventPersonMap = new HashMap<>();
        eventsFiltered = new HashMap<>();
        persons = new ArrayList<>();
        events = new ArrayList<>();
        eventTypes = new HashSet<>();
        mothersSide = new HashSet<>();
        fathersSide = new HashSet<>();
        currentEvent = null;
        userAuthToken = null;
        currentPerson = null;
        //setEventTypeColor("Birth", BitmapDescriptorFactory.HUE_CYAN);//Set as constants or get from a variable
        //setEventTypeColor("Baptism", BitmapDescriptorFactory.HUE_GREEN);
        //setEventTypeColor("Marriage", BitmapDescriptorFactory.HUE_MAGENTA);
        //setEventTypeColor("Death", BitmapDescriptorFactory.HUE_ORANGE);
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
                if (person.getFather() != null) {
                    if (person.getFather().equals(parentId)) {
                        children.add(person);
                    }
                }
            }
        }
        else {
            for (Person person : persons) {
                if (person.getMother() != null) {
                    if (person.getMother().equals(parentId)) {
                        children.add(person);
                    }
                }
            }
        }
        return children;
    }

    /**
     * Returns the person object associated with a person id
     * @param id person id of the person you are looking for
     * @return Person object associated with the id, or null if that id does not exist in the database
     */
    public Person getPersonFromId(String id) {
        for (Person person: persons) {
            if (person.getPersonID().equals(id)) {
                return person;
            }
        }
        return null;
    }

    /**
     * Gets a list of events corresponding to a person
     * @param personId Person id of the individual we want the events of
     * @return ArrayList of Event objects representing the events
     */
    public List<Event> getEventsFromPersonId(String personId) {
        ArrayList<Event> personEvents = new ArrayList<>();
        ArrayList<Event> orderedEvents = new ArrayList<>();
        Event death = null;
        Event birth = null;
        boolean inserted = false;
        for (Event event : events) {
            if (event.getPerson().equals(personId)) {
                personEvents.add(event);
            }
        }
        for (Event event: personEvents) {
            if (event.getEventType().toLowerCase().equals("birth")) {
                birth = event;
            }
            else if (event.getEventType().toLowerCase().equals("death")) {
                death = event;
            }
            else if (event.getYear() != null) {
                for (int i = 0; i < orderedEvents.size(); i++) {
                    if (event.getYear() == orderedEvents.get(i).getYear()) {
                        if (orderedEvents.get(i).getEventType().compareToIgnoreCase(event.getEventType()) < 0) {
                            orderedEvents.add(i + 1, event);
                        }
                        break;
                    }
                    else if (event.getYear() > orderedEvents.get(i).getYear()) {
                        orderedEvents.add(i + 1, event);
                        inserted = true;
                        break;
                    }
                }
                if (!inserted) {
                    orderedEvents.add(0, event);
                }
                inserted = false;
            }
            else {
                for (int i = 0; i < orderedEvents.size(); i++) {
                    if (orderedEvents.get(i).getYear() == null) {
                        if (orderedEvents.get(i).getEventType().compareToIgnoreCase(event.getEventType()) < 0) {
                            orderedEvents.add(i + 1, event);
                        }
                    }
                }
            }
        }
        if (death != null) {
            orderedEvents.add(death);
        }
        if (birth != null) {
            orderedEvents.add(0, birth);
        }
        return personEvents;
    }

    /**
     * Gets an event given an event id
     * @param id id of the desired event
     * @return Event object with the given event id
     */
    public Event getEventFromEventId(String id) {
        for (Event event : events) {
            if (event.getEventID().equals(id)) {
                return event;
            }
        }
        return null;
    }

    /**
     * Sets the color of the map markers for a given event
     * @param eventType string representation of an event type
     * @param color A color from the BitmapDescriptorFactory
     */
    public void setEventTypeColor(String eventType, Float color) {

    }

    /**
     * Get a marker color given the event type
     * @param eventType String representing the event type
     * @return Float representing a color from the BitmapDescriptorFactory
     */
    public Float getEventTypeColor(String eventType) {
        if (!eventTypeColors.containsKey(eventType.toLowerCase())) {
            eventTypeColors.put(eventType.toLowerCase(), eventColors[eventColorCounter]);
            eventColorCounter++;
            if (eventColorCounter >= 10) {
                eventColorCounter = 0;
            }
        }
        return eventTypeColors.get(eventType.toLowerCase());
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
     * Updates the model with the current event types
     */
    public void updateEventTypes() {
        for (Event event : events) {
            eventTypes.add(event.getEventType());
        }
    }

    public boolean isFiltered(Event event) {
        Person person = getPersonFromEvent(event);
        if (maleFiltered && person.getGender().equals("m")) {
            return true;
        }
        if (femaleFiltered && person.getGender().equals("f")) {
            return true;
        }
        for (String eventType : eventTypes) {
            if (!eventsFiltered.containsKey(eventType)) {
                eventsFiltered.put(eventType, false);
            } else if (event.getEventType().equals(eventType) && eventsFiltered.get(eventType)) {
                return true;
            }
        }
        if (fatherSideFiltered && fathersSide.contains(person)) {
            return true;
        }
        if (motherSideFiltered && mothersSide.contains(person)) {
            return true;
        }
        return false;
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
