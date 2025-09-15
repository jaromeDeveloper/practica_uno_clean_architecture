# Clean Architecture CRUD (Java 17 + Spring Boot + H2)

Proyecto de ejemplo siguiendo **Clean Architecture** con 4 capas y CRUD de `Product`.

## Capas y módulos

```
clean-arch-java17/
├── domain/               # Dominio: entidades y puertos (interfaces)
│   └── src/main/java/com/example/domain/...
│       ├── model/Product.java
│       └── ports/ProductRepository.java
├── application/          # Aplicación: DTOs, Commands/Queries, casos de uso
│   └── src/main/java/com/example/application/...
│       ├── dto/ProductDTO.java
│       ├── usecase/CreateProductCommand.java
│       ├── usecase/UpdateProductCommand.java
│       ├── usecase/DeleteProductCommand.java
│       ├── usecase/GetProductQuery.java
│       ├── usecase/ListProductsQuery.java
│       └── service/ProductService.java
├── infrastructure/       # Infraestructura: DB, JPA, adaptadores
│   └── src/main/java/com/example/infrastructure/...
│       ├── jpa/ProductEntity.java
│       ├── jpa/ProductJpaRepository.java
│       └── adapters/ProductRepositoryAdapter.java
└── presentation/         # Presentación: API REST (Spring Boot)
    ├── src/main/java/com/example/presentation/...
    │   ├── ApiApplication.java
    │   ├── config/BeanConfig.java
    │   └── controller/
    │       ├── ProductController.java
    │       └── ApiExceptionHandler.java
    ├── src/main/resources/application.properties
    └── src/test/java/com/example/presentation/ProductApiIT.java
```

## Dirección de dependencias (regla Clean Architecture)

- `application` → depende de → `domain`
- `infrastructure` → depende de → `application` y `domain`
- `presentation` → depende de → `infrastructure` (para wiring) y transitivamente de `application`/`domain`
- **Nunca** `domain` depende de frameworks o de `infrastructure/presentation`

## Requisitos

- Java **17.0.14**
- Maven 3.9+

## Cómo ejecutar

```bash
# En la raíz:
mvn clean install

# Levantar la API
cd presentation
mvn spring-boot:run
```

La API queda en `http://localhost:8080`.

## Endpoints

- `POST /api/products` → crea un producto  
  Body JSON:
  ```json
  {"name":"Laptop","price":25000.00}
  ```
- `GET /api/products` → lista todos
- `GET /api/products/{id}` → obtiene por id
- `PUT /api/products/{id}` → actualiza
- `DELETE /api/products/{id}` → elimina

## Pruebas (GET y POST)

```bash
mvn -pl presentation test
```

O manualmente con cURL:

```bash
# Crear
curl -i -X POST http://localhost:8080/api/products   -H "Content-Type: application/json"   -d '{"name":"Laptop","price":25000.00}'

# Listar
curl -i http://localhost:8080/api/products
```

## Notas de diseño

- **Dominio puro**: sin anotaciones ni framework.
- **Aplicación**: casos de uso que orquestan reglas y devuelven DTOs.
- **Infraestructura**: JPA/H2 como detalle reemplazable.
- **Presentación**: controladores delgados + mapeo de errores a HTTP.
- **Tests**: `ProductApiIT` valida POST y GET con contexto real (H2 in-memory).
