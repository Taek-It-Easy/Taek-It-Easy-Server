package com.example.demo.src.user.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetUserContentsRes {
    private int userIdx;
    private int poseIdx;
    private int poomsaeIdx;
}
