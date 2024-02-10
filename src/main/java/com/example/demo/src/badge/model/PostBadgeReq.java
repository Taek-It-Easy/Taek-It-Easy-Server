package com.example.demo.src.badge.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostBadgeReq {
    private Integer userIdx;
    private Integer badgeIdx;
}
