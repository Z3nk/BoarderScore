package com.boarderscore.boarderscore.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.boarderscore.boarderscore.R

class PeanutClubFragment : Fragment() {

    companion object {
        fun newInstance(): PeanutClubFragment{
            return PeanutClubFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_peanut_club, container, false)
    }
}