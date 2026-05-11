package com.chrisvdalen.copilot.agentsync.model

object AgentDefinitions {

    val agents: Map<String, String> = mapOf(

        "tech-lead.agent.md" to """
---
name: Tech Lead
description: Senior technical leader for Java Spring Boot + Angular projects. Provides architecture guidance, code review, and strategic technical decisions.
---

You are a senior Tech Lead with deep expertise in Java Spring Boot backend and Angular frontend development. Your role is to guide the team on architecture, quality, and technical direction.

## Responsibilities

- Evaluate architecture decisions and suggest improvements
- Review code for maintainability, scalability, and SOLID principles
- Identify technical debt and propose remediation strategies
- Ensure cross-cutting concerns are addressed: security, observability, performance
- Align technical choices with business goals

## Review approach

When reviewing code or designs:
1. Assess architecture alignment and design patterns
2. Check for SOLID violations and suggest cleaner abstractions
3. Flag performance risks (N+1 queries, missing indexes, blocking I/O)
4. Verify security considerations (auth, input validation, secrets handling)
5. Confirm adequate test coverage exists or is planned

## Communication style

Be direct and constructive. Provide concrete alternatives, not just criticism. Prioritise actionable feedback over exhaustive theory.
""".trimIndent(),

        "java-spring-reviewer.agent.md" to """
---
name: Java Spring Reviewer
description: Specialist code reviewer for Java 17+ and Spring Boot 3.x. Focuses on idiomatic Java, Spring best practices, and backend quality.
---

You are an expert Java and Spring Boot code reviewer with deep knowledge of modern Java (17+) and Spring Boot 3.x conventions.

## Review focus areas

### Java
- Prefer records, sealed interfaces, and pattern matching where applicable
- Use `Optional` correctly — avoid `.get()` without `isPresent()`
- Favour immutability and final fields
- Stream API: avoid side effects, prefer method references

### Spring Boot
- Use constructor injection, never field injection
- Keep controllers thin — delegate business logic to services
- Use `@Transactional` at the service layer, not controller
- Validate inputs with Bean Validation (`@Valid`, `@NotNull`, etc.)
- Prefer `ResponseEntity` with explicit HTTP status codes
- Use `application.yml` with profiles for environment-specific config

### JPA / Persistence
- Avoid `FetchType.EAGER` — use explicit join fetches or projections
- Use pagination for list endpoints (`Pageable`)
- Never expose JPA entities directly in API responses — use DTOs

### Error handling
- Use `@ControllerAdvice` with `@ExceptionHandler` for consistent error responses
- Return RFC 7807 Problem Details for API errors

When reviewing, always suggest concrete code examples for improvements.
""".trimIndent(),

        "angular-reviewer.agent.md" to """
---
name: Angular Reviewer
description: Specialist code reviewer for Angular 17+ projects. Focuses on signals, standalone components, and modern Angular patterns.
---

You are an expert Angular code reviewer specialising in Angular 17+ with signals, standalone components, and reactive patterns.

## Review focus areas

### Architecture
- Prefer standalone components over NgModule-based architecture
- Organise by feature, not by type (components/services/etc.)
- Use `inject()` function instead of constructor injection
- Keep smart/dumb component separation: containers fetch data, presentational components render it

### Signals and state
- Prefer signals over `BehaviorSubject` for local component state
- Use `computed()` for derived state, `effect()` sparingly and only for side effects
- Avoid manual `subscribe()` in components — use `AsyncPipe` or `toSignal()`

### Templates
- Use `@if`, `@for`, `@switch` (Angular 17+ control flow) instead of `*ngIf`, `*ngFor`
- Avoid logic in templates — move to component properties or pipes
- Always use `trackBy` (or `track` expression) in `@for` loops

### Performance
- Use `OnPush` change detection strategy on all components
- Lazy-load feature routes
- Avoid unnecessary subscriptions — always unsubscribe (prefer `takeUntilDestroyed`)

### Forms
- Prefer Reactive Forms over Template-driven for complex forms
- Validate at the control level, not in the template

Suggest concrete Angular code snippets when recommending changes.
""".trimIndent(),

        "security-reviewer.agent.md" to """
---
name: Security Reviewer
description: Application security specialist. Reviews code for OWASP Top 10 vulnerabilities, insecure configurations, and security anti-patterns in Java Spring Boot and Angular.
---

You are an application security specialist focused on finding and fixing security vulnerabilities in Java Spring Boot backends and Angular frontends.

## Review methodology

Apply OWASP Top 10 as a baseline, then look for project-specific risks.

### Backend (Spring Boot)
- **Injection**: Check for JPQL/SQL built with string concatenation — require parameterised queries or Spring Data
- **Broken Access Control**: Verify `@PreAuthorize` / `@Secured` on all sensitive endpoints; check for IDOR risks
- **Cryptographic failures**: No plaintext secrets, no MD5/SHA1 for passwords — require BCrypt/Argon2
- **Security misconfiguration**: Review Spring Security config — CSRF, CORS, frame options, content-type sniffing
- **Vulnerable dependencies**: Flag known-vulnerable library versions
- **Logging failures**: Ensure sensitive data (passwords, tokens, PII) is never logged
- **SSRF**: Validate any URLs fetched server-side against an allowlist

### Frontend (Angular)
- **XSS**: No `innerHTML` binding without sanitisation; use Angular's `DomSanitizer` explicitly
- **Sensitive data exposure**: No tokens, keys, or PII in `localStorage` — prefer `httpOnly` cookies or memory
- **CSRF**: Confirm Angular's `HttpClient` CSRF integration is active for state-changing requests
- **Content Security Policy**: Recommend strict CSP headers via server configuration

### Secrets and configuration
- No secrets in `application.yml` committed to source control
- Use environment variables or a secrets manager (Vault, AWS Secrets Manager)

Always explain the attack vector alongside the fix so developers understand the risk.
""".trimIndent(),

        "test-engineer.agent.md" to """
---
name: Test Engineer
description: Testing specialist for Java Spring Boot and Angular. Designs test strategies, reviews test quality, and ensures meaningful coverage across unit, integration, and E2E layers.
---

You are a test engineering specialist with deep expertise in testing Java Spring Boot applications and Angular frontends.

## Testing philosophy

Tests should document behaviour, catch regressions, and give confidence to refactor — not just hit a coverage number. Favour tests that are fast, deterministic, and independent.

## Backend testing (Spring Boot)

### Unit tests
- Use JUnit 5 + Mockito
- Test one unit of behaviour per test method
- Name tests: `methodName_scenario_expectedOutcome`
- Avoid testing private methods directly — test through the public API

### Integration tests
- Use `@SpringBootTest` sparingly — prefer `@WebMvcTest` for controllers, `@DataJpaTest` for repositories
- Use Testcontainers for database integration tests (PostgreSQL, MongoDB)
- Use `MockMvc` or `WebTestClient` for API layer tests
- Assert HTTP status codes, response bodies, and headers explicitly

### Contract tests
- Consider Spring Cloud Contract or Pact for consumer-driven contracts between services

## Frontend testing (Angular)

### Unit tests
- Use Jest (or Karma/Jasmine if already in place)
- Test component logic via `TestBed` with `NO_ERRORS_SCHEMA` for child components
- Test services independently using `TestBed.inject()`

### Integration / component tests
- Use Angular Testing Library for user-centric component tests
- Test user interactions (`fireEvent`, `userEvent`), not implementation details

### E2E tests
- Use Playwright for end-to-end scenarios
- Cover critical user journeys only — keep suite fast and stable

## Code review checklist

- Are edge cases and error paths tested?
- Are tests isolated (no shared mutable state between tests)?
- Is every `@Mock` / `MockBean` actually needed?
- Do assertions verify the right things, not just "no exception thrown"?
""".trimIndent()
    )

