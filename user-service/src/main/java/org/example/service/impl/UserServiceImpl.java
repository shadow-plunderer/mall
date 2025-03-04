package org.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.dao.IUserMapper;
import org.example.entity.UserInfo;
import org.example.service.IUserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<IUserMapper, UserInfo> implements IUserService {

    @Override
    public UserInfo login(String username, String password) {
        return this.getOne(new QueryWrapper<UserInfo>().eq("username", username).eq("password", password));
    }

    @Override
    public List<UserInfo> getAllUser() {
        return this.list();
    }

    @Override
    public UserInfo getUserById(Integer id) {
        return this.getById(id);
    }

    @Override
    public boolean addUser(UserInfo userInfo) {
        return this.save(userInfo);
    }

    @Override
    public boolean updateUserById(Integer id, UserInfo userInfo) {
        userInfo.setId(id);
        return this.updateById(userInfo);
    }

    @Override
    public boolean deleteUserById(Integer id) {
        return this.removeById(id);
    }
}
