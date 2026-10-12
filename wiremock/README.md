# WireMock Server

Локальный mock-сервер для RESTful API, используется для демонстрации и разработки без обращения к внешнему сервису.

## Структура

```
wiremock/
├── docker-compose.yml    # Конфигурация Docker Compose
├── mappings/             # Моки HTTP-запросов
│   ├── get-object-1.json
│   ├── get-object-13.json
│   ├── post-object.json
│   ├── put-object.json
│   ├── patch-object.json
│   └── delete-object.json
└── __files/              # Статические файлы (если нужны)
```

## Запуск

1. **Запустить WireMock:**
   ```bash
   cd wiremock
   docker-compose up -d
   ```

2. **Проверить работу:**
   ```bash
   curl http://localhost:8080/objects/1
   ```

3. **Остановить WireMock:**
   ```bash
   docker-compose down
   ```

## Переключение между mock и реальным сервисом

В файле `src/test/resources/application.yml`:

**Для WireMock:**
```yaml
external:
  api:
    url: http://localhost:8080
```

**Для реального сервиса:**
```yaml
external:
  api:
    url: https://api.restful-api.dev
```

## Доступные моки

| Метод | Эндпоинт | Описание |
|-------|----------|----------|
| GET | `/objects/1` | Получить объект с id=1 |
| GET | `/objects/13` | Получить объект с id=13 |
| POST | `/objects` | Создать новый объект |
| PUT | `/objects/{id}` | Полное обновление объекта |
| PATCH | `/objects/{id}` | Частичное обновление объекта |
| DELETE | `/objects/{id}` | Удалить объект |

## Добавление новых моков

1. Создайте JSON-файл в папке `mappings/`
2. Следуйте формату WireMock:
   ```json
   {
     "request": {
       "method": "GET",
       "urlPathPattern": "/objects/1"
     },
     "response": {
       "status": 200,
       "jsonBody": { ... },
       "headers": {
         "Content-Type": "application/json"
       }
     }
   }
   ```
3. Перезапустите WireMock: `docker-compose restart`
