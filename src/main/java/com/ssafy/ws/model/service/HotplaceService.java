package com.ssafy.ws.model.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ssafy.ws.model.dto.Att;
import com.ssafy.ws.model.dto.Hotplace;
import com.ssafy.ws.model.dto.response.HotplaceDetailResponse;
import com.ssafy.ws.model.dto.response.HotplacePost;

public interface HotplaceService {

    List<Att> findAllAttractions();
    void createPost(HotplacePost post);
    List<Hotplace> getAllPosts();
    HotplaceDetailResponse getHotplaceById(int hotplaceId);
}
