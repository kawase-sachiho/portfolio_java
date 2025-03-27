package validation;

import java.util.Map;

import common.util.validation.Validation;
import common.util.validation.ValidationUtil;
import jakarta.servlet.http.HttpServletRequest;

public class HealthRecordValidation extends Validation{
	public HealthRecordValidation(HttpServletRequest request) {
		super(request);
	}
	public Map<String,String> validate(){
		/* 登録日 のバリデーション */
		if(!ValidationUtil.isDate(this.request.getParameter("registrationDate"))) {
			this.errors.put("registrationDate","登録日は正しい形式で入力してください");
		}
		
		if (!ValidationUtil.isMinLength(this.request.getParameter("registrationDate"), 1)) {
			this.errors.put("registrationDate", "登録日は必ず入力してください");
		}
		/* 体重 のバリデーション */
		if (!ValidationUtil.isMinLength(this.request.getParameter("weight"), 1)) {
			this.errors.put("weight", "体重は必ず入力してください");
		}
		
		if(!ValidationUtil.isNumber(this.request.getParameter("weight"))) {
			this.errors.put("weight","体重は数値で入力してください");
		
		}else if(Double.parseDouble(request.getParameter("weight"))<=0){
			this.errors.put("weight","体重は1以上の数値を入力してください");}
		
		if (!ValidationUtil.isMaxLength(this.request.getParameter("weight"), 10)) {
			this.errors.put("weight", "体重の値が長すぎます");
		}
		return errors;
}	
}
