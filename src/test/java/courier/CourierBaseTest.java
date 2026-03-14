package courier;

import data.BaseApiTest;
import steps.CourierSteps;

import static data.TestData.*;

public class CourierBaseTest extends BaseApiTest {
    protected String courierLogin;
    protected String courierPassword;
    protected String courierFirstName;
    protected CourierSteps courierSteps = new CourierSteps();

    // Метод для генерации свежих данных
    protected void generateCourierData() {
        courierLogin = getCourierLogin();
        courierPassword = getCourierPassword();
        courierFirstName = getCourierFirstName();
    }
}
