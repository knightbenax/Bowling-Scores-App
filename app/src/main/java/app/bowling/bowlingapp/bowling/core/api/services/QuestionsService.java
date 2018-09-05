package app.bowling.bowlingapp.bowling.core.api.services;

import rabaapp.raba.app.raba.core.api.models.fetchanswers.AnswersRequest;
import rabaapp.raba.app.raba.core.api.models.fetchanswers.AnswersResponse;
import rabaapp.raba.app.raba.core.api.models.fetchquestions.QuestionsResponse;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by knightbenax on 01/03/2018.
 */

public interface QuestionsService {

    @GET("question/fetch")
    Observable<Response<QuestionsResponse>> fetchQuestion(@Header("Authorization") String token, @Header("x-refresh-token") String refreshToken);

    @POST("question/verifyanswer")
    Observable<Response<AnswersResponse>> checkAnswers(@Header("Authorization") String token, @Header("x-refresh-token") String refreshToken, @Body AnswersRequest answersRequest);

}
