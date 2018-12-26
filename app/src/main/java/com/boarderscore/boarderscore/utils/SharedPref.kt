package com.boarderscore.boarderscore.utils

import android.content.Context
import android.content.SharedPreferences

object SharedPref{
    const val name = "com.boarderscore"
    const val dataNbPlayers = "dataNbPlayers"
    fun getSharedPref(view: Context): SharedPreferences {
       return view.getSharedPreferences(name, Context.MODE_PRIVATE)
    }
}