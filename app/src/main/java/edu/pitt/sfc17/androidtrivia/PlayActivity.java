package edu.pitt.sfc17.androidtrivia;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;



public class PlayActivity extends AppCompatActivity{

    private int questions = 0;
    private int score = 0;
    private HashMap<String, String> words;
    private ArrayList<String> keys;
    private TextView termView;
    private Toast toast=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        words = new HashMap<>();
        try{

            BufferedReader reader1 = new BufferedReader(new InputStreamReader(getResources().openRawResource(R.raw.originalwords)));
            while(reader1.ready()){
               String term = reader1.readLine();
               String definition = reader1.readLine();
               words.put(term,definition);
            }
            try {
                BufferedReader reader2 = new BufferedReader(new InputStreamReader(openFileInput("addedwords.txt")));
                while (reader2.ready()) {
                    String term = reader2.readLine();
                    String definition = reader2.readLine();
                    words.put(term, definition);
                }
            }
            catch (FileNotFoundException fileE){
                Log.v("Play Activity",fileE.toString());
            }
            keys = new ArrayList<>(words.keySet());
            nextQuestion();
        }
        catch (Exception e){
            Toast.makeText(this,e.toString(),Toast.LENGTH_LONG).show();
        }
    }

    protected void nextQuestion(){
        Random rand = new Random();
        String term = keys.get(rand.nextInt(keys.size()));
        termView = findViewById(R.id.term);
        termView.setText(term);
        String definition = words.get(term);
        keys.remove(term);
        ArrayList<String> remainingWords = new ArrayList<>(words.values());
        remainingWords.remove(definition);
        Collections.shuffle(remainingWords);
        remainingWords = new ArrayList<>(remainingWords.subList(0,4));
        remainingWords.add(definition);
        Collections.shuffle(remainingWords);
        ListView list = findViewById(R.id.listView);
        list.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,remainingWords));
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getItemAtPosition(i).equals(words.get(termView.getText().toString()))){
                    if(toast!=null){
                        toast.cancel();
                    }
                    score++;
                    toast = Toast.makeText(getApplicationContext(),"Correct Answer! Your score is " + score, Toast.LENGTH_SHORT);
                    toast.show();
                }
                else{
                    if(toast!=null){
                        toast.cancel();
                    }
                    toast = Toast.makeText(getApplicationContext(),"Wrong Answer!", Toast.LENGTH_SHORT);
                    toast.show();
                }
                if(questions>=5){
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("score", score);
                    setResult(RESULT_OK, resultIntent);
                    finish();
                }
                else {
                    nextQuestion();
                }
            }
        });
        ProgressBar bar = findViewById(R.id.progressBar);
        bar.setProgress(questions);
        questions++;

    }



}
