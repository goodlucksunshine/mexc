package com.mexc.dfs.service;


import com.mexc.dfs.dto.FileDto;

/**
 * Created by huangxinguang on 2017/9/28 上午11:17.
 */
public interface IFileService {
    /**
     * 新增文件
     *
     * @param fileDto
     * @return
     */
    String add(FileDto fileDto);

    /**
     * 下载文件
     * @param fileId
     * @return
     */
    FileDto download(String fileId);
}
