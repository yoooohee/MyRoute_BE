package com.ssafy.ws.model.dto;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Parking {

	@SerializedName("주차장관리번호")
	private String prkplceNo;

	@SerializedName("주차장명")
	private String prkplceNm;

	@SerializedName("소재지도로명주소")
	private String rdnmadr;

	@SerializedName("주차구획수")
	private String prkcmprtStr;

	@SerializedName("위도")
	private String latitudeStr;

	@SerializedName("경도")
	private String longitudeStr;

	private int prkcmprt; // 주차구획수
	private double latitude; // 위도
	private double longitude; // 경도
	public double distance; // 거리

	public void convert() {
		try {
			if (latitudeStr != null && !latitudeStr.isEmpty()) {
				latitude = Double.parseDouble(latitudeStr);
			}
			if (longitudeStr != null && !longitudeStr.isEmpty()) {
				longitude = Double.parseDouble(longitudeStr);
			}
			if (prkcmprtStr != null && !prkcmprtStr.isEmpty()) {
				prkcmprt = Integer.parseInt(prkcmprtStr);
			}
		} catch (NumberFormatException e) {
			System.err.println("변환 실패: " + e.getMessage());
		}
	}

}
