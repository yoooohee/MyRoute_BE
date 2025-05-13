package com.ssafy.ws.model.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

import com.ssafy.ws.model.dto.Place;

@Getter
@Setter
public class PlanSaveRequest {
    private String title;
    private int days;
    private int budget;
    private String sido;
    private List<Place> places;
}
