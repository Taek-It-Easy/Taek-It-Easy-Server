package com.example.demo.src.camera;

import com.example.demo.src.camera.model.GetCameraReq;
import com.example.demo.src.camera.model.GetPoseNumRes;
import com.example.demo.src.camera.model.GetTargetRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository

public class CameraDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public GetTargetRes getTarget(GetCameraReq getCameraReq, int order) {
        // db에 처음 , 중간 , 끝의 column 추가 및 그걸 출력하는 코드 추가
        String getTargetQuery = "SELECT dotIdx, x, y, z, reliability FROM Position LEFT JOIN Pose b ON Position.poseIdx = b.poseIdx WHERE b.poseIdx = ? AND Position.poseSeq = ? AND b.status = 'y';"; // 이부분은 수정 필요 - chapterIdx에 맞추어 target 값 가져오기
        int getCameraReqPoseIdx = getCameraReq.getPoseIdx();
        int getCameraReqPoseReq = order;

        List<GetTargetRes.PoseData> poseDataList = this.jdbcTemplate.query(getTargetQuery,
                (rs, rowNum) -> {
                    GetTargetRes.PoseData poseData = new GetTargetRes.PoseData();
                    poseData.setPosition(rs.getString("dotIdx"));
                    poseData.setX(rs.getFloat("x"));
                    poseData.setY(rs.getFloat("y"));
                    poseData.setZ(rs.getFloat("z"));
                    poseData.setReliability(rs.getFloat("reliability"));
                    return poseData;
                },
                getCameraReqPoseIdx,
                getCameraReqPoseReq
        );
        GetTargetRes getTargetRes = new GetTargetRes();
        getTargetRes.setPoseIdx(getCameraReqPoseIdx);
        getTargetRes.setPose(poseDataList);

        return getTargetRes;
    }

    public GetPoseNumRes getPoseNum(GetCameraReq getCameraReq) {
        String getPwdQuery = "SELECT COUNT(DISTINCT poseSeq) as cnt from Position where poseIdx =?;";
        int getCameraReqPoseIdx = getCameraReq.getPoseIdx();

        return this.jdbcTemplate.queryForObject(getPwdQuery,
                (rs, rowNum) -> new GetPoseNumRes(
                        rs.getInt("cnt")
                ), // RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
                getCameraReqPoseIdx
        ); // 한 개의 회원정보를 얻기 위한 jdbcTemplate 함수(Query, 객체 매핑 정보, Params)의 결과 반환
    }
}
