package com.bohdanov.bootounter.app.notificationshandler

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class NotificationsWorker(context: Context, workerParams: WorkerParameters) : Worker(
    context,
    workerParams
) {
    companion object{
        val KEY_BOOT_EVENTS = "bootEvents"
    }

    override fun doWork(): Result {
        // todo show notification

        return Result.success()
    }
}