# es

Event sourcing sample ported from [gregoryyoung/m-r](https://github.com/gregoryyoung/m-r) repository.

## Requirements

```
Java 16
PostgreSQL
```

## DB Configuration

Using docker run the following command to create an empty database.
```
 docker run --name es-db -p 5432:5432 -e POSTGRES_PASSWORD=postgres -e POSTGRES_USER=es-user -e POSTGRES_DB=es-db -d postgres
```

## Swagger endpoint

http://localhost:8080/swagger-ui.html