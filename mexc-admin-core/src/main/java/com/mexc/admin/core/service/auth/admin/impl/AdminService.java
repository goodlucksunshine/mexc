package com.mexc.admin.core.service.auth.admin.impl;

import com.laile.esf.common.util.MD5Util;
import com.laile.esf.common.util.Page;
import com.mexc.admin.core.service.auth.admin.IAdminService;
import com.mexc.admin.core.service.impl.AbstractService;
import com.mexc.common.exception.TipsException;
import com.mexc.dao.delegate.AbstractDelegate;
import com.mexc.dao.delegate.auth.admin.AdminDelegate;
import com.mexc.dao.dto.admin.AdminDto;
import com.mexc.dao.model.auth.admin.Admin;
import com.mexc.dao.vo.admin.AdminVo;
import com.mexc.security.cipher.CipherHelper;
import com.mexc.security.constant.SecurityConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by huangxinguang on 2017/9/26 下午3:14.
 */
@Service
public class AdminService extends AbstractService<Admin,Integer> implements IAdminService {

    @Autowired
    private AdminDelegate adminDelegate;

    @Override
    public Page<AdminVo> queryAdminListPage(Integer currentPage, Integer showCount, String searchKey) {
        return adminDelegate.queryAdminListPage(currentPage,showCount,searchKey);
    }

    @Override
    public void saveOrUpdate(AdminDto adminDto) {
        if(adminDto.getId() == null) {
            Boolean exist = adminDelegate.checkNameExist(adminDto.getAdminName());
            if(exist) {
                throw new TipsException("用户名已经被使用");
            }
            adminDelegate.saveAdmin(adminDto);
        }else {
            adminDelegate.updateAdmin(adminDto);
        }
    }

    @Override
    public void delete(Integer id) {
        adminDelegate.deleteAdmin(id);
    }

    @Override
    public void changePwd(Integer id, String password, String newPassword) {
        Admin admin = adminDelegate.selectByPrimaryKey(id);
        if(!admin.getPassword().equalsIgnoreCase(CipherHelper.encryptPassword(password, SecurityConstant.SOLT))) {
            throw new TipsException("原密码不正确");
        }else {
            Admin newAdmin = new Admin();
            newAdmin.setId(admin.getId());
            newAdmin.setUpdatePwdCount(admin.getUpdatePwdCount()+1);
            newAdmin.setPassword(CipherHelper.encryptPassword(newPassword, SecurityConstant.SOLT));
            adminDelegate.updateByPrimaryKeySelective(admin);
        }
    }


    @Override
    public Admin getAdmin(String name) {
        return adminDelegate.getAdmin(name);
    }

    @Override
    public Admin login(String name, String password) {
        return adminDelegate.login(name,password);
    }

    @Autowired
    @Override
    public void setBaseDelegate(AbstractDelegate<Admin, Integer> baseDelegate) {
        super.setBaseDelegate(baseDelegate);
    }
}
