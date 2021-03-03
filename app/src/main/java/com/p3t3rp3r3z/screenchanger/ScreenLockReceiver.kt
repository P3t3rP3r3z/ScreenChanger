package com.p3t3rp3r3z.screenchanger

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class ScreenLockReceiver : BroadcastReceiver() {
    private var screenOn = false

    override fun onReceive(context: Context, intent: Intent) {
//        val actionString = intent.action.toString()
//        Toast.makeText(context, intent.getStringExtra("time-zone"), Toast.LENGTH_LONG).show()
//        Log.d("blargle", actionString)
        if (intent.action.equals(Intent.ACTION_SCREEN_OFF)) {
            screenOn = false
        } else if (intent.action.equals(Intent.ACTION_SCREEN_ON)) {
            screenOn = true
        }

        val i = Intent(context, BackgroundService::class.java)
        i.putExtra("screen_state", screenOn)
        context.startService(i)
    }
}
