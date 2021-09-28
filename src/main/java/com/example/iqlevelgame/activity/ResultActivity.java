package com.example.iqlevelgame.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.example.iqlevelgame.R;
import com.example.iqlevelgame.databinding.ActivityResultBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

public class ResultActivity extends AppCompatActivity {
    ActivityResultBinding binding;
    int POINTS = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        int correctAnswers=getIntent().getIntExtra("correct",0);
        int totalQuestios=getIntent().getIntExtra("total",0);

        int points = correctAnswers * POINTS;

        binding.score.setText(String.format("%d/%d",correctAnswers,totalQuestios));
        binding.earnCoins.setText(String.valueOf(points));

        FirebaseFirestore database=FirebaseFirestore.getInstance();

        database.collection("users")
                .document(FirebaseAuth.getInstance().getUid())
                .update("coins", FieldValue.increment(points));


        binding.restartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ResultActivity.this,QuizeActivity.class));
                finish();
            }
        });

    }
}