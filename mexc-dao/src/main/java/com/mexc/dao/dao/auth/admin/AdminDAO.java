package com.mexc.dao.dao.auth.admin;

import com.laile.esf.common.util.Page;
import com.mexc.dao.dao.IBaseDAO;
import com.mexc.dao.model.auth.admin.Admin;
import com.mexc.dao.vo.admin.AdminVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminDAO extends IBaseDAO<Admin,Integer> {

    List<AdminVo> queryAdminListPage(@Param("page") Page<AdminVo> page, @Param("searchKey") String searchKey);

    Admin login(@Param("name") String name, @Param("password") String password);

    void addLoginErrorTime(@Param("name") String name, @Param("time") Integer time);

    void resetLoginErrorTime(@Param("name") String name);
}