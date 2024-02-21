package com.example.demo.src.gpt;

import com.example.demo.src.gpt.model.*;
import com.example.demo.src.user.UserDao;
import com.example.demo.src.user.UserProvider;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GptService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final GptDao gptDao;
    private final GptProvider gptProvider;

    @Autowired //readme 참고
    public GptService(GptDao gptDao, GptProvider gptProvider) {
        this.gptDao = gptDao;
        this.gptProvider = gptProvider;

    }
    private static RestTemplate restTemplate = new RestTemplate();

    public HttpEntity<GptReq> buildHttpEntity(GptReq gptReq) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(config.MEDIA_TYPE));
        headers.add(config.AUTHORIZATION, config.BEARER + config.API_KEY);
        return new HttpEntity<>(gptReq, headers);
    }

    public GptRes getResponse(HttpEntity<GptReq> gptReqHttpEntity) {
        // Adjust the method to expect the correct type HttpEntity<GptReq>
        ResponseEntity<GptRes> responseEntity = restTemplate.postForEntity(
                config.URL,
                gptReqHttpEntity,
                GptRes.class);

        return responseEntity.getBody();
    }

    public GptRes askQuestion(QuestionReq questionReq) {
        // Change the type of the HttpEntity in buildHttpEntity to GptRes
        return this.getResponse(
                this.buildHttpEntity(
                        new GptReq(
                                config.MODEL,
                                questionReq.getQuestion(),
                                config.MAX_TOKEN,
                                config.TEMPERATURE,
                                config.TOP_P
                        )
                )
        );
    }



}
