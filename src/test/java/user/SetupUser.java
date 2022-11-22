package user;

import client.IngredientClient;
import client.OrderClient;
import client.UserClient;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import pojo.Order;
import pojo.User;

public class SetupUser {
    protected static UserClient userClient;
    protected static User user;
    protected static String accessToken;

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

     public void deleteUser() {
        if (accessToken != null) {
            Response response = userClient.delete(accessToken);
            response.then()
                    .statusCode(202);
        }
        accessToken = null;
    }
}
