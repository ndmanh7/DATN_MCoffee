package com.example.mcoffee.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

object LoginConfig {
    private const val SHARED_PREFS = "share_preference"
    private const val SHARED_PREFS_NAME = "share_preference_name"

    @SuppressLint("CommitPrefEdits")
    fun loginState(context: Context, state: String) {
        val sharedPreferences = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
        val sharedPreferencesEditor = sharedPreferences.edit() as SharedPreferences.Editor
        sharedPreferencesEditor.apply {
            putString(SHARED_PREFS_NAME, state)
            apply()
        }
    }

    fun isLogin(context: Context, state: String): Boolean {
        val sharedPreferences = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
        val stateFromSharedPrefs = sharedPreferences.getString(SHARED_PREFS_NAME, "")
        return stateFromSharedPrefs!! == state
    }
}