package courier;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class RawCourierTest {
    private String courierLogin;
    private String courierPassword;
    private String courierFirstName;
    private Integer courierId; // для удаления после теста

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    private Integer loginAndGetId(String courierLogin, String courierPassword) {
        String loginBody = String.format(
                "{\"login\": \"%s\", \"password\": \"%s\"}",
                courierLogin, courierPassword
        );
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(loginBody)
                .when()
                .post("/api/v1/courier/login")
                .then()
                .log().all()
                .extract()
                .response();
        response.then().statusCode(200); // ожидаем успешный логин
        return response.path("id");
    }

    private void deleteCourier(int id) {
        RestAssured.given()
                .when()
                .delete("/api/v1/courier/" + id)
                .then()
                .statusCode(200);
    }

    @Test
    public void testCreateCourierSuccess() {
        courierLogin = "ninja_" + System.currentTimeMillis();
        courierPassword = "1234";
        courierFirstName = "Saske";

        String requestBody = String.format(
                "{\"login\": \"%s\", " +
                   "\"password\": \"%s\"," +
                   "\"firstName\": \"%s\"}",
                courierLogin, courierPassword, courierFirstName
        );

        System.out.println(courierLogin);

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/v1/courier")
                .then()
                .statusCode(201)
                .body("ok", equalTo(true));

        // Сохраняем ID для удаления
        courierId = loginAndGetId(courierLogin, courierPassword);
    }

    @Test
    public void createCourierWithoutLogin() {
        courierPassword = "1234";
        courierFirstName = "NoLogin";

        String requestBody = String.format("{\"password\": \"%s\", \"firstName\": \"%s\"}", courierPassword, courierFirstName);

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/v1/courier")
                .then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    public void createCourierWithoutPassword() {
        courierLogin = "ninja_no_pass_" + System.currentTimeMillis();
        courierPassword = null;
        courierFirstName = "NoPass";

        String requestBody = String.format("{\"login\": \"%s\", \"firstName\": \"%s\"}", courierLogin, courierFirstName);
        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/v1/courier")
                .then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    public void createDuplicateCourier() {
        courierLogin = "duplicate_" + System.currentTimeMillis();
        courierPassword = "1234";
        courierFirstName = "Dup";

        String requestBody = String.format(
                "{\"login\": \"%s\", \"password\": \"%s\", \"firstName\": \"%s\"}",
                courierLogin, courierPassword, courierFirstName
        );

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/v1/courier")
                .then()
                .statusCode(201);

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/v1/courier")
                .then()
                .statusCode(409)
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));

        courierId = loginAndGetId(courierLogin, courierPassword);
    }

    @After
    public void cleanUp() {
        if (courierId != null) {
            deleteCourier(courierId);
        }
    }
}