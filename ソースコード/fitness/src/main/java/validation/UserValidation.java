package validation;

import java.util.Map;

import common.util.validation.Validation;
import common.util.validation.ValidationUtil;
import jakarta.servlet.http.HttpServletRequest;
public class UserValidation extends Validation {

	public UserValidation(HttpServletRequest request) {
		super(request);
	}
	
	public Map<String, String> validate(){
		/* mailのバリデーション */
		if (!ValidationUtil.isMail(this.request.getParameter("mail"))) {
			this.errors.put("mail", "メールアドレスの形式が正しくありません");
		}

		/* passのバリデーション */
		if (!ValidationUtil.isPass(this.request.getParameter("pass"))) {
			this.errors.put("pass","パスワードの形式が正しくありません");
		}

		/* user nameのバリデーション */
		if (!ValidationUtil.isMinLength(this.request.getParameter("userName"), 1)) {
			this.errors.put("userName", "ユーザー名が短すぎます");
		}

		/* user nameのバリデーション */
		if (!ValidationUtil.isMaxLength(this.request.getParameter("userName"), 50)) {
			this.errors.put("userName", "ユーザー名が長すぎます");
		}

		return errors;
	}
}
