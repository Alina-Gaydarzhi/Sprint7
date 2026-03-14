package order;

import io.qameta.allure.junit4.DisplayName;
import model.order.OrderCreate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static data.TestData.*;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderTest extends OrderBaseTest {
    private final List<String> colors;

    public CreateOrderTest(List<String> colors) {
        this.colors = colors;
    }

    @Parameterized.Parameters(name = "Colors: {0}")
    public static Object[][] getColors() {
        return new Object[][]{
                {COLOR_BLACK},
                {COLOR_GREY},
                {COLOR_BOTH},
                {null}
        };
    }

    @Test
    @DisplayName("Создание заказа с разными цветами")
    public void testCreateOrderWithDifferentColors() {
        OrderCreate order = new OrderCreate(
                getOrderFirstName(),
                getOrderLastName(),
                getOrderAddress(),
                getOrderMetroStation(),
                getOrderPhone(),
                getOrderRentTime(),
                getOrderDeliveryDate(),
                getOrderComment(),
                colors
        );

        track = orderSteps.createOrder(order)
                .then()
                .statusCode(201)
                .body("track", notNullValue())
                .extract()
                .path("track");
    }

}
