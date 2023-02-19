package com.admin.admin_back.controller;

import com.admin.admin_back.annotations.CheckRole;
import com.admin.admin_back.pojo.Result;
import com.admin.admin_back.pojo.common.ResponseMessage;
import com.admin.admin_back.pojo.enums.ResourceTypeEnum;
import com.admin.admin_back.pojo.exception.*;
import com.admin.admin_back.pojo.form.DeleteResourceForm;
import com.admin.admin_back.pojo.form.ResourceForm;
import com.admin.admin_back.pojo.vo.ResourceVo;
import com.admin.admin_back.service.ResourceService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 *
 * @author 陈群矜
 */
@RestController
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @CheckRole("checkAuthority")
    @GetMapping("/checkAuthority")
    public Result<Boolean> checkAuthority(@RequestParam(value = "resourceId", required = false) String resourceId) {
        if (StringUtils.isBlank(resourceId)) {
            return new Result<>(ResponseMessage.SUCCESS, false);
        }
        try {
            return new Result<>(ResponseMessage.SUCCESS, resourceService.checkAuthority(resourceId));
        } catch (RuntimeException exception) {
            return new Result<>(ResponseMessage.SUCCESS, false);
        }
    }

    /**
     * 不受Aop控制，提供给其它系统使用，因此需要提供userNo
     * @param userNo 用户名
     * @param resourceId 资源编码
     * @return 判断用户是否有权访问这项资源
     */
    @GetMapping("/checkAuthorityWithUserNoAndResourceId")
    public Result<Boolean> checkAuthorityWithUserNoAndResourceId(@RequestParam(value = "userNo", required = false) String userNo,
                                                                 @RequestParam(value = "resourceId", required = false) String resourceId) {
        if (StringUtils.isBlank(userNo)) {
            return new Result<>(ResponseMessage.SUCCESS, false);
        }
        if (StringUtils.isBlank(resourceId)) {
            return new Result<>(ResponseMessage.SUCCESS, false);
        }
        try {
            return new Result<>(ResponseMessage.SUCCESS, resourceService.checkAuthorityWithUserNoAndResourceId(userNo, resourceId));
        } catch (RuntimeException exception) {
            return new Result<>(ResponseMessage.SUCCESS, false);
        }
    }

    /**
     * 提供给其它系统使用
     * @param userNo 用户名
     * @param resourceType 资源类型
     * @return ResourveVo列表
     */
    @GetMapping("/getResourceWithUserNoAndResourceType")
    public Result<List<?>> getResourceWithUserNoAndResourceType(@RequestParam(value = "userNo", required = false) String userNo,
                                                                @RequestParam(value = "resourceType", required = false) String resourceType) {
        if (StringUtils.isBlank(userNo)) {
            return new Result<>(ResponseMessage.SUCCESS, new ArrayList<>());
        }
        if (StringUtils.isBlank(resourceType)) {
            return new Result<>(ResponseMessage.SUCCESS, new ArrayList<>());
        }
        ResourceTypeEnum resourceTypeEnum = ResourceTypeEnum.findResourceTypeEnumByMessage(resourceType);
        if (Objects.isNull(resourceTypeEnum)) {
            return new Result<>(ResponseMessage.SUCCESS, new ArrayList<>());
        }
        try {
            return new Result<>(ResponseMessage.SUCCESS, resourceService.getResourceByUserNoAndResourceType(userNo, resourceTypeEnum));
        } catch (RuntimeException exception) {
            return new Result<>(ResponseMessage.SYSTEM_ERROR, new ArrayList<>());
        }
    }

