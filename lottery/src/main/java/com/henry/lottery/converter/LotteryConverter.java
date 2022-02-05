package com.henry.lottery.converter;


import com.henry.lottery.dto.DoDrawDto;
import com.henry.lottery.vo.LotteryItemVo;
import org.mapstruct.Mapper;

/**
 * mapstruct 对象属性映射
 */
@Mapper(componentModel = "spring")
public interface LotteryConverter {

    LotteryItemVo dto2LotteryItemVo(DoDrawDto drawDto);

}
