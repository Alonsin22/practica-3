# E-Commerce Gateway - Composite API

Práctica de Desarrollo Web Integral (Unidad 3) - UTCH.
Implementación del patrón **Composite API / API Aggregator** con Spring Boot.

## Descripción

La Composite API expone un único endpoint que consulta en paralelo tres servicios
y unifica sus respuestas en un solo JSON:

| Servicio | Tipo | Endpoint |
|---|---|---|
| Catálogo | Propio | `GET /microservices/products/{id}` |
| Inventario | Propio | `GET /microservices/inventory/{id}` |
| Divisas (Frankfurter) | Terceros | `https://api.frankfurter.app/latest?from=USD&to=MXN` |
| **Composite API** | Propia | `GET /api/v1/product-summary/{id}` |

## Requisitos

- JDK 17 o superior
- Maven (o usar el wrapper `mvnw` incluido)
- Conexión a internet (para la API de divisas)

## Cómo ejecutar

```bash
git clone [URL_DE_TU_REPOSITORIO]
cd ecommerce-gateway
./mvnw spring-boot:run
```
(En Windows: `mvnw.cmd spring-boot:run`)

La aplicación arranca en `http://localhost:8080`.

## Pruebas

| Petición | Resultado |
|---|---|
| `GET /api/v1/product-summary/P-101` | `SUCCESS`, `IN_STOCK`, 15 unidades |
| `GET /api/v1/product-summary/P-102` | `OUT_OF_STOCK`, 0 unidades |
| `GET /api/v1/product-summary/P-999` | `ERROR` (404) |

## Ejemplo de respuesta

```json
{
  "product_id": "P-101",
  "title": "Laptop Gamer XYZ",
  "price_usd": 1200.0,
  "price_mxn": 20400.0,
  "stock_status":