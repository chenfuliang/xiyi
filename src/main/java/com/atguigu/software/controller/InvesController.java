package com.atguigu.software.controller;


import com.atguigu.software.config.R;
import com.atguigu.software.entity.Customer;
import com.atguigu.software.entity.Inves;
import com.atguigu.software.entity.vo.InvesQuery;
import com.atguigu.software.service.CustomerService;
import com.atguigu.software.service.InvesService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/software/inves")
@CrossOrigin
public class InvesController {

    @Autowired
    private InvesService InvesService;

    //修改充值记录ById
    @PostMapping("updateInves")
    public R updateInves(@RequestBody Inves Inves) {
        boolean b = InvesService.updateById(Inves);
        return b ? R.ok() : R.error();
    }

    //添加充值记录
    @GetMapping("addInves/{memId}/{money}")
    public R addInves(@PathVariable String memId, @PathVariable BigDecimal money) {
        Inves inves = new Inves();
        inves.setInvestMoney(money);
        inves.setMemId(memId);
        boolean save = InvesService.save(inves);
        return save ? R.ok() : R.error();
    }

    //删除充值记录
    @DeleteMapping("deleteInves/{id}")
    public R deleteInves(@PathVariable String id) {
        QueryWrapper<Inves> wrapper = new QueryWrapper<>();
        wrapper.eq("inv_id", id);
        boolean remove = InvesService.remove(wrapper);
        return remove ? R.ok() : R.error();
    }

    //条件查询分页
    @ApiOperation(value = "条件查询分页")
    @PostMapping("getInvesListPage/{current}/{limit}")
    public R getInvesListPage(@PathVariable long current, @PathVariable long limit,
                              @RequestBody(required = false) InvesQuery InvesQuery) {
        //1.创建page对象
        Page<Inves> pageInves = new Page<>(current, limit);

        QueryWrapper<Inves> wrapper = new QueryWrapper<>();

        //判断条件是否为空
        String memId = InvesQuery.getMemId();
        BigDecimal money = InvesQuery.getMoney();
        String begin = InvesQuery.getBegin();
        String end = InvesQuery.getEnd();

        if (!StringUtils.isEmpty(memId)) {
            wrapper.eq("mem_id", memId);
        }
        if (!StringUtils.isEmpty(money)) {
            wrapper.eq("invest_money", money);
        }

        if (!StringUtils.isEmpty(begin)) {
            wrapper.ge("inv_time", begin);
        }

        if (!StringUtils.isEmpty(end)) {
            wrapper.le("inv_time", end);
        }

        wrapper.orderByDesc("inv_time");
        InvesService.page(pageInves, wrapper);

        List<Inves> records = pageInves.getRecords();

        return R.ok().data("total", pageInves.getTotal()).data("rows", records);
    }
}

