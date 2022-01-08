package com.henry.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.henry.domain.UserDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description:
 * @Author: huangjw-b
 * @Date: 2021/12/16
 */
public interface UserMapper extends BaseMapper<UserDO> {
    /**
     * 批量插入
     * @param userList
     * @return
     */
    int batchInsert(@Param("listUser") List<UserDO> userList);
}
