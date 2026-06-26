# Verificación de Endpoints — Proyecto Billing (Invoice)

Guía paso a paso para confirmar que los endpoints ya implementados funcionan
correctamente.

---

## Paso 1 — Confirmar que MySQL (Docker) está corriendo

```bash
docker ps
```

Si el contenedor no aparece como `Up`, inícialo:

```bash
docker start mysql-billing
```

---

## Paso 2 — Revisar la configuración de conexión a la base de datos

```bash
cat src/main/resources/application-dev.properties
```

Verifica que `url`, `username` y `password` coincidan con los datos del
contenedor Docker.

---

## Paso 3 — Compilar el proyecto antes de correrlo

```bash
chmod +x mvnw
./mvnw clean install -DskipTests
```

Si aparece `BUILD FAILURE`, detente aquí y revisa el error antes de continuar.

---

## Paso 4 — Ejecutar la aplicación

```bash
./mvnw spring-boot:run
```

Deja esta terminal abierta y corriendo. Espera a ver en consola:

```
Tomcat started on port 8080
```

---

## Paso 5 — Verificar que el servidor responde (en una segunda terminal)

```bash
curl http://localhost:8080/api/v1/invoices
```

Respuesta esperada: `[]` (lista vacía) con código HTTP 200.

---

## Paso 6 — Abrir Swagger UI en el navegador

```
http://localhost:8080/swagger-ui/index.html
```

Si no carga, probar también:

```
http://localhost:8080/swagger-ui.html
```

---

## Paso 7 — Probar el endpoint POST desde Swagger

1. Desplegar `POST /api/v1/invoices`.
2. Clic en **"Try it out"**.
3. Revisar/ajustar el JSON de ejemplo pre-cargado.
4. Clic en **"Execute"**.
5. Confirmar código de respuesta `201` y el JSON del invoice creado con su `id`.

---

## Paso 8 — Alternativa: probar el POST desde consola con curl

```bash
curl -X POST http://localhost:8080/api/v1/invoices \
  -H "Content-Type: application/json" \
  -d '{
    "invoiceIdentifier": "3f29c6b2-8b1a-4c2d-9a3e-7d9f6a2b1c44",
    "customerId": 1,
    "billingStartDate": "2026-06-01",
    "billingEndDate": "2026-06-30",
    "totalAmountValue": 199.90,
    "totalAmountCurrency": "PEN",
    "status": "PENDING",
    "memo": "Test invoice"
  }'
```

---

## Paso 9 — Verificar en MySQL que el registro se guardó

En MySQL Workbench (conectado al contenedor Docker), ejecutar:

```sql
SELECT * FROM invoices;
```

Debe aparecer la fila recién creada en el Paso 7 u 8.

---

## Paso 10 — Detener la aplicación cuando termines

En la terminal donde corre la app (Paso 4):

```
Ctrl + C
```

## Paso 11 — Detener el contenedor de MySQL (opcional, si terminaste de probar)

```bash
docker stop mysql-billing
```