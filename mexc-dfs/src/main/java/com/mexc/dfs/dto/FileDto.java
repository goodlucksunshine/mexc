package com.mexc.dfs.dto;

import java.io.File;
import java.io.InputStream;

/**
 * Class Describe
 * <p>
 * User: yangguang
 * Date: 17/7/19
 * Time: 下午4:12
 */
public class FileDto {
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 文件流
     */
    private InputStream inputStream;
    /**
     * 那个用户上传的
     */
    private String account;
    /**
     * 文件大小
     */
    private Long fileSize;
    /**
     * 文件
     */
    private File file;


    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
