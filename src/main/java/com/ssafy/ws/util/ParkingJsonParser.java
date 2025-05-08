package com.ssafy.ws.util;

import com.google.gson.Gson;
import com.ssafy.ws.model.dto.Parking;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

public class ParkingJsonParser {

    public static List<Parking> parse(Reader reader) throws IOException {
        Gson gson = new Gson();
        ParkingResponse response = gson.fromJson(reader, ParkingResponse.class);
        List<Parking> list = response.getRecords();
        list.forEach(Parking::convert);
        return list;
    }

    private static class ParkingResponse {
        private List<Parking> records;

        public List<Parking> getRecords() {
            return records;
        }

        public void setRecords(List<Parking> records) {
            this.records = records;
        }
    }
}
