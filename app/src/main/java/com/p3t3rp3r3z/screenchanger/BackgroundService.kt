package com.p3t3rp3r3z.screenchanger

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Intent
import android.content.Intent.getIntent
import android.content.Intent.parseUri
import android.content.IntentFilter
import android.net.Uri
import android.os.IBinder
import android.util.Log
import com.google.android.material.internal.ContextUtils.getActivity
import java.net.URI

class BackgroundService : Service() {
    private val MYTAG = "blargle: "
    private lateinit var mReceiver: BroadcastReceiver
    private lateinit var uri : Uri

    override fun onCreate() {
        super.onCreate()

        val filter = IntentFilter(Intent.ACTION_SCREEN_ON)
        filter.addAction(Intent.ACTION_SCREEN_OFF)
        mReceiver = ScreenLockReceiver()
        registerReceiver(mReceiver, filter)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        var screenOn = true
//        var extras = intent?.extras.get("uri_from_intent")
        uri = Uri.parse(intent?.extras?.get("uri_from_intent").toString())

        try {
            screenOn = intent!!.getBooleanExtra("screen_state", true)
        } catch (e: Exception) {
            // Do nothing
        }
        if (!screenOn) {
            Log.d(MYTAG, "Screen off.")
        } else {

            Log.d(MYTAG, "screen on")
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(MYTAG, "Service destroy")
        if (mReceiver != null)
            unregisterReceiver(mReceiver)
    }
}
