package com.mexc.admin.controller;

import com.laile.esf.common.exception.ResultCode;
import com.laile.esf.common.exception.SystemException;
import com.mexc.admin.constant.WebConstant;
import com.mexc.common.base.BaseController;
import com.mexc.dao.model.auth.admin.Admin;
import com.mexc.dfs.dto.FileDto;
import com.mexc.dfs.service.IFileService;
import com.mexc.security.utils.ShiroUtils;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by huangxinguang on 2017/11/21 下午2:54.
 */
public class WebController extends BaseController {
    protected static final Logger logger = LoggerFactory.getLogger(BaseController.class);

    @Autowired
    private IFileService fileService;

    public Admin getSessionUser() {
        return (Admin) ShiroUtils.getSessionAttribute(WebConstant.SESSION_USER);
    }


    public Session getSession() {
        return ShiroUtils.getSession();
    }

    public Object getCaptcha() {
        return ShiroUtils.getSessionAttribute(WebConstant.SESSION_SECURITY_CODE);
    }


    protected String uploadFile(Admin admin, MultipartFile file) {
        FileDto fileDto = new FileDto();
        fileDto.setFileSize(file.getSize());
        fileDto.setFileName(file.getOriginalFilename());
        fileDto.setAccount(admin.getAdminName());
        try {
            fileDto.setInputStream(file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            throw new SystemException(ResultCode.COMMON_ERROR, "文件上传失败！");
        }
        return fileService.add(fileDto);
    }
}
