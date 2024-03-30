package com.example.login_firebase;

public class ReadwriteUserDetail {
    public String  doB, gender;
    public ReadwriteUserDetail(){};

    public ReadwriteUserDetail(String textDob, String textGender){
        this.doB=textDob;
        this.gender=textGender;
    }
}
