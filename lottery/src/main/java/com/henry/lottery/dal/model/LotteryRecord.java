package com.henry.lottery.dal.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LotteryRecord extends Model {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String accountIp;

    private Integer itemId;

    private String prizeName;

    private LocalDateTime createTime;


}
