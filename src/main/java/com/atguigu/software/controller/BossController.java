package com.atguigu.software.controller;


import com.atguigu.software.config.R;
import com.atguigu.software.entity.Boss;
import com.atguigu.software.service.BossService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/software/boss")
public class BossController {
    @Autowired
    private BossService bossService;

    @GetMapping("/aaa")
    public R test() {
        Boss byId = bossService.getById("1");
        System.out.println(byId);
        return R.ok().data("test", byId);
    }
}

