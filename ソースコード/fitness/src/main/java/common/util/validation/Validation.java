package common.util.validation;

import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

public abstract class Validation {

	/* リクエストオブジェクト */
	protected HttpServletRequest request;
	
	/* エラーメッセージを格納する */
	protected Map<String,String> errors;
	
	public Validation(HttpServletRequest request) {
		this.request=request;
		this.errors=new HashMap<String,String>();
	}
	
	/* エラーの有無を判定する
	 * true エラーあり 
	 * false エラーなし
	 *  */
	public boolean hasErrors() {
		if (this.errors.size() > 0) {
			return true;
		}
		return false;
	}
	
	/* バリデーションを行う */
	public abstract Map<String,String> validate();
}
