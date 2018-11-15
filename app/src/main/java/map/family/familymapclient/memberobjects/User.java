package map.family.familymapclient.memberobjects;

import java.util.UUID;

import map.family.familymapclient.request.RegisterRequest;

/**
 * The user class represents a Family Map user
 */
public class User {
    /**
     * String representing the username of the User.  The primary element of this class
     */
    private String userName;

    /**
     * String representing the password of the User
     */
    private String password;

    /**
     * String representing the email of the User
     */
    private String email;

    /**
     * String representing the first name of the User
     */
    private String firstName;

    /**
     * String representing the last name of the User
     */
    private String lastName;

    /**
     * String representing the gender of the User.  Should be 'f' or 'm'
     */
    private String gender;

    /**
     * String representing a unique id for the User
     */
    private String personID;

    /**
     * The constructor should set the data elements and generate an id for this person
     */
    public User() {
    }

    /**
     * Fills all of the data of the user object from the data of a register request object.
     * Will also generate a person id for the user.
     * @param request The register request object, filled with user data.
     */
    public void fillFromRegisterRequest(RegisterRequest request) {
        this.userName = request.getUserName();
        this.password = request.getPassword();
        this.email = request.getEmail();
        this.firstName = request.getFirstName();
        this.lastName = request.getLastName();
        this.gender = request.getGender();
        this.personID = UUID.randomUUID().toString();
    }

    /**
     * Checks if any of the parameters in the User object are null
     * @return boolean indicating whether any of the parameters are null
     */
    public boolean hasNullData() {
        if (userName == null || password == null || email == null || firstName == null ||
                lastName == null || gender == null || personID == null) {
            return true;
        }
        return false;
    }

    /**
     * Checks if any of the parameters in the User object are empty strings
     * @return boolean indicating whether any of the parameters are empty strings
     */
    public boolean hasEmptyData() {
        if (userName.equals("") || password.equals("") || email.equals("") ||
                firstName.equals("") || lastName.equals("") || gender.equals("") || personID.equals("")) {
            return true;
        }
        return false;
    }

    public String getUsername() {
        return userName;
    }

    public void setUsername(String username) {
        this.userName = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }
}