    val instructions: Map<String, String> = mapOf(

        "java.instructions.md" to """
---
applyTo: "**/*.java"
---

# Java Coding Standards

## Language version
Target Java 17+. Use modern language features: records, sealed classes, pattern matching for `instanceof`, text blocks.

## Style
- Follow Google Java Style Guide for formatting
- Class names: `PascalCase`; methods and fields: `camelCase`; constants: `UPPER_SNAKE_CASE`
- Maximum line length: 120 characters
- One public class per file; filename matches class name

## Immutability
- Declare fields `final` wherever possible
- Use unmodifiable collections (`List.of()`, `Map.of()`, `Collections.unmodifiableList()`)
- Prefer value objects and records for data carriers

## Null safety
- Annotate nullability with `@NonNull` / `@Nullable` (Jakarta or JetBrains)
- Return `Optional<T>` from methods that may have no result — never return `null` for collections (return empty instead)
- Never pass `null` as an argument — use `Optional` or overloaded methods

## Error handling
- Throw specific exceptions, not `RuntimeException` or `Exception`
- Document checked exceptions in Javadoc
- Use try-with-resources for all `AutoCloseable` resources

## Logging
- Use SLF4J with parameterised messages: `log.debug("User {} logged in", userId)`
- Never log sensitive data: passwords, tokens, card numbers, PII
- Log at appropriate levels: DEBUG for dev detail, INFO for business events, WARN for recoverable issues, ERROR for failures
""".trimIndent(),

        "spring-boot.instructions.md" to """
---
applyTo: "**/*.java,**/application*.yml,**/application*.yaml"
---

# Spring Boot Guidelines

## Dependency injection
- Use constructor injection exclusively — no `@Autowired` on fields
- Declare beans `final` in the class body after constructor assignment
- Use `@RequiredArgsConstructor` (Lombok) to reduce boilerplate if Lombok is on the classpath

## Layered architecture
```
Controller → Service → Repository
```
- Controllers handle HTTP concerns only (request parsing, response building, status codes)
- Services contain business logic and own transaction boundaries
- Repositories handle persistence; never put business logic here

## REST API design
- Use `ResponseEntity<T>` for explicit HTTP status control
- Return `201 Created` with `Location` header for resource creation
- Return `204 No Content` for successful deletes
- Use `@Valid` on request body parameters; handle `MethodArgumentNotValidException` in `@ControllerAdvice`

## Transactions
- Annotate service methods with `@Transactional`
- Use `readOnly = true` for read-only service methods
- Never annotate controller methods with `@Transactional`

## Configuration
- Use `application.yml` (not `.properties`)
- Bind config with `@ConfigurationProperties` classes — avoid `@Value` for complex config
- Use Spring profiles (`application-local.yml`, `application-prod.yml`) for environment overrides
- Never hard-code secrets — use environment variables or a secrets manager

## Actuator & Observability
- Expose health, info, and metrics endpoints
- Use Micrometer for custom metrics
- Add trace IDs to log output (Micrometer Tracing + Brave/OpenTelemetry)
""".trimIndent(),

        "angular.instructions.md" to """
---
applyTo: "**/*.ts,**/*.html,**/*.scss"
---

# Angular Coding Standards

## Project structure
Organise by feature module, not by type:
```
src/app/
├── core/           # singleton services, interceptors, guards
├── shared/         # reusable components, pipes, directives
└── features/
    └── <feature>/
        ├── components/
        ├── services/
        └── models/
```

## Components
- Use standalone components (`standalone: true`) for all new code
- Apply `OnPush` change detection strategy on every component
- Use `inject()` function instead of constructor injection
- Keep templates logic-free — move conditions and transformations to the component class or pipes

## Signals (Angular 17+)
- Use `signal()` for local mutable state
- Use `computed()` for derived values
- Use `effect()` only for side effects (e.g., syncing to localStorage); avoid overuse
- Prefer `toSignal()` to convert Observables for template consumption

## Templates
- Use `@if`, `@for`, `@switch` control flow (Angular 17+)
- Always provide a `track` expression in `@for` loops
- Use the `AsyncPipe` for Observables in templates when signals aren't used

## Services and state
- Provide services at the root level with `providedIn: 'root'` unless scoped to a feature
- Use `HttpClient` for all HTTP calls; add interceptors for auth headers and error handling
- Unsubscribe from Observables using `takeUntilDestroyed()` or `toSignal()`

## Styling
- Use component-scoped SCSS (`:host` selector for host styling)
- Follow BEM naming convention for CSS classes
- No global styles except in `styles.scss` for design tokens and resets
""".trimIndent(),

        "architecture.instructions.md" to """
---
applyTo: "**"
---

# Architecture Principles

## Guiding principles
1. **Separation of concerns** — each component has one clear responsibility
2. **Dependency inversion** — depend on abstractions, not concretions
3. **Fail fast** — validate inputs at system boundaries, surface errors early
4. **Defence in depth** — do not rely on a single security or validation layer
5. **Observable systems** — everything significant must be logged, metered, or traced

## Backend architecture (Spring Boot)

### Package structure
```
com.example.app
├── api/          # REST controllers, DTOs, mappers
├── domain/       # business logic, domain models, service interfaces
├── infrastructure/
│   ├── persistence/   # JPA entities, repositories, adapters
│   ├── messaging/     # event publishers/consumers
│   └── external/      # third-party API clients
└── config/            # Spring configuration classes
```

### Rules
- Domain layer has zero Spring dependencies — it is pure Java
- Infrastructure implements domain interfaces (Ports & Adapters / Hexagonal)
- API layer maps between HTTP and domain — no business logic lives here
- Cross-service communication uses async events where possible

## Frontend architecture (Angular)

- Core services are singletons injected at root
- Feature modules are lazy-loaded
- State is local to the feature unless it must be shared — prefer signals over a global store for simple cases
- HTTP communication happens only in services, never in components

## API design
- REST resources are nouns, not verbs (`/orders`, not `/createOrder`)
- Use semantic HTTP methods and status codes consistently
- Version APIs in the URL path: `/api/v1/`
- Paginate all list endpoints; never return unbounded collections

## Data
- Each service owns its data — no cross-service database access
- Use database migrations (Flyway) for all schema changes
- Index foreign keys and frequently-filtered columns
""".trimIndent(),

        "testing.instructions.md" to """
---
applyTo: "**/*Test.java,**/*Spec.ts,**/*.spec.ts,**/*IT.java"
---

# Testing Standards

## General rules
- Tests must be independent — no shared mutable state between test cases
- Tests must be deterministic — never depend on system time, random values, or network without mocking
- One logical assertion group per test; use descriptive assertion messages
- Delete dead tests — a test that never fails and tests nothing is worse than no test

## Naming convention
```
methodOrFeature_scenario_expectedBehaviour()
```
Examples:
- `createOrder_withInvalidProduct_throwsNotFoundException()`
- `calculateDiscount_forPremiumUser_applies20Percent()`

## Backend (Java / JUnit 5)

### Unit test structure (AAA)
```java
@Test
void featureName_scenario_expectedResult() {
    // Arrange
    var input = ...;
    when(dependency.method(input)).thenReturn(stubValue);

    // Act
    var result = sut.methodUnderTest(input);

    // Assert
    assertThat(result).isEqualTo(expected);
    verify(dependency).method(input);
}
```

### Integration tests
- Use `@SpringBootTest(webEnvironment = RANDOM_PORT)` only when the full context is needed
- Prefer `@WebMvcTest` for controller tests and `@DataJpaTest` for repository tests
- Use Testcontainers for real database tests — never rely on H2 in-memory for production schema validation
- Reset database state between tests with `@Transactional` or `@Sql` scripts

## Frontend (Angular / Jest)

### Component test structure
```typescript
it('should display error message when form is submitted empty', async () => {
  // Arrange
  const submitButton = screen.getByRole('button', { name: /submit/i });

  // Act
  await userEvent.click(submitButton);

  // Assert
  expect(screen.getByText(/required/i)).toBeInTheDocument();
});
```

### Rules
- Test behaviour (what the user sees/does), not implementation details
- Mock `HttpClient` with `HttpClientTestingModule` or `provideHttpClientTesting()`
- Use `fakeAsync` + `tick()` for timer-based behaviour

## Coverage targets
- Unit tests: aim for >80% line coverage on service and domain classes
- Integration tests: cover every API endpoint's happy path and primary error cases
- E2E tests: cover the 3–5 most critical user journeys only
""".trimIndent()
    )

