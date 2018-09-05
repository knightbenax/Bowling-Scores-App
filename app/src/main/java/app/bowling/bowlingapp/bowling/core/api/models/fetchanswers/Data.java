
package app.bowling.bowlingapp.bowling.core.api.models.fetchanswers;

import com.google.gson.annotations.SerializedName;


public class Data {

    @SerializedName("accountBalance")
    private Long mAccountBalance;
    @SerializedName("accountName")
    private String mAccountName;
    @SerializedName("email")
    private String mEmail;
    @SerializedName("name")
    private String mName;
    @SerializedName("phoneNumber")
    private String mPhoneNumber;
    @SerializedName("_id")
    private String m_id;

    public Long getAccountBalance() {
        return mAccountBalance;
    }

    public void setAccountBalance(Long accountBalance) {
        mAccountBalance = accountBalance;
    }

    public String getAccountName() {
        return mAccountName;
    }

    public void setAccountName(String accountName) {
        mAccountName = accountName;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        mPhoneNumber = phoneNumber;
    }

    public String get_id() {
        return m_id;
    }

    public void set_id(String _id) {
        m_id = _id;
    }

}
