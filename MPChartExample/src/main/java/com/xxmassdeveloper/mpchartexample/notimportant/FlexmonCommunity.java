package com.xxmassdeveloper.mpchartexample.notimportant;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.xxmassdeveloper.mpchartexample.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

//메모 삭제 기능 추가

public class FlexmonCommunity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ImageButton imageButton1, imageButton2;

    ListView listView1, listView2;

    MyDBCommunityHelper myDBHelper;

    private ArrayList<String> noticeArray, postArray;
    private ArrayAdapter adapter1, adapter2;

    private String userId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = getIntent();

        if(i != null) {
            try{
                userId = i.getExtras().getString("userid");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        myDBHelper = new MyDBCommunityHelper(getApplicationContext(), "flexmon_community.db", null, 1);

        setContentView(R.layout.flexmon_community);

        noticeArray = new ArrayList<String>();
        postArray = new ArrayList<String>();

        adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, noticeArray);
        adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, postArray);

        imageButton1 = findViewById(R.id.imageButton1);
        imageButton2 = findViewById(R.id.imageButton2);

        listView1 = findViewById(R.id.listView1);
        listView2 = findViewById(R.id.listView2);

        listView1.setAdapter(adapter1);
        listView2.setAdapter(adapter2);

        redraw();

//        try{
//            communityContent = new CommunityContent(id, title, description, author, time, comment, notice);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        listView1.setOnItemClickListener(this);
        listView2.setOnItemClickListener(this);

        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog_post(1);
            }
        });

        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog_post(0);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        overridePendingTransition(R.anim.move_left_in_activity, R.anim.move_right_out_activity);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String title = adapterView.getAdapter().getItem(i).toString();
        String[] tokens = title.split(" ");

        openDialog_community(Integer.parseInt(tokens[0]));
    }

    public void redraw() {
        noticeArray.clear();
        postArray.clear();

        Cursor cursor = myDBHelper.getResult();

        byte[] comment = null;

        int id = 0;
        int notice = 0;

        String title = null;
        String description = null;
        String author = null;
        String time = null;

        while (cursor.moveToNext()) {
            id = cursor.getInt(0);
            title = cursor.getString(1);
            description = cursor.getString(2);
            author = cursor.getString(3);
            time = cursor.getString(4);
            comment = cursor.getBlob(5);
            notice = cursor.getInt(6);

            if(notice == 1)
                noticeArray.add(id + " - " + title);

            postArray.add(id + " - " + title);
        }

        if(cursor.getCount() == 0)
        {
            noticeArray.add("메모가 없습니다.");
            postArray.add("메모가 없습니다.");
        }

        adapter1.notifyDataSetChanged();
        adapter2.notifyDataSetChanged();
    }

    public void openDialog_community(int id) {
        byte[] comment = null;

        final Dialog dlg = new Dialog(this);

        Cursor cursor = myDBHelper.getContent(id);

        int notice = 0;

        String title = null;
        String description = null;
        String author = null;
        String time = null;

        while (cursor.moveToNext()) {
            title = cursor.getString(0);
            description = cursor.getString(1);
            author = cursor.getString(2);
            time = cursor.getString(3);
            comment = cursor.getBlob(4);
            notice = cursor.getInt(5);
        }

        dlg.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dlg.setContentView(R.layout.flexmon_dialog_community);
        dlg.show();

        TextView textView1 = dlg.findViewById(R.id.textView1);
        TextView textView2 = dlg.findViewById(R.id.textView2);

        Button button1 = dlg.findViewById(R.id.button1);

        EditText editText1 = dlg.findViewById(R.id.editText1);

        switch(notice)
        {
            case 0: {
                textView1.setText("#" + id + " - " + title);

                break;
            }

            case 1: {
                textView1.setText("(★) #" + id + " - " + title);

                break;
            }

            default: break;
        }

        textView2.setText(author + ", " + time);

        editText1.setText(description);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dlg.dismiss();
            }
        });
    }

    public void openDialog_post(final int notice) {
        final Dialog dlg = new Dialog(this);

        dlg.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dlg.setContentView(R.layout.flexmon_dialog_post);
        dlg.show();

        final Button button1 = dlg.findViewById(R.id.button1);
        final Button button2 = dlg.findViewById(R.id.button2);

        final EditText editText1 = dlg.findViewById(R.id.editText1);
        final EditText editText2 = dlg.findViewById(R.id.editText2);

        long now = System.currentTimeMillis();

        Date date = new Date(now);

        SimpleDateFormat sdfNow = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
        sdfNow.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));

        final String time = sdfNow.format(date);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = String.valueOf(editText1.getText());
                String description = String.valueOf(editText2.getText());

                if(userId == null)
                    userId = "익명";

                myDBHelper.insert(title, description, userId, time, "댓글", notice);

                redraw();

                Toast.makeText(getApplicationContext(), "메모가 등록되었습니다.", Toast.LENGTH_SHORT).show();

                dlg.dismiss();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dlg.dismiss();
            }
        });
    }
}