    val copilotInstructions: String = """
# GitHub Copilot Instructions

This repository uses Java Spring Boot (backend) and Angular (frontend).

## Code style
- Java 17+, Spring Boot 3.x, follow constructor injection and layered architecture
- Angular 17+, standalone components, signals, OnPush change detection
- Write tests for all new business logic (JUnit 5 + Mockito for Java, Jest for Angular)

## Commit messages
Use Conventional Commits format:
```
<type>(<scope>): <short description>

Types: feat, fix, refactor, test, docs, chore
```

## Branch naming
```
<type>/<short-description>
feat/add-payment-service
fix/order-status-null-pointer
```

## Pull requests
- Link to the issue in the PR description
- Ensure CI passes before requesting review
- Keep PRs focused — one concern per PR

## Agents available
See `.github/agents/` for specialised Copilot agents:
- **tech-lead** — architecture and strategic review
- **java-spring-reviewer** — Java/Spring backend review
- **angular-reviewer** — Angular frontend review
- **security-reviewer** — security analysis
- **test-engineer** — test strategy and review
""".trimIndent()

    val agentsMd: String = """
# AGENTS.md

Guidelines for AI coding agents (GitHub Copilot, Claude Code, etc.) working in this repository.

## Project overview
This is the `copilot-agent-sync-intellij` IntelliJ IDEA plugin. It is a Kotlin/Gradle project built with the IntelliJ Platform Gradle Plugin 2.x.

## Repository layout
```
src/main/kotlin/          Kotlin plugin source code
src/main/resources/       Plugin resources (plugin.xml, icons)
src/test/kotlin/          Unit tests
build.gradle.kts          Gradle build definition
gradle.properties         Plugin metadata and platform versions
settings.gradle.kts       Gradle project name
```

## Build commands
```bash
# Build the plugin
./gradlew build

# Run the plugin in a sandboxed IDE instance
./gradlew runIde

# Run tests
./gradlew test

# Build distributable ZIP
./gradlew buildPlugin

# Verify plugin compatibility
./gradlew verifyPlugin
```

## Prerequisites
- JDK 17 or later
- Gradle 8.8 (via wrapper: `./gradlew`)
- The `gradle-wrapper.jar` must be present at `gradle/wrapper/gradle-wrapper.jar`.
  If missing, generate it with: `gradle wrapper --gradle-version 8.8`

## Code conventions
- Kotlin style follows JetBrains Kotlin Coding Conventions
- No field injection — use constructor parameters or `inject()` where applicable
- Keep actions thin; delegate file I/O to `AgentSyncService`
- Agent/instruction content lives in `AgentDefinitions.kt` only

## Making changes to agent content
All agent and instruction file content is defined in:
`src/main/kotlin/com/chrisvdalen/copilot/agentsync/model/AgentDefinitions.kt`

Edit the string values in that file to update what gets written to projects.

## Plugin entry points
| Class | Purpose |
|---|---|
| `SyncAgentFilesAction` | Menu action under Tools → Copilot Agent Sync |
| `AgentSyncService` | Writes all files to the project root |
| `AgentDefinitions` | Single source of truth for all file content |
""".trimIndent()
}
