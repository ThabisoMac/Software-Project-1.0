package com.wits.witssrcconnect.managers;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.Toast;

import com.wits.witssrcconnect.fragments.AllSrcActivitiesFragment;
import com.wits.witssrcconnect.fragments.MySrcActivitiesFragment;
import com.wits.witssrcconnect.services.ServerCommunicator;
import com.wits.witssrcconnect.utils.ServerUtils;
import com.wits.witssrcconnect.utils.UserUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SrcActivityManager {

    //this function posts an activity to
    public static void postActivity(String title, String activity, Activity activityClass) {
        Toast.makeText(activityClass, "Please wait...", Toast.LENGTH_SHORT).show();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy-HH:mm", Locale.getDefault());
        String sDateTime = simpleDateFormat.format(new Date());
        String[] dateTimeList = sDateTime.split("-");
        String date = dateTimeList[0].trim();
        String time = dateTimeList[1].trim();

        ContentValues cv = new ContentValues();
        cv.put(ServerUtils.ACTION, ServerUtils.POST_ACTIVITY);
        cv.put(ServerUtils.SRC_USERNAME, UserManager.getCurrentlyLoggedInUsername());
        cv.put(ServerUtils.ACTIVITY_TITLE, title);
        cv.put(ServerUtils.ACTIVITY_DESC, activity);
        cv.put(ServerUtils.ACTIVITY_DATE, date);
        cv.put(ServerUtils.ACTIVITY_TIME, time);

        new ServerCommunicator(cv) {
            @Override
            protected void onPreExecute() {

            }

            @Override
            protected void onPostExecute(String output) {
                if (output != null && output.equals(ServerUtils.SUCCESS)){
                    Toast.makeText(activityClass, "Activity posted", Toast.LENGTH_SHORT).show();
                    activityClass.finish();
                }
                else{
                    Toast.makeText(activityClass, "Activity post failed", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute(ServerUtils.SRC_MEMBER_LINK);
    }

    public static void fetchAllActivities(Context c, SwipeRefreshLayout pullToRefresh){
        ContentValues cv = new ContentValues();
        cv.put(ServerUtils.ACTION, ServerUtils.READ_ALL_ACTIVITIES);
        new ServerCommunicator(cv) {
            @Override
            protected void onPreExecute() {

            }

            @Override
            protected void onPostExecute(String output) {
                if (pullToRefresh != null) pullToRefresh.setRefreshing(false);

                if (output == null ){
                    Toast.makeText(c, "Failed to get activities", Toast.LENGTH_SHORT).show();
                }
                else if(output.equals("")){
                    Toast.makeText(c, "There are no activities", Toast.LENGTH_SHORT).show();
                }
                else{
                    try {
                        JSONArray activities = new JSONArray(output);

                        switch (UserManager.getLoggedInUserType()){
                            case UserUtils.SRC_MEMBER:
                                String current_src_username = UserManager.getCurrentlyLoggedInUsername();
                                JSONArray myActivities = new JSONArray();

                                for (int i = 0; i < activities.length(); i++){
                                    JSONObject activity = (JSONObject) activities.get(i);
                                    if (current_src_username.equals(activity.getString(ServerUtils.SRC_USERNAME))){
                                        myActivities.put(activity);
                                    }
                                }

                                AllSrcActivitiesFragment.init(activities);
                                MySrcActivitiesFragment.init(myActivities);
                                break;

                            case UserUtils.STUDENT:

                                break;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.execute(ServerUtils.SRC_MEMBER_LINK);
    }
}