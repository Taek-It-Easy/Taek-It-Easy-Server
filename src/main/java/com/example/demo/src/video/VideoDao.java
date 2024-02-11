package com.example.demo.src.video;
import com.example.demo.src.badge.model.PostBadgeReq;
import com.example.demo.src.video.model.GetPoseVideoRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class VideoDao {
    private JdbcTemplate jdbcTemplate;
    @Autowired //readme 참고
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 해당 userIdx를 갖는 유저조회
    public GetPoseVideoRes getPoseVideo(int poseIdx) {
        String getPoseVideoQuery = "select b.poseIdx, poseName, url from Pose LEFT JOIN PoseVideo b ON b.poseIdx = Pose.poseIdx where b.poseIdx = ?;"; // 해당 userIdx를 만족하는 유저를 조회하는 쿼리문
        int getPoseVideoParam = poseIdx;
        return this.jdbcTemplate.queryForObject(getPoseVideoQuery,
                (rs, rowNum) -> new GetPoseVideoRes(
                        rs.getInt("poseIdx"),
                        rs.getString("poseName"),
                        rs.getString("url")), // RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
                getPoseVideoParam); // 한 개의 회원정보를 얻기 위한 jdbcTemplate 함수(Query, 객체 매핑 정보, Params)의 결과 반환
    }

}
