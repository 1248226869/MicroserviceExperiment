package com.cxytiandi.sharding.service.impl;

import com.cxytiandi.sharding.po.LouDong;
import com.cxytiandi.sharding.repository.LouDongRepository;
import com.cxytiandi.sharding.service.LouDongService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LouDongServiceImpl implements LouDongService {

	@Resource
	private LouDongRepository louDongRepository;
	
	@Override
	public List<LouDong> list() {
		return louDongRepository.list();
	}

	@Override
	public Long addLouDong(LouDong louDong) {
		return louDongRepository.addLouDong(louDong);
	}

}
