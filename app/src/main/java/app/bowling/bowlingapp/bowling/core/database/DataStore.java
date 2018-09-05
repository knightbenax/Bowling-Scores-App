package app.bowling.bowlingapp.bowling.core.database;

import javax.inject.Inject;

import rabaapp.raba.app.raba.core.api.models.fetchanswers.AnswersRequest;
import rabaapp.raba.app.raba.core.api.models.fetchanswers.AnswersResponse;
import rabaapp.raba.app.raba.core.api.models.fetchquestions.QuestionsResponse;
import rabaapp.raba.app.raba.core.api.models.loginuser.LoginRequest;
import rabaapp.raba.app.raba.core.api.models.loginuser.LoginResponse;
import rabaapp.raba.app.raba.core.api.models.registeruser.RegisterRequest;
import rabaapp.raba.app.raba.core.api.models.registeruser.RegisterResponse;
import rabaapp.raba.app.raba.core.database.models.User;
import retrofit2.Response;
import rx.Observable;

public class DataStore {

    private OnlineStore onlineStore;
    private OfflineStore offlineStore;

    @Inject
    public DataStore(OfflineStore offlineStore, OnlineStore onlineStore){
        this.offlineStore = offlineStore;
        this.onlineStore = onlineStore;
    }

    public void saveUser(String name, String email, String phone, String token, String refreshToken, String wallet){

        offlineStore.saveUser(name, email, phone, token, refreshToken, wallet);

    }


    public void saveUser(User user){
        offlineStore.saveUser(user);
    }


    public boolean userExist(){
        return offlineStore.userExists();
    }

    public void deleteAllUsers(){
        offlineStore.deleteUsers();
    }

    public User getUser(){
        return offlineStore.getUser();
    }

    public Observable<Response<RegisterResponse>> registerUser(RegisterRequest registerRequest){
        return onlineStore.registerUser(registerRequest);
    }

    public Observable<Response<LoginResponse>> loginUser(LoginRequest loginRequest){
        return onlineStore.loginUser(loginRequest);
    }

    public Observable<Response<QuestionsResponse>> fetchQuestion(String token, String refreshToken){
        return onlineStore.fetchQuestion(token, refreshToken);
    }

    public Observable<Response<AnswersResponse>> checkAnswers(String token, String refreshToken, AnswersRequest answersRequest){
        return onlineStore.checkAnswers(token, refreshToken, answersRequest);
    }
}
