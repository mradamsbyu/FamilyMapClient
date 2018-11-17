package map.family.familymapclient.proxy;

import org.junit.BeforeClass;
import org.junit.Test;

import map.family.familymapclient.client.HttpClient;
import map.family.familymapclient.model.Model;
import map.family.familymapclient.request.LoginRequest;
import map.family.familymapclient.request.RegisterRequest;
import map.family.familymapclient.response.LoginResponse;
import map.family.familymapclient.response.RegisterResponse;

import static org.junit.Assert.*;

public class ProxyTests {

    @BeforeClass
    public static void setUP(){
        HttpClient.getInstance().setServer("10.37.30.164","8080");
    }

    @Test
    public void register() {
        HttpClient.getInstance().postRequest(null, "/clear");

        RegisterRequest registerRequest = new RegisterRequest("user", "pass", "email", "first", "last", "m");
        RegisterResponse registerResponse = RegisterProxy.getInstance().register(registerRequest);
        assertEquals("user", registerResponse.getUserName());
        assertNotNull(registerResponse.getPersonID());
        assertNull(registerResponse.getErrorMessage());
        assertEquals(Model.getInstance().getUserAuthToken().getAuthToken(), registerResponse.getAuthToken());
    }

    @Test
    public void registerFail() {
        HttpClient.getInstance().postRequest(null, "/clear");

        RegisterRequest registerRequest = new RegisterRequest("user", "pass", "email", "first", "last", "m");
        RegisterResponse registerResponse = RegisterProxy.getInstance().register(registerRequest);
        assertEquals("user", registerResponse.getUserName());
        assertNotNull(registerResponse.getPersonID());
        assertNull(registerResponse.getErrorMessage());
        assertEquals(Model.getInstance().getUserAuthToken().getAuthToken(), registerResponse.getAuthToken());
    }

    @Test
    public void login() {
        HttpClient.getInstance().postRequest(null, "/clear");

        RegisterRequest registerRequest = new RegisterRequest("my_username", "my_password", "email", "first", "last", "m");
        RegisterProxy.getInstance().register(registerRequest);
        registerRequest = new RegisterRequest("username2", "password2", "email", "first", "last", "m");
        RegisterProxy.getInstance().register(registerRequest);

        LoginRequest loginRequest = new LoginRequest("my_username", "my_password");
        LoginResponse loginResponse = LoginProxy.getInstance().login(loginRequest);
        assertEquals("my_username", loginResponse.getUserName());
        assertNotNull(loginResponse.getPersonID());
        assertNull(loginResponse.getMessage());
        assertEquals(Model.getInstance().getUserAuthToken().getAuthToken(), loginResponse.getAuthToken());

        LoginRequest loginRequest2 = new LoginRequest("username2", "password2");
        LoginResponse loginResponse2 = LoginProxy.getInstance().login(loginRequest2);
        assertEquals("username2", loginResponse2.getUserName());
        assertNotNull(loginResponse2.getPersonID());
        assertEquals(Model.getInstance().getUserAuthToken().getAuthToken(), loginResponse2.getAuthToken());
        assertNotEquals(Model.getInstance().getUserAuthToken().getAuthToken(), loginResponse.getAuthToken());
        assertNull(loginResponse2.getMessage());
    }

    @Test
    public void loginFail() {
        HttpClient.getInstance().postRequest(null, "/clear");

        //Login without registering
        LoginRequest loginRequest = new LoginRequest("username2", "password2");
        LoginResponse loginResponse = LoginProxy.getInstance().login(loginRequest);
        assertNull(loginResponse.getPersonID());
        assertNull(loginResponse.getAuthToken());
        assertNull(loginResponse.getUserName());
        assertNull(Model.getInstance().getUserAuthToken());
        assertEquals("Invalid username and/or password", loginResponse.getMessage());

        //login with null data
        loginRequest = new LoginRequest(null, null);
        loginResponse = LoginProxy.getInstance().login(loginRequest);
        assertNull(loginResponse.getPersonID());
        assertNull(loginResponse.getAuthToken());
        assertNull(loginResponse.getUserName());
        assertNull(Model.getInstance().getUserAuthToken());
        assertEquals("Request property missing or has invalid value", loginResponse.getMessage());
    }

    @Test
    public void getPersons() {
    }

    @Test
    public void getPersonsFail() {
    }

    @Test
    public void getEvents() {
    }

    @Test
    public void getEventsFail() {
    }
}