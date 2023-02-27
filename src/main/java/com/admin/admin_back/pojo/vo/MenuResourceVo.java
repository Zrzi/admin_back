package com.admin.admin_back.pojo.vo;

import com.alibaba.fastjson.JSON;

/**
 * @author 陈群矜
 */
public class MenuResourceVo {

    private String menuId;

    private String menuName;

    private String path;

    public MenuResourceVo() {}

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
