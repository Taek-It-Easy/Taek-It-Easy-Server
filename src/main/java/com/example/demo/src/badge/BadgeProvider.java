package com.example.demo.src.badge;

import com.example.demo.config.BaseException;
import com.example.demo.src.badge.BadgeDao;
import com.example.demo.src.badge.model.GetBadgeRes;
import com.example.demo.src.user.UserDao;
import com.example.demo.src.user.model.GetUserRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class BadgeProvider {
    private final BadgeDao badgeDao;
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired //readme 참고
    public BadgeProvider(BadgeDao badgeDao) {
        this.badgeDao = badgeDao;
    }

    // 해당 유저의 뱃지 정보 조회
    public List<GetBadgeRes> getBadges(int userIdx) throws BaseException {
        try {
            List<GetBadgeRes> getBadgeRes = badgeDao.getBadges(userIdx);
            return getBadgeRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }


}
