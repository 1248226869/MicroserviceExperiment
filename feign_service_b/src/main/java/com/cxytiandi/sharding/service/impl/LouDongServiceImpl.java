package com.cxytiandi.sharding.service.impl;

import com.cxytiandi.sharding.po.LouDong;
import com.cxytiandi.sharding.repository.LouDongRepository;
import com.cxytiandi.sharding.service.LouDongService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	@Transactional
	public Long addLouDong(LouDong louDong) {
		Long aLong=louDongRepository.addLouDong(louDong);

		return aLong;
	}

}
