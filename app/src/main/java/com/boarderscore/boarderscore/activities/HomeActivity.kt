package com.boarderscore.boarderscore.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate
import com.boarderscore.boarderscore.R
import com.boarderscore.boarderscore.fragments.HomeFragment
import com.boarderscore.boarderscore.utils.SharedPref

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        AppCompatDelegate.setDefaultNightMode(
            AppCompatDelegate.MODE_NIGHT_AUTO
        )
        supportFragmentManager.beginTransaction().replace(R.id.container, HomeFragment.newInstance()).commit()
        /*val nbPlayers = SharedPref.getSharedPref(this).getInt(SharedPref.dataNbPlayers, -1)
        if (nbPlayers == -1) {
            SharedPref.getSharedPref(this).edit()?.putInt(SharedPref.dataNbPlayers, 1)?.apply()
        }*/

    }
}
