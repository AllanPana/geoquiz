package com.exercise.allan.geoquiz;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {



    private static final String TAG = "allan";
    private static final String KEY_INDEX = "index";
    private static final int REQUEST_CODE_CHEAT = 0;
    private static final String BOOLEAN_IS_CHEAT = "boolean_is_cheat";
    private Button mTrueButton;
    private Button mFalseButton;
    private Button mCheatButton;
    private TextView mQuestionTextview;
    private TextView mQuestionTextviewHeader;
    private ImageButton  mNextButton;
    private ImageButton mPreviousButton;


    private int mCurrentIndex = 0;
    private boolean mIsCheater;
    private static boolean isViewed;

    private Question [] mQuestions = new Question[]{
            new Question(true,R.string.question_ocean,false),
            new Question(false,R.string.question_mideast,false),
            new Question(false,R.string.question_africa,false),
            new Question(true,R.string.question_america,false),
            new Question(true,R.string.question_asia,false),
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "---------------------------onCreate called");
        setContentView(R.layout.activity_quiz);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(savedInstanceState != null){
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX);
            mIsCheater = savedInstanceState.getBoolean(BOOLEAN_IS_CHEAT);
            mQuestions[mCurrentIndex].setAnswerViewed(mIsCheater);


        }

        Log.d(TAG, "---------------------------onCreate called" + mCurrentIndex);

        setUpFab();
        initViews();
        updateQuestion();
    }

    private void initViews() {
        final int question = mQuestions[mCurrentIndex].getTextRestId();

        mQuestionTextviewHeader = (TextView) findViewById(R.id.textView_question_header);
        mQuestionTextviewHeader.setText("Question " + (mCurrentIndex + 1));
        mQuestionTextview = (TextView) findViewById(R.id.textView_question);
        mQuestionTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex+1) % mQuestions.length;
                updateQuestion();
            }
        });



        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(QuizActivity.this, R.string.correct_toast, Toast.LENGTH_SHORT).show();
                checkAnswer(true);
            }
        });


        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(QuizActivity.this, R.string.incorrect_toast, Toast.LENGTH_SHORT).show();
                checkAnswer(false);
            }
        });

        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // this is the simplified code to go to next question
                //dividend / divisor = quotient
                // in modulo if the divisor is greater than dividend the answer is is the value of divisor as modulo
                //    modulo of 1 % 5 = 1
                // (0 + 1) % 5 = 0
                mCurrentIndex = (mCurrentIndex + 1) % mQuestions.length;
                mIsCheater = mQuestions[mCurrentIndex].isAnswerViewed();

                //this code is the same as code above to go ot next question
              /*  mCurrentIndex++;
                if(mCurrentIndex >= mQuestions.length){
                    mCurrentIndex = 0;
                }*/
                updateQuestion();
            }
        });


        mPreviousButton = (ImageButton) findViewById(R.id.previous_button);
        mPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*if(mCurrentIndex <= 0){
                    mCurrentIndex = mQuestions.length ;
                }
                mCurrentIndex--;*/


                mCurrentIndex = (mCurrentIndex <= 0) ? mQuestions.length - 1 : --mCurrentIndex;
                mIsCheater = mQuestions[mCurrentIndex].isAnswerViewed();
                updateQuestion();
            }
        });

        mCheatButton = (Button) findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean cheatAnswer = mQuestions[mCurrentIndex].isAnswerTrue();
                //Intent intent = new Intent(QuizActivity.this, CheatActivity.class);
                Intent intent = CheatActivity.newIntent(QuizActivity.this, cheatAnswer);
                startActivityForResult(intent, REQUEST_CODE_CHEAT);
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //RESULT_CODE_OK = operation succeeded
        if (resultCode != Activity.RESULT_OK){
            return;
        }

        //request_code_cancelled
        if(resultCode == REQUEST_CODE_CHEAT){
            if(data == null){
                return;
            }
        }
        mIsCheater = CheatActivity.wasAnswerShown(data);
    }


    private void updateQuestion(){

        int question = mQuestions[mCurrentIndex].getTextRestId();
        mQuestionTextview.setText(question);
        mQuestionTextviewHeader.setText("Question "+ (mCurrentIndex+1));
    }

    private void setUpFab(){
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(QuizActivity.this,"test FAB",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void checkAnswer(boolean userPressedTrue){
        boolean answerIsTrue = mQuestions[mCurrentIndex].isAnswerTrue();
        mQuestions[mCurrentIndex].setAnswerViewed(mIsCheater);
        isViewed = mQuestions[mCurrentIndex].isAnswerViewed();
        int messageResId = 0;

        if(mIsCheater || isViewed){
            messageResId = R.string.judgement_toast;

        }else{
            if(userPressedTrue == answerIsTrue){
                messageResId = R.string.correct_toast;

            }else {
                messageResId = R.string.incorrect_toast;
            }
        }



        Toast.makeText(QuizActivity.this, messageResId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_INDEX, mCurrentIndex);
        outState.putBoolean(BOOLEAN_IS_CHEAT,mIsCheater);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }





   /* @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "---------------------------onStart called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "---------------------------onPause called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "---------------------------onResume called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "---------------------------onStop called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "---------------------------onDestoy called");
    }*/
}
