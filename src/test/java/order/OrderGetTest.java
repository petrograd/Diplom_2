package order;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;

import setup.Setup;
import client.OrderClient;

import static org.hamcrest.Matchers.*;

@DisplayName("Получение заказов конкретного пользователя")
public class OrderGetTest extends Setup {

    private static final String UNAUTHORIZED_USER_401 = "You should be authorised";

    @Test
    @DisplayName("Получение заказов пользователя с авторизацией")
    public void shouldGetOrdersWithAuth() {
        createTestUser();
        accessToken = userClient.getAccessToken(user);
        setupOrder(accessToken);
        expStatusCode = 200;
        Response response = orderClient.getWithAuth(accessToken);
        response.then()
                .assertThat()
                .statusCode(expStatusCode)
                .and()
                .body("orders", hasSize(greaterThan(0)));
    }

    @Test
    @DisplayName("Нельзя получить заказы пользователя без авторизации")
    public void shouldNotGetWithoutAuth() {
        expStatusCode = 401;
        orderClient = new OrderClient();
        Response response = orderClient.getOrdersWithoutAuth();
        response.then()
                .assertThat()
                .statusCode(expStatusCode)
                .and()
                .body("message", is(UNAUTHORIZED_USER_401));
    }

    @After
    public void tearDown() {
        deleteUser();
        order = null;
    }

}
