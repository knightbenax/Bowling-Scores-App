package app.bowling.bowlingapp.bowling.core.database;

import javax.inject.Inject;

import rabaapp.raba.app.raba.core.api.models.fetchanswers.AnswersRequest;
import rabaapp.raba.app.raba.core.api.models.fetchanswers.AnswersResponse;
import rabaapp.raba.app.raba.core.api.models.fetchquestions.QuestionsResponse;
import rabaapp.raba.app.raba.core.api.models.loginuser.LoginRequest;
import rabaapp.raba.app.raba.core.api.models.loginuser.LoginResponse;
import rabaapp.raba.app.raba.core.api.models.registeruser.RegisterRequest;
import rabaapp.raba.app.raba.core.api.models.registeruser.RegisterResponse;
import rabaapp.raba.app.raba.core.api.services.AuthService;
import rabaapp.raba.app.raba.core.api.services.QuestionsService;
import retrofit2.Response;
import retrofit2.Retrofit;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class OnlineStore {


    private Retrofit retrofit;
    private AuthService authService;
    private QuestionsService questionsService;

    @Inject
    public OnlineStore(Retrofit retrofit){
        this.retrofit = retrofit;

        authService = retrofit.create(AuthService.class);
        questionsService = retrofit.create(QuestionsService.class);
    }


    //Register User
    public Observable<Response<RegisterResponse>> registerUser(RegisterRequest registerRequest){
        return authService.registerUser(registerRequest).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    //Login User
    public Observable<Response<LoginResponse>> loginUser(LoginRequest loginRequest){
        return authService.loginUser(loginRequest).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    //Fetch Question
    public Observable<Response<QuestionsResponse>> fetchQuestion(String token, String refreshToken){
        return questionsService.fetchQuestion(token, refreshToken).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    //Check Answers
    public Observable<Response<AnswersResponse>> checkAnswers(String token, String refreshToken, AnswersRequest answersRequest){
        return questionsService.checkAnswers(token, refreshToken, answersRequest).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
