package com.example.iqlevelgame.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iqlevelgame.R;
import com.example.iqlevelgame.databinding.ActivityQuizeBinding;
import com.example.iqlevelgame.model.Qustions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Random;

import static android.os.Build.VERSION_CODES.Q;

public class QuizeActivity extends AppCompatActivity {
    ActivityQuizeBinding binding;

    ArrayList<Qustions> questions;
    Qustions question;
    int index=0;
    CountDownTimer timer;
    FirebaseFirestore database;

    int correctAnswer = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuizeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();


        questions=new ArrayList<>();
      final   String catId=getIntent().getStringExtra("catId");

        Random random=new Random();
      final   int rand=random.nextInt(10);

        database=FirebaseFirestore.getInstance();
        database.collection("catogries")
                .document(catId)
                .collection("questions")
                .whereGreaterThanOrEqualTo("index",rand)
                .orderBy("index")
                .limit(5).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (queryDocumentSnapshots.getDocuments().size() < 5){
                    database=FirebaseFirestore.getInstance();
                    database.collection("catogries")
                            .document(catId)
                            .collection("questions")
                            .whereLessThanOrEqualTo("index",rand)
                            .orderBy("index")
                            .limit(5).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                for (DocumentSnapshot snapshot : queryDocumentSnapshots){
                                    Qustions question=snapshot.toObject(Qustions.class);
                                    questions.add(question);


                            }
                            resetTimer();
                            setNextQuestion();
                        }
                    });

                }else{
                    for (DocumentSnapshot snapshot : queryDocumentSnapshots){
                        Qustions question=snapshot.toObject(Qustions.class);
                        questions.add(question);
                    }
                    resetTimer();
                    setNextQuestion();
                }
            }
        });







    }
    void resetTimer(){
        timer = new CountDownTimer(30000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                binding.timer.setText(String.valueOf(millisUntilFinished/1000));
            }

            @Override
            public void onFinish() {

            }
        };
    }
    void showAnswer(){
        if (question.getAnswer().equals(binding.option1.getText().toString())){
            binding.option1.setBackground(getResources().getDrawable(R.drawable.right_option));}
        else if (question.getAnswer().equals(binding.option2.getText().toString())){
                binding.option2.setBackground(getResources().getDrawable(R.drawable.right_option));}

        else if (question.getAnswer().equals(binding.option3.getText().toString())){
            binding.option3.setBackground(getResources().getDrawable(R.drawable.right_option));}
        else if (question.getAnswer().equals(binding.option4.getText().toString())){
            binding.option4.setBackground(getResources().getDrawable(R.drawable.right_option));}
    }

    void setNextQuestion() {
        resetTimer();
        if (timer !=null)
            timer.cancel();
        timer.start();
        if (index < questions.size()){
            binding.quistionCounter.setText(String.format("%d/%d",(index+1),questions.size()));
            question=questions.get(index);
            binding.qustionItems.setText(question.getQuestion());
            binding.option1.setText(question.getOption1());
            binding.option2.setText(question.getOption2());
            binding.option3.setText(question.getOption3());
            binding.option4.setText(question.getOption4());
        }
    }

    void reset(){
        binding.option1.setBackground(getResources().getDrawable(R.drawable.option_box));
        binding.option2.setBackground(getResources().getDrawable(R.drawable.option_box));
        binding.option3.setBackground(getResources().getDrawable(R.drawable.option_box));
        binding.option4.setBackground(getResources().getDrawable(R.drawable.option_box));
    }
    void checkAnswer(TextView textView){
        String selectedAnswer = textView.getText().toString();
        if (selectedAnswer.equals(question.getAnswer())){
            correctAnswer++;
            textView.setBackground(getResources().getDrawable(R.drawable.right_option));
        }
        else {
            showAnswer();
            textView.setBackground(getResources().getDrawable(R.drawable.wrong_option));
        }
    }

    public void onClick(View view){
        switch (view.getId()) {
            case R.id.option_1:
            case R.id.option_2:
            case R.id.option_3:
            case R.id.option_4:
                if (timer !=null)
                timer.cancel();
                TextView selected = (TextView) view;
                checkAnswer(selected);

                break;

            case R.id.nextBtn:
                reset();
                if (index <= questions.size()) {
                    index++;
                    setNextQuestion();
                }else {
                    Intent intent=new Intent(QuizeActivity.this,ResultActivity.class);
                    intent.putExtra("correct",correctAnswer);
                    intent.putExtra("total",questions.size());


                    startActivity(intent);

                   // Toast.makeText(this, "Quiz finished", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}