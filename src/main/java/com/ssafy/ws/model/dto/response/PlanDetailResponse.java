package com.ssafy.ws.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import com.ssafy.ws.model.dto.Place;
import com.ssafy.ws.model.dto.Plan;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlanDetailResponse {
    private Plan plan;
    private List<Place> places;
}
