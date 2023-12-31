# Дипломный проект профессии «Тестировщик»

Дипломный проект представляет собой автоматизацию тестирования комплексного сервиса, взаимодействующего с СУБД и API Банка.

**Документация**
* [Описание приложения](#описание-приложения)
* [Задача](#задача)
* [I. Планирование](#i-планирование)
* [II. Автоматизация](#ii-автоматизация)
* [III. Отчетные документы по итогам автоматизированного тестирования](#iii-отчетные-документы-по-итогам-автоматизированного-тестирования)
* [IV. Отчетные документы по итогам автоматизации](#iv-отчетные-документы-по-итогам-автоматизации)

## Описание приложения

Приложение представляет из себя веб-сервис.

![](documentation/pic/service.png)

Приложение предлагает купить тур по определённой цене с помощью двух способов:
1. Обычная оплата по дебетовой карте
1. Уникальная технология: выдача кредита по данным банковской карты

Само приложение не обрабатывает данные по картам, а пересылает их банковским сервисам:
* сервису платежей
* кредитному сервису

Заявлена поддержка двух СУБД:
* MySQL
* PostgreSQL

## Задача

Ключевая задача — автоматизировать сценарии (как позитивные, так и негативные) покупки тура.

Задача разложена на 4 этапа:
1. Планирование автоматизации тестирования
1. Непосредственно сама автоматизация
1. Подготовка отчётных документов по итогам автоматизированного тестирования
1. Подготовка отчётных документов по итогам автоматизации

## I. Планирование

Тест-план представлен в отдельном файле [`Plan.md`](documentation/Plan.md).

## II. Автоматизация

* ### Предварительная подготовка к тестированию

    * Убедиться в присутствии необходимого ПО. Список ПО и инструкции по его установке можно найти [здесь](documentation/Preparation.md).
    * Скачать проект ZIP-файлом по [ссылке](https://github.com/ks1109b/DiplomaProject/archive/refs/heads/main.zip).
    * Распаковать скаченный файл в удобном месте.
    * Открыть проект в IntelliJ IDEA.
       ```
       File -> Open... -> Выделить папку, в которой лежит файл README.md -> OK
       ```

* ### Запуск тестов

  Для дальнейшей работы необходимо запустить контейнеры следующей командой:
   ```  
   docker-compose up -d
   ```
    * #### Для работы с MySQL

        * Запуск SUT:
          ```
          java -Dspring.datasource.url=jdbc:mysql://localhost:3306/app -jar artifacts/aqa-shop.jar
          ```
        * Запуск тестов осуществить в новом окне терминала:
          ```
          gradlew test -Ddb.url=jdbc:mysql://localhost:3306/app allureReport
          ```

    * #### Для работы с Postgres

        * Запуск SUT
          ```
          java -Dspring.datasource.url=jdbc:postgresql://localhost:5432/app -jar artifacts/aqa-shop.jar
          ```
        * Запуск тестов осуществить в новом окне терминала:
          ```
          gradlew test -Ddb.url=jdbc:postgresql://localhost:5432/app allureReport
          ```

* ### Просмотр отчета Allure

  Для открытия отчета в браузере выполнить команду:
   ```  
   gradlew allureServe
   ```

* ### Остановка контейнеров

  Чтобы остановить работу контейнеров, выполните следующую команду:
   ```  
   docker-compose down
   ```

## III. Отчетные документы по итогам автоматизированного тестирования

Отчет по итогам автоматизированного тестирования представлен в отдельном файле [`Report.md`](documentation/Report.md).

## IV. Отчетные документы по итогам автоматизации

Отчет по итогам автоматизации представлен в отдельном файле [`Summary.md`](documentation/Summary.md).