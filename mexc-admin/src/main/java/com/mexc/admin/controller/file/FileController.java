package com.mexc.admin.controller.file;

import com.laile.esf.common.exception.ResultCode;
import com.laile.esf.common.exception.SystemException;
import com.mexc.admin.controller.WebController;
import com.mexc.dfs.dto.FileDto;
import com.mexc.dfs.service.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@RequestMapping("/file")
@Controller
public class FileController extends WebController {

    @Autowired
    IFileService fileService;


    @RequestMapping(value = "/upload")
    public ModelAndView upload(@RequestParam(value = "file", required = false) MultipartFile file) {
        FileDto fileDto = new FileDto();
        fileDto.setFileSize(file.getSize());
        fileDto.setFileName(file.getOriginalFilename());
        fileDto.setAccount(getSessionUser().getAdminName());
        try {
            fileDto.setInputStream(file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            throw new SystemException(ResultCode.COMMON_ERROR, "文件上传失败");
        }
        String fileId = fileService.add(fileDto);
        ModelAndView modelAndView = getModelAndView();
        modelAndView.addObject("fileId", fileId);
        return modelAndView;
    }

    @RequestMapping(value = "/download/{fileId}")
    public void download(@PathVariable("fileId") String fileId, HttpServletRequest request, HttpServletResponse response) {
        FileDto fileDto = fileService.download(fileId);
        if (fileDto == null) {
            return;
        }
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition", "attachment;fileName=" + fileDto.getFileName());
        try {
            InputStream inputStream = new FileInputStream(fileDto.getFile());
            OutputStream os = response.getOutputStream();
            byte[] b = new byte[2048];
            int length;
            while ((length = inputStream.read(b)) > 0) {
                os.write(b, 0, length);
            }
            // 这里主要关闭。
            os.close();
            inputStream.close();
        } catch (FileNotFoundException e) {
            logger.error("获取文件异常:{}", fileId, e);
        } catch (IOException e) {
            logger.error("获取文件异常:{}", fileId, e);
        }
    }

}
