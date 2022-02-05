package com.henry.lottery.dal.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LotteryItem extends Model {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 活动id
     */
    private Integer lotteryId;

    /**
     * 奖项名称
     */
    private String itemName;

    /**
     * 奖项等级
     */
    private Integer level;

    /**
     * 奖项概率
     */
    private BigDecimal percent;

    /**
     * 奖品id
     */
    private Integer prizeId;

    /**
     * 创建时间
     */
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createTime;

    /**
     * 默认奖项
     */
    private Integer defaultItem;

}
