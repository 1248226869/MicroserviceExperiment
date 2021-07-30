package com.cxytiandi.sharding.controller;

import com.cxytiandi.sharding.config.moreDadtaSource.DBSource;
import com.cxytiandi.sharding.config.moreDadtaSource.DataSourceType;
import com.cxytiandi.sharding.po.LouDong;
import com.cxytiandi.sharding.service.LouDongService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class LouDongController {
	
	@Resource
	private LouDongService louDongService;
	
	@GetMapping("/lds")
	public Object list() {
		return louDongService.list();
	}
	
	@GetMapping("/ld/add")
	@DBSource(DataSourceType.ORACLE)
	public Object add(long id) {


		LouDong louDong = new LouDong();
		louDong.setId(id+"a");
		louDong.setCity("拉萨");
		louDong.setRegion("布达拉宫");
		louDong.setName("李婷");
		louDong.setLdNum("AS");
		louDong.setUnitNum("2sd");
		louDongService.addLouDong(louDong);
		return "success";
	}
	@GetMapping("/ld/add1")
	@DBSource(DataSourceType.MYSQL)
	public Object add1(long id) {
		LouDong louDong = new LouDong();
		louDong.setId(id+"a");
		louDong.setCity("成都");
		louDong.setRegion("马路");
		louDong.setName("郑蒙蒙");
		louDong.setLdNum("Z");
		louDong.setUnitNum("2we");
		louDongService.addLouDong(louDong);
		return "success";
	}
}
