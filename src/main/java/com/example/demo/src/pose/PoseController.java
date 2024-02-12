package com.example.demo.src.pose;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.pose.model.GetPoseRes;
import com.example.demo.src.video.VideoProvider;
import com.example.demo.src.video.VideoService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "pose API")
@RestController

@RequestMapping("/app/poses")
public class PoseController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private final PoseProvider poseProvider;
    @Autowired
    private final PoseService poseService;

    public PoseController(PoseProvider poseProvider, PoseService poseService) {
        this.poseProvider = poseProvider;
        this.poseService = poseService;
    }

    /**
     * 모든 pose 조회 API
     * [GET] /poses
     */
    //Query String
    @ResponseBody
    @GetMapping("") // (GET) 127.0.0.1:9000/app/poses
    // GET 방식의 요청을 매핑하기 위한 어노테이션
    public BaseResponse<List<GetPoseRes>> getPoses() {
        try {
                List<GetPoseRes> getPoseRes = poseProvider.getPoses();
                return new BaseResponse<>(getPoseRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }


}
