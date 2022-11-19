package setup;

import lombok.SneakyThrows;

import client.IngredientClient;
import client.OrderClient;
import client.UserClient;
import pojo.Order;
import pojo.User;

public class Setup {

    protected OrderClient orderClient;
    protected IngredientClient ingrClient;
    protected Order order;
    protected static UserClient userClient;
    protected static User user;
    protected static String accessToken;
    protected int expStatusCode;

    @SneakyThrows
    public static void registerTestUser() {
        userClient = new UserClient();
        user = User.createRandomUser();
        userClient.register(user)
                .then()
                .statusCode(200);
        Thread.sleep(2000); //из-за ошибки 429 - Too Many Requests
    }

    public void setupOrder(String accessToken) {
        ingrClient = new IngredientClient();
        orderClient = new OrderClient();
        order = Order.getRandomOrder(ingrClient.getIngredients(), 3);
        orderClient.createWithAuth(accessToken, order)
                .then()
                .statusCode(200);
    }

    public void deleteUser() {
        if (accessToken != null) {
            userClient.delete(accessToken)
                    .then()
                    .statusCode(202);
        }
        accessToken = null;
    }
}
