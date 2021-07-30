package com.cxytiandi.sharding.repository;

import com.cxytiandi.sharding.config.DateTableShardStrategy;
import com.cxytiandi.sharding.config.TableShard;
import com.cxytiandi.sharding.po.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
//@TableShard(tableName="user",field = "flag", shardStrategy = DateTableShardStrategy.class)
public interface UserRepository {
	
	Long addUser(User user);
	
	List<User> list();
	
	User findById(Long id);
	
	User findByName(String name);
}
