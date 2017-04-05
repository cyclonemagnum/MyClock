package com.example.sasuke.myclock;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

public class MySimpleAdapter extends ArrayAdapter<AlarmData> {

    private List<AlarmData> dataList;
    private Context context ;
    private int resourceId;

    public MySimpleAdapter(Context context, int resourceId, List<AlarmData> data) {
        super(context, resourceId, data);
        this.context = context ;
        this.resourceId = resourceId;
        this.dataList = data;
        // TODO Auto-generated constructor stub
    }

    /* (non-Javadoc)
     * @see android.widget.SimpleAdapter#getView(int, android.view.View, android.view.ViewGroup)
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View view= LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        AlarmData data = dataList.get(position);
        TextView tv_time = (TextView)view.findViewById(R.id.tv_time);
        tv_time.setText(String.format("%02d", data.getTime_hour())+":"+String.format("%02d", data.getTime_minute()));
        TextView tv_name = (TextView)view.findViewById(R.id.view1);
        tv_name.setText(data.getName());
        String strWeek="";
        if(data.getWeek0())
            strWeek+="Sun ";
        if(data.getWeek1())
            strWeek+="Mon ";
        if(data.getWeek2())
            strWeek+="Tue ";
        if(data.getWeek3())
            strWeek+="Wes ";
        if(data.getWeek4())
            strWeek+="Thu ";
        if(data.getWeek5())
            strWeek+="Fri ";
        if(data.getWeek6())
            strWeek+="Sat ";

        TextView tv_week = (TextView)view.findViewById(R.id.view2);
        tv_week.setText(strWeek);
        ImageButton btn=(ImageButton) view.findViewById(R.id.imageButton);
        if(data.getIsOpen()){
            btn.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_alarm_on_black_24dp));
        }else{
            btn.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_alarm_off_black_24dp));
        }
        btn.setTag(position);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(dataList.get(position).getIsOpen()){
                    dataList.get(position).setIsOpen(false);
                    dataList.get(position).save();
                    ImageButton btn_ = (ImageButton)v;
                    btn_.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_alarm_off_black_24dp));
                }else{
                    dataList.get(position).setIsOpen(true);
                    dataList.get(position).save();
                    ImageButton btn_ = (ImageButton)v;
                    btn_.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_alarm_on_black_24dp));
                }
            }
        });
        return view;
    }
}
