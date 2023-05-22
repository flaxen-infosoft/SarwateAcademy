package com.flaxeninfosoft.sarwateAcademy.views.userFragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.flaxeninfosoft.sarwateAcademy.R;
import com.flaxeninfosoft.sarwateAcademy.databinding.FragmentStartQuizBinding;

public class StartQuizFragment extends Fragment {

    FragmentStartQuizBinding binding;

    public StartQuizFragment() {
    }
    String questions[] = {
            "Which method can be defined only once in a program?",
            "Which of these is not a bitwise operator?",
            "Which keyword is used by method to refer to the object that invoked it?",
            "Which of these keywords is used to define interfaces in Java?",
            "Which of these access specifiers can be used for an interface?",
            "Which of the following is correct way of importing an entire package ‘pkg’?",
            "What is the return type of Constructors?",
            "Which of the following package stores all the standard java classes?",
            "Which of these method of class String is used to compare two String objects for their equality?",
            "An expression involving byte, int, & literal numbers is promoted to which of these?"
    };
    String answers[] = {"main method","<=","this","interface","public","import pkg.*","None of the mentioned","java","equals()","int"};
    String opt[] = {
            "finalize method","main method","static method","private method",
            "&","&=","|=","<=",
            "import","this","catch","abstract",
            "Interface","interface","intf","Intf",
            "public","protected","private","All of the mentioned",
            "Import pkg.","import pkg.*","Import pkg.*","import pkg.",
            "int","float","void","None of the mentioned",
            "lang","java","util","java.packages",
            "equals()","Equals()","isequal()","Isequal()",
            "int","long","byte","float"
    };
    int flag=0;
    private  CountDownTimer mycount;
    public static int marks=0,correct=0,wrong=0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_start_quiz, container, false);

        binding.tvque.setText(questions[flag]);
        binding.radioButton.setText(opt[0]);
        binding.radioButton2.setText(opt[1]);
        binding.radioButton3.setText(opt[2]);
        binding.radioButton4.setText(opt[3]);

        binding.button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(binding.answersgrp.getCheckedRadioButtonId()==-1)
                {
                    Toast.makeText(getContext(), "Please select one choice", Toast.LENGTH_SHORT).show();
                    return;
                }
                RadioButton uans = (RadioButton) getView().findViewById(binding.answersgrp.getCheckedRadioButtonId());
                String ansText = uans.getText().toString();
                if(ansText.equals(answers[flag])) {
                    correct++;
                }
                else {
                    wrong++;
                }



                flag++;

                if (binding.textView4 != null)
                    binding.textView4.setText(""+correct);

                if(flag<questions.length)
                {
                    binding.tvque.setText(questions[flag]);
                    binding.radioButton.setText(opt[flag*4]);
                    binding.radioButton2.setText(opt[flag*4 +1]);
                    binding.radioButton3.setText(opt[flag*4 +2]);
                    binding.radioButton4.setText(opt[flag*4 +3]);
                }
                else
                {
                    marks=correct;
                    Intent in = new Intent(getActivity(), ResultActivity.class);
                    startActivity(in);
                }
                binding.answersgrp.clearCheck();
            }
        });

        mycount = new CountDownTimer(90000, 1000) {

            @Override
            public void onFinish() {
                timeUp();
            }

            @Override
            public void onTick(long millisUntilFinished) {
                binding.tx.setText("Time left: "
                        + String.valueOf(millisUntilFinished / 1000));
            }

        }.start();
        return binding.getRoot();
    }

    public void timeUp() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Times up!")
                .setMessage("Quiz Finish")
                .setCancelable(false)
                .setNeutralButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                marks=correct;
                                Intent in = new Intent(getActivity(), ResultActivity.class);
                                startActivity(in);
                                mycount.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
        mycount.cancel();
    }



}