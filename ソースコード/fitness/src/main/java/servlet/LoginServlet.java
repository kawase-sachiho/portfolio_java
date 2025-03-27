package servlet;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import logic.OneTimeTokenCheckLogic;
import logic.OneTimeTokenLogic;
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
		OneTimeTokenLogic.tokenGenerate(request, response);

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
			OneTimeTokenCheckLogic.tokenCheck(request, response);
			//リクエストパラメータの取得
			request.setCharacterEncoding("UTF-8");
			String mail = request.getParameter("mail");
			String pass = request.getParameter("pass");
			//ログイン処理実行
			UserLogic loginLogic = new UserLogic();
			UserModel loginUser = loginLogic.login(mail, pass);

			//ログイン情報が取得できた場合
			if (loginUser != null) {
				HttpSession session = request.getSession();
				session.setAttribute("loginUser", loginUser);
				//TOP画面へ遷移する
				response.sendRedirect("./Main");
			} else {
				//ログインできなかった場合
				//メールアドレスからユーザーを検索
				UserLogic findLogic = new UserLogic();
				UserModel registerdUser = findLogic.findByMail(mail);

				//メールアドレスが登録されていない場合
				if (registerdUser == null) {
					String errorMsg = "未登録のメールアドレスです";
					HttpSession session = request.getSession();
					session.setAttribute("Msg", errorMsg);
					RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/login.jsp");
					dispatcher.forward(request, response);
					return;
				//登録済のメールアドレスが入力された場合
				} else {
					String errorMsg = "パスワードが間違っています";
					HttpSession session = request.getSession();
					session.setAttribute("Msg", errorMsg);
					RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/login.jsp");
					dispatcher.forward(request, response);
					return;
				}
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