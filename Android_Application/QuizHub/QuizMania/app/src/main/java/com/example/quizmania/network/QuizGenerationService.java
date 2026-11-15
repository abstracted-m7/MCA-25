package com.example.quizmania.network;

import com.google.gson.JsonObject;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface QuizGenerationService {
    @POST
    Single<JsonObject> generateQuiz(@Url String url, @Body JsonObject requestBody);
}
