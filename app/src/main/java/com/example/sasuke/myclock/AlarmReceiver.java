package com.example.sasuke.myclock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;

/**
 * Created by SASUKE on 2017/3/24.
 */

public class AlarmReceiver extends BroadcastReceiver {
    MediaPlayer mpp;
    AlarmData ad_;
    String song_path;
    Context c;
    /* (non-Javadoc)
     * @see android.content.BroadcastReceiver#onReceive(android.content.Context, android.content.Intent)
     */
    @Override
    public void onReceive(Context arg0, Intent data) {
        mpp = new MediaPlayer();
        ad_ = new AlarmData();
        String strId = data.getStringExtra("strId");
        //open unlock interface
        Intent clockIntent = new Intent(arg0, ClockAlarmActivity.class);
        clockIntent.putExtra("strId",strId);
        clockIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        arg0.startActivity(clockIntent);

        //Toast.makeText(arg0, song_path, Toast.LENGTH_SHORT).show();
    }
}