package com.udacity.gradle.builditbigger;

import android.test.AndroidTestCase;
import android.test.InstrumentationTestCase;
import android.text.TextUtils;
import android.util.Log;


import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by gabrielquiles-perez on 2/22/16.
 */
public class JokeFetcherTest extends AndroidTestCase {
    static String LOG_TAG = JokeFetcherTest.class.getSimpleName();
    private CountDownLatch mSignal;
    private String mJoke = null;

    @Override
    protected void setUp() {
        mSignal = new CountDownLatch(1);
    }

    @Override
    protected void tearDown() {
        mSignal.countDown();
    }

    public void testGetJoke() throws InterruptedException {
        JokeFetcher fetcher = new JokeFetcher();
        fetcher.execute(new JokeReceiver() {
            @Override
            public void receiveJoke(String joke) {
                mJoke = joke;
            }
        });

        mSignal.await(10, TimeUnit.SECONDS);
        assertNotNull("Didn't received a joke", mJoke);
        assertFalse("Joke received is empty", TextUtils.isEmpty(mJoke));
    }
}