package com.mexc.dao.delegate.member;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.laile.esf.common.exception.BusinessException;
import com.laile.esf.common.exception.ResultCode;
import com.laile.esf.common.util.Page;
import com.mexc.common.plugin.SqlCondition;
import com.mexc.common.util.AddressUtil;
import com.mexc.dao.dao.IBaseDAO;
import com.mexc.dao.dao.common.MexcLoginLogDAO;
import com.mexc.dao.dao.member.MexcMemberAuthDAO;
import com.mexc.dao.dao.member.MexcMemberDAO;
import com.mexc.dao.delegate.AbstractDelegate;
import com.mexc.dao.dto.asset.AssetDto;
import com.mexc.dao.dto.member.LoginInfoDto;
import com.mexc.dao.dto.member.MemberQueryDto;
import com.mexc.dao.model.common.MexcLoginLog;
import com.mexc.dao.model.member.MexcMember;
import com.mexc.dao.model.member.MexcMemberAsset;
import com.mexc.dao.model.member.MexcMemberAuth;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by huangxinguang on 2017/11/24 下午2:41.
 */
@Component
@Transactional
public class MemberDelegate extends AbstractDelegate<MexcMember, String> {

    @Autowired
    private MexcMemberDAO mexcMemberDAO;

    @Autowired
    private MexcMemberAuthDAO mexcMemberAuthDAO;

    @Autowired
    private MexcLoginLogDAO mexcLoginLogDAO;

    @Autowired
    private MexcMemberAuthDAO authDAO;

    @Autowired
    @Override
    public void setBaseMapper(IBaseDAO<MexcMember, String> baseMapper) {
        super.setBaseMapper(baseMapper);
    }

    public Page<MexcMember> queryMemberListPage(MemberQueryDto queryDto) {
        Page<MexcMember> page = new Page<>();
        SqlCondition condition = new SqlCondition();
        condition.put(" where 1=1 ");
        if (!StringUtils.isEmpty(queryDto.getAccount())) {
            condition.put(" and account like ", "%" + queryDto.getAccount() + "%");
        }
        if (!StringUtils.isEmpty(queryDto.getStatus())) {
            condition.put(" and status=", queryDto.getStatus());
        }
        mexcMemberDAO.selectPagination(page, condition);
        return page;
    }

    public MexcMember queryMermberByAccount(String account) {
        SqlCondition condition = new SqlCondition();
        condition.put(" where account=", account);
        List<MexcMember> mexcMemberList = mexcMemberDAO.selectByCondition(condition);
        if (CollectionUtils.isNotEmpty(mexcMemberList)) {
            return mexcMemberList.get(0);
        } else {
            return null;
        }
    }

    public MexcMember queryMermberByAccountAndPwd(String account, String pwd) {
        SqlCondition condition = new SqlCondition();
        condition.put(" where account=", account);
        condition.put(" and account_pwd=", pwd);
        List<MexcMember> mexcMemberList = mexcMemberDAO.selectByCondition(condition);
        if (CollectionUtils.isNotEmpty(mexcMemberList)) {
            return mexcMemberList.get(0);
        } else {
            return null;
        }
    }


    public int updateLoginInfo(LoginInfoDto loginInfoDto) {
        String result = AddressUtil.getAddresses("ip=" + loginInfoDto.getLoginIp(), "utf-8");
        MexcLoginLog loginLog = new MexcLoginLog();
        loginLog.setAccount(loginInfoDto.getAccount());
        loginLog.setLoginIp(loginInfoDto.getLoginIp());
        loginLog.setLoginTime(loginInfoDto.getLoginTime());

        JSONObject addressObject;
        if (!result.equals("0")) {
            addressObject = JSON.parseObject(result);
            if (!StringUtils.isEmpty(addressObject.getString("code")) && addressObject.getString("code").equals("0")) {
                JSONObject dataObject = JSON.parseObject(addressObject.getString("data"));
                loginLog.setCountryId(dataObject.getString("country_id"));
                loginLog.setRegionId(dataObject.getString("region_id"));
                loginLog.setCityId(dataObject.getString("city_id"));
                loginLog.setLoginAddress(dataObject.getString("country") + "-" + dataObject.getString("region") + "-" + dataObject.getString("city"));
            }
        }
        mexcLoginLogDAO.insert(loginLog);

        MexcMember mexcMember = new MexcMember();
        mexcMember.setLastLoginIp(loginInfoDto.getLoginIp());
        mexcMember.setLastLoginTime(loginInfoDto.getLoginTime());
        mexcMember.setAccount(loginInfoDto.getAccount());
        SqlCondition sqlCondition = new SqlCondition();
        sqlCondition.put(" where account=", loginInfoDto.getAccount());
        return mexcMemberDAO.updateByParam(mexcMember, sqlCondition);
    }

