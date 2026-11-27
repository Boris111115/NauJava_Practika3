# Исправления по замечаниям к практической работе №4

1. Добавлена ER-диаграмма пяти сущностей в `docs/ER-diagram.md` (Mermaid).
2. В `PatientTransferRepository` добавлен составной Derived Query
   `findByStatusAndTransferTimeMinutesBetween`, использующий `And` и `Between`.
3. В Criteria API добавлен аналогичный запрос по `status` и диапазону
   `transferTimeMinutes` через `CriteriaBuilder.and()` и `CriteriaBuilder.between()`.
   Добавлен автотест с проверкой совпадающего статуса и попадания/непопадания в диапазон.
4. Criteria API вынесен в отдельный пакет `dao.criteria`: для каждой сущности
   созданы интерфейс `XxxCriteriaRepository` и реализация
   `XxxCriteriaRepositoryImpl`.
5. Тест транзакционного сервиса больше не обёрнут общей тестовой транзакцией.
   Добавлен сценарий, в котором больница сохраняется, а сохранение передачи
   завершается ошибкой из-за `status = null`; проверяется отсутствие обеих
   записей после отката.
6. Удалены старые коллекционные DAO и избыточная конфигурация
   `@EnableJpaRepositories`.

Проверка: выполните `mvn test` в среде с установленными Maven и PostgreSQL,
доступной по настройкам `application.properties`. В текущей среде Maven
не установлен, поэтому автоматический запуск тестов здесь выполнить не удалось.
