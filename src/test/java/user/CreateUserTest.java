package user;

import client.UserClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pojo.User;

import static org.hamcrest.Matchers.is;

@DisplayName("Создание пользователя")
public class CreateUserTest extends SetupUser {

    private static final String USER_EXISTS_403 = "User already exists";
    private static final String FIELDS_REQUIRED_403 = "Email, password and name are required fields";

    @Before
    public void setup() {
        userClient = new UserClient();
    }

    @Test
    @DisplayName("Можно создать уникального пользователя")
    public void shouldCreateUniqueUser() {
        expStatusCode = 200;
        user = User.createRandomUser();
        Response response = userClient.register(user);
        response.then()
                .assertThat()
                .statusCode(expStatusCode)
                .and()
                .body("success", is(true));
        accessToken = userClient.getAccessToken(user);
    }

    @Test
    @DisplayName("Нельзя создать пользователя с пустым name")
    public void shouldNotCreateWithoutName() {
        expStatusCode = 403;
        user = User.createUserWithoutName();
        Response response = userClient.register(user);
        response.then()
                .assertThat()
                .statusCode(expStatusCode)
                .and()
                .body("message", is(FIELDS_REQUIRED_403));
    }

    @Test
    @DisplayName("Нельзя создать пользователя без password")
    public void shouldNotCreateWithoutPassword() {
        expStatusCode = 403;
        user = User.createUserWithoutPassword();
        Response response = userClient.register(user);
        response.then()
                .assertThat()
                .statusCode(expStatusCode)
                .and()
                .body("message", is(FIELDS_REQUIRED_403));
    }

    @SneakyThrows
    @Test
    @DisplayName("Нельзя создать пользователя, который уже зарегистрирован")
    public void shouldNotCreateExistingUser() {
        expStatusCode = 403;
        user = User.createRandomUser();
        Response response = userClient.register(user);
        response.then()
                .statusCode(200);
        accessToken = userClient.getAccessToken(user);
        response = userClient.register(user);
        response.then()
                .assertThat()
                .statusCode(expStatusCode)
                .and()
                .body("message", is(USER_EXISTS_403));
    }

    @Test
    @DisplayName("Нельзя создать пользователя без email")
    public void shouldNotCreateWithoutEmail() {
        expStatusCode = 403;
        user = User.createUserWithoutEmail();
        Response response = userClient.register(user);
        response.then()
                .assertThat()
                .statusCode(expStatusCode)
                .and()
                .body("message", is(FIELDS_REQUIRED_403));
    }

    @After
    public void tearDown() {
        deleteUser();
    }

}