    public int updateLoginInfo(String account, String ip) {
        MexcMember mexcMember = new MexcMember();
        mexcMember.setLastLoginTime(new Date());
        mexcMember.setLastLoginIp(ip);
        SqlCondition condition = new SqlCondition();
        condition.put("where account=", account);
        return mexcMemberDAO.updateByParam(mexcMember, condition);
    }


    public void finishRegister(String account) {
        MexcMember mexcMember = new MexcMember();
        mexcMember.setStatus(1);
        SqlCondition condition = new SqlCondition();
        condition.put("where account=", account);
        mexcMemberDAO.updateByParam(mexcMember, condition);
    }

    public void identityAuth(MexcMemberAuth mexcMemberAuth) {
        MexcMember mexcMember = mexcMemberDAO.selectByPrimaryKey(mexcMemberAuth.getMemberId());
        //增加认证等级
        mexcMember.setAuthLevel(mexcMember.getAuthLevel() + 1);
        mexcMemberDAO.updateByPrimaryKeySelective(mexcMember);
        //添加认证信息
        mexcMemberAuthDAO.insert(mexcMemberAuth);
    }

    public List<MexcLoginLog> queryRecentLog() {
        SqlCondition condition = new SqlCondition();
        condition.put(" order by login_time desc limit 3");
        return mexcLoginLogDAO.selectByCondition(condition);
    }


    public MexcMemberAuth queryMemberAuth(String authId) {
        SqlCondition sqlCondition = new SqlCondition();
        sqlCondition.put(" where id=", authId);
        List<MexcMemberAuth> mexcMemberAuths = authDAO.selectByCondition(sqlCondition);
        if (CollectionUtils.isNotEmpty(mexcMemberAuths)) {
            return mexcMemberAuths.get(0);
        } else {
            return null;
        }
    }


    public Page<MexcMemberAuth> queryMemberAuthPage(Integer currentPage, Integer pageSize, Integer status, String searchKey) {
        Page<MexcMemberAuth> mexcMemberAuthPage = new Page<>(currentPage, pageSize);
        SqlCondition sqlCondition = new SqlCondition();
        sqlCondition.put(" where 1=1 ");
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(searchKey)) {
            sqlCondition.put(" and account like ", "%" + searchKey + "%");
        }
        if (status != null) {
            sqlCondition.put(" and status=", status);
        }
        mexcMemberAuthDAO.selectPagination(mexcMemberAuthPage, sqlCondition);
        return mexcMemberAuthPage;
    }

    public int auditAuth(String authId, Integer status) {
        MexcMemberAuth mexcMemberAuth = new MexcMemberAuth();
        mexcMemberAuth.setId(authId);
        mexcMemberAuth.setStatus(status);
        int result = mexcMemberAuthDAO.updateByPrimaryKeySelective(mexcMemberAuth);
        if (result < 1) {
            throw new BusinessException(ResultCode.COMMON_ERROR, "审核异常,请重新尝试");
        }
        MexcMemberAuth oldMemberAuth = mexcMemberAuthDAO.selectByPrimaryKey(authId);
        MexcMember mexcMember = mexcMemberDAO.selectByPrimaryKey(oldMemberAuth.getMemberId());
        mexcMember.setAuthLevel(mexcMember.getAuthLevel() + 1);
        result = mexcMemberDAO.updateByPrimaryKeySelective(mexcMember);
        if (result < 1) {
            throw new BusinessException(ResultCode.COMMON_ERROR, "审核异常,请重新尝试");
        }
        return 1;

    }


    public List<MexcMember> queryMemberConditionAsset(String vcoinId, BigDecimal value) {
        return mexcMemberDAO.queryMemberByAssetCondition(vcoinId, value);
    }


}
