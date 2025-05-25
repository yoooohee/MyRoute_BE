package com.ssafy.ws.model.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {
    private int notificationId;
    private String memberId;
    private String type;
    private String content;
    private String url;
    private boolean isRead;
    private LocalDateTime createdAt;
}
