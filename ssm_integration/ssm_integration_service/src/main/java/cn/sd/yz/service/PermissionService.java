package cn.sd.yz.service;

import cn.sd.yz.domain.Permission;

import java.util.List;

public interface PermissionService {


    List<Permission> findAll() throws Exception;

    void savePermission(Permission permission) throws Exception;
}
