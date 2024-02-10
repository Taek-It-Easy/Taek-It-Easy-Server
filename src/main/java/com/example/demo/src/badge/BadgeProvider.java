package com.example.demo.src.badge;

import com.example.demo.config.BaseException;
import com.example.demo.src.badge.BadgeDao;
import com.example.demo.src.badge.model.GetBadgeRes;
import com.example.demo.src.badge.model.PostBadgeReq;
import com.example.demo.src.camera.CameraDao;
import com.example.demo.src.camera.CameraProvider;
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
    //private final BadgeService badgeService;

    @Autowired //readme 참고
    public BadgeProvider(BadgeDao badgeDao) {
        this.badgeDao = badgeDao;
        //this.badgeService = badgeService;

    }
    final Logger logger = LoggerFactory.getLogger(this.getClass());


    // 해당 유저의 뱃지 정보 조회
    public List<GetBadgeRes> getBadges(int userIdx) throws BaseException {
        try {
            List<GetBadgeRes> getBadgeRes = badgeDao.getBadges(userIdx);
            return getBadgeRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 해당 유저의 뱃지 정보 조회
    public int checkBadge(PostBadgeReq postBadgeReq) throws BaseException {
        try {
            return badgeDao.checkBadge(postBadgeReq);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
