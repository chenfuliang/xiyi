package com.atguigu.software.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author atguigu
 * @since 2022-05-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "Cloths对象", description = "")
public class Cloths implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "clo_id", type = IdType.ID_WORKER_STR)
    private String cloId;
    @TableId(value = "cus_id", type = IdType.ID_WORKER_STR)
    private String cusId;

    private String type;

    private String brand;

    private String imageUrl;

    private String washState;

    private String color;
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;


}
