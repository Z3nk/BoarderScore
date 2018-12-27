package com.boarderscore.boarderscore.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
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
import com.boarderscore.boarderscore.activities.ComputeActivity
import com.boarderscore.boarderscore.adapters.PlayersAdapter
import com.boarderscore.boarderscore.models.Players
import com.boarderscore.boarderscore.utils.StaticFields.maxPlayers
import com.boarderscore.boarderscore.utils.StaticFields.nbPlayers
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {
    var mSceneRoot: ViewGroup? = null
    var mCollapsedScene: Scene? = null
    var mExpandedScene: Scene? = null
    val mList = ArrayList<Players>()

    companion object {
        const val TAG = "HomeFragment"
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mSceneRoot = view.findViewById(R.id.settings)
        mCollapsedScene = Scene.getSceneForLayout(mSceneRoot, R.layout.fragment_settings_collapsed, activity)
        mExpandedScene = Scene.getSceneForLayout(mSceneRoot, R.layout.fragment_settings_extended, activity)

        listOfPlayers.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = PlayersAdapter(mList) {
                startActivityForResult(Intent(activity!!, ComputeActivity::class.java), 0)
            }
        }


        sliding_layout.addPanelSlideListener(object : SlidingUpPanelLayout.PanelSlideListener {
            override fun onPanelSlide(panel: View, slideOffset: Float) {
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
        mList.add(Players(getString(R.string.playerX, nbPlayers)))
        loadCollapsedSettings()
    }

    private fun loadCollapsedSettings() {
        /*SharedPref.getSharedPref(activity!!).intLiveData(SharedPref.dataNbPlayers, -1)
            .observe(viewLifecycleOwner, Observer {
                while (it!! > mList.size) {
                    mList.add(Players(getString(R.string.playerX, mList.size + 1)))
                    listOfPlayers.adapter.notifyDataSetChanged()
                }
            })*/
        activity?.findViewById<ImageView>(R.id.editPlayer)?.setOnClickListener {
            mList.forEach { player -> player.editable = !player.editable }
            listOfPlayers.adapter?.notifyDataSetChanged()
        }
        activity?.findViewById<ImageView>(R.id.addPlayer)?.setOnClickListener {
            if (nbPlayers < maxPlayers) {
                activity?.findViewById<TextView>(R.id.tv_nb_players)?.text = getString(R.string.nb_players, mList.size)
                mList.forEach { player -> player.editable = false }
                mList.add(Players(getString(R.string.playerX, ++nbPlayers)))
                listOfPlayers.adapter?.notifyDataSetChanged()
            }
        }
    }
}