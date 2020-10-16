package ru.netology;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class ApplicationFormTest {

    SelenideElement form = $(".form");

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void SubmitValidName() {
        form.$("[data-test-id=name] input").setValue("Марк Цукерберг");
        form.$("[data-test-id=phone] input").setValue("+71234567890");
        form.$("[data-test-id=agreement]").click();
        form.$("[type='button']").click();
        $("[data-test-id='order-success']").shouldHave(exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }
    @Test
    void LatinName() {
        form.$("[data-test-id=name] input").setValue("Mark Zuckerberg");
        form.$("[data-test-id=phone] input").setValue("+71234567890");
        form.$("[data-test-id=agreement]").click();
        form.$("[type='button']").click();
        form.$(".input_theme_alfa-on-white.input_invalid .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void SubmitValidNameDash() {
        form.$("[data-test-id=name] input").setValue("Марк Цукерберг-Цукерберг");
        form.$("[data-test-id=phone] input").setValue("+71234567890");
        form.$("[data-test-id=agreement]").click();
        form.$("[type='button']").click();
        $("[data-test-id='order-success']").shouldHave(exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void SymbolName() {
        form.$("[data-test-id=name] input").setValue("***");
        form.$("[data-test-id=phone] input").setValue("+71234567890");
        form.$("[data-test-id=agreement]").click();
        form.$("[type='button']").click();
        form.$(".input_theme_alfa-on-white.input_invalid .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void EmptyPhone() {
        form.$("[data-test-id=name] input").setValue("Марк Цукерберг");
        form.$("[data-test-id=agreement]").click();
        form.$("[type='button']").click();
        form.$(".input_theme_alfa-on-white.input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void WrongPhone() {
        form.$("[data-test-id=name] input").setValue("Марк Цукерберг");
        form.$("[data-test-id=phone] input").setValue("+712345678901234567890");
        form.$("[data-test-id=agreement]").click();
        form.$("[type='button']").click();
        form.$(".input_theme_alfa-on-white.input_invalid .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void CheckboxNotChecked(){
        form.$("[data-test-id=name] input").setValue("Марк Цукерберг");
        form.$("[data-test-id=phone] input").setValue("+71234567890");
        form.$(".button").click();
        form.$(".input_invalid[data-test-id=agreement]").shouldHave(exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй"));
    }

    @Test
    void EmptyForm() {
        form.$("[type='button']").click();
        form.$(".input_theme_alfa-on-white.input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }
}
