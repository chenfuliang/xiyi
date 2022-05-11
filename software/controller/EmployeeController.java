package com.atguigu.software.controller;


import com.atguigu.software.config.R;
import com.atguigu.software.entity.Employee;
import com.atguigu.software.entity.vo.EmpQuery;
import com.atguigu.software.service.EmployeeService;
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
@RequestMapping("software/emp")
@CrossOrigin
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    //查询员工ById
    @GetMapping("getEmpById/{id}")
    public R getEmpById(@PathVariable String id) {
        Employee employee = employeeService.getById(id);
        return R.ok().data("emp", employee);
    }

    //修改员工ById
    @PostMapping("updateEmp")
    public R updateEmp(@RequestBody Employee emp) {
        System.out.println(emp);
        boolean b = employeeService.updateById(emp);
        return b ? R.ok() : R.error();
    }


    //添加员工
    @PostMapping("addEmp")
    public R addEmp(@RequestBody Employee emp) {
        boolean b = employeeService.save(emp);
        return b ? R.ok() : R.error();
    }

    //删除员工
    @DeleteMapping("deleteEmp/{id}")
    public R deleteEmp(@PathVariable String id) {
        boolean b = employeeService.removeById(id);
        return b ? R.ok() : R.error();
    }

    //条件查询分页
    @ApiOperation(value = "条件查询分页")
    @PostMapping("getEmpListPage/{current}/{limit}")
    public R getEmpListPage(@PathVariable long current, @PathVariable long limit,
                            @RequestBody(required = false) EmpQuery empQuery) {
        //1.创建page对象
        Page<Employee> pageEmp = new Page<>(current, limit);

        QueryWrapper<Employee> wrapper = new QueryWrapper<>();
        //判断条件是否为空
        String name = empQuery.getName();
        Integer level = empQuery.getLevel();
        String begin = empQuery.getBegin();
        String end = empQuery.getEnd();
        System.out.println(empQuery.toString());
        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(level)) {
            wrapper.eq("level", level);
        }
        if (!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_create", end);
        }
        wrapper.orderByDesc("gmt_create");
        employeeService.page(pageEmp, wrapper);

        List<Employee> records = pageEmp.getRecords();

        return R.ok().data("total", pageEmp.getTotal()).data("rows", records);
    }
}
