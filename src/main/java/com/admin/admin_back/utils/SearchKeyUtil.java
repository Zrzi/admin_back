package com.admin.admin_back.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * 处理查询条件
 * @author 陈群矜
 */
public class SearchKeyUtil {

    public static String handle(String searchKey) {
        if (StringUtils.isBlank(searchKey)) {
            return null;
        }
        return '%' + searchKey + '%';
    }

}
