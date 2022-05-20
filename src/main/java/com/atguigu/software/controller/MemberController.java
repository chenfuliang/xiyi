package com.atguigu.software.controller;


import com.atguigu.software.config.R;
import com.atguigu.software.entity.Customer;
import com.atguigu.software.entity.Member;
import com.atguigu.software.entity.vo.MemberQuery;
import com.atguigu.software.mapper.CustomerMapper;
import com.atguigu.software.service.CustomerService;
import com.atguigu.software.service.MemberService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
@RequestMapping("/software/member")
@CrossOrigin
public class MemberController {

    @Autowired
    private MemberService memberService;
    @Autowired
    private CustomerService customerService;
    @Resource
    private CustomerMapper customerMapper;

    //查询会员ById
    @GetMapping("getMemberById/{id}")
    public R getMemberById(@PathVariable String id) {
        Member member = memberService.getById(id);
        return R.ok().data("member", member);
    }

    //修改会员ById
    @PostMapping("updateMember")
    public R updateMember(@RequestBody Member member) {
        System.out.println(member);
        boolean b = memberService.updateById(member);
        return b ? R.ok() : R.error();
    }

    //添加会员
    @GetMapping("addMember/{cusId}")
    public R addMember(@PathVariable String cusId) {
        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.eq("cus_id", cusId);
        int count = memberService.count(wrapper);
        if (count > 0)
            return R.error().message("已是会员");
        Member m = new Member();
        m.setCusId(cusId);
        m.setMemLevel("1");
        boolean b = memberService.save(m);

        QueryWrapper<Customer> wrapper2 = new QueryWrapper<>();
        wrapper2.eq("cus_id", cusId);
        Customer cus = customerService.getById(cusId);
        cus.setMemId(m.getMemId());
        customerService.update(cus, wrapper2);
        return b ? R.ok() : R.error();
    }

    //删除会员
    @DeleteMapping("deleteMember/{id}")
    public R deleteMember(@PathVariable String id) {
        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.eq("cus_id", id);
        boolean remove = memberService.remove(wrapper);
        boolean b = customerService.setMemberIdToNull(id);
        if (b) {
            return remove && b ? R.ok() : R.error().message("会员不存在");
        } else {
            return remove && b ? R.ok() : R.error().message("用户不是会员");
        }
    }

    //条件查询分页
    @ApiOperation(value = "条件查询分页")
    @PostMapping("getMemberListPage/{current}/{limit}")
    public R getMemberListPage(@PathVariable long current, @PathVariable long limit,
                               @RequestBody(required = false) MemberQuery memberQuery) {
        //1.创建page对象
        Page<Member> pageMember = new Page<>(current, limit);

        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        //判断条件是否为空
        String name = memberQuery.getName();
        Integer level = memberQuery.getLevel();
        String begin = memberQuery.getBegin();
        String end = memberQuery.getEnd();
        System.out.println(memberQuery.toString());
        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(level)) {
            wrapper.eq("mem_level", level);
        }
        if (!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_create", end);
        }
        wrapper.orderByDesc("gmt_create");
        memberService.page(pageMember, wrapper);

        List<Member> records = pageMember.getRecords();

        return R.ok().data("total", pageMember.getTotal()).data("rows", records);
    }
}

