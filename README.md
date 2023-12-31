![main front - reference](img/Kanban-Post.jpg)

# **Task Management System**

API для приложения **Task Management System** (англ. «Система Управления Задачами») поддерживает создание, 
редактирование, удаление и просмотр задач. 
Каждая задача содержит заголовок, описание, статус ("в ожидании", "в процессе", "завершено")
и приоритет ("высокий", "средний", "низкий"), а также автора задачи и исполнителя. 

<details><summary><strong>Фичи</strong></summary>

1. Сервис поддерживает аутентификацию и авторизацию пользователей по email и паролю.
2. Доступ к API аутентифицируется с помощью JWT токена.
3. Пользователи могут управлять своими задачами: создавать новые, редактировать существующие, просматривать и удалять,
   менять статус и назначать исполнителей задачи.
4. Пользователи могут просматривать задачи других пользователей, а исполнители задачи могут менять статус своих задач.
5. К задачам можно оставлять комментарии.
6. API позволяет получать задачи конкретного автора или исполнителя,
   а также все комментарии к ним. Обеспечена фильтрация и пагинация вывода.
7. Сервис корректно обрабатывает ошибки и возвращает понятные сообщения, а также валидирует входящие данные.
8. API описан с помощью Open API и Swagger. В сервисе настроен Swagger UI.
9. Написано несколько базовых тестов для проверки основных функций вашей системы.

</details>

---

### [Pull request](https://github.com/elGordoGato/task-management-system/pull/1)

---

### Стек:
> Java 17, Spring Boot, Spring Security, PostgreSQL, JPA Hibernate, Lombok, Maven, Docker.

---

### Спецификация API
[Документация OpenAPI (swagger)](https://petstore.swagger.io/?url=https://raw.githubusercontent.com/elGordoGato/task-management-system/jwt-with-email/swagger/task-management-system-openapi.yml)

Файл со спецификацией можно найти по пути: `swagger/task-management-system-openapi.yml`

---

### ➡️ Аутентификация и авторизация
Авторизация происходит через валидирование JWT токена, который передается в заголовке `Authorization`
и имеет формат:
<details><summary><strong>JWT token</strong></summary>

      Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJib2JAZ21haWwuY29tIiwiaWF0IjoxNzAyMTE4MjQxLCJleHAiOjE3MDIxNDcwNDF9.TapPa7HHd56WFi63phHUd2VbG43752Vc99Kr9Q3O4qk

</details>

Получение токена происходит из поля `token` в теле ответа на запрос с логином по email и паролю, 
также в поле `expiresIn` приходит время в миллисекундах через которое токен станет недействительным.

--- 

### Тестирование
Для тестов использована библиотека JUnit и Mockito.
Также написана коллекция тестов для **Postman** по пути: `postman/TaskManagementSystem.postman_collection.json` 

---

### Запуск приложения локально

1. Запустить БД (PostgreSQL)
2. <details><summary>Настроить aplication-test.yml</summary>
   <pre>
   spring:
        datasource:
            driverClassName: org.postgresql.Driver <- Драйвер подключения к БД (по умолчанию PostgreSQL)
            url: jdbc:postgresql://localhost:6544/maindb <- адрес для подключения к БД 
            username: root <- Логин для подключения к БД
            password: root <- Пароль для подключения к БД

    </pre>
    </details>
   
3. Находясь в корневой папке проекта вызвать      
   
-      mvn spring-boot:run -Dspring-boot.run.profiles=test
 
---

### Запуск дев среды

Находясь в корневой папке проекта вызвать 

-      mvn clean package -DskipTests=true
 
-      docker compose up

---

### Схема базы данных:
<a href="https://dbdiagram.io/d/Task-Management-System-6576a23756d8064ca0c393ed"><img src="img/db.png"></a>
