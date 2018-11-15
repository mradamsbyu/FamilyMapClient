package map.server.model;

/**
 * Event class objects represent an event in the life of a person
 */
public class Event {
    /**
     * String representing a unique event identifier
     */
    private String eventID;

    /**
     * Username of the descendant of the person
     */
    private String descendant;

    /**
     * Id of the person that this event belongs to
     */
    private String personID;

    /**
     * Latitude location of where this event occurred
     */
    private Double latitude;

    /**
     * Longitude location of where this event occurred
     */
    private Double longitude;

    /**
     * Country where this event occurred
     */
    private String country;

    /**
     * City where this event occurred
     */
    private String city;

    /**
     * Type of event that occurred (birth, baptism, marriage, or death)
     */
    private String eventType;

    /**
     * Year that the event occurred
     */
    private Integer year;

    /**
     * The constructor will fill the data of the Event object
     */
    public Event() {

    }

    /**
     * Checks if any of the parameters in the Event object are null
     * @return boolean indicating whether any of the parameters are null
     */
    public boolean hasNullData() {
        if (eventID == null || descendant == null || personID == null || latitude == null ||
                longitude == null || country == null || city == null || eventType == null) {
            return true;
        }
        return false;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getDescendant() {
        return descendant;
    }

    public void setDescendant(String descendant) {
        this.descendant = descendant;
    }

    public String getPerson() {
        return personID;
    }

    public void setPerson(String person) {
        this.personID = person;
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
}
