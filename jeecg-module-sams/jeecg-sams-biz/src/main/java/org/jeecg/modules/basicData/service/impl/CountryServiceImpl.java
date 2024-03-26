package org.jeecg.modules.basicData.service.impl;

import org.jeecg.modules.basicData.entity.Country;
import org.jeecg.modules.basicData.mapper.CountryMapper;
import org.jeecg.modules.basicData.service.ICountryService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: country
 * @Author: jeecg-boot
 * @Date:   2024-03-26
 * @Version: V1.0
 */
@Service
public class CountryServiceImpl extends ServiceImpl<CountryMapper, Country> implements ICountryService {

}
