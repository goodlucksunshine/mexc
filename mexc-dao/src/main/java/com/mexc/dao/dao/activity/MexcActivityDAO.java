package com.mexc.dao.dao.activity;

import com.laile.esf.common.util.Page;
import com.mexc.dao.dao.IBaseDAO;
import com.mexc.dao.dto.activity.ActivityRuleDto;
import com.mexc.dao.dto.activity.ActivityRuleSearchDto;
import com.mexc.dao.model.activity.MexcActivity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MexcActivityDAO extends IBaseDAO<MexcActivity, String> {



}