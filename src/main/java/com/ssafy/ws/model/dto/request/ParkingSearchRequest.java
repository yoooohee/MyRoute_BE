package com.ssafy.ws.model.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParkingSearchRequest {
    private double lat;
    private double lon;
}
