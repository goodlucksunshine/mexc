package com.mexc.web.core.service.order;
import com.mexc.dao.model.vcoin.MexcEnTrust;

/**
 * Created by huangxinguang on 2018/1/26 下午2:27.
 */
public interface IUserEntrustOrderService {
    /**
     * 添加到用户当前委托列表中
     * @param enTrust
     * @return
     */
    void addCurrentEntrustOrderCache(MexcEnTrust enTrust);

    /**
     * 从用户当前委托列表中删除
     * @param enTrust
     * @return
     */
    void deleteCurrentEntrustOrderCache(MexcEnTrust enTrust);

    /**
     * 更新
     * @param enTrust
     */
    void updateCurrentEntrustOrderCache(MexcEnTrust enTrust);
}
