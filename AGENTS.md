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
- JDK 25
- Gradle 9.7.1 (via wrapper: `./gradlew`)
- The `gradle-wrapper.jar` must be present at `gradle/wrapper/gradle-wrapper.jar`.
  If missing, generate it with: `gradle wrapper --gradle-version 9.7.1`

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
