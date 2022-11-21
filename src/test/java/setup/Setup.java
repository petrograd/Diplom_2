package setup;

import io.restassured.response.Response;
import lombok.SneakyThrows;

import client.IngredientClient;
import client.OrderClient;
import client.UserClient;
import pojo.Order;
import pojo.User;

public class Setup {
    protected static UserClient userClient;
    protected static User user;
    protected static String accessToken;
    protected OrderClient orderClient;
    protected IngredientClient ingrClient;
    protected Order order;
    protected int expStatusCode;

    @SneakyThrows
    public static void createTestUser() {
        userClient = new UserClient();
        user = User.createRandomUser();
        Response response = userClient.register(user);
        response.then()
                .statusCode(200);
        Thread.sleep(2000); //из-за ошибки 429 - Too Many Requests
    }

    public void setupOrder(String accessToken) {
        ingrClient = new IngredientClient();
        order = Order.getRandomOrder(ingrClient.getIngredients(), 3);
        orderClient = new OrderClient();
        Response response = orderClient.createWithAuth(accessToken, order);
        response.then()
                .statusCode(200);
    }

    public void deleteUser() {
        if (accessToken != null) {
            Response response = userClient.delete(accessToken);
            response.then()
                    .statusCode(202);
        }
        accessToken = null;
    }
}
