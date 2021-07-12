package com.example.stepgraph

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import kotlin.properties.Delegates

const val debugTag = "StepCounter"

class StepCounter(context: Context) : SensorEventListener {
    var mainActivity = context
    val sensorManager = mainActivity.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    val sensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)


    private var stepCountAtCreation = -1f
    private var timeSliceIndexStepCount = -1f
    private var currentTodayStepCount = -1f
    private var currentTimeSliceStepCount = -1f
    private var rawStepCount = -1f

    fun init() {
        sensorManager?.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI)
    }

    fun getIndexCreationStepCount(shouldReset: Boolean = false) : Float {
        if (shouldReset) {
            stepCountAtCreation = rawStepCount
        } else {
            if (stepCountAtCreation == -1f) {
                stepCountAtCreation = rawStepCount
            }
        }
        return stepCountAtCreation
    }

    fun getIndexTimeSliceStepCount(shouldReset: Boolean = false) : Float {
        if (shouldReset) {
            timeSliceIndexStepCount = rawStepCount
        } else {
            if (timeSliceIndexStepCount == -1f) {
                timeSliceIndexStepCount = rawStepCount
            }
        }
        return timeSliceIndexStepCount
    }

    fun getTodayStepCount(shouldReset: Boolean = false) : Float {
        currentTodayStepCount = rawStepCount - getIndexCreationStepCount(shouldReset)
        return currentTodayStepCount
    }

    fun getTimeSliceStepCount(shouldReset: Boolean = false) : Float {
        currentTimeSliceStepCount = rawStepCount - getIndexTimeSliceStepCount(shouldReset)
        return currentTimeSliceStepCount
    }


    override fun onSensorChanged(event: SensorEvent?) {
        Log.d(debugTag, "Sensor data changed")
        rawStepCount = event!!.values[0]
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        TODO("Not yet implemented")
    }
}