# Caso VendorFlow — Extracción del Enunciado

Aggregate root:
- VendorProfile

Atributos:
- id 			(Long, obligatorio, autogenerado, primarykey)
- vendorIdentifier 	(VendorIdentifier, obligatorio, UUID)
- supplierId 		(SupplierId, obligatorio, UUID)
- period 		(AgreementPeriod, obligatorio)
- totalValue 		(CreditAmount, obligatorio)
- status 		(VendorStatus, obligatorio)
- description 		(String, opcional)

Bounded Context:
- vendor-management (VendorProfile y demás core business)
- shared (AgreementPeriod y CreditAmount)

Endpoint asignado:
- /api/v1/vendor-profiles
- Operación única: POST

---

## Value Objects

VendorIdentifier
- value: UUID
- identifica de forma única al documento VendorProfile
- generado en otro bounded context

SupplierId
- value: UUID
- se origina en otro bounded context

AgreementPeriod (bounded context: shared)
- startDate: LocalDate
- endDate: LocalDate
- regla: si endDate <= startDate → excepción de argumento ilegal, con texto explicativo
- método: boolean isExpired(LocalDate checkDate) → true si checkDate es posterior a endDate

CreditAmount (bounded context: shared)
- value: (numérico)
- currency: (texto)
- regla: value debe ser >= BigDecimal.ZERO, de otro modo excepción de argumento inválido con texto explicativo
- regla: currency no debe ser null ni blank, de otro modo excepción de argumento inválido con texto explicativo
- método: multiplicar value por un factor, retornando un CreditAmount resultante con la misma moneda

VendorStatus (enum)
- DRAFT (valor por defecto si no se especifica)
- ACTIVE
- TERMINATED

---

## Reglas de negocio adicionales

- No se permite registrar dos VendorProfile con el mismo vendorIdentifier
- VendorProfile es auditable (fechas de creación y última actualización)

---

## Forma del response del API

Para cada VendorProfile incluir:
- id
- vendorIdentifier (como String)
- supplierId (como String)
- periodStartDate (como String)
- periodEndDate (como String)
- contractValueAmount (como BigDecimal)
- contractValueCurrency (como String)
- status (como String)
- description (como String)

---

## Tech Stack solicitado (Technical Constraints)

- Java 26
- Spring Boot Framework 4.1.0
- Spring Data JPA (ORM)
- Base de datos: MySQL, esquema vendorflow
- Nombre del proyecto: pc2<nrc>u<código-estudiante>
- Paquete raíz: com.vendorflow.platform.u<código-estudiante>
- Puerto: 8080
- Arquitectura: DDD, layered architecture (domain, application, interfaces, infrastructure), CQRS, patrones Resource/Assembler
- Naming: inglés, Upper-Camel-Case (clases), Lower-Camel-Case (atributos/métodos), snake_case (BD), tablas en plural
- shared debe seguir las especificaciones del shared de Learning Center Platform (proyecto de ejemplo revisado en clase)
- Lombok permitido
- Documentación JavaDoc en inglés (incluye @author)
- Swagger/OpenAPI en inglés
- Gestión centralizada de excepciones
- README.md en inglés

Fuera de alcance:
- CORS
- Security
- Testing