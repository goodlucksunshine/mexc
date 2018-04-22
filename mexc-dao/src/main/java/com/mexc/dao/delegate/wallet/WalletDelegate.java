package com.mexc.dao.delegate.wallet;

import com.laile.esf.common.util.Page;
import com.mexc.common.plugin.SqlCondition;
import com.mexc.common.util.ThressDescUtil;
import com.mexc.common.util.UUIDUtil;
import com.mexc.dao.dao.IBaseDAO;
import com.mexc.dao.dao.wallet.MexcWalletDAO;
import com.mexc.dao.delegate.AbstractDelegate;
import com.mexc.dao.model.wallet.MexcWallet;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huangxinguang on 2017/12/26 下午3:05.
 */
@Transactional
@Component
public class WalletDelegate extends AbstractDelegate<MexcWallet, String> {

    @Autowired
    private MexcWalletDAO walletDAO;

    @Autowired
    @Override
    public void setBaseMapper(IBaseDAO<MexcWallet, String> baseMapper) {
        super.setBaseMapper(baseMapper);
    }

    public Page<MexcWallet> queryWalletListPage(Integer currentPage, Integer showCount, String searchKey) {
        Page<MexcWallet> page = new Page<>(currentPage, showCount);
        SqlCondition condition = new SqlCondition();
        if (StringUtils.isNotEmpty(searchKey)) {
            condition.put(" where name like ", "%" + searchKey + "%");
        }
        List<MexcWallet> list = walletDAO.selectPagination(page, condition);
        page.setResultList(list);
        return page;
    }

    public void saveOrUpdate(MexcWallet wallet) {
        if (StringUtils.isNotEmpty(wallet.getHotAddress())) {
            String hotAddress = ThressDescUtil.encodeHotAddress(wallet.getHotAddress());
            wallet.setHotAddress(hotAddress);
        }
        if (StringUtils.isNotEmpty(wallet.getColdAddress())) {
            String coldAddress = ThressDescUtil.encodeClodAddress(wallet.getColdAddress());
            wallet.setColdAddress(coldAddress);
        }
        String hotPwd = ThressDescUtil.encodeHotPwd(wallet.getHotPwd());
        String walletPwd = ThressDescUtil.encodeHotPwd(wallet.getWalletPwd());
        wallet.setHotPwd(hotPwd);
        wallet.setWalletPwd(walletPwd);
        if (StringUtils.isNotEmpty(wallet.getId())) {
            walletDAO.updateByPrimaryKeySelective(wallet);
        } else {
            wallet.setId(UUIDUtil.get32UUID());
            walletDAO.insert(wallet);
        }
    }


    public List<Map> queryWalletByType(String type) {
        SqlCondition condition = new SqlCondition();
        condition.put("where type=", type);
        condition.put(" and status=1");
        List<MexcWallet> walletList = walletDAO.selectByCondition(condition);
        if (CollectionUtils.isEmpty(walletList)) {
            return null;
        }
        List<Map> infolist = new ArrayList<>();
        for (MexcWallet wallet : walletList) {
            Map<String, String> walletInfo = new HashMap<>();
            walletInfo.put("id", wallet.getId());
            walletInfo.put("ip", wallet.getUrl());
            walletInfo.put("port", wallet.getRpcPort());
            walletInfo.put("sockPort", wallet.getSocketPort());
            walletInfo.put("hotAddress", ThressDescUtil.decodeHotAddress(wallet.getHotAddress()));
            walletInfo.put("hotPwd", ThressDescUtil.decodeHotPwd(wallet.getHotPwd()));
            walletInfo.put("hotFile", wallet.getHotFile());
            walletInfo.put("coldAddress", ThressDescUtil.decodeClodAddress(wallet.getColdAddress()));
            walletInfo.put("lastTransOffset", wallet.getLastSyncTransHash());
            walletInfo.put("lastSync", wallet.getBlockSync());
            walletInfo.put("user", wallet.getWalletUser());
            if (type.equalsIgnoreCase("btc")) {
                walletInfo.put("pwd", ThressDescUtil.decodeHotPwd(wallet.getWalletPwd()));
            }
            infolist.add(walletInfo);
        }
        return infolist;
    }


    public int syncEthBlock(String blockSync) {
        return walletDAO.syncEthBlock(blockSync);
    }

    public int syncBtcBlock(String lastTransHash) {
        return walletDAO.syncBtcBlock(lastTransHash);
    }

    public void delete(String id) {
        walletDAO.deleteByPrimaryKey(id);
    }
}
