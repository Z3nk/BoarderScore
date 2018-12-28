package com.boarderscore.boarderscore.fragments

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.boarderscore.boarderscore.R
import com.boarderscore.boarderscore.models.Players
import kotlinx.android.synthetic.main.fragment_compute.*
import java.io.Serializable

class ComputeFragment : Fragment() {

    companion object {
        const val TAG = "ComputeFragment"
        const val RESULT_COMPUTE_ADD_POINTS = 1337
        const val RESULT_COMPUTE_EDIT = 1338
        const val RESULT_COMPUTE_KO = -1
        const val BUNDLE_PLAYER = "BUNDLE_PLAYER"
        const val BUNDLE_NEW_SCORE = "BUNDLE_NEW_SCORE"
        const val BUNDLE_NEW_PSEUDO = "BUNDLE_NEW_PSEUDO"

        fun newInstance(player: Serializable?): ComputeFragment {
            return ComputeFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(BUNDLE_PLAYER, player)
                }
            }
        }
    }

    private var player: Players? = null
    private var currentScoreLD: MutableLiveData<Int> = MutableLiveData<Int>().apply { postValue(0) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_compute, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        player = arguments?.getSerializable(BUNDLE_PLAYER) as? Players
        et_total_score.setText(player?.score.toString())
        et_pseudo.setText(player?.pseudo.toString())

        currentScoreLD.observe(viewLifecycleOwner, Observer {
            tv_actuel_score.text = getString(R.string.Xpoints, it)
        })
        currentScoreLD.postValue(0)

        tab_layout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.text){
                    getString(R.string.add_points) -> {
                        layout_add_points.visibility = View.VISIBLE
                        layout_edit.visibility = View.GONE
                    }
                    getString(R.string.edit) -> {
                        layout_add_points.visibility = View.GONE
                        layout_edit.visibility = View.VISIBLE
                    }
                }
                val i = tab
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })

        et_collector.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                var scoreCollector = et_collector.text.toString().toIntOrNull() ?: 0
                currentScoreLD.postValue(scoreCollector + currentScoreLD.value!!)
            }
        })
        et_lux_or_antiq.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                var scoreLux = et_lux_or_antiq.text.toString().toIntOrNull() ?: 0
                currentScoreLD.postValue(scoreLux + currentScoreLD.value!!)
            }
        })
        et_trio.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                var scoreTrio = et_trio.text.toString().toIntOrNull() ?: 0
                currentScoreLD.postValue(scoreTrio * 3 + currentScoreLD.value!!)
            }
        })

        btn_compute.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt(BUNDLE_NEW_SCORE, player!!.score + currentScoreLD.value!!)
            activity?.setResult(RESULT_COMPUTE_ADD_POINTS, Intent().apply { putExtras(bundle) })
            activity?.finish()
        }

        btn_finish_edit.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt(BUNDLE_NEW_SCORE, et_total_score.text.toString().toInt())
            bundle.putString(BUNDLE_NEW_PSEUDO, et_pseudo.text.toString())
            activity?.setResult(RESULT_COMPUTE_EDIT, Intent().apply { putExtras(bundle) })
            activity?.finish()
        }
    }

}