# es
Event sourcing sample based on m-r repo

## Requirements

```
Java 16
Maven
PostgreSQL
```

## DB Configuration

Using docker run the following command to create an empty database.
```
 docker run --name es-db -p 5432:5432 -e POSTGRES_PASSWORD=postgres -e POSTGRES_USER=es-user -e POSTGRES_DB=es-db -d postgres
```
