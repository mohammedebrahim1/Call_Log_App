package com.geek.animation

import android.Manifest
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.annotation.NonNull
import androidx.core.app.ActivityCompat
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel


class MainActivity: FlutterActivity() {

//   private lateinit var callRecord: CallRecord
//    private var fileName: String = ""
    private  val CHANNEL = "test_activity"
    private val RECORD_REQUEST_CODE = 101
    var prefs: SharedPreferences? = null


//    var recorder: MediaRecorder? = null
//
//    var player: MediaPlayer? = null
//class CallMonitor : BroadcastReceiver() {
//    companion object {
//        private var lastState = TelephonyManager.CALL_STATE_IDLE
//        private var callStartTime: Date? = null
//        private var isIncoming: Boolean = false
//        private var savedNumber: String? = null
//        private var  number : String? = null
//        private var duration: Int = 0
//        private var ringDuration: Int = 0
//    }
//
//    //    The onReceive() callback handles incoming broadcasts, this time an incoming or outgoing call.
//    @RequiresApi(Build.VERSION_CODES.O)
//    override
//    fun onReceive(context: Context, intent: Intent) {
//        if (intent.action == Intent.ACTION_NEW_OUTGOING_CALL) {
//            savedNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER)
//        } else {
//            val stateStr = intent.extras!!.getString(TelephonyManager.EXTRA_STATE)
//            if (intent.extras != null && intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER) != null) {
//                number = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)
//                println("Phone 2: $number")}
//            val state = when (stateStr) {
//                TelephonyManager.EXTRA_STATE_IDLE -> TelephonyManager.CALL_STATE_IDLE
//                TelephonyManager.EXTRA_STATE_OFFHOOK
//                -> TelephonyManager.CALL_STATE_OFFHOOK
//                TelephonyManager.EXTRA_STATE_RINGING
//                -> TelephonyManager.CALL_STATE_RINGING
//                else -> 0
//            }
//            if (stateStr == TelephonyManager.EXTRA_STATE_IDLE && lastState == TelephonyManager.CALL_STATE_IDLE ) {
//                val bundle = Bundle()
//                bundle.putInt(CallLog.Calls.TYPE, CallLog.Calls.OUTGOING_TYPE)
//
//                val cursor = context?.contentResolver?.query(
//                        CallLog.Calls.CONTENT_URI, null,
//                        bundle, null
//                )
//
//                duration = cursor!!.getColumnIndex(CallLog.Calls.DURATION)
//
//                cursor.moveToLast()
//                val callDuration = cursor.getString(duration)
//                println("callDuration: $callDuration")
//                if(cursor.getInt(duration) != 0){
//                    ringDuration -= (cursor.getInt(duration))%60
//                    if(ringDuration<0)
//                        ringDuration+=120
//                }
//                println("ringDuration: $ringDuration")
//
//                cursor.close()
//
//            }
//
//            callStateChanged(context, state, number)
//        }
//    }
//
//    protected fun onIncomingCallReceived(ctx: Context, number: String?, start: Date) {
//        Log.e("LOG", "IncomingCallReceived ${number} ${start}")
//    }
//
//    protected fun onIncomingCallAnswered(ctx: Context, number: String?, start: Date) {
//        Log.e("LOG", "IncomingCallAnswered ${number} ${start}")
//    }
//
//    protected fun onIncomingCallEnded(ctx: Context, number: String?, start: Date?, end: Date) {
//        Log.e("LOG", "IncomingCallEnded ${number} ${end}")
//        ringDuration = end.seconds -  start!!.seconds
//
//    }
//
//    protected fun onOutgoingCallStarted(ctx: Context, number: String?, start: Date) {
//        Log.e("LOG", "OutgoingCallStarted ${number} ${start}")
//    }
//
//    protected fun onOutgoingCallEnded(ctx: Context, number: String?, start: Date?, end: Date) {
//        Log.e("LOG", "OutgoingCallEnded ${number} ${end}")
//        ringDuration = end.seconds -  start!!.seconds
//
//
//    }
//
//    protected fun onMissedCall(ctx: Context, number: String?, start: Date?) {
//        Log.e("LOG", "MissedCall ${number} ${start}")
//        ringDuration = Date().seconds -  start!!.seconds
//    }
//
//
//
////    private method callStateChanged() reacts on the various state changes corresponding to phone calls.
//    /** * Incoming call: *IDLE -> RINGING when it rings, *-> OFFHOOK when it's answered, *-> IDLE when its hung up * Outgoing call: *IDLE -> OFFHOOK when it dials out,
//     *-> IDLE when hung up * */
//    @RequiresApi(Build.VERSION_CODES.O)
//    private fun callStateChanged(context: Context, state: Int, number: String?) {
//        if (lastState == state) {
//            return // no change in state}
//        }
//        when (state) {
//            TelephonyManager.CALL_STATE_RINGING -> {
//                isIncoming = true
//                callStartTime = Date()
//                savedNumber = number
//                onIncomingCallReceived(context, number, callStartTime!!)
//                println(" is received : $isIncoming")
//
//            }
//            TelephonyManager.CALL_STATE_OFFHOOK -> if (lastState != TelephonyManager.CALL_STATE_RINGING) {
//                isIncoming = false
//                callStartTime = Date()
//                onOutgoingCallStarted(context, savedNumber, callStartTime!!)
//                println(" is received : $isIncoming")
//
//            } else {
//                isIncoming = true
//                callStartTime = Date()
//                onIncomingCallAnswered(context, savedNumber, callStartTime!!)
//                println(" is received : $isIncoming")
//
//            }
//            TelephonyManager.CALL_STATE_IDLE
//            -> {
//                if (lastState == TelephonyManager.CALL_STATE_RINGING) {//Ring but no pickup- a misson
//                    onMissedCall(context, savedNumber, callStartTime)
//                    println(" is received : $isIncoming")
//
//                }
//                else if (isIncoming) {
//                    onIncomingCallEnded(context, savedNumber, callStartTime, Date())
//                    println(" is received : $isIncoming")
//
//                } else {
//                    onOutgoingCallEnded(context, savedNumber, callStartTime, Date())
//                    println(" is received : $isIncoming")
//                }
//
////
//            }
//        }
//        lastState = state
//    }
//}


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

    override fun onResume() {
        super.onResume()
        prefs = context.getSharedPreferences("key", MODE_PRIVATE)
        println(prefs!!.getString("key" , null))
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

