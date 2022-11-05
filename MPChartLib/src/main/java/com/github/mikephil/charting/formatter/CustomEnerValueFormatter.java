package com.github.mikephil.charting.formatter;

import java.text.DecimalFormat;

public class CustomEnerValueFormatter extends ValueFormatter {

    private DecimalFormat mFormat;

    public CustomEnerValueFormatter() {
        mFormat = new DecimalFormat("###,###,##0");
    }

    @Override
    public String getFormattedValue(float value) {
        return mFormat.format(value) + "kcal";
    }
}