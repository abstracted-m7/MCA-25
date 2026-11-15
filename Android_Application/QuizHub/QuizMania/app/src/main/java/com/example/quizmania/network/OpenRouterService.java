package com.example.quizmania.network;

import com.google.gson.JsonObject;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface OpenRouterService {

    @Headers({
            "Content-Type: application/json"
    })
    @POST("api/v1/chat/completions")
    Single<Response<JsonObject>> generateQuiz(
            @Body JsonObject requestBody
    );
}
