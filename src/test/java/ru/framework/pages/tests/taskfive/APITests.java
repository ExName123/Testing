package ru.framework.pages.tests.taskfive;

import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.framework.pages.taskfive.*;
import ru.framework.pages.taskfour.MainPageKinopoisk;
import ru.framework.pages.tests.taskfive.base.BaseTest;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.core.IsEqual.equalTo;

import io.qameta.allure.Step;

import static org.hamcrest.MatcherAssert.assertThat;

public class APITests extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(APITests.class);

    public APITests() {
        super();
    }

    @BeforeAll
    public static void setUp() {
        BaseTest.setUp();
        requestSpecification.basePath("/users");
    }

    @Test
    public void checkListUsersValidate() {
        validateListUsersJSONSchema();
    }

    @Step("Validating a list of users via JSON schema")
    public void validateListUsersJSONSchema() {
        logger.info("Validating a list of users via JSON schema");

        sendGetRequest(200)
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("UsersSchema.json"));
    }

    @Test
    public void checkListUsersDTO() {
        validateListUsersDTO();
    }

    @Step("Validating user data via DTO")
    public void validateListUsersDTO() {
        logger.info("Validating user data via DTO");

        UsersPage usersPage = checkStatusCodeGet("https://reqres.in/api/users?page=2", 200)
                .extract().as(UsersPage.class);
        Assertions.assertEquals(usersPage.getPage().intValue(), 2);
        Assertions.assertEquals(usersPage.getPer_page().intValue(), 6);
        Assertions.assertEquals(usersPage.getTotal().intValue(), 12);
        Assertions.assertEquals(usersPage.getTotal_pages().intValue(), 2);
        Assertions.assertFalse(usersPage.getData().isEmpty());
        Assertions.assertEquals(usersPage.getData().get(2).getFirst_name(), "Tobias");
        Assertions.assertEquals(usersPage.getData().get(2).getLast_name(), "Funke");
    }

    @Test
    public void testUserFromResponse() {
        validateUserFromResponse();
    }

    @Step("Single user data verification")
    public void validateUserFromResponse() {
        logger.info("Single user data verification");

        UserData userData = checkStatusCodeGet("https://reqres.in/api/users/2", 200)
                .extract().body().jsonPath().getObject("data", UserData.class);
        assertThat("User ID must be 2", userData.getId(), is(2));
        assertThat("The user's email address must be janet.weaver@reqres.in", userData.getEmail(), is("janet.weaver@reqres.in"));
        assertThat("Username should be Janet", userData.getFirst_name(), is("Janet"));
        assertThat("The user's last name must be Weaver", userData.getLast_name(), is("Weaver"));
        assertThat("The user avatar must be correct", userData.getAvatar(), is("https://reqres.in/img/faces/2-image.jpg"));
    }

    @Test
    public void singleList() {
        validateSingleResource();
    }

    @Step("Checking the data of one resource")
    public void validateSingleResource() {
        logger.info("Checking the data of one resource");

        ColorData resource = checkStatusCodeGet("https://reqres.in/api/unknown/2", 200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("ColorSchema.json"))
                .extract().jsonPath().getObject("data", ColorData.class);
        assertThat("", resource.getId(), is(2));
        assertThat("", resource.getName(), is("fuchsia rose"));
        assertThat("", resource.getYear(), is(2001));
        assertThat("", resource.getColor(), is("#C74375"));
        assertThat("", resource.getPantone_value(), is("17-2031"));
    }

    @Test
    public void singleListNotFound() {
        validateSingleResourceNotFound();
    }

    @Step("Checking for missing resource")
    public void validateSingleResourceNotFound() {
        logger.info("Checking for missing resource");

        checkStatusCodeGet("https://reqres.in/api/unknown/23", 404)
                .body(equalTo("{}"));
    }

    @Test
    public void userNotFound() {
        validateUserNotFound();
    }

    @Step("Checking for user absence")
    public void validateUserNotFound() {
        logger.info("Checking for user absence");

        String responseBody = checkStatusCodeGet("https://reqres.in/api/users/22", 404)
                .extract()
                .asString();
        Assertions.assertEquals("{}", responseBody, "{}");
    }

    @Test
    public void createUser() {
        validateCreateUser();
    }

    @Step("Creating a user via endpoint /api/users POST")
    public void validateCreateUser() {
        logger.info("Creating a user via endpoint /api/users POST");

        People people = new People("morpheus", "leader");
        PeopleCreater peopleCreater = checkStatusCodePost(people, "https://reqres.in/api/users", 201)
                .extract()
                .as(PeopleCreater.class);
        Assertions.assertEquals(peopleCreater.getName(), people.getName());
        Assertions.assertEquals(peopleCreater.getJob(), people.getJob());
    }

    @Test
    public void listResource() {
        validateListResource();
    }

    @Step("Getting a list of resources")
    public void validateListResource() {
        logger.info("Getting a list of resources");

        List<UsersPage> usersPageList = checkStatusCodeGet("https://reqres.in/api/unknown", 200)
                .extract().jsonPath().getList("data", UsersPage.class);
        Assertions.assertNotNull(usersPageList, "The list shouldn't be null");
        Assertions.assertFalse(usersPageList.isEmpty(), "The list must not be empty");
        Assertions.assertEquals(6, usersPageList.size(), "Expected six elements in the list");
    }

    @Test
    public void putUser() {
        validatePutUser();
    }

    @Step("User update via PUT")
    public void validatePutUser() {
        logger.info("User update via PUT");

        People people = new People("morpheus", "zion resident");
        checkStatusCodePut(people, "https://reqres.in/api/users/2", 200)
                .body("name", equalTo("morpheus"))
                .body("job", equalTo("zion resident"));
    }

    @Test
    public void patchUser() {
        validatePatchUser();
    }

    @Step("Partial user update via PATCH")
    public void validatePatchUser() {
        logger.info("Partial user update via PATCH");

        People people = new People("morpheus", "zion resident");
        checkStatusCodePatch(people, "https://reqres.in/api/users/2", 200)
                .body("name", equalTo("morpheus"))
                .body("job", equalTo("zion resident"));
    }

    @Test
    public void deleteUser() {
        validateDeleteUser();
    }

    @Step("Deleting a user")
    public void validateDeleteUser() {
        logger.info("Deleting a user");

        deleteUser("https://reqres.in/api/users/2", 204)
                .body(isEmptyOrNullString());
    }

    @Test
    public void registerUser() {
        validateRegisterUser();
    }

    @Step("User registration")
    public void validateRegisterUser() {
        logger.info("User registration");

        String requestBody = "{\"email\": \"eve.holt@reqres.in\", \"password\": \"pistol\"}";
        postRegister(requestBody, "https://reqres.in/api/register", 200)
                .body("id", equalTo(4))
                .body("token", equalTo("QpwL5tke4Pnpja7X4"));
    }

    @Test
    public void unsuccessfulRegisterUser() {
        validateUnsuccessfulRegisterUser();
    }

    @Step("Error while registering user")
    public void validateUnsuccessfulRegisterUser() {
        logger.info("Error while registering user");

        String requestBody = "{\"email\": \"sydney@fife\"}";
        postRegister(requestBody, "https://reqres.in/api/register", 400)
                .body("error", equalTo("Missing password"));
    }

    @Test
    public void loginUser() {
        validateLoginUser();
    }

    @Step("User Login")
    public void validateLoginUser() {
        logger.info("User Login");

        String requestBody = "{\"email\": \"eve.holt@reqres.in\", \"password\": \"cityslicka\"}";
        postRegister(requestBody, "https://reqres.in/api/login", 200)
                .body("token", equalTo("QpwL5tke4Pnpja7X4"));
    }

    @Test
    public void unsuccessfulLoginUser() {
        validateUnsuccessfulLoginUser();
    }


    @Step("User login error")
    public void validateUnsuccessfulLoginUser() {
        logger.info("User login error");

        String requestBody = "{\"email\": \"peter@klaven\"}";
        postRegister(requestBody, "https://reqres.in/api/login", 400)
                .body("error", equalTo("Missing password"));
    }
}