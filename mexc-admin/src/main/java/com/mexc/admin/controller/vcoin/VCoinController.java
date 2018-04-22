package com.mexc.admin.controller.vcoin;

import com.laile.esf.common.util.Page;
import com.mexc.admin.constant.WebConstant;
import com.mexc.admin.controller.WebController;
import com.mexc.admin.core.service.vcoin.IVCoinFeeService;
import com.mexc.admin.core.service.vcoin.IVCoinService;
import com.mexc.common.util.R;
import com.mexc.dao.dto.vcoin.VCoinDto;
import com.mexc.dao.model.vcoin.MexcVCoin;
import com.mexc.dao.model.vcoin.MexcVCoinFee;
import com.mexc.vcoin.TokenEnum;
import com.mexc.vcoin.WalletHolder;
import com.mexc.vcoin.eth.ERC20TokenUtil;
import com.neemre.btcdcli4j.core.BitcoindException;
import com.neemre.btcdcli4j.core.CommunicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * Created by huangxinguang on 2017/11/23 下午2:13.
 */
@Controller
@RequestMapping("/vcoin")
public class VCoinController extends WebController {

    @Autowired
    private IVCoinService vCoinService;

    @Autowired
    private IVCoinFeeService vCoinFeeService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(Integer currentPage, Integer showCount, Integer status, String searchKey) {
        ModelAndView modelAndView = getModelAndView();
        Page<MexcVCoin> page = vCoinService.queryVCoinListPage(currentPage, showCount, status, searchKey);
        modelAndView.addObject("page", page);
        modelAndView.addObject("searchKey", searchKey);
        modelAndView.addObject("status", status);
        modelAndView.setViewName("/vcoin/list");
        return modelAndView;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView add() {
        ModelAndView modelAndView = getModelAndView();
        MexcVCoin mexcVCoin = new MexcVCoin();
        mexcVCoin.setCanCash(1);
        mexcVCoin.setCanRecharge(1);
        mexcVCoin.setStatus(-1);
        modelAndView.addObject("vCoin", mexcVCoin);
        modelAndView.addObject("codelist", WebConstant.VCOIN_LIST.split(","));
        modelAndView.setViewName("/vcoin/add");
        return modelAndView;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(String id) {
        ModelAndView modelAndView = getModelAndView();
        MexcVCoin vCoin = vCoinService.selectByPrimaryKey(id);
        MexcVCoinFee vCoinFee = vCoinFeeService.queryVCoinFee(vCoin.getId());
        modelAndView.addObject("codelist", WebConstant.VCOIN_LIST.split(","));
        modelAndView.addObject("vCoinFee", vCoinFee);
        modelAndView.addObject("vCoin", vCoin);
        modelAndView.setViewName("/vcoin/add");
        return modelAndView;
    }

    @RequestMapping(value = "/del", method = RequestMethod.GET)
    public R del(String id) {
        vCoinService.delete(id);
        return R.ok();
    }

    @ResponseBody
    @RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
    public R saveOrUpdate(VCoinDto vCoinDto, @RequestParam("file") MultipartFile file) {
        if (file != null && file.getSize() > 0) {
            String fileId = this.uploadFile(getSessionUser(), file);
            vCoinDto.setIcon(fileId);
        }
        //币种
        if (StringUtils.isEmpty(vCoinDto.getId())) {

            vCoinDto.setCreateByName(getSessionUser().getAdminName());
            vCoinDto.setCreateBy(getSessionUser().getId().toString());
            vCoinDto.setCreateTime(new Date());
            vCoinDto.setUpdareByName(getSessionUser().getAdminName());
            vCoinDto.setUpdateBy(getSessionUser().getId().toString());
            vCoinDto.setUpdateTime(new Date());
        } else {
            vCoinDto.setUpdareByName(getSessionUser().getAdminName());
            vCoinDto.setUpdateBy(getSessionUser().getId().toString());
            vCoinDto.setUpdateTime(new Date());
        }
        vCoinService.saveOrUpdate(vCoinDto);
        return R.ok();
    }

    @ResponseBody
    @RequestMapping("/balanceCheck")
    public R balanceCheck(String id) throws BitcoindException, CommunicationException {
        MexcVCoin vCoin = vCoinService.selectByPrimaryKey(id);
        String message = "";
        if (vCoin.getVcoinToken().equalsIgnoreCase(TokenEnum.BIT_COIN.getCode())) {
            BigDecimal bigDecimal = WalletHolder.getBtcdClient().getBtcdClient().getBalance();
            message = bigDecimal.stripTrailingZeros().toPlainString();
        } else {
            String address = WalletHolder.getParityClient().getEthWallInfo().getHotAddress();
            if (vCoin.getMainCoin() == 1) {
                BigInteger bigInteger = ERC20TokenUtil.queryBalance(address);
                BigDecimal balance = ERC20TokenUtil.parseAmount(vCoin.getScale(), bigInteger.toString());
                message = balance.stripTrailingZeros().toPlainString();
            } else {
                String blance = ERC20TokenUtil.queryTokenBalance(address, vCoin.getContractAddress());
                BigDecimal balance = ERC20TokenUtil.parseAmount(vCoin.getScale(), blance);
                message = balance.stripTrailingZeros().toPlainString();
            }
        }
        return R.ok(message);
    }
}
