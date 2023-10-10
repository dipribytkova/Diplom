package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import ru.netology.page.BuyByCardPage;
import ru.netology.page.MainPage;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static ru.netology.data.DataHelper.*;

public class BuyByCardTest {

    MainPage mainPage;
    BuyByCardPage buyByCardPage;
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
        mainPage.getBuyByCardPage();
        buyByCardPage = new BuyByCardPage();
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
        buyByCardPage.sendData(validCard);
        buyByCardPage.checkSuccess();
    }

    @CsvFileSource(resources = "/fullFields.csv", numLinesToSkip = 1)
    void shouldPayWithApprovedCardOtherFields(String num, String month, String year, String owner, String cvcCvv) {
        buyByCardPage.sendData(num,month,year,owner,cvcCvv);
        buyByCardPage.checkSuccess();
    }

    @Test
    void shouldGetErrorWithDeclinedCard() {
        buyByCardPage.sendData(invalidCard);
        buyByCardPage.checkError();
    }

    @Test
    void shouldGetErrorIfEmptyForm() {
        buyByCardPage.sendData("", "", "", "", "");
        String[] actual = {buyByCardPage.getTextUnderNumberField(),
                buyByCardPage.getTextUnderMonthField(),
                buyByCardPage.getTextUnderYearField(),
                buyByCardPage.getTextUnderOwnerField(),
                buyByCardPage.getTextUnderCvcCvcField()};
        String[] expected = {"Поле обязательно для заполнения",
                "Поле обязательно для заполнения",
                "Поле обязательно для заполнения",
                "Поле обязательно для заполнения",
                "Поле обязательно для заполнения"};
        assertArrayEquals(expected, actual);
    }

    @CsvFileSource(resources = "/numberField.csv", numLinesToSkip = 1)
    void shouldGetErrorIfInvalidNumber(String num, String expected) {
        buyByCardPage.sendData(num,validMonth,validYear,validOwner,validCvcCvv);
        val actual = buyByCardPage.getTextUnderNumberField();
        assertEquals(expected, actual);
    }

    @CsvFileSource(resources = "/monthField.csv", numLinesToSkip = 1)
    void shouldGetErrorIfInvalidMonth(String month, String expected) {
        buyByCardPage.sendData(validNumber,month,validYear,validOwner,validCvcCvv);
        val actual = buyByCardPage.getTextUnderMonthField();
        assertEquals(expected, actual);
    }

    @CsvFileSource(resources = "/yearField.csv", numLinesToSkip = 1)
    void shouldGetErrorIfInvalidYear(String year, String expected) {
        buyByCardPage.sendData(validNumber,validMonth,year,validOwner,validCvcCvv);
        val actual = buyByCardPage.getTextUnderYearField();
        assertEquals(expected, actual);
    }

    @CsvFileSource(resources = "/ownerField.csv", numLinesToSkip = 1)
    void shouldGetErrorIfInvalidOwner(String owner, String expected) {
        buyByCardPage.sendData(validNumber,validMonth,validYear,owner,validCvcCvv);
        val actual = buyByCardPage.getTextUnderOwnerField();
        assertEquals(expected, actual);
    }

    @CsvFileSource(resources = "/cvcCvvField.csv", numLinesToSkip = 1)
    void shouldGetErrorIfInvalidCvcCvv(String cvcCvv, String expected) {
        buyByCardPage.sendData(validNumber,validMonth,validYear,validOwner,cvcCvv);
        val actual = buyByCardPage.getTextUnderCvcCvcField();
        assertEquals(expected, actual);
    }
}