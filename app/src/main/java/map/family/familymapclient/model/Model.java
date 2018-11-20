package map.family.familymapclient.model;

import java.util.ArrayList;

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
