package map.family.familymapclient.proxy;

import com.google.gson.Gson;

import map.family.familymapclient.client.HttpClient;
import map.family.familymapclient.response.PersonResponse;

/**
 * Created by mradams on 11/15/18.
 */

public class PersonProxy {
    /**
     * Singleton instance of the person proxy
     */
    private static PersonProxy instance = null;

    /**
     * Singleton function to get a unique instance of the person proxy
     * @return Instance of the person proxy
     */
    public static PersonProxy getInstance() {
        if (instance == null) {
            instance = new PersonProxy();
        }
        return instance;
    }

    /**
     * Proxy function for processing a person request
     * @return PersonResponse object with response data from server
     */
    public PersonResponse getPersons() {
        Gson gson = new Gson();
        String jsonResponse = HttpClient.getInstance().getRequest("/person");
        PersonResponse response = gson.fromJson(jsonResponse, PersonResponse.class);
        return response;
    }
}
