package org.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.entity.UserInfo;

import java.util.List;

public interface IUserService extends IService<UserInfo> {
    UserInfo login(String username, String password);

    List<UserInfo> getAllUser();

    UserInfo getUserById(Integer id);

    boolean addUser(UserInfo userInfo);

    boolean updateUserById(Integer id, UserInfo userInfo);

    boolean deleteUserById(Integer id);
}
