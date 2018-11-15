package map.server.request;

import java.util.ArrayList;

import map.server.model.Event;
import map.server.model.Person;
import map.server.model.User;

/**
 * Holds the request data for the load service
 */
public class LoadRequest {
    /**
     * Array of User objects
     */
    private ArrayList<User> users = null;

    /**
     * Array of Person objects
     */
    private ArrayList<Person> persons = null;

    /**
     * Array of Event objects
     */
    private ArrayList<Event> events = null;

    /**
     * Constructor that initializes request data
     * @param users array of users to load
     * @param persons array of persons to load
     * @param events array of events to load
     */
    public LoadRequest(ArrayList<User> users, ArrayList<Person>  persons, ArrayList<Event> events) {
        this.users = users;
        this.persons = persons;
        this.events = events;
    }

    /**
     * Constructor that does not initialize request data
     */
    public LoadRequest() {
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public ArrayList<Person> getPersons() {
        return persons;
    }

    public void setPersons(ArrayList<Person>  persons) {
        this.persons = persons;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }
}
