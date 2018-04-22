package com.mexc.admin.core.service.auth.admin;

import com.laile.esf.common.util.Page;
import com.mexc.admin.core.service.IBaseService;
import com.mexc.dao.dto.admin.AdminDto;
import com.mexc.dao.model.auth.admin.Admin;
import com.mexc.dao.vo.admin.AdminVo;


/**
 * Created by huangxinguang on 2017/9/26 下午3:13.
 */
public interface IAdminService extends IBaseService<Admin,Integer> {

    Page<AdminVo> queryAdminListPage(Integer currentPage, Integer showCount, String searchKey);

    void saveOrUpdate(AdminDto adminDto);

    void delete(Integer id);

    void changePwd(Integer id, String password, String newPassword);

    Admin getAdmin(String name);

    Admin login(String name, String password);
}
