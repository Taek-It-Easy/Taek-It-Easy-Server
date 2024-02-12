package com.example.demo.src.pose;

import com.example.demo.config.BaseException;
import com.example.demo.src.pose.model.GetPoseRes;
import com.example.demo.src.video.VideoDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class PoseProvider {
    private final PoseDao poseDao;
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired //readme 참고
    public PoseProvider(PoseDao poseDao) {
        this.poseDao = poseDao;
    }

    public List<GetPoseRes> getPoses() throws BaseException {
        try {
            List<GetPoseRes> getUserRes = poseDao.getPoses();
            return getUserRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
