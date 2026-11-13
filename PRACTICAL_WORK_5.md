# Практическая работа № 5 — Spring Web, REST API

## Выполнено
- Добавлены зависимости Spring Web, Thymeleaf, Spring Data REST, HATEOAS и Springdoc OpenAPI.
- REST-ресурсы опубликованы для репозиториев пациентов, переводов пациентов, больниц, медицинского персонала и бригад скорой помощи.
- Созданы кастомные REST endpoint-ы для методов поиска из репозиториев пациентов и переводов пациентов.
- Добавлена HTML-страница со списком пациентов.
- Добавлена единая обработка исключений REST API в JSON-формате.
- Подключён Swagger UI через Springdoc OpenAPI.

## Адреса после запуска приложения
- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`
- Таблица пациентов: `http://localhost:8080/patients/view`
- REST-ресурсы: `/patients`, `/patient-transfers`, `/hospitals`, `/medical-staff`, `/ambulance-teams`

## Примеры запросов
- `GET /custom/patients/findByName?name=Иванов`
- `GET /custom/patients/findByBloodType?bloodType=A%2B`
- `GET /custom/patients/bornBefore?date=2000-01-01`
- `GET /custom/patient-transfers/findByPatientId?patientId=1`
- `GET /custom/patient-transfers/findByHospitalName?name=Центральная`

## Запуск и проверка
1. Убедитесь, что PostgreSQL запущен, а база `naujava4` и параметры подключения в `src/main/resources/application.properties` соответствуют вашей среде.
2. Выполните `mvn test` и `mvn spring-boot:run`.
3. Откройте Swagger UI и страницу пациентов по адресам выше.
4. Проверьте кастомные GET-запросы через Swagger UI или браузер.
5. Создайте ветку, commit и Pull Request, как требует методичка.

Примечание: проект содержит локальные настройки подключения к PostgreSQL. Успешный запуск зависит от наличия указанной БД и соответствующих учётных данных.
