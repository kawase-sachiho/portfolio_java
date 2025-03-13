package validation;

import java.util.Map;

import common.util.validation.Validation;
import common.util.validation.ValidationUtil;
import jakarta.servlet.http.HttpServletRequest;

public class ExerciseValidation extends Validation {
	
	public ExerciseValidation(HttpServletRequest request) {
		super(request);
	}
	
	public Map<String,String> validate(){
		/* implemented dateのバリデーション */
		if(!ValidationUtil.isDate(this.request.getParameter("implementedDate"))) {
			this.errors.put("implementedDate","実施日は正しい形式で入力してください");
		}
		
		if (!ValidationUtil.isMinLength(this.request.getParameter("implementedDate"), 1)) {
			this.errors.put("implementedDate", "実施日は必ず入力してください");
		}
		/* time のバリデーション */
		if (!ValidationUtil.isMinLength(this.request.getParameter("time"), 1)) {
			this.errors.put("time", "時間は必ず入力してください");
		}
		
		if(!ValidationUtil.isInteger(this.request.getParameter("time"))) {
			this.errors.put("time","時間は数値で入力してください");
		
		}else if(Integer.parseInt(request.getParameter("time"))<=0){
			this.errors.put("time","時間は1以上の数値を入力してください");}
		
		
		if (!ValidationUtil.isMinLength(this.request.getParameter("time"), 1)) {
			this.errors.put("time", "時間は必ず入力してください");
		}
		
		/* content のバリデーション */
		if (!ValidationUtil.isMaxLength(this.request.getParameter("content"), 255)) {
			this.errors.put("content", "内容文が長すぎます");
		}
		
		if (!ValidationUtil.isMinLength(this.request.getParameter("content"), 1)) {
			this.errors.put("content", "内容文は必ず入力してください");
		}
		
		/* comment のバリデーション */
		if (!ValidationUtil.isMaxLength(this.request.getParameter("comment"), 255)) {
			this.errors.put("content", "コメントが長すぎます");
		}
		return errors;
	}
}
