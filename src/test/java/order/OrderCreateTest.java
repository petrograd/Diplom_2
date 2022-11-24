package order;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pojo.Order;

import client.IngredientClient;
import client.OrderClient;

import static org.hamcrest.Matchers.*;

@DisplayName("Создание заказа")
public class OrderCreateTest extends SetupOrder {
    private static final String INCORRECT_ID_400 = "One or more ids provided are incorrect";
    private static final String NO_ID_400 = "Ingredient ids must be provided";
    private String hashCode;

    @Before
    public void setup() {
        orderClient = new OrderClient();
        ingrClient = new IngredientClient();
        order = Order.getRandomOrder(ingrClient.getIngredients(), 3);
    }

    @Test
    @DisplayName("Создание заказа с авторизацией и ингредиентами")
    public void shouldCreateOrderWithAuth() {
        createTestUser();
        accessToken = userClient.getAccessToken(user);
        expStatusCode = 200;
        Response response = orderClient.createWithAuth(accessToken, order);
        response.then()
                .assertThat()
                .statusCode(expStatusCode)
                .and()
                .body("order.number", notNullValue())
                .and()
                .body("order.owner.email", is(user.getEmail().toLowerCase()));
    }

    @Test
    @DisplayName("Создание заказа без авторизации с ингредиентами")
    public void shouldCreateOrderWithoutAuth() {
        expStatusCode = 200;
        Response response = orderClient.createWithoutAuth(order);
        response.then()
                .assertThat()
                .statusCode(expStatusCode)
                .and()
                .body("order.number", notNullValue())
                .and()
                .body("success", is(true));
    }

    @Test
    @DisplayName("Не создать заказ с невалидным форматом хеша ингредиентов")
    public void shouldNotCreateWithWrongHash() {
        expStatusCode = 500;
        hashCode = "abcde";
        order = Order.getOrderWithHashAdded(hashCode);
        Response response = orderClient.createWithoutAuth(order);
        response.then()
                .assertThat()
                .statusCode(expStatusCode);
    }

    @Test
    @DisplayName("Не создать заказь без ингредиентов")
    public void shouldNotСreateOrderWithoutIngr() {
        expStatusCode = 400;
        Response response = orderClient.createWithoutIngr();
        response.then()
                .assertThat()
                .statusCode(expStatusCode)
                .and()
                .body("message", is(NO_ID_400));
    }

    @Test
    @DisplayName("Нельзя создать заказ с невалидным хешем ингредиентов")
    public void shouldNotCreateOrderWithInvalidHash() {
        expStatusCode = 400;
        hashCode = "000000000000000000000000";
        order = Order.getOrderWithHashAdded(hashCode);
        Response response = orderClient.createWithoutAuth(order);
        response.then()
                .assertThat()
                .statusCode(expStatusCode)
                .and()
                .body("message", is(INCORRECT_ID_400));
    }

    @After
    public void tearDown() {
        // В документации нет описания способа удаления тестового заказа из базы
        order = null;
    }

}
