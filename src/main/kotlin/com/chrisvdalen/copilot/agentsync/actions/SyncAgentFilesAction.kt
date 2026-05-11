package com.chrisvdalen.copilot.agentsync.actions

import com.chrisvdalen.copilot.agentsync.services.AgentSyncService
import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages

private const val NOTIFICATION_GROUP = "Copilot Agent Sync"

class SyncAgentFilesAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return

        val confirmed = Messages.showYesNoDialog(
            project,
            """
            This will write GitHub Copilot agent and instruction files to your project:

              .github/agents/          (5 agent files)
              .github/instructions/    (5 instruction files)
              .github/copilot-instructions.md
              AGENTS.md

            Existing files will be overwritten. Continue?
            """.trimIndent(),
            "Sync Copilot Agent Files",
            Messages.getQuestionIcon()
        )

        if (confirmed != Messages.YES) return

        val result = AgentSyncService(project).syncAll()

        if (result.isSuccess) {
            notify(
                project,
                "Copilot agent files synced — ${result.fileCount} files written to your project.",
                NotificationType.INFORMATION
            )
        } else {
            notify(
                project,
                "Sync failed: ${result.error}",
                NotificationType.ERROR
            )
        }
    }

    override fun update(e: AnActionEvent) {
        e.presentation.isEnabled = e.project != null
    }

    private fun notify(project: Project, message: String, type: NotificationType) {
        NotificationGroupManager.getInstance()
            .getNotificationGroup(NOTIFICATION_GROUP)
            .createNotification(message, type)
            .notify(project)
    }
}
