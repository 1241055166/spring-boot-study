package com.henry.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.henry.dal.mapper.CountrylanguageMapper;
import com.henry.dal.model.Countrylanguage;
import com.henry.service.ICountrylanguageService;
import org.springframework.stereotype.Service;

@Service
public class CountrylanguageServiceImpl extends ServiceImpl<CountrylanguageMapper, Countrylanguage
        > implements ICountrylanguageService {

}
