package steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.courier.CourierCreate;
import model.courier.CourierLogin;

import static io.restassured.RestAssured.given;

public class CourierSteps {
    @Step("Создание курьера: {courier.login}")
    public Response createCourier(CourierCreate courier) {
        return given()
                .contentType(ContentType.JSON)
                .body(courier)
                .when()
                .post("/api/v1/courier")
                .then()
                .extract()
                .response();
    }

    @Step("Логин курьера: {credentials.login}")
    public Response loginCourier(CourierLogin credentials) {
        return given()
                .contentType(ContentType.JSON)
                .body(credentials)
                .when()
                .post("/api/v1/courier/login")
                .then()
                .extract()
                .response();
    }

    @Step("Удаление курьера с id = {id}")
    public Response deleteCourier(int id) {
        return given()
                .contentType(ContentType.JSON)
                .when()
                .delete("/api/v1/courier/" + id)
                .then()
                .extract()
                .response();
    }
}
