package com.example.sasuke.myclock;

import android.app.Service;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.Random;

public class ClockAlarmActivity extends AppCompatActivity {
    private MediaPlayer mp;
    private Vibrator vb;
    private String song_path;
    AlarmData ad_;
    TextView calculate;
    EditText re;
    int result;
    int shake_count;
    TextView tv_shakecount;
    private PowerManager.WakeLock mWakelock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SysApplication.getInstance().addActivity(this);

        //Pop up window
        requestWindowFeature(Window.FEATURE_NO_TITLE); // hide title
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        winParams.flags |= (WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        mp = new MediaPlayer();
        String strId = getIntent().getStringExtra("strId");
        //mp=new MediaPlayer();
        ad_ = DataSupport.where("identity =?", strId).findFirst(AlarmData.class);
        song_path = ad_.getSongpath();
        if (ad_.getType().equals("Vibrate")) {
            Vibration();
            Toast.makeText(this, "ALARM!!!!!", Toast.LENGTH_SHORT).show();
        }
        if (ad_.getType().equals("Bell")) {
            Play();
        }
        if (ad_.getType().equals("Bell and Vibrate")) {
            Vibration();
            Play();
        }

        if (ad_.getUnlock().equals("Calculate")) {
            setContentView(R.layout.receiver_calculate);
            calculate = (TextView) findViewById(R.id.calculate);
            re = (EditText) findViewById(R.id.re);
            Calculate();
        }
        if (ad_.getUnlock().equals("Shake")) {
            setContentView(R.layout.receiver_shake);
            tv_shakecount = (TextView) findViewById(R.id.tv_shakecount);
            Shake();
        }

        //showDialogInBroadcastReceiver(strId);
    }

    public void Vibration() {
        vb = (Vibrator) this.getSystemService(Service.VIBRATOR_SERVICE);
        vb.vibrate(new long[]{100, 10, 100, 600}, 0);
    }

    public void Play() {
        if (song_path.equals("Default music")) {
            mp = MediaPlayer.create(this, R.raw.shapeofyou);
            mp.start();

        } else {
            try {
                mp.setDataSource(song_path);
                mp.prepare();
                mp.start();
            } catch (IOException e) {
                Toast.makeText(this, song_path, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void Calculate() {
        Random random1 = new Random();
        Random random2 = new Random();
        Random random3 = new Random();
        Random random4 = new Random();
        int r1 = random1.nextInt(10);
        int r2 = random2.nextInt(20);
        int r3 = random3.nextInt(10);
        int r4 = random4.nextInt(20);
        result = r1*r2 + r3*r4;
        calculate.setText(r1 + "*" + r2 + "+" + r3 + "*" + r4+"=???");
    }

    public void ca_unlock(View v) {
        int r = Integer.valueOf(re.getText().toString());
        if (r == result) {
            mp.stop();
            SysApplication.getInstance().exit();
        } else
            Toast.makeText(this, "Wrong!", Toast.LENGTH_SHORT).show();
    }

    public void Shake() {
        shake_count = 0;
        SensorManagerHelper sensorHelper = new SensorManagerHelper(this);
        sensorHelper.setOnShakeListener(new SensorManagerHelper.OnShakeListener() {
            @Override
            public void onShake() {
                // TODO Auto-generated method stub
                tv_shakecount.setText(String.valueOf(++shake_count));
                if (shake_count == 30) {
                    mp.stop();
                    SysApplication.getInstance().exit();
                }

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        // awake screen
        acquireWakeLock();
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseWakeLock();
    }

    /**
     * awake screen
     */
    private void acquireWakeLock() {
        if (mWakelock == null) {
            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            mWakelock = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP
                    | PowerManager.SCREEN_DIM_WAKE_LOCK, this.getClass()
                    .getCanonicalName());
            mWakelock.acquire();
        }
    }

    /**
     * release unlock
     */
    private void releaseWakeLock() {
        if (mWakelock != null && mWakelock.isHeld()) {
            mWakelock.release();
            mWakelock = null;
        }
    }
}


    /*
        //数组参数意义：第一个参数为等待指定时间后开始震动，震动时间为第二个参数。后边的参数依次为等待震动和震动的时间
        //第二个参数为重复次数，-1为不重复，0为一直震动
        if (flag == 0 || flag == 2) {
            vibrator = (Vibrator) this.getSystemService(Service.VIBRATOR_SERVICE);
            vibrator.vibrate(new long[]{100, 10, 100, 600}, 0);
        }*/


