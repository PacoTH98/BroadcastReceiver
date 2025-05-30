package com.example.broadcastreceiver

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

object SharedPrefManager {
    private const val PREF_NAME = "AutoReplyPrefs"
    private const val KEY_NUMBER = "saved_number"
    private const val KEY_MESSAGE = "saved_message"

    fun saveNumber(context: Context, number: String) {
        val prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(KEY_NUMBER, number).apply()
        Log.d("SharedPrefManager", "Número guardado: $number")
    }

    fun saveMessage(context: Context, message: String) {
        val prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(KEY_MESSAGE, message).apply()
        Log.d("SharedPrefManager", "Mensaje guardado: $message")
    }

    fun getSavedNumber(context: Context): String {
        val prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val number = prefs.getString(KEY_NUMBER, "") ?: ""
        Log.d("SharedPrefManager", "Número recuperado: $number")
        return number
    }

    fun getSavedMessage(context: Context): String {
        val prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val message = prefs.getString(KEY_MESSAGE, "Lo siento, no puedo atender en este momento.") ?: ""
        Log.d("SharedPrefManager", "Mensaje recuperado: $message")
        return message
    }
}
