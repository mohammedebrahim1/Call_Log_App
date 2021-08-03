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
import android.telephony.TelephonyManager as TM
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.getSystemService
import java.util.*
import kotlin.math.absoluteValue
import kotlin.time.seconds


//class CallReceive : BroadcastReceiver() {
//    var prevState = TelephonyManager.CALL_STATE_IDLE
//    private var callStartTime : Date? = null
//    private var isReceived : Boolean? = false
//    private var captainNumber : String? = null
//
//    @RequiresApi(Build.VERSION_CODES.O)
//    @TargetApi(Build.VERSION_CODES.CUPCAKE)
//    override fun onReceive(context: Context?, intent: Intent?) {
//        println("onReceive")
//
//        if (intent == null || intent.extras == null) {
//            Log.e("CAll receiver", "Invalid onReceive bundle")
//            return
//        }
//        if(intent.action == Intent.ACTION_NEW_OUTGOING_CALL){
//            captainNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER)
//            println("captain number $captainNumber")
//        }
//
//
//
//            val stateStr = if (intent.hasExtra(TelephonyManager.EXTRA_STATE))
//                intent.getStringExtra(TelephonyManager.EXTRA_STATE)
//            else null
//
//            var state = stateToInt(stateStr!!)
//
//            println("StateStr $stateStr")
//            println("stateToString ${stateToString(prevState)}")
//
//            if (stateStr == TelephonyManager.EXTRA_STATE_IDLE && stateToString(prevState) == TelephonyManager.EXTRA_STATE_IDLE) {
//                println("CALL ENDED BITCH")
//            }
//
//
//            if (stateStr == TelephonyManager.EXTRA_STATE_IDLE) {
//                val bundle = Bundle()
//                bundle.putInt(CallLog.Calls.TYPE, CallLog.Calls.OUTGOING_TYPE)
//
//                val cursor = context?.contentResolver?.query(
//                    CallLog.Calls.CONTENT_URI, null,
//                    bundle, null
//                )
//
//                val duration: Int = cursor!!.getColumnIndex(CallLog.Calls.DURATION)
//
//                cursor.moveToLast()
//                val callDuration = cursor.getString(duration)
//                println("callDuration$callDuration")
//
//                cursor.close()
//
//                state = TelephonyManager.CALL_STATE_IDLE
//                println("CALL_STATE_IDLE")
//
//            } else if (stateStr == TelephonyManager.EXTRA_STATE_OFFHOOK) {
//                state = TelephonyManager.CALL_STATE_OFFHOOK
//                println("CALL_STATE_OFFHOOK")
//            } else if (stateStr == TelephonyManager.EXTRA_STATE_RINGING) {
//                state = TelephonyManager.CALL_STATE_RINGING
//                isReceived = true
//                callStartTime = Date()
//                println("CALL_STATE_RINGING")
//
//            }
//
//            prevState = state
//
//        }
//    }
//
//    private fun stateToString(state: Int): String {
//        return when (state) {
//            0 -> "IDLE"
//            1 -> "RINGING"
//            else -> "OFFHOOK"
//        }
//    }
//
//    private fun stateToInt(state: String): Int {
//        return when (state) {
//            "IDLE" -> 0
//            "RINGING" -> 1
//            else -> 2
//        }
//    }
//}
class CallMonitor : BroadcastReceiver() {
    companion object {
        private var lastState = TM.CALL_STATE_IDLE
        private var callStartTime: Date? = null
        private var isIncoming: Boolean = false
        private var savedNumber: String? = null
        private var  number : String? = null
        private var duration: Int = 0
        private var ringDuration: Int = 0
    }

    //    The onReceive() callback handles incoming broadcasts, this time an incoming or outgoing call.
    @RequiresApi(Build.VERSION_CODES.O)
    override
    fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_NEW_OUTGOING_CALL) {
            savedNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER)
        } else {
            val stateStr = intent.extras!!.getString(TM.EXTRA_STATE)
            if (intent.extras != null && intent.getStringExtra(TM.EXTRA_INCOMING_NUMBER) != null) {
             number = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)
            println("Phone 2: $number")}
            val state = when (stateStr) {
                TM.EXTRA_STATE_IDLE -> TM.CALL_STATE_IDLE
                TM.EXTRA_STATE_OFFHOOK
                -> TM.CALL_STATE_OFFHOOK
                TM.EXTRA_STATE_RINGING
                -> TM.CALL_STATE_RINGING
                else -> 0
            }
            if (stateStr == TM.EXTRA_STATE_IDLE && lastState == TM.CALL_STATE_IDLE ) {
                    val bundle = Bundle()
                    bundle.putInt(CallLog.Calls.TYPE, CallLog.Calls.OUTGOING_TYPE)

                    val cursor = context?.contentResolver?.query(
                            CallLog.Calls.CONTENT_URI, null,
                            bundle, null
                    )

                     duration = cursor!!.getColumnIndex(CallLog.Calls.DURATION)

                    cursor.moveToLast()
                    val callDuration = cursor.getString(duration)
                    println("callDuration: $callDuration")
                if(cursor.getInt(duration) != 0){
                    ringDuration -= (cursor.getInt(duration))%60
                    if(ringDuration<0)
                        ringDuration+=120
                }
                println("ringDuration: $ringDuration")

                cursor.close()

            }

            callStateChanged(context, state, number)
        }
    }

    protected fun onIncomingCallReceived(ctx: Context, number: String?, start: Date) {
        Log.e("LOG", "IncomingCallReceived ${number} ${start}")
    }

    protected fun onIncomingCallAnswered(ctx: Context, number: String?, start: Date) {
        Log.e("LOG", "IncomingCallAnswered ${number} ${start}")
    }

    protected fun onIncomingCallEnded(ctx: Context, number: String?, start: Date?, end: Date) {
        Log.e("LOG", "IncomingCallEnded ${number} ${end}")
        ringDuration = end.seconds -  start!!.seconds

    }

    protected fun onOutgoingCallStarted(ctx: Context, number: String?, start: Date) {
        Log.e("LOG", "OutgoingCallStarted ${number} ${start}")
    }

    protected fun onOutgoingCallEnded(ctx: Context, number: String?, start: Date?, end: Date) {
        Log.e("LOG", "OutgoingCallEnded ${number} ${end}")
        ringDuration = end.seconds -  start!!.seconds


    }

    protected fun onMissedCall(ctx: Context, number: String?, start: Date?) {
        Log.e("LOG", "MissedCall ${number} ${start}")
        ringDuration = Date().seconds -  start!!.seconds
    }



//    private method callStateChanged() reacts on the various state changes corresponding to phone calls.
    /** * Incoming call: *IDLE -> RINGING when it rings, *-> OFFHOOK when it's answered, *-> IDLE when its hung up * Outgoing call: *IDLE -> OFFHOOK when it dials out,
     *-> IDLE when hung up * */
    @RequiresApi(Build.VERSION_CODES.O)
    private fun callStateChanged(context: Context, state: Int, number: String?) {
        if (lastState == state) {
            return // no change in state}
        }
            when (state) {
                TM.CALL_STATE_RINGING -> {
                    isIncoming = true
                    callStartTime = Date()
                    savedNumber = number
                    onIncomingCallReceived(context, number, callStartTime!!)
                    println(" is received : $isIncoming")

                }
                TM.CALL_STATE_OFFHOOK -> if (lastState != TM.CALL_STATE_RINGING) {
                    isIncoming = false
                    callStartTime = Date()
                    onOutgoingCallStarted(context, savedNumber, callStartTime!!)
                    println(" is received : $isIncoming")

                } else {
                    isIncoming = true
                    callStartTime = Date()
                    onIncomingCallAnswered(context, savedNumber, callStartTime!!)
                    println(" is received : $isIncoming")

                }
                TM.CALL_STATE_IDLE
                -> {
                    if (lastState == TM.CALL_STATE_RINGING) {//Ring but no pickup- a misson
                        onMissedCall(context, savedNumber, callStartTime)
                        println(" is received : $isIncoming")

                    }
                    else if (isIncoming) {
                        onIncomingCallEnded(context, savedNumber, callStartTime, Date())
                        println(" is received : $isIncoming")

                    } else {
                        onOutgoingCallEnded(context, savedNumber, callStartTime, Date())
                        println(" is received : $isIncoming")
                    }

//
                }
            }
            lastState = state
        }
    }
