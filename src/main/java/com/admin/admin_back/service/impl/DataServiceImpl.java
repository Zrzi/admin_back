package com.admin.admin_back.service.impl;

import com.admin.admin_back.mapper.DataMapper;
import com.admin.admin_back.pojo.form.GetResultForm;
import com.admin.admin_back.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author 陈群矜
 */
@Service
public class DataServiceImpl implements DataService {

    @Autowired
    private DataMapper dataMapper;

    @Override
    public List<Map<String, Object>> getResults(GetResultForm getResultForm) {
        final String sqlTableName = getResultForm.getSqlTableName();
        final List<String> columnNames = getResultForm.getSqlColumnNames();
        final Map<String, String> conditions = getResultForm.getConditions();
        return dataMapper.getResults(sqlTableName, columnNames, conditions);
    }

}
