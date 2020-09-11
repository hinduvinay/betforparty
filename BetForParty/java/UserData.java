package com.vinay.betforparty;

public class UserData {
    public String UName;
    public String UEmail;

    public UserData(){

    }

    public UserData(String UName, String UEmail) {
        this.UName = UName;
        this.UEmail = UEmail;
    }

    public String getUName() {
        return UName;
    }

    public void setUName(String UName) {
        this.UName = UName;
    }

    public String getUEmail() {
        return UEmail;
    }

    public void setUEmail(String UEmail) {
        this.UEmail = UEmail;
    }
}
