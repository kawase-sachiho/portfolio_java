package common.util.validation;

import org.apache.commons.validator.routines.DateValidator;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.commons.validator.routines.IntegerValidator;
import org.apache.commons.validator.routines.RegexValidator;

public class ValidationUtil {

	/* 文字数の判定を行う */
	public static final boolean isMaxLength(String str, int length) {
		return str.length() <= length;
	}

	/* 文字数の判定を行う */
	public static final boolean isMinLength(String str, int length) {
		return str.length() >= length;
	}

	/* mailの形式が正しいか判定する */
	public static final boolean isMail(String mail) {
		EmailValidator validator = EmailValidator.getInstance();
		return validator.isValid(mail);
	}

	/* passの形式が正しいか判定する */
	public static final String PASSWORD_REGEXP_STRING = "^[0-9a-zA-Z]{8,20}$";

	public static final boolean isPass(String pass) {
		RegexValidator regex = new RegexValidator(PASSWORD_REGEXP_STRING);
		return regex.isValid(pass);
	}

	/* 日付の形式が正しいか判定する */
	public static final boolean isDate(String value) {
		DateValidator validator = DateValidator.getInstance();
		return validator.isValid(value, "yyyy-MM-dd");
	}

	/* 数字(整数)かどうか判断する */
	public static final boolean isInteger(String value) {
		IntegerValidator validator = IntegerValidator.getInstance();
		return validator.isValid(value);
	}

	/* 数字(double型含む)かどうか判断する */
	public static final boolean isNumber(String value) {
		try {
			Double.parseDouble(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
