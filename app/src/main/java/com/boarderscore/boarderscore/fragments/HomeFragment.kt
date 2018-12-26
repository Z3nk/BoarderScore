package com.boarderscore.boarderscore.fragments

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.transition.AutoTransition
import android.transition.Fade
import android.transition.Scene
import android.transition.TransitionManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.boarderscore.boarderscore.R
import com.boarderscore.boarderscore.utils.SharedPref
import com.boarderscore.boarderscore.utils.intLiveData
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {


    var mSceneRoot: ViewGroup? = null
    var mCollapsedScene: Scene? = null
    var mExpandedScene: Scene? = null

    companion object {
        const val TAG = "HomeFragment"
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        childFragmentManager.beginTransaction().replace(R.id.subcontainer, PeanutClubFragment.newInstance()).commit()
        mSceneRoot = view.findViewById(R.id.settings)
        mCollapsedScene = Scene.getSceneForLayout(mSceneRoot, R.layout.fragment_settings_collapsed, activity)
        mExpandedScene = Scene.getSceneForLayout(mSceneRoot, R.layout.fragment_settings_extended, activity)

        sliding_layout.addPanelSlideListener(object : SlidingUpPanelLayout.PanelSlideListener {
            override fun onPanelSlide(panel: View, slideOffset: Float) {
                Log.i(TAG, "onPanelSlide, offset $slideOffset")
            }

            override fun onPanelStateChanged(
                panel: View,
                previousState: SlidingUpPanelLayout.PanelState,
                newState: SlidingUpPanelLayout.PanelState
            ) {
                Log.i(TAG, "onPanelStateChanged $newState")
                when (newState) {
                    SlidingUpPanelLayout.PanelState.EXPANDED -> {
                        val transition = AutoTransition()
                        TransitionManager.go(mExpandedScene, transition)
                    }
                    SlidingUpPanelLayout.PanelState.COLLAPSED -> {
                        val transition = Fade()
                        transition.duration = 150
                        TransitionManager.go(mCollapsedScene, transition)
                        loadCollapsedSettings()
                    }
                }
            }
        }
        )

        TransitionManager.go(mCollapsedScene, AutoTransition())
        loadCollapsedSettings()
    }

    private fun loadCollapsedSettings() {
        var nbPlayers = SharedPref.getSharedPref(activity!!).getInt(SharedPref.dataNbPlayers, -1)

        SharedPref.getSharedPref(activity!!).intLiveData(SharedPref.dataNbPlayers, -1)
            .observe(viewLifecycleOwner, Observer {
                activity?.findViewById<TextView>(R.id.tv_nb_players)?.text = getString(R.string.nb_players, it)
            })
        activity?.findViewById<ImageView>(R.id.removePlayer)?.setOnClickListener {
            nbPlayers--
            SharedPref.getSharedPref(activity!!).edit()?.putInt(SharedPref.dataNbPlayers, nbPlayers)?.apply()
        }
        activity?.findViewById<ImageView>(R.id.addPlayer)?.setOnClickListener {
            nbPlayers++
            SharedPref.getSharedPref(activity!!).edit()?.putInt(SharedPref.dataNbPlayers, nbPlayers)?.apply()
        }
    }
}