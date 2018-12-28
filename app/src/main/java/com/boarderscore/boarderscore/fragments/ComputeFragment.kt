package com.boarderscore.boarderscore.fragments

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.boarderscore.boarderscore.R
import kotlinx.android.synthetic.main.fragment_compute.*

class ComputeFragment : Fragment() {

    companion object {
        const val TAG = "ComputeFragment"
        const val RESULT_COMPUTE_OK = 1337
        const val RESULT_COMPUTE_KO = -1
        fun newInstance(): ComputeFragment {
            return ComputeFragment()
        }
    }

    private var currentScoreLD: MutableLiveData<Int> = MutableLiveData<Int>().apply { postValue(0) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_compute, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentScoreLD.observe(viewLifecycleOwner, Observer {
            tv_actuel_score.text = getString(R.string.Xpoints, it)
        })
        currentScoreLD.postValue(0)

        et_collector.isPressed = true
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
            bundle.putInt(HomeFragment.KEY_COMPUTE, currentScoreLD.value!!)
            activity?.setResult(RESULT_COMPUTE_OK, Intent().apply { putExtras(bundle) })
            activity?.finish()
        }
    }

}