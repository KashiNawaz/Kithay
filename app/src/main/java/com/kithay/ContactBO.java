package com.kithay;

/**
 * Created by Noman Sial on 6/11/2018.
 */

public class ContactBO {
    int _id;
    String _name;
    String _phone_number;
    public ContactBO() {

    }
    public ContactBO(int id,String name, String phone_number){
        this._id=id;
        this._name=name;
        this._phone_number=phone_number;
    }
    public ContactBO(int id,String name){
        this._id=id;
        this._name=name;
    }
    public ContactBO(String name,String phone_number){
        this._name=name;
        this._phone_number=phone_number;
    }

    public void setID(int id) {this._id=id;}
    public int getID(){return this._id;}

    public void setName(String name){this._name=name;}
    public String getName(){return this._name;}

    public void setPhone(String phone_number){this._phone_number=phone_number;}
    public String getPhone(){return this._phone_number;}

}
