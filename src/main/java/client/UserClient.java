package client;

import io.restassured.response.Response;
import pojo.User;

import static pojo.User.getUserLoginData;

public class UserClient extends BasicClient {

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
                .body(getUserLoginData(user))
                .when()
                .post(LOGIN);
        return response;
    }

    public String getAccessToken(User user) {
        String token = login(user)
                .then()
                .extract()
                .path("accessToken");
        return token;
    }

    public Response updateWithoutAuth(User user) {
        Response response = getSpec()
                .and()
                .body(user)
                .when()
                .patch(USER);
        return response;
    }

    public Response updateData(User user, String accessToken) {
        Response response = getSpec()
                .header("Authorization", accessToken)
                .and()
                .body(user)
                .when()
                .patch(USER);
        return response;
    }

    public Response delete(String accessToken) {
        Response response =  getSpec()
                .header("Authorization", accessToken)
                .when()
                .delete(USER);
        return response;
    }

    public Response delete(User user) {
        accessToken = getAccessToken(user);
        Response response =  getSpec()
                .header("Authorization", accessToken)
                .when()
                .delete(USER);
        return response;
    }

}
