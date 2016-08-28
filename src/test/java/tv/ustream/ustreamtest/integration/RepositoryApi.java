/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tv.ustream.ustreamtest.integration;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

/**
 *
 * @author pfeiferlaszlo
 */
public class RepositoryApi {
    public void getRepository(String name) {
        given().
            contentType("application/json").
        when().
            get("/repository/" + name).
        then().
            body(containsString("name")).
            body(containsString("creator")).
            statusCode(200);
    }
    
    public void getRepositoryWhenNotExist(String name) {
        given().
            contentType("application/json").
        when().
            get("/repository/" + name).
        then().
            body(containsString("There are no repository with this name!")).
            body(containsString("tv.ustream.ustreamtest.exception.NotExistException")).
            statusCode(400);
    }
    
    public void createRepository(String name, String author) {
        given().
            contentType("application/json").
            body("{\"name\":\"" + name + "\",\"creator\":\"" + author + "\"}").
        when().
            post("/repository").
        then().
            body(containsString(name)).
            body(containsString(author)).
            statusCode(200);
    }

    public void createRepositoryAlreadyExist(String name, String author) {
        given().
            contentType("application/json").
            body("{\"name\":\"" + name + "\",\"creator\":\"" + author + "\"}").
        when().
            post("/repository").
        then().
            body(containsString("tv.ustream.ustreamtest.exception.AlreadyExistException")).
            body(containsString("There is already a repository with this name!")).
            statusCode(400);
    }
    
    public void getRepositories(int threshold) {
        given().
            contentType("application/json").
        when().
            get("/repository?threshold="+threshold).
        then().
            body("", hasSize(equalTo(1))).
            statusCode(200);
    }
    
    public void deleteRepositroy(String name) {
        given().
            contentType("application/json").
        when().
            delete("/repository/" + name).
        then().
            statusCode(200);
    }
    
    public void deleteRepositroyNotExist(String name) {
        given().
            contentType("application/json").
        when().
            delete("/repository/" + name).
        then().
            body(containsString("tv.ustream.ustreamtest.exception.NotExistException")).
            body(containsString("There are no repository with this name!")).
            statusCode(400);
    }
}
