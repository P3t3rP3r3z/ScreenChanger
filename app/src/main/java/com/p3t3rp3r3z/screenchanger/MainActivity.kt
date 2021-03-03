package com.p3t3rp3r3z.screenchanger

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import java.io.File

class MainActivity : AppCompatActivity() {
    private var screenLockReceiver = ScreenLockReceiver()
    private lateinit var serviceIntent: Intent
    private lateinit var dataURI : Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    }

    override fun onStart() {
        super.onStart()

        onClickSetup()
    }

    fun onClickSetup(){

        val startBtn : Button = findViewById(R.id.start_svc)
        val stopBtn : Button = findViewById(R.id.stop_svc)
        val fileBtn : Button = findViewById(R.id.file_chsr)

        startBtn.setOnClickListener{
            serviceIntent = Intent(applicationContext, BackgroundService::class.java)
            if(this::dataURI.isInitialized) {
                serviceIntent.putExtra("uri_from_intent", this.dataURI.toString())
            }
            this.startService(serviceIntent)
        }

        stopBtn.setOnClickListener {
            serviceIntent = Intent(applicationContext, BackgroundService::class.java)
            stopService(serviceIntent)
        }

        fileBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE).apply {
                flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or
                        Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION
            }
            startActivityForResult(intent, 1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            dataURI = data?.data!!

            Log.i("blargle","${data?.data}")

        }
    }
}
