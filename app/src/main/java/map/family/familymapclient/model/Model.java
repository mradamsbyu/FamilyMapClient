package map.family.familymapclient.model;

import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import map.family.familymapclient.memberobjects.Auth;
import map.family.familymapclient.memberobjects.Event;
import map.family.familymapclient.memberobjects.Person;

import static android.graphics.Color.BLUE;
import static android.graphics.Color.GREEN;
import static android.graphics.Color.MAGENTA;
import static android.graphics.Color.RED;
import static android.graphics.Color.YELLOW;
import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_HYBRID;
import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_NORMAL;
import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_SATELLITE;
import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_TERRAIN;
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
     * Set of relatives of the user
     */
    private HashSet<Person> persons;
    /**
     * Set of life events of the relatives of the user
     */
    private HashSet<Event> events;
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
    private HashMap<String, Boolean> eventsFiltered;
    /**
     * Is true if the fathers side is filtered
     */
    private boolean fatherSideFiltered = false;
    /**
     * Is true if the mothers side is filtered
     */
    private boolean motherSideFiltered = false;
    /**
     * Is true if males are filtered
     */
    private boolean maleFiltered = false;
    /**
     * Is true if females are filtered
     */
    private boolean femaleFiltered = false;
    /**
     * Color of the spouse lines
     */
    private int spouseLineColor;
    /**
     * Color of the family tree lines
     */
    private int familyTreeLineColor;
    /**
     * Color of the life story lines
     */
    private int lifeStoryLineColor;
    /**
     * Is true if spouse lines are enabled in settings
     */
    private boolean spouseLinesEnabled = true;
    /**
     * Is true if family tree lines are enabled in settings
     */
    private boolean familyTreeLinesEnabled = true;
    /**
     * Is true if life story lines are enabled in settings
     */
    private boolean lifeStoryLinesEnabled = true;
    /**
     *  Represents the type of map that is displayed (Normal, Satellite, Hybrid, or Terrain)
     */
    private int mapType;

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
        persons = new HashSet<>();
        events = new HashSet<>();
        eventTypes = new HashSet<>();
        mothersSide = new HashSet<>();
        fathersSide = new HashSet<>();
        spouseLineColor = BLUE;
        familyTreeLineColor = YELLOW;
        lifeStoryLineColor = RED;
        mapType = MAP_TYPE_NORMAL;
        currentEvent = null;
        userAuthToken = null;
        currentPerson = null;
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
     * Updates lists of maternal and paternal ancestors
      */
    private void updateParentSideLists() {
        Person user;
        Iterator<Person> it = persons.iterator();
        Person somePerson = it.next();
        List<Person> children;
        do {
            children = getChildren(somePerson.getPersonID(), somePerson.getGender().equals("m"));
            if (children.size() > 0) {
                somePerson = children.get(0);
            }
        } while (children.size() > 0);
        user = somePerson;
        if (user != null) {
            Person mother = getPersonFromId(user.getMother());
            Person father = getPersonFromId(user.getFather());
            mothersSide.add(mother);
            fathersSide.add(father);
            addMaternalAncestors(mother);
            addPaternalAncestors(father);
        }
    }

    /**
     * Recursively adds maternal ancestors to the set mothersSide
     * @param parent parent whose parents are to be added to the list if they exist
     */
    private void addMaternalAncestors(Person parent) {
        if (parent.getFather() != null) {
            Person father = getPersonFromId(parent.getFather());
            mothersSide.add(father);
            addMaternalAncestors(father);
        }
        if (parent.getMother() != null) {
            Person mother = getPersonFromId(parent.getMother());
            mothersSide.add(mother);
            addMaternalAncestors(mother);
        }
    }

    /**
     * Recursively adds paternal ancestors to the set fathersSide
     * @param parent parent whose parents are to be added to the list if they exist
     */
    private void addPaternalAncestors(Person parent) {
        if (parent.getFather() != null) {
            Person father = getPersonFromId(parent.getFather());
            fathersSide.add(father);
            addPaternalAncestors(father);
        }
        if (parent.getMother() != null) {
            Person mother = getPersonFromId(parent.getMother());
            fathersSide.add(mother);
            addPaternalAncestors(mother);
        }
    }

    /**
     * Returns the person object associated with a person id
     * @param id person id of the person you are looking for
     * @return Person object associated with the id, or null if that id does not exist in the database
     */
    public Person getPersonFromId(String id) {
        if (id == null) {
            return null;
        }
        for (Person person: persons) {
            if (person.getPersonID().equals(id)) {
                return person;
            }
        }
        return null;
    }

    /**
     * Gets a list of events corresponding to a person, ordered chronologically
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
                    if (event.getYear().equals(orderedEvents.get(i).getYear())) {
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
        return orderedEvents;
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
     * Get a marker color given the event type
     * @param eventType String representing the event type
     * @return Float representing a color from the BitmapDescriptorFactory
     */
    public Float getEventTypeColor(String eventType) {
        if (!eventTypeColors.containsKey(eventType.toLowerCase())) {
            eventTypeColors.put(eventType.toLowerCase(), eventColors[eventColorCounter]);
            eventColorCounter++;
            if (eventColorCounter >= eventColors.length) {
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
            eventTypes.add(event.getEventType().toLowerCase());
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
            } else if (event.getEventType().toLowerCase().equals(eventType) && eventsFiltered.get(eventType)) {
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
     * Sets the given filter type to be filtered or not
     * @param eventType Filter type to be set
     * @param filterState State to be set to (true or false)
     */
    public void setFilterItem(String eventType, boolean filterState) {
        for (String type : eventTypes) {
            if (type.equals(eventType.toLowerCase())) {
                eventsFiltered.put(eventType.toLowerCase(), filterState);
            }
        }
    }

    /**
     * Indicates whether a certain event type is currently filtered
     * @param eventType The type of event that is to be checked
     * @return Boolean indicating if the event type is filtered.  If event type does not exist, false is returned.
     */
    public boolean typeIsFiltered(String eventType) {
        if (eventsFiltered.containsKey(eventType.toLowerCase())) {
            return eventsFiltered.get(eventType.toLowerCase());
        }
        else {
            return false;
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

    /**
     * Gets an integer representation of a color given its string representation
     * @param stringColor String representation of a colr
     * @return Integer representation of a color
     */
    private int getColorFromString(String stringColor) {
        int color = RED;
        if (stringColor.equals("Red")) {
            color = RED;
        }
        else if (stringColor.equals("Yellow")) {
            color = YELLOW;
        }
        else if (stringColor.equals("Blue")) {
            color = BLUE;
        }
        else if (stringColor.equals("Green")) {
            color = GREEN;
        }
        else if (stringColor.equals("Pink")) {
            color = MAGENTA;
        }
        return color;
    }

    /**
     * Gets the position of the spinner in the settings that corresponds to the given color
     * @param color Color that we want the spinner position of
     * @return Spinner position of that color
     */
    public int getSpinnerPositionFromColor(int color) {
        if (color == RED) {
            return 0;
        }
        else if (color == YELLOW) {
            return 1;
        }
        else if (color == BLUE) {
            return 2;
        }
        else if (color == GREEN) {
            return 3;
        }
        else if (color == MAGENTA) {
            return 4;
        }
        return 0;
    }

    /**
     * Gets an integer representation of a map type given its string representation
     * @param stringMapType String representation of a map type
     * @return Integer representation of that map type
     */
    private int getMapTypeFromString(String stringMapType) {
        int mapType = MAP_TYPE_NORMAL;
        if (stringMapType.equals("Normal")) {
            mapType = MAP_TYPE_NORMAL;
        }
        else if (stringMapType.equals("Hybrid")) {
            mapType = MAP_TYPE_HYBRID;
        }
        else if (stringMapType.equals("Satellite")) {
            mapType = MAP_TYPE_SATELLITE;
        }
        else if (stringMapType.equals("Terrain")) {
            mapType = MAP_TYPE_TERRAIN;
        }
        return mapType;
    }

    /**
     * Gets a spinner position of the map type spinner given the map type
     * @param mapType Map type that we want the position of
     * @return Integer representation of the map type
     */
    public int getSpinnerPositionFromMapType(int mapType) {
        if (mapType == MAP_TYPE_NORMAL) {
            return 0;
        }
        else if (mapType == MAP_TYPE_HYBRID) {
            return 1;
        }
        else if (mapType == MAP_TYPE_SATELLITE) {
            return 2;
        }
        else if (mapType == MAP_TYPE_TERRAIN) {
            return 3;
        }
        return 0;
    }


    public Auth getUserAuthToken() {
        return userAuthToken;
    }

    public void setUserAuthToken(Auth userAuthToken) {
        this.userAuthToken = userAuthToken;
    }

    public HashSet<Person> getPersons() {
        return persons;
    }

    public void setPersons(HashSet<Person> persons) {
        this.persons = persons;
        updateParentSideLists();
    }

    public void addPerson(Person person) {
        this.persons.add(person);
    }

    public HashSet<Event> getEvents() {
        return events;
    }

    public void setEvents(HashSet<Event> events) {
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

    public boolean isSpouseLinesEnabled() {
        return spouseLinesEnabled;
    }

    public void setSpouseLinesEnabled(boolean spouseLinesEnabled) {
        this.spouseLinesEnabled = spouseLinesEnabled;
    }

    public boolean isFamilyTreeLinesEnabled() {
        return familyTreeLinesEnabled;
    }

    public void setFamilyTreeLinesEnabled(boolean familyTreeLinesEnabled) {
        this.familyTreeLinesEnabled = familyTreeLinesEnabled;
    }

    public boolean isLifeStoryLinesEnabled() {
        return lifeStoryLinesEnabled;
    }

    public void setLifeStoryLinesEnabled(boolean lifeStoryLinesEnabled) {
        this.lifeStoryLinesEnabled = lifeStoryLinesEnabled;
    }

    public int getSpouseLineColor() {
        return spouseLineColor;
    }

    public void setSpouseLineColor(int spouseLineColor) {
        this.spouseLineColor = spouseLineColor;
    }

    public void setSpouseLineColor(String spouseLineColor) {
        this.spouseLineColor = getColorFromString(spouseLineColor);
    }

    public int getFamilyTreeLineColor() {
        return familyTreeLineColor;
    }

    public void setFamilyTreeLineColor(int familyTreeLineColor) {
        this.familyTreeLineColor = familyTreeLineColor;
    }

    public void setFamilyTreeLineColor(String familyTreeLineColor) {
        this.familyTreeLineColor = getColorFromString(familyTreeLineColor);
    }

    public int getLifeStoryLineColor() {
        return lifeStoryLineColor;
    }

    public void setLifeStoryLineColor(String lifeStoryLineColor) {
        this.lifeStoryLineColor = getColorFromString(lifeStoryLineColor);
    }

    public void setLifeStoryLineColor(int lifeStoryLineColor) {
        this.lifeStoryLineColor = lifeStoryLineColor;
    }

    public boolean isFatherSideFiltered() {
        return fatherSideFiltered;
    }

    public void setFatherSideFiltered(boolean fatherSideFiltered) {
        this.fatherSideFiltered = fatherSideFiltered;
    }

    public boolean isMotherSideFiltered() {
        return motherSideFiltered;
    }

    public void setMotherSideFiltered(boolean motherSideFiltered) {
        this.motherSideFiltered = motherSideFiltered;
    }

    public boolean isMaleFiltered() {
        return maleFiltered;
    }

    public void setMaleFiltered(boolean maleFiltered) {
        this.maleFiltered = maleFiltered;
    }

    public boolean isFemaleFiltered() {
        return femaleFiltered;
    }

    public void setFemaleFiltered(boolean femaleFiltered) {
        this.femaleFiltered = femaleFiltered;
    }

    public int getMapType() {
        return mapType;
    }

    public void setMapType(int mapType) {
        this.mapType = mapType;
    }

    public void setMapType(String mapType) {
        this.mapType = getMapTypeFromString(mapType);
    }

    public HashSet<String> getEventTypes() {
        return eventTypes;
    }

    public void setEventTypes(HashSet<String> eventTypes) {
        this.eventTypes = eventTypes;
    }
}
