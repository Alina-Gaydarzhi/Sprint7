package courier;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class RawCourierTest {
    private static String courierLogin;
    private static String courierPassword;
    private static String courierFirstName;

    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";

        // Генерируем уникальные данные для тестового курьера
        courierLogin = "junit_ninja_" + System.currentTimeMillis();
        courierPassword = "123654";
        courierFirstName = "saske_uchiha";
    }

    @Test
    public void testCreateCourierSuccess() {
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
    }

    // @After
    // public void cleanUp(){}
}
