package com.example.demo.src.user;

import org.springframework.stereotype.Service;
import com.example.demo.config.BaseException;
import com.example.demo.config.secret.Secret;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.AES128;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.jdbc.core.JdbcTemplate;
import javax.sql.DataSource;
import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class UserService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserDao userDao;
    private final UserProvider userProvider;

    @Autowired //readme 참고
    public UserService(UserDao userDao, UserProvider userProvider, JwtService jwtService) {
        this.userDao = userDao;
        this.userProvider = userProvider;

    }

    public PostUserRes createUser(PostUserReq postUserReq) throws BaseException {
        // 중복 확인: 디바이스 정보가 이미 있다면 회원가입 진행 멈춤
        if (userProvider.checkDeviceNum(postUserReq.getDeviceNum()) != 0) {
            System.out.println("중복");
            int userIdx = userDao.checkDeviceNum(postUserReq.getDeviceNum());
            return new PostUserRes(userIdx);
        }

        try {
            System.out.println("중복 아님");
            int userIdx = userDao.createUser(postUserReq);
            return new PostUserRes(userIdx);

        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void modifyUserPose(int userIdx, int poseIdx) throws BaseException {
        if (userProvider.checkUserPose(userIdx) == 1) {
            try {
                int result = userDao.modifyUserPose(userIdx,poseIdx);
                if (result == 0) { // result값이 0이면 과정이 실패한 것이므로 에러 메서지를 보냅니다.
                    throw new BaseException(MODIFY_FAIL_USERPOSE);
                }
            }catch (Exception exception){
                throw new BaseException(DATABASE_ERROR);
            }
        }else{
            try {
                int result = userDao.insertUserPose(userIdx,poseIdx); // 해당 과정이 무사히 수행되면 True(1), 그렇지 않으면 False(0)입니다.
                if (result == 0) { // result값이 0이면 과정이 실패한 것이므로 에러 메서지를 보냅니다.
                    throw new BaseException(INSERT_FAIL_USERPOSE);
                }
            } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
                throw new BaseException(DATABASE_ERROR);
            }
        }
    }
}
