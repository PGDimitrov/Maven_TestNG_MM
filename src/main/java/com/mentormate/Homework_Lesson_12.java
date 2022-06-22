package com.mentormate;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

class LoginDto {
    private String usernameOrEmail;
    private String password;

    public String getUsernameOrEmail() {
        return usernameOrEmail;
    }

    public void setUsernameOrEmail(String usernameOrEmail) {
        this.usernameOrEmail = usernameOrEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

public class Homework_Lesson_12 {

    @BeforeTest
    public void setUp() {
        RestAssured.baseURI = "http://training.skillo-bg.com:3100";
    }

    @Test
    public void testLogin() {

    LoginDto loginDto = new LoginDto();

    loginDto.setUsernameOrEmail("test01234");
    loginDto.setPassword("test43210");

        Response response = given()
                .log().all()
                .header("Content-Type", "application/json")
                .body(loginDto)
                .when()
                .post("/users/login")
                .then()
                .log()
                .all()
                .extract()
                .response();

        int statusCode = response.statusCode();

        Assert.assertEquals(statusCode, HttpStatus.SC_CREATED);
    }
}
