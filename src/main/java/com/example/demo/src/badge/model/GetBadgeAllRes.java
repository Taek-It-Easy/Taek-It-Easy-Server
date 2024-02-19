package com.example.demo.src.badge.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter // 해당 클래스에 대한 접근자 생성
@Setter // 해당 클래스에 대한 설정자 생성
@AllArgsConstructor // 해당 클래스의 변수(userIdx)를 받는 생성자를 생성
public class GetBadgeAllRes {
    private int badgeIdx;
    private String content;
}
