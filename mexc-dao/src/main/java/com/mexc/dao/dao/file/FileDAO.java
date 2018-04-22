package com.mexc.dao.dao.file;

import com.mexc.dao.dao.IBaseDAO;
import com.mexc.dao.model.file.File;
import org.springframework.stereotype.Repository;


@Repository
public interface FileDAO extends IBaseDAO<File,String> {
}