package com.example.sasuke.myclock;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView list_clock;
    private ListView search;
    private List<Song> list;
    List<String> selectsong;
    List<String> idList;
    List<AlarmData> dataList;
    static int alarm_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SysApplication.getInstance().addActivity(this);

        alarm_id = 0;
        list_clock = (ListView) findViewById(R.id.list_clock);
        search = (ListView) findViewById(R.id.search);

        idList = new ArrayList<>();
        //创建闹钟数据库
        LitePal.getDatabase();
        dataList = DataSupport.select("*").find(AlarmData.class);
        for (AlarmData each : dataList) {
            idList.add(each.getIdentity());
        }
        final MySimpleAdapter adapter = new MySimpleAdapter(this, R.layout.item, dataList);
        list_clock.setAdapter(adapter);
        //添加监听器
        list_clock.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(MainActivity.this, Edit.class);
                intent.putExtra("id_", idList.get(position));
                startActivity(intent);
            }
        });

        list_clock.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, View view,
                                           final int position, long id) {
                //long press pop up this dialog to delete data
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Delete?");
                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DataSupport.deleteAll(AlarmData.class, "identity =?", idList.get(position));
                        dataList.remove(position);
                        idList.remove(position);
                        adapter.notifyDataSetChanged();
                        list_clock.setAdapter(adapter);

                    }
                });
                builder.show();
                return true;
            }
        });
    }
    public void new_alarm(View v){
        Intent intent=new Intent(MainActivity.this, Edit.class);
        startActivity(intent);

    }

}
    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/




