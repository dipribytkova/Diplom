package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

public class BuyInCreditPage {

    private final SelenideElement numberField = $(byText("Номер карты")).parent().$(".input__control");
    private final SelenideElement monthField = $(byText("Месяц")).parent().$(".input__control");
    private final SelenideElement yearField = $(byText("Год")).parent().$(".input__control");
    private final SelenideElement ownerField = $(byText("Владелец")).parent().$(".input__control");
    private final SelenideElement cvcCvvField = $(byText("CVC/CVV")).parent().$(".input__control");
    private final SelenideElement continueButton = $$(".button__content").find(exactText("Продолжить"));
    private final SelenideElement success = $$(".notification__title").find(text("Успешно"));
    private final SelenideElement error = $$(".notification__title").find(text("Ошибка"));
    private final SelenideElement numberUnderField = $(byText("Номер карты")).parent().$(".input__sub");
    private final SelenideElement monthUnderField = $(byText("Месяц")).parent().$(".input__sub");
    private final SelenideElement yearUnderField = $(byText("Год")).parent().$(".input__sub");
    private final SelenideElement ownerUnderField = $(byText("Владелец")).parent().$(".input__sub");
    private final SelenideElement cvcCvcUnderField = $(byText("CVC/CVV")).parent().$(".input__sub");
    private final SelenideElement requestMessage = $(byText("Отправляем запрос в Банк"));
    private final SelenideElement notification = $(".notification_stick-to_right");

    public void waitingForDecision(){
        requestMessage.waitUntil(hidden, 15000);
        notification.shouldBe(visible);
    }

    public BuyInCreditPage() {
        SelenideElement heading = $$("h3").find(text("Кредит по данным карты"));
        heading.shouldBe(visible);
    }

    public BuyInCreditPage sendData(String number, String month, String year, String owner, String cvcCvv) {
        numberField.setValue(number);
        monthField.setValue(month);
        yearField.setValue(year);
        ownerField.setValue(owner);
        cvcCvvField.setValue(cvcCvv);
        continueButton.click();
        return new BuyInCreditPage();
    }

    public BuyInCreditPage sendData(DataHelper.CardInfo cardInfo) {
        numberField.setValue(cardInfo.getNumber());
        monthField.setValue(cardInfo.getMonth());
        yearField.setValue(cardInfo.getYear());
        ownerField.setValue(cardInfo.getOwner());
        cvcCvvField.setValue(cardInfo.getCvcCvv());
        continueButton.click();
        return new BuyInCreditPage();
    }

    public void checkSuccess() {
        success.waitUntil(visible, 15000);
        error.shouldBe(hidden);
    }

    public void checkError() {
        error.waitUntil(visible, 15000);
        success.shouldBe(hidden);
    }

    public String getTextUnderNumberField() {
        return numberUnderField.innerText();
    }

    public String getTextUnderMonthField() {
        return monthUnderField.innerText();
    }

    public String getTextUnderYearField() {
        return yearUnderField.innerText();
    }

    public String getTextUnderOwnerField() {
        return ownerUnderField.innerText();
    }

    public String getTextUnderCvcCvcField() {
        return cvcCvcUnderField.innerText();
    }
}