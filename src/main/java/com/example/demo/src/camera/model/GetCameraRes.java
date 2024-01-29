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
public class GetCameraRes {
    private List<cameraResult> cameraResult;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class cameraResult{
        private Integer poseSeq;
        private Integer pOder;
        private Double CDmax;
        private Double CDavg;
    }

}
