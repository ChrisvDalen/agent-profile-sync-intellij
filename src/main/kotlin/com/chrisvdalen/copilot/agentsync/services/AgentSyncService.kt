package com.chrisvdalen.copilot.agentsync.services

import com.chrisvdalen.copilot.agentsync.model.AgentDefinitions
import com.intellij.openapi.project.Project
import java.io.File

data class SyncResult(
    val isSuccess: Boolean,
    val fileCount: Int = 0,
    val error: String? = null
)

class AgentSyncService(private val project: Project) {

    fun syncAll(): SyncResult {
        val root = project.basePath?.let { File(it) }
            ?: return SyncResult(false, error = "No project base path found")

        return try {
            var count = 0

            val agentsDir = File(root, ".github/agents")
            agentsDir.mkdirs()
            AgentDefinitions.agents.forEach { (filename, content) ->
                File(agentsDir, filename).writeText(content)
                count++
            }

            val instructionsDir = File(root, ".github/instructions")
            instructionsDir.mkdirs()
            AgentDefinitions.instructions.forEach { (filename, content) ->
                File(instructionsDir, filename).writeText(content)
                count++
            }

            val githubDir = File(root, ".github")
            githubDir.mkdirs()
            File(githubDir, "copilot-instructions.md").writeText(AgentDefinitions.copilotInstructions)
            count++

            File(root, "AGENTS.md").writeText(AgentDefinitions.agentsMd)
            count++

            SyncResult(true, count)
        } catch (e: Exception) {
            SyncResult(false, error = e.message ?: "Unknown error")
        }
    }
}
