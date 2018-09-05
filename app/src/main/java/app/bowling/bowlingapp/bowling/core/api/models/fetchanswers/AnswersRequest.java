
package app.bowling.bowlingapp.bowling.core.api.models.fetchanswers;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AnswersRequest {

    @SerializedName("answers")
    private List<Answer> mAnswers;

    public List<Answer> getAnswers() {
        return mAnswers;
    }

    public void setAnswers(List<Answer> answers) {
        mAnswers = answers;
    }

}
