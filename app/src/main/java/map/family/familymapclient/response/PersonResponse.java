package map.family.familymapclient.response;

import java.util.ArrayList;
import map.family.familymapclient.memberobjects.Person;

/**
 * Holds response data for the person service
 */
public class PersonResponse {
    /**
     * Name of user account this person belongs to
     */
    private String descendant = null;
    /**
     * Person's unique ID
     */
    private String personID = null;
    /**
     * Person's first name
     */
    private String firstName = null;
    /**
     * Person's last name
     */
    private String lastName = null;
    /**
     * Person's gender ("m" or "f")
     */
    private String gender = null;
    /**
     *  ID of person's father [OPTIONAL, can be missing]
     */
    private String father = null;
    /**
     * ID of person's mother  [OPTIONAL, can be missing]
     */
    private String mother = null;
    /**
     * ID of person's spouse [OPTIONAL, can be missing]
     */
    private String spouse = null;
    /**
     * Array to hold Person objects
     */
    private ArrayList<Person> persons = null;
    /**
     * Response message if error occurs
     */
    private String message = null;

    /**
     * Constructs a person response with the given data (for requesting a single person)
     * @param descendant Name of user account this person belongs to
     * @param personID Person's unique ID
     * @param firstName Person's first name
     * @param lastName Person's last name
     * @param gender Person's gender ("m" or "f")
     * @param father ID of person's father [OPTIONAL, can be null]
     * @param mother ID of person's mother  [OPTIONAL, can be null]
     * @param spouse ID of person's spouse [OPTIONAL, can be null]
     */
    public PersonResponse(String descendant, String personID, String firstName, String lastName,
                          String gender, String father, String mother, String spouse) {
        this.descendant = descendant;
        this.personID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.father = father;
        this.mother = mother;
        this.spouse = spouse;
    }

    /**
     * Sets all the fields from information extracted from a Person object
     * @param person Object from which to extract data to be put into the response
     */
    public void setAll(Person person) {
        this.descendant = person.getDescendant();
        this.personID = person.getPersonID();
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.gender = person.getGender();
        this.father = person.getFather();
        this.mother = person.getMother();
        this.spouse = person.getSpouse();
    }

    /**
     * Constructs a person response for requesting multiple people
     * @param data Array of person objects to be returned
     */
    public PersonResponse(ArrayList<Person> data) {
        this.persons = data;
    }

    /**
     * Generic constructor that does not initialize data
     */
    public PersonResponse() {
    }

    /**
     * Sets the error message if something goes wrong in a person service
     * @param errorMessage String describing the error that occurred
     */
    public void setError(String errorMessage) {
        this.message = errorMessage;
    }

    public String getDescendant() {
        return descendant;
    }

    public void setDescendant(String descendant) {
        this.descendant = descendant;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFather() {
        return father;
    }

    public void setFather(String father) {
        this.father = father;
    }

    public String getMother() {
        return mother;
    }

    public void setMother(String mother) {
        this.mother = mother;
    }

    public String getSpouse() {
        return spouse;
    }

    public void setSpouse(String spouse) {
        this.spouse = spouse;
    }

    public ArrayList<Person> getPersons() {
        return persons;
    }

    public void setPersons(ArrayList<Person> data) {
        this.persons = data;
    }

    public String getError() {
        return message;
    }
}
