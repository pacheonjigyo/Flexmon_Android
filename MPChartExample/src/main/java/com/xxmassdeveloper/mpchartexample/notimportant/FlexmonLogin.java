package com.xxmassdeveloper.mpchartexample.notimportant;

//회원가입 시 특수문자 입력, 네이버/카카오톡 로그인 연동, 아이디/비밀번호 찾기 필요

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.xxmassdeveloper.mpchartexample.R;

public class FlexmonLogin extends AppCompatActivity {
    EditText editText1, editText2;
    ImageButton imageButton1;
    Button button1, button2;

    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final MyDBLoginHelper myDBHelper = new MyDBLoginHelper(getApplicationContext(), "flexmon_login.db", null, 1);

        setContentView(R.layout.flexmon_login);

        imageButton1 = findViewById(R.id.imageButton1);

        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);

        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);

        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int state = -2;
                String password;

                userId = editText1.getText().toString();
                password = editText2.getText().toString();

                if(userId.equals("")) {
                    editText1.requestFocus();
                    editText1.startAnimation(shakeError());

                    Toast.makeText(getApplicationContext(), "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (password.equals("")) {
                        editText2.requestFocus();
                        editText2.startAnimation(shakeError());

                        Toast.makeText(getApplicationContext(), "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    else
                        state = myDBHelper.loginProcess(userId, password);
                }

                switch(state)
                {
                    case 1:
                    {
                        Intent i = new Intent(getApplicationContext(), FlexmonMain.class);

                        i.putExtra("userid", userId);

                        startActivity(i);

                        overridePendingTransition(R.anim.move_left_in_activity, R.anim.move_right_out_activity);

                        FlexmonLogin.this.finish();

                        Toast.makeText(getApplicationContext(), "로그인되었습니다.", Toast.LENGTH_SHORT).show();

                        break;
                    }

                    case 0:
                    {
                        editText2.requestFocus();
                        editText2.startAnimation(shakeError());

                        Toast.makeText(getApplicationContext(), "패스워드가 틀립니다.", Toast.LENGTH_SHORT).show();

                        break;
                    }

                    case -1:
                    {
                        editText1.requestFocus();
                        editText1.startAnimation(shakeError());

                        Toast.makeText(getApplicationContext(), "아이디가 존재하지 않습니다.", Toast.LENGTH_SHORT).show();

                        break;
                    }

                    default: break;
                }
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog_signup();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), FlexmonMain.class);

                startActivity(i);

                overridePendingTransition(R.anim.move_left_in_activity, R.anim.move_right_out_activity);

                FlexmonLogin.this.finish();
            }
        });
    }

    public TranslateAnimation shakeError() {
        TranslateAnimation shake = new TranslateAnimation(0, 10, 0, 0);
        shake.setDuration(500);
        shake.setInterpolator(new CycleInterpolator(7));
        return shake;
    }

    public boolean isLoginSucceed(){
        if(userId == null)
            return true;
        else
            return false;
    }

    public void openDialog_signup() {
        final Dialog dlg = new Dialog(this);
        final MyDBLoginHelper myDBHelper = new MyDBLoginHelper(getApplicationContext(), "flexmon_login.db", null, 1);

        dlg.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dlg.setContentView(R.layout.flexmon_dialog_signup);

        dlg.show();

        Button button1 = dlg.findViewById(R.id.button1);
        Button button2 = dlg.findViewById(R.id.button2);

        final EditText editText1 = dlg.findViewById(R.id.editText1);
        final EditText editText2 = dlg.findViewById(R.id.editText2);
        final EditText editText3 = dlg.findViewById(R.id.editText3);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String id, password, password_checker;

                id = editText1.getText().toString();
                password = editText2.getText().toString();
                password_checker = editText3.getText().toString();

                if(!password.equals("") && password.equals(password_checker))
                {
                    int state = myDBHelper.loginProcess(id, password);

                    switch(state)
                    {
                        case -1:
                        {
                            myDBHelper.insert(id, password);

                            dlg.dismiss();

                            Toast.makeText(getApplicationContext(), "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();

                            break;
                        }

                        default:
                        {
                            editText1.requestFocus();
                            editText1.startAnimation(shakeError());

                            Toast.makeText(getApplicationContext(), "이미 존재하는 아이디입니다.", Toast.LENGTH_SHORT).show();

                            break;
                        }
                    }


                }
                else
                {
                    editText2.startAnimation(shakeError());
                    editText3.startAnimation(shakeError());

                    Toast.makeText(getApplicationContext(), "패스워드를 확인해주세요.", Toast.LENGTH_SHORT).show();
                }
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
    public void onBackPressed() {
    }
}
