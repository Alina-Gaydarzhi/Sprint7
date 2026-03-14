package order;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import java.net.HttpURLConnection;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class GetOrdersListTest extends OrderBaseTest {

    @Test
    @DisplayName("Получение списка заказов")
    public void testGetOrdersListSuccess() {
        orderSteps.getOrdersList()
                .then()
                .statusCode(HttpURLConnection.HTTP_OK)
                .body("orders", notNullValue());
    }

    @Test
    @DisplayName("Получение списка заказов с несуществующим id курьера возвращает ошибку")
    public void testGetOrdersListWithNonexistentCourierIdReturnsError() {
        int nonexistentId = 999999;
        orderSteps.getOrdersListByCourierId(nonexistentId)
                .then()
                .statusCode(HttpURLConnection.HTTP_NOT_FOUND)
                .body("message", equalTo("Курьер с идентификатором " + nonexistentId + " не найден"));
    }
}
