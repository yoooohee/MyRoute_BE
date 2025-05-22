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

        JSONObject requestBody = new JSONObject()
            .put("model", "gpt-3.5-turbo")
            .put("messages", new org.json.JSONArray()
                .put(new JSONObject()
                    .put("role", "system")
                    .put("content", 
                    	    "너는 마이루트의 AI 여행 도우미야. 사용자로부터 받은 질문이나 장소명이 관광지, 명소, 음식점, 숙박, 일정, 여행, 쇼핑, 문화시설, 코스, 레포츠와 관련 있다고 판단되면 간단하고 친절하게 설명해줘. \\n\\n답변 마지막에는 자연스럽게 다음 문장을 붙여줘:\\n📌 참고: 장소의 후기는 핫플 게시판에서 확인할 수 있습니다! 또는 여행 계획 게시판에서 실제 사용자들의 일정을 참고해보세요 :). 단, 정말로 여행과 관련이 전혀 없다고 판단되는 경우에는 반드시 다음 문장으로만 응답해: '저는 마이루트의 여행 도우미입니다! 여행과 관련된 질문만 부탁드려요 :)'\n\n답변은 간결하고 따뜻하게. ✨\n답변 중 관련 포인트에는 이모지도 활용해줘 (예: 🏞️, 🍽️, 🏨 등)"
                    	)
                )
                .put(new JSONObject()
                    .put("role", "user")
                    .put("content", title)
                )
            );

        Request request = new Request.Builder()
            .url(config.getApiUrl())
            .addHeader("Authorization", "Bearer " + config.getApiKey())
            .addHeader("Content-Type", "application/json")
            .post(RequestBody.create(requestBody.toString(), MediaType.parse("application/json")))
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

        String prompt = String.format(
			"%s 지역을 %s일 동안 %s 여행자가 여행한다고 가정하고, 아침부터 저녁까지 이모지와 함께 장소 및 간단한 설명을 포함한 여행 일정을 추천해줘. 각 일정은 간결하고 보기 좋게 정리해줘. ✨ 답변 마지막에 '📌 참고: 여행 계획 게시판을 참고해보세요!' 를 붙여줘.",
		    area, days, userType
        );

        JSONObject requestBody = new JSONObject()
            .put("model", "gpt-3.5-turbo")
            .put("messages", new org.json.JSONArray()
                .put(new JSONObject()
                    .put("role", "system")
                    .put("content", "너는 마이루트의 AI 여행 도우미야. 지역, 일정, 명소, 추천 경로 등 여행과 관련된 질문에만 응답해야 해. 한번 찾아보고 여행과 관련이 아예 0%인 주제는 무조건 다음 문장으로만 응답해: '저는 마이루트의 여행 도우미입니다! 여행과 관련된 질문만 부탁드려요 :)'")
                )
                .put(new JSONObject()
                    .put("role", "user")
                    .put("content", prompt)
                )
            );

        Request request = new Request.Builder()
            .url(config.getApiUrl())
            .addHeader("Authorization", "Bearer " + config.getApiKey())
            .addHeader("Content-Type", "application/json")
            .post(RequestBody.create(requestBody.toString(), MediaType.parse("application/json")))
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
