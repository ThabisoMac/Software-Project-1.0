package com.wits.witssrcconnect.managers;

import android.content.ContentValues;
import android.content.Context;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.TextInputEditText;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.LinearLayout;

import com.wits.witssrcconnect.R;
import com.wits.witssrcconnect.utils.ServerUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static android.support.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;

@RunWith(AndroidJUnit4.class)
public class UiManagerTest {

    private Context c = InstrumentationRegistry.getTargetContext();

    @Test
    public void forceBottomSheetToFullyExpand() {
        try {
            runOnUiThread(() -> {
                BottomSheetDialog dialog = new BottomSheetDialog(c);
                dialog.setOnShowListener(UiManager::forceBottomSheetToFullyExpand);
                dialog.show();
            });
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    ////////////////////////////////////////////////////////////////////////MODIFY AGAIN HERE
    @Test
    public void populateWithSrcActivities(){
        try {
            runOnUiThread(()->{
                try {
                    LinearLayout holder = new LinearLayout(c);
                    holder.setOrientation(LinearLayout.VERTICAL);
                    FragmentManager fragmentManager = Mockito.mock(FragmentManager.class);
                    String output = "[" +
                            "{\"activity_id\":4,\"member_username\":\"srcpresident\",\"activity_title\":\"title\",\"activity_desc\":\"activity\",\"activity_date\":\"11\\/05\\/2019\",\"activity_time\":\"23:04\"}," +
                            "{\"activity_id\":5,\"member_username\":\"srcpresident\",\"activity_title\":\"title\",\"activity_desc\":\"activity\",\"activity_date\":\"11\\/05\\/2019\",\"activity_time\":\"23:04\"}," +
                            "{\"activity_id\":9,\"member_username\":\"srcpresident\",\"activity_title\":\"title\",\"activity_desc\":\"activity\",\"activity_date\":\"13\\/05\\/2019\",\"activity_time\":\"08:09\"}," +
                            "{\"activity_id\":8,\"member_username\":\"srcpresident\",\"activity_title\":\"title\",\"activity_desc\":\"activity\",\"activity_date\":\"13\\/05\\/2019\",\"activity_time\":\"08:06\"}," +
                            "{\"activity_id\":7,\"member_username\":\"srcpresident\",\"activity_title\":\"title\",\"activity_desc\":\"activity\",\"activity_date\":\"13\\/05\\/2019\",\"activity_time\":\"08:01\"}," +
                            "{\"activity_id\":6,\"member_username\":\"srcpresident\",\"activity_title\":\"title\",\"activity_desc\":\"activity\",\"activity_date\":\"13\\/05\\/2019\",\"activity_time\":\"07:54\"}]";
                    JSONArray jsonArray = new JSONArray(output);
                    UiManager.populateWithSrcActivities(holder, jsonArray, false);
                    UiManager.populateWithSrcActivities(holder, jsonArray, true);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            });
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    @Test
    public void sendComment(){
        try {
            runOnUiThread(()->{
                TextInputEditText comment = new TextInputEditText(c);
                int[] a = new int[1];
                a[0] = ServerUtils.ANONYMOUS_COMMENT_ON;

                UiManager.sendComment(comment, a, anyInt());
                comment.setText("test text");
                UiManager.sendComment(comment, a, anyInt());
            });
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    @Test
    public void openComments(){
        try {
            runOnUiThread(()->{
                UiManager.openComments(anyInt(), anyString(), anyString(), c);
            });
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

    }

    @Test
    public void deleteActivity(){
        try {
            runOnUiThread(()->{
                LinearLayout holder = new LinearLayout(c);
                holder.setOrientation(LinearLayout.VERTICAL);
                View activityItemView = View.inflate(holder.getContext(), R.layout.item_src_activity_card, null);
                holder.addView(activityItemView);
                UiManager.deleteActivity(anyInt(), activityItemView, holder);
            });
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

    }

    @Test
    public void updateActivity(){
        try {
            runOnUiThread(()->{
                UiManager.updateActivity(c, anyInt(), anyString(), anyString());
            });
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    @Test
    public void handleAnonymity(){
        try {
            runOnUiThread(()->{
                int[] a = new int[1];
                UiManager.handleAnonymity(true, a, c);
                UiManager.handleAnonymity(false, a, c);
            });
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

    }

    @Test
    public void deleteItem(){
        try {
            runOnUiThread(()->{
                UiManager.deleteItem(new ContentValues(), new LinearLayout(c), new View(c), anyString());
            });
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

    }

    @Test
    public void handleDeleteItemFeedback(){
        try {
            runOnUiThread(()->{
                LinearLayout l = new LinearLayout(c);
                l.setOrientation(LinearLayout.VERTICAL);

                View v = new View(c);
                l.addView(v);

                UiManager.handleDeleteItemFeedback(ServerUtils.FAILED, l, v);
                UiManager.handleDeleteItemFeedback(ServerUtils.SUCCESS, l, v);
            });
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }


    }

    @Test
    public void populateWithPolls(){

        try {
            runOnUiThread(()->{
                String output = "[" +
                        "{\"poll_id\":12,\"member_username\":\"1627982\",\"poll_title\":\"test\",\"poll_desc\":\"desc\",\"poll_choices\":\"opt1~opt2\",\"poll_type\":0,\"poll_date\":\"15\\/05\\/2019\",\"poll_time\":\"09:35\"}," +
                        "{\"poll_id\":11,\"member_username\":\"1627982\",\"poll_title\":\"Test\",\"poll_desc\":\"test desc\",\"poll_choices\":\"opt1~opt2\",\"poll_type\":1,\"poll_date\":\"12\\/05\\/2019\",\"poll_time\":\"16:00\"}]";
                LinearLayout holder = new LinearLayout(c);
                holder.setOrientation(LinearLayout.VERTICAL);
                try {
                    JSONArray polls = new JSONArray(output);
                    JSONObject jsonObject = polls.getJSONObject(0);
                    UiManager.openPollVoteBottomSheet(jsonObject, new String[]{"opt1", "opt2"}, c);
                    UiManager.populateWithPolls(holder, polls);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            });
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    @Test
    public void openPollVoteBottomSheetSuccess(){
        try {
            runOnUiThread(()->{
                try {
                    JSONObject testJsonObject = new JSONObject();
                    testJsonObject.put(ServerUtils.POLL_ID, 2);
                    testJsonObject.put(ServerUtils.POLL_TITLE, anyString());
                    testJsonObject.put(ServerUtils.SRC_USERNAME, anyString());
                    testJsonObject.put(ServerUtils.POLL_DATE, anyString());
                    testJsonObject.put(ServerUtils.POLL_TIME, anyString());
                    testJsonObject.put(ServerUtils.POLL_DESC, anyString());
                    String[] options = {"opt1","opt2","opt3"};
                    testJsonObject.put(ServerUtils.POLL_TYPE, ServerUtils.POLL_TYPE_MULTI_SELECT);
                    UiManager.openPollVoteBottomSheet(testJsonObject, options, c);
                    testJsonObject.put(ServerUtils.POLL_TYPE, ServerUtils.POLL_TYPE_SINGLE_SELECT);
                    UiManager.openPollVoteBottomSheet(testJsonObject, options, c);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            });
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    @Test
    public void openPollVoteBottomFailed(){
        try {
            runOnUiThread(()->{
                JSONObject testJsonObject = new JSONObject();
                String[] options = {"opt1","opt2","opt3"};
                UiManager.openPollVoteBottomSheet(testJsonObject, options, c);
            });
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    @Test
    public void performDeleteAction(){
        try {
            runOnUiThread(()->{
                UiManager.performDeleteAction(new ContentValues(), new LinearLayout(c), new View(c), anyString());
            });
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

}