package com.henry.redislikes.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.henry.redislikes.model.dto.JobBaseInfoDTO;
import com.henry.redislikes.model.entity.JobBaseInfo;
import com.henry.redislikes.model.vo.JobBaseInfoVO;

import java.util.List;

/**
 * <p>
 * 工作任务详情基础信息表 Mapper 接口
 * </p>
 *
 * @author henry
 * @since 2021-03-24
 */
public interface JobBaseInfoMapper extends BaseMapper<JobBaseInfo> {

    List<JobBaseInfoVO> selectJobList(JobBaseInfoDTO jobBaseInfoDTO);
}
