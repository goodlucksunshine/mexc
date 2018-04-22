package com.mexc.common.handler;

import com.alibaba.fastjson.JSON;
import com.laile.esf.common.exception.BusinessException;
import com.laile.esf.common.exception.SystemException;
import com.mexc.common.exception.TipsException;
import com.mexc.common.util.R;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 全局异常处理器
 */
@Component
public class GlobalExceptionHandler implements HandlerExceptionResolver {
	private static final Logger logger = LoggerFactory.getLogger(TipsException.class);
	
	@Override
	public ModelAndView resolveException(HttpServletRequest request,
										 HttpServletResponse response, Object handler, Exception ex) {
		R r = new R();
		try {
			response.setContentType("application/json;charset=utf-8");
			response.setCharacterEncoding("utf-8");
			
			if (ex instanceof TipsException) {
				r.put("code", ((TipsException) ex).getCode());
				r.put("msg", ((TipsException) ex).getMessage());
			}else if(ex instanceof DuplicateKeyException){
				r = R.error("数据库中已存在该记录");
			}else if(ex instanceof AuthorizationException){
				r = R.error("没有权限，请联系管理员授权");
			}else if(ex  instanceof SystemException){
				logger.error(ex.getMessage(),ex);
				r = R.error(ex.getMessage());
			}else if(ex instanceof MaxUploadSizeExceededException){
				r = R.error("文件超过限制大小");
			}else if(ex instanceof BusinessException){
				r = R.error(ex.getMessage());
			}else {
				logger.error(ex.getMessage(),ex);
				r = R.error();
			}
			String json = JSON.toJSONString(r);
			response.getWriter().print(json);
		} catch (Exception e) {
			logger.error(" 异常处理失败", e);
		}
		return new ModelAndView();
	}
}
