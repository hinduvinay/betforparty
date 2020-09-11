package com.vinay.betforparty;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.Date;

public class OnematchActivity extends AppCompatActivity {

    SimpleDateFormat now = new SimpleDateFormat("HH:mm:ss");
    private long RemTime;

    private TextView Timer,Date,Time;
    private Button Team1,Team2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onematch);

        Timer = findViewById(R.id.timer);
        Date = findViewById(R.id.mdate);
        Time = findViewById(R.id.mtime);
        Team1 = findViewById(R.id.team1);
        Team2 = findViewById(R.id.team2);
        Team1.setEnabled(false);
        Team2.setEnabled(false);

        getMatchdet();

        Team1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetButton1(Team1,Team2,HomeActivity.ShortTeam1_1);
            }
        });

        Team2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetButton1(Team2,Team1,HomeActivity.ShortTeam2_1);
            }
        });
    }

    private void getMatchdet(){
        Date.setText(HomeActivity.matchdet1.getMdate());
        Time.setText(HomeActivity.matchdet1.getMtime());
        Team1.setText(HomeActivity.matchdet1.getTeam1());
        Team2.setText(HomeActivity.matchdet1.getTeam2());

        HomeActivity.PredCollection.document(HomeActivity.Document1.getId()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot SelectedDoc = task.getResult();
                    if (SelectedDoc.getString("pred") != null) {
                        setSelectedButtons(SelectedDoc.getString("pred"));
                    }else {
                        setOpenButtons();
                    }
                }
            }
        });
    }

    private void SetButton1(Button TeamA, Button TeamB,String pred){
        TeamA.setEnabled(false);
        TeamB.setEnabled(false);
        TeamA.setBackgroundResource(R.drawable.custom_button1);
        TeamB.setBackgroundResource(R.drawable.custom_button2);

        String mcnt = HomeActivity.Document1.getId();
        int mcnt1 = Integer.parseInt(mcnt);
        Prediction prediction = new Prediction(HomeActivity.ShortTeam1_1,HomeActivity.ShortTeam2_1,pred,"",HomeActivity.matchdet1.getMdate(),0,mcnt1);
        HomeActivity.PredCollection.document(mcnt).set(prediction);
        HomeActivity.myPreds.add(prediction);
    }

    private void setOpenButtons(){
        Date.setText(HomeActivity.matchdet1.getMdate());
        Time.setText(HomeActivity.matchdet1.getMtime());
        Team1.setText(HomeActivity.matchdet1.getTeam1());
        Team2.setText(HomeActivity.matchdet1.getTeam2());
        Team1.setEnabled(true);
        Team2.setEnabled(true);

        StartTimer();
    }

    private void setSelectedButtons(String predteam){

        Date.setText(HomeActivity.matchdet1.getMdate());
        Time.setText(HomeActivity.matchdet1.getMtime());
        Team1.setText(HomeActivity.matchdet1.getTeam1());
        Team2.setText(HomeActivity.matchdet1.getTeam2());

        Team1.setEnabled(false);
        Team2.setEnabled(false);
        if(predteam.equals(HomeActivity.ShortTeam1_1)){
            Team1.setBackgroundResource(R.drawable.custom_button1);
            Team2.setBackgroundResource(R.drawable.custom_button2);
        }else if(predteam.equals(HomeActivity.ShortTeam2_1)){
            Team2.setBackgroundResource(R.drawable.custom_button1);
            Team1.setBackgroundResource(R.drawable.custom_button2);
        }
        StartTimer();
    }

    private void StartTimer(){
        String currtime = now.format(new Date());
        String []ct = currtime.split(":");
        String []et = HomeActivity.matchdet1.getMtime().split(":");
        final String mcnt = HomeActivity.Document1.getId();

        int ehh = Integer.parseInt(et[0]);
        int emm = Integer.parseInt(et[1]);

        int chh = Integer.parseInt(ct[0]);
        int cmm = Integer.parseInt(ct[1]);
        int css = Integer.parseInt(ct[2]);

        int EndMil = ((((ehh * 60) + emm) * 60) * 1000) - 1800000 ;
        int CurrMil = ((((chh * 60) + cmm) * 60) + css) * 1000;
        RemTime = EndMil - CurrMil;

        if (RemTime > 500){
            Timer.setTextColor(Color.parseColor("#5EF10B"));
            CountDownTimer countDownTimer = new CountDownTimer(RemTime, 1000) {
                @Override
                public void onTick(long l) {
                    RemTime = l;
                    updateTimer();
                }

                @Override
                public void onFinish() {
                    Timer.setText("00:00:00");
                    Timer.setTextColor(Color.parseColor("#CA0404"));
                    if (Team1.isEnabled() || Team2.isEnabled()) {
                        Team1.setEnabled(false);
                        Team2.setEnabled(false);
                        Team1.setBackgroundResource(R.drawable.custom_button2);
                        Team2.setBackgroundResource(R.drawable.custom_button2);
                        int mcnt1 = Integer.parseInt(mcnt);
                        Prediction prediction = new Prediction(HomeActivity.ShortTeam1_1, HomeActivity.ShortTeam2_1, "", "", HomeActivity.matchdet1.getMdate(), 0, mcnt1);
                        HomeActivity.PredCollection.document(mcnt).set(prediction);
                        HomeActivity.myPreds.add(prediction);
                    }
                }
            }.start();
        }else {
            Timer.setText("00:00:00");
            Timer.setTextColor(Color.parseColor("#CA0404"));
            if (Team1.isEnabled() || Team2.isEnabled()) {
                Team1.setEnabled(false);
                Team2.setEnabled(false);
                Team1.setBackgroundResource(R.drawable.custom_button2);
                Team2.setBackgroundResource(R.drawable.custom_button2);
            }
        }
    }

    private void updateTimer(){
        int a = (int)RemTime;
        int a1 =  a / 3600000;
        int a2 = (a % 3600000) / 60000;
        int a3 = ((a % 3600000) % 60000) / 1000;

        String ShowTime;
        if (a2 > 9 && a3 > 9){
            ShowTime = Integer.toString(a1) + ":" + Integer.toString(a2) + ":" + Integer.toString(a3);
        }else if (a2 < 10 && a3 < 10){
            ShowTime = Integer.toString(a1) + ":0" + Integer.toString(a2) + ":0" + Integer.toString(a3);
        }else if (a2 < 10){
            ShowTime = Integer.toString(a1) + ":0" + Integer.toString(a2) + ":" + Integer.toString(a3);
        }else{
            ShowTime = Integer.toString(a1) + ":" + Integer.toString(a2) + ":0" + Integer.toString(a3);
        }
        Timer.setText(ShowTime);
    }
}