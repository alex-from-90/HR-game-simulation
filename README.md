## Проект игры симулятора собеседования
### Запуск из IntelliJ IDEA:
#### Бэкенд:
- `mvn clean install`
- `mvn spring-boot:run`
  или просто запустите `CrudApplication`

#### Фронтенд:
- Откройте терминал
- Перейдите в директорию `frontend`
- `npm install` (при первом запуске)
- `npm run serve`

### Запуск с помощью Docker:
- `docker-compose up`
  или
- Запустите `docker-compose.yml`
- После монтирования образов перейдите по адресу http://localhost:8081/
## Описание:
Приложение состоит из двух основных частей: Игры и Административной секции.

Через административную секцию можно создавать темы -> подтемы -> вопросы к темам.
*(Поле Примечание: необязателен)*

№№ Настройки
Пин код для входа в панель администратора 0000
- Эндпоинты бэкенда в backend  src/main/java/ru/simulator/hr/controller
- Эндпоинты frontend в frontend/src/service
- Настройки Postgres src/main/resources/application.properties
  Создайте БД hr_simulator или любую свою (изменив настройки)



