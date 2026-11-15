package com.example.quizmania.repository;

import com.example.quizmania.network.OpenRouterService;
import com.example.quizmania.network.RetrofitClient;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;

public class AiQuizRepository {

    private final OpenRouterService service;

    public AiQuizRepository() {
        service = RetrofitClient.getOpenRouterService();
    }

    public Single<Response<JsonObject>> generateQuiz(
            String topic,
            int numQuestions,
            String model) {

        // Default number of questions
        if (numQuestions < 1) {
            numQuestions = 5;
        }

        // Maximum number of questions
        if (numQuestions > 50) {
            numQuestions = 50;
        }

        // Create request payload
        Map<String, Object> payload =
                new HashMap<>();

        payload.put(
                "model",
                model
        );

        // Create messages
        List<Map<String, String>> messages =
                new ArrayList<>();

        // ==============================
        // SYSTEM MESSAGE
        // ==============================

        Map<String, String> system =
                new HashMap<>();

        system.put(
                "role",
                "system"
        );

        system.put(
                "content",

                "You are an expert quiz generator. "

                        + "Generate a multiple choice quiz. "

                        + "Return ONLY valid JSON. "

                        + "Do not use Markdown. "

                        + "Do not use code fences. "

                        + "Do not write any text before or after the JSON. "

                        + "The JSON must follow exactly this structure: "

                        + "{"
                        + "\"title\":\"Quiz title\","
                        + "\"description\":\"Short quiz description\","
                        + "\"questions\":["
                        + "{"
                        + "\"id\":\"q1\","
                        + "\"text\":\"Question text\","
                        + "\"options\":["
                        + "\"Option A\","
                        + "\"Option B\","
                        + "\"Option C\","
                        + "\"Option D\""
                        + "],"
                        + "\"correctOptionIndex\":0,"
                        + "\"explanation\":\"Explain why the correct answer is correct.\""
                        + "}"
                        + "]"
                        + "}"

                        + "There must be exactly four options "
                        + "for every question. "

                        + "correctOptionIndex must be 0, 1, 2, or 3. "

                        + "Every question must have an explanation. "

                        + "Generate exactly the number of questions "
                        + "requested by the user."
        );

        messages.add(system);

        // ==============================
        // USER MESSAGE
        // ==============================

        Map<String, String> user =
                new HashMap<>();

        user.put(
                "role",
                "user"
        );

        user.put(
                "content",

                "Create a quiz about "
                        + topic
                        + " with exactly "
                        + numQuestions
                        + " questions."
        );

        messages.add(user);

        payload.put(
                "messages",
                messages
        );

        // Convert Java object → JSON
        JsonObject body =
                new Gson()
                        .toJsonTree(payload)
                        .getAsJsonObject();

        // Send request
        return service.generateQuiz(body);
    }

    /**
     * Extracts the quiz JSON from
     * OpenRouter's response.
     */
    public static JsonObject extractQuizJson(
            JsonObject response) {

        if (response == null) {

            throw new IllegalArgumentException(
                    "Empty response from AI"
            );
        }

        // Check choices
        if (!response.has("choices")) {

            throw new IllegalArgumentException(
                    "AI response does not contain choices"
            );
        }

        JsonArray choices =
                response.getAsJsonArray(
                        "choices"
                );

        if (choices.size() == 0) {

            throw new IllegalArgumentException(
                    "AI returned no choices"
            );
        }

        // First choice
        JsonObject firstChoice =
                choices
                        .get(0)
                        .getAsJsonObject();

        if (!firstChoice.has("message")) {

            throw new IllegalArgumentException(
                    "AI response does not contain message"
            );
        }

        JsonObject message =
                firstChoice
                        .getAsJsonObject(
                                "message"
                        );

        if (!message.has("content")) {

            throw new IllegalArgumentException(
                    "AI response does not contain content"
            );
        }

        JsonElement contentElement =
                message.get("content");

        if (contentElement == null
                || contentElement.isJsonNull()) {

            throw new IllegalArgumentException(
                    "AI returned empty content"
            );
        }

        String content =
                contentElement.getAsString();

        if (content == null
                || content.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "AI returned empty quiz content"
            );
        }

        content =
                content.trim();

        // =================================
        // Remove Markdown code fences
        // =================================

        if (content.startsWith("```json")) {

            content =
                    content.substring(7)
                            .trim();

        } else if (content.startsWith("```")) {

            content =
                    content.substring(3)
                            .trim();
        }

        if (content.endsWith("```")) {

            content =
                    content.substring(
                                    0,
                                    content.length() - 3
                            )
                            .trim();
        }

        // =================================
        // Convert generated JSON string
        // to JsonObject
        // =================================

        JsonElement element =
                new Gson()
                        .fromJson(
                                content,
                                JsonElement.class
                        );

        if (element == null
                || !element.isJsonObject()) {

            throw new IllegalArgumentException(
                    "AI did not return a valid JSON object"
            );
        }

        return element.getAsJsonObject();
    }
}