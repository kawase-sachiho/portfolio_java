package common.util.security;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/* セキュリティ対策関連クラス */

public class Security {
	public static String generateToken() {
	//トークンの配列を生成する
	byte token[] = new byte[16];

	// 文字列を操作するためのインスタンスを生成
	StringBuffer buf = new StringBuffer();

	// 乱数を生成する
	SecureRandom random = null;

	try
	{

		random = SecureRandom.getInstance("SHA1PRNG");

		//乱数を生成する
		random.nextBytes(token);

		for (int i = 0; i < token.length; i++) {
			// formatメソッドを使って16進数に変換する。（"%02x"は16進数の意味）
			// 16進数に変換した文字列を後ろへ追加していく。
			buf.append(String.format("%02x", token[i]));

		}
		return buf.toString();
	}catch(
	NoSuchAlgorithmException e)
	{
		e.printStackTrace();
		return null;
	}
}
}
