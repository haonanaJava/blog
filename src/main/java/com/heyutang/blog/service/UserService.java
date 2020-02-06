package com.heyutang.blog.service;

import com.heyutang.blog.po.User;

public interface UserService {

    User checkUser(String username,String password);
}
