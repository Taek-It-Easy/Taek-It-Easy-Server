package com.example.demo.src.camera.model;

import lombok.*;

import java.util.List;

@Getter // 해당 클래스에 대한 접근자 생성
@Setter // 해당 클래스에 대한 설정자 생성
@AllArgsConstructor // 해당 클래스의 모든 멤버 변수(userIdx, nickname, email, password)를 받는 생성자를 생성
@NoArgsConstructor
/**
 * 유저관련된 정보들을 담고 있는 클래스(유저 관련정보를 추출할 때 해당 클래스에서 Getter를 사용해서 가져온다.)
 * GetUserRes는 클라이언트한테 response줄 때 DTO고(DTO란 DB의 정보를 Service나 Controller등에 보낼때 사용하는 객체를 의미한다.)
 * User 클래스는 스프링에서 사용하는 Objec이다.(내부에서 사용하기 위한 객체라고 보면 된다.)
 */

public class GetCameraaReq {
    private List<Nose> nose;
    private List<LeftEye> leftEye;
    private List<RightEye> rightEye;
    private List<LeftEar> leftEar;
    private List<RightEar> rightEar;
    private List<LeftShoulder> leftShoulder;
    private List<RightShoulder> rightShoulder;
    private List<LeftElbow> leftElbow;
    private List<RightElbow> rightElbow;
    private List<LeftWrist> leftWrist;
    private List<RightWrist> rightWrist;
    private List<LeftHip> leftHip;
    private List<RightHip> rightHip;
    private List<LeftKnee> leftKnee;
    private List<RightKnee> rightKnee;
    private List<LeftAnkle> leftAnkle;
    private List<RightAnkle> rightAnkle;
}

class Nose{
    private int type;
    private float x;
    private float y;
    private float z;
    private float likelihood;
}
class LeftEye{
    private int type;
    private float x;
    private float y;
    private float z;
    private float likelihood;
}

class RightEye{
    private int type;
    private float x;
    private float y;
    private float z;
    private float likelihood;
}

class LeftEar{
    private int type;
    private float x;
    private float y;
    private float z;
    private float likelihood;
}
class RightEar{
    private int type;
    private float x;
    private float y;
    private float z;
    private float likelihood;
}
class LeftShoulder{
    private int type;
    private float x;
    private float y;
    private float z;
    private float likelihood;
}
class RightShoulder{
    private int type;
    private float x;
    private float y;
    private float z;
    private float likelihood;
}
class LeftElbow{
    private int type;
    private float x;
    private float y;
    private float z;
    private float likelihood;
}
class RightElbow{
    private int type;
    private float x;
    private float y;
    private float z;
    private float likelihood;
}
class LeftWrist{
    private int type;
    private float x;
    private float y;
    private float z;
    private float likelihood;
}
class RightWrist{
    private int type;
    private float x;
    private float y;
    private float z;
    private float likelihood;
}
class LeftHip{
    private int type;
    private float x;
    private float y;
    private float z;
    private float likelihood;
}
class RightHip{
    private int type;
    private float x;
    private float y;
    private float z;
    private float likelihood;
}
class LeftKnee{
    private int type;
    private float x;
    private float y;
    private float z;
    private float likelihood;
}
class RightKnee{
    private int type;
    private float x;
    private float y;
    private float z;
    private float likelihood;
}
class LeftAnkle{
    private int type;
    private float x;
    private float y;
    private float z;
    private float likelihood;
}
class RightAnkle{
    private int type;
    private float x;
    private float y;
    private float z;
    private float likelihood;
}