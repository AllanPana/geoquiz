package com.exercise.allan.geoquiz;

/**
 * Created by allan on 19/11/2015.
 */
public class Question {

    private int mTextRestId;
    private boolean mAnswerTrue;
    private boolean mAnswerViewed;

    public Question(boolean mAnswerTrue, int mTextRestId, boolean mAnswerViewed) {
        this.mAnswerTrue = mAnswerTrue;
        this.mTextRestId = mTextRestId;
        this.mAnswerViewed = mAnswerViewed;
    }



    public int getTextRestId() {
        return mTextRestId;
    }

    public void setTextRestId(int textRestId) {
        mTextRestId = textRestId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }

    public boolean isAnswerViewed() {
        return mAnswerViewed;
    }

    public void setAnswerViewed(boolean answerViewed) {
        mAnswerViewed = answerViewed;
    }
}
