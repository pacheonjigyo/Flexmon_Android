package com.xxmassdeveloper.mpchartexample.notimportant;

import android.animation.ValueAnimator;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.xxmassdeveloper.mpchartexample.R;
import com.xxmassdeveloper.mpchartexample.custom.DayAxisValueFormatter;
import com.xxmassdeveloper.mpchartexample.custom.MyValueFormatter;
import com.xxmassdeveloper.mpchartexample.custom.XYMarkerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class FlexmonDashboard extends DemoBase implements OnChartValueSelectedListener {
    ImageButton imagebutton1, imagebutton2, imageButton3, imageButton4, imageButton5, imageButton6, imageButton7, button2, button3, button4, button5, button6, button7;

    MyDBChartHelper myDBHelper;

    TextView textView3, textView4, textView9, textView11, textView13, textView14;

    private BarChart barChart;
    private PieChart pieChart;

    private int state = 0;
    private int state2 = 1;
    private int state3 = 1;
    private int state4 = 0;

    private int yy1, mm1, dd1, yy2, mm2, dd2;

    private float sumY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.flexmon_dashboard);

        myDBHelper = new MyDBChartHelper(getApplicationContext(), "flexmon_chart.db", null, 1);

        getNowTime();

        final AlphaAnimation fadeIn = new AlphaAnimation(0.0f, 1.0f) ;

        fadeIn.setDuration(500);
        fadeIn.setFillAfter(true);

        textView3 = findViewById(R.id.textView3);
        textView4 = findViewById(R.id.textView4);
        textView9 = findViewById(R.id.textView9);
        textView11 = findViewById(R.id.textView11);
        textView13 = findViewById(R.id.textView13);
        textView14 = findViewById(R.id.textView14);

        textView3.setText(yy1 + "/" + mm1 + "/" + dd1);
        textView4.setText(yy2 + "/" + mm2 + "/" + dd2);

        imagebutton1 = findViewById(R.id.imageButton1);
        imagebutton2 = findViewById(R.id.imageButton2);
        imageButton3 = findViewById(R.id.imageButton3);
        imageButton4 = findViewById(R.id.imageButton4);
        imageButton5 = findViewById(R.id.imageButton5);
        imageButton6 = findViewById(R.id.imageButton6);
        imageButton7 = findViewById(R.id.imageButton7);

        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
        button6 = findViewById(R.id.button6);
        button7 = findViewById(R.id.button7);

        imagebutton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "기능 없음", Toast.LENGTH_SHORT).show();
            }
        });

        imagebutton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "기능 없음", Toast.LENGTH_SHORT).show();
            }
        });

        imageButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog_datePicker(textView3, 1);
            }
        });

        imageButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog_datePicker(textView4, 2);
            }
        });

        imageButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "기능 없음", Toast.LENGTH_SHORT).show();
            }
        });

        imageButton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(state4 > 0)
                    state4 --;
                else
                    state4 = 4;

                setPrediction(state4);

                textView14.startAnimation(fadeIn);

            }
        });

        imageButton7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(state4 < 4)
                    state4 ++;
                else
                    state4 = 0;

                setPrediction(state4);

                textView14.startAnimation(fadeIn);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                state = 1;
                setBarData();
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                state = 2;
                setBarData();
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                state2 = 1;
                setPieData();
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                state2 = 2;
                setPieData();
            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                state3 = 1;
                setPrediction(state4);
            }
        });

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                state3 = 2;
                setPrediction(state4);
            }
        });

        barChart = findViewById(R.id.chart1);

        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);
        barChart.getDescription().setEnabled(false);
        barChart.setMaxVisibleValueCount(60);
        barChart.setPinchZoom(false);
        barChart.setDrawGridBackground(false);

        ValueFormatter xAxisFormatter = new DayAxisValueFormatter(barChart);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTypeface(tfLight);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(5);
        xAxis.setValueFormatter(xAxisFormatter);

        ValueFormatter custom = new MyValueFormatter("");

        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setTypeface(tfLight);
        leftAxis.setLabelCount(8, false);
        leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinimum(0f);

        YAxis rightAxis = barChart.getAxisRight();
        rightAxis.setEnabled(false);

        Legend l = barChart.getLegend();
        l.setEnabled(false);

        XYMarkerView mv = new XYMarkerView(this, xAxisFormatter);
        mv.setChartView(barChart);
        barChart.setMarker(mv);

        pieChart = findViewById(R.id.chart2);
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setHoleRadius(55f);
        pieChart.setTransparentCircleRadius(60f);
        pieChart.setDrawCenterText(true);
        pieChart.setRotationAngle(0);
        pieChart.setRotationEnabled(true);
        pieChart.setHighlightPerTapEnabled(true);
        pieChart.setOnChartValueSelectedListener(this);
        pieChart.setDrawEntryLabels(false);

        l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);
        l.setTypeface(tfLight);

        setBarData();
        setPieData();
        setPrediction(state4);

        mHandler.sendEmptyMessage(0);
    }

    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (state4 == 0) {
                ValueAnimator animator1;

                ArrayList<BarEntry> valuesTemp;

                float averageY1;

                long now = System.currentTimeMillis();

                Date date = new Date(now);

                SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd HH:mm");
                sdfNow.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));

                String dateString = sdfNow.format(date);

                String[] nowArray = dateString.split(" ");
                String[] dateArray = nowArray[0].split("/");
                String[] timeArray = nowArray[1].split(":");

                int yy = Integer.parseInt(dateArray[0]);
                int mm = Integer.parseInt(dateArray[1]);
                int dd = Integer.parseInt(dateArray[2]);

                int h = Integer.parseInt(timeArray[0]);
                int m = Integer.parseInt(timeArray[1]);

                valuesTemp = myDBHelper.getResult(state3, yy, mm, dd, h, m);

                averageY1 = getSummary(valuesTemp);

                if (averageY1 == 0f) {
                    String unit = null;

                    switch (state3) {
                        case 1: {
                            unit = "kW";

                            break;
                        }

                        case 2: {
                            unit = "kcal";

                            break;
                        }

                        default:
                            break;
                    }

                    textView14.setText(String.format("%.1f", averageY1) + unit);
                } else {
                    animator1 = ValueAnimator.ofFloat(0f, averageY1);
                    animator1.setDuration(2000);

                    animator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        public void onAnimationUpdate(ValueAnimator animation) {
                            String unit = null;

                            switch (state3) {
                                case 1: {
                                    unit = "kW";

                                    break;
                                }

                                case 2: {
                                    unit = "kcal";

                                    break;
                                }

                                default:
                                    break;
                            }

                            textView14.setText(String.format("%.1f", animation.getAnimatedValue()) + unit);
                        }
                    });

                    animator1.start();
                }

                textView13.setText("실시간 사용량");
            }

            mHandler.sendEmptyMessageDelayed(0, 5000);
        }
    };

    private void setPrediction(int state) {
        ValueAnimator animator, animator1, animator3, animator4 = null;

        ArrayList<BarEntry> valuesTemp;

        float increased, predicted, averageY1, averageY2, averageY3;

        long now = System.currentTimeMillis();

        Date date = new Date(now);

        SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd");
        sdfNow.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));

        String dateString = sdfNow.format(date);

        String[] nowArray = dateString.split(" ");
        String[] dateArray = nowArray[0].split("/");

        int yy = Integer.parseInt(dateArray[0]);
        int mm = Integer.parseInt(dateArray[1]);
        int dd = Integer.parseInt(dateArray[2]);

        valuesTemp = myDBHelper.getResult(state3, yy, mm, dd);
        averageY1 = getSummary(valuesTemp);

        valuesTemp = myDBHelper.getResult(state3, yy, mm - 1);
        averageY2 = getSummary(valuesTemp);

        valuesTemp = myDBHelper.getResult(state3, yy, mm);
        averageY3 = getSummary(valuesTemp);

        increased = (averageY3 - averageY2) / averageY2 * 100.0f;
        predicted = (averageY2 + averageY3) / 2;

        switch(state) {
            case 0: {
                textView14.setText("...");
                textView13.setText("실시간 사용량");

                break;
            }

            case 1: {
                animator1 = ValueAnimator.ofFloat(0f, averageY1);

                animator1.setDuration(2000);
                animator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    public void onAnimationUpdate(ValueAnimator animation) {
                        String unit = null;

                        switch(state3){
                            case 1:{
                                unit = "kW";

                                break;
                            }

                            case 2:{
                                unit = "kcal";

                                break;
                            }

                            default: break;
                        }

                        textView14.setText(String.format("%.1f", animation.getAnimatedValue()) + unit);
                    }
                });

                animator1.start();

                textView13.setText("당일 사용량");

                break;
            }

            case 2: {
                animator3 = ValueAnimator.ofFloat(0f, averageY3);

                animator3.setDuration(2000);
                animator3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    public void onAnimationUpdate(ValueAnimator animation) {
                        String unit = null;

                        switch(state3){
                            case 1:{
                                unit = "kW";

                                break;
                            }

                            case 2:{
                                unit = "kcal";

                                break;
                            }

                            default: break;
                        }

                        textView14.setText(String.format("%.1f", animation.getAnimatedValue()) + unit);
                    }
                });

                animator3.start();

                textView13.setText("당월 사용량");

                break;
            }

            case 3: {
                if(Float.isNaN(increased) || Float.isInfinite(increased))
                    textView14.setText("...");
                else {
                    animator = ValueAnimator.ofFloat(0f, increased);

                    animator.setDuration(2000);
                    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        public void onAnimationUpdate(ValueAnimator animation) {
                            switch(state3){
                                case 1:{
                                    break;
                                }

                                case 2:{
                                    break;
                                }

                                default: break;
                            }

                            textView14.setText(String.format("%.1f", animation.getAnimatedValue()) + "%");
                        }
                    });

                    animator.start();
                }

                textView13.setText("전월 대비 증감");

                break;
            }

            case 4: {
                animator4 = ValueAnimator.ofFloat(0f, predicted);

                animator4.setDuration(2000);
                animator4.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    public void onAnimationUpdate(ValueAnimator animation) {
                        String unit = null;

                        switch(state3){
                            case 1:{
                                unit = "kW";

                                break;
                            }

                            case 2:{
                                unit = "kcal";

                                break;
                            }

                            default: break;
                        }

                        textView14.setText(String.format("%.1f", animation.getAnimatedValue()) + unit);
                    }
                });

                animator4.start();

                textView13.setText("다음 달 예상");

                break;
            }

            default: break;
        }
    }

    public void openDialog_datePicker(final TextView textView, final int type) {
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month++;

                textView.setText(year+"/"+month+"/"+day);

                switch(type){
                    case 1:{
                        yy1 = year;
                        mm1 = month;
                        dd1 = day;

                        break;
                    }

                    case 2:{
                        yy2 = year;
                        mm2 = month;
                        dd2 = day;

                        break;
                    }

                    default: break;
                }

                setBarData();
            }
        };

        DatePickerDialog dialog = null;
        
        switch(type){
            case 1:{
                dialog = new DatePickerDialog(this, listener, yy1, mm1 - 1, dd1);

                break;
            }

            case 2:{
                dialog = new DatePickerDialog(this, listener, yy2, mm2 - 1, dd2);

                break;
            }

            default: break;
        }

        dialog.show();
    }

    public void getNowTime()
    {
        long now = System.currentTimeMillis();

        Date date = new Date(now);

        SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd");
        sdfNow.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));

        String dateString = sdfNow.format(date);

        String[] nowArray = dateString.split(" ");
        String[] dateArray = nowArray[0].split("/");

        yy2 = Integer.parseInt(dateArray[0]);
        mm2 = Integer.parseInt(dateArray[1]);
        dd2 = Integer.parseInt(dateArray[2]);

        yy1 = yy2;
        mm1 = mm2;
        dd1 = dd2 - 7;
    }

    private float getAverageY(ArrayList<BarEntry> arrayList) {
        float sum = 0f;
        float output;

        String[] temp;

        for(int i = 0; i < arrayList.size(); i++)
        {
            temp = String.valueOf(arrayList.get(i)).split(" ");

            if(temp[4] != "")
                sum += Float.parseFloat(temp[4]);
        }

        if(state == 0) {
            String strNumber = String.format("%.1f", sum / arrayList.size());

            output = Float.parseFloat(strNumber);

            if (Float.isNaN(output))
                return 0f;
            else
                return Float.parseFloat(strNumber);
        }
        else
        {
            return sum;
        }
    }

    private void setBarData() {
        ArrayList<BarEntry> values = new ArrayList<>();
        ArrayList<BarEntry> valuesTemp;

        int averageX;

        float averageY;

        for(int i = 0; i <= (dd2 - dd1); i++)
        {
            valuesTemp = myDBHelper.getResult(state, yy1, mm1, (dd1 + i));

            averageX = yy1 % 100 * 10000 + mm1 * 100 + (dd1 + i) * 1;
            averageY = getAverageY(valuesTemp);

            values.add(new BarEntry(averageX, averageY));
        }

        BarDataSet set1;

        set1 = new BarDataSet(values, "Analysis");

        set1.setDrawIcons(false);

        switch(state) {
            case 0: {
                set1.setColor(Color.argb(255, 100, 100, 255));

                break;
            }

            case 1: {
                set1.setColor(Color.argb(255, 255, 100, 100));

                break;
            }

            case 2: {
                set1.setColor(Color.argb(255, 255, 200, 100));

                break;
            }

            default: break;
        }

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        BarData data = new BarData(dataSets);
        data.setValueTextSize(10f);
        data.setValueTypeface(tfLight);
        data.setBarWidth(0.3f);

        barChart.setData(data);
        barChart.invalidate();
        barChart.animateY(1000);
    }

    private float getSummary(ArrayList<BarEntry> arrayList) {
        float sum = 0f;

        String[] temp;

        for(int i = 0; i < arrayList.size(); i++)
        {
            temp = String.valueOf(arrayList.get(i)).split(" ");

            if(temp[4] != "")
                sum += Float.parseFloat(temp[4]);
        }

        return sum;
    }

    private void setPieData() {
        ArrayList<PieEntry> entries = new ArrayList<>();
        ArrayList<BarEntry> valuesTemp;

        float averageY;
        sumY = 0f;

        for(int i = 0; i < 12; i++)
        {
            valuesTemp = myDBHelper.getResult(state2, yy1, i + 1);

            averageY = getSummary(valuesTemp);

            if(averageY != 0f) {
                entries.add(new PieEntry(averageY, (i + 1) + "월"));

                sumY += averageY;
            }
        }

        PieDataSet dataSet = new PieDataSet(entries, "");

        dataSet.setDrawIcons(false);
        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        ArrayList<Integer> colors = new ArrayList<>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);

        data.setValueFormatter(new PercentFormatter(pieChart));
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.TRANSPARENT);
        data.setValueTypeface(tfLight);

        pieChart.setData(data);
        pieChart.highlightValues(null);
        pieChart.invalidate();
        pieChart.animateY(1000, Easing.EaseInOutQuad);
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

        if (h == null)
            return;

        openDialog_pieChart(h);
    }

    public void openDialog_pieChart(Highlight h) {
        final Dialog dlg = new Dialog(this);

        dlg.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dlg.setContentView(R.layout.flexmon_dialog_piechart);

        dlg.show();

        TextView textView1 = dlg.findViewById(R.id.textView1);
        TextView textView3 = dlg.findViewById(R.id.textView3);
        TextView textView5 = dlg.findViewById(R.id.textView5);

        Button button1 = dlg.findViewById(R.id.button1);

        int x = (int) h.getX() + 1;
        String y = String.format("%.1f", (h.getY() / sumY) * 100f);

        textView1.setText("상세 보기");

        switch(state2)
        {
            case 1: {
                textView3.setText(h.getY() + "kW");

                break;
            }

            case 2: {
                textView3.setText(h.getY() + "kcal");

                break;
            }

            default: break;
        }

        textView5.setText(y + "% 차지");

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dlg.dismiss();
            }
        });
    }

    @Override
    public void onNothingSelected() { }

    @Override
    protected void saveToGallery() { }
}
