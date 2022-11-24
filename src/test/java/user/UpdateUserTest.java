package user;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pojo.User;

import static org.hamcrest.Matchers.is;

@DisplayName("Изменение данных пользователя")
public class UpdateUserTest extends SetupUser {

    private static final String AUTHORIZED_ERROR_401 = "You should be authorised";
    private String accessToken;
    private User userData;

    @Before
    public void setUp() {
        createTestUser();
        accessToken = userClient.getAccessToken(user);
    }

    @Test
    @DisplayName("Можно изменить name, email пользователя с авторизацией по токену")
    public void shouldUpdateUserData() {
        expStatusCode = 200;
        userData = User.createUserWithoutPassword();
        String expName = userData.getName();
        String expEmail = userData.getEmail().toLowerCase();
        Response response = userClient.updateData(userData, accessToken);
        response.then()
                .assertThat()
                .statusCode(expStatusCode)
                .and()
                .body("user.email", is(expEmail))
                .and()
                .body("user.name", is(expName));
    }

    @Test
    @DisplayName("Можно изменить password пользователя с авторизацией по токену")
    public void shouldUpdateUserPassword() {
        expStatusCode = 200;
        user.setPassword(User.generateRandomString());
        Response response = userClient.updateData(user, accessToken);
        response.then()
                .assertThat()
                .statusCode(expStatusCode)
                .and()
                .body("success", is(true));
        response = userClient.login(user);
        response.then()
                .statusCode(200);
        accessToken = userClient.getAccessToken(user);
    }

    @Test
    @DisplayName("Нельзя без авторизации менять данные пользователя")
    public void shouldNotUpdateWithoutAuth() {
        expStatusCode = 401;
        Response response = userClient.updateWithoutAuth(user);
        response.then()
                .assertThat()
                .statusCode(expStatusCode)
                .and()
                .body("message", is(AUTHORIZED_ERROR_401));
    }

    @After
    public void tearDown() {
        deleteUser();
    }
}
