package io.goji.team.controller;

import io.goji.team.common.result.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {

    @GetMapping("/jwt")
    public Result<Void> test() {
        return Result.success();
    }

}
