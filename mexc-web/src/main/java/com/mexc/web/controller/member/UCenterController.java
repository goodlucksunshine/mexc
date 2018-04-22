package com.mexc.web.controller.member;

import com.google.zxing.WriterException;
import com.laile.esf.common.exception.ResultCode;
import com.laile.esf.common.exception.SystemException;
import com.mexc.common.util.google.GoogleAuthenticator;
import com.mexc.dao.dto.asset.AssetQueryDto;
import com.mexc.dao.model.common.MexcLoginLog;
import com.mexc.dao.model.member.MexcMember;
import com.mexc.dao.model.member.MexcMemberAuth;
import com.mexc.dfs.dto.FileDto;
import com.mexc.dfs.service.impl.FileService;
import com.mexc.member.dto.request.GoogleAuthRequest;
import com.mexc.member.dto.request.IdentityAuthRequest;
import com.mexc.member.dto.request.UserChangePwdRequest;
import com.mexc.member.dto.response.BaseResponse;
import com.mexc.member.dto.response.GoogleAuthBindResponse;
import com.mexc.member.dto.response.GoogleAuthInfoResponse;
import com.mexc.member.dto.response.UserChangePwdResponse;
import com.mexc.member.user.IUserService;
import com.mexc.web.controller.MemberController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * Created by huangxinguang on 2017/12/14 下午2:14.
 */
@Controller
@RequestMapping("/member/ucenter")
public class UCenterController extends MemberController {

    @Autowired
    private IUserService userService;

    @Autowired
    private FileService fileService;


    @RequestMapping("/index")
    public ModelAndView centerPage() {
        ModelAndView modelAndView = getModelAndView();
        MexcMember loginMember = userService.queryMemberByAccount(getSessionUser().getAccount());
        MexcMemberAuth idenAuth = userService.queryIdentityInfo(loginMember.getId());
        List<MexcLoginLog> logList = userService.queryRecentLoginLog();

        modelAndView.addObject("level1", userService.cashLimit().get("1"));
        modelAndView.addObject("level2", userService.cashLimit().get("2"));
        modelAndView.addObject("logList", logList);
        modelAndView.addObject("loginMember", loginMember);
        modelAndView.addObject("idenAuth", idenAuth);
        modelAndView.setViewName("/ucenter/center");
        return modelAndView;
    }

    @RequestMapping("/qrCode")
    public void qrCode(HttpServletResponse response) throws WriterException, IOException {
        response.setContentType("image/png");
        MexcMember mexcMember = userService.queryMemberByAccount(getSessionUser().getAccount());
        BufferedImage bufferedImage = GoogleAuthenticator.getQRcodeBufferedImage(mexcMember.getAccount(), "mexc.com", mexcMember.getSecondPwd());
        OutputStream stream = response.getOutputStream();
        ImageIO.write(bufferedImage, "png", stream);
        stream.flush();
        stream.close();
    }


    @RequestMapping("/googleAuthPage")
    public ModelAndView googleAuthPage() {
        ModelAndView modelAndView = getModelAndView();
        GoogleAuthInfoResponse response = userService.getGoogleAuthInfo(getSessionUser().getAccount());
        modelAndView.addObject("googleAuth", response);
        modelAndView.setViewName("/ucenter/googleauth");
        return modelAndView;
    }

    @RequestMapping("/closeGoogleAuthPage")
    public ModelAndView closeGoogleAuthPage() {
        ModelAndView modelAndView = getModelAndView();
        modelAndView.setViewName("/ucenter/closegoogleauth");
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping("/googleAuth/bind")
    public Object bind(GoogleAuthRequest googleAuthRequest) {
        googleAuthRequest.setAccount(getSessionUser().getAccount());
        GoogleAuthBindResponse googleAuthBindResponse = userService.bindGoogleAuth(googleAuthRequest);
        return googleAuthBindResponse;
    }

    @ResponseBody
    @RequestMapping("/googleAuth/unbind")
    public Object unbind(GoogleAuthRequest googleAuthRequest) {
        googleAuthRequest.setAccount(getSessionUser().getAccount());
        BaseResponse response = userService.unbindGoogleAuth(googleAuthRequest);
        return response;
    }


    @RequestMapping("/identityAuthPage")
    public ModelAndView identityAuthPage() {
        ModelAndView modelAndView = getModelAndView();
        modelAndView.setViewName("/ucenter/identityauth");
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping("/saveIdentityAuth")
    public Object identityAuth(IdentityAuthRequest identityAuthRequest, MultipartFile idCardHomeFile, MultipartFile idCardBackFile, MultipartFile idCardHandFile) {
        String account = getSessionUser().getAccount();

        if (idCardHomeFile != null && idCardHomeFile.getSize() > 0) {
            String idCardHome = this.uploadFile(account, idCardHomeFile);
            identityAuthRequest.setCardHome(idCardHome);
        }
        if (idCardBackFile != null && idCardBackFile.getSize() > 0) {
            String idCardBack = this.uploadFile(account, idCardBackFile);
            identityAuthRequest.setCardBack(idCardBack);
        }
        if (idCardHandFile != null && idCardHandFile.getSize() > 0) {
            String idCardHand = this.uploadFile(account, idCardHandFile);
            identityAuthRequest.setCardHand(idCardHand);
        }
        identityAuthRequest.setAccount(account);
        BaseResponse response = userService.identityAuth(identityAuthRequest);
        return response;
    }


    @RequestMapping("/changePwdPage")
    public ModelAndView changePwdPage() {
        ModelAndView modelAndView = getModelAndView();
        modelAndView.setViewName("/ucenter/changepwd");
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping("/changePwd")
    public Object changePwd(UserChangePwdRequest request) {
        request.setAccount(getSessionUser().getAccount());
        UserChangePwdResponse response = userService.changePwd(request);
        return response;
    }

    private String uploadFile(String account, MultipartFile file) {
        FileDto fileDto = new FileDto();
        fileDto.setFileSize(file.getSize());
        fileDto.setFileName(file.getOriginalFilename());
        fileDto.setAccount(account);
        try {
            fileDto.setInputStream(file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            throw new SystemException(ResultCode.COMMON_ERROR, "文件上传失败！");
        }
        return fileService.add(fileDto);
    }
}
