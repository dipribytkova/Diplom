package ru.netology.data;

import com.codeborne.selenide.SelenideElement;
import lombok.*;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import ru.netology.data.models.*;

import java.sql.*;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class DBUtils {

    private static Connection conn;
    private static final String url = System.getProperty("db.url");
    private static final String user = System.getProperty("db.login");
    private static final String password = System.getProperty("db.pass");

    private static final String creditSQLQuery = "SELECT * FROM credit_request_entity WHERE created IN (SELECT max(created) " +
            "FROM credit_request_entity);";
    private static final String orderSQLQuery = "SELECT * FROM order_entity WHERE created IN (SELECT max(created) " +
            "FROM order_entity);";
    private static final String paymentSQLQuery = "SELECT * FROM payment_entity WHERE created IN (SELECT max(created) " +
            "FROM payment_entity);";

    @SneakyThrows
    private static Connection getConnection() {
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    @SneakyThrows
    public static void clearDBTables() {
        val runner = new QueryRunner();
        runner.update(getConnection(), "DELETE FROM credit_request_entity;");
        runner.update(getConnection(), "DELETE FROM payment_entity;");
        runner.update(getConnection(), "DELETE FROM order_entity;");
    }

    @SneakyThrows
    public static PaymentEntity getLastPaymentRecord() {
        val runner = new QueryRunner();
        return runner.query(getConnection(), paymentSQLQuery, new BeanHandler<>(PaymentEntity.class));
    }

    @SneakyThrows
    public static CreditEntity getLastCreditRecord() {
        val runner = new QueryRunner();
        return runner.query(getConnection(), creditSQLQuery, new BeanHandler<>(CreditEntity.class));
    }

    @SneakyThrows
    public static OrderEntity getLastOrderRecord() {
        val runner = new QueryRunner();
        return runner.query(getConnection(), orderSQLQuery, new BeanHandler<>(OrderEntity.class));
    }
}