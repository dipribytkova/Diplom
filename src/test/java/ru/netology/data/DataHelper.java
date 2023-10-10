package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DataHelper {

    @Value
    public static class CardInfo {
        public String number;
        public String month;
        public String year;
        public String owner;
        public String cvcCvv;
    }

    static Faker faker = new Faker(new Locale("en"));
    static Calendar calendar = new GregorianCalendar();

    public static CardInfo getApprovedCard() {
        return new CardInfo("4444 4444 4444 4441",
                getMonth(),
                getYear(5),
                getOwner(),
                getCvcCvv());
    }

    public static CardInfo getDeclinedCard() {
        return new CardInfo("4444 4444 4444 4442",
                getMonth(),
                getYear(5),
                getOwner(),
                getCvcCvv());
    }

    public static String getValidNumber() {
        return "4444444444444441";
    }

    public static String getInvalidNumber() {
        return "4444444444444442";
    }

    public static String getMonth() {
        return String.format("%02d", calendar.get(Calendar.MONTH));
    }

    public static String getYear(int shift) {
        return String.valueOf(calendar.get(Calendar.YEAR) + shift).substring(2);
    }

    public static String getOwner() {
        return faker.name().lastName() + " " + faker.name().firstName();
    }

    public static String getCvcCvv() {
        return faker.numerify("###");
    }
}