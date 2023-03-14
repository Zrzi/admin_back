package com.admin.admin_back.pojo.dto;

import java.util.HashMap;
import java.util.Map;

/**
 * 记录每一条Excel数据
 * @author 陈群矜
 */
public class ExcelDataDto {

    private final Map<String, Object> primaryKeys = new HashMap<>();

    private final Map<String, Object> data = new HashMap<>();

    public ExcelDataDto() {}

    public Map<String, Object> getPrimaryKeys() {
        return primaryKeys;
    }

    public Map<String, Object> getData() {
        return data;
    }

}
