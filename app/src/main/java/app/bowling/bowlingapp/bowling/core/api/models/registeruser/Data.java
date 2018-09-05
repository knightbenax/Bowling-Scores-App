
package app.bowling.bowlingapp.bowling.core.api.models.registeruser;

import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("formattedUser")
    private FormattedUser mFormattedUser;
    @SerializedName("refreshToken")
    private String mRefreshToken;
    @SerializedName("token")
    private String mToken;

    public FormattedUser getFormattedUser() {
        return mFormattedUser;
    }

    public void setFormattedUser(FormattedUser formattedUser) {
        mFormattedUser = formattedUser;
    }

    public String getRefreshToken() {
        return mRefreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        mRefreshToken = refreshToken;
    }

    public String getToken() {
        return mToken;
    }

    public void setToken(String token) {
        mToken = token;
    }

}
