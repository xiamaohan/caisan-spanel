package io.jee.caisan.spanel.util;

import io.jee.medusa.util.ResultModel;
import io.jee.medusa.util.ValidatorUtils;

public class ValidationUtils {
	
	public static ResultModel<?> validUsername(String username){
		if(username==null||username.trim().length()==0){
			return ResultModel.result(false, "用户名不能为空");
		}
		int length = ValidatorUtils.chineseLength(username);
		if(length<4||length>15){
			return ResultModel.result(false, "用户名长度为4-15位，汉子算两位");
		}
		return ResultModel.result(true, "");
	}

}
