package com.vinay.betforparty;

public class Prediction {
    private String Team1;
    private String Team2;
    private String Pred;
    private String Winner;
    private String Date;
    private int Amt;
    private int Mcnt;

    public Prediction() {

    }

    public Prediction(String team1, String team2, String pred, String winner, String date, int amt, int mcnt) {
        Team1 = team1;
        Team2 = team2;
        Pred = pred;
        Winner = winner;
        Date = date;
        Amt = amt;
        Mcnt = mcnt;
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

    public String getPred() {
        return Pred;
    }

    public void setPred(String pred) {
        Pred = pred;
    }

    public String getWinner() {
        return Winner;
    }

    public void setWinner(String winner) {
        Winner = winner;
    }

    public int getAmt() {
        return Amt;
    }

    public void setAmt(int amt) {
        Amt = amt;
    }

    public int getMcnt() {
        return Mcnt;
    }

    public void setMcnt(int mcnt) {
        Mcnt = mcnt;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}