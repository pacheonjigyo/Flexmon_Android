package com.xxmassdeveloper.mpchartexample.notimportant;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.xxmassdeveloper.mpchartexample.R;

import java.util.ArrayList;
import java.util.Vector;

public class FlexmonHelper extends AppCompatActivity{

    EditText editText;
    ImageButton imageButton;
    SpeechRecognizer recognizer = SpeechRecognizer.createSpeechRecognizer(this);

    private RecognitionListener listener = new RecognitionListener() {
        @Override
        public void onReadyForSpeech(Bundle bundle) {

        }

        @Override
        public void onBeginningOfSpeech() {

        }

        @Override
        public void onRmsChanged(float v) {

        }

        @Override
        public void onBufferReceived(byte[] bytes) {

        }

        @Override
        public void onEndOfSpeech() {

        }

        @Override
        public void onError(int i) {
            Toast.makeText(getApplicationContext(), "녹음 실패", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResults(Bundle bundle) {
            Vector vector = null;

            ArrayList<String> matches = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

            for(int i = 0; i < matches.size(); i++)
                vector.add(matches.get(i));

            Toast.makeText(getApplicationContext(), "인식 결과: " + vector, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPartialResults(Bundle bundle) {

        }

        @Override
        public void onEvent(int i, Bundle bundle) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.flexmon_helper);

        final Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,getPackageName());
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"ko-KR");

        imageButton = findViewById(R.id.button1);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recognizer.setRecognitionListener(listener);
                recognizer.startListening(intent);

                Toast.makeText(getApplicationContext(), "듣고 있습니다. 계속 말씀하세요.", Toast.LENGTH_SHORT).show();
            }
        });

        editText = findViewById(R.id.editText1);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Intent intent = null;

                if(isContained(charSequence, "dashboard"))
                    intent = new Intent(getApplicationContext(), FlexmonDashboard.class);
                else if(isContained(charSequence, "data"))
                    intent = new Intent(getApplicationContext(), FlexmonData.class);
                else if(isContained(charSequence, "notepad"))
                    intent = new Intent(getApplicationContext(), FlexmonCommunity.class);
                else if(isContained(charSequence, "login"))
                    intent = new Intent(getApplicationContext(), FlexmonLogin.class);

                if(intent != null) {
                    startActivity(intent);

                    overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public boolean isContained(CharSequence msg1, String msg2)
    {
        if(msg1.toString().toLowerCase().contains(msg2))
            return true;
        else
            return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        overridePendingTransition(R.anim.move_left_in_activity, R.anim.move_right_out_activity);
    }
}
