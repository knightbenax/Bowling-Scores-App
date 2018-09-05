
package app.bowling.bowlingapp.bowling.core.api.models.fetchquestions;

import com.google.gson.annotations.SerializedName;


public class Data {

    @SerializedName("A")
    private String mA;
    @SerializedName("B")
    private String mB;
    @SerializedName("C")
    private String mC;
    @SerializedName("Category")
    private String mCategory;
    @SerializedName("correctAnswer")
    private String mCorrectAnswer;
    @SerializedName("createdAt")
    private String mCreatedAt;
    @SerializedName("D")
    private String mD;
    @SerializedName("Question")
    private String mQuestion;
    @SerializedName("updatedAt")
    private String mUpdatedAt;
    @SerializedName("__v")
    private Long m_V;
    @SerializedName("_id")
    private String m_id;

    public String getA() {
        return mA;
    }

    public void setA(String a) {
        mA = a;
    }

    public String getB() {
        return mB;
    }

    public void setB(String b) {
        mB = b;
    }

    public String getC() {
        return mC;
    }

    public void setC(String c) {
        mC = c;
    }

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String category) {
        mCategory = category;
    }

    public String getCorrectAnswer() {
        return mCorrectAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        mCorrectAnswer = correctAnswer;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
    }

    public String getD() {
        return mD;
    }

    public void setD(String d) {
        mD = d;
    }

    public String getQuestion() {
        return mQuestion;
    }

    public void setQuestion(String question) {
        mQuestion = question;
    }

    public String getUpdatedAt() {
        return mUpdatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        mUpdatedAt = updatedAt;
    }

    public Long get_V() {
        return m_V;
    }

    public void set_V(Long _V) {
        m_V = _V;
    }

    public String get_id() {
        return m_id;
    }

    public void set_id(String _id) {
        m_id = _id;
    }

}
