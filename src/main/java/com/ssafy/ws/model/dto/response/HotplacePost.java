package com.ssafy.ws.model.dto.response;

import lombok.Data;

@Data
public class HotplacePost {
    private int id;
    private String memberId;
    private int attractionNo;
    private String title;
    private double rating;
    private String content;
    private String imageUrl;
    private byte[] image;
}
