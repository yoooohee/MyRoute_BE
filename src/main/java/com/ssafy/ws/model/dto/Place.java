package com.ssafy.ws.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Place {
	private Integer placeId;
	private Integer planId;
	private Integer attractionNo;
	private Integer visitOrder;
    private double latitude; //위도
    private double longitude; //경도
    private String placeName;
    private String first_image1;
    private String addr1;
}
