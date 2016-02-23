package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.os.AsyncTask;

import com.gabyquiles.builditbigger.jokebackend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

/**
 * Created by gabrielquiles-perez on 2/20/16.
 */
public class JokeFetcher extends AsyncTask<JokeReceiver, Void, String> {
    private static MyApi mApiService = null;
    private JokeReceiver mReceiver;

    @Override
    protected String doInBackground(JokeReceiver... params) {
        if(mApiService == null) {
            //TODO: How can I get the strings from the strings.xml ??
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });

            mApiService = builder.build();
        }
        mReceiver = params[0];

        try {
            return mApiService.getJoke().execute().getData();
        } catch (IOException e) {

            return "Not in the mood of making jokes. Error getting a joke";
        }
    }

    @Override
    protected void onPostExecute(String joke) {
        mReceiver.receiveJoke(joke);
    }
}
