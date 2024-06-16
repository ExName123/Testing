package ru.framework.pages.tests.taskfive;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.given;

public class BaseTest {
    static RequestSpecification requestSpecification;
    static final String BASE_URL = "https://reqres.in/";

    @BeforeAll
    public static void setUp() {
        requestSpecification = given()
                .baseUri(BASE_URL)
                .log().all()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON);
    }

    static ValidatableResponse checkStatusCodeGet(String url, int code) {
        return given(requestSpecification)
                .get(url)
                .then()
                .log().all()
                .statusCode(code);
    }

    static ValidatableResponse checkStatusCodeGet(int code) {
        return given(requestSpecification)
                .get()
                .then()
                .log().all()
                .statusCode(code);
    }

    static ValidatableResponse checkStatusCodePost(String url, Object body, int code) {
        return given(requestSpecification)
                .body(body)
                .post(url)
                .then()
                .log().all()
                .statusCode(code);
    }

    static ValidatableResponse checkStatusCodePost(Object body, int code) {
        return given(requestSpecification)
                .body(body)
                .post()
                .then()
                .log().all()
                .statusCode(code);
    }
}

