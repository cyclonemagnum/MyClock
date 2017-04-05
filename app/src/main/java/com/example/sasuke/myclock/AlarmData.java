package com.example.sasuke.myclock;

import org.litepal.crud.DataSupport;

/**
 * Created by SASUKE on 2017/3/24.
 */

public class AlarmData extends DataSupport{
    /**
     * 闹钟id
     */
    private String identity;

    private String songpath;

    public String getSongpath() {
        return songpath;
    }

    public void setSongpath(String songpath) {
        this.songpath = songpath;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public int getTime_hour() {
        return time_hour;
    }

    public void setTime_hour(int time_hour) {
        this.time_hour = time_hour;
    }

    public int getTime_minute() {
        return time_minute;
    }

    public void setTime_minute(int time_minute) {
        this.time_minute = time_minute;
    }

    public String getType() {return type;}

    public void setType(String type) {
        this.type = type;
    }

    public String getUnlock() {
        return unlock;
    }

    public void setUnlock(String unlock) {
        this.unlock = unlock;
    }

    /**

     * 闹钟名
     */
    private String name;
    /**
     * 本地铃声地址
     */
    private String song;
    /**
     * 闹钟时间
     */
    private int time_hour;
    private int time_minute;
    /**
     * 响铃模式
     */
    private String type;
    /**
     * 解锁方式
     */
    private String unlock;

    private Boolean week0;
    private Boolean week1;
    private Boolean week2;
    private Boolean week3;
    private Boolean week4;
    private Boolean week5;
    private Boolean week6;

    public Boolean getWeek0() {
        return week0;
    }

    public void setWeek0(Boolean week0) {
        this.week0 = week0;
    }

    public Boolean getWeek1() {
        return week1;
    }

    public void setWeek1(Boolean week1) {
        this.week1 = week1;
    }

    public Boolean getWeek2() {
        return week2;
    }

    public void setWeek2(Boolean week2) {
        this.week2 = week2;
    }

    public Boolean getWeek3() {
        return week3;
    }

    public void setWeek3(Boolean week3) {
        this.week3 = week3;
    }

    public Boolean getWeek4() {
        return week4;
    }

    public void setWeek4(Boolean week4) {
        this.week4 = week4;
    }

    public Boolean getWeek5() {
        return week5;
    }

    public void setWeek5(Boolean week5) {
        this.week5 = week5;
    }

    public Boolean getWeek6() {
        return week6;
    }

    public void setWeek6(Boolean week6) { this.week6 = week6; }

    private Boolean isopen;

    public Boolean getIsOpen() {
        return isopen;
    }

    public void setIsOpen(Boolean open) {
        isopen = open;
    }

}
