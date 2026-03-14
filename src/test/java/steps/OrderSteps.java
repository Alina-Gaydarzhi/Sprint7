package steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.order.OrderCreate;

import static io.restassured.RestAssured.given;

public class OrderSteps {
    @Step("Создание заказа")
    public Response createOrder(OrderCreate order) {
        return given()
                .contentType(ContentType.JSON)
                .body(order)
                .when()
                .post("/api/v1/orders")
                .then()
                .extract()
                .response();
    }

    @Step("Получение списка заказов")
    public Response getOrdersList() {
        return given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/v1/orders")
                .then()
                .extract()
                .response();
    }

    @Step("Получение списка заказов по courierId = {courierId}")
    public Response getOrdersListByCourierId(int courierId) {
        return given()
                .contentType(ContentType.JSON)
                .queryParam("courierId", courierId)
                .when()
                .get("/api/v1/orders")
                .then()
                .extract()
                .response();
    }

    @Step("Отмена заказа по track = {track}")
    public Response cancelOrder(int track) {
        return given()
                .contentType(ContentType.JSON)
                .when()
                .put("/api/v1/orders/cancel?track=" + track)
                .then()
                .extract()
                .response();
    }
}
