package validation;

import java.util.Map;

import common.util.validation.Validation;
import common.util.validation.ValidationUtil;
import jakarta.servlet.http.HttpServletRequest;

public class HealthInformationValidation extends Validation{
	public HealthInformationValidation(HttpServletRequest request) {
		super(request);
	}
	public Map<String,String> validate(){
		/* 身長 のバリデーション */		
		if (!ValidationUtil.isMinLength(this.request.getParameter("height"), 1)) {
			this.errors.put("height", "身長は必ず入力してください");
		}
		
		if(!ValidationUtil.isNumber(this.request.getParameter("height"))) {
			this.errors.put("height","身長は数値で入力してください");
		
		}else if(Double.parseDouble(request.getParameter("height"))<=0){
			this.errors.put("height","身長は1以上の数値を入力してください");}
		
		if (!ValidationUtil.isMaxLength(this.request.getParameter("height"), 10)) {
			this.errors.put("height", "身長の値が長すぎます");
		}
		/*  目標体重のバリデーション */
		if (!ValidationUtil.isMinLength(this.request.getParameter("targetWeight"), 1)) {
			this.errors.put("targetWeight", "体重は必ず入力してください");
		}
		
		if(!ValidationUtil.isNumber(this.request.getParameter("targetWeight"))) {
			this.errors.put("targetWeight","体重は数値で入力してください");
		
		}else if(Double.parseDouble(request.getParameter("targetWeight"))<=0){
			this.errors.put("targetWeight","体重は1以上の数値を入力してください");}
		
		if (!ValidationUtil.isMaxLength(this.request.getParameter("targetWeight"), 10)) {
			this.errors.put("targetWeight", "体重の値が長すぎます");
		}
		return errors;
}	
}
