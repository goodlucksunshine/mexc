package com.mexc.dao.delegate.auth.admin;

import com.laile.esf.common.util.DateUtil;
import com.laile.esf.common.util.Page;
import com.mexc.common.exception.TipsException;
import com.mexc.common.plugin.SqlCondition;
import com.mexc.dao.dao.IBaseDAO;
import com.mexc.dao.dao.auth.admin.AdminDAO;
import com.mexc.dao.dao.auth.admin.AdminRoleDAO;
import com.mexc.dao.delegate.AbstractDelegate;
import com.mexc.dao.dto.admin.AdminDto;
import com.mexc.dao.model.auth.admin.Admin;
import com.mexc.dao.model.auth.admin.AdminRole;
import com.mexc.dao.vo.admin.AdminVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by huangxinguang on 2017/9/26 下午3:05.
 */
@Transactional
@Component
public class AdminDelegate extends AbstractDelegate<Admin,Integer> {

    @Autowired
    private AdminDAO adminDAO;

    @Autowired
    private AdminRoleDAO adminRoleDAO;

    @Autowired
    @Override
    public void setBaseMapper(IBaseDAO<Admin, Integer> baseMapper) {
        super.setBaseMapper(baseMapper);
    }

    public Page<AdminVo> queryAdminListPage(Integer currentPage, Integer showCount, String searchKey) {
        Page<AdminVo> page = new Page<>(currentPage,showCount);
        List<AdminVo> list = adminDAO.queryAdminListPage(page,searchKey);
        page.setResultList(list);
        return page;
    }

    public void saveAdmin(AdminDto adminDto) {
        Admin admin = new Admin();
        BeanUtils.copyProperties(adminDto,admin);
        adminDAO.insert(admin);

        AdminRole adminRole = new AdminRole();
        adminRole.setAdminId(admin.getId());
        adminRole.setCreateBy(adminDto.getCreateBy());
        adminRole.setCreateTime(adminDto.getCreateTime());
        adminRole.setRoleId(adminDto.getRoleId());
        adminRole.setUpdateBy(adminDto.getCreateBy());
        adminRole.setUpdateTime(adminDto.getCreateTime());
        adminRoleDAO.insert(adminRole);
    }

    public void updateAdmin(AdminDto adminDto) {
        Admin admin = new Admin();
        BeanUtils.copyProperties(adminDto,admin);
        adminDAO.updateByPrimaryKeySelective(adminDto);

        AdminRole adminRole = new AdminRole();
        adminRole.setAdminId(admin.getId());
        adminRole.setRoleId(adminDto.getRoleId());

        SqlCondition condition = new SqlCondition();
        condition.put(" where admin_id=",adminDto.getId());
        adminRoleDAO.updateByParam(adminRole,condition);
    }

    public void deleteAdmin(Integer id) {
        adminDAO.deleteByPrimaryKey(id);

        SqlCondition condition = new SqlCondition();
        condition.put(" where admin_id=",id);
        adminRoleDAO.deleteByParam(condition);
    }

    public Admin getAdmin(String name) {
        SqlCondition condition = new SqlCondition();
        condition.put(" where admin_name=",name);
        List<Admin> adminList = adminDAO.selectByCondition(condition);
        if(!CollectionUtils.isEmpty(adminList)) {
            return adminList.get(0);
        }
        return null;
    }

    public Boolean checkNameExist(String name) {
        SqlCondition condition = new SqlCondition();
        condition.put(" where admin_name=",name);
        List<Admin> adminList = adminDAO.selectByCondition(condition);
        if(!CollectionUtils.isEmpty(adminList)) {
            return true;
        }
        return false;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Admin login(String name,String password) {
        Admin admin = this.getAdmin(name);
        if(admin.getLimitCount().intValue() - admin.getLoginErrorCount().intValue() == 0) {
            throw new TipsException("当天密码错误次数超过限制，请24小时之后重试");
        }

        if (!StringUtils.equals(password, admin.getPassword())) {
            if (admin.getLastLoginTime() == null || !DateUtil.isSameDay(DateUtil.parse(DateUtil.format(admin.getLastLoginTime(), DateUtil.YYYY_MM_DD)),
                    DateUtil.fomatDate(DateUtil.getDay()))) {
                    admin.setLoginErrorCount(admin.getLoginErrorCount() * 0+ 1);
                    adminDAO.addLoginErrorTime(admin.getAdminName(), 1);
            } else {
                admin.setLoginErrorCount(admin.getLoginErrorCount() + 1);
                adminDAO.addLoginErrorTime(admin.getAdminName(), 1);
            }
            throw new TipsException("用户名/密码错误,当天可尝试登录剩余次数[" + (admin.getLimitCount() - admin.getLoginErrorCount()) + "]次");
        } else {
            adminDAO.resetLoginErrorTime(admin.getAdminName());
        }
        return admin;
    }
}
