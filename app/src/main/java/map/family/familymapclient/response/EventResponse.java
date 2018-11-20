package map.family.familymapclient.response;


import java.util.ArrayList;

import map.family.familymapclient.memberobjects.Event;

/**
 * Holds response data for the event service
 */
public class EventResponse {
    /**
     * Name of user account this event belongs to (non-empty string)
     */
    private String descendant = null;

    /**
     * Event's unique ID (non-empty string)
     */
    private String eventID = null;

    /**
     * ID of the person this event belongs to (non-empty string)
     */
    private String personID = null;

    /**
     * Latitude of the event's location (number)
     */
    private Double latitude = null;

    /**
     * Longitude of the event's location (number)
     */
    private Double longitude = null;

    /**
     * Name of country where event occurred (non-empty String)
     */
    private String country = null;

    /**
     * Name of city where event occurred (non-empty string)
     */
    private String city = null;

    /**
     * Type of event ("birth", "baptism", etc.) (non-empty string)
     */
    private String eventType = null;

    /**
     * Year the event occurred (integer)
     */
    private Integer year = null;

    /**
     * Error response message
     */
    private String message = null;

    /**
     * Array to hold multiple event data
     */
    private ArrayList<Event> events = null;

    /**
     * Constructor with data passed in
     * @param descendant Name of user account this event belongs to (non-empty string)
     * @param eventID Event's unique ID (non-empty string)
     * @param personID ID of the person this event belongs to (non-empty string)
     * @param latitude Latitude of the event's location (number)
     * @param longitude  Longitude of the event's location (number)
     * @param country Name of country where event occurred (non-empty String)
     * @param city Name of city where event occurred (non-empty string)
     * @param eventType Type of event ("birth," "baptism," etc.) (non-empty string)
     * @param year Year the event occurred (integer)
     */
    public EventResponse(String descendant, String eventID, String personID, Double latitude,
                         Double longitude, String country, String city, String eventType, Integer year) {
        this.descendant = descendant;
        this.eventID = eventID;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
    }

    /**
     * Generic constructor that does not initialize data
     */
    public EventResponse() {
    }

    /**
     * Will initialize data based on the event that is passed in
     * @param event Event object that contains the data to be passed into the response object
     */
    public void setAll(Event event) {
        this.descendant = event.getDescendant();
        this.eventID = event.getEventID();
        this.personID = event.getPerson();
        this.latitude = event.getLatitude();
        this.longitude = event.getLongitude();
        this.country = event.getCountry();
        this.city = event.getCity();
        this.eventType = event.getEventType();
        this.year = event.getYear();
    }

    public String getDescendant() {
        return descendant;
    }

    public void setDescendant(String descendant) {
        this.descendant = descendant;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }

    public String getMessage() {
        return message;
    }

    /**
     * Sets an error message if something went wrong in an event service
     * @param message String describing the error that occured
     */
    public void setErrorMessage(String message) {
        this.message = message;
    }
}
