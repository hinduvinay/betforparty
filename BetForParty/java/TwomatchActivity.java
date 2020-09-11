package com.vinay.betforparty;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TwomatchActivity extends AppCompatActivity {

    SimpleDateFormat cd = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat now = new SimpleDateFormat("HH:mm:ss");
    private long RemTime;


    private TextView Timer1,Date1,Time1,Timer2,Date2,Time2;
    private Button Team1_1,Team2_1,Team1_2,Team2_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twomatch);

        Timer1 = findViewById(R.id.timer_1);
        Date1 = findViewById(R.id.mdate_1);
        Time1 = findViewById(R.id.mtime_1);
        Team1_1 = findViewById(R.id.team1_1);
        Team2_1 = findViewById(R.id.team2_1);

        Timer2 = findViewById(R.id.timer_2);
        Date2 = findViewById(R.id.mdate_2);
        Time2 = findViewById(R.id.mtime_2);
        Team1_2 = findViewById(R.id.team1_2);
        Team2_2 = findViewById(R.id.team2_2);

        getMatchdet();

        Team1_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetButton1(Team1_1,Team2_1,HomeActivity.matchdet1,HomeActivity.Document1.getId(),HomeActivity.ShortTeam1_1,1);
            }
        });

        Team2_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetButton1(Team2_1,Team1_1,HomeActivity.matchdet1,HomeActivity.Document1.getId(),HomeActivity.ShortTeam2_1,1);
            }
        });

        Team1_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetButton1(Team1_2,Team2_2,HomeActivity.matchdet2,HomeActivity.Document2.getId(),HomeActivity.ShortTeam1_2,2);
            }
        });

        Team2_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetButton1(Team2_2,Team1_2,HomeActivity.matchdet2,HomeActivity.Document2.getId(),HomeActivity.ShortTeam2_2,2);
            }
        });
    }

    public void getMatchdet() {
        HomeActivity.PredCollection.document(HomeActivity.Document1.getId()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot SelectedDoc = task.getResult();
                    if (SelectedDoc.getString("pred") != null) {
                        setSelectedButtons(1,SelectedDoc.getString("pred"),HomeActivity.matchdet1,HomeActivity.ShortTeam1_1,HomeActivity.ShortTeam2_1);
                    }else {
                        setOpenButtons(1,HomeActivity.matchdet1);
                    }
                }
            }
        });
        HomeActivity.PredCollection.document(HomeActivity.Document2.getId()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot SelectedDoc = task.getResult();
                    if (SelectedDoc.getString("pred") != null) {
                        setSelectedButtons(2,SelectedDoc.getString("pred"),HomeActivity.matchdet2,HomeActivity.ShortTeam1_2,HomeActivity.ShortTeam2_2);
                    }else {
                        setOpenButtons(2,HomeActivity.matchdet2);
                    }
                }
            }
        });
    }

    private void SetButton1(Button TeamA, Button TeamB, Matchdet matchdet, String mcnt, String pred, int i){
        TeamA.setEnabled(false);
        TeamB.setEnabled(false);
        TeamA.setBackgroundResource(R.drawable.custom_button1);
        TeamB.setBackgroundResource(R.drawable.custom_button2);

        int mcnt1 = Integer.parseInt(mcnt);
        if (i == 1){
            Prediction prediction = new Prediction(HomeActivity.ShortTeam1_1,HomeActivity.ShortTeam2_1,pred,"",matchdet.getMdate(),0,mcnt1);
            HomeActivity.PredCollection.document(mcnt).set(prediction);
            HomeActivity.myPreds.add(prediction);
        }else if (i == 2){
            Prediction prediction = new Prediction(HomeActivity.ShortTeam1_2,HomeActivity.ShortTeam2_2,pred,"",matchdet.getMdate(),0,mcnt1);
            HomeActivity.PredCollection.document(mcnt).set(prediction);
            HomeActivity.myPreds.add(prediction);
        }
    }

    private void setOpenButtons(int mcnt,Matchdet matchdet){

        switch (mcnt){
            case 1:
                Date1.setText(matchdet.getMdate());
                Time1.setText(matchdet.getMtime());
                Team1_1.setText(matchdet.getTeam1());
                Team2_1.setText(matchdet.getTeam2());
                Team1_1.setEnabled(true);
                Team2_1.setEnabled(true);
                StartTimer(HomeActivity.Document1.getId(),matchdet,Team1_1,Team2_1,Timer1,HomeActivity.ShortTeam1_1,HomeActivity.ShortTeam2_1);
                break;

            case 2:
                Date2.setText(matchdet.getMdate());
                Time2.setText(matchdet.getMtime());
                Team1_2.setText(matchdet.getTeam1());
                Team2_2.setText(matchdet.getTeam2());
                Team1_2.setEnabled(true);
                Team2_2.setEnabled(true);
                StartTimer(HomeActivity.Document2.getId(),matchdet,Team1_2,Team2_2,Timer2,HomeActivity.ShortTeam1_2,HomeActivity.ShortTeam2_2);
        }
    }

    private void setSelectedButtons(int mcnt,String predteam, Matchdet matchdet, String ShrtA, String ShrtB){
        String TeamA = matchdet.getTeam1();
        String TeamB = matchdet.getTeam2();

        switch (mcnt){
            case 1:
                Date1.setText(matchdet.getMdate());
                Time1.setText(matchdet.getMtime());
                Team1_1.setText(TeamA);
                Team2_1.setText(TeamB);
                Team1_1.setEnabled(false);
                Team2_1.setEnabled(false);

                if(predteam.equals(ShrtA)){
                    Team1_1.setBackgroundResource(R.drawable.custom_button1);
                    Team2_1.setBackgroundResource(R.drawable.custom_button2);
                }
                if(predteam.equals(ShrtB)){
                    Team2_1.setBackgroundResource(R.drawable.custom_button1);
                    Team1_1.setBackgroundResource(R.drawable.custom_button2);
                }
                StartTimer(HomeActivity.Document1.getId(),matchdet,Team1_1,Team2_1,Timer1,HomeActivity.ShortTeam1_1,HomeActivity.ShortTeam2_1);
                break;

            case 2:
                Date2.setText(matchdet.getMdate());
                Time2.setText(matchdet.getMtime());
                Team1_2.setText(TeamA);
                Team2_2.setText(TeamB);

                Team1_2.setEnabled(false);
                Team2_2.setEnabled(false);
                if(predteam.equals(ShrtA)){
                    Team1_2.setBackgroundResource(R.drawable.custom_button1);
                    Team2_2.setBackgroundResource(R.drawable.custom_button2);
                }
                if(predteam.equals(ShrtB)){
                    Team2_2.setBackgroundResource(R.drawable.custom_button1);
                    Team1_2.setBackgroundResource(R.drawable.custom_button2);
                }
                StartTimer(HomeActivity.Document2.getId(),matchdet,Team1_2,Team2_2,Timer2,HomeActivity.ShortTeam1_2,HomeActivity.ShortTeam2_2);
                break;
        }
    }

    private void StartTimer(final String mcnt, final Matchdet matchdet, final Button TeamA, final Button TeamB, final TextView Timer, final String ShrtA, final String ShrtB){
        String currtime = now.format(new Date());
        String []ct = currtime.split(":");
        String []et = matchdet.getMtime().split(":");
        final int mcnt1 = Integer.parseInt(mcnt);

        int ehh = Integer.parseInt(et[0]);
        int emm = Integer.parseInt(et[1]);

        int chh = Integer.parseInt(ct[0]);
        int cmm = Integer.parseInt(ct[1]);
        int css = Integer.parseInt(ct[2]);

        int EndMil = ((((ehh * 60) + emm) * 60) * 1000) - 1800000 ;
        int CurrMil = ((((chh * 60) + cmm) * 60) + css) * 1000;
        RemTime = EndMil - CurrMil;

        if (RemTime > 0){
            Timer.setTextColor(Color.parseColor("#5EF10B"));
            CountDownTimer countDownTimer = new CountDownTimer(RemTime, 1000) {
                @Override
                public void onTick(long l) {
                    RemTime = l;
                    updateTimer(Timer);
                }

                @Override
                public void onFinish() {
                    Timer.setText("00:00:00");
                    Timer.setTextColor(Color.parseColor("#CA0404"));
                    if (TeamA.isEnabled() || TeamB.isEnabled()) {
                        TeamA.setEnabled(false);
                        TeamB.setEnabled(false);
                        TeamA.setBackgroundResource(R.drawable.custom_button2);
                        TeamB.setBackgroundResource(R.drawable.custom_button2);
                        Prediction prediction = new Prediction(ShrtA, ShrtB, "", "", matchdet.getMdate(), 0, mcnt1);
                        HomeActivity.PredCollection.document(mcnt).set(prediction);
                        HomeActivity.myPreds.add(prediction);
                    }
                }
            }.start();
        }else {
            Timer.setText("00:00:00");
            Timer.setTextColor(Color.parseColor("#CA0404"));
            if (TeamA.isEnabled()) {
                TeamA.setEnabled(false);
                TeamA.setBackgroundResource(R.drawable.custom_button2);
            }
            if (TeamB.isEnabled()){
                TeamB.setEnabled(false);
                TeamB.setBackgroundResource(R.drawable.custom_button2);
            }
        }
    }

    private void updateTimer(TextView Timer){
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