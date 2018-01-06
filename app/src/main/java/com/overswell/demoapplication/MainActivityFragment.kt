package com.overswell. demoapplication

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_main.view.*

/**
 * A placeholder fragment containing a simple view.
 */
class MainActivityFragment : Fragment() {

    private val viewModel: SensorViewModel by lazy {
        ViewModelProviders
                .of(activity)
                .get(SensorViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_main, container, false)

        // Add viewmodel data observations here

        viewModel.data.observe(activity, Observer {

            view.textView_x.text = "${it?.x}"
            view.textView_y.text = "${it?.y}"
            view.textView_z.text = "${it?.z}"

            view.imageView.translationX = 100f * (it?.y ?: 0f)
            view.imageView.translationY = 100f * (it?.x ?: 0f)
            view.imageView.rotation = 10f* (it?.z ?: 0f)

        })

        return view
    }
}

