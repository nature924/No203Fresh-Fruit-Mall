package com.softeem.fresh.service.impl;

import com.softeem.fresh.entity.User;
import com.softeem.fresh.mapper.UserMapper;
import com.softeem.fresh.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
@Service("userService")
public class UserServiceImpl implements UserService {

    @Resource
    UserMapper mapper;

    @Override
    public User findUserByUsername(String username) {
        return mapper.findUserByUsername(username);
    }

    @Override
    public int doReg(User user) {
        return mapper.insert(user);
    }

    @Override
    public int updatePassword(User user) {
        return mapper.updatePassword(user);
    }
}
