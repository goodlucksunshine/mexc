package com.mexc.dao.dao.wallet;

import com.mexc.dao.dao.IBaseDAO;
import com.mexc.dao.model.wallet.MexcWallet;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by huangxinguang on 2017/12/26 下午3:02.
 */
@Repository
public interface MexcWalletDAO extends IBaseDAO<MexcWallet,String> {

    int syncEthBlock(@Param("block") String block);

    int syncBtcBlock(@Param("lastHash") String lashHash);
}
