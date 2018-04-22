package com.mexc.admin.core.service.activity.impl;

import com.laile.esf.common.exception.BusinessException;
import com.laile.esf.common.exception.ResultCode;
import com.laile.esf.common.util.Page;
import com.mexc.admin.core.service.activity.IActivityService;
import com.mexc.common.util.UUIDUtil;
import com.mexc.dao.delegate.activity.ActivityCandiesDelegate;
import com.mexc.dao.delegate.activity.ActivityDelegate;
import com.mexc.dao.delegate.member.MemberAssetDelegate;
import com.mexc.dao.delegate.member.MemberDelegate;
import com.mexc.dao.delegate.vcoin.VCoinDelegate;
import com.mexc.dao.dto.activity.ActivityRuleDto;
import com.mexc.dao.dto.activity.ActivityRuleSearchDto;
import com.mexc.dao.dto.activity.ActivitySearchDto;
import com.mexc.dao.model.activity.MexcActivity;
import com.mexc.dao.model.activity.MexcActivityCandies;
import com.mexc.dao.model.member.MexcMember;
import com.mexc.dao.model.member.MexcMemberAsset;
import com.mexc.dao.model.vcoin.MexcVCoin;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Class Describe
 * <p>
 * User: yangguang
 * Date: 2018/2/7
 * Time: 下午4:54
 */
@Component
public class ActivityServiceImpl implements IActivityService {
    private static final Logger logger = LoggerFactory.getLogger(ActivityServiceImpl.class);
    @Resource
    ActivityDelegate activityDelegate;
    @Resource
    ActivityCandiesDelegate candiesDelegate;
    @Resource
    MemberDelegate memberDelegate;
    @Resource
    MemberAssetDelegate memberAssetDelegate;
    @Resource
    VCoinDelegate vCoinDelegate;

    private Executor executor = Executors.newFixedThreadPool(15);

    @Override
    public Page<MexcActivity> queryActivityPage(ActivitySearchDto searchDto) {
        Page<MexcActivity> page = new Page<>(searchDto.getCurrentPage(), searchDto.getShowCount());
        activityDelegate.selectPagination(page, null);
        return page;
    }

    public MexcActivity queryActivityDetail(String activityId) {
        return activityDelegate.selectByPrimaryKey(activityId);
    }


    public void saveOrUpdate(MexcActivity activity) {
        if (StringUtils.isEmpty(activity.getId())) {
            activity.setId(UUIDUtil.get32UUID());
            activity.setCreateDate(new Date());
            activity.setActivityStatus(0);
            activityDelegate.insertSelective(activity);
        } else {
            activityDelegate.updateByPrimaryKeySelective(activity);
        }
    }

    public Page<ActivityRuleDto> queryRulePage(ActivityRuleSearchDto searchDto) {
        Page<ActivityRuleDto> page = new Page<>(searchDto.getCurrentPage(), searchDto.getShowCount());
        candiesDelegate.queryByActivityId(page, searchDto);
        return page;
    }


    public void saveOrUpdateRule(MexcActivityCandies candies) {
        if (candies.getId() == null) {
            candiesDelegate.insertSelective(candies);
        } else {
            candiesDelegate.updateByPrimaryKeySelective(candies);
        }
    }

    public MexcActivityCandies queryRuleDetail(Integer ruleId) {
        return candiesDelegate.selectByPrimaryKey(ruleId);
    }

    @Override
    public boolean execActivityRule(Integer ruleId) {
        MexcActivityCandies candies = candiesDelegate.selectByPrimaryKey(ruleId);
        if (candies.getExecStatus() == 1) {
            throw new BusinessException(ResultCode.COMMON_ERROR, "任务还在执行中,请勿重复执行");
        }
        candies.setExecStatus(1);
        candiesDelegate.updateByPrimaryKeySelective(candies);
        execSendCandies(candies);
        return true;
    }


    private void execSendCandies(MexcActivityCandies candies) {
        executor.execute(() -> {
            try {
                List<MexcMember> mexcMemberList = memberDelegate.queryMemberConditionAsset(candies.getSourceId(), BigDecimal.valueOf(candies.getRuleCondition()));
                if (CollectionUtils.isNotEmpty(mexcMemberList)) {
                    for (MexcMember mexcMember : mexcMemberList) {
                        MexcMemberAsset sourceAsset = memberAssetDelegate.selectMemberAssetByVcoin(candies.getSourceId(), mexcMember.getId());
                        BigDecimal sendAmount = sourceAsset.getBalanceAmount().multiply(candies.getPercent());
                        MexcVCoin mexcVCoin = vCoinDelegate.selectByPrimaryKey(candies.getPercentId());
                        MexcMemberAsset mexcMemberAsset = memberAssetDelegate.selectMemberAssetByVcoin(candies.getPercentId(), mexcMember.getId());
                        if (mexcMemberAsset == null) {
                            mexcMemberAsset = new MexcMemberAsset();
                            mexcMemberAsset.setId(UUIDUtil.get32UUID());
                            mexcMemberAsset.setUpdateBy("system");
                            mexcMemberAsset.setUpdateTime(new Date());
                            mexcMemberAsset.setCreateBy("system");
                            mexcMemberAsset.setCreateTime(new Date());
                            mexcMemberAsset.setTotalAmount(sendAmount);
                            mexcMemberAsset.setBalanceAmount(sendAmount);
                            mexcMemberAsset.setToken(mexcVCoin.getVcoinToken());
                            mexcMemberAsset.setVcoinId(mexcVCoin.getId());
                            mexcMemberAsset.setMemberId(mexcMember.getId());
                            mexcMemberAsset.setAccount(mexcMember.getAccount());
                            memberAssetDelegate.insertSelective(mexcMemberAsset);
                        } else {
                            memberAssetDelegate.candiesSend(mexcMemberAsset.getId(), sendAmount, String.valueOf(candies.getId()));
                        }
                    }
                }
                candies.setExecStatus(2);
                candiesDelegate.updateByPrimaryKeySelective(candies);
                return;
            } catch (Exception e) {
                logger.error("发送糖果执行失败", e);
                candies.setExecStatus(-1);
                candiesDelegate.updateByPrimaryKeySelective(candies);
            }
        });
    }


}
