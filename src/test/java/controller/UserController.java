package controller;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.configuration.ConfigurationException;
import restUtils.RestUtils;
import setup.UserModel;
import utils.JsonUtils;
import utils.Utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class UserController {
    Properties prop;
    public UserController(Properties prop){
        this.prop=prop;
    }
    public Response userLogin(UserModel userModel) throws ConfigurationException, IOException {
        String env = System.getProperty("env") == null ? "dev" : System.getProperty("env");
        Map<String, String> data = JsonUtils.getJsonDataAsMap("dmoney/"+env+"/DmoneyAPIData.json");
        String endPoint = data.get("createEndpoint")+"/user/login";
        //String endPoint = "http://dmoney.roadtocareer.net/user/login";
        Response response = RestUtils.performLogin(endPoint,userModel);
        return response;
    }
    public Response searchUser(String userId) throws IOException {
        Map<String, String> data = JsonUtils.getJsonDataAsMap("dmoney/dev/DmoneyAPIData.json");
        String endPoint=  data.get("createEndpoint")+"/user/search/id/"+userId;
        Response response = RestUtils.performSearch(endPoint, Utils.getHeaderValues(prop));
        return response;
    }
    public Response createUser(UserModel userModel) throws IOException {
        Map<String, String> data = JsonUtils.getJsonDataAsMap("dmoney/dev/DmoneyAPIData.json");
        String endPoint=  data.get("createEndpoint")+"/user/create";
        Response response = RestUtils.performPost(endPoint,userModel,Utils.getHeaderValues(prop));
        return response;

    }
    public Response deleteUser(String userId) throws IOException {
        Map<String, String> data = JsonUtils.getJsonDataAsMap("dmoney/dev/DmoneyAPIData.json");
        String endPoint=  data.get("createEndpoint")+"/user/delete/"+userId;
        Response response = RestUtils.performDelete(endPoint,Utils.getHeaderValues(prop));
        return response;
    }
}