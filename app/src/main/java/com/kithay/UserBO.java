package com.kithay;

/**
 * Created by Noman Sial on 6/11/2018.
 */

public class UserBO {
    int _id;
    String _name,_email,_phone,_password;

    public UserBO(){}
    public UserBO(String name,String email, String phone,String password){
        this._name=name;
        this._email=email;
        this._phone=phone;
        this._password=password;
    }
    public void setID(int id) {this._id=id;}
    public int getID(){return this._id;}

    public void setName(String name){this._name=name;}
    public String getName(){return this._name;}

    public void setPhone(String phone_number){this._phone=phone_number;}
    public String getPhone(){return this._phone;}

    public void setEmail(String email){
        this._email = email;
    }
    public String getEmail(){
        return this._email;
    }

    public void setPassword(String password){
        this._password = password;
    }
    public String getPassword(){
        return this._password;
    }

}

