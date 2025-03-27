package logic;

import java.io.IOException;

import common.util.security.Security;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class OneTimeTokenLogic {

	public static void tokenGenerate(ServletRequest request, ServletResponse response) 
			throws IOException, ServletException {
	// getMethod()メソッド、getSession()メソッドが使えるように、
	// ServletRequestクラスオブジェクトをHttpServletRequestクラスオブジェクトにキャストする。
	HttpServletRequest req = (HttpServletRequest) request;

	// ワンタイムトークンを生成してセッションに保存。
	// メソッドがGETのときのみ処理をする。
	if (req.getMethod().equals("GET")) {
		// トークンを生成する。
		String token =Security.generateToken();

		// HttpSessionインスタンスを取得
		// セッションが開始されていない場合は、新しいインスタンスを取得。
		HttpSession session = req.getSession(true);

		// トークンをセッションスコープに保存する。
		session.setAttribute("token", token);
	}
}
}