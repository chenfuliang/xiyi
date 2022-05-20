package com.atguigu.software.controller;


import com.atguigu.software.config.R;
import com.atguigu.software.entity.Order;
import com.atguigu.software.entity.vo.OrderQuery;
import com.atguigu.software.entity.vo.OrderResult;
import com.atguigu.software.service.OrderService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/software/order")
@CrossOrigin
public class OrderController {
    @Autowired
    private OrderService orderService;

    //查询订单ById
    @GetMapping("getOrderById/{id}")
    public R getOrderById(@PathVariable String id) {
        OrderResult order = orderService.getOrderInfo(id);
        if (order == null)
            return R.error().message("订单不存在");
        return R.ok().data("order", order);
    }

    //修改订单ById
    @PostMapping("updateOrder")
    public R updateOrder(@RequestBody Order order) {
        System.out.println(order);
        boolean b = orderService.updateById(order);
        return b ? R.ok() : R.error();
    }

    //添加订单
    @PostMapping("addOrder")
    public R addOrder(@RequestBody Order order) {
        boolean b = orderService.save(order);
        return b ? R.ok() : R.error();
    }

    //删除订单
    @DeleteMapping("deleteOrder/{id}")
    public R deleteOrder(@PathVariable String id) {
        boolean b = orderService.removeById(id);
        return b ? R.ok() : R.error();
    }

    //条件查询分页
    @ApiOperation(value = "条件查询分页")
    @PostMapping("getOrderListPage/{current}/{limit}")
    public R getOrderListPage(@PathVariable long current, @PathVariable long limit,
                              @RequestBody(required = false) OrderQuery orderQuery) {
        //1.创建page对象
        Page<Order> pageOrder = new Page<>(current, limit);

        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        //判断条件是否为空
        BigDecimal cost = orderQuery.getCost();

        String payState = orderQuery.getPayState();
        if (!StringUtils.isEmpty(cost)) {
            wrapper.like("cost", cost);
        }
        if (!StringUtils.isEmpty(payState)) {
            wrapper.eq("pay_state", payState);
        }
        if (!StringUtils.isEmpty(orderQuery.getReceiveTime())) {
            wrapper.ge("receive_time", orderQuery.getReceiveTime());
        }
        if (!StringUtils.isEmpty(orderQuery.getReceiveTime())) {
            wrapper.le("fetch_time", orderQuery.getFetchTime());
        }
//        wrapper.orderByDesc("gmt_create");
        orderService.page(pageOrder, wrapper);

        List<Order> records = pageOrder.getRecords();

        return R.ok().data("total", pageOrder.getTotal()).data("rows", records);
    }
}

