# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview
Spring Boot 3.5.7 interview application managing Activities and Suppliers. Uses Java 21, H2 in-memory database, JPA/Hibernate, Flyway migrations, Lombok, and Thymeleaf.

## Build & Run Commands

### Run application
```bash
./gradlew bootRun
```
Application runs on http://localhost:8080/

### Run tests
```bash
./gradlew test                    # All tests
./gradlew test --tests ClassName  # Specific test class
./gradlew test --tests ClassName.methodName  # Single test method
```

### Build
```bash
./gradlew build      # Compile, test, package
./gradlew clean      # Clean build artifacts
./gradlew bootJar    # Create executable JAR
```

## Architecture

### Layered Structure
Standard Spring Boot layered architecture:
- **Controller** layer: REST endpoints (returns ResponseEntity)
- **Service** layer: Business logic, entity-to-DTO mapping
- **Repository** layer: JPA repositories extending JpaRepository
- **Entity** layer: JPA entities with Lombok annotations
- **DTO** layer: Data transfer objects for API responses

### Database
- **Runtime**: H2 file-based database at `./data/testdb`
- **Schema**: `getyourguide` schema with tables: `activity`, `supplier`
- **Migrations**: Flyway migrations in `src/main/resources/db/migration/`
  - V1.0.1: Creates activities table with seed data
  - V1.0.2: Creates suppliers table with seed data
- **Relationship**: Activity has ManyToOne with Supplier (lazy fetch, @NotFound to ignore missing suppliers)

### Key Patterns
- All entities use Lombok (@Getter, @Setter, @AllArgsConstructor, @NoArgsConstructor, etc.)
- Controllers use constructor injection via @AllArgsConstructor
- Services manually map entities to DTOs (no mapper framework)
- Tests use @SpringBootTest for integration testing with full context

### Testing
- Test helpers in `src/test/java/.../helpers/` (ActivityHelper, SupplierHelper)
- Integration tests use @SpringBootTest with @Autowired dependencies
- JUnit 5 (Jupiter) with Spring Boot Test

## Package Structure
All code under `com.getourguide.interview`:
- `controller/` - REST controllers
- `service/` - Business logic services
- `repository/` - JPA repositories
- `entity/` - JPA entities (Activity, Supplier)
- `dto/` - DTOs (ActivityDto)
- `error/` - Error handlers

## Development Notes
- JDK 21 required (configured via Gradle toolchain)
- Lombok annotations reduce boilerplate - getters/setters/constructors auto-generated
- H2 console accessible if enabled in application.properties
- Spring DevTools included for hot reload during development

# Implementation
- Always use Clean Code Principles
- Use JPA entities and DTOs for data transfer
- Be pragmatic and avoid unnecessary complexity
- Prefer TDD whenever possible
