# Backend интернет-магазина

Выпускной проект в рамках командной работы курса Java-разработчик

### Технологии

* Spring Boot
* Spring Web
* Spring Security
* Spring Data
* MapStruct
* PostgresSQL
* Liquibase
* Docker

### Запуск

Проверка работоспособности с помощью фронтэнда или PostMan.

Адрес фронтэнда: `http://localhost:3000/`

Адрес бекэнда: `http://localhost:8080/`

Спецификация Swagger [здесь](https://github.com/BizinMitya/front-react-avito/blob/v1.13/openapi.yaml).

Запуск с помощью docker: `docker-compose -p graduate-work up`. Файл docker-compose.yml:

```
version: '4'
services:
  client-frontend:
    image: ghcr.io/bizinmitya/front-react-avito:v1.13
    ports:
      - "3000:3000"
    depends_on:
      - client-backend

  client-backend:
    image: m7only/backend:0.0.1
    build:
      context: .
      dockerfile: Dockerfile
    ports:
      - "8080:8181"
    depends_on:
      - service-db
    environment:
      - SERVER_PORT= 8181
      - SPRING_DATASOURCE_URL=jdbc:postgresql://service-db/graduate_work

  service-db:
    image: postgres:14.7-alpine
    environment:
      POSTGRES_DB: graduate_work
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: root
    ports:
      - "15432:5432"
    volumes:
      - db-data:/var/lib/postgresql/data
    restart: unless-stopped

volumes:
  db-data:
  pgadmin-data:
```

### Исполнители

* Владимир Дюшкин
* Антон Салтанов
* Роман Селин
* Антон Скрягин

