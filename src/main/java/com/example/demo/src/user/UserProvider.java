package com.example.demo.src.user;

import com.example.demo.config.BaseException;
import com.example.demo.src.user.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class UserProvider {
    private final UserDao userDao;
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired //readme 참고
    public UserProvider(UserDao userDao) {
        this.userDao = userDao;
    }

    public int checkDeviceNum(String deviceNum) throws BaseException {
        try {
            return userDao.checkDeviceNum(deviceNum);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int checkUserPose(int userIdx) throws BaseException {
        try {
            return userDao.checkUserPose(userIdx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public GetUserContentsRes getUserContents(int userIdx) throws BaseException{
        try {
            return userDao.getUserContents(userIdx);
        }catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
