package courier;

import io.qameta.allure.junit4.DisplayName;
import model.courier.CourierCreate;
import org.junit.Test;

import java.net.HttpURLConnection;

import static data.TestData.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class CreateCourierTest extends CourierBaseTest {

    @Test
    @DisplayName("Успешное создание курьера")
    public void testCreateCourierSuccess() {
        generateCourierData(); // генерируем данные

        CourierCreate courier = new CourierCreate(courierLogin, courierPassword, courierFirstName);
        courierSteps.createCourier(courier)
                .then()
                .statusCode(HttpURLConnection.HTTP_CREATED)
                .body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Нельзя создать курьера без логина")
    public void testCannotCreateCourierWithoutLogin() {
        courierPassword = getCourierPassword();
        courierFirstName = getCourierFirstName();

        CourierCreate courier = new CourierCreate(null, courierPassword, courierFirstName);
        courierSteps.createCourier(courier)
                .then()
                .statusCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Нельзя создать курьера без пароля")
    public void testCannotCreateCourierWithoutPassword() {
        courierLogin = getCourierLogin();
        courierFirstName = getCourierFirstName();

        CourierCreate courier = new CourierCreate(courierLogin, null, courierFirstName);
        courierSteps.createCourier(courier)
                .then()
                .statusCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Нельзя создать двух одинаковых курьеров")
    public void testCannotCreateDuplicateCourier() {
        generateCourierData();
        CourierCreate courier = new CourierCreate(courierLogin, courierPassword, courierFirstName);

        // создаём первого
        courierSteps.createCourier(courier)
                .then()
                .statusCode(HttpURLConnection.HTTP_CREATED);

        // пытаемся создать такого же
        courierSteps.createCourier(courier)
                .then()
                .statusCode(HttpURLConnection.HTTP_CONFLICT)
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }
}
