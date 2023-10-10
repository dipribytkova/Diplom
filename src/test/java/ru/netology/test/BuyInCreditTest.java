package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.page.BuyInCreditPage;
import ru.netology.page.MainPage;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static ru.netology.data.DataHelper.*;

public class BuyInCreditTest {

    MainPage mainPage;
    BuyInCreditPage buyInCreditPage;
    CardInfo validCard = getApprovedCard();
    CardInfo invalidCard = getDeclinedCard();
    String validNumber = getValidNumber();
    String validMonth = getMonth();
    String validYear = getYear(3);
    String validOwner = getOwner();
    String validCvcCvv = getCvcCvv();

    @BeforeEach
    void setUp() {
        mainPage = new MainPage();
        mainPage.getBuyInCreditPage();
        buyInCreditPage = new BuyInCreditPage();
    }

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Test
    void shouldPayWithApprovedCard() {
        buyInCreditPage.sendData(validCard);
        buyInCreditPage.checkSuccess();
    }

    @CsvFileSource(resources = "/fullFields.csv", numLinesToSkip = 1)
    void shouldPayWithApprovedCardOtherFields(String num, String month, String year, String owner, String cvcCvv) {
        buyInCreditPage.sendData(num,month,year,owner,cvcCvv);
        buyInCreditPage.checkSuccess();
    }

    @Test
    void shouldGetErrorWithDeclinedCard() {
        buyInCreditPage.sendData(invalidCard);
        buyInCreditPage.checkError();
    }

    @Test
    void shouldGetErrorIfEmptyForm() {
        buyInCreditPage.sendData("", "", "", "", "");
        String[] actual = {buyInCreditPage.getTextUnderNumberField(),
                buyInCreditPage.getTextUnderMonthField(),
                buyInCreditPage.getTextUnderYearField(),
                buyInCreditPage.getTextUnderOwnerField(),
                buyInCreditPage.getTextUnderCvcCvcField()};
        String[] expected = {"Поле обязательно для заполнения",
                "Поле обязательно для заполнения",
                "Поле обязательно для заполнения",
                "Поле обязательно для заполнения",
                "Поле обязательно для заполнения"};
        assertArrayEquals(expected, actual);
    }

    @CsvFileSource(resources = "/numberField.csv", numLinesToSkip = 1)
    void shouldGetErrorIfInvalidNumber(String num, String expected) {
        buyInCreditPage.sendData(num,validMonth,validYear,validOwner,validCvcCvv);
        val actual = buyInCreditPage.getTextUnderNumberField();
        assertEquals(expected, actual);
    }

    @CsvFileSource(resources = "/monthField.csv", numLinesToSkip = 1)
    void shouldGetErrorIfInvalidMonth(String month, String expected) {
        buyInCreditPage.sendData(validNumber,month,validYear,validOwner,validCvcCvv);
        val actual = buyInCreditPage.getTextUnderMonthField();
        assertEquals(expected, actual);
    }

    @CsvFileSource(resources = "/yearField.csv", numLinesToSkip = 1)
    void shouldGetErrorIfInvalidYear(String year, String expected) {
        buyInCreditPage.sendData(validNumber,validMonth,year,validOwner,validCvcCvv);
        val actual = buyInCreditPage.getTextUnderYearField();
        assertEquals(expected, actual);
    }

    @CsvFileSource(resources = "/ownerField.csv", numLinesToSkip = 1)
    void shouldGetErrorIfInvalidOwner(String owner, String expected) {
        buyInCreditPage.sendData(validNumber,validMonth,validYear,owner,validCvcCvv);
        val actual = buyInCreditPage.getTextUnderOwnerField();
        assertEquals(expected, actual);
    }

    @CsvFileSource(resources = "/cvcCvvField.csv", numLinesToSkip = 1)
    void shouldGetErrorIfInvalidCvcCvv(String cvcCvv, String expected) {
        buyInCreditPage.sendData(validNumber,validMonth,validYear,validOwner,cvcCvv);
        val actual = buyInCreditPage.getTextUnderCvcCvcField();
        assertEquals(expected, actual);
    }
}