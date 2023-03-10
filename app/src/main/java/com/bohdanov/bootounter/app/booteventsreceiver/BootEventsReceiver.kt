package com.bohdanov.bootounter.app.booteventsreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.bohdanov.bootounter.data.BootDataRepository

class BootEventsReceiver : BroadcastReceiver() {
    lateinit var repository : BootDataRepository
    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action

        action?.let {
            if (it == Intent.ACTION_BOOT_COMPLETED) {
                repository.saveBootData(System.currentTimeMillis())
            }
        }
    }
}
