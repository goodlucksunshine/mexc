package com.mexc.dao.delegate.vcoin;

import com.laile.esf.common.util.Page;
import com.mexc.common.constant.CommonConstant;
import com.mexc.common.plugin.SqlCondition;
import com.mexc.dao.dao.IBaseDAO;
import com.mexc.dao.dao.vcoin.MexcEnTrustDAO;
import com.mexc.dao.delegate.AbstractDelegate;
import com.mexc.dao.delegate.member.MemberAssetDelegate;
import com.mexc.dao.dto.order.EntrustOrderQueryDto;
import com.mexc.dao.dto.order.Match.MatchOrder;
import com.mexc.dao.dto.order.Match.MatchOrderQuery;
import com.mexc.dao.dto.order.OrderDto;
import com.mexc.dao.dto.order.OrderQueryDto;
import com.mexc.dao.dto.vcoin.UpdateEntrustDto;
import com.mexc.dao.model.vcoin.MexcEnTrust;
import com.mexc.dao.vo.vcoin.entrust.EntrustOrder;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * Class Describe
 * <p>
 * User: yangguang
 * Date: 17/12/16
 * Time: 下午3:12
 */
@Transactional
@Component
public class MexcEnTrustDelegate extends AbstractDelegate<MexcEnTrust, String> {

    @Resource
    private MexcEnTrustDAO mexcEnTrustDAO;
    @Resource
    private MemberAssetDelegate assetDelegate;

    @Autowired
    @Override
    public void setBaseMapper(IBaseDAO<MexcEnTrust, String> baseMapper) {
        super.setBaseMapper(baseMapper);
    }

    public Page<MexcEnTrust> queryEntrustOrderPage(OrderQueryDto queryDto) {
        Page<MexcEnTrust> page = new Page<>(queryDto.getCurrentPage(), queryDto.getShowCount());
        List<MexcEnTrust> list = mexcEnTrustDAO.queryEntrustOrderPage(page, queryDto);
        page.setResultList(list);
        return page;
    }

    public List<OrderDto> queryOrderList(OrderQueryDto queryDto) {
        return mexcEnTrustDAO.queryOrder(queryDto);
    }


    public List<OrderDto> queryOrderLimit(OrderQueryDto queryDto, Integer limit) {
        return mexcEnTrustDAO.queryOrderLimit(queryDto, limit);
    }

    public List<EntrustOrder> queryEntrustTradeOrderLimit(EntrustOrderQueryDto queryDto, Integer limit) {
        return mexcEnTrustDAO.queryEntrustTradeOrderLimit(queryDto, limit);
    }

    public String queryEntrustTradeOrderSum(EntrustOrderQueryDto queryDto) {
        return mexcEnTrustDAO.queryEntrustTradeOrderSum(queryDto);
    }


    public List<MatchOrder> queryMatchEntrustOrder(MatchOrderQuery matchOrderQuery) {
        return mexcEnTrustDAO.queryMatchEntrustOrder(matchOrderQuery);
    }

    /**
     * 通过交易号查询委托单
     *
     * @param tradeNo
     *
     * @return
     */
    public MexcEnTrust queryEntrust(String tradeNo) {
        SqlCondition sqlCondition = new SqlCondition();
        sqlCondition.put(" where trade_no=", tradeNo);
        List<MexcEnTrust> list = mexcEnTrustDAO.queryEntrust(tradeNo);
        if (CollectionUtils.isNotEmpty(list)) {
            return list.get(0);
        } else {
            return null;
        }
    }

    public List<MexcEnTrust> queryEntrustByMqStatus(Integer limit) {
        SqlCondition sqlCondition = new SqlCondition();
        sqlCondition.put(" where mq_status=", 0);
        sqlCondition.put(" and  status in (1,3)");
        sqlCondition.put(" order by  create_Time limit " + limit);
        return mexcEnTrustDAO.selectByCondition(sqlCondition);
    }


    /**
     * 更新委托到已完成:未交易 、部成、剩余数量 >0 才可更新数据
     *
     * @param updateEntrustDto
     *
     * @return
     */
    public int updateEntrustInfo(UpdateEntrustDto updateEntrustDto) {
        return mexcEnTrustDAO.updateEntrustInfo(updateEntrustDto);
    }

    /**
     * 更新委托到撤销/部分交易:未交易 、部成、剩余数量 >0 才可撤销
     *
     * @param enTrust
     *
     * @return
     */
    public int updateEntrustToCancel(MexcEnTrust enTrust) {
        return mexcEnTrustDAO.updateEntrustToCancel(enTrust);
    }


    public int updateMqStatus(String tradeNo) {
        return mexcEnTrustDAO.updateMqStatus(tradeNo);
    }


    public void entrustOrder(MexcEnTrust enTrust, String assetId) {
        insertSelective(enTrust);
        if (enTrust.getTradeType() == CommonConstant.BUY) {//买：冻结主币资产
            BigDecimal frozenAmount = enTrust.getTradeTotalAmount();
            assetDelegate.frozenAmount(assetId, frozenAmount);
        } else if (enTrust.getTradeType() == CommonConstant.SELL) {//卖：冻结交易币资产
            BigDecimal frozenAmount = enTrust.getTradeNumber();
            assetDelegate.frozenAmount(assetId, frozenAmount);
        }
    }
}
