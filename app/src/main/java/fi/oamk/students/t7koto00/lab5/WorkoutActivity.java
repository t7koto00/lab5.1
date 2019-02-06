package fi.oamk.students.t7koto00.lab5;

import android.content.Intent;
import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class WorkoutActivity extends AppCompatActivity implements TextToSpeech.OnInitListener{
    ArrayList<WorkoutPartBase> WorkoutPartBase = new ArrayList<>();
    private TextView textViewCounter = null;
    private TextView textViewType = null;
    int length = 0;
    int counter = 0;
    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        tts = new TextToSpeech(this, this);
        textViewCounter = (TextView) findViewById(R.id.textViewCounter);
        textViewType = (TextView) findViewById(R.id.textViewType);
        Intent intent = getIntent();
        WorkoutPartBase = (ArrayList<WorkoutPartBase>) intent.getSerializableExtra("DATA");

    }


    private void countDown(int length) {
        CountDownTimer timer = new CountDownTimer(length * 1000, 1000) {

            public void onTick(long millisUntilFinished) {
                textViewCounter.setText("" + millisUntilFinished / 1000);
            }

            public void onFinish() {
                counter++;
                if (counter < WorkoutPartBase.size()) {
                    WorkoutPartBase part = WorkoutPartBase.get(counter);
                    String type = part.getName();
                    int length2 = part.getLength();
                    textViewType.setText(type);
                    textViewCounter.setText("" + length2);
                    countDown(length2);
                    speakOut(type);
                } else {
                    finish();
                }
            }
        };
        timer.start();

    }

    @Override
    public void onInit(int status) {

        if (status == TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(Locale.US);
            if (WorkoutPartBase.isEmpty()) {

            } else {
                WorkoutPartBase part = WorkoutPartBase.get(0);
                String type = part.getName();
                length = part.getLength();
                textViewType.setText(type);
                textViewCounter.setText("" + length);
                speakOut(type);
                countDown(length);
            }
        }
    }

    private void speakOut(String text) {
        tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
            @Override
            public void onStart(String s) {
                final String keyword = s;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                    }
                });
            }
            @Override
            public void onDone(String s) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                    }
                });
            }
            @Override
            public void onError(String s) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                    }
                });
            }
        });

        Bundle params = new Bundle();
        params.putString(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "");
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, params, "Dummy String");
    }

    @Override
    public void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }
}