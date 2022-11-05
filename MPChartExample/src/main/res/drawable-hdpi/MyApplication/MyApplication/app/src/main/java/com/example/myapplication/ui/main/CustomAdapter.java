package com.example.myapplication.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<String> {
    private ArrayList<String> items;

    public CustomAdapter(Context context, int textViewResourceId, ArrayList<String> objects) {
        super(context, textViewResourceId, objects);
        this.items = objects;
    }

    @Override
    public int getCount() {
        return items.size() ;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.listview_item, null);
        }

        // ImageView 인스턴스
        ImageView imageView = (ImageView)v.findViewById(R.id.imageView);

        // 리스트뷰의 아이템에 이미지를 변경한다.
        imageView.setImageResource(R.drawable.logo);
        TextView textView = (TextView)v.findViewById(R.id.textView);
        textView.setText(items.get(position));

        final String text = items.get(position);

        Button button1 = (Button)v.findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), text + "님과 대화를 시작합니다.", Toast.LENGTH_SHORT).show();
            }
        });

        Button button2 = (Button)v.findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), text + "님의 정보를 수정합니다.", Toast.LENGTH_SHORT).show();
            }
        });

        Button button3 = (Button)v.findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), text + "님을 목록에서 삭제합니다.", Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }
}
