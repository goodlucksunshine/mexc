package com.mexc.admin.controller.login;

import com.mexc.admin.constant.WebConstant;
import com.mexc.admin.controller.WebController;
import com.mexc.admin.core.service.auth.admin.IAdminService;
import com.mexc.common.exception.TipsException;
import com.mexc.common.util.R;
import com.mexc.dao.model.auth.admin.Admin;
import com.mexc.security.cipher.CipherHelper;
import com.mexc.security.constant.SecurityConstant;
import com.mexc.security.utils.ShiroUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by huangxinguang on 2017/10/14 下午2:43.
 */
@Controller
@RequestMapping
public class LoginController extends WebController {

    @Autowired
    private IAdminService adminService;

    @RequestMapping(value = "/toLogin")
    public ModelAndView toLogin() {
        ModelAndView mav = getModelAndView();
        mav.setViewName("/login");
        return mav;
    }

    @ResponseBody
    @RequestMapping(value = "/loginIn")
    public Object loginIn(String adminName,String password,String captchaCode) {
        if(getCaptcha() == null) {
            throw new TipsException("验证码过期，请重新输入");
        }
        if (!((String)getCaptcha()).equalsIgnoreCase(captchaCode)) {
            throw new TipsException("验证码不正确");
        }
        Admin admin = adminService.getAdmin(adminName);
        if (admin == null) {
            throw new TipsException("用户名或密码不正确");
        }

        admin = adminService.login(admin.getAdminName(), CipherHelper.encryptPassword(password, SecurityConstant.SOLT));

        Subject subject = ShiroUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(adminName, password);
        token.setRememberMe(true);
        subject.login(token);

        Session session = getSession();
        session.setAttribute(WebConstant.SESSION_USER, admin);// 把用户信息放session中
        session.removeAttribute(WebConstant.SESSION_SECURITY_CODE);// 清除登录验证码的session
        return R.ok();
    }

    @RequestMapping(value = "/loginOut")
    public ModelAndView loginOut() {
        ModelAndView mav = getModelAndView();
        Session session = getSession(); // 以下清除session缓存
        session.removeAttribute(WebConstant.SESSION_USER);
        ShiroUtils.logout();// shiro销毁登录;
        mav.setViewName("/login");
        return mav;
    }
}
