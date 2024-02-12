package com.example.demo.src.user;

import com.example.demo.src.user.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class UserDao {
    private JdbcTemplate jdbcTemplate;
    @Autowired //readme 참고
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 회원가입
    public int createUser(PostUserReq postUserReq) {
        String createUserQuery = "insert into User (userAge, deviceNum) VALUES (?,?)";
        Object[] createUserParams = new Object[]{postUserReq.getUserAge(), postUserReq.getDeviceNum()};
        this.jdbcTemplate.update(createUserQuery, createUserParams);

        String lastInserIdQuery = "select last_insert_id()"; // 가장 마지막에 생성된 id값 가져오기.
        return this.jdbcTemplate.queryForObject(lastInserIdQuery, int.class); // 해당 쿼리문의 결과 마지막으로 삽인된 유저의 userIdx번호를 반환.
    }

    // 디바이스 고유정보 확인
    public int checkDeviceNum(String deviceNum) {
        String checkDeviceNumQuery = "select exists(select deviceNum from User where deviceNum = ?)"; // User Table에 해당 deviceNum 값을 갖는 유저 정보가 존재하는가?
        String checkDeviceNumParams = deviceNum;
        return this.jdbcTemplate.queryForObject(checkDeviceNumQuery,
                int.class,
                checkDeviceNumParams); // -> 쿼리문의 결과(존재하지 않음(False,0),존재함(True, 1))를 int형(0,1)으로 반환
    }

    public int checkUserPose(int userIdx) {
        String checkUserPoseQuery = "select exists(select userIdx from UserContentsList where userIdx = ?);"; // User Table에 해당 deviceNum 값을 갖는 유저 정보가 존재하는가?
        int checkUserPoseParams = userIdx;
        return this.jdbcTemplate.queryForObject(checkUserPoseQuery,
                int.class,
                checkUserPoseParams); // -> 쿼리문의 결과(존재하지 않음(False,0),존재함(True, 1))를 int형(0,1)으로 반환
    }

    // 사용자 pose 학습 진도 업데이트
    public int modifyUserPose(int userIdx, int poseIdx) {
        String modifyUserPoseQuery = "update UserContentsList set poseIdx = ? where userIdx = ?;";
        Object[] modifyUserPoseParams = new Object[]{poseIdx, userIdx};
        return this.jdbcTemplate.update(modifyUserPoseQuery, modifyUserPoseParams); // 대응시켜 매핑시켜 쿼리 요청(생성했으면 1, 실패했으면 0)
    }

    public int insertUserPose(int userIdx, int poseIdx) {
        String insertUserPoseQuery = "INSERT INTO UserContentsList (poseIdx, userIdx) VALUES (?, ?);";
        Object[] insertUserPoseParams = new Object[]{poseIdx, userIdx};
        return this.jdbcTemplate.update(insertUserPoseQuery, insertUserPoseParams); // 대응시켜 매핑시켜 쿼리 요청(생성했으면 1, 실패했으면 0)
    }

    public GetUserContentsRes getUserContents(int userIdx){
        String getUserPoseQuery = " select userIdx, poseIdx, poomsaeIdx from UserContentsList where userIdx =?;";
        int getUserPoseParam = userIdx;
        return this.jdbcTemplate.queryForObject(getUserPoseQuery,
                (rs, rowNum) -> new GetUserContentsRes(
                        rs.getInt("userIdx"),
                        rs.getInt("poseIdx"),
                        rs.getInt("poomsaeIdx")),
                getUserPoseParam);

    }

}
