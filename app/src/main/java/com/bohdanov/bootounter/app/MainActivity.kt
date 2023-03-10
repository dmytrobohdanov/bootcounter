package com.bohdanov.bootounter.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.work.*
import com.bohdanov.bootounter.app.notificationshandler.NotificationsWorker
import com.bohdanov.bootounter.app.notificationshandler.NotificationsWorker.Companion.KEY_BOOT_EVENTS
import com.bohdanov.bootounter.data.BootDataRepository
import com.bohdanov.bootounter.databinding.ActivityMainBinding
import com.bohdanov.bootounter.models.BootData
import com.bohdanov.bootounter.utils.NOTIFICATIONS_PERIOD_MINUTES
import com.bohdanov.bootounter.utils.serialize
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

        binding.activityMainBootText.text = "hello world"
    }

    private fun startWork() {
        val work = createWorkRequest(
            repository.getSavedBoots()
        )

        WorkManager.getInstance(applicationContext)
            .enqueueUniquePeriodicWork(
                "NotificationsWork",
                ExistingPeriodicWorkPolicy.REPLACE, work
            )
    }

    private fun createWorkRequest(bootEvents: List<BootData>): PeriodicWorkRequest {
        val data = Data.Builder()
            .putString(KEY_BOOT_EVENTS, bootEvents.serialize())
            .build()

        return PeriodicWorkRequestBuilder<NotificationsWorker>(
            NOTIFICATIONS_PERIOD_MINUTES,
            TimeUnit.MINUTES
        ).setInputData(data)
            .setBackoffCriteria(
                BackoffPolicy.LINEAR,
                PeriodicWorkRequest.MIN_BACKOFF_MILLIS,
                TimeUnit.MILLISECONDS
            )
            .build()
    }
}
