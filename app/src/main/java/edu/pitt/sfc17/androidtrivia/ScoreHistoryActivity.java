package edu.pitt.sfc17.androidtrivia;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;



public class ScoreHistoryActivity extends AppCompatActivity{

    @Override
    @SuppressWarnings("unchecked")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scorehistory);
        ArrayList<String> scoreHistory = (ArrayList<String>) getIntent().getExtras().get("scores");

        TextView highScoreValue = findViewById(R.id.score);
        highScoreValue.setText(Integer.toString(getIntent().getIntExtra("highest", 0)) + "%");
        ListView list = findViewById(R.id.listView);
        if(scoreHistory!=null) {
            Collections.sort(scoreHistory);
            list.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, scoreHistory));
        }
    }

    protected void backClick(View view){
        finish();
    }
}
