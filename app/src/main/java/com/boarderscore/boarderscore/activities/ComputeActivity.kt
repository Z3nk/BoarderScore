package com.boarderscore.boarderscore.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate
import com.boarderscore.boarderscore.R
import com.boarderscore.boarderscore.fragments.ComputeFragment

class ComputeActivity : AppCompatActivity() {

    companion object {
        const val BUNDLE_PLAYER = "BUNDLE_PLAYER"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compute)
        AppCompatDelegate.setDefaultNightMode(
            AppCompatDelegate.MODE_NIGHT_AUTO
        )

        var player = intent?.extras?.getSerializable(BUNDLE_PLAYER)
        supportFragmentManager.beginTransaction().replace(R.id.container, ComputeFragment.newInstance(player)).commit()
    }
}
