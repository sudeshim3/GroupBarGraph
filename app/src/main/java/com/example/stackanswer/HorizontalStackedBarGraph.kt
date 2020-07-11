package com.example.stackanswer

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.LegendEntry
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter

class HorizontalStackedBarGraph : AppCompatActivity() {

    val startTime = 9f
    val EXTRA_GRAPH_SIZE = 3
    val BAR_WIDTH = 0.3F
    val entries = floatArrayOf(2f, 1f, 3f, 3f, 1f, 2f)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_horizontal_stacked_bar_graph)
        val barChart = findViewById<BarChart>(R.id.timetable_barchart);
        val timeTableEntries = BarEntry(0f, entries)
        val set1 = BarDataSet(listOf(timeTableEntries), "TimeTable")
        set1.setColors(intArrayOf(R.color.colorAvailableSlot, R.color.colorUnAvailableSlot), this)
        set1.setDrawValues(false)
        val barData = BarData(set1)
        barData.barWidth = BAR_WIDTH
        barChart.data = barData
        barChart.description.isEnabled = false
        val legend1= LegendEntry().apply {
            formColor = ContextCompat.getColor(this@HorizontalStackedBarGraph, R.color.colorAvailableSlot)
            label = "Unavailable Slot"
        }
        val legend2= LegendEntry().apply {
            formColor = ContextCompat.getColor(this@HorizontalStackedBarGraph, R.color.colorUnAvailableSlot)
            label = "Available Slot"
        }

        val valueFormatterForTime = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return getString(R.string.time, (startTime + value).toInt())
            }
        }

        barChart.extraBottomOffset = 20f
        barChart.legend.xEntrySpace = 10f
        barChart.legend.setCustom(arrayListOf(legend1, legend2))
        barChart.axisRight.apply {
            setDrawGridLines(false)
            granularity = 1f
            valueFormatter = valueFormatterForTime
            labelCount = entries.size + EXTRA_GRAPH_SIZE
        }
        barChart.axisLeft.isEnabled = false
        barChart.xAxis.isEnabled = false
        barChart.xAxis.axisMinimum = 0f
        barChart.setDrawGridBackground(false)
        barChart.setDrawBarShadow(false)
        barChart.setDrawValueAboveBar(false)
        barChart.invalidate()
    }
}