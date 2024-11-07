package restUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.Map;

public class RestUtils {

    public static Response performLogin(String endPoint, Object requestPayload){
        return RestAssured.given().log().all()
                .baseUri(endPoint)
                .contentType(ContentType.JSON)
                .body(requestPayload)
                .post().then().extract().response();

    }
     public static Response performPost(String endPoint, Object requestPayload, Map<String,String> headers){
         return RestAssured.given().log().all()
                 .baseUri(endPoint)
                 .headers(headers)
                 .contentType(ContentType.JSON)
                 .body(requestPayload)
                 .post().then().extract().response();

     }

    public static Response performDelete(String endPoint, Map<String,String> headers){
        return RestAssured.given().log().all()
                .baseUri(endPoint)
                .headers(headers)
                .contentType(ContentType.JSON)
                .delete().then().extract().response();

    }
    public static Response performSearch(String endPoint, Map<String,String> headers){
        return RestAssured.given().log().all()
                .baseUri(endPoint)
                .headers(headers)
                .contentType(ContentType.JSON)
                .get().then().extract().response();

    }

//    public static Response performPost(String endPoint, Map<String, Object> requestPayload, Map<String,String> headers){
//        return RestAssured.given().log().all()
//                .baseUri(endPoint)
//                .headers(headers)
//                .contentType(ContentType.JSON)
//                .body(requestPayload)
//                .post().then().extract().response();
//
//    }
}
