package cn.sd.yz.service;

import cn.sd.yz.domain.Role;
import cn.sd.yz.domain.UserInfo;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<UserInfo> findAll() throws Exception;

    void saveUser(UserInfo userInfo) throws Exception;

    UserInfo findById(String id) throws Exception;

    List<Role> findOtherRole(String id) throws Exception;

    void addRoleToUser(String userId, String[] roleIds) throws Exception;
}
