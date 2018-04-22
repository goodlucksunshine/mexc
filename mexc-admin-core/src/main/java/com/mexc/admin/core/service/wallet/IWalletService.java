package com.mexc.admin.core.service.wallet;

import com.laile.esf.common.util.Page;
import com.mexc.admin.core.service.IBaseService;
import com.mexc.dao.model.wallet.MexcWallet;

/**
 * Created by huangxinguang on 2017/12/26 下午3:17.
 */
public interface IWalletService extends IBaseService<MexcWallet,String> {

    Page<MexcWallet> queryWalletListPage(Integer currentPage, Integer showCount, String searchKey);

    void saveOrUpdate(MexcWallet wallet);

    void delete(String id);
}
