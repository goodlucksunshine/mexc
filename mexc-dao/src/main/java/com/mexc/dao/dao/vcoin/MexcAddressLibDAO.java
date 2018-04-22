package com.mexc.dao.dao.vcoin;

import com.mexc.dao.dao.IBaseDAO;
import com.mexc.dao.model.vcoin.MexcAddressLib;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface MexcAddressLibDAO extends IBaseDAO<MexcAddressLib, String> {


    List<Map> queryUsePercent();


    int updateAssetStatus(@Param("address") List<String> assets,@Param("memberId") String memberId);

}