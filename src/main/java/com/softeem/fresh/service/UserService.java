package com.softeem.fresh.service;

import com.softeem.fresh.entity.User;

public interface UserService {
    User findUserByUsername(String username);

    int doReg(User user);

    int updatePassword(User user);
}
