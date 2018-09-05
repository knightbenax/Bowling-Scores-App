
package app.bowling.bowlingapp.bowling.core.api.models.fetchanswers;

import com.google.gson.annotations.SerializedName;

public class Answer {

    @SerializedName("answer")
    private String mAnswer;
    @SerializedName("question")
    private String mQuestion;

    public String getAnswer() {
        return mAnswer;
    }

    public void setAnswer(String answer) {
        mAnswer = answer;
    }

    public String getQuestion() {
        return mQuestion;
    }

    public void setQuestion(String question) {
        mQuestion = question;
    }

}
