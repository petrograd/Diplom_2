package client;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojo.Order;

public class OrderClient extends BaseClient {

    @Step("Заказ без авторизации")
    public Response createWithoutAuth(Order order) {
        Response response = getSpec()
                .and()
                .body(order)
                .when()
                .post(ORDERS);
        return response;
    }

    @Step("Заказ без ингредиентов")
    public Response createWithoutIngr() {
        Response response = getSpec()
                .when()
                .post(ORDERS);
        return response;
    }

    @Step("Заказ с авторизацией")
    public Response createWithAuth(String accessToken, Order order) {
        Response response = getSpec()
                .header("Authorization", accessToken)
                .and()
                .body(order)
                .when()
                .post(ORDERS);
        return response;
    }

    @Step("Получение заказов пользователя с авторизацией")
    public Response getWithAuth(String accessToken) {
        Response response = getSpec()
                .header("Authorization", accessToken)
                .when()
                .get(ORDERS);
        return response;
    }

    public Response getWithoutAuth() {
        Response response = getSpec()
                .when()
                .get(ORDERS);
        return response;
    }


}
