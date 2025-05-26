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
            .put("model", "gpt-4o")
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

        // 사용자 유형별 맞춤 인삿말
        String userPromptPrefix = switch (userType.toLowerCase()) {
            case "연인" -> "💑 연인과 함께하는 여행이라면 이런 일정이 어울려요!\n";
            case "가족" -> "👨‍👩‍👧‍👦 가족들과의 여행이라면 이렇게 추천드릴게요!\n";
            case "친구" -> "🧑‍🤝‍🧑 친구들과 함께라면 즐거운 추억을 만들 수 있어요!\n";
            case "혼자" -> "🧍 혼자 떠나는 여행이라면 이런 코스는 어때요?\n";
            default -> "✨ 여행자님께 딱 맞는 일정을 추천드릴게요!\n";
        };

        String prompt = String.format(
            "%s%s 지역을 %s일 동안 여행한다고 가정하고, 아침부터 저녁까지 이모지와 함께 장소 및 간단한 설명을 포함한 여행 일정을 추천해줘. 각 일정은 간결하고 보기 좋게 정리해줘. ✨ 답변 마지막에 '📌 참고: 여행 계획 게시판을 참고해보세요!' 를 붙여줘.",
            userPromptPrefix, area, days
        );

        JSONObject requestBody = new JSONObject()
            .put("model", "gpt-4o") // 또는 gpt-4o-mini
            .put("messages", new org.json.JSONArray()
                .put(new JSONObject()
                    .put("role", "system")
                    .put("content", """
                        너는 마이루트의 AI 여행 도우미야.
                        지역, 일정, 명소, 추천 경로 등 여행과 관련된 질문에만 응답해야 해.
                        여행과 관련이 전혀 없다고 판단되면 반드시 다음 문장으로만 응답해:
                        '저는 마이루트의 여행 도우미입니다! 여행과 관련된 질문만 부탁드려요 :)'
                        답변은 따뜻하고 간결하게, 여행자 유형에 따라 자연스럽게 인삿말을 포함해줘. ✨
                        이모지 활용은 자유롭게 하되 너무 과하지 않게 써줘.
                    """)
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
