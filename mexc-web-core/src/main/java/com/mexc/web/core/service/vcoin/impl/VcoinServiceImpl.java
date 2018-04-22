package com.mexc.web.core.service.vcoin.impl;

import com.laile.esf.common.exception.BusinessException;
import com.laile.esf.common.exception.ResultCode;
import com.mexc.common.util.jedis.RedisUtil;
import com.mexc.dao.delegate.market.MarketVCoinCollectDelegate;
import com.mexc.dao.delegate.member.MemberDelegate;
import com.mexc.dao.delegate.vcoin.VCoinDelegate;
import com.mexc.dao.delegate.vcoin.VCoinFeeDelegate;
import com.mexc.dao.model.market.MexcMarketVcoinCollect;
import com.mexc.dao.model.member.MexcMember;
import com.mexc.dao.model.vcoin.MexcVCoin;
import com.mexc.dao.model.vcoin.MexcVCoinFee;
import com.mexc.match.engine.util.QueueKeyUtil;
import com.mexc.web.core.service.vcoin.IVcoinService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Class Describe
 * <p>
 * User: yangguang
 * Date: 17/12/15
 * Time: 下午6:50
 */
@Service
public class VcoinServiceImpl implements IVcoinService {
    @Resource
    VCoinDelegate vCoinDelegate;

    @Resource
    VCoinFeeDelegate vCoinFeeDelegate;

    @Resource
    MemberDelegate memberDelegate;

    @Resource
    MarketVCoinCollectDelegate marketVCoinCollectDelegate;

    @Override
    public List<MexcVCoin> queryAllCanUseVcoin() {
        return vCoinDelegate.queryCanUseVcoinList();
    }

    @Override
    public MexcVCoin queryVCoinById(String vcoinId) {
        return vCoinDelegate.selectByPrimaryKey(vcoinId);
    }

    @Override
    public int collect(MexcMarketVcoinCollect collect) {
        Boolean flag = marketVCoinCollectDelegate.checkCollect(collect.getMarketId(),collect.getVcoinId(),collect.getCreateBy());
        if(flag) {
            throw new BusinessException(ResultCode.COMMON_ERROR,"你已经收藏过");
        }
        MexcMember mexcMember = memberDelegate.queryMermberByAccount(collect.getCreateByName());
        collect.setCreateBy(mexcMember.getId());
        String userGroupKey = QueueKeyUtil.getUserGroupKey(collect.getMarketId(),collect.getVcoinId(),mexcMember.getAccount());
        RedisUtil.set(userGroupKey,"1");
        return marketVCoinCollectDelegate.insert(collect);
    }

    @Override
    public int uncollect(MexcMarketVcoinCollect collect) {
        String userGroupKey = QueueKeyUtil.getUserGroupKey(collect.getMarketId(),collect.getVcoinId(),collect.getCreateByName());
        RedisUtil.set(userGroupKey,"0");
        return marketVCoinCollectDelegate.uncollect(collect);
    }

    @Override
    public boolean checkCollect(String marketId, String vcoinId,String memberId) {
        return marketVCoinCollectDelegate.checkCollect(marketId,vcoinId,memberId);
    }

    @Override
    public MexcVCoin queryDefaultVCoin(String marketId) {
        return vCoinDelegate.queryDefaultVCoin(marketId);
    }

    @Override
    public MexcVCoinFee queryVCoinFee(String vcoinId) {
        return vCoinFeeDelegate.queryVcoinFeeByVcoinId(vcoinId);
    }
}
