package com.admin.admin_back.pojo.dto;

import java.util.HashMap;
import java.util.Map;

/**
 * 记录每一条Excel数据
 * @author 陈群矜
 */
public class ExcelDataDto {

    private final Map<String, String> primaryKeys = new HashMap<>();

    private final Map<String, String> data = new HashMap<>();

    public ExcelDataDto() {}

    public Map<String, String> getPrimaryKeys() {
        return primaryKeys;
    }

    public Map<String, String> getData() {
        return data;
    }

}
