package com.admin.admin_back.controller;

import com.admin.admin_back.annotations.CheckRole;
import com.admin.admin_back.pojo.Result;
import com.admin.admin_back.pojo.common.ResponseMessage;
import com.admin.admin_back.pojo.enums.ResourceTypeEnum;
import com.admin.admin_back.pojo.exception.*;
import com.admin.admin_back.pojo.form.ResourceForm;
import com.admin.admin_back.pojo.vo.ResourceVo;
import com.admin.admin_back.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 *
 * @author 陈群矜
 */
@RestController
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @CheckRole("getResourcesBySystemId")
    @GetMapping("/resource/get")
    public Result<List<ResourceVo>> getResourcesBySystemId(@RequestParam("systemId") String systemId) {
        try {
            List<ResourceVo> resourceVos = resourceService.getResourcesBySystemId(systemId);
            return new Result<>(ResponseMessage.SUCCESS, resourceVos);
        } catch (BaseException exception) {
            return new Result<>(ResponseMessage.RESOURCE_NOT_FOUND);
        }
    }

    @CheckRole("addResource")
    @PostMapping("/resource/post")
    public Result<?> addResource(@RequestBody ResourceForm resourceForm) {
        String flag = checkValidResourceForm(resourceForm, false);
        if (StringUtils.hasLength(flag)) {
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
        if (StringUtils.hasLength(flag)) {
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
    public Result<?> deleteResource(@RequestParam("resourceId") String resourceId) {
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
            if (StringUtils.isEmpty(resourceId)) {
                return "资源编码为空";
            }
            resourceForm.setResourceId(resourceId.trim());
        } else {
            // 检查传入的systemId
            String systemId = resourceForm.getSystemId();
            if (StringUtils.isEmpty(systemId)) {
                return "系统编码为空";
            }
            resourceForm.setSystemId(systemId.trim());
        }
        String resourceName = resourceForm.getResourceName();
        if (StringUtils.isEmpty(resourceName)) {
            return "资源名称为空";
        }
        resourceName = resourceName.trim();
        if (resourceName.length() > 50) {
            return "资源名称长度大于50个字符";
        }
        resourceForm.setResourceName(resourceName);
        String resourceUrl = resourceForm.getResourceUrl();
        if (StringUtils.isEmpty(resourceUrl)) {
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
