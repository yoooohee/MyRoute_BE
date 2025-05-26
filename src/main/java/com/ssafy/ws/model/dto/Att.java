package com.ssafy.ws.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Att {
    private String addr1; //주소1
    private String addr2; //주소2
    private int area_code; //시도코드
    private int content_id; //콘텐츠번호
    private int content_type_id; //콘텐츠타입
    private String first_image1; //이미지경로1
    private String first_image2; //이미지경로2
    private String homepage; //홈페이지
    private double latitude; //위도
    private double longitude; //경도
    private int map_level; //줌레벨
    private int no; //명소코드
    private String overview; //설명
    private int si_gun_gu_code; //구군코드
    private String tel; //전화번호
    private String title; //명소이름

	private double avgRating;
    private String content_type_name; //콘텐츠타입이름
    private String sido_name; //시도이름
    private String gugun_name; //구이름
}
