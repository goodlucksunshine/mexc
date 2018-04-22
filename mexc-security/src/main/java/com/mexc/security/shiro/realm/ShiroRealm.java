package com.mexc.security.shiro.realm;

import com.laile.esf.common.exception.BusinessException;
import com.mexc.security.constant.SecurityConstant;
import com.mexc.dao.delegate.auth.admin.AdminDelegate;
import com.mexc.dao.delegate.auth.resource.ResourceDelegate;
import com.mexc.dao.delegate.auth.role.RoleDelegate;
import com.mexc.dao.model.auth.admin.Admin;
import com.mexc.security.exception.SecErrCode;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author ectrip 2015-3-6
 */
public class ShiroRealm extends AuthorizingRealm {
    @Autowired
    private AdminDelegate adminDelegate;

    @Autowired
    public RoleDelegate roleDelegate;

    @Autowired
    public ResourceDelegate resourceDelegate;

    /**
     * 登录信息和用户验证信息验证(non-Javadoc)
     * 
     * @see org.apache.shiro.realm.AuthenticatingRealm#doGetAuthenticationInfo(AuthenticationToken)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String name = (String) token.getPrincipal(); // 得到用户名
        Admin admin = adminDelegate.getAdmin(name);
        if (admin == null) {
            throw new BusinessException(SecErrCode.SEC001);// 没找到帐号 也提示账号密码错误
        }
        if (admin.getStatus() == 0) {
            throw new BusinessException(SecErrCode.SEC002);// 账号被锁定
        }
        // 交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以自定义实现
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(admin.getAdminName(), // 用户名
                admin.getPassword(), // 密码
                ByteSource.Util.bytes(SecurityConstant.SOLT),
                getName() // realm name
        );
        return authenticationInfo;
    }

    /**
     * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用,负责在应用程序中决定用户的访问控制的方法(non-Javadoc)
     *
     * @see AuthorizingRealm#doGetAuthorizationInfo(PrincipalCollection)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection pc) {
        String name = (String) pc.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        Admin admin = adminDelegate.getAdmin(name);
        authorizationInfo.setRoles(roleDelegate.queryAdminRoleCodes(admin.getId()));
        authorizationInfo.setStringPermissions(resourceDelegate.queryResourceCodes(admin.getId()));
        return authorizationInfo;
    }
}
