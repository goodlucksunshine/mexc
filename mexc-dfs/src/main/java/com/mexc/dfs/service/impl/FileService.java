package com.mexc.dfs.service.impl;

import com.laile.esf.common.exception.BusinessException;
import com.laile.esf.common.exception.ResultCode;
import com.laile.esf.common.exception.SystemException;
import com.laile.esf.common.util.FileUtil;
import com.laile.esf.common.util.PrimaryUtil;
import com.laile.esf.common.util.RandomUtil;
import com.mexc.dao.dao.file.FileDAO;
import com.mexc.dao.delegate.AbstractDelegate;
import com.mexc.dfs.constant.DFSConstant;
import com.mexc.dfs.dto.FileDto;
import com.mexc.dfs.service.IFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.Date;


/**
 * Created by huangxinguang on 2017/9/28 上午11:17.
 */
@Service
public class FileService extends AbstractDelegate<com.mexc.dao.model.file.File, String> implements IFileService {

    private static Logger logger = LoggerFactory.getLogger(FileService.class);

    @Autowired
    private FileDAO fileDAO;

    public String add(FileDto fileDto) {
        String filePath = DFSConstant.COMMON_PATH;
        String relFilePath = RandomUtil.randomStr(1, RandomUtil.NUM_CHAR) + File.separator;
        String fileId = PrimaryUtil.primaryId("F");
        String suffix = FileUtil.getFileNameSuffix(fileDto.getFileName());
        filePath += relFilePath;
        try {
            FileUtil.createDir(filePath);
            FileUtil.writeFile(fileDto.getInputStream(), filePath + fileId + suffix);
        } catch (Exception e) {
            logger.error("创建文件失败", e);
            throw new SystemException(ResultCode.COMMON_ERROR, "文件上传失败");
        }
        com.mexc.dao.model.file.File record = new com.mexc.dao.model.file.File();
        if (".jpg,.png,.bmp,.jpeg".indexOf(suffix) != -1) {
            record.setFileType(1);
        } else {
            record.setFileType(2);
        }
        record.setStatus(1);
        record.setSuffix(suffix);
        record.setFilePath(relFilePath);
        record.setFileName(fileDto.getFileName());
        record.setFileSize(fileDto.getFileSize());
        record.setId(fileId);
        record.setVirtualId(record.getId());
        record.setSvrAddr(DFSConstant.FILE_SERVER);
        record.setUpdateBy(fileDto.getAccount());
        record.setUpdateTime(new Date());
        record.setCreateBy(fileDto.getAccount());
        record.setCreateTime(new Date());
        record.setUseCount(1);
        fileDAO.insert(record);
        return record.getId();
    }

    public FileDto download(String fileId) {
        FileDto fileDto = new FileDto();
        if (fileId == null || StringUtils.isEmpty(fileId)) {
            throw new BusinessException(ResultCode.COMMON_ERROR, "文件ID不能为空");
        }

        com.mexc.dao.model.file.File resFileVo = fileDAO.selectByPrimaryKey(fileId);

        if (resFileVo == null) {
            throw new BusinessException(ResultCode.COMMON_ERROR, "文件不存在");
        }

        String filePath = DFSConstant.COMMON_PATH;

        filePath += resFileVo.getFilePath() + resFileVo.getId() + resFileVo.getSuffix();

        logger.debug("FileServiceImpl:query 文件地址:{}", filePath);
        fileDto.setFileName(resFileVo.getFileName());
        fileDto.setFile(new File(filePath));
        if (!fileDto.getFile().exists()) {
            logger.error("文件不存在:{}", fileId);
        }
        return fileDto;
    }

}
