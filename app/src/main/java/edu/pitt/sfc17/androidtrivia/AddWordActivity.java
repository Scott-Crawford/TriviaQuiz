package edu.pitt.sfc17.androidtrivia;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.PrintStream;



public class AddWordActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addword);
    }

    protected void addClick(View view){
        EditText term = findViewById(R.id.term_input);
        EditText definition = findViewById(R.id.definition_input);
        try{
            PrintStream fileWrite = new PrintStream(openFileOutput("addedwords.txt",MODE_APPEND));
            fileWrite.println(term.getText().toString());
            fileWrite.println(definition.getText().toString());
            fileWrite.close();
        }
        catch(Exception e){
            Toast.makeText(this,e.toString(),Toast.LENGTH_LONG).show();
        }
        finish();
    }
}
