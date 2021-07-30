package com.cxytiandi.sharding.repository;

import com.cxytiandi.sharding.config.mysql.DateTableShardStrategy;
import com.cxytiandi.sharding.config.mysql.TableShard;
import com.cxytiandi.sharding.po.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
//@TableShard(tableName = "user", fieldShard = "flag", shardStrategy = DateTableShardStrategy.class)
public interface UserRepository {
	
	Long addUser(User user);
	
	List<User> list();
	
	User findById(Long id);
	
	User findByName(String name);
}
