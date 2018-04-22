package com.mexc.dao.dao.vcoin;

import com.laile.esf.common.util.Page;
import com.mexc.dao.dao.IBaseDAO;
import com.mexc.dao.dto.order.EntrustOrderQueryDto;
import com.mexc.dao.dto.order.Match.MatchOrder;
import com.mexc.dao.dto.order.Match.MatchOrderQuery;
import com.mexc.dao.dto.vcoin.UpdateEntrustDto;
import com.mexc.dao.vo.vcoin.entrust.EntrustOrder;
import com.mexc.dao.dto.order.OrderDto;
import com.mexc.dao.dto.order.OrderQueryDto;
import com.mexc.dao.model.vcoin.MexcEnTrust;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by huangxinguang on 2017/11/22 下午4:47.
 */
@Repository
public interface MexcEnTrustDAO extends IBaseDAO<MexcEnTrust, String> {


    List<OrderDto> queryOrder(@Param("query") OrderQueryDto queryDto);

    List<OrderDto> queryOrderLimit(@Param("query") OrderQueryDto queryDto, @Param("limit") Integer limit);

    List<EntrustOrder> queryEntrustTradeOrderLimit(@Param("query") EntrustOrderQueryDto queryDto, @Param("limit") Integer limit);

    String queryEntrustTradeOrderSum(@Param("query") EntrustOrderQueryDto queryDto);

    int updateEntrustInfo(@Param("upinfo") UpdateEntrustDto updateEntrustDto);

    int updateEntrustToCancel(MexcEnTrust mexcEnTrust);

    /**
     * 查询委托单
     *
     * @param page
     * @param queryDto
     *
     * @return
     */
    List<MexcEnTrust> queryEntrustOrderPage(@Param("page") Page<MexcEnTrust> page, @Param("query") OrderQueryDto queryDto);

    List<MexcEnTrust> queryEntrust(@Param("tradeNo") String tradeNo);

    /**
     * 撮合查询：买：查<=当前买价的所有 卖单 从小到大排序，状态为：未成交、部分成交
     * 卖：查>=当前卖价的所有 买单 从大到小排序，状态为：未成交、部分成交
     *
     * @param matchOrderQuery
     *
     * @return
     */
    List<MatchOrder> queryMatchEntrustOrder(@Param("matchOrderQuery") MatchOrderQuery matchOrderQuery);

    int updateMqStatus(@Param("tradeNo") String tradeNo);
}
