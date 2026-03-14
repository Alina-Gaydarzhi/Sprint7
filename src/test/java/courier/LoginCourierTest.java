package courier;

import io.qameta.allure.junit4.DisplayName;
import model.courier.CourierLogin;
import org.junit.Test;

import java.net.HttpURLConnection;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class LoginCourierTest extends CourierWithCreatedCourierBaseTest {
    @Test
    @DisplayName("Курьер может авторизоваться")
    public void testCourierSuccessLogin() {
        CourierLogin credentials = new CourierLogin(courierLogin, courierPassword);
        courierSteps.loginCourier(credentials)
                .then()
                .statusCode(HttpURLConnection.HTTP_OK)
                .body("id", notNullValue());
    }

    @Test
    @DisplayName("Вход без логина возвращает ошибку")
    public void testLoginWithoutLoginReturnsError() {
        CourierLogin credentials = new CourierLogin(null, courierPassword);
        courierSteps.loginCourier(credentials)
                .then()
                .statusCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Вход без пароля возвращает ошибку")
    public void testLoginWithoutPasswordReturnsError() {
        CourierLogin credentials = new CourierLogin(courierLogin, null);
        courierSteps.loginCourier(credentials)
                .then()
                .statusCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Вход с неверным паролем")
    public void testLoginWithWrongPassword() {
        CourierLogin credentials = new CourierLogin(courierLogin, "wrongpass");
        courierSteps.loginCourier(credentials)
                .then()
                .statusCode(HttpURLConnection.HTTP_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Вход с несуществующим пользователем")
    public void testLoginWithNonexistentUser() {
        CourierLogin credentials = new CourierLogin("nonexistent_" + courierLogin, courierPassword);
        courierSteps.loginCourier(credentials)
                .then()
                .statusCode(HttpURLConnection.HTTP_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
    }
}
