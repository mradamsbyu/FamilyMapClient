package map.server.model;

/**
 * A person object represents a person that is in the map
 */
public class Person {
    /**
     * Unique identifier for the person object
     */
    private String personID;

    /**
     * Username of the descendant of the person
     */
    private String descendant;

    /**
     * String representing the first name of the person
     */
    private String firstName;

    /**
     * String representing the last name of the person
     */
    private String lastName;

    /**
     * String representing the gender of the person.  Should be 'f' or 'm'
     */
    private String gender;

    /**
     * Person ID of the father of the person.  May be null.
     */
    private String father;

    /**
     * Person ID of the mother of the person.  May be null.
     */
    private String mother;

    /**
     * Person ID of the spouse of the person.  May be null.
     */
    private String spouse;

    /**
     * Person constructor will initialize all of the data of the person object including a unique
     * personID
     */
    public Person() {
    }

    /**
     * Checks if any of the parameters in the Person object are null that shouldn't be (only the father,
     * mother, and spouse parameters may be null.)
     * @return boolean indicating whether any of the parameters are null that shouldn't be
     */
    public boolean hasInvalidNullData() {
        if (personID == null || descendant == null || firstName == null || lastName == null ||
                lastName == null || gender == null) {
            return true;
        }
        return false;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getDescendant() {
        return descendant;
    }

    public void setDescendant(String descendant) {
        this.descendant = descendant;
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
}
