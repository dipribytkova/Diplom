## Подготовка тестового окружения для запуска автотестов.

### Установить приложения

1. Intelij IDEA 2022.3.2 (Community Edition)
2. Docker Desktop
3. Chromedriver Version: 118.0.5993.70
4. Браузер: Chrome Версия 118.0.5993.89 (Официальная сборка), (64 бит)
5. DBeaver 23.2.2

### Процедура запуска тестов

1. Запускаем Docker Desktop
2. Запускаем IDEA
3. В терминале IDEA набираем `docker compose down`
4. Ждем удаления 3 контейнеров и набираем `docker compose up`
5. Ждем запуска контейнеров _node-app_, _mysql_, _postgres_ и во 2ом терминале для  
   запуска джарника набираем `java -jar ./artifacts/aqa-shop.jar`
6. В 3ем терминале запускаем тесты командой `./gradlew clean test --info`
7. Для генерации отчетов на Allure после прохождения тестов набираем в терминале `./gradlew allureserve`
8. По умолчанию в файле _application.properties_ указано подключение к MySQL
   Для замены СУБД на PostgreSQL необходимо заменить строку 3 на  
   spring.datasource.url=jdbc:postgresql://localhost:5432/app и затем также запусить тесты согласно  
   пунктам 3-6
9. Для отключения джарника или остановки контейнеров нажать `ctrl+C`