package com.admin.admin_back.pojo.event;

import org.springframework.context.ApplicationEvent;

import java.util.Map;

/**
 * @author 陈群矜
 */
public class ExcelEvent extends ApplicationEvent {

    private final String sqlName;

    private final Map<String, Object> data;

    private final String userNo;

    private final boolean isUpdate;

    public ExcelEvent(Object source, String sqlName, Map<String, Object> data, String userNo, boolean isUpdate) {
        super(source);
        this.sqlName = sqlName;
        this.data = data;
        this.userNo = userNo;
        this.isUpdate = isUpdate;
    }

    public String getSqlName() {
        return sqlName;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public String getUserNo() {
        return userNo;
    }

    public boolean getIsUpdate() {
        return isUpdate;
    }

}
