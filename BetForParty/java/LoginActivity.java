package com.vinay.betforparty;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText Email, Password;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Email = findViewById(R.id.lemail);
        Password = findViewById(R.id.lpassword);
        Button login = findViewById(R.id.btllogin);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user!= null){
            MainActivity.fa.finish();
            finish();
            startActivity(new Intent(LoginActivity.this,HomeActivity.class));
        }

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() != null) {
            login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                if (cm.getActiveNetworkInfo() != null) {
                validate();
                }else {
                    Toast.makeText(LoginActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
                }
            }
            });
        }else {
            Toast.makeText(this, "Network Error", Toast.LENGTH_SHORT).show();
        }
    }

    private void validate(){

        String UserName = Email.getText().toString().trim();
        String PassWord = Password.getText().toString().trim();

        if (!TextUtils.isEmpty(UserName) && !TextUtils.isEmpty(PassWord)){
            firebaseAuth.signInWithEmailAndPassword(UserName,PassWord).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                        MainActivity.fa.finish();
                        finish();
                        startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                    }
                    else{
                        Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else{
            Toast.makeText(LoginActivity.this, "Enter all the details", Toast.LENGTH_SHORT).show();
        }
    }
}