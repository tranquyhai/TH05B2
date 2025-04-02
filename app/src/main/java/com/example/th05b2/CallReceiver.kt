package com.example.th05b2

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager
import android.telephony.SmsManager
import android.util.Log

class CallReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == TelephonyManager.ACTION_PHONE_STATE_CHANGED) {
            val state = intent.getStringExtra(TelephonyManager.EXTRA_STATE)
            val incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)

            if (state == TelephonyManager.EXTRA_STATE_RINGING && incomingNumber != null) {
                Log.d("CallReceiver", "Cuộc gọi đến từ: $incomingNumber")
            }

            if (state == TelephonyManager.EXTRA_STATE_IDLE && incomingNumber != null) {
                Log.d("CallReceiver", "Cuộc gọi nhỡ từ: $incomingNumber")
                sendAutoReplySMS(incomingNumber, context)
            }
        }
    }

    private fun sendAutoReplySMS(phoneNumber: String, context: Context) {
        try {
            val smsManager = SmsManager.getDefault()
            val message = "Xin chào! Tôi hiện không thể nghe máy. Tôi sẽ gọi lại sau."
            smsManager.sendTextMessage(phoneNumber, null, message, null, null)
            Log.d("CallReceiver", "Đã gửi tin nhắn tự động đến: $phoneNumber")
        } catch (e: Exception) {
            Log.e("CallReceiver", "Lỗi khi gửi tin nhắn: ${e.message}")
        }
    }
}