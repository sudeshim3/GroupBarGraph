package com.example.stackanswer;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();
    BarChart barChart;
    ArrayList<IBarDataSet> dataSets = new ArrayList<>();
    float defaultBarWidth = -1;
    List<String> xAxisValues = new ArrayList<>(Arrays.asList("Jan", "Feb", "March", "April", "May", "June","July", "August", "September", "October", "November", "Decemeber"));


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        barChart = findViewById(R.id.barchart);
        setChart();
    }

    private void setChart() {

        ArrayList<BarEntry> incomeEntries = getIncomeEntries();
        ArrayList<BarEntry> expenseEntries = getExpenseEntries();

        BarDataSet set1, set2;

        set1 = new BarDataSet(incomeEntries, "Income");
        set1.setColor(Color.rgb(65, 168, 121));
        set1.setValueTextColor(Color.rgb(55, 70, 73));
        set1.setValueTextSize(10f);

        set2 = new BarDataSet(expenseEntries, "Expense");
        set2.setColors(Color.rgb(241, 107, 72));
        set2.setValueTextColor(Color.rgb(55, 70, 73));
        set2.setValueTextSize(10f);

        dataSets.add(set1);
        dataSets.add(set2);

        BarData data = new BarData(dataSets);
        barChart.setData(data);
        barChart.getAxisLeft().setAxisMinimum(0);

        barChart.getDescription().setEnabled(false);
        barChart.getAxisRight().setAxisMinimum(0);
        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);
        barChart.setMaxVisibleValueCount(10);
        barChart.setPinchZoom(false);
        barChart.setDrawGridBackground(false);

        barChart.animateY(1400, Easing.EaseInOutQuad);
        barChart.animateXY(3000, 3000);

        Legend l = barChart.getLegend();
        l.setWordWrapEnabled(true);
        l.setTextSize(14);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.CIRCLE);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setCenterAxisLabels(true);
        xAxis.setDrawGridLines(false);
        xAxis.setLabelRotationAngle(-45);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMaximum(getExpenseEntries().size());



        /*IndexAxisValueFormatter formatter = new IndexAxisValueFormatter() {


            @Override
            public String getFormattedValue(float value) {
                if(value < 0)
                    return "";
                else
                    return xAxisValues.get((int)value);

            }
        };*/

        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xAxisValues));


        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.removeAllLimitLines();
        leftAxis.setTypeface(Typeface.DEFAULT);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setTextColor(Color.BLACK);
        leftAxis.setDrawGridLines(false);
        barChart.getAxisRight().setEnabled(false);

        setBarWidth(data);
        barChart.invalidate();

    }

    private void setBarWidth(BarData barData) {
        if (dataSets.size() > 1) {
            float barSpace = 0.02f;
            float groupSpace = 0.3f;
            defaultBarWidth = (1 - groupSpace) / dataSets.size() - barSpace;
            if (defaultBarWidth >= 0) {
                barData.setBarWidth(defaultBarWidth);
            } else {
                Toast.makeText(getApplicationContext(), "Default Barwdith " + defaultBarWidth, Toast.LENGTH_SHORT).show();
            }
            int groupCount = getExpenseEntries().size();
            if (groupCount != -1) {
                barChart.getXAxis().setAxisMinimum(0);
                barChart.getXAxis().setAxisMaximum(0 + barChart.getBarData().getGroupWidth(groupSpace, barSpace) * groupCount);
                barChart.getXAxis().setCenterAxisLabels(true);
            } else {
                Toast.makeText(getApplicationContext(), "no of bar groups is " + groupCount, Toast.LENGTH_SHORT).show();
            }

            barChart.groupBars(0, groupSpace, barSpace); // perform the "explicit" grouping
            barChart.invalidate();
        }
    }

    private ArrayList<BarEntry> getExpenseEntries() {
        ArrayList<BarEntry> expenseEntries = new ArrayList<>();

        expenseEntries.add(new BarEntry(1,1710));
        expenseEntries.add(new BarEntry(2,2480));
        expenseEntries.add(new BarEntry(3,242));
        expenseEntries.add(new BarEntry(4,2409));
        expenseEntries.add(new BarEntry(5,8100));
        expenseEntries.add(new BarEntry(6,1200));
        expenseEntries.add(new BarEntry(7,6570));
        expenseEntries.add(new BarEntry(8,5455));
        expenseEntries.add(new BarEntry(9,15000));
        expenseEntries.add(new BarEntry(10,11340));
        expenseEntries.add(new BarEntry(11,9100));
        expenseEntries.add(new BarEntry(12,6300));
        return expenseEntries;
    }

    private ArrayList<BarEntry> getIncomeEntries() {
        ArrayList<BarEntry> incomeEntries = new ArrayList<>();

        incomeEntries.add(new BarEntry(1, 11300));
        incomeEntries.add(new BarEntry(2, 1390));
        incomeEntries.add(new BarEntry(3, 1190));
        incomeEntries.add(new BarEntry(4, 7200));
        incomeEntries.add(new BarEntry(5, 4790));
        incomeEntries.add(new BarEntry(6, 4500));
        incomeEntries.add(new BarEntry(7, 8000));
        incomeEntries.add(new BarEntry(8, 7034));
        incomeEntries.add(new BarEntry(9, 4307));
        incomeEntries.add(new BarEntry(10, 8762));
        incomeEntries.add(new BarEntry(11, 4355));
        incomeEntries.add(new BarEntry(12, 6000));
        return incomeEntries;
    }
}
