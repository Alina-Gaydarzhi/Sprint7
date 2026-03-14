package courier;

import data.BaseApiTest;
import model.courier.CourierCreate;
import model.courier.CourierLogin;
import org.junit.After;
import org.junit.Before;
import steps.CourierSteps;

import static data.TestData.*;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class CourierWithCreatedCourierBaseTest extends BaseApiTest {
    protected String courierLogin;
    protected String courierPassword;
    protected String courierFirstName;
    protected Integer courierId;
    protected CourierSteps courierSteps = new CourierSteps();

    @Before
    public void setUpCourier() {
        // енерирум данные
        courierLogin = getCourierLogin();
        courierPassword = getCourierPassword();
        courierFirstName = getCourierFirstName();

        CourierCreate createReq = new CourierCreate(courierLogin, courierPassword, courierFirstName);
        courierSteps.createCourier(createReq)
                .then()
                .statusCode(201);

        // получаем id для удаления в последующем
        CourierLogin loginReq = new CourierLogin(courierLogin, courierPassword);
        courierId = courierSteps.loginCourier(loginReq)
                .then()
                .statusCode(200)
                .extract()
                .path("id");
        assertThat("ID курьера не должен быть null", courierId, notNullValue());
    }

    @After
    public void tearDownCourier() {
        if (courierId != null) {
            courierSteps.deleteCourier(courierId)
                    .then()
                    .statusCode(200);
        }
    }
}
