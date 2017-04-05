package com.example.sasuke.myclock;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.example.sasuke.myclock.R.id.timePicker;

/**
 * Created by SASUKE on 2017/3/18.
 */

public class Edit extends AppCompatActivity {
    private static final int INTERVAL = 1000 * 60 * 60 * 24;// 24h

    private Button cancel;
    private Button done;
    private ToggleButton[] tb;
    private TimePicker tp;
    private Calendar  calendar;
    private ListView search;
    private List<Song> list;
    private List<String> selectsong;

    private String strId;
    private TextView tv_type;
    private TextView tv_song_path;
    private TextView tv_song;
    private TextView tv_unlock_way;
    private EditText ed_name;
    AlarmData data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit);
        SysApplication.getInstance().addActivity(this);

        Intent intent=getIntent();
        strId = intent.getStringExtra("id_");

        //timepicker
        calendar = Calendar.getInstance();
        tp=(TimePicker) findViewById(timePicker);
        cancel= (Button) findViewById(R.id.btn_Cancel);
        done= (Button) findViewById(R.id.btn_Done);
        tv_type=(TextView) findViewById(R.id.tv_type);
        tv_song_path=(TextView) findViewById(R.id.tv_song_path);
        tv_song=(TextView) findViewById(R.id.tv_song);
        tv_unlock_way=(TextView) findViewById(R.id.tv_unlock);
        ed_name=(EditText) findViewById(R.id.ev_name);

        tbInit();
        if(strId != null) {
            data = DataSupport.where("identity = ?", strId).findFirst(AlarmData.class);
            if(data.getWeek0())tb[0].setChecked(true);
            else{
                tb[0].setChecked(false);
                tb[0].setBackgroundColor(0000);
            }
            if(data.getWeek1())tb[1].setChecked(true);
            else{
                tb[1].setChecked(false);
                tb[1].setBackgroundColor(0000);
            }
            if(data.getWeek2())tb[2].setChecked(true);
            else{
                tb[2].setChecked(false);
                tb[2].setBackgroundColor(0000);

            }
            if(data.getWeek3())tb[3].setChecked(true);
            else {
                tb[3].setChecked(false);
                tb[3].setBackgroundColor(0000);
            }
            if(data.getWeek4())tb[4].setChecked(true);
            else {
                tb[4].setChecked(false);
                tb[4].setBackgroundColor(0000);
            }
            if(data.getWeek5())tb[5].setChecked(true);
            else{
                tb[5].setChecked(false);
                tb[5].setBackgroundColor(0000);
            }
            if(data.getWeek6())tb[6].setChecked(true);
            else{
                tb[6].setChecked(false);
                tb[6].setBackgroundColor(0000);
            }
            tp.setCurrentHour(data.getTime_hour());
            tp.setCurrentMinute(data.getTime_minute());
            tv_type.setText(data.getType());
            tv_song_path.setText(data.getSongpath());
            tv_song.setText(data.getSong());
            tv_unlock_way.setText(data.getUnlock());
            ed_name.setText(data.getName());
        }

        View choose_alarmtype = (LinearLayout)findViewById(R.id.Alarm_Type);
        choose_alarmtype.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Edit.this);
                builder.setTitle("Alarm Type");
                int type_=0;
                String type = tv_type.getText().toString();
                if(type.equals("Bell"))
                    type_=0;
                if(type.equals("Vibrate"))
                    type_=1;
                if(type.equals("Bell and Vibrate"))
                    type_=2;

                builder.setSingleChoiceItems(new String[]{"Bell","Vibrate","Bell and Vibrate"}, type_,
                        new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        String[] a =new String[]{"Bell","Vibrate","Bell and Vibrate"};
                        tv_type.setText(a[which]);
                    }
                });
                builder.setNegativeButton("OK",null);
                builder.show();
            }
        });

        View choose_alarmsong = (LinearLayout)findViewById(R.id.Alarm_Song);
        choose_alarmsong.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog aDialog;
                aDialog=new AlertDialog.Builder(Edit.this)
                        .setTitle("Alarm Bell")
                        .show();
                aDialog.getWindow().setLayout(1000, 1600);
                aDialog.getWindow().setContentView(R.layout.choose_bell);
                //把扫描到的音乐赋值给list
                list = new ArrayList<>();
                //把扫描到的音乐赋值给list
                list = MusicUtils.getMusicData(Edit.this);
                selectsong = new ArrayList<>();
                for(int i=0;i<list.size();i++)
                {
                    String a=list.get(i).song;
                    selectsong.add(a);
                }
                search=(ListView) aDialog.getWindow().findViewById(R.id.choose_bell);
                search.setAdapter(new ArrayAdapter<String>(Edit.this,
                        android.R.layout.simple_expandable_list_item_1,selectsong));
                search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String song=search.getItemAtPosition(position).toString();
                        String song_path="";
                        tv_song.setText(song);
                        for(int i=0;i<list.size();i++)
                        {
                            if(song.equals(list.get(i).song))
                            {
                                song_path=list.get(i).path;
                                break;
                            }

                        }
                        tv_song_path.setText(song_path);
                    }
                });

            }
        });

        View choose_unlockway = (LinearLayout) findViewById(R.id.Unlock_Way);
        choose_unlockway.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Edit.this);
                builder.setTitle("Alarm Type");

                int unlock_ = 0;
                String unlock = tv_unlock_way.getText().toString();
                if (unlock.equals("Calculate"))
                    unlock_ = 0;
                if (unlock.equals("Shake"))
                    unlock_ = 1;
                builder.setSingleChoiceItems(new String[]{"Calculate", "Shake"}, unlock_, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        String[] a =new String[]{"Calculate", "Shake"};
                        tv_unlock_way.setText(a[which]);
                    }
                });
                builder.setNegativeButton("OK",null);
                builder.show();


            }
        });
        tp.setIs24HourView(true);
        /*tp.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                calendar.set(Calendar.MINUTE,minute);
                calendar.set(Calendar.SECOND,0);
            }
        });*/
    }
    public void onClick_Event(View v){
        switch (v.getId()){
            case R.id.btn_Cancel:
            {
                Intent intent = new Intent(Edit.this,MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            }
            case R.id.btn_Done:
            {
                //save in database
                if(strId == null){
                    SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
                    strId = df.format(new Date());
                }
                calendar.set(Calendar.HOUR_OF_DAY,tp.getCurrentHour());
                calendar.set(Calendar.MINUTE,tp.getCurrentMinute());
                calendar.set(Calendar.SECOND,0);
                AlarmData ad = new AlarmData();
                ad.setName(ed_name.getText().toString());
                ad.setSong(tv_song.getText().toString());
                ad.setSongpath(tv_song_path.getText().toString());
                ad.setTime_hour(calendar.get(Calendar.HOUR_OF_DAY));
                ad.setTime_minute(calendar.get(Calendar.MINUTE));
                ad.setType(tv_type.getText().toString());
                ad.setUnlock(tv_unlock_way.getText().toString());
                ad.setIdentity(strId);
                ad.setIsOpen(true);
                if(tb[0].isChecked()) ad.setWeek0(true);
                else ad.setWeek0(false);
                if(tb[1].isChecked()) ad.setWeek1(true);
                else ad.setWeek1(false);
                if(tb[2].isChecked()) ad.setWeek2(true);
                else ad.setWeek2(false);
                if(tb[3].isChecked())ad.setWeek3(true);
                else ad.setWeek3(false);
                if(tb[4].isChecked())ad.setWeek4(true);
                else ad.setWeek4(false);
                if(tb[5].isChecked())ad.setWeek5(true);
                else ad.setWeek5(false);
                if(tb[6].isChecked())ad.setWeek6(true);
                else ad.setWeek6(false);

                //if id 存在,更新数据库，不存在，创立新数据
                AlarmData ad_c= DataSupport.select("identity").where("identity =?", strId).findFirst(AlarmData.class);
                if(ad_c == null){
                    ad.save();
                }
                else{
                    ad_c.setSongpath(ad.getSongpath());
                    ad_c.setName(ad.getName());
                    ad_c.setSong(ad.getSong());
                    ad_c.setTime_hour(ad.getTime_hour());
                    ad_c.setTime_minute(ad.getTime_minute());
                    ad_c.setIsOpen(ad.getIsOpen());
                    ad_c.setType(ad.getType());
                    ad_c.setUnlock(ad.getUnlock());
                    ad_c.setWeek0(ad.getWeek0());
                    ad_c.setWeek1(ad.getWeek1());
                    ad_c.setWeek2(ad.getWeek2());
                    ad_c.setWeek3(ad.getWeek3());
                    ad_c.setWeek4(ad.getWeek4());
                    ad_c.setWeek5(ad.getWeek5());
                    ad_c.setWeek6(ad.getWeek6());
                    ad_c.save();
                    //ad.updateAll("identity =?", strId);
                    Toast.makeText(this, "Update", Toast.LENGTH_LONG).show();
                }

                //a
                Intent intent_receiver = new Intent(this, AlarmReceiver.class);
                intent_receiver.putExtra("strId", strId);

                PendingIntent sender = PendingIntent.getBroadcast(this,
                        0, intent_receiver, PendingIntent.FLAG_UPDATE_CURRENT);

                // Schedule the alarm!
                AlarmManager am = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);

                /*am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                INTERVAL, sender);*/
                am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);

                Toast.makeText(this, "Set Alarm："+calendar.get(Calendar.HOUR_OF_DAY) +
                        ":"+calendar.get(Calendar.MINUTE), Toast.LENGTH_SHORT).show();

                Intent intent_back = new Intent(Edit.this,MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent_back);

                break;
            }
        }

    }

    /*public void Choose_Alarm_Type(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Edit.this);
        builder.setTitle("Alarm Type");
        builder.setSingleChoiceItems(new String[]{"Bell","Shock","Bell and Shock"}, 0, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Cancel",null);
        builder.show();
    }*/
    private void tbInit(){
        tb=new ToggleButton[7];
        tb[0]=(ToggleButton) findViewById(R.id.tb1);
        tb[1]=(ToggleButton) findViewById(R.id.tb2);
        tb[2]=(ToggleButton) findViewById(R.id.tb3);
        tb[3]=(ToggleButton) findViewById(R.id.tb4);
        tb[4]=(ToggleButton) findViewById(R.id.tb5);
        tb[5]=(ToggleButton) findViewById(R.id.tb6);
        tb[6]=(ToggleButton) findViewById(R.id.tb7);

        View.OnClickListener listener=new View.OnClickListener() {
            public void onClick(View v) {
                // 当按钮第一次被点击时候响应的事件
                if (((ToggleButton)v).isChecked()) {
                    v.setBackgroundColor(0xff99cc00);
                }
                // 当按钮再次被点击时候响应的事件
                else{
                    v.setBackgroundColor(0000);
                }

            }
        };
        String[] week={"Sun","Mon","Tue","Wes","Thu","Fri","Sta"};
        for(int i=0;i<7;i++)
        {

            tb[i].setText(week[i]);
            tb[i].setTextOff(week[i]);
            tb[i].setTextOn(week[i]);
            tb[i].setOnClickListener(listener);
        }

    }

}
