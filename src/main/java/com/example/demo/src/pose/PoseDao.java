package com.example.demo.src.pose;

import com.example.demo.src.pose.model.GetPoseRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class PoseDao {
    private JdbcTemplate jdbcTemplate;
    @Autowired //readme 참고
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetPoseRes> getPoses() {
        String getUsersQuery = "select * from Pose";
        return this.jdbcTemplate.query(getUsersQuery,
                (rs, rowNum) -> new GetPoseRes(
                        rs.getInt("poseIdx"),
                        rs.getString("poseName"),
                        rs.getString("poseEngName"),
                        rs.getString("poseRomName"))
        );
    }
}
