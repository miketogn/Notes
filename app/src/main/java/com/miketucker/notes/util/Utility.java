package com.miketucker.notes.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utility {

    // return current time with simple format

    public static String getCurrentTimeStamp(){

        try{
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM-yyyy");
            String currentDateTime = dateFormat.format(new Date());

            return currentDateTime;
        }catch(Exception e){
            return null;
        }
    }

    // create month name strings for timestamp

    public static String getMonthFromNumber(String monthNumber){

        switch (monthNumber){
            case "01":{
                return "Jan";
            }
            case "02":{
                return "Feb";
            }
            case "03":{
                return "Mar";
            }
            case "04":{
                return "Apr";
            }
            case "05":{
                return "May";
            }
            case "06":{
                return "Jun";
            }
            case "07":{
                return "Jul";
            }
            case "08":{
                return "Aug";
            }
            case "09":{
                return "Sep";
            }
            case "10":{
                return "Oct";
            }
            case "11":{
                return "Nov";
            }
            case "12":{
                return "Dec";
            }

            default:{
                return "Error";
            }
        }
    }
}
