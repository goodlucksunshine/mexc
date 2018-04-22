package com.mexc.admin.core.service.wallet.impl;

import com.laile.esf.common.util.Page;
import com.mexc.admin.core.service.impl.AbstractService;
import com.mexc.admin.core.service.wallet.IWalletService;
import com.mexc.dao.delegate.AbstractDelegate;
import com.mexc.dao.delegate.wallet.WalletDelegate;
import com.mexc.dao.model.wallet.MexcWallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by huangxinguang on 2017/12/26 下午3:17.
 */
@Service
public class WalletService extends AbstractService<MexcWallet,String> implements IWalletService {

    @Autowired
    private WalletDelegate walletDelegate;

    @Autowired
    @Override
    public void setBaseDelegate(AbstractDelegate<MexcWallet, String> baseDelegate) {
        super.setBaseDelegate(baseDelegate);
    }

    @Override
    public Page<MexcWallet> queryWalletListPage(Integer currentPage, Integer showCount, String searchKey) {
        return walletDelegate.queryWalletListPage(currentPage,showCount,searchKey);
    }

    @Override
    public void saveOrUpdate(MexcWallet wallet) {
        walletDelegate.saveOrUpdate(wallet);
    }

    @Override
    public void delete(String id) {
        walletDelegate.delete(id);
    }


}
