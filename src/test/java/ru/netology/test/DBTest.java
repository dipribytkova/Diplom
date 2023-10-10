package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.page.*;

import static org.junit.jupiter.api.Assertions.*;
import static ru.netology.data.DBUtils.*;

public class DBTest {

    MainPage mainPage;
    BuyByCardPage buyByCardPage;
    BuyInCreditPage buyInCreditPage;
    DataHelper.CardInfo validCard = DataHelper.getApprovedCard();
    DataHelper.CardInfo invalidCard = DataHelper.getDeclinedCard();
    private final String APPROVED_STATUS = "APPROVED";
    private final String DECLINED_STATUS = "DECLINED";
    private final int tourAmount = 4500000;

    @BeforeEach
    void setUp() {
        mainPage = new MainPage();
    }

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterEach
    void clearAll() {
        clearDBTables();
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Test
    void shouldPayWithApprovedCard() {
        mainPage.getBuyByCardPage();
        buyByCardPage = new BuyByCardPage();
        buyByCardPage.sendData(validCard);
        buyByCardPage.waitingForDecision();
        var paymentRecord = getLastPaymentRecord();
        var orderRecord = getLastOrderRecord();
        assertAll(
                () -> assertEquals(tourAmount, paymentRecord.getAmount()),
                () -> assertEquals(APPROVED_STATUS, paymentRecord.getStatus()),
                () -> assertEquals(paymentRecord.getTransaction_id(), orderRecord.getPayment_id())
        );
    }

    @Test
    void shouldPayWithDeclinedCard() {
        mainPage.getBuyByCardPage();
        buyByCardPage = new BuyByCardPage();
        buyByCardPage.sendData(invalidCard);
        buyByCardPage.waitingForDecision();
        var paymentRecord = getLastPaymentRecord();
        var orderRecord = getLastOrderRecord();
        assertAll(
                () -> assertEquals(tourAmount, paymentRecord.getAmount()),
                () -> assertEquals(DECLINED_STATUS, paymentRecord.getStatus()),
                () -> assertEquals(paymentRecord.getTransaction_id(), orderRecord.getPayment_id())
        );
    }

    @Test
    void shouldGetCreditWithApprovedCard() {
        mainPage.getBuyInCreditPage();
        buyInCreditPage = new BuyInCreditPage();
        buyInCreditPage.sendData(validCard);
        buyInCreditPage.waitingForDecision();
        var creditRecord = getLastCreditRecord();
        var orderRecord = getLastOrderRecord();
        assertAll(
                () -> assertEquals(APPROVED_STATUS, creditRecord.getStatus()),
                () -> assertEquals(creditRecord.getBank_id(), orderRecord.getCredit_id())
        );
    }

    @Test
    void shouldGetCreditWithDeclinedCard() {
        mainPage.getBuyInCreditPage();
        buyInCreditPage = new BuyInCreditPage();
        buyInCreditPage.sendData(invalidCard);
        buyInCreditPage.waitingForDecision();
        var creditRecord = getLastCreditRecord();
        var orderRecord = getLastOrderRecord();
        assertAll(
                () -> assertEquals(DECLINED_STATUS, creditRecord.getStatus()),
                () -> assertEquals(creditRecord.getBank_id(), orderRecord.getCredit_id())
        );
    }
}