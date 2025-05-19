package com.ssafy.ws.model.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;

@Data
public class HotplaceDetailResponse {
    private int id;
    private String title;
    private String attractionName;
    private double starPoint;
    private String memberId;
    private String content;
    private byte[] image;
    private String imageBase64;
    private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
	public String getImageBase64() {
        return imageBase64 != null ? imageBase64 :
               (image != null ? "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(image) : null);
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }
}
