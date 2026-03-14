package order;

import data.BaseApiTest;
import org.junit.After;
import steps.OrderSteps;

public class OrderBaseTest extends BaseApiTest {
    protected Integer track; //чтобы отменять заказ в окнце
    protected OrderSteps orderSteps = new OrderSteps();

    @After
    public void cancelOrder() {
        //для того чтобы отменить заказ после теста по track
        if (track != null) {
            orderSteps.cancelOrder(track)
                    .then()
                    .statusCode(200);
        }
    }
}
