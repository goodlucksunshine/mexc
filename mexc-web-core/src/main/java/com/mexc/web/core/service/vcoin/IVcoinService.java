package com.mexc.web.core.service.vcoin;

import com.mexc.dao.model.market.MexcMarketVcoinCollect;
import com.mexc.dao.model.vcoin.MexcVCoin;
import com.mexc.dao.model.vcoin.MexcVCoinFee;

import java.util.List;

/**
 * Class Describe
 * <p>
 * User: yangguang
 * Date: 17/12/15
 * Time: 下午4:17
 */
public interface IVcoinService {

    List<MexcVCoin> queryAllCanUseVcoin();

    MexcVCoin queryVCoinById(String vcoinId);

    int collect(MexcMarketVcoinCollect collect);

    int uncollect(MexcMarketVcoinCollect collect);

    boolean checkCollect(String marketId,String vcoinId,String memberId);

    /**
     * 查询市场下的币种
     * @param marketId
     * @return
     */
    MexcVCoin queryDefaultVCoin(String marketId);

    /**
     * 查询币种手续费
     * @param vcoinId
     * @return
     */
    MexcVCoinFee queryVCoinFee(String vcoinId);
}
