package servlet;

import java.io.IOException;
import java.sql.SQLException;

import filter.OneTimeTokenCheckFilter;
import filter.OneTimeTokenFilter;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import logic.UserLogic;
import model.UserModel;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// セッションを全て破棄する
		HttpSession session = request.getSession();
		session.invalidate();

		//トークンの生成
	    OneTimeTokenFilter.tokenGenerate(request, response);
		
		//ログインページへ遷移する
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/login.jsp");
		dispatcher.forward(request, response);

		return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			//トークンのチェック
		    OneTimeTokenCheckFilter.tokenCheck(request, response);

			//リクエストパラメータの取得
			request.setCharacterEncoding("UTF-8");
			String mail = request.getParameter("mail");
			String pass = request.getParameter("pass");
			//ログイン処理実行
			UserLogic logic = new UserLogic();
			UserModel loginUser = logic.login(mail, pass);

			//ログイン情報が取得できた場合
			if (loginUser != null) {
				HttpSession session = request.getSession();
				session.setAttribute("loginUser", loginUser);
				//TOP画面へ遷移する
				response.sendRedirect("./Main");
			}else {
			//ログイン画面へ遷移する
			String errorMsg="メースアドレスかパスワードが間違っています";
			HttpSession session = request.getSession();
			session.setAttribute("Msg", errorMsg);
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/login.jsp");
			dispatcher.forward(request, response);
			return;
			}
			//エラーが起きた場合、エラーページに遷移する
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/error.jsp");
			dispatcher.forward(request, response);

			return;
		}

	}
}