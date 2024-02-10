package com.example.demo.src.badge;


import com.example.demo.src.badge.model.GetBadgeRes;
import com.example.demo.src.badge.model.PostBadgeReq;
import com.example.demo.src.user.model.PostUserReq;
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

    // badge 중복 확인
    public int checkBadge(PostBadgeReq postBadgeReq) {
        String checkBadgeQuery = "select exists(select badgeIdx from UserBadgeList where userIdx = ? and badgeIdx = ?)";
        Object[] checkBadgeParams = new Object[]{postBadgeReq.getUserIdx(), postBadgeReq.getBadgeIdx()};
        return this.jdbcTemplate.queryForObject(checkBadgeQuery,
                int.class,
                checkBadgeParams); // checkBadgeQuery, checkBadgeParams를 통해 가져온 값(intgud)을 반환한다. -> 쿼리문의 결과(존재하지 않음(False,0),존재함(True, 1))를 int형(0,1)으로 반환됩니다.
    }

    // 뱃지 생성
    public int createBadge(PostBadgeReq postBadgeReq) {
        String createBadgeQuery = "insert into UserBadgeList (userIdx, badgeIdx) VALUES (?,?)"; // 실행될 동적 쿼리문
        Object[] createBadgeParams = new Object[]{postBadgeReq.getUserIdx(), postBadgeReq.getBadgeIdx()}; // 동적 쿼리의 ?부분에 주입될 값
        this.jdbcTemplate.update(createBadgeQuery, createBadgeParams);
        String lastInserIdQuery = "select last_insert_id()"; // 가장 마지막에 삽입된(생성된) id값은 가져온다.
        return this.jdbcTemplate.queryForObject(lastInserIdQuery, int.class); // 해당 쿼리문의 결과 마지막으로 삽인된 유저의 userIdx번호를 반환한다.
    }

}
