package com.example.demo.src.camera.model;

import lombok.*;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCameraReq {
    private Integer poseIdx;
    private Integer scaleX;
    private Integer scaleY;
    private List<PoseList> poseList;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PoseList {
        private Integer p_order;
        private String time;
        private List<PoseData> pose;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PoseData {
        private String position;
        private float x;
        private float y;
        private float z;
        private float reliability;
    }
}

