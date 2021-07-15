package com.example.stepgraph

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.TextView
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

class PieChartHandler(view: View){
    //    val context = parentContext
    val view = view
    val debugTag = "PieChartHandler_DEBUG"
    var pieChart = view.findViewById<PieChart>(R.id.PieChartBaby)
    val currentStepsText = view.findViewById<TextView>(R.id.currentStepsText)
    val stepsGoalText = view.findViewById<TextView>(R.id.stepGoalText)
    var animationTime = 1000

    fun generatePieData(steps: Float, goal: Float) {
        Log.d(debugTag, "Generating pie chart data... Steps: $steps, goal $goal")
        val pieElements = ArrayList<PieEntry>()
        val pieColors = ArrayList<Int>()
        if (steps <= goal) {
//          if goal has not been met, calculate normally
            pieElements.add(PieEntry(steps, 0))
            pieElements.add(PieEntry(goal - steps, 1))
        } else {
//          if goal has been met, set red to none to avoid weird pie chart
            pieElements.add(PieEntry(steps, ""))
            pieElements.add(PieEntry(0f, ""))
        }
        pieColors.add(Color.parseColor("#00b300"))
        pieColors.add(Color.parseColor("#ff0000"))

        val pieDataSet = PieDataSet(pieElements, "")
        pieDataSet.colors = pieColors
        var pieData = PieData(pieDataSet)
        pieData.setValueTextSize(0f)

        formatPieData()

        pieChart.data = pieData
//        pieChart.invalidate()

        currentStepsText.text = pieElements[0].toString()
        stepsGoalText.text = pieElements[1].toString()
    }

    private fun formatPieData() {
        pieChart.setUsePercentValues(false)
        pieChart.setDrawMarkers(false)
        pieChart.isDrawHoleEnabled = true
        pieChart.setHoleColor(R.color.design_default_color_background)
        pieChart.holeRadius = 70f
        pieChart.description.isEnabled = false
        pieChart.setDrawEntryLabels(false)
        pieChart.legend.isEnabled = false
    }

    fun triggerAnimate() {
        Log.d(debugTag, "Pie chart animation triggered")
        pieChart.animateY(animationTime, com.github.mikephil.charting.animation.Easing.EaseInOutQuad)
    }
}