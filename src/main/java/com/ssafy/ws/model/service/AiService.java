package com.ssafy.ws.model.service;

import java.io.IOException;
import okhttp3.*;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import com.ssafy.ws.config.OpenAiConfig;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class AiService {
	private final OpenAiConfig config;

    public String generateTourDescription(String title) throws IOException {
        OkHttpClient client = new OkHttpClient();

        String prompt = "'" + title + "' 관광지에 대한 간단한 설명을 한국어로 2~3문장 작성해줘. 마지막에는 추천점수를 5점 만점을 기준으로 포함해줘. 답변도 조금 귀엽게!";

        JSONObject requestBody = new JSONObject()
            .put("model", "gpt-3.5-turbo")
            .put("messages", new org.json.JSONArray()
                .put(new JSONObject()
                    .put("role", "user")
                    .put("content", prompt)
                )
            );

        Request request = new Request.Builder()
            .url(config.getApiUrl())
            .addHeader("Authorization", "Bearer " + config.getApiKey())
            .addHeader("Content-Type", "application/json")
            .post(RequestBody.create(
                requestBody.toString(),
                MediaType.parse("application/json")))
            .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        JSONObject json = new JSONObject(responseBody);
        return json.getJSONArray("choices")
                   .getJSONObject(0)
                   .getJSONObject("message")
                   .getString("content")
                   .trim();
    }
    
    public String recommendCourse(String area, String days, String userType) throws IOException {
        OkHttpClient client = new OkHttpClient();

        // ✨ 사용자 유형까지 고려한 프롬프트 구성
        String prompt = String.format(
            "한국 %s 지역을 %s일 동안 %s 여행자가 여행할 수 있는 추천 코스를 짜줘. " +
            "아침부터 저녁까지 시간 순으로 관광지를 나열하고, 각 장소에 대한 엄청 간단한 이유도 함께 설명해줘. 답변도 조금 귀엽게! 여행자 정보에 맞게 부탁해. 그리고 보기 쉽게 정리해서 보내줘 엔터를 누른다던가",
            area, days, userType
        );

        JSONObject requestBody = new JSONObject()
            .put("model", "gpt-3.5-turbo")
            .put("messages", new org.json.JSONArray()
                .put(new JSONObject()
                    .put("role", "user")
                    .put("content", prompt)
                )
            );

        Request request = new Request.Builder()
            .url(config.getApiUrl())
            .addHeader("Authorization", "Bearer " + config.getApiKey())
            .addHeader("Content-Type", "application/json")
            .post(RequestBody.create(
                requestBody.toString(),
                MediaType.parse("application/json")))
            .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        JSONObject json = new JSONObject(responseBody);
        return json.getJSONArray("choices")
                   .getJSONObject(0)
                   .getJSONObject("message")
                   .getString("content")
                   .trim();
    }

}