//    @CheckRole("getResourcesCount")
//    @GetMapping("/resource/count")
//    public Result<Integer> getResourcesCount(@RequestParam("systemId") String systemId) {
//        return new Result<>(ResponseMessage.SUCCESS, resourceService.getResourcesCount(systemId));
//    }

    @CheckRole("getResourcesBySystemId")
    @GetMapping("/resource/get")
    public Result<?> getResourcesBySystemId(@RequestParam("systemId") String systemId,
                                                           @RequestParam(value = "start", required = false) Integer start,
                                                           @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        try {
            if (start == null || start < 0) {
                start = 0;
            }
            if (pageSize == null || pageSize < 0) {
                pageSize = 10;
            }
            List<ResourceVo> resourceVos = resourceService.getResourcesPageBySystemId(systemId, start, pageSize);
            Integer total = resourceService.getResourcesCount(systemId);
            Map<String, Object> map = new HashMap<String, Object>(){{
                put("resources", resourceVos);
                put("total", total);
            }};
            return new Result<>(ResponseMessage.SUCCESS, map);
        } catch (BaseException exception) {
            return new Result<>(ResponseMessage.RESOURCE_NOT_FOUND);
        }
    }

    @CheckRole("getResourceById")
    @GetMapping("/resource/getById")
    public Result<?> getResourceById(@RequestParam("resourceId") String resourceId) {
        try {
            return new Result<>(ResponseMessage.SUCCESS, resourceService.getResourceById(resourceId));
        } catch (ResourceExistException exception) {
            return new Result<>(ResponseMessage.RESOURCE_NOT_FOUND);
        }
    }

    @CheckRole("addResource")
    @PostMapping("/resource/post")
    public Result<?> addResource(@RequestBody ResourceForm resourceForm) {
        String flag = checkValidResourceForm(resourceForm, false);
        if (StringUtils.isNotBlank(flag)) {
            return new Result<>(ResponseMessage.RESOURCE_FORM_ERROR, flag);
        }
        try {
            resourceService.addResource(resourceForm);
            return new Result<>(ResponseMessage.SUCCESS);
        } catch (ResourceNameExistException exception) {
            return new Result<>(ResponseMessage.RESOURCE_NAME_EXISTS);
        } catch (SystemExistException exception) {
            return new Result<>(ResponseMessage.SYSTEM_NOT_FOUNT);
        } catch (ResourceParentExistException exception) {
            return new Result<>(ResponseMessage.PARENT_RESOURCE_NOT_FOUND);
        } catch (ResourceTypeException exception) {
            return new Result<>(ResponseMessage.RESOURCE_TYPE_NOT_EQUAL);
        }
    }

    @CheckRole("updateResource")
    @PostMapping("/resource/update")
    public Result<?> updateResource(@RequestBody ResourceForm resourceForm) {
        String flag = checkValidResourceForm(resourceForm, true);
        if (StringUtils.isNotBlank(flag)) {
            return new Result<>(ResponseMessage.RESOURCE_FORM_ERROR, flag);
        }
        try {
            resourceService.updateResource(resourceForm);
            return new Result<>(ResponseMessage.SUCCESS);
        } catch (ResourceExistException exception) {
            return new Result<>(ResponseMessage.RESOURCE_NOT_FOUND);
        } catch (ResourceNameExistException exception) {
            return new Result<>(ResponseMessage.RESOURCE_NAME_EXISTS);
        } catch (SystemExistException exception) {
            return new Result<>(ResponseMessage.SYSTEM_NOT_FOUNT);
        } catch (ResourceParentExistException exception) {
            return new Result<>(ResponseMessage.PARENT_RESOURCE_NOT_FOUND);
        } catch (ResourceTypeException exception) {
            return new Result<>(ResponseMessage.RESOURCE_TYPE_NOT_EQUAL);
        }
    }

    @CheckRole("deleteResource")
    @PostMapping("/resource/delete")
    public Result<?> deleteResource(@RequestBody DeleteResourceForm deleteResourceForm) {
        String resourceId = deleteResourceForm.getResourceId();
        if (StringUtils.isBlank(resourceId)) {
            return new Result<>(ResponseMessage.RESOURCE_ID_NOT_FOUND);
        }
        try {
            resourceService.deleteResourceByResourceId(resourceId);
            return new Result<>(ResponseMessage.SUCCESS);
        } catch (ResourceExistException exception) {
            return new Result<>(ResponseMessage.RESOURCE_NOT_FOUND);
        } catch (ResourceChildExistException exception) {
            return new Result<>(ResponseMessage.RESOURCE_CHILD_EXIST);
        }
    }

    private String checkValidResourceForm(ResourceForm resourceForm, boolean isUpdate) {
        if (isUpdate) {
            // 检查传入的resourceId
            String resourceId = resourceForm.getResourceId();
            if (StringUtils.isBlank(resourceId)) {
                return "资源编码为空";
            }
            resourceForm.setResourceId(resourceId.trim());
        } else {
            // 检查传入的systemId
            String systemId = resourceForm.getSystemId();
            if (StringUtils.isBlank(systemId)) {
                return "系统编码为空";
            }
            resourceForm.setSystemId(systemId.trim());
        }
        String resourceName = resourceForm.getResourceName();
        if (StringUtils.isBlank(resourceName)) {
            return "资源名称为空";
        }
        resourceName = resourceName.trim();
        if (resourceName.length() > 50) {
            return "资源名称长度大于50个字符";
        }
        resourceForm.setResourceName(resourceName);
        String resourceUrl = resourceForm.getResourceUrl();
        if (StringUtils.isBlank(resourceUrl)) {
            return "资源路径为空";
        }
        resourceForm.setResourceUrl(resourceUrl.trim());
        String resourceType = resourceForm.getResourceType();
        ResourceTypeEnum typeEnum = ResourceTypeEnum.findResourceTypeEnumByMessage(resourceType);
        if (Objects.isNull(typeEnum)) {
            return "资源类型错误";
        }
        resourceForm.setResourceType(resourceType);
        return "";
    }

}
