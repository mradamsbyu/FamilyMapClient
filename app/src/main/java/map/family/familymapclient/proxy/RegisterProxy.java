package map.family.familymapclient.proxy;

import com.google.gson.Gson;

import map.family.familymapclient.client.HttpClient;
import map.family.familymapclient.memberobjects.Auth;
import map.family.familymapclient.model.Model;
import map.family.familymapclient.request.RegisterRequest;
import map.family.familymapclient.response.RegisterResponse;

/**
 * Created by mradams on 11/15/18.
 */

public class RegisterProxy {
    /**
     * Singleton instance of the register proxy
     */
    private static RegisterProxy instance = null;

    /**
     * Singleton function to get a unique instance of the register proxy
     * @return Instance of the register proxy
     */
    public static RegisterProxy getInstance() {
        if (instance == null) {
            instance = new RegisterProxy();
        }
        return instance;
    }

    /**
     * Proxy function for processing a register request
     * @param request RegisterRequest object with request data
     * @return RegisterResponse function containing response from server
     */
    public RegisterResponse register(RegisterRequest request) {
        Gson gson = new Gson();
        String jsonRequest = gson.toJson(request);
        String jsonResponse = HttpClient.getInstance().postRequest(jsonRequest, "/register");
        RegisterResponse response = gson.fromJson(jsonResponse, RegisterResponse.class);
        if (response.getAuthToken() != null) {
            Model.getInstance().setUserAuthToken(new Auth(response.getUserName(), response.getAuthToken()));
        }
        return response;
    }
}
