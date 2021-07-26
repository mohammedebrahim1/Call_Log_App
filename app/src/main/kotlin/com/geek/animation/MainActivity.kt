package com.geek.animation

import android.Manifest
import android.annotation.TargetApi
import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.BatteryManager
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.os.Environment
import android.os.PersistableBundle
import android.util.Log
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugins.GeneratedPluginRegistrant
import java.io.IOException


class MainActivity: FlutterActivity() {

//   private lateinit var callRecord: CallRecord
//    private var fileName: String = ""
    private  val CHANNEL = "test_activity"
    private val RECORD_REQUEST_CODE = 101





//    var recorder: MediaRecorder? = null
//
//    var player: MediaPlayer? = null


//    private  val LOG_TAG = "AudioRecordTest"
//    private  val REQUEST_RECORD_AUDIO_PERMISSION = 200
//    private var permissionToRecordAccepted = false
//    private var permissions: Array<String> = arrayOf(Manifest.permission.RECORD_AUDIO)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkAndRequestPermission()

//        Log.e(LOG_TAG, "akjjvsbvakjjvakjvnajvakja")
//        callRecord = CallRecord.Builder(this)
//                .setLogEnable(true)
//                .setRecordFileName("CallRecorderTestFile")
//                .setRecordDirName("CallRecorderTest")
//                .setAudioSource(MediaRecorder.AudioSource.CAMCORDER)
//                .setShowSeed(true)
//                .build()
////        callRecord.changeReceiver( MyCallRecordReceiver(callRecord))

    }


//    override fun onRequestPermissionsResult(
//            requestCode: Int,
//            permissions: Array<String>,
//            grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        permissionToRecordAccepted = if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
//            grantResults[0] == PackageManager.PERMISSION_GRANTED
//        } else {
//            false
//        }
//        if (!permissionToRecordAccepted) finish()
//    }




    override fun configureFlutterEngine(@NonNull flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL).setMethodCallHandler {
            // Note: this method is invoked on the main thread.

            call, result ->

//            if (call.method == "getBatteryLevel") {
//                val batteryLevel = getBatteryLevel()
//
//                if (batteryLevel != -1) {
//                    result.success(batteryLevel)
//                } else {
//                    result.error("UNAVAILABLE", "Battery level not available.", null)
//                }
//            }
            when (call.method) {
                "startRecording" -> {

//                    startRecording()

                }
                "stopRecording" -> {

//                    stopRecording()

                }
                else -> {
                    result.notImplemented()
                }
            }
        }

    }
    private fun checkAndRequestPermission() {
        /*  val readPhoneState =
              ContextCompat.checkSelfPermission(this, READ_PHONE_STATE)
          val read_call_log =
              ContextCompat.checkSelfPermission(this, READ_CALL_LOG)*/

        val listPermissionsNeeded = ArrayList<String>()

        val PERMISSIONS = arrayOf(
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.CALL_PHONE,
                Manifest.permission.READ_CALL_LOG,
                Manifest.permission.READ_PHONE_STATE

        )


        if (!hasPermissions(*PERMISSIONS)) {
            ActivityCompat.requestPermissions(this,
                    PERMISSIONS,
                    RECORD_REQUEST_CODE)
        }




    }
    fun hasPermissions(vararg permissions: String): Boolean {
        for (permission in permissions) {
            if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

//    private fun getBatteryLevel(): Int {
//        val batteryLevel: Int
//        if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
//            val batteryManager = getSystemService(Context.BATTERY_SERVICE) as BatteryManager
//            batteryLevel = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
//        } else {
//            val intent = ContextWrapper(applicationContext).registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
//            batteryLevel = intent!!.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) * 100 / intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
//        }
//
//        return batteryLevel
//    }
//
//    private  fun start(){
//
//    }
//
//
//    private fun onRecord(start: Boolean) = if (start) {
//        startRecording()
//    } else {
//        stopRecording()
//    }
//
//    private fun onPlay(start: Boolean) = if (start) {
//        startPlaying()
//    } else {
//        stopPlaying()
//    }
//
//    private fun startPlaying() {
//        player = MediaPlayer().apply {
//            try {
//                setDataSource(fileName)
//                prepare()
//                start()
//            } catch (e: IOException) {
//                Log.e(LOG_TAG, "prepare() failed")
//            }
//        }
//    }
//
//    private fun stopPlaying() {
//        player?.release()
//        player = null
//    }
//
//    @TargetApi(VERSION_CODES.FROYO)
//    private fun startRecording() {
////        Log.e(LOG_TAG, "bkjbjhhbhhvjhvjvhv")
////
//        fileName = "${ContextWrapper(applicationContext).externalCacheDir?.absolutePath}/audiorecordtest.3gp"
//        Log.e(LOG_TAG, fileName)
//
//        recorder = MediaRecorder().apply {
//            setAudioSource(MediaRecorder.AudioSource.VOICE_DOWNLINK)
//            setOutputFormat(MediaRecorder.OutputFormat.AMR_NB)
//            setOutputFile(fileName)
//            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
//
//            try {
//                prepare()
//            } catch (e: IOException) {
//                Log.e(LOG_TAG, "prepare() failed")
//            }
//
//            start()
//        }
//
////        callRecord.startCallReceiver()
////        callRecord.enableSaveFile()
////        LogUtils.i(TAG, "StartCallRecordClick")
//////        callRecord.startCallReceiver()
//    }
//
//    private fun stopRecording() {
//        recorder?.apply {
//            stop()
//            release()
//            println("helooooooo")
//    }
////        LogUtils.i(TAG, "StopCallRecordClick")
////        callRecord.stopCallReceiver()
//    }

}

