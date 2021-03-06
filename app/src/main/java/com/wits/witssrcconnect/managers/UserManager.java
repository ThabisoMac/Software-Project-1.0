package com.wits.witssrcconnect.managers;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.wits.witssrcconnect.R;
import com.wits.witssrcconnect.activities.SrcMemberActivity;
import com.wits.witssrcconnect.activities.StudentActivity;
import com.wits.witssrcconnect.services.ServerCommunicator;
import com.wits.witssrcconnect.utils.ServerUtils;
import com.wits.witssrcconnect.utils.UserUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class UserManager {

    static SharedPreferences SHARED_PREFERENCES = null;
    private static String NAME = "USER_PREF";
    private static String USER_NAME_SURNAME = "NAME_SURNAME";
    private static String LOGGED_IN_USER_KEY = "LOGGED_IN_USERNAME";
    private static String LOGGED_IN_KEY = "LOGGED_IN";
    private static String USER_TYPE_KEY = "USER_TYPE";

    public static void initUserManager(Context context) {
        SHARED_PREFERENCES = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
    }

    //saves which type of user logged into shared preferences
    public static void setUserLoggedIn(int userType, String username) {
        SHARED_PREFERENCES.edit()
                .putString(LOGGED_IN_USER_KEY, username)
                .putBoolean(LOGGED_IN_KEY, true)
                .putInt(USER_TYPE_KEY, userType)
                .apply();
    }

    //retrieves the name of the user who is currently logged in
    public static String getCurrentlyLoggedInUsername() {
        return SHARED_PREFERENCES.getString(LOGGED_IN_USER_KEY, "");
    }

    //retrieves the type of user logged in as type of string e.g. Student, SRC Member
    static String getLoggedInUserTypeName(Context context) {
        switch (getLoggedInUserType()) {
            case UserUtils.STUDENT:
                return context.getString(R.string.student);

            case UserUtils.SRC_MEMBER:
                return context.getString(R.string.src_member);

            default:
                return "";
        }
    }

    //checks if you are loggedIn
    public static boolean getCurrentlyLoggedInStatus() {
        return SHARED_PREFERENCES.getBoolean(LOGGED_IN_KEY, false);
    }

    //retrieves which user type is logged in
    public static int getLoggedInUserType() {
        return SHARED_PREFERENCES.getInt(USER_TYPE_KEY, UserUtils.DEFAULT_USER);
    }

    //save name and surname of a user after logging in
    static void setUserNameSurname(String name, String surname) {
        SHARED_PREFERENCES.edit().putString(USER_NAME_SURNAME, String.format("%s %s", name, surname))
                .apply();
    }

    static String getUserNameSurname() {
        return SHARED_PREFERENCES.getString(USER_NAME_SURNAME, "");
    }

    //for src member and student
    //since their data is stored on our server
    public static void logIn(int user, ContentValues cv, String link, Context context) {
        new ServerCommunicator(cv) {
            @Override
            protected void onPreExecute() {
                Toast.makeText(context, "Please wait", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected void onPostExecute(String output) {
                handleLogin(user, output, context, cv);
            }
        }.execute(link);
    }

    public static void handleLogin(int user, String output, Context context, ContentValues cv) {
        switch (user) {
            case UserUtils.STUDENT:
                try {
                    JSONObject userInfo = new JSONObject(output);
                    if (userInfo.length() == 0) showLogInFailedToast(context);
                    else {
                        setUserNameSurname(userInfo.getString(ServerUtils.NAME),
                                userInfo.getString(ServerUtils.SURNAME));
                        UserManager.setUserLoggedIn(user, cv.getAsString(ServerUtils.USERNAME));
                        Intent i = new Intent(context, StudentActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    showLogInFailedToast(context);
                }
                break;

            case UserUtils.SRC_MEMBER:

                if (output.equals(ServerUtils.SUCCESS)) {
                    UserManager.setUserLoggedIn(user, cv.getAsString(ServerUtils.SRC_USERNAME));
                    Intent i = new Intent(context, SrcMemberActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                } else showLogInFailedToast(context);

                break;
        }
    }

    private static void showLogInFailedToast(Context context) {
        Toast.makeText(context, "LogIn failed", Toast.LENGTH_SHORT).show();
    }

    //clear the data that stored in preferences
    static void userLoggedOut(Context context) {
        if (SHARED_PREFERENCES == null) {
            initUserManager(context);
        }
        SHARED_PREFERENCES.edit().clear().apply();
    }
}
