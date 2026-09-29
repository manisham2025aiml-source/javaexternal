RETAILSTOCK FRONTEND - HTML/CSS/JAVASCRIPT

This frontend connects to the Spring Boot backend on http://localhost:8080.

FILES
- index.html
- css/style.css
- js/app.js

FEATURES
- Dashboard
- Product CRUD
- Stock IN / OUT
- Movement history
- Low-stock alerts and resolve
- Fast-moving report
- Responsive UI

INTEGRATION OPTION A: SERVE SEPARATELY
1. Keep Spring Boot running with: mvn spring-boot:run
2. Serve this folder with VS Code Live Server, or:
   python -m http.server 5500
3. Open http://localhost:5500
4. If the browser reports CORS errors, add CORS support in Spring Boot for http://localhost:5500.

INTEGRATION OPTION B: SERVE FROM SPRING BOOT
Copy index.html, css/, and js/ into:
src/main/resources/static/

Then change the first line of js/app.js from:
const API="http://localhost:8080/api";
to:
const API="/api";

Restart Spring Boot and open:
http://localhost:8080/

The frontend uses the endpoints already tested in your project:
GET /api/products
POST /api/products
PUT /api/products/{id}
DELETE /api/products/{id}
POST /api/stock/{id}/in
POST /api/stock/{id}/out
GET /api/stock/movements
PUT /api/stock/{id}/reorder-level
GET /api/alerts
PUT /api/alerts/{id}/resolve
GET /api/reports/fast-moving?from=YYYY-MM-DD&to=YYYY-MM-DD
