package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

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
import validation.UserValidation;

/**
 * Servlet implementation class UserRegister
 */
@WebServlet("/UserRegister")
public class UserRegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			HttpSession session = request.getSession();
			session.removeAttribute("Msg");
			
			//トークンのチェック
			OneTimeTokenFilter.tokenGenerate(request, response);

			//ユーザー登録ページを表示する
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/userRegister.jsp");
			dispatcher.forward(request, response);

			return;

			//エラーが生じた場合、エラーページに遷移する
		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/error.jsp");
			dispatcher.forward(request, response);

			return;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			//トークンのチェック
			OneTimeTokenCheckFilter.tokenCheck(request, response);

			//セッションに保存されたメッセージを破棄する
			HttpSession session = request.getSession();

			//リクエストパラメータの取得
			request.setCharacterEncoding("UTF-8");
			String mail = request.getParameter("mail");
			String pass = request.getParameter("pass");
			String userName = request.getParameter("userName");

			UserLogic logic;
			logic = new UserLogic();

			//ユーザーのバリデーションを行う
			UserValidation validate = new UserValidation(request);
			Map<String, String> errors = validate.validate();

			//バリデーションエラーがあった場合
			if (validate.hasErrors()) {
				session.removeAttribute("Msg");
				request.setAttribute("errors", errors);
				//inputタグの入力値を保持する
				Map<String, String> user = new HashMap<String, String>();
				user.put("mail", mail);
				user.put("userName", userName);
				request.setAttribute("user", user);

				// 登録ページへ遷移する
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/userRegister.jsp");
				dispatcher.forward(request, response);

				return;
			}

			//同一アドレスのユーザーがいないかを検索する
			UserModel registeredUser = logic.findByMail(mail);
			//既に同一のアドレスのユーザーがいた場合
			if (registeredUser != null) {
				//エラーメッセージをセッションに登録する
				String errorMsg = "同一のメースアドレスのユーザーが既に存在します";
				session.setAttribute("Msg", errorMsg);

				//inputタグの入力値を保持する
				Map<String, String> user = new HashMap<String, String>();
				user.put("mail", mail);
				user.put("userName", userName);
				request.setAttribute("user", user);

				// 登録ページへ遷移する
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/userRegister.jsp");
				dispatcher.forward(request, response);
				return;
			}

			// ユーザーを登録
			boolean result = logic.create(mail, pass, userName);

			//登録処理に成功した場合
			if (result) {
				//ログインページに遷移する
				session.removeAttribute("Msg");
				RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/login.jsp");
				dispatcher.forward(request, response);
				//登録処理に失敗した場合
			} else {
				//登録ぺージに遷移する
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/userRegister.jsp");
				dispatcher.forward(request, response);
				return;
			}
			//エラーが生じた場合、エラーページに遷移する
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/error.jsp");
			dispatcher.forward(request, response);

			return;
		}

	}
}
