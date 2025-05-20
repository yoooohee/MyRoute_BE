package com.ssafy.ws.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;

import com.ssafy.ws.model.dto.Hotplace;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HotplaceDetailResponse {
    Hotplace hotplace;
    private String imageBase64;
	private boolean likedByUser;
    private boolean myPost;
	
	public String getImageBase64() {
        return imageBase64 != null ? imageBase64 :
               (hotplace.getImage() != null ? "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(hotplace.getImage()) : null);
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }
}
