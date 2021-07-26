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
    var prevState = TelephonyManager.CALL_STATE_IDLE

    @RequiresApi(Build.VERSION_CODES.O)
    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    override fun onReceive(context: Context?, intent: Intent?) {
        println("onReceive")

        if (intent == null || intent.extras == null) {
            Log.e("CAll receiver", "Invalid onReceive bundle")
            return
        }

        if (intent.extras != null && intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER) != null) {
            val mblNum = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)
            println("Phone 2: $mblNum")


            val stateStr = if (intent.hasExtra(TelephonyManager.EXTRA_STATE))
                intent.getStringExtra(TelephonyManager.EXTRA_STATE)
            else null

            var state = stateToInt(stateStr!!)

            println("StateStr $stateStr")
            println("stateToString ${stateToString(prevState)}")

            if (stateStr == TelephonyManager.EXTRA_STATE_IDLE && stateToString(prevState) != TelephonyManager.EXTRA_STATE_IDLE) {
                println("CALL ENDED BITCH")
            }


            if (stateStr == TelephonyManager.EXTRA_STATE_IDLE) {
                val bundle = Bundle()
                bundle.putInt(CallLog.Calls.TYPE, CallLog.Calls.OUTGOING_TYPE)

                val cursor = context?.contentResolver?.query(
                    CallLog.Calls.CONTENT_URI, null,
                    bundle, null
                )

                val duration: Int = cursor!!.getColumnIndex(CallLog.Calls.DURATION)

                cursor.moveToLast()
                val callDuration = cursor.getString(duration)
                println("callDuration$callDuration")

                cursor.close()

                state = TelephonyManager.CALL_STATE_IDLE
                println("CALL_STATE_IDLE")

            } else if (stateStr == TelephonyManager.EXTRA_STATE_OFFHOOK) {
                state = TelephonyManager.CALL_STATE_OFFHOOK
                println("CALL_STATE_OFFHOOK")
            } else if (stateStr == TelephonyManager.EXTRA_STATE_RINGING) {
                state = TelephonyManager.CALL_STATE_RINGING
                println("CALL_STATE_RINGING")

            }

            prevState = state

        }
    }

    private fun stateToString(state: Int): String {
        return when (state) {
            0 -> "IDLE"
            1 -> "RINGING"
            else -> "OFFHOOK"
        }
    }

    private fun stateToInt(state: String): Int {
        return when (state) {
            "IDLE" -> 0
            "RINGING" -> 1
            else -> 2
        }
    }
}