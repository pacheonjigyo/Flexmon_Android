package com.github.mikephil.charting.formatter;

import java.text.DecimalFormat;

public class CustomTempValueFormatter extends ValueFormatter {

    private DecimalFormat mFormat;

    public CustomTempValueFormatter() {
        mFormat = new DecimalFormat("0.0");
    }

    @Override
    public String getFormattedValue(float value) {
        return mFormat.format(value) + "℃";
    }
}