package com.github.mikephil.charting.formatter;

import java.text.DecimalFormat;

public class CustomTimeValueFormatter extends ValueFormatter {

    private DecimalFormat mFormat;

    public CustomTimeValueFormatter() {
        mFormat = new DecimalFormat("00");
    }

    @Override
    public String getFormattedValue(float value) {
        return mFormat.format(value) + "시";
    }
}