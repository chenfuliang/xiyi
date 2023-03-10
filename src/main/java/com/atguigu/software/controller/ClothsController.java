package com.atguigu.software.controller;


import com.atguigu.software.config.R;
import com.atguigu.software.entity.Cloths;
import com.atguigu.software.entity.vo.ClothQuery;
import com.atguigu.software.service.ClothsService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/software/cloths")
@CrossOrigin
public class ClothsController {
    @Autowired
    private ClothsService clothService;

    //查询订单ById
    @GetMapping("getClothsById/{id}")
    public R getClothsById(@PathVariable String id) {
        Cloths cloth = clothService.getById(id);
        return R.ok().data("cloth", cloth);
    }

    //修改订单ById
    @PostMapping("updateCloths")
    public R updateCloths(@RequestBody Cloths cloth) {
        System.out.println(cloth);
        boolean b = clothService.updateById(cloth);
        return b ? R.ok() : R.error();
    }

    //添加订单
    @PostMapping("addCloths")
    public R addCloths(@RequestBody Cloths cloth) {
        QueryWrapper<Cloths> wrapper = new QueryWrapper<>();
        wrapper.eq("clo_id", cloth.getCloId());
        int newC = clothService.count(wrapper);
        if (newC > 0) {
            return R.error().message("衣物已存在");
        }
        boolean b = clothService.save(cloth);
        return b ? R.ok() : R.error();
    }

    //删除订单
    @DeleteMapping("deleteCloths/{id}")
    public R deleteCloths(@PathVariable String id) {
        boolean b = clothService.removeById(id);
        return b ? R.ok() : R.error();
    }

    //条件查询分页
    @ApiOperation(value = "条件查询分页")
    @PostMapping("getClothsListPage/{current}/{limit}")
    public R getClothsListPage(@PathVariable long current, @PathVariable long limit,
                               @RequestBody(required = false) ClothQuery clothQuery) {
        //1.创建page对象
        Page<Cloths> pageCloths = new Page<>(current, limit);

        QueryWrapper<Cloths> wrapper = new QueryWrapper<>();
        //判断条件是否为空
        String type = clothQuery.getType();
        String washState = clothQuery.getWashState();
        String color = clothQuery.getColor();
        String begin = clothQuery.getBegin();

        if (!StringUtils.isEmpty(type)) {
            wrapper.like("type", type);
        }
        if (!StringUtils.isEmpty(washState)) {
            wrapper.eq("wash_state", washState);
        }
        if (!StringUtils.isEmpty(color)) {
            wrapper.eq("color", color);
        }
        if (!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create", begin);
        }
        wrapper.orderByDesc("gmt_create");
        clothService.page(pageCloths, wrapper);

        List<Cloths> records = pageCloths.getRecords();

        return R.ok().data("total", pageCloths.getTotal()).data("rows", records);
    }
}

