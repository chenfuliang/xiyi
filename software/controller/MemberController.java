package com.atguigu.software.controller;


import com.atguigu.software.config.R;
import com.atguigu.software.entity.Member;
import com.atguigu.software.entity.vo.EmpQuery;
import com.atguigu.software.entity.vo.MemberQuery;
import com.atguigu.software.service.MemberService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
@RequestMapping("/software/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

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
    @PostMapping("addMember")
    public R addMember(@RequestBody Member member) {
        boolean b = memberService.save(member);
        return b ? R.ok() : R.error();
    }

    //删除会员
    @DeleteMapping("deleteMember/{id}")
    public R deleteMember(@PathVariable String id) {
        boolean b = memberService.removeById(id);
        return b ? R.ok() : R.error();
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
            wrapper.eq("level", level);
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

