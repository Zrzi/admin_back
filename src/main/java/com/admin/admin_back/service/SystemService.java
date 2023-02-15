package com.admin.admin_back.service;

import com.admin.admin_back.pojo.vo.SystemVo;

import java.util.List;

public interface SystemService {

    List<SystemVo> getSystems();

    void addSystem(String systemName);

    void updateSystem(String systemId, String systemName);

    void deleteSystem(String systemId);

}
