package testrunner;

import com.github.javafaker.Faker;
import controller.UserController;
import io.qameta.allure.Step;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.configuration.ConfigurationException;
import org.testng.Assert;
import org.testng.annotations.Test;
import setup.Setup;
import setup.Models;
import utils.Utils;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class TestRunner extends Setup {
    @Test(priority = 1, description = "User Login")
    @Step("Successful user login in the system")
    public void doLogin() throws ConfigurationException, InterruptedException, IOException {
        UserController userController=new UserController(prop);
        Models model=new Models();
        model.setEmail("admin@roadtocareer.net");
        model.setPassword("1234");
        Response res= userController.userLogin(model);
        Assert.assertEquals(res.getStatusCode(), 200, "Expected status code 200");
        System.out.println(res.asString());

        JsonPath jsonObj=res.jsonPath();
        String token= jsonObj.get("token");
        System.out.println(token);
        Utils.setEnvVar("token",token);
        Thread.sleep(3000);
    }
    @Test(priority = 2, description = "Create new user")
    @Step("Creating a new user in the system")
    public void createUser() throws ConfigurationException, IOException {
        UserController userController=new UserController(prop);
        Models model=new Models();
        Faker faker=new Faker();
        model.setName("Rest Assured "+ faker.name().firstName());
        model.setEmail(faker.internet().emailAddress().toLowerCase());
        model.setPassword("1234");
        model.setPhone_number("0150"+ Utils.generateRandomId(1000000,9999999));
        model.setNid("123456789");
        model.setRole("Customer");
        Response res= userController.createUser(model);
        System.out.println(res.asString());

        JsonPath jsonPath=res.jsonPath();
        String message=jsonPath.get("message");
        Assert.assertTrue(message.contains("User created"));

        int userId= jsonPath.get("user.id");
        Utils.setEnvVar("userId",String.valueOf(userId));
    }
    @Test(priority = 3, description = "Search user by id")
    @Step("Searching the newly created user in the system")
    public void searchUser() throws IOException {
        UserController userController=new UserController(prop);
        Response res= userController.searchUser(prop.getProperty("userId"));
        System.out.println(res.asString());

        JsonPath jsonPath=res.jsonPath();
        int id= jsonPath.get("user.id");
        String email= jsonPath.get("user.email");
        System.out.println(id+" "+email);
    }

    @Test(priority = 4, description = "Delete user")
    @Step("Deleting the newly created user from the system")
    public void deleteUser() throws IOException {
        UserController userController=new UserController(prop);
        Response res= userController.deleteUser(prop.getProperty("userId"));
        System.out.println(res.asString());
        Assert.assertEquals(res.getStatusCode(), 200, "Expected status code 200");

        JsonPath jsonPath = res.jsonPath();
        String message = jsonPath.get("message");
        Assert.assertTrue(message.contains("User deleted"), "Expected message to contain 'User deleted'");
    }
}
