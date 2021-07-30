package com.cxytiandi.sharding.service.impl;

import com.cxytiandi.sharding.po.LouDong;
import com.cxytiandi.sharding.po.User;
import com.cxytiandi.sharding.repository.UserRepository;
import com.cxytiandi.sharding.service.LouDongService;
import com.cxytiandi.sharding.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepository userRepository;
    @Resource
    private LouDongService louDongService;

    public List<User> list() {
        return userRepository.list();
    }

//	@SagaStart(timeout=10)
//    public void allTransatinal() {
//        serverA();
//    }
//
//    @Compensable(timeout=5, compensationMethod="cancelA")
//    public void serverA() {
//    }
//    public void cancelA() {
//    }

    @Transactional
    public Long add(User user) {
        LouDong louDong=createLouDong();
        Long aLong=louDongService.addLouDong(louDong);
        Long aLong1=userRepository.addUser(user);

        return aLong1;
    }

    private LouDong createLouDong() {
        LouDong louDong=new LouDong();
        louDong.setId(String.valueOf(new Date().getTime()));
        louDong.setCity("北京");
        louDong.setRegion("宝安");
        louDong.setName("李四");
        louDong.setLdNum("A");
        louDong.setUnitNum("2");
        return louDong;
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User findByName(String name) {
        return userRepository.findByName(name);
    }

}
