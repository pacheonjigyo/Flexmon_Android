
package com.xxmassdeveloper.mpchartexample.notimportant;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.xxmassdeveloper.mpchartexample.R;

//도움말 기능 추가

public class FlexmonMain extends AppCompatActivity {
    Button button1;

    CardView cardView1, cardView2, cardView3, cardView4;

    Intent i = null;

    TextView textView;

    private String userId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flexmon_main);

        button1 = findViewById(R.id.button1);

        cardView1 = findViewById(R.id.cardView1);
        cardView2 = findViewById(R.id.cardView2);
        cardView3 = findViewById(R.id.cardView3);
        cardView4 = findViewById(R.id.cardView4);

        textView = findViewById(R.id.textView6);

        i = getIntent();

        if(i != null) {
            try{
                userId = i.getExtras().getString("userid");

                if (userId != null) {
                    textView.setText(userId + "님, 환영합니다!");

                    button1.setText("로그아웃");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userId == null)
                    Toast.makeText(getApplicationContext(), "로그인 후 이용하실 수 있습니다.", Toast.LENGTH_SHORT).show();
                else {
                    i = new Intent(getApplicationContext(), FlexmonDashboard.class);
                    i.putExtra("userid", userId);

                    startActivity(i);

                    overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);
                }
            }
        });

        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userId == null)
                    Toast.makeText(getApplicationContext(), "로그인 후 이용하실 수 있습니다.", Toast.LENGTH_SHORT).show();
                else {
                    i = new Intent(getApplicationContext(), FlexmonData.class);
                    i.putExtra("userid", userId);

                    startActivity(i);

                    overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);
                }
            }
        });

        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userId == null)
                    Toast.makeText(getApplicationContext(), "로그인 후 이용하실 수 있습니다.", Toast.LENGTH_SHORT).show();
                else {
                    i = new Intent(getApplicationContext(), FlexmonHelper.class);
                    i.putExtra("userid", userId);

                    startActivity(i);

                    overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);
                }
            }
        });

        cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(getApplicationContext(), FlexmonCommunity.class);
                i.putExtra("userid", userId);

                startActivity(i);

                overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userId != null){
                    userId = null;

                    button1.setText("로그인");

                    textView.setText("로그인하여 더 많은 서비스를 이용해보세요!");

                    Toast.makeText(getApplicationContext(), "로그아웃되었습니다.", Toast.LENGTH_SHORT).show();
                }
                else {
                    i = new Intent(getApplicationContext(), FlexmonLogin.class);

                    startActivity(i);

                    overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);

                    FlexmonMain.this.finish();
                }
            }
        });
    }
}
