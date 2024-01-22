package com.example.demo.src.camera;

import com.example.demo.config.BaseException;
import com.example.demo.config.secret.Secret;
import com.example.demo.src.camera.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;

@Service

public class CameraService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final CameraDao cameraDao;
    private final CameraProvider cameraProvider;

    @Autowired //readme 참고
    public CameraService(CameraDao cameraDao, CameraProvider cameraProvider) {
        this.cameraDao = cameraDao;
        this.cameraProvider = cameraProvider;

    }
}
