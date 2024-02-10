package com.example.demo.src.badge;

import com.example.demo.config.BaseException;
import com.example.demo.src.badge.model.PostBadgeReq;
import com.example.demo.src.badge.model.PostBadgeRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class BadgeService {
    private final BadgeDao badgeDao;
    private final BadgeProvider badgeProvider;

    @Autowired //readme 참고
    public BadgeService(BadgeDao badgeDao, BadgeProvider badgeProvider) {
        this.badgeDao = badgeDao;
        this.badgeProvider = badgeProvider;
    }

    public PostBadgeRes createBadge(PostBadgeReq postBadgeReq) throws BaseException {
        // 중복 확인: 해당 뱃지를 가지고 있는지 확인
        if (badgeProvider.checkBadge(postBadgeReq) == 1) {
            throw new BaseException(DUPLICATED_BADGE);
        }
        try {
            int userIdx = badgeDao.createBadge(postBadgeReq);
            return new PostBadgeRes(userIdx, postBadgeReq.getBadgeIdx());

        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
