package com.example.demo.src.user;

import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.user.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import static com.example.demo.config.BaseResponseStatus.*;

@Api(tags = "user API")
@RestController
@RequestMapping("/app/users")
public class UserController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final UserProvider userProvider;

    @Autowired
    private final UserService userService;

    public UserController(UserProvider userProvider, UserService userService) {
        this.userProvider = userProvider;
        this.userService = userService;
    }

    /**
     * 회원가입 API
     * [POST] /users
     */
    @ResponseBody
    @PostMapping("/sign-up")
    public BaseResponse<PostUserRes> createUser(@RequestBody PostUserReq postUserReq){
        if(postUserReq.getDeviceNum().isEmpty()){
            System.out.println("d1");
            return new BaseResponse<>(POST_USERS_EMPTY_DEVICENUM);

        }
        try {
            PostUserRes postUserRes = userService.createUser(postUserReq);
            return new BaseResponse<>(postUserRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 사용자 진도 조회 API
     * [GET] /users/contents/{userIdx}
     */
    //Path Variable
    @ResponseBody
    @GetMapping("/contents/{userIdx}") // (GET) 127.0.0.1:9000/app/users/contents/{userIdx}
    // GET 방식의 요청을 매핑하기 위한 어노테이션
    public BaseResponse<GetUserContentsRes> getUserContents(@PathVariable("userIdx") int userIdx) {
        try {
            GetUserContentsRes getUserPoseRes = userProvider.getUserContents(userIdx);
            return new BaseResponse<>(getUserPoseRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    /**
     * 사용자 pose 진도 업데이트 API
     * [PATCH] /users/pose/:userIdx/:poseIdx
     */
    @ResponseBody
    @PatchMapping("/pose/{userIdx}/{poseIdx}")
    public BaseResponse<String> modifyUserPose(@PathVariable("userIdx") int userIdx, @PathVariable("poseIdx") int poseIdx) {
        try {
            userService.modifyUserPose(userIdx, poseIdx);

            String result = "회원정보가 수정되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

}
