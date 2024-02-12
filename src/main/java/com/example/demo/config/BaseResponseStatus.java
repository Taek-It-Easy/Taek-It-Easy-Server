package com.example.demo.config;

import lombok.Getter;

/**
 * 에러 코드 관리
 */
@Getter
public enum BaseResponseStatus {
    /**
     * 1000 : 요청 성공
     */
    SUCCESS(true, 1000, "요청에 성공하였습니다."),


    /**
     * 2000 : Request 오류
     */
    // Common
    REQUEST_ERROR(false, 2000, "입력값을 확인해주세요."),
    EMPTY_JWT(false, 2001, "JWT를 입력해주세요."),
    INVALID_JWT(false, 2002, "유효하지 않은 JWT입니다."),
    INVALID_USER_JWT(false,2003,"권한이 없는 유저의 접근입니다."),

    // users
    USERS_EMPTY_USER_ID(false, 2010, "유저 아이디 값을 확인해주세요."),

    // [POST] /users
    POST_USERS_EMPTY_DEVICENUM(false, 2015, "디바이스 정보를 입력해주세요."),
    POST_USERS_EXISTS_DEVICENUM(false,2016,"중복된 디바이스 정보입니다."),


    //[GET] /cosine
    GET_CAMERA_COSINE_EMPTY_POSE(false, 2100, "pose를 입력해주세요."),
    GET_CAMERA_COSINE_COUNT_POSE(false, 2101, "pose는 17개의 점이 필요합니다."),

    //[POST] /badge
    POST_USERS_EMPTY_USERIDX(false, 2200, "사용자 정보를 입력해주세요."),
    POST_USERS_EMPTY_BADGEIDX(false,2201,"뱃지 정보를 입력해주세요."),
    DUPLICATED_BADGE(false, 2202, "중복된 뱃지입니다."),

    //[GET] /video
    GET_VIDEO_EMPTY_POSEIDX(false, 2301, "원하는 자세 정보를 입력해주세요."),

    /**
     * 3000 : Response 오류
     */
    // Common
    RESPONSE_ERROR(false, 3000, "값을 불러오는데 실패하였습니다."),

    // [POST] /users
    DUPLICATED_EMAIL(false, 3013, "중복된 이메일입니다."),
    FAILED_TO_LOGIN(false,3014,"없는 아이디거나 비밀번호가 틀렸습니다."),



    /**
     * 4000 : Database, Server 오류
     */
    DATABASE_ERROR(false, 4000, "데이터베이스 연결에 실패하였습니다."),
    SERVER_ERROR(false, 4001, "서버와의 연결에 실패하였습니다."),

    //[PATCH] /users/pose/{userIdx}/{poseIdx}
    MODIFY_FAIL_USERPOSE(false,4010,"사용자 pose 학습 내용 수정 실패"),
    INSERT_FAIL_USERPOSE(false,4011,"사용자 pose 학습 내용 입력 실패");


    // 5000 : 필요시 만들어서 쓰세요
    // 6000 : 필요시 만들어서 쓰세요


    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) { //BaseResponseStatus 에서 각 해당하는 코드를 생성자로 맵핑
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
