package com.vinay.betforparty;

public class Matchdet {

    private String Mdate;
    private String Mtime;
    private String Team1;
    private String Team2;

    public Matchdet(){

    }

    public Matchdet(String mdate, String mtime, String team1, String team2) {
        Mdate = mdate;
        Mtime = mtime;
        Team1 = team1;
        Team2 = team2;
    }

    public String getMdate() {
        return Mdate;
    }

    public void setMdate(String mdate) {
        Mdate = mdate;
    }

    public String getMtime() {
        return Mtime;
    }

    public void setMtime(String mtime) {
        Mtime = mtime;
    }

    public String getTeam1() {
        return Team1;
    }

    public void setTeam1(String team1) {
        Team1 = team1;
    }

    public String getTeam2() {
        return Team2;
    }

    public void setTeam2(String team2) {
        Team2 = team2;
    }
}
