package com.grit.learning.mapper;

import com.grit.learning.entity.UserEntity;
import com.grit.learning.param.UserParam;
import java.util.List;

public interface UserMapper {

	List<UserEntity> getAll();

	List<UserEntity> getList(UserParam userParam);

	int getCount(UserParam userParam);

	UserEntity getOne(Long id);

	void insert(UserEntity user);

	int update(UserEntity user);

	int delete(Long id);

}