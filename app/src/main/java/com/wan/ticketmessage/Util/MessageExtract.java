package com.wan.ticketmessage.Util;

import com.wan.ticketmessage.Bean.Message;

import java.util.Calendar;

/**
 * Created by xen on 2019/1/30 0030.
 */

public class MessageExtract {
    private static String time1,time2,startTime;
    private static Message message ;

    public static Message getMessage(String data) {
        message = new  Message();
        String[] s = data.split("，");
        time1 = getStartTime(s[0]);
        time2 = getTime(s[1]);
        Calendar calendar = Calendar.getInstance();
        String year = calendar.get(Calendar.YEAR) + "年";
        startTime = year+time1+" "+time2;
        message.setStartTime(startTime);
        getStartStation(s[1]);
        getTrainNumber(s[2]);
        getTicketNumber(s[3]);
        getNameandSeat(s[4]);
        return message;
    }

    private static void getNameandSeat(String s) {
        String[] temp = s.split(" ");
        String name = temp[0];
        int start,end;
        start = temp[1].indexOf("座");
        end = temp[1].indexOf("车");
        String busNumber = temp[1].substring(start+1,end);
        start = temp[1].indexOf("厢");
        end = temp[1].indexOf("号");
        String seatNumber = temp[1].substring(start+1, end);
        message.setName(name);
        message.setBusNumber(busNumber);
        message.setSeatNumber(seatNumber);
    }

    private static void getTicketNumber(String s) {
        StringBuffer stringBuffer = new StringBuffer(s);
        stringBuffer.delete(0, 3);
        message.setTicketNumber(stringBuffer.toString());
    }

    private static void getTrainNumber(String s) {
        String temp = s.substring(0, s.length() - 4) + "次";
        message.setTrainNumber(temp);
    }

    private static void getStartStation(String s) {
        int start = s.indexOf("（");
        int end = s.lastIndexOf("）");
        StringBuffer temp = new StringBuffer(s);
        temp.delete(start, end + 1);
        String[] station = temp.toString().split("-");
        message.setStartStation(station[0]);
        message.setEndStation(station[1]);
    }

    private static String getTime(String s) {
        int start = s.indexOf("（");
        int end = s.lastIndexOf("）");
        String temp = s.substring(start+1, end);
        return temp;
    }

    private  static String getStartTime(String s) {
        StringBuffer temp = new StringBuffer(s);
        temp.delete(0, 4);
        return temp.toString();
    }

}
