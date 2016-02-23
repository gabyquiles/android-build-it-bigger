package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.gabyquiles.androidjoke.JokeActivity;


public class MainActivity extends AppCompatActivity implements JokeReceiver{
    private ProgressBar mSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSpinner = (ProgressBar)findViewById(R.id.progressBar);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view){
        mSpinner.setVisibility(View.VISIBLE);
        new JokeFetcher().execute(this);
    }

    public void receiveJoke(String joke) {
        Intent jokeIntent = new Intent(this, JokeActivity.class);
        jokeIntent.putExtra(getString(R.string.joke_text), joke);
        mSpinner.setVisibility(View.GONE);
        startActivity(jokeIntent);
    }
}
