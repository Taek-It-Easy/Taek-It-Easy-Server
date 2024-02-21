package com.example.demo.src.gpt;

import com.example.demo.src.gpt.model.GptReq;
import com.example.demo.src.gpt.model.GptRes;
import com.example.demo.src.gpt.model.QuestionReq;
import com.example.demo.src.user.UserProvider;
import com.example.demo.src.user.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat-gpt")
public class GptController {
    private final GptService gptService;
    private final GptProvider gptProvider;

    public GptController(GptProvider gptProvider, GptService gptService) {
        this.gptProvider = gptProvider;
        this.gptService = gptService;
    }

    @PostMapping("/question")
    public GptRes sendQuestion(@RequestBody QuestionReq questionReq) {
        return gptService.askQuestion(questionReq);
    }
}
