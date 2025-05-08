package com.ssafy.ws.model.service;

import java.util.List;

import com.ssafy.ws.model.dto.Hotplace;

public interface HotplaceService {

    // 핫플 등록
    void createHotplace(Hotplace hotplace);

    // 핫플 목록 조회 (페이징)
    List<Hotplace> getHotplaces(int page, int size);

    // 핫플 총 개수 조회
    int getHotplaceCount();

    // 핫플 단건 조회
    Hotplace getHotplaceById(int hotplaceId);

    // 핫플 수정
    void updateHotplace(Hotplace hotplace);

    // 핫플 삭제
    void deleteHotplace(int hotplaceId);
}
