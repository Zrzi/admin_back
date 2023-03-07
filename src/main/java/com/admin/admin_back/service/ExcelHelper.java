package com.admin.admin_back.service;

import com.admin.admin_back.pojo.dto.ExcelDataDto;
import com.admin.admin_back.pojo.dto.ExcelDto;

import java.util.List;

/**
 * @author 陈群矜
 */
public interface ExcelHelper {

    void batchSave(String taskCode, ExcelDto excelDto, List<ExcelDataDto> dataList, boolean isCover);

}
