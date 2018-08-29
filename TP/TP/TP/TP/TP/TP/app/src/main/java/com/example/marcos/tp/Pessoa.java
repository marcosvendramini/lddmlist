package com.example.marcos.tp;

/**
 * Created by Marcos on 18/09/2016.
 */
public class Pessoa {
    String tel;
    String name;
    String email;
    String postal;

    public void Pessoa(){
        tel="";
        name="";
        email="";
        postal="";
    }

    public void Pessoa(String t, String n, String e, String p){
        tel=t;
        name=n;
        email=e;
        postal=p;
    }

    public String getTel (){
        return tel;
    }
    public String getName (){
        return name;
    }
    public String getEmail (){
        return email;
    }
    public String getPostal (){
        return postal;
    }

    public void setTel (String t) {
        this.tel=t;
    }
    public void setName (String n) {
        this.name=n;
    }
    public void setEmail (String e) {
        this.email=e;
    }
    public void setPostal (String p) {
        this.postal=p;
    }
}
