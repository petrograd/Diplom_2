package user;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pojo.User;

import static org.hamcrest.Matchers.is;

@DisplayName("Логин пользователя")
public class LoginUserTest extends SetupUser {
    private static final String FIELDS_INCORRECT_202 = "email or password are incorrect";

    @Before
    public void setUp() {
        createTestUser();
    }

    @Test
    @DisplayName("Логин под существующим пользователем")
    public void shouldLoginExistingUser() {
        expStatusCode = 200;
        Response response = userClient.login(user);
        response.then()
                .assertThat()
                .statusCode(expStatusCode)
                .and()
                .body("success", is(true));
        accessToken = userClient.getAccessToken(user);
    }

    @Test
    @DisplayName("Логин с неверным логином и паролем")
    public void shouldNotLoginIncorrectCredentials() {
        expStatusCode = 401;
        accessToken = userClient.getAccessToken(user);
        user.setPassword(User.generateRandomString());
        Response response = userClient.login(user);
        response.then()
                .assertThat()
                .statusCode(expStatusCode)
                .and()
                .body("message", is(FIELDS_INCORRECT_202));
    }

    @After
    public void tearDown() {
        deleteUser();
        }

}
