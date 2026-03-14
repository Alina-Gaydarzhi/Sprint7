package data;

import com.github.javafaker.Faker;

public class TestData {
    private static final Faker faker = new Faker();

    public static String getCourierLogin() {
        return faker.name().lastName() + faker.number().digits(6);
    }

    public static String getCourierPassword() {
        return faker.internet().password(6, 10);
    }

    public static String getCourierFirstName() {
        return faker.name().firstName();
    }

    // Данные для заказа
    public static String getOrderFirstName() {
        return faker.name().firstName();
    }

    public static String getOrderLastName() {
        return faker.name().lastName();
    }

    public static String getOrderAddress() {
        return faker.address().streetAddress();
    }

    public static String getOrderMetroStation() {
        return String.valueOf(faker.number().numberBetween(1, 50));
    }

    public static String getOrderPhone() {
        return faker.numerify("+7##########");
    }

    public static int getOrderRentTime() {
        return faker.number().numberBetween(1, 7);
    }

    public static String getOrderDeliveryDate() {
        return java.time.LocalDate.now().plusDays(1).toString();
    }

    public static String getOrderComment() {
        return faker.lorem().sentence();
    }

    public static final java.util.List<String> COLOR_BLACK = java.util.Arrays.asList("BLACK");
    public static final java.util.List<String> COLOR_GREY = java.util.Arrays.asList("GREY");
    public static final java.util.List<String> COLOR_BOTH = java.util.Arrays.asList("BLACK", "GREY");
}
