package map.family.familymapclient.proxy;

import com.google.gson.Gson;

import map.family.familymapclient.client.HttpClient;
import map.family.familymapclient.response.EventResponse;

/**
 * Created by mradams on 11/15/18.
 */

public class EventProxy {
    /**
     * Singleton instance of the event proxy
     */
    private static EventProxy instance = null;

    /**
     * Singleton function to get a unique instance of the event proxy
     * @return Instance of the event proxy
     */
    public static EventProxy getInstance() {
        if (instance == null) {
            instance = new EventProxy();
        }
        return instance;
    }

    /**
     * Proxy function for processing an event request
     * @return EventResponse object with response data from server
     */
    public EventResponse getEvents() {
        Gson gson = new Gson();
        EventResponse response;
        String jsonResponse = HttpClient.getInstance().getRequest("/event");
        if (jsonResponse.equals("IOException occurred") || jsonResponse.equals("ERROR: Bad Request")) {
            response = new EventResponse();
            response.setErrorMessage(jsonResponse);
        }
        else {
            response = gson.fromJson(jsonResponse, EventResponse.class);
        }
        return response;
    }
}
