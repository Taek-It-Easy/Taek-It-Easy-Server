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
    private Integer chapterIdx;
    private List<PoseData> pose;

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


//@Getter
//@Setter
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//public class GetCameraReq {
//    private List<PoseData> pose;
//
//    public String toString() {
//        return "GetCameraReq [pose=" + pose + "]";
//    }
//
//    public static class PoseData {
//        private String position;
//        private float x;
//        private float y;
//        private float z;
//
//        public PoseData(){
//
//        }
//         public PoseData(float x, float y, float z, String position) {
//                this.position = position;
//                this.x = x;
//                this.y = y;
//                this.z = z;
//         }
//         public String toString() {
//            return "[x=" + x + ", y=" + y + " z = " + z + " , position = " + position + "]";
//
//        }
//    }
//
//
//
//}
//public class GetCameraReq {
//    private List<Pose> pose;
//
//    public String toString() {
//        return "GetCameraReq [pose=" + pose + "]";
//    }
//}
//
//class Pose {
//    private String position;
//    private float x;
//    private float y;
//    private float z;
//
//    public Pose() {
//    }
//
//    public Pose(float x, float y, float z, String position) {
//        this.position = position;
//        this.x = x;
//        this.y = y;
//        this.z = z;
//    }
//
//    public String toString() {
//        return "[x=" + x + ", y=" + y + " z = " + z + " , position = " + position + "]";
//
//    }
//}
