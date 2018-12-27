package com.boarderscore.boarderscore.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate
import com.boarderscore.boarderscore.R
import com.boarderscore.boarderscore.fragments.ComputeFragment
import com.boarderscore.boarderscore.fragments.HomeFragment
import com.boarderscore.boarderscore.utils.SharedPref
import kotlinx.android.synthetic.main.activity_compute.*

class ComputeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compute)
        AppCompatDelegate.setDefaultNightMode(
            AppCompatDelegate.MODE_NIGHT_AUTO
        )
        supportFragmentManager.beginTransaction().replace(R.id.container, ComputeFragment.newInstance()).commit()
    }
}
