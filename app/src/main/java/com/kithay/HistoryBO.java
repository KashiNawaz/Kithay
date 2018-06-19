package com.kithay;

/**
 * Created by Noman Sial on 6/11/2018.
 */

public class HistoryBO {

    int _id;
    String _name;
    String _phone_number;
    String _lang;
    String _lat;
    String _time;
    String _date;
    String _batteryLevel;
    String _address;

    public HistoryBO() {

    }
    public HistoryBO(String name, String phone_number,String lang,String lat, String date,String time){
        this._name=name;
        this._phone_number=phone_number;
        this._date=date;
        this._time=time;
        this._lang=lang;
        this._lat=lat;
    }
    public HistoryBO(String name, String phone_number,String lang,String lat, String date,String time,String batteryLevel,String address){
        this._name=name;
        this._phone_number=phone_number;
        this._date=date;
        this._time=time;
        this._lang=lang;
        this._lat=lat;
        this._batteryLevel=batteryLevel;
        this._address=address;
    }

    public HistoryBO(int id,String name, String phone_number,String lang,String lat, String date,String time,String batteryLevel,String address){
        this._id=id;
        this._name=name;
        this._phone_number=phone_number;
        this._date=date;
        this._time=time;
        this._lang=lang;
        this._lat=lat;
        this._batteryLevel=batteryLevel;
        this._address=address;
    }

    public HistoryBO(int id,String name, String phone_number,String lang,String lat, String date,String time){
        this._id=id;
        this._name=name;
        this._phone_number=phone_number;
        this._date=date;
        this._time=time;
        this._lang=lang;
        this._lat=lat;
    }

    public void setID(int id) {this._id=id;}
    public int getID(){return this._id;}

    public void setName(String name){this._name=name;}
    public String getName(){return this._name;}

    public void setPhone(String phone_number){this._phone_number=phone_number;}
    public String getPhone(){return this._phone_number;}

    public void setDate(String date){
        this._date = date;
    }
    public String getDate(){
        return this._date;
    }

    public void settime(String time){
        this._time = time;
    }
    public String gettime(){
        return this._time;
    }

    public void setLang(String lang){this._lang=lang;}
    public String getLang(){return this._lang;}

    public void setLat(String lat){this._lat=lat;}
    public String getLat(){return this._lat;}

    public void setBattery(String bat){this._batteryLevel=bat;}
    public String getBattery(){return this._batteryLevel;}

    public void setAddress(String add){this._address=add;}
    public String getAddress(){return this._address;}

}
