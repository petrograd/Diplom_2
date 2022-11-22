package client;

import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class BasicClient extends EndPointURLs {

    protected RequestSpecification getSpec() {
        return given().log().all()
                .header("Content-Type", "application/json")
                .baseUri(BASE_URL);
    }
}
