package com.example.branchtask.common

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TokenManager @Inject constructor(@ApplicationContext context: Context) {
    private var prefs: SharedPreferences =
        context.getSharedPreferences(constants.PREF_FILE, Context.MODE_PRIVATE)

    fun saveToken(token: String?) {
        val editor = prefs.edit()
        editor.putString(constants.USER_TOKEN, token)
        editor.apply()
    }

    fun getToken(): String? {
        return prefs.getString(constants.USER_TOKEN, null)
    }
}