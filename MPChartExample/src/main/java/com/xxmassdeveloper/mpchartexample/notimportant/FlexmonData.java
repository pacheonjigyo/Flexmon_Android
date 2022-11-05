
package com.xxmassdeveloper.mpchartexample.notimportant;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

//축 글자 크기 증가, 값 표시 크기 증가,

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.CustomElecValueFormatter;
import com.github.mikephil.charting.formatter.CustomEnerValueFormatter;
import com.github.mikephil.charting.formatter.CustomTempValueFormatter;
import com.github.mikephil.charting.formatter.CustomTimeValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.xxmassdeveloper.mpchartexample.R;
import com.xxmassdeveloper.mpchartexample.custom.CustomColorPicker;
import com.xxmassdeveloper.mpchartexample.custom.MyMarkerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class FlexmonData extends DemoBase implements OnChartGestureListener, OnChartValueSelectedListener {

    private ArrayAdapter adapter3;

    private ArrayList<Entry>[] myDataSet = new ArrayList[3];
    private ArrayList<String> items;

    private EditText editText;

    private ImageButton button, button1, button2, button3;

    private ListView listView;
    private LineChart chart;
    private LimitLine ll1, ll2, ll3;

    private Spinner spinner;
    private String[] arraySpinner = new String[] {"기온", "전력량", "에너지소비량"};

    private TextView textView10;

    private boolean switch1State, switch2State, switch3State = false;

    private float[] limitNumber = {0f, 0f, 0f};

    private int color;
    private int state = -1;
    private int yy, mm, dd;

    MyDBChartHelper myDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myDBHelper = new MyDBChartHelper(getApplicationContext(), "flexmon_chart.db", null, 1);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.flexmon_data);
        setTitle("MultiLineChartActivity");

        items = new ArrayList<String>();

        adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);

        listView = findViewById(R.id.listView);
        listView.setAdapter(adapter3);

        chart = findViewById(R.id.chart1);
        chart.setOnChartValueSelectedListener(this);

        chart.setDrawGridBackground(false);
        chart.getDescription().setEnabled(false);
        chart.setDrawBorders(false);

        MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);
        mv.setChartView(chart);

        chart.setMarker(mv);

        chart.getAxisLeft().setEnabled(true);
        chart.getAxisRight().setEnabled(false);

        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        chart.getXAxis().setDrawAxisLine(true);
        chart.getXAxis().setDrawGridLines(false);
        chart.getXAxis().setAxisMinimum(0);
        chart.getXAxis().setAxisMaximum(24);
        chart.getXAxis().setLabelCount(6);
        chart.getXAxis().setDrawLimitLinesBehindData(true);
        chart.getXAxis().setAxisLineColor(Color.GRAY);
        chart.getXAxis().setTextColor(Color.GRAY);
        chart.getXAxis().setTypeface(tfLight);
        chart.getXAxis().setValueFormatter(new CustomTimeValueFormatter());

        chart.getAxisLeft().setDrawGridLines(false);
        chart.getAxisLeft().setAxisMaxLabels(10);
        chart.getAxisLeft().setDrawLimitLinesBehindData(true);
        chart.getAxisLeft().setTextColor(Color.GRAY);

        chart.setTouchEnabled(true);
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);
        chart.setPinchZoom(false);

        getNowTime();

        textView10 = findViewById(R.id.textView10);
        textView10.setText(yy+"/"+mm+"/"+dd);

        myDataSet[0] = myDBHelper.getResult("temparture", yy, mm, dd);

        if(myDataSet[0] != null) {
            state = 0;

            redraw_Charts(myDataSet, arraySpinner, state);
        }

        ImageButton imageButton, imageButton1, imageButton2, imageButton4;

        imageButton = findViewById(R.id.imageButton);
        imageButton1 = findViewById(R.id.imageButton1);
        imageButton2 = findViewById(R.id.imageButton2);
        imageButton4 = findViewById(R.id.imageButton4);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog_datePicker();
            }
        });

        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "기능 없음", Toast.LENGTH_SHORT).show();
            }
        });

        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog_statistics();
            }
        });
        imageButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog_log();
            }
        });

        Legend l = chart.getLegend();
        l.setEnabled(false);

        spinner = findViewById(R.id.spinner1);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arraySpinner);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        editText = findViewById(R.id.editText2);

        button = findViewById(R.id.button);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = String.valueOf(editText.getText());

                if(input.equals(""))
                    Toast.makeText(getApplicationContext(), "값을 입력해주세요. (예: 10.0)", Toast.LENGTH_SHORT).show();
                else
                {
                    double valy = Double.parseDouble(String.valueOf(editText.getText()));

                    String formattedlog = null;
                    String item = (String) spinner.getSelectedItem();

                    long now = System.currentTimeMillis();

                    Date date = new Date(now);

                    SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd HH:mm");
                    sdfNow.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));

                    String valx = sdfNow.format(date);

                    String[] nowArray = valx.split(" ");
                    String[] dateArray = nowArray[0].split("/");

                    yy = Integer.parseInt(dateArray[0]);
                    mm = Integer.parseInt(dateArray[1]);
                    dd = Integer.parseInt(dateArray[2]);

                    textView10.setText(yy+"/"+mm+"/"+dd);

                    if (item.equals(arraySpinner[0])) {
                        state = 0;

                        myDBHelper.insert(state, valx, valy, null, null);
                        myDataSet[0] = myDBHelper.getResult("temparture", yy, mm, dd);

                        formattedlog = "℃";
                    } else if (item.equals(arraySpinner[1])) {
                        state = 1;

                        myDBHelper.insert(state, valx, null, valy, null);
                        myDataSet[1] = myDBHelper.getResult("electric", yy, mm, dd);

                        formattedlog = "kWh";
                    } else if (item.equals(arraySpinner[2])) {
                        state = 2;

                        myDBHelper.insert(state, valx, null, null, valy);
                        myDataSet[2] = myDBHelper.getResult("energy", yy, mm, dd);

                        formattedlog = "kcal";
                    }

                    redraw_Charts(myDataSet, arraySpinner, state);

                    items.add("[" + valx + "] " + item + ": " + valy + formattedlog + " 입력됨");

                    adapter3.notifyDataSetChanged();

                    Toast.makeText(getApplicationContext(), "값이 입력되었습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] dateArray = textView10.getText().toString().split("/");

                yy = Integer.parseInt(dateArray[0]);
                mm = Integer.parseInt(dateArray[1]);
                dd = Integer.parseInt(dateArray[2]);

                myDataSet[0] = myDBHelper.getResult("temparture", yy, mm, dd);

                if(myDataSet[0] != null) {
                    state = 0;

                    redraw_Charts(myDataSet, arraySpinner, state);
                }
                else Toast.makeText(getApplicationContext(), "기온 데이터가 없습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            ArrayList<ILineDataSet> dataSets = new ArrayList<>();

            @Override
            public void onClick(View v) {
                String[] dateArray = textView10.getText().toString().split("/");

                yy = Integer.parseInt(dateArray[0]);
                mm = Integer.parseInt(dateArray[1]);
                dd = Integer.parseInt(dateArray[2]);

                myDataSet[1] = myDBHelper.getResult("electric", yy, mm, dd);

                if(myDataSet[1] != null) {
                    state = 1;

                    redraw_Charts(myDataSet, arraySpinner, state);
                }
                else Toast.makeText(getApplicationContext(), "전력량 데이터가 없습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            ArrayList<ILineDataSet> dataSets = new ArrayList<>();

            @Override
            public void onClick(View v) {
                String[] dateArray = textView10.getText().toString().split("/");

                yy = Integer.parseInt(dateArray[0]);
                mm = Integer.parseInt(dateArray[1]);
                dd = Integer.parseInt(dateArray[2]);

                myDataSet[2] = myDBHelper.getResult("energy", yy, mm, dd);

                if(myDataSet[2] != null) {
                    state = 2;

                    redraw_Charts(myDataSet, arraySpinner, state);
                }
                else Toast.makeText(getApplicationContext(), "에너지소비량 데이터가 없습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void redraw_Charts(ArrayList<Entry>[] array, String[] label, int number){
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();

        LineDataSet d = new LineDataSet(array[number], label[number]);

        d.setLineWidth(3.0f);
        d.setCircleRadius(3.0f);
        d.setColor(colors[number]);
        d.setCircleColor(colors[number]);
        d.setValueTextColor(Color.GRAY);
        d.setValueTypeface(tfLight);

        switch(number)
        {
            case 0:
            {
                chart.getAxisLeft().setValueFormatter(new CustomTempValueFormatter());

                break;
            }

            case 1:
            {
                chart.getAxisLeft().setValueFormatter(new CustomElecValueFormatter());

                break;
            }

            case 2:
            {
                chart.getAxisLeft().setValueFormatter(new CustomEnerValueFormatter());

                break;
            }

            default: break;
        }

        dataSets.add(d);

        LineData data = new LineData(dataSets);

        chart.setData(data);
        chart.invalidate();
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

        yy = Integer.parseInt(dateArray[0]);
        mm = Integer.parseInt(dateArray[1]);
        dd = Integer.parseInt(dateArray[2]);
    }

    public void openDialog_datePicker() {
        getNowTime();

        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month++;

                textView10.setText(year+"/"+month+"/"+day);

                switch(state)
                {
                    case 0:
                    {
                        myDataSet[0] = myDBHelper.getResult("temparture", year, month, day);

                        break;
                    }

                    case 1:
                    {
                        myDataSet[1] = myDBHelper.getResult("electric", year, month, day);

                        break;
                    }

                    case 2:
                    {
                        myDataSet[2] = myDBHelper.getResult("energy", year, month, day);

                        break;
                    }

                    default: break;
                }

                redraw_Charts(myDataSet, arraySpinner, state);
            }
        };

        String[] temp = textView10.getText().toString().split("/");

        DatePickerDialog dialog = new DatePickerDialog(this, listener, Integer.parseInt(temp[0]), Integer.parseInt(temp[1]) - 1, Integer.parseInt(temp[2]));

        dialog.show();
    }

    public void openDialog_statistics() {
        final Dialog dlg = new Dialog(this);

        dlg.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dlg.setContentView(R.layout.flexmon_dialog_record);

        dlg.show();

        Button button1 = dlg.findViewById(R.id.button1);
        Button button2 = dlg.findViewById(R.id.button2);
        Button button3 = dlg.findViewById(R.id.button3);

        final EditText editText1 = dlg.findViewById(R.id.editText1);
        final EditText editText2 = dlg.findViewById(R.id.editText2);
        final EditText editText3 = dlg.findViewById(R.id.editText3);

        final Switch switch1 = dlg.findViewById(R.id.switch1);
        final Switch switch2 = dlg.findViewById(R.id.switch2);
        final Switch switch3 = dlg.findViewById(R.id.switch3);

        editText1.setText(Float.toString(limitNumber[0]));
        editText2.setText(Float.toString(limitNumber[1]));
        editText3.setText(Float.toString(limitNumber[2]));

        if(switch1State)
            switch1.setChecked(true);
        else
            switch1.setChecked(false);

        if(switch2State)
            switch2.setChecked(true);
        else
            switch2.setChecked(false);

        if(switch3State)
            switch3.setChecked(true);
        else
            switch3.setChecked(false);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                color = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getInt("color", Color.BLACK);
                new CustomColorPicker(dlg.getContext(), new CustomColorPicker.OnColorChangedListener() {
                    @Override
                    public void colorChanged(int color) {
                        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putInt("color", color).commit();

                        switch(state)
                        {
                            case 0: colors[0] = color; break;
                            case 1: colors[1] = color; break;
                            case 2: colors[2] = color; break;

                            default: break;
                        }

                        chart.invalidate();
                    }
                }, color).show();
            }
        });

        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                switch1State = b;

                chart.invalidate();
            }
        });

        switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                switch2State = b;

                chart.invalidate();
            }
        });

        switch3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                switch3State = b;
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                limitNumber[0] = Float.parseFloat(String.valueOf(editText1.getText()));
                limitNumber[1] = Float.parseFloat(String.valueOf(editText2.getText()));
                limitNumber[2] = Float.parseFloat(String.valueOf(editText3.getText()));

                if(switch1State) {
                    chart.getAxisLeft().removeLimitLine(ll1);

                    ll1 = new LimitLine(limitNumber[0], "기준값");
                    ll1.setLineWidth(1f);
                    ll1.enableDashedLine(5f, 5f, 0f);
                    ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
                    ll1.setTextSize(10f);
                    ll1.setTypeface(tfRegular);

                    chart.getAxisLeft().addLimitLine(ll1);
                }
                else
                    chart.getAxisLeft().removeLimitLine(ll1);

                if(switch2State) {
                    chart.getAxisLeft().removeLimitLine(ll2);

                    ll2 = new LimitLine(limitNumber[1], "상한선");
                    ll2.setLineWidth(1f);
                    ll2.enableDashedLine(5f, 5f, 0f);
                    ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
                    ll2.setTextSize(10f);
                    ll2.setTypeface(tfRegular);

                    chart.getAxisLeft().addLimitLine(ll2);
                }
                else
                    chart.getAxisLeft().removeLimitLine(ll2);

                if(switch3State) {
                    chart.getAxisLeft().removeLimitLine(ll3);

                    ll3 = new LimitLine(limitNumber[2], "하한선");
                    ll3.setLineWidth(1f);
                    ll3.enableDashedLine(5f, 5f, 0f);
                    ll3.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
                    ll3.setTextSize(10f);
                    ll3.setTypeface(tfRegular);

                    chart.getAxisLeft().addLimitLine(ll3);
                }
                else
                    chart.getAxisLeft().removeLimitLine(ll3);

                redraw_Charts(myDataSet, arraySpinner, state);

                chart.invalidate();

                dlg.dismiss();
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dlg.dismiss();
            }
        });
    }

    public void openDialog_log() {
        final Dialog dlg = new Dialog(this);

        dlg.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dlg.setContentView(R.layout.flexmon_dialog_log);
        dlg.show();

        Button button1 = dlg.findViewById(R.id.button1);
        Button button2 = dlg.findViewById(R.id.button2);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                items.clear();

                adapter3.notifyDataSetChanged();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dlg.dismiss();
            }
        });
    }

    @Override
    protected void saveToGallery() {

    }

    private final int[] colors = new int[] {
        Color.argb(255, 100, 100, 255),
        Color.argb(255, 255, 100, 100),
        Color.argb(255, 255, 200, 0)
    };

    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        Log.i("Gesture", "START, x: " + me.getX() + ", y: " + me.getY());
    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        Log.i("Gesture", "END, lastGesture: " + lastPerformedGesture);

        if(lastPerformedGesture != ChartTouchListener.ChartGesture.SINGLE_TAP)
            chart.highlightValues(null);
    }

    @Override
    public void onChartLongPressed(MotionEvent me) {
        Log.i("LongPress", "Chart long pressed.");
    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {
        Log.i("DoubleTap", "Chart double-tapped.");
    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {
        Log.i("SingleTap", "Chart single-tapped.");
    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {
        Log.i("Fling", "Chart fling. VelocityX: " + velocityX + ", VelocityY: " + velocityY);
    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
        Log.i("Scale / Zoom", "ScaleX: " + scaleX + ", ScaleY: " + scaleY);
    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {
        Log.i("Translate / Move", "dX: " + dX + ", dY: " + dY);
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Log.i("VAL SELECTED", "Value: " + e.getY() + ", xIndex: " + e.getX() + ", DataSet index: " + h.getDataSetIndex());
    }

    @Override
    public void onNothingSelected() {

    }
}
