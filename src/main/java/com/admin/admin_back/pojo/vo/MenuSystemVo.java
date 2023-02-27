package com.admin.admin_back.pojo.vo;

import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * @author 陈群矜
 */
public class MenuSystemVo {

    private String systemId;

    private String systemName;

    private List<MenuResourceVo> menus;

    public MenuSystemVo() {}

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public List<MenuResourceVo> getMenus() {
        return menus;
    }

    public void setMenus(List<MenuResourceVo> menus) {
        this.menus = menus;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
