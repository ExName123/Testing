package ru.framework.pages.tests.taskfive;

import io.qameta.allure.Step;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import ru.framework.pages.taskfive.*;
import ru.framework.pages.tests.taskfive.base.BaseTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.core.IsEqual.equalTo;
import org.junit.Test;
import org.junit.Assert;
import static ru.framework.pages.tests.taskfive.base.BaseTest.*;

public class APITests extends BaseTest {

    public APITests(){
        super();
    }
    @BeforeClass
    public static void setUp() {
        BaseTest.setUp();
        requestSpecification.basePath("/users");
    }

    @Test
    public void checkListUsersValidate() {
        validateListUsersJSONSchema();
    }

    @Step("Валидация списка пользователей через схему JSON")
    public void validateListUsersJSONSchema() {
        sendGetRequest(200)
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("UsersSchema.json"));
    }

    @Test
    public void checkListUsersDTO() {
        validateListUsersDTO();
    }

    @Step("Проверка данных пользователей через DTO")
    public void validateListUsersDTO() {
        UsersPage usersPage = checkStatusCodeGet("https://reqres.in/api/users?page=2", 200)
                .extract().as(UsersPage.class);
        Assert.assertEquals(usersPage.getPage().intValue(), 2);
        Assert.assertEquals(usersPage.getPer_page().intValue(), 6);
        Assert.assertEquals(usersPage.getTotal().intValue(), 12);
        Assert.assertEquals(usersPage.getTotal_pages().intValue(), 2);
        Assert.assertFalse(usersPage.getData().isEmpty());
        Assert.assertEquals(usersPage.getData().get(2).getFirst_name(), "Tobias");
        Assert.assertEquals(usersPage.getData().get(2).getLast_name(), "Funke");
    }

    @Test
    public void testUserFromResponse() {
        validateUserFromResponse();
    }

    @Step("Проверка данных одного пользователя")
    public void validateUserFromResponse() {
        UserData userData = checkStatusCodeGet("https://reqres.in/api/users/2", 200)
                .extract().body().jsonPath().getObject("data", UserData.class);
        assertThat("Идентификатор пользователя должен быть равен 2", userData.getId(), is(2));
        assertThat("Адрес электронной почты пользователя должен быть janet.weaver@reqres.in", userData.getEmail(), is("janet.weaver@reqres.in"));
        assertThat("Имя пользователя должно быть Джанет", userData.getFirst_name(), is("Janet"));
        assertThat("Фамилия пользователя должна быть Weaver", userData.getLast_name(), is("Weaver"));
        assertThat("Аватар пользователя должен быть корректным", userData.getAvatar(), is("https://reqres.in/img/faces/2-image.jpg"));
    }

    @Test
    public void singleList() {
        validateSingleResource();
    }

    @Step("Проверка данных одного ресурса")
    public void validateSingleResource() {
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

    @Step("Проверка отсутствия ресурса")
    public void validateSingleResourceNotFound() {
        checkStatusCodeGet("https://reqres.in/api/unknown/23", 404)
                .body(equalTo("{}"));
    }

    @Test
    public void userNotFound() {
        validateUserNotFound();
    }

    @Step("Проверка отсутствия пользователя")
    public void validateUserNotFound() {
        String responseBody = checkStatusCodeGet("https://reqres.in/api/users/22", 404)
                .extract()
                .asString();
        Assert.assertEquals("{}", responseBody, "{}");
    }

    @Test
    public void createUser() {
        validateCreateUser();
    }

    @Step("Создание пользователя через эндпоинт /api/users POST")
    public void validateCreateUser() {
        People people = new People("morpheus", "leader");
        PeopleCreater peopleCreater = checkStatusCodePost(people, "https://reqres.in/api/users", 201)
                .extract()
                .as(PeopleCreater.class);
        Assert.assertEquals(peopleCreater.getName(), people.getName());
        Assert.assertEquals(peopleCreater.getJob(), people.getJob());
    }

    @Test
    public void listResource() {
        validateListResource();
    }

    @Step("Получение списка ресурсов")
    public void validateListResource() {
        List<UsersPage> usersPageList = checkStatusCodeGet("https://reqres.in/api/unknown", 200)
                .extract().jsonPath().getList("data", UsersPage.class);
        Assert.assertNotNull("Список не должен быть null", usersPageList);
        Assert.assertFalse("Список не должен быть пустым", usersPageList.isEmpty());
        Assert.assertEquals("Ожидалось шесть элементов в списке", 6, usersPageList.size());
    }

    @Test
    public void putUser() {
        validatePutUser();
    }

    @Step("Обновление пользователя через PUT")
    public void validatePutUser() {
        People people = new People("morpheus", "zion resident");
        checkStatusCodePut(people, "https://reqres.in/api/users/2", 200)
                .body("name", equalTo("morpheus"))
                .body("job", equalTo("zion resident"));
    }

    @Test
    public void patchUser() {
        validatePatchUser();
    }

    @Step("Частичное обновление пользователя через PATCH")
    public void validatePatchUser() {
        People people = new People("morpheus", "zion resident");
        checkStatusCodePatch(people, "https://reqres.in/api/users/2", 200)
                .body("name", equalTo("morpheus"))
                .body("job", equalTo("zion resident"));
    }

    @Test
    public void deleteUser() {
        validateDeleteUser();
    }

    @Step("Удаление пользователя")
    public void validateDeleteUser() {
        deleteUser("https://reqres.in/api/users/2", 204)
                .body(isEmptyOrNullString());
    }

    @Test
    public void registerUser() {
        validateRegisterUser();
    }

    @Step("Регистрация пользователя")
    public void validateRegisterUser() {
        String requestBody = "{\"email\": \"eve.holt@reqres.in\", \"password\": \"pistol\"}";
        postRegister(requestBody, "https://reqres.in/api/register", 200)
                .body("id", equalTo(4))
                .body("token", equalTo("QpwL5tke4Pnpja7X4"));
    }

    @Test
    public void unsuccessfulRegisterUser() {
        validateUnsuccessfulRegisterUser();
    }

    @Step("Ошибка при регистрации пользователя")
    public void validateUnsuccessfulRegisterUser() {
        String requestBody = "{\"email\": \"sydney@fife\"}";
        postRegister(requestBody, "https://reqres.in/api/register", 400)
                .body("error", equalTo("Missing password"));
    }

    @Test
    public void loginUser() {
        validateLoginUser();
    }

    @Step("Вход пользователя")
    public void validateLoginUser() {
        String requestBody = "{\"email\": \"eve.holt@reqres.in\", \"password\": \"cityslicka\"}";
        postRegister(requestBody, "https://reqres.in/api/login", 200)
                .body("token", equalTo("QpwL5tke4Pnpja7X4"));
    }

    @Test
    public void unsuccessfulLoginUser() {
        validateUnsuccessfulLoginUser();
    }


    @Step("Ошибка при входе пользователя")
    public void validateUnsuccessfulLoginUser() {
        String requestBody = "{\"email\": \"peter@klaven\"}";
        postRegister(requestBody, "https://reqres.in/api/login", 400)
                .body("error", equalTo("Missing password"));
    }
}