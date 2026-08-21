package com.chrisvdalen.copilot.agentsync.model

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class AgentDefinitionsTest {

    @Test
    fun generatedProfiles_targetTheCurrentApplicationStack() {
        val generatedContent = buildList {
            addAll(AgentDefinitions.agents.values)
            addAll(AgentDefinitions.instructions.values)
            add(AgentDefinitions.copilotInstructions)
            add(AgentDefinitions.agentsMd)
        }.joinToString("\n")

        assertTrue(generatedContent.contains("Java 25"))
        assertTrue(generatedContent.contains("Spring Boot 4.1"))
        assertTrue(generatedContent.contains("Angular 22"))
        assertTrue(generatedContent.contains("JUnit 6"))
        assertTrue(generatedContent.contains("Vitest"))

        listOf("Java 17", "Spring Boot 3", "Angular 17", "JUnit 5", "Jest", "Karma")
            .forEach { obsoleteTerm ->
                assertFalse("Obsolete generated guidance: $obsoleteTerm", generatedContent.contains(obsoleteTerm))
            }
    }
}
