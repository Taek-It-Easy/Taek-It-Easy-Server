package com.example.demo.src.badge;


import com.example.demo.src.badge.model.GetBadgeRes;
import com.example.demo.src.user.model.GetUserRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository

public class BadgeDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 해당 userIdx를 갖는 유저조회
    public List<GetBadgeRes> getBadges(int userIdx) {
        String getUserQuery = "select userIdx, b.badgeIdx, content from  badge left join UserBadgeList b on badge.badgeIdx = b.badgeIdx where b.userIdx = ? and b.status = 'y';"; // 해당 userIdx를 만족하는 유저를 조회하는 쿼리문
        int getBadgesParams = userIdx;
        return this.jdbcTemplate.query(getUserQuery,
                (rs, rowNum) -> new GetBadgeRes(
                        rs.getInt("userIdx"),
                        rs.getInt("badgeIdx"),
                        rs.getString("content")),
                getBadgesParams);// RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기 // 한 개의 회원정보를 얻기 위한 jdbcTemplate 함수(Query, 객체 매핑 정보, Params)의 결과 반환
    }

}
