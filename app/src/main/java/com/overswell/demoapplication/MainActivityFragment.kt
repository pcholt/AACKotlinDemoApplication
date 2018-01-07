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

    // Get the SensorViewModel from the activity
    val viewModel by lazy { ViewModelProviders.of(activity).get(SensorViewModel::class.java)}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_main, container, false)

        // Add data observations here
        viewModel.data.observe(activity, Observer {
            a ->
            val (x,y,z) = listOf(a?.x?:0f,a?.y?:0f,a?.z?:0f)

            view.text_view_x.text = String.format("%02.5f", x)
            view.text_view_y.text = String.format("%02.5f", y)
            view.text_view_z.text = String.format("%02.5f", z)

            view.progressBar_x.progress = (x*10+100).toInt()
            view.progressBar_y.progress = (y*10+100).toInt()
            view.progressBar_z.progress = (z*10+100).toInt()
        })

        return view
    }
}

