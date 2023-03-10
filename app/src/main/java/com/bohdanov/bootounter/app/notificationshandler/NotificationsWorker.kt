package com.bohdanov.bootounter.app.notificationshandler

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.bohdanov.bootounter.data.BootDataRepository
import com.bohdanov.bootounter.utils.formNotificationMessage

private const val NOTIFICATION_TITLE = "Boots title"

class NotificationsWorker(context: Context, workerParams: WorkerParameters) : Worker(
    context,
    workerParams
) {
    private val notificationDisplayer = NotificationDisplayer(context)
    private lateinit var repository: BootDataRepository

    override fun doWork(): Result {
        notificationDisplayer.displayBoots(
            NOTIFICATION_TITLE,
            formNotificationMessage(applicationContext, repository.getSavedBoots())
        )
        return Result.success()
    }
}
