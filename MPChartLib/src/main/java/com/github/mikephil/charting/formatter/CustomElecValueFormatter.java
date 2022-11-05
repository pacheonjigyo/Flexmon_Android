package com.github.mikephil.charting.formatter;

import java.text.DecimalFormat;

public class CustomElecValueFormatter extends ValueFormatter {

    private DecimalFormat mFormat;

    public CustomElecValueFormatter() {
        mFormat = new DecimalFormat("###,###,##0");
    }

    @Override
    public String getFormattedValue(float value) {
        return mFormat.format(value) + "kW";
    }
}