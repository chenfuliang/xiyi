package com.atguigu.software.controller;


import com.atguigu.software.config.R;
import com.atguigu.software.entity.Customer;
import com.atguigu.software.entity.vo.CustomerQuery;
import com.atguigu.software.entity.vo.EmpQuery;
import com.atguigu.software.service.CustomerService;
import com.atguigu.software.service.CustomerService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2022-05-09
 */
@RestController
@RequestMapping("/software/cus")
@CrossOrigin
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    //查询客户ById
    @GetMapping("getCustomerById/{id}")
    public R getCustomerById(@PathVariable String id) {
        Customer customer = customerService.getById(id);
        return R.ok().data("customer", customer);
    }

    //修改客户ById
    @PostMapping("updateCustomer")
    public R updateCustomer(@RequestBody Customer customer) {
        System.out.println(customer);
        boolean b = customerService.updateById(customer);
        return b ? R.ok() : R.error();
    }


    //添加客户
    @PostMapping("addCustomer")
    public R addCustomer(@RequestBody Customer customer) {
        boolean b = customerService.save(customer);
        return b ? R.ok() : R.error();
    }

    //删除客户
    @DeleteMapping("deleteCustomer/{id}")
    public R deleteCustomer(@PathVariable String id) {
        boolean b = customerService.removeById(id);
        return b ? R.ok() : R.error();
    }

    //条件查询分页
    @ApiOperation(value = "条件查询分页")
    @PostMapping("getCustomerListPage/{current}/{limit}")
    public R getCustomerListPage(@PathVariable long current, @PathVariable long limit,
                                 @RequestBody(required = false) CustomerQuery customerQuery) {
        //1.创建page对象
        Page<Customer> pageCus = new Page<>(current, limit);

        QueryWrapper<Customer> wrapper = new QueryWrapper<>();
        //判断条件是否为空
        String name = customerQuery.getName();
        String phone = customerQuery.getPhone();
        char sex = customerQuery.getSex();
        String begin = customerQuery.getBegin();
        String end = customerQuery.getEnd();
        System.out.println(customerQuery.toString());
        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(phone)) {
            wrapper.eq("phone", phone);
        }
        if (!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_create", end);
        }
        if (!StringUtils.isEmpty(sex) && sex != ' ') {
            wrapper.le("sex", sex);
        }
        wrapper.orderByDesc("gmt_create");
        customerService.page(pageCus, wrapper);

        List<Customer> records = pageCus.getRecords();
        return R.ok().data("total", pageCus.getTotal()).data("rows", records);
    }
}

