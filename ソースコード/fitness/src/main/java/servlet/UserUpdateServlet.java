package servlet;

import java.io.IOException;
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
 * Servlet implementation class UserUpdateServlet
 */
@WebServlet("/UserUpdate")
public class UserUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			//トークンのチェック
			OneTimeTokenFilter.tokenGenerate(request, response);

			//ログイン中のユーザーを取得・保持する
			HttpSession session = request.getSession();
			UserModel loginUser = (UserModel) session.getAttribute("loginUser");
			session.setAttribute("loginUser", loginUser);
			//ログイン中のユーザーの情報がない時
			if (loginUser == null) {
				String errorMsg = "ログインし直して下さい。";
				session.setAttribute("Msg", errorMsg);
			}

			//ユーザー更新画面を表示する
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/userUpdate.jsp");
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
			//トークンの生成
			OneTimeTokenCheckFilter.tokenCheck(request, response);

			//セッションに保存されているメッセージを破棄する
			HttpSession session = request.getSession();
			session.removeAttribute("Msg");

			//ログイン中のユーザーを取得する
			UserModel loginUser = (UserModel) session.getAttribute("loginUser");

			//ログイン中のユーザー情報がない時
			if (loginUser == null) {
				String errorMsg = "ログインし直して下さい。";
				session.setAttribute("Msg", errorMsg);
			}

			//リクエストパラメータの取得
			request.setCharacterEncoding("UTF-8");
			int id = loginUser.getId();
			String mail = request.getParameter("mail");
			String pass = request.getParameter("pass");
			String userName = request.getParameter("userName");

			UserLogic logic = new UserLogic();

			//ユーザーのバリデーションを行う
			UserValidation validate = new UserValidation(request);
			Map<String, String> errors = validate.validate();
			//バリデーションエラーがある場合
			if (validate.hasErrors()) {
				request.setAttribute("errors", errors);
				//inputタグの入力値を保持する
				Map<String, String> user = new HashMap<String, String>();
				user.put("mail", mail);
				user.put("userName", userName);
				request.setAttribute("user", user);
				session.setAttribute("loginUser", loginUser);

				// 編集ページへフォワードする
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/userUpdate.jsp");
				dispatcher.forward(request, response);

				return;
			}

			//同一アドレスのユーザーがいないかを検索する
			UserModel registeredUser = logic.findByMail(mail);
			if (registeredUser != null) {
				if (id != registeredUser.getId()) {
					//エラーメッセージをセッションに登録する
					String errorMsg = "同一のメースアドレスのユーザーが既に存在します";
					session.setAttribute("Msg", errorMsg);

					//inputタグの入力値を保持する
					Map<String, String> user = new HashMap<String, String>();
					user.put("mail", mail);
					user.put("userName", userName);
					request.setAttribute("user", user);
					session.setAttribute("loginUser", loginUser);

					// 編集ページへフォワードする
					RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/userUpdate.jsp");
					dispatcher.forward(request, response);

					return;
				}
			}

			boolean result = logic.update(mail, pass, userName, id);
			//ユーザー情報の変更に成功した時
			if (result) {
				String successMsg = "ユーザー情報を変更しました";
				session.setAttribute("Msg", successMsg);

				//更新されたユーザーの情報をログイン中のユーザーとして再登録する
				UserModel updatedUser = logic.findUpdatedUser(id);
				session.setAttribute("loginUser", updatedUser);
				response.sendRedirect("./UserUpdate");

			} else {
				String errorMsg = "変更に失敗しました";
				session.setAttribute("Msg", errorMsg);
				session.setAttribute("loginUser", loginUser);
				//ユーザー情報更新ページにリダイレクトする
				response.sendRedirect("./UserUpdate");
			}
			//エラーが生じた場合、エラーページに遷移する
		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/error.jsp");
			dispatcher.forward(request, response);

			return;
		}
	}
}
