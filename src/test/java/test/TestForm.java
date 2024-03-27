package test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static data.DataGenerator.Registration.getRegisteredUser;
import static data.DataGenerator.Registration.getUser;
import static data.DataGenerator.getRandomLogin;
import static data.DataGenerator.getRandomPassword;


class TestForm {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    void validForm() {
        var registeredUser = getRegisteredUser("active");
        $("[data-test-id='login'] .input__box .input__control").val(registeredUser.getLogin());
        $("[data-test-id='password'] .input__box .input__control").val(registeredUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("h2").shouldHave(Condition.exactText("  Личный кабинет"));
    }
    @Test
    void notRegistered() {
        var notRegisteredUser = getUser("active");
        $("[data-test-id='login'] .input__box .input__control").val(notRegisteredUser.getLogin());
        $("[data-test-id='password'] .input__box .input__control").val(notRegisteredUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification'] .notification__content").shouldHave(Condition.exactText("Ошибка! " + "Неверно указан логин или пароль"));
    }
    @Test
    void blockedUser() {
        var blockedUser = getRegisteredUser("blocked");
        $("[data-test-id='login'] .input__box .input__control").val(blockedUser.getLogin());
        $("[data-test-id='password'] .input__box .input__control").val(blockedUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification'] .notification__content").shouldHave(Condition.exactText("Ошибка! " + "Пользователь заблокирован"));
    }
    @Test
    void wrongLogin() {
        var registeredUser = getRegisteredUser("active");
        var wrongLogin = getRandomLogin();
        $("[data-test-id='login'] .input__box .input__control").val(wrongLogin);
        $("[data-test-id='password'] .input__box .input__control").val(registeredUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification'] .notification__content").shouldHave(Condition.exactText("Ошибка! " + "Неверно указан логин или пароль"));

    }
    @Test
    void wrongPassword() {
        var registeredUser = getRegisteredUser("active");
        var wrongPassword = getRandomPassword();
        $("[data-test-id='login'] .input__box .input__control").val(registeredUser.getLogin());
        $("[data-test-id='password'] .input__box .input__control").val(wrongPassword);
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification'] .notification__content").shouldHave(Condition.exactText("Ошибка! " + "Неверно указан логин или пароль"));

    }
    @Test
    void invalidLogin() {
        var registeredUser = getRegisteredUser("active");
        $("[data-test-id='login'] .input__box .input__control").val();
        $("[data-test-id='password'] .input__box .input__control").val(registeredUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='login'].input_invalid .input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }
    @Test
    void invalidPass() {
        var registeredUser = getRegisteredUser("active");
        $("[data-test-id='login'] .input__box .input__control").val(registeredUser.getLogin());
        $("[data-test-id='password'] .input__box .input__control").val();
        $("[data-test-id='action-login']").click();
        $("[data-test-id='password'].input_invalid .input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }
}
