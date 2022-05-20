package com.atguigu.software.controller;

import com.atguigu.software.config.R;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/software/user")
@Api(description = "登录管理")
@CrossOrigin
public class LoginController {

    @PostMapping("login")
    public R login() {
        return R.ok().data("token", "admin");
    }

    @GetMapping("info")
    public R info() {
        return R.ok()
                .data("roles", "admin")
                .data("name", "admin")
                .data("avatar", "https://wpimg.wallstcn.com/69a1c46c-eb1c-4b46-8bd4-e9e686ef5251.png");
    }
}
