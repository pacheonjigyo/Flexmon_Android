package com.xxmassdeveloper.mpchartexample.custom;

import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.formatter.ValueFormatter;

/**
 * Created by philipp on 02/06/16.
 */
public class DayAxisValueFormatter extends ValueFormatter
{
    private final BarLineChartBase<?> chart;

    public DayAxisValueFormatter(BarLineChartBase<?> chart) {
        this.chart = chart;
    }

    @Override
    public String getFormattedValue(float value) {

        int yy, mm, dd;

        yy = (int) (value / 10000);
        mm = (int) (value % 10000) / 100;
        dd = (int) (value % 100);

        if(dd == 0)
        {
            mm--;
            dd = 30;
        }

        return yy + "/" + mm + "/" + dd;
    }
}
