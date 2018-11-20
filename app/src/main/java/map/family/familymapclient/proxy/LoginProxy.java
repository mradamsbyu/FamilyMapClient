package map.family.familymapclient.proxy;

import com.google.gson.Gson;

import map.family.familymapclient.client.HttpClient;
import map.family.familymapclient.memberobjects.Auth;
import map.family.familymapclient.model.Model;
import map.family.familymapclient.request.LoginRequest;
import map.family.familymapclient.response.LoginResponse;

/**
 * Created by mradams on 11/15/18.
 */

public class LoginProxy {
    /**
     * Singleton instance of the login proxy
     */
    private static LoginProxy instance = null;

    /**
     * Singleton function to get a unique instance of the login proxy
     * @return Instance of the login proxy
     */
    public static LoginProxy getInstance() {
        if (instance == null) {
            instance = new LoginProxy();
        }
        return instance;
    }

    /**
     * Proxy function for processing a login request
     * @param request LoginRequest object with login data
     * @return LoginResponse function containing response from server
     */
    public LoginResponse login(LoginRequest request) {
        Gson gson = new Gson();
        String jsonRequest = gson.toJson(request);
        String jsonResponse = HttpClient.getInstance().postRequest(jsonRequest, "/user/login");
        if (jsonResponse.startsWith("{")) {
            LoginResponse response = gson.fromJson(jsonResponse, LoginResponse.class);
            if (response.getAuthToken() != null) {
                Model.getInstance().setUserAuthToken(new Auth(response.getUserName(), response.getAuthToken()));
            }
            return response;
        }
        else {
            System.out.println(jsonResponse);
            return null;
        }
    }
}
