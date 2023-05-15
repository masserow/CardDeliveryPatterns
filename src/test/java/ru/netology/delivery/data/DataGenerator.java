
package ru.netology.delivery.data;
import com.github.javafaker.Faker;
import lombok.Value;
import lombok.experimental.UtilityClass;
import lombok.val;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;
//@UtilityClass
public class DataGenerator {

    //@UtilityClass
    // public static class Registration  {
    // public static RegistrationInfo generateInfo(String locale)  {
    //Faker faker = new Faker(new Locale(locale));
    //return new RegistrationInfo(faker.address().city(), faker.name().fullName(),
    //("+" + faker.phoneNumber().subscriberNumber(11)));
    //}
    // }
    private DataGenerator() {
    }

    public static String generateDate(int days) {

        String date = LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        return date;
    }

    public static String generateCity(String locale) {
        Faker faker = new Faker(new Locale("ru"));
        String city = faker.address().city();
        return city;
    }

    public static String generateName(String locale) {
        Faker faker = new Faker(new Locale("ru"));
        String name = faker.name().fullName();
        return name;
    }

    public static String generatePhone(String locale) {
        Faker faker = new Faker(new Locale("ru"));
        String phone = ("+" + faker.phoneNumber().subscriberNumber(11));
        return phone;
    }

    public static class Registration {
        private Registration() {

        }

        public static UserInfo generateUser(String locale) {
            return new UserInfo(generateCity(locale), generateName(locale), generatePhone(locale));
        }


        @Value

        public static class UserInfo {
            String city;
            String name;
            String phone;
        }
    }
}


