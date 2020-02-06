package com.heyutang.blog.service.serviceImpl;

import com.heyutang.blog.dao.UserRepository;
import com.heyutang.blog.po.User;
import com.heyutang.blog.service.UserService;
import com.heyutang.blog.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User checkUser(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, MD5Utils.code(password));
    }
}
