package app.bowling.bowlingapp.bowling.core.api.services;

import rabaapp.raba.app.raba.core.api.models.loginuser.LoginRequest;
import rabaapp.raba.app.raba.core.api.models.loginuser.LoginResponse;
import rabaapp.raba.app.raba.core.api.models.registeruser.RegisterRequest;
import rabaapp.raba.app.raba.core.api.models.registeruser.RegisterResponse;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by knightbenax on 01/03/2018.
 */

public interface AuthService {

    @POST("user/register")
    Observable<Response<RegisterResponse>> registerUser(@Body RegisterRequest registerRequest);

    @POST("user/login")
    Observable<Response<LoginResponse>> loginUser(@Body LoginRequest loginRequest);

}
