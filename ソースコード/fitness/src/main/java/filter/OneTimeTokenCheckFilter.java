package filter;

import java.io.IOException;
import java.util.Objects;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class OneTimeTokenCheckFilter {
	public static void tokenCheck(ServletRequest request, ServletResponse response)
			throws IOException, ServletException {
		// getSession()メソッド、getRequestDispatcher()メソッドを使えるようにするために、
		// ServletRequestクラスオブジェクトをHttpServletRequestクラスオブジェクトにキャスト
		HttpServletRequest req = (HttpServletRequest) request;

		// メソッドがPOSTのときのみ処理を行う。
		if (req.getMethod().equals("POST")) {
			// HttpSessionインスタンスを取得する。
			// セッションが開始されていない場合は、新しいインスタンスを取得する。
			HttpSession session = req.getSession(true);

			// POSTされてきたトークンの値とセッションスコープに保存されたトークンの値を比較する。
			// Objects.equals(a, b)は、abそれぞれがnullであってもNullPointerExceptionが発生しない。
			if (!Objects.equals(request.getParameter("token"), session.getAttribute("token"))) {
				// ログインしている場合はログアウトさせる
				session.removeAttribute("loginUser");

				// エラーメッセージを設定し、ログインページにフォワードする。
				req.setAttribute("Msg", "不正な処理が行われました。");
				RequestDispatcher dispatcher = req.getRequestDispatcher("WEB-INF/jsp/error.jsp");
				dispatcher.forward(request, response);

				return;
			}
		}
		}
}
