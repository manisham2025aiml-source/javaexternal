# Retail Stock Inventory Recorder & Alert System

Spring Boot + Maven + MySQL project based on the provided problem statement.

## Technology Stack
- Java 17
- Spring Boot 3.5.5
- Maven
- Spring Web
- Spring Data JPA / Hibernate
- MySQL
- MySQL Workbench
- Jakarta Validation

## Main Requirements Covered
1. Track stock in/out movements per product.
2. Configure reorder thresholds per product.
3. Automatically create low-stock/restock alerts.
4. Maintain an audit/history log of stock movements.
5. Generate a fast-moving product report for a selected date range.
6. Exception handling for missing products, duplicate products and insufficient stock.

## Database Setup
1. Open MySQL Workbench.
2. Open `database.sql` and run it.
3. Open `src/main/resources/application.properties`.
4. Replace:
   `YOUR_MYSQL_PASSWORD`
   with your MySQL root password.

The database name is `retail_stock_db`.
Hibernate will create/update the required tables.

## Run using Maven
From the project root:

```bash
mvn clean install
mvn spring-boot:run
```

Or run:

```bash
mvn clean package
java -jar target/retail-stock-inventory-system-1.0.0.jar
```

Server: `http://localhost:8080`

## REST API

### Products
- `GET /api/products`
- `GET /api/products/{id}`
- `POST /api/products`
- `PUT /api/products/{id}`
- `DELETE /api/products/{id}`

Example POST:
```json
{
  "productCode": "P101",
  "name": "Rice Bag",
  "price": 850.00,
  "currentStock": 20,
  "reorderLevel": 5
}
```

### Stock
- `POST /api/stock/{id}/in`
- `POST /api/stock/{id}/out`
- `GET /api/stock/movements`
- `PUT /api/stock/{id}/reorder-level`

Stock request:
```json
{
  "quantity": 5
}
```

### Alerts
- `GET /api/alerts`
- `PUT /api/alerts/{id}/resolve`

### Fast-moving report
`GET /api/reports/fast-moving?from=2026-09-01&to=2026-09-30`

The report calculates fast-moving products from STOCK_OUT quantities.

## Suggested demo flow
1. Add 2-3 products.
2. Perform STOCK_IN.
3. Perform STOCK_OUT.
4. Reduce one product below its reorder level.
5. Open `/api/alerts` to show the automatically generated alert.
6. Open `/api/stock/movements` to show the audit trail.
7. Use `/api/reports/fast-moving` to show products with the highest sales/stock-out volume.

## Architecture
Controller -> Service -> Repository -> MySQL

Entities:
- Product
- StockMovement
- StockAlert

DTOs:
- StockRequest
- ReorderLevelRequest
- FastMovingProduct

Exceptions:
- ResourceNotFoundException
- DuplicateProductException
- InsufficientStockException
