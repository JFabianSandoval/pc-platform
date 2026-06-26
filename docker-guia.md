# Guía: Configurar MySQL en una Mac institucional prestada (con restricciones)

Esta guía cubre cómo levantar una base de datos MySQL para el proyecto
`pc211834u20221a132` en una Mac prestada donde no se conocen de antemano
los permisos disponibles (administrador, MDM/Jamf, etc.).

El enfoque general: **probar primero las opciones que NO requieren instalar
nada a nivel de sistema**, y solo avanzar a instalación local si es
estrictamente necesario.

---

## Paso 0 — Diagnóstico inicial

Antes de instalar nada, identifica qué herramientas ya están disponibles.

```bash
which docker
which brew
which mysql
```

Según el resultado:

| Resultado | Siguiente paso |
|---|---|
| `docker` devuelve una ruta | Ir a **Opción A (Docker)** |
| `brew` devuelve una ruta, pero no `docker` | Ir a **Opción B (Homebrew)** |
| Ninguno devuelve ruta | Ir a **Opción C (Base de datos remota gratuita)** |

---

## Opción A — MySQL con Docker (recomendada)

Esta opción no toca el sistema operativo de la Mac: todo corre dentro de un
contenedor aislado, y se puede borrar sin dejar rastro.

### A.1. Verificar que Docker esté corriendo

```bash
docker ps
```

Si da error de conexión, abre la app **Docker Desktop** primero y espera a
que el ícono de la barra de menú indique que está listo.

### A.2. Levantar el contenedor de MySQL

```bash
docker run --name mysql-billing \
  -e MYSQL_ROOT_PASSWORD=tupassword \
  -e MYSQL_DATABASE=pc211834u20221a132_db \
  -p 3306:3306 \
  -d mysql:8.0
```

Esto crea:
- Usuario `root` con la contraseña indicada.
- Base de datos `pc211834u20221a132_db` ya creada.
- Puerto `3306` expuesto, igual que una instalación local normal.

### A.3. Verificar que el contenedor está corriendo

```bash
docker ps
```

Deberías ver `mysql-billing` en estado `Up`.

### A.4. Ver logs si algo falla

```bash
docker logs mysql-billing
```

### A.5. Detener / eliminar el contenedor cuando termines de usar la Mac

```bash
docker stop mysql-billing
docker rm mysql-billing
```

Esto borra el contenedor por completo, sin dejar nada instalado en la Mac.

### A.6. Conexión desde `application-dev.properties`

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/pc211834u20221a132_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=tupassword
```

---

## Opción B — MySQL con Homebrew

Usar solo si Docker no está disponible. Homebrew instala en el directorio
del usuario (no requiere ser administrador del sistema), pero algunas
políticas de MDM pueden bloquear igualmente la ejecución de instaladores.

### B.1. Confirmar que Homebrew funciona

```bash
brew --version
```

### B.2. Instalar MySQL

```bash
brew install mysql
```

### B.3. Iniciar el servicio

```bash
brew services start mysql
```

### B.4. Verificar que está corriendo

```bash
brew services list
```

Busca `mysql` con estado `started`.

### B.5. Definir la contraseña de root (primera vez)

```bash
mysql_secure_installation
```

Sigue las instrucciones en pantalla para establecer la contraseña de
`root` y las opciones de seguridad recomendadas.

### B.6. Conectarse para verificar

```bash
mysql -u root -p
```

### B.7. Crear la base de datos manualmente (opcional)

Si no usas `createDatabaseIfNotExist=true` en la URL de conexión:

```sql
CREATE DATABASE pc211834u20221a132_db;
```

### B.8. Detener el servicio cuando termines

```bash
brew services stop mysql
```

### B.9. Desinstalar antes de devolver la Mac (opcional, buena práctica)

```bash
brew services stop mysql
brew uninstall mysql
```

---

## Opción C — Base de datos MySQL remota gratuita

Usar si ni Docker ni Homebrew están disponibles por restricciones del MDM.
No requiere instalar ni ejecutar nada local — solo necesitas conexión a
internet.

### C.1. Crear una cuenta en un proveedor gratuito

Algunas opciones conocidas (verificar disponibilidad y vigencia del plan
gratuito al momento de usarlo):
- [Aiven](https://aiven.io/) (incluye plan gratuito con MySQL)
- [Railway](https://railway.app/) (incluye crédito gratuito mensual)
- [FreeSQLDatabase](https://www.freesqldatabase.com/)

### C.2. Crear una instancia de MySQL

Sigue el flujo del proveedor elegido para crear una base de datos MySQL
nueva. Al finalizar, el proveedor entrega:
- Host (ej. `mysql-xxxx.aivencloud.com`)
- Puerto (no siempre es `3306`, revisa el valor exacto)
- Usuario
- Contraseña
- Nombre de la base de datos

### C.3. Conexión desde `application-dev.properties`

Reemplaza los valores entre `< >` con los datos reales que te dio el
proveedor:

```properties
spring.datasource.url=jdbc:mysql://<HOST>:<PUERTO>/<NOMBRE_BD>?useSSL=true&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=<USUARIO>
spring.datasource.password=<CONTRASEÑA>
```

> **Nota:** los proveedores remotos casi siempre requieren `useSSL=true`,
> a diferencia de una instalación local donde se suele usar `useSSL=false`.

### C.4. Verificar la conexión con MySQL Workbench (opcional)

En Workbench, crea una nueva conexión usando el mismo host/puerto/usuario/
contraseña, para confirmar que la base responde antes de correr la app.

---

## Después de configurar cualquiera de las 3 opciones

Con la base de datos lista (por cualquiera de las 3 vías), corre la
aplicación normalmente:

```bash
chmod +x mvnw
./mvnw spring-boot:run
```

Si todo conecta bien, Hibernate va a crear automáticamente las tablas
(gracias a `spring.jpa.hibernate.ddl-auto=update`), incluyendo la tabla
`invoices` del bounded context `billing`.

---

## Resumen rápido de decisión

```
¿Docker disponible?
 ├── Sí  → Opción A (recomendada, no toca el sistema)
 └── No
     ├── ¿Homebrew disponible y sin bloqueos de MDM?
     │    ├── Sí → Opción B
     │    └── No → Opción C (base de datos remota gratuita)
```