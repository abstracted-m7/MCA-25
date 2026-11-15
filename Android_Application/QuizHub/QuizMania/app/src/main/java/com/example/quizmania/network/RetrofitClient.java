package com.example.quizmania.network;

import com.example.quizmania.BuildConfig;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL =
            "https://openrouter.ai/";

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {

        if (retrofit == null) {

            Interceptor authInterceptor =
                    new Interceptor() {

                        @Override
                        public Response intercept(
                                Chain chain
                        ) throws IOException {

                            Request originalRequest =
                                    chain.request();

                            Request newRequest =
                                    originalRequest
                                            .newBuilder()
                                            .header(
                                                    "Authorization",
                                                    "Bearer " +
                                                            BuildConfig.OPENROUTER_API_KEY
                                            )
                                            .header(
                                                    "Content-Type",
                                                    "application/json"
                                            )
                                            .header(
                                                    "HTTP-Referer",
                                                    "https://quizmania.app"
                                            )
                                            .header(
                                                    "X-Title",
                                                    "QuizMania"
                                            )
                                            .build();

                            return chain.proceed(
                                    newRequest
                            );
                        }
                    };

            OkHttpClient client =
                    new OkHttpClient.Builder()

                            .addInterceptor(
                                    authInterceptor
                            )

                            .connectTimeout(
                                    30,
                                    TimeUnit.SECONDS
                            )

                            .readTimeout(
                                    120,
                                    TimeUnit.SECONDS
                            )

                            .writeTimeout(
                                    30,
                                    TimeUnit.SECONDS
                            )

                            .build();

            retrofit =
                    new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .client(client)
                            .addConverterFactory(
                                    GsonConverterFactory.create()
                            )
                            .addCallAdapterFactory(
                                    RxJava3CallAdapterFactory.create()
                            )
                            .build();
        }

        return retrofit;
    }

    public static OpenRouterService getOpenRouterService() {

        return getClient()
                .create(OpenRouterService.class);
    }
}