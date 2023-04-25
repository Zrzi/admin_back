package com.admin.admin_back.service;

import com.admin.admin_back.pojo.form.GetResultForm;

import java.util.List;
import java.util.Map;

/**
 * @author 陈群矜
 */
public interface DataService {

    List<Map<String, Object>> getResults(GetResultForm getResultForm);

}
