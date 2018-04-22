package com.mexc.admin.controller.wallet;

import com.laile.esf.common.util.Page;
import com.mexc.admin.controller.WebController;
import com.mexc.admin.core.service.wallet.IWalletService;
import com.mexc.common.util.R;
import com.mexc.common.util.ThressDescUtil;
import com.mexc.dao.model.wallet.MexcWallet;
import com.mexc.vcoin.TokenEnum;
import com.mexc.vcoin.WalletHolder;
import com.mexc.vcoin.bitcoin.BitServerUtil;
import com.mexc.vcoin.eth.ERC20TokenUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;


/**
 * Created by huangxinguang on 2017/12/26 下午3:12.
 */
@Controller
@RequestMapping("/wallet")
public class WalletController extends WebController {

    @Autowired
    private IWalletService walletService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(Integer currentPage, Integer showCount, String searchKey) {
        ModelAndView modelAndView = getModelAndView();
        Page<MexcWallet> page = walletService.queryWalletListPage(currentPage, showCount, searchKey);
        List<MexcWallet> list = page.getResultList();
        try {
            for (MexcWallet wallet : list) {
                if (wallet.getType().equals(TokenEnum.BIT_COIN.getCode())) {
                    wallet.setBalance(BitServerUtil.getBalance().toPlainString());
                } else if (wallet.getType().equals(TokenEnum.ETH_COIN.getCode())) {
                    BigInteger balance = ERC20TokenUtil.queryBalance(ThressDescUtil.decodeHotAddress(wallet.getHotAddress()));
                    wallet.setBalance(ERC20TokenUtil.parseAmount(18, balance.toString()).toPlainString());
                }
            }
        } catch (Exception e) {
            logger.info("查询余额异常");
        }
        page.setResultList(list);
        modelAndView.addObject("page", page);
        modelAndView.addObject("searchKey", searchKey);
        modelAndView.setViewName("/wallet/list");
        return modelAndView;
    }

    @RequestMapping("/add")
    public ModelAndView add() {
        ModelAndView modelAndView = getModelAndView();
        modelAndView.setViewName("/wallet/add");
        return modelAndView;
    }

    @RequestMapping("/edit")
    public ModelAndView edit(String id) {
        ModelAndView modelAndView = getModelAndView();
        MexcWallet wallet = walletService.selectByPrimaryKey(id);
        wallet.setColdAddress(ThressDescUtil.decodeClodAddress(wallet.getColdAddress()));
        wallet.setHotAddress(ThressDescUtil.decodeHotAddress(wallet.getHotAddress()));
        wallet.setHotPwd(ThressDescUtil.decodeHotPwd(wallet.getHotPwd()));
        if (StringUtils.isNotEmpty(wallet.getWalletPwd())) {
            wallet.setWalletPwd(ThressDescUtil.decodeHotPwd(wallet.getWalletPwd()));
        }
        modelAndView.addObject("wallet", wallet);
        modelAndView.setViewName("/wallet/add");
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping("/saveOrUpdate")
    public R saveOrUpdate(MexcWallet wallet) {
        if (wallet.getType().equalsIgnoreCase("btc")) {
            if (StringUtils.isEmpty(wallet.getWalletUser())) {
                return R.error("钱包用户不能为空");
            }
            if (StringUtils.isEmpty(wallet.getWalletPwd())) {
                return R.error("钱包用户密码不能为空");
            }
        }
        if (StringUtils.isEmpty(wallet.getId())) {
            wallet.setCreateBy(getSessionUser().getId().toString());
            wallet.setCreateByName(getSessionUser().getAdminName());
            wallet.setCreateTime(new Date());
        } else {
            wallet.setUpdateTime(new Date());
            wallet.setUpdateBy(getSessionUser().getId().toString());
            wallet.setUpdateByName(getSessionUser().getAdminName());
        }
        //设置热钱包标签
        if(wallet.getType().equalsIgnoreCase("btc"))
        {
            if(!BitServerUtil.setAccount(wallet.getHotAddress(),wallet.getHotAddress()))
                return R.error();
        }
        walletService.saveOrUpdate(wallet);
        return R.ok();
    }

    @ResponseBody
    @RequestMapping("/del")
    public R del(String id) {
        walletService.delete(id);
        return R.ok();
    }

    /**
     * 创建热钱包地址
     *
     * @param type
     * @param pwd
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/createHotAddress")
    public R createHotAddress(String type, String pwd) {
        String hotAddress = "";
        if (type.equals(TokenEnum.ETH_COIN.getCode())) {
            hotAddress = ERC20TokenUtil.createWalletAccountBySecret(pwd);
        } else if (type.equals(TokenEnum.BIT_COIN.getCode())) {
            hotAddress = BitServerUtil.createAddress();
        }
        return R.ok().put("hotAddress", hotAddress);
    }

    /**
     * 测试密码
     *
     * @param type
     * @param pwd
     * @param address
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/testPwd")
    public R testPwd(String type, String pwd, String address) {
        boolean result = false;
        if (type.equals(TokenEnum.ETH_COIN.getCode())) {
            result = ERC20TokenUtil.testPwd(pwd, address);
        }
        return R.ok().put("result", result);
    }

    @ResponseBody
    @RequestMapping("/reload")
    public R reload() {
        WalletHolder.reload();
        return R.ok();
    }

}
