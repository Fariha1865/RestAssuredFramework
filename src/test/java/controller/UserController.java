package controller;

import io.restassured.response.Response;
import org.apache.commons.configuration.ConfigurationException;
import restUtils.RestUtils;
import setup.Models;
import utils.JsonUtils;
import utils.Utils;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class UserController {
    Properties prop;
    public UserController(Properties prop){
        this.prop=prop;
    }
    public Response userLogin(Models models) throws IOException {
        String env = System.getProperty("env") == null ? "dev" : System.getProperty("env");
        Map<String, String> data = JsonUtils.getJsonDataAsMap("dmoney/"+env+"/DmoneyAPIData.json");
        String endPoint = data.get("createEndpoint")+"/user/login";
        //String endPoint = "http://dmoney.roadtocareer.net/user/login";
        Response response = RestUtils.performLogin(endPoint, models);
        return response;
    }
    public Response searchUser(String userId) throws IOException {
        String endPoint=  prop.getProperty("baseURI")+"/user/search/id/"+userId;
        Response response = RestUtils.performSearch(endPoint, Utils.getHeaderValues(prop));
        return response;
    }
    public Response createUser(Models models) throws IOException {

        String endPoint=  prop.getProperty("baseURI")+"/user/create";
        Response response = RestUtils.performPost(endPoint, models,Utils.getHeaderValues(prop));
        return response;

    }
    public Response deleteUser(String userId) throws IOException {
        String endPoint=  prop.getProperty("baseURI")+"/user/delete/"+userId;
        Response response = RestUtils.performDelete(endPoint,Utils.getHeaderValues(prop));
        return response;
    }
}
