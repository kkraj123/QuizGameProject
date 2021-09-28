package com.example.iqlevelgame.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.iqlevelgame.R;
import com.example.iqlevelgame.databinding.ActivitySignupBinding;
import com.example.iqlevelgame.model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

public class SignupActivity extends AppCompatActivity {

    ActivitySignupBinding binding;
    FirebaseAuth auth;
    FirebaseFirestore databse;
    ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dialog=new ProgressDialog(this);
        dialog.setMessage("Please wait.......");

        auth=FirebaseAuth.getInstance();
        databse=FirebaseFirestore.getInstance();

        binding.submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                String name,emai,password,refercode;
                name=binding.nameBox.getText().toString();
                emai=binding.emailBox.getText().toString();
                password=binding.passwordBox.getText().toString();
                refercode=binding.refercodeBox.getText().toString();

                Users user=new Users(name,emai,password,refercode);

                auth.createUserWithEmailAndPassword(emai,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                     if (task.isSuccessful()){
                         String uid=task.getResult().getUser().getUid();
                         databse.collection("users")
                                 .document(uid)
                                 .set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                             @Override
                             public void onComplete(@NonNull Task<Void> task) {
                                 if (task.isSuccessful()){
                                     dialog.dismiss();
                                     startActivity(new Intent(SignupActivity.this,MainActivity.class));
                                 }
                                 else {
                                     dialog.dismiss();
                                     Toast.makeText(SignupActivity.this, ""+task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                 }
                             }
                         });
                         dialog.dismiss();
                         Toast.makeText(SignupActivity.this, "successfully sign up", Toast.LENGTH_SHORT).show();
                     }
                     else {
                         dialog.dismiss();
                         Toast.makeText(SignupActivity.this, ""+task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                     }
                    }
                });


            }
        });

        binding.accountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this,LoginActivity.class));
            }
        });
    }
}