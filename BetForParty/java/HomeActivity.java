package com.vinay.betforparty;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HomeActivity extends AppCompatActivity {

    SimpleDateFormat cd = new SimpleDateFormat("yyyy-MM-dd");
    private String currdate = cd.format(new Date());

    private Button logout;
    private Button Prediction;
    private Button MyallPred;
    private Button Contribution;
    private Button MyfriendPred;
    private TextView Current_User;

    public static FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static CollectionReference RefUserdet = FirebaseFirestore.getInstance().collection("Userdet");
    public static CollectionReference RefMatchdet = FirebaseFirestore.getInstance().collection("Matchdet");
    public static CollectionReference RefSynonym = FirebaseFirestore.getInstance().collection("Synonyms");
    public static CollectionReference PredCollection;

    public static DocumentSnapshot Document1, Document2;
    public static Matchdet matchdet1, matchdet2;

    public static ArrayList<Prediction> myPreds = new ArrayList<>();
    public static ArrayList<Contribution_List> contribution_lists = new ArrayList<>();
    public static ArrayList<Prediction_List> prediction_lists = new ArrayList<>();
    public static String emailid = "Email", Username = "User", ShortTeam1_1 = "", ShortTeam2_1 = "", ShortTeam1_2 = "", ShortTeam2_2 = "";
    public static int MyTotAmt, MatchCnt = 0, validatecnt = 0;
    private Boolean noMatch = true,QUnsuccess=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        logout = findViewById(R.id.btllogout);
        Prediction = findViewById(R.id.btnprediction);
        Current_User = findViewById(R.id.currentuser);
        MyallPred = findViewById(R.id.btnmypred);
        Contribution = findViewById(R.id.btncontibution);
        MyfriendPred = findViewById(R.id.btnmyfriendpred);

        emailid = FirebaseAuth.getInstance().getCurrentUser().getEmail().toLowerCase();
        PredCollection = db.collection(emailid);

        retrieveInfo();

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() != null) {
            if (myPreds.size() < 1) {
                populatemypred();
            }
            if (contribution_lists.size() < 1) {
                populatecontribution();
            }
            gettodaysmatch();
        } else {
            Toast.makeText(this, "Network Error", Toast.LENGTH_SHORT).show();
        }

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteInfo();
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(HomeActivity.this, MainActivity.class));
            }
        });

        Prediction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (noMatch) {
                    Toast.makeText(HomeActivity.this, "No Match Today", Toast.LENGTH_SHORT).show();
                } else if (MatchCnt == 1) {
                    startActivity(new Intent(HomeActivity.this, OnematchActivity.class));
                } else if (MatchCnt == 2) {
                    startActivity(new Intent(HomeActivity.this, TwomatchActivity.class));
                }
            }
        });

        MyallPred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, MypredictionActivity.class));
            }
        });

        Contribution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, CollectionActivity.class));
            }
        });

        MyfriendPred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (noMatch) {
                    Toast.makeText(HomeActivity.this, "No Match Today", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(HomeActivity.this, MyfriendActivity.class));
                }
            }
        });
    }

    private void getcurrentusername() {
        RefUserdet.document(emailid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    Username = documentSnapshot.getString("Name");
                    if (Username != null) {
                        Current_User.setText("Hi " + Username);
                        saveInfo();
                    } else {
                        Toast.makeText(HomeActivity.this, "Please ask Vinay to add your details", Toast.LENGTH_SHORT).show();
                        Username = "User";
                    }
                } else {
                    Toast.makeText(HomeActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void populatemypred() {
        Query query = PredCollection.orderBy("mcnt");
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Prediction myPred = document.toObject(Prediction.class);
                        myPreds.add(myPred);
                        MyTotAmt = MyTotAmt + myPred.getAmt();
                    }
                } else {
                    Toast.makeText(HomeActivity.this, "Query Unsuccessful", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void gettodaysmatch() {
        Query query = RefMatchdet.whereEqualTo("Mdate", currdate);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    if(task.getResult().size() > 0){
                        MatchCnt = task.getResult().size();
                        Document1 = task.getResult().getDocuments().get(task.getResult().size() - MatchCnt);
                        matchdet1 = Document1.toObject(Matchdet.class);
                        RefSynonym.document(matchdet1.getTeam1()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                ShortTeam1_1 = task.getResult().getString("ShortName");
                            }
                        });
                        RefSynonym.document(matchdet1.getTeam2()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                ShortTeam2_1 = task.getResult().getString("ShortName");
                            }
                        });
                        if (MatchCnt == 2) {
                            Document2 = task.getResult().getDocuments().get(task.getResult().size() - 1);
                            matchdet2 = Document2.toObject(Matchdet.class);
                            RefSynonym.document(matchdet2.getTeam1()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    ShortTeam1_2 = task.getResult().getString("ShortName");
                                }
                           });
                            RefSynonym.document(matchdet2.getTeam2()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    ShortTeam2_2 = task.getResult().getString("ShortName");
                                }
                            });
                        }
                        validatemacth();
                    }else{
                        noMatch = true;
                        Toast.makeText(HomeActivity.this, "No Match Today", Toast.LENGTH_SHORT).show();
                        Contribution.setEnabled(true);
                        MyallPred.setEnabled(true);
                    }
                }
            }
        });
    }

    private void populatecontribution() {
        RefUserdet.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot doc : task.getResult()) {
                        calculateamt(doc.getString("Name"), doc.getId());
                    }
                }
            }
        });
    }

    private void calculateamt(final String name, String email) {
        db.collection(email).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                int amt = 0;
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot doc : task.getResult()) {
                        Prediction m1 = doc.toObject(Prediction.class);
                        amt = amt + m1.getAmt();
                    }
                    Contribution_List contribution_list = new Contribution_List();
                    contribution_list.setName(name);
                    contribution_list.setAmt(amt);
                    contribution_lists.add(contribution_list);
                }
            }
        });
    }

    public void validatemacth() {
        final Handler handler = new Handler();
        if (MatchCnt == 0) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    validatecnt = validatecnt + 1;
                    if (validatecnt < 4) {
                        validatemacth();
                    } else {
                        noMatch = true;
                        Toast.makeText(HomeActivity.this, "No Match Today", Toast.LENGTH_SHORT).show();
                        Contribution.setEnabled(true);
                        MyallPred.setEnabled(true);
                    }
                }
            }, 1000);
        }else if (QUnsuccess){
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    QUnsuccess = false;
                    validatemacth();
                }
            }, 1000);
        } else {
            noMatch = false;
            if (MatchCnt == 1) {
                validatecnt = 1;
                loopgetmyfriend1();
            } else {
                validatecnt = 1;
                loopgetmyfriend2();
            }
        }
    }

    public void loopgetmyfriend1() {
        final Handler handler = new Handler();
        if (ShortTeam1_1 == "" || ShortTeam2_1 == "") {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    validatecnt = validatecnt + 1;
                    loopgetmyfriend1();
                }
            }, 500);
        } else {
            getmyfriendpred();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    enablebuttons();
                }
            }, validatecnt * 1500);

        }
    }

    public void loopgetmyfriend2() {
        final Handler handler = new Handler();
        if (ShortTeam1_1 == "" || ShortTeam2_1 == "" || ShortTeam1_2 == "" || ShortTeam2_2 == "") {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    validatecnt = validatecnt + 1;
                    loopgetmyfriend2();
                }
            }, 500);
        } else {
            getmyfriendpred();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    enablebuttons();
                }
            }, validatecnt * 1500);
        }
    }

    private void enablebuttons() {
        Prediction.setEnabled(true);
        MyallPred.setEnabled(true);
        Contribution.setEnabled(true);
        MyfriendPred.setEnabled(true);
    }

    public void getmyfriendpred() {
        prediction_lists = new ArrayList<>();
        RefUserdet.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (final QueryDocumentSnapshot doc : task.getResult()) {
                        getfriendpred(doc.getId(), doc.getString("Name"), Document1, 1);
                        if (MatchCnt == 2) {
                            getfriendpred(doc.getId(), doc.getString("Name"), Document2, 2);
                        }
                        if (QUnsuccess){
                            break;
                        }
                    }
                    QUnsuccess = false;

                }
            }
        });
    }

    public void getfriendpred(String collection, final String name, DocumentSnapshot tmpdoc, final int cnt) {
        db.collection(collection).document(tmpdoc.getId()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot preddoc = task.getResult();
                    if (preddoc.getString("pred") != null) {
                        getmyfriend(name, preddoc.getString("pred"), cnt);
                    } else {
                        getmyfriend(name, "-", cnt);
                    }
                } else {
                    QUnsuccess = true;
                    validatemacth();
                }
            }
        });
    }

    public void getmyfriend(String name, String pred, int cnt) {
        Prediction_List prediction_list = new Prediction_List();
        prediction_list.setPred(pred);
        prediction_list.setName(name);
        if (cnt == 1) {
            prediction_list.setTeamA(ShortTeam1_1);
            prediction_list.setTeamB(ShortTeam2_1);
            prediction_list.setMcnt(Document1.getId());
            prediction_lists.add(prediction_list);
        } else if (cnt == 2) {
            prediction_list.setTeamA(ShortTeam1_2);
            prediction_list.setTeamB(ShortTeam2_2);
            prediction_list.setMcnt(Document2.getId());
            prediction_lists.add(prediction_list);
        }
    }

    public class Contribution_List implements Comparable<Contribution_List> {
        String Name;
        int Amt;

        public Contribution_List() {
        }

        public Contribution_List(String name, int amt) {
            Name = name;
            Amt = amt;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public int getAmt() {
            return Amt;
        }

        public void setAmt(int amt) {
            Amt = amt;
        }

        @Override
        public int compareTo(Contribution_List o) {
            int ret;
            if (this.getAmt() > o.getAmt()){
                ret=1;
            }else if (this.getAmt() < o.getAmt()){
                ret=-1;
            }else{
                ret = Name.compareTo(o.Name);
            }
            return ret;
        }
    }

    public class Prediction_List implements Comparable<Prediction_List> {
        String Name;
        String Mcnt;
        String TeamA;
        String TeamB;
        String Pred;

        public Prediction_List() {
        }

        public Prediction_List(String name, String mcnt, String teamA, String teamB, String pred) {
            Name = name;
            Mcnt = mcnt;
            TeamA = teamA;
            TeamB = teamB;
            Pred = pred;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public String getMcnt() {
            return Mcnt;
        }

        public void setMcnt(String mcnt) {
            Mcnt = mcnt;
        }

        public String getTeamA() {
            return TeamA;
        }

        public void setTeamA(String teamA) {
            TeamA = teamA;
        }

        public String getTeamB() {
            return TeamB;
        }

        public void setTeamB(String teamB) {
            TeamB = teamB;
        }

        public String getPred() {
            return Pred;
        }

        public void setPred(String pred) {
            Pred = pred;
        }

        @Override
        public int compareTo(Prediction_List o) {
            int compare = Mcnt.compareTo(o.Mcnt);
            if (compare == 0) {
                compare = Name.compareTo(o.Name);
            }
            return compare;
        }
    }

    public void saveInfo() {
        SharedPreferences sharedPreferences = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Email", emailid);
        editor.putString("Name", Username);
        editor.apply();
    }

    public void retrieveInfo() {
        SharedPreferences sharedPreferences = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        Username = sharedPreferences.getString("Name", "");
        emailid = sharedPreferences.getString("Email", "");
        if (emailid == "Email" || emailid.isEmpty() || Username == "User" || Username.isEmpty()) {
            emailid = FirebaseAuth.getInstance().getCurrentUser().getEmail().toLowerCase();
            PredCollection = db.collection(emailid);
            getcurrentusername();
        } else {
            PredCollection = db.collection(emailid);
            Current_User.setText("Hi " + Username);
        }
    }

    public void deleteInfo() {
        SharedPreferences sharedPreferences = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

}