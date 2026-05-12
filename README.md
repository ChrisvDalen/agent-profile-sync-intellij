# copilot-agent-sync-intellij

An IntelliJ IDEA plugin that syncs shared GitHub Copilot agent and instruction files into any project with a single menu action.

## What it does

After running **Tools → Copilot Agent Sync → Sync Agent Files**, the plugin writes the following structure to your project root:

```
.github/
├── agents/
│   ├── tech-lead.agent.md
│   ├── java-spring-reviewer.agent.md
│   ├── angular-reviewer.agent.md
│   ├── security-reviewer.agent.md
│   └── test-engineer.agent.md
├── instructions/
│   ├── java.instructions.md
│   ├── spring-boot.instructions.md
│   ├── angular.instructions.md
│   ├── architecture.instructions.md
│   └── testing.instructions.md
└── copilot-instructions.md

AGENTS.md
```

GitHub Copilot then automatically discovers the agents under **Copilot Chat → Agent dropdown → Configure Agents**.

## Included agents

| Agent | Purpose |
|---|---|
| **Tech Lead** | Architecture guidance, strategic code review |
| **Java Spring Reviewer** | Java 17+ and Spring Boot 3.x best practices |
| **Angular Reviewer** | Angular 17+, signals, standalone components |
| **Security Reviewer** | OWASP Top 10, Spring Security, Angular XSS |
| **Test Engineer** | Test strategy, JUnit 5, Jest, Playwright |

## Development

### Prerequisites
- JDK 17+
- Gradle 8.8 (the wrapper `./gradlew` downloads it automatically)

> **First-time setup**: The `gradle-wrapper.jar` is not committed to this repository.
> Generate it once with:
> ```bash
> gradle wrapper --gradle-version 8.8
> ```
> After that, use `./gradlew` for all commands.

### Common tasks

```bash
./gradlew build          # compile and test
./gradlew runIde         # launch a sandboxed IntelliJ with the plugin loaded
./gradlew buildPlugin    # produce the distributable ZIP in build/distributions/
./gradlew verifyPlugin   # check compatibility with the target platform
./gradlew test           # run unit tests only
```

### Updating agent content

All file content is defined in a single place:

```
src/main/kotlin/com/chrisvdalen/copilot/agentsync/model/AgentDefinitions.kt
```

Edit the string maps in that file to change what gets written to projects.

## License

Copyright © 2026 Chris van Dalen. All rights reserved.
Proprietary — see LICENSE for terms.
