package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.gabyquiles.androidjoke.JokeActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;


public class MainActivity extends AppCompatActivity implements JokeReceiver{
    InterstitialAd mInterstitialAd;
    private ProgressBar mSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSpinner = (ProgressBar)findViewById(R.id.progressBar);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.banner_ad_unit_id));

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                getJokeFromServer();
            }
        });

        requestNewInterstitial();
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
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            getJokeFromServer();
        }
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        mInterstitialAd.loadAd(adRequest);
    }

    private void getJokeFromServer() {
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
