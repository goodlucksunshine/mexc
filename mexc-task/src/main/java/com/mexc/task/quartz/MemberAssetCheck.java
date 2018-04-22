package com.mexc.task.quartz;

import com.mexc.dao.delegate.member.MemberAssetCheckDelegate;
import com.mexc.dao.util.mail.EmailSessionFactory;
import com.mexc.dao.util.mail.MailSender;
import com.mexc.dao.util.mail.entity.EmailEntity;
import com.mexc.dao.vo.wallet.MemberAssetCheckVo;
import org.springframework.beans.factory.annotation.Autowired;

import javax.mail.Session;
import java.util.List;

public class MemberAssetCheck {

    @Autowired
    private MemberAssetCheckDelegate memberAssetCheckDelegate;


    public void  memberAssetCheck(){
       /* List<MemberAssetCheckVo> memberExceptionList = memberAssetCheckDelegate.memberAssetCheck();
        if (memberExceptionList == null || memberExceptionList.size() == 0){
            return ;
        }*/
    }
}
