package com.example.demo.src.camera;

import com.example.demo.src.camera.CameraProvider;
import com.example.demo.src.camera.CameraService;
import com.example.demo.utils.JwtService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.camera.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.isRegexEmail;

@Api(tags = "camera API")
@RestController

@RequestMapping("/app/camera")

public class CameraController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private final CameraProvider cameraProvider;
    @Autowired
    private final CameraService cameraService;

    public CameraController(CameraProvider cameraProvider, CameraService cameraService) {
        this.cameraProvider = cameraProvider;
        this.cameraService = cameraService;
    }

    /**
     * Cosine Similarity API
     * [POST] /camera/cosine
     */
    // Body
    @ResponseBody
    @PostMapping("/cosine")
    // GET method에서는 body 사용이 불가능하다는 오류가 발생함 -> post로 변경
    public BaseResponse<GetCameraRes> findSimularity(@RequestBody GetCameraReq getCameraReq) {
        //System.out.println(getCameraReq.getChapterIdx());
        int chapterIdx = getCameraReq.getPoseIdx();
        if (getCameraReq == null || getCameraReq.getPoseList() == null || getCameraReq.getPoseList().isEmpty()) {
            // Handle validation error - empty pose
            return new BaseResponse<>(GET_CAMERA_COSINE_EMPTY_POSE);
        }
        List<GetCameraReq.PoseList> poseDataList = getCameraReq.getPoseList();

        // poseList 내부의 pose 객체가 17개 존재하는지 확인
        for (GetCameraReq.PoseList poseList : poseDataList) {
            List<GetCameraReq.PoseData> GetPoseDataList = poseList.getPose();
            if (GetPoseDataList.size() != 17) {
                return new BaseResponse<>(GET_CAMERA_COSINE_COUNT_POSE);
            }
        }
        try {
            GetCameraRes getCameraRes = cameraProvider.cosineSimilarity(getCameraReq);
            return new BaseResponse<>(getCameraRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }




}
