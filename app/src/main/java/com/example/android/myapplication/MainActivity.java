package com.example.android.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    TextInputEditText email, password;
    Button login;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       email = findViewById(R.id.email);
       password = findViewById(R.id.password);
       login = findViewById(R.id.login);

       login.setOnClickListener(new View.OnClickListener(){
           @Override
            public void onClick (View v){
               String user_email = email.getText().toString();
               String user_password = password.getText().toString();
               Log.d("email",user_email);
               Log.d("password",user_password);
               if (TextUtils.isEmpty(user_email) || TextUtils.isEmpty(user_password)){
                   Toast.makeText(MainActivity.this, "Fields can not be empty!", Toast.LENGTH_SHORT).show();
               }
               else{
                   auth.getInstance().signInWithEmailAndPassword(user_email,user_password)
                           .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                               @Override
                               public void onComplete(@NonNull Task<AuthResult> task) {
                                   if (task.isSuccessful()){
                                       Toast.makeText(MainActivity.this, "Login success!", Toast.LENGTH_SHORT).show();
                                       Intent intent = new Intent(MainActivity.this, Chat.class);
                                       startActivity(intent);
//                                        finish();
                                   }
                                   else {
                                       Toast.makeText(MainActivity.this, "Login failed!", Toast.LENGTH_SHORT).show();
                                   }
                               }
                           });
               }

            }
           });
    }


}
