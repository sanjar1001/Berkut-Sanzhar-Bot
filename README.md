# Telegram Messaging API 
Java Spring Boot. Регистрация пользователеи и генерация Телеграм токена для связи Телеграм аккаунта

## Технологии
- Java 17
- Spring Boot
- Spring Security
- Spring Data JPA
- Telegram Bots API
- PostgreSQL
- MapStruct
- liquibase
- Postman (тестирование API)

  ## Оснавные Эндпойны
1. Пользователь регистрируется  `/registration`.
2. Авторизуется через `/login/token`, получает `telegramToken`.
3. В Telegram-боте вводит токен — привязывается к chatId.
4. Через `/send/message` можно отправить сообщение в Telegram.
5. Через `/find/all/message` можно получить историю сообщений.

## 🗂️ Структура проекта
- `controller/` — REST контроллеры
- `service/impl/` — бизнес-логика
- `repository/` — работа с базой
- `map/` — MapStruct мапперы
- `exception/` — кастомные исключения
- `entity/` — сущьности 


## Команда проекта

- [Ерсайын Санжар] — +77477210658 - tg: @sanja_r_q

