package com.admin.admin_back.service;

import com.admin.admin_back.pojo.vo.ExcelVo;

import java.util.List;

/**
 * @author 陈群矜
 */
public interface ExcelService {

    List<ExcelVo> getExcels();

    ExcelVo getExcelByExcelId(String excelId);

    void deleteExcel(String excelId);

}
