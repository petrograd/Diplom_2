package client;

import io.restassured.specification.RequestSpecification;

import config.Config;
import static io.restassured.RestAssured.given;

public class BasicClient extends Config {

    protected RequestSpecification getSpec() {
        return given().log().all()
                .header("Content-Type", "application/json")
                .baseUri(BASE_URL);
    }
}
