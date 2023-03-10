package com.bohdanov.bootounter.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import com.bohdanov.bootounter.app.notificationshandler.NotificationsWorker
import com.bohdanov.bootounter.data.BootDataRepository
import com.bohdanov.bootounter.databinding.ActivityMainBinding
import com.bohdanov.bootounter.utils.NOTIFICATIONS_PERIOD_MINUTES
import com.bohdanov.bootounter.utils.formNotificationMessage
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var repository: BootDataRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        startWork()
        displayCurrentBootsData()
    }

    private fun startWork() {
        WorkManager.getInstance(applicationContext)
            .enqueueUniquePeriodicWork(
                "NotificationsWork",
                ExistingPeriodicWorkPolicy.REPLACE,
                createWorkRequest()
            )
    }

    private fun createWorkRequest(): PeriodicWorkRequest {
        return PeriodicWorkRequestBuilder<NotificationsWorker>(
            NOTIFICATIONS_PERIOD_MINUTES,
            TimeUnit.MINUTES
        ).setBackoffCriteria(
            BackoffPolicy.LINEAR,
            PeriodicWorkRequest.MIN_BACKOFF_MILLIS,
            TimeUnit.MILLISECONDS
        ).build()
    }

    private fun displayCurrentBootsData() {
        val message = formNotificationMessage(this, repository.getSavedBoots())

        binding.activityMainBootText.text = message
    }
}
