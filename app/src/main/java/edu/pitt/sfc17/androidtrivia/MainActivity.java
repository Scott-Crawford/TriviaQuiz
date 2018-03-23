package edu.pitt.sfc17.androidtrivia;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private int highestScore = 0;
    private ArrayList<String> scoreHistory = null;
    private final int REQUEST_CODE = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences prefs = getPreferences(0);
        highestScore = prefs.getInt("highest", 0);
        scoreHistory = new ArrayList<>(prefs.getStringSet("scores", new HashSet<String>()));

        Switch aSwitch = findViewById(R.id.musicSwitch);

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                playBackgroundMusic(isChecked);
            }
        });
    }


    @Override
    protected void onStop(){
        super.onStop();
        SharedPreferences.Editor editor = getPreferences(0).edit();
        editor.putStringSet("scores", new HashSet<>(scoreHistory));
        editor.putInt("highest", highestScore);
        editor.apply();
    }

    @Override
    protected void onSaveInstanceState(Bundle bundle){
        super.onSaveInstanceState(bundle);
        bundle.putSerializable("scores",scoreHistory);
        bundle.putInt("highest",highestScore);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void onRestoreInstanceState(Bundle bundle){
        super.onRestoreInstanceState(bundle);
        scoreHistory=(ArrayList<String>)bundle.getSerializable("scores");
        highestScore=bundle.getInt("highest");
    }

    protected void playOnClick(View view){
        Intent intent = new Intent(this,PlayActivity.class);
        startActivityForResult(intent,REQUEST_CODE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode==REQUEST_CODE&&resultCode==RESULT_OK){
            int score = data.getIntExtra("score",0);
            score=score*100/5;
            Date currentTime = Calendar.getInstance().getTime();
            String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(currentTime);
            scoreHistory.add(date+"\t\t\t\t"+score);
            if(score>highestScore){
                highestScore=score;
                Toast.makeText(this,"You got "+score+"! That's a new high score!",Toast.LENGTH_LONG).show();
            }
            else if(score<100){
                Toast.makeText(this,"You got "+score+"! I bet you can do better next time!",Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(this, "You got " + score + "! That's a perfect score!", Toast.LENGTH_LONG).show();
            }
        }
    }


    protected void addWordOnClick(View view){
        Intent intent = new Intent(this,AddWordActivity.class);
        startActivity(intent);
    }

    protected void scoreHistoryOnClick(View view){
        Intent intent = new Intent(this,ScoreHistoryActivity.class);
        intent.putExtra("scores", scoreHistory);
        intent.putExtra("highest", highestScore);
        startActivity(intent);
    }

    protected void playBackgroundMusic(boolean play){
        Intent svc=new Intent(this, BackgroundMusicService.class);
        if(play){
            startService(svc);
        }
        else{
            stopService(svc);
        }
    }


}
