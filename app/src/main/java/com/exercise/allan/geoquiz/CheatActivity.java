package com.exercise.allan.geoquiz;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {


    private static final String EXTRA_ANSWER = "com.exercise.allan.geoquiz.answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN = "com.exercise.allan.geoquiz.answer_shown";
    private static final String ANSWER_TEXT = "com.exercise.allan.geoquiz.answer_text";
    private static final String CHECKER = "com.exercise.allan.geoquiz.checker";

    private boolean mCheatChecker;
    private boolean mAnswerIsTrue;
    private Button mShowAnswer;
    private TextView mAnswerTextView, mApiLevelTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mApiLevelTextView = (TextView) findViewById(R.id.api_level_textview);
        mAnswerTextView = (TextView) findViewById(R.id.textView_answer);

        if(savedInstanceState != null){
            mAnswerIsTrue = savedInstanceState.getBoolean(ANSWER_TEXT);
            mCheatChecker = savedInstanceState.getBoolean(CHECKER);

            if(mCheatChecker==true){
                mAnswerTextView.setText(String.valueOf(mAnswerIsTrue));
                setAnswerShowResult(true);
            }
        }


        if(getIntent().hasExtra(EXTRA_ANSWER)){

            mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER, false);
            //setAnswerShowResult(false);
        }


        mShowAnswer = (Button) findViewById(R.id.show_answer_button);
        mShowAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* if(mAnswerIsTrue){
                    mAnswerTextView.setText(R.string.true_button);
                }else {
                    mAnswerTextView.setText(R.string.false_button);
                }*/

                mCheatChecker = true;
                mAnswerTextView.setText(String.valueOf(mAnswerIsTrue));


                //set the setAnswerShown when the button click
                setAnswerShowResult(true);

                animateCheatActivity();



            }
        });
    }



    //animate when the mShowAnswr button clicked
    private void animateCheatActivity() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int cx = mShowAnswer.getWidth();
            int cy = mShowAnswer.getHeight();
            float radius = mShowAnswer.getWidth();
            Animator anim = ViewAnimationUtils.createCircularReveal(mShowAnswer, cx, cy, radius, 0);
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    mAnswerTextView.setVisibility(View.VISIBLE);
                    mShowAnswer.setVisibility(View.INVISIBLE);
                }
            });
            anim.start();

        }else {
            mAnswerTextView.setVisibility(View.VISIBLE);
            mShowAnswer.setVisibility(View.INVISIBLE);
        }


        mApiLevelTextView.setText(Build.VERSION.CODENAME);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String s = mAnswerTextView.getText().toString();

        if(s != null){
            outState.putBoolean(ANSWER_TEXT, mAnswerIsTrue);
            outState.putBoolean(CHECKER,mCheatChecker);
        }
    }


    //called in QuizActivity
    public static Intent newIntent(Context context, boolean cheatAnswer){
        Intent intent = new Intent(context, CheatActivity.class);
        intent.putExtra(EXTRA_ANSWER, cheatAnswer);
        return intent;
    }

    public static boolean wasAnswerShown(Intent result){
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN,false);
    }


    //called in mShowAnswer button
    private void setAnswerShowResult(boolean isAnswerShown){
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK,data);
    }
}
