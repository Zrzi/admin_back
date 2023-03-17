package com.admin.admin_back.service;

import com.admin.admin_back.pojo.form.ExcelForm;
import com.admin.admin_back.pojo.vo.ExcelTaskVo;
import com.admin.admin_back.pojo.vo.ExcelVo;
import com.admin.admin_back.pojo.vo.GetHistoryUploadExcelResult;
import com.admin.admin_back.pojo.vo.GetSqlColumnsVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author 陈群矜
 */
public interface ExcelService {

    List<ExcelVo> getExcels();

    ExcelVo getExcelByExcelId(String excelId);

    List<String> getSqlTables();

    GetSqlColumnsVo getSqlColumns(String sqlTableName);

    void addExcel(ExcelForm excelForm);

    void updateExcel(ExcelForm excelForm);

    void deleteExcel(String excelId);

    String uploadExcel(MultipartFile file);

    ExcelTaskVo getUploadExcelResult(String taskId);

    GetHistoryUploadExcelResult getHistoryUploadExcelResult(int start, int pageSize);

//    void testAsync();

}
