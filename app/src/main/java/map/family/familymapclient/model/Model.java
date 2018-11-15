package map.family.familymapclient.model;

import map.family.familymapclient.memberobjects.Auth;
import map.family.familymapclient.memberobjects.Person;

public class Model {
    /**
     * Singleton instance of the Model class
     */
    private static Model instance = null;

    private Auth userAuthToken;

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

    public void setUserAuthToken(Auth userAuthToken) {
        this.userAuthToken = userAuthToken;
    }
}
