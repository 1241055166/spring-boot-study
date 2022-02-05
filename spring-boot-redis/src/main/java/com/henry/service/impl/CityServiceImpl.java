package com.henry.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.henry.dal.mapper.CityMapper;
import com.henry.dal.model.City;
import com.henry.service.ICityService;
import org.springframework.stereotype.Service;

@Service
public class CityServiceImpl extends ServiceImpl<CityMapper, City> implements ICityService {

}
