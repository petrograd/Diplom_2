package client;

import io.restassured.response.Response;
import pojo.User;

import static pojo.User.loginData;

public class UserClient extends BaseClient {

    private String accessToken;

    public Response register(User user) {
        Response response = getSpec()
                .and()
                .body(user)
                .when()
                .post(REGISTER);
        return response;
    }


    public Response login(User user) {
        Response response = getSpec()
                .and()
                .body(loginData(user))
                .when()
                .post(LOGIN);
        return response;
    }


    public String getAccessToken(User user) {
        return login(user)
                .then()
                .extract()
                .path("accessToken");
    }

    public Response delete(User user) {
        accessToken = getAccessToken(user);
        return getSpec()
                .header("Authorization", accessToken)
                .when()
                .delete(USER);
    }

    public Response delete(String accessToken) {
        return getSpec()
                .header("Authorization", accessToken)
                .when()
                .delete(USER);
    }

    public Response updateWithoutAuth(User user) {
        Response response = getSpec()
                .and()
                .body(user)
                .when()
                .patch(USER);
        return response;
    }

    public Response update(User user, String accessToken) {
        Response response = getSpec()
                .header("Authorization", accessToken)
                .and()
                .body(user)
                .when()
                .patch(USER);
        return response;
    }


}
