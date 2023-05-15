
package ru.netology.delivery.test;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

class DeliveryTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }
    SelenideElement city = $("[data-test-id=city] input");
    SelenideElement date = $("[data-test-id='date'] input");
    SelenideElement name = $("[data-test-id='name'] input");
    SelenideElement phone = $("[data-test-id='phone'] input");
    SelenideElement checkbox = $("[data-test-id='agreement']");
    SelenideElement bookInput = $(".button");


    @Test
    @DisplayName("Should successful plan and replay meeting")
    void shouldSuccessfulPlanAndReplayMeeting() {
        Configuration.holdBrowserOpen = true;
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 3;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 5;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);

        //назначаем первую дату
        city.setValue(validUser.getCity());
        date.sendKeys(Keys.LEFT_SHIFT, Keys.HOME, Keys.BACK_SPACE);
        date.setValue(firstMeetingDate);
        name.setValue(validUser.getName());
        phone.setValue(validUser.getPhone());
        checkbox.click();
        bookInput.click();
        $("[data-test-id='success-notification'] .notification__content")
                .shouldHave(Condition.text("Встреча успешно запланирована на " + firstMeetingDate));

        //назначаем вторую дату
        date.sendKeys(Keys.LEFT_SHIFT, Keys.HOME, Keys.BACK_SPACE);
        date.setValue(secondMeetingDate);

        bookInput.click();
        $("[data-test-id='replan-notification'] .notification__content")
                .shouldHave(text("У вас уже запланирована встреча на другую дату. Перепланировать?"));
        $x("//div[@data-test-id='replan-notification']//button").click();

        //проверяем
        $("[data-test-id=success-notification] .notification__content")
                .shouldHave(visible, text("Встреча успешно запланирована на " + secondMeetingDate));
    }
}