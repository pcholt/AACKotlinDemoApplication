package com.overswell.demoapplication

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(SensorViewModel::class.java)
    }

    private val sensorManager by lazy {
        application.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    private val sensor: Sensor? by lazy {
        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }

    private val sensorEventListener by lazy {
        object : SensorEventListener {

            override fun onAccuracyChanged(sensor1: Sensor?, newAccuracy: Int) {
                viewModel.accuracyChange(newAccuracy)
            }

            override fun onSensorChanged(sensorEvent: SensorEvent?) {
                viewModel.sensorChange(sensorEvent?.values)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(sensorEventListener,
                sensor, SensorManager.SENSOR_DELAY_GAME)
    }

    override fun onPause() {
        sensorManager.unregisterListener(sensorEventListener)
        super.onPause()
    }

}

class SensorViewModel : ViewModel() {

    val data: MutableLiveData<SensorData> = MutableLiveData()
    val accuracy: MutableLiveData<Int> = MutableLiveData()
    val sensorCache = SensorData()

    val x get() = data.value?.x ?: 0f
    val y get() = data.value?.y ?: 0f
    val z get() = data.value?.z ?: 0f

    fun sensorChange(values: FloatArray?) {
        sensorCache.fromSensor(values)
        data.value = sensorCache
    }

    fun accuracyChange(accuracy: Int) {
        this.accuracy.value = accuracy
    }

}

data class SensorData(var x:Float=0f, var y:Float=0f, var z:Float=0f) {
    fun fromSensor(data:FloatArray?) {
        if (data?.size?:0 > 0) x = data?.get(0) ?: 0f
        if (data?.size?:0 > 1) y = data?.get(1) ?: 0f
        if (data?.size?:0 > 2) z = data?.get(2) ?: 0f
    }
}
