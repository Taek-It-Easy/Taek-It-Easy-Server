package com.example.demo.src.video;

import com.example.demo.config.BaseException;
import com.example.demo.src.badge.model.GetBadgeRes;
import com.example.demo.src.user.UserDao;
import com.example.demo.src.video.model.GetPoseVideoRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class VideoProvider {
    private final VideoDao videoDao;
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired //readme 참고
    public VideoProvider(VideoDao videoDao) {
        this.videoDao = videoDao;
    }

    public GetPoseVideoRes getPoseVideo(int poseIdx) throws BaseException {
        try {
            GetPoseVideoRes getPoseVideoRes = videoDao.getPoseVideo(poseIdx);
            return getPoseVideoRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
