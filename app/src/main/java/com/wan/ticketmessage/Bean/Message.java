package com.wan.ticketmessage.Bean;

import android.util.Log;

import org.litepal.crud.LitePalSupport;

import java.util.Calendar;

/**
 * Created by xen on 2019/1/30 0030.
 */

public class Message extends LitePalSupport {
    private String trainNumber;
    private String startStation;
    private String endStation;
    private String startTime;
    private String arriveTime;
    private String busNumber;
    private String seatNumber;
    private String name;
    private boolean isOutDate;

    public boolean isOutDate() {
        return isOutDate;
    }

    public void setOutDate(boolean outDate) {
        isOutDate = outDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public Message(String trainNumber, String startStation, String endStation, String startTime, String arriveTime, String busNumber, String seatNumber, String name, String ticketNumber) {
        this.trainNumber = trainNumber;
        this.startStation = startStation;
        this.endStation = endStation;
        this.startTime = startTime;
        this.arriveTime = arriveTime;
        this.busNumber = busNumber;
        this.seatNumber = seatNumber;
        this.name = name;
        this.ticketNumber = ticketNumber;
    }

    private String ticketNumber;

    public Message() {

    }
    public Message(String trainNumber, String startStation, String endStation, String startTime, String arriveTime, String busNumber, String seatNumber) {
        this.trainNumber = trainNumber;
        this.startStation = startStation;
        this.endStation = endStation;
        this.startTime = startTime;
        this.arriveTime = arriveTime;
        this.busNumber = busNumber;
        this.seatNumber = seatNumber;
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }

    public String getStartStation() {
        return startStation;
    }

    public void setStartStation(String startStation) {
        this.startStation = startStation;
    }

    public String getEndStation() {
        return endStation;
    }

    public void setEndStation(String endStation) {
        this.endStation = endStation;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(String arriveTime) {
        this.arriveTime = arriveTime;
    }

    public String getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(String busNumber) {
        this.busNumber = busNumber;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    //每次都得更新，进行设置
    public void setOutDate() {
        String time = this.getStartTime();
        String[] temp = time.split(" ");
        int index,index1,index2;
        String year, month,day,hour,minute;
        index = temp[0].indexOf("年");
        year = temp[0].substring(0,index);
        index1 = temp[0].indexOf("月");
        month = temp[0].substring(index+1,index1);
        if (month.startsWith("0")) {
            month = month.replace("0", "");
        }
        index2 = temp[0].indexOf("日");
        day =  temp[0].substring(index1+1,index2);
        if (day.startsWith("0")) {
            day = day.replace("0", "");
        }
        String[] t = temp[1].split(":");
        hour = t[0];
        minute = t[1];
        boolean flag = panDuanOutDate(year, month, day, hour, minute);
        Log.d("---标志？？--", ""+flag);
        setOutDate(flag);
    }

    public boolean panDuanOutDate(String year,String month, String day, String hour, String minute) {
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH) + 1;
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMin = calendar.get(Calendar.MINUTE);

        int y = Integer.valueOf(year);
        int mon = Integer.valueOf(month);
        int d = Integer.valueOf(day);
        int h = Integer.valueOf(hour);
        int min = Integer.valueOf(minute);

        if (currentYear < y ) {
            return false;
        } else if (currentYear == y && currentMonth < mon) {
            return false;
        } else if (currentYear==y && currentMonth ==mon && currentDay < d) {
            return false;
        } else if (currentYear==y && currentMonth ==mon && currentDay == d &&currentHour < h) {
            return false;
        } else if (currentYear==y && currentMonth ==mon && currentDay == d &&currentHour == h &&currentMin < min) {
            return false;
        } else {
            return true;
        }

    }
}
