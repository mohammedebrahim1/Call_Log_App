package com.geek.animation

import android.annotation.TargetApi
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.CallLog
import android.telephony.TelephonyManager
import android.util.Log
import androidx.annotation.RequiresApi


class CallReceive : BroadcastReceiver() {
    private var lastState = TelephonyManager.CALL_STATE_IDLE
    var prevState = TelephonyManager.CALL_STATE_IDLE
    var isRecording = false
    var running = false
    var seconds: Int = 0
    var minutes: Int = 0
    var hours: Int = 0
    var begin: Long = 10
    var end: Long = 10
    var bundle = Bundle()


    @RequiresApi(Build.VERSION_CODES.O)
    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    override fun onReceive(context: Context?, intent: Intent?) {
        println("inRecieve")
        if (intent == null || intent.extras == null) {
            Log.e("CAll receiver", "Invalid onReceive bundle")
            return
        }

        if (intent.extras != null) {
            val mblNum = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)
            Log.d("phone 2", "" + mblNum)

            val stateStr = if (intent.hasExtra(TelephonyManager.EXTRA_STATE)) intent.getStringExtra(
                    TelephonyManager.EXTRA_STATE
            ) else null

            var state = 0
            if (stateStr == TelephonyManager.EXTRA_STATE_IDLE
                    && stateToString(prevState) != TelephonyManager.EXTRA_STATE_IDLE) {
                println("CALL ENDED BITCH")

            }



            if (stateStr != null && stateStr == TelephonyManager.EXTRA_STATE_IDLE) {
                bundle.putInt(
                        CallLog.Calls.TYPE, CallLog.Calls.OUTGOING_TYPE)
                var sum = 0

                var cursor = context?.contentResolver?.query(CallLog.Calls.CONTENT_URI, null,
                        bundle, null)

                var duration: Int = cursor!!.getColumnIndex(CallLog.Calls.DURATION)

//                while (cursor!!.mo()) {
//                    var callDuration = cursor.getString(duration)
//                    sum += Integer.parseInt(callDuration)
//                                    println(callDuration + "  sssssss")
//
//                }
                cursor!!.moveToLast()
                var callDuration = cursor.getString(duration)
                println(callDuration.toString() + "sssssss")

                cursor.close()
                end = System.currentTimeMillis()
                state = TelephonyManager.CALL_STATE_IDLE
            } else if (stateStr != null && stateStr == TelephonyManager.EXTRA_STATE_OFFHOOK) {
                begin = System.currentTimeMillis()
                state = TelephonyManager.CALL_STATE_OFFHOOK

            } else if (stateStr != null && stateStr == TelephonyManager.EXTRA_STATE_RINGING) {
                state = TelephonyManager.CALL_STATE_RINGING
            }

            if (!mblNum.isNullOrEmpty()) {
                onCallStateChanged(state, mblNum)
            }

        }

    }

    private fun onCallStateChanged(state: Int, number: String?) {
        when (state) {
            TelephonyManager.CALL_STATE_RINGING -> {
                Log.d("CALL_STATE_RINGING : %s", number)

            }
            TelephonyManager.CALL_STATE_OFFHOOK -> {
                Log.d("CALL_STATE_OFF_HOOK", number)
                Log.d("isrecord inside hook", "" + isRecording)

//                    CallRecorder.startRecorder()
                isRecording = true

//                Log.d("finished++", seconds.toString() + minutes.toString() + "duration")


                Log.d("isrecord", "" + isRecording)


            }
            TelephonyManager.CALL_STATE_IDLE -> {
                Log.d("isrecord inside idle", "" + isRecording)
                Log.d("CALL_STATE_IDLE", number)

                Log.d("isrecord inside", "" + isRecording)
                running = false

//                    CallRecorder.stopRecording(number)


            }

        }
        if (state == TelephonyManager.CALL_STATE_OFFHOOK) {
//            seconds += 1
//            if (seconds > 59) {
//                minutes += 1
//                seconds = 0
//                if (minutes > 59) {
//                    hours += 1
//                    minutes = 0
//                }
//            }
            println("started in hook -  $begin")


        }
        if (state == TelephonyManager.CALL_STATE_IDLE) {
//            var time = android.provider.CallLog.Calls.DURATION
//            println("started -  $begin")
//            println("finished -  $end")
//            val mills = end - begin
//            val period = String.format("%02d:%02d",
//                    TimeUnit.MILLISECONDS.toHours(mills),
//                    TimeUnit.MILLISECONDS.toMinutes(mills) % TimeUnit.HOURS.toMinutes(1))
//
//            println("Duration hh:mm -  $time")


        }





        lastState = state
        prevState = state

    }

    fun stateToString(state: Int): String {
        when (state) {
            0 ->
                return "IDLE"

            1 ->
                return "RINGING"

            else -> return "OFFHOOK"
        }
    }
}