package com.wits.witssrcconnect.fragments;

import android.support.test.runner.AndroidJUnit4;

import org.json.JSONArray;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class AllSrcPollFragmentTest {

    @Test
    public void init() {
        try {
            runOnUiThread(()->{
                JSONArray polls = new JSONArray();
                AllSrcPollFragment.init(polls);
            });
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}