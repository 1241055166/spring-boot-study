package com.henry.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.henry.dal.mapper.CountryMapper;
import com.henry.dal.model.Country;
import com.henry.service.ICountryService;
import org.springframework.stereotype.Service;

@Service
public class CountryServiceImpl extends ServiceImpl<CountryMapper, Country> implements ICountryService {

}
