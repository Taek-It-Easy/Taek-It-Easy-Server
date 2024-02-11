package com.example.demo.src.video;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.video.model.GetPoseVideoRes;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

import static com.example.demo.config.BaseResponseStatus.*;


@Api(tags = "video API")
@RestController

@RequestMapping("/app/video")
public class VideoController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private final VideoProvider videoProvider;
    @Autowired
    private final VideoService videoService;

    public VideoController(VideoProvider videoProvider, VideoService videoService) {
        this.videoProvider = videoProvider;
        this.videoService = videoService;
    }

    /**
     * 기본동작 video API
     * [GET] /video/{poseIdx}
     */
    @ResponseBody
    @GetMapping("/{poseIdx}") // (GET) 127.0.0.1:9000/app/video/{poseIdx}
    // GET 방식의 요청을 매핑하기 위한 어노테이션
    public BaseResponse<GetPoseVideoRes> getPoseVideo(@PathVariable("poseIdx") int poseIdx) {
        // @PathVariable RESTful(URL)에서 명시된 파라미터({})를 받는 어노테이션, 이 경우 poseIdx를 받아옴.
        //  null값 or 공백값이 들어가는 경우는 적용하지 말 것
        if (Objects.equals(poseIdx, null)) {
            return new BaseResponse<>(GET_VIDEO_EMPTY_POSEIDX);
        }
        // Get PoseVideo
        try {
            GetPoseVideoRes getPoseVideoRes = videoProvider.getPoseVideo(poseIdx);
            return new BaseResponse<>(getPoseVideoRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }

}
