package servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import logic.HealthInformationLogic;
import logic.OneTimeTokenLogic;
import model.HealthInformationModel;
import model.UserModel;

/**
 * Servlet implementation class BasicInfoServlet
 */
@WebServlet("/BasicInfo")
public class BasicInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BasicInfoServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//新規登録ページか更新ページのどちらかに遷移させる
		try {
			//トークンの生成
			OneTimeTokenLogic.tokenGenerate(request, response);

			//セッションに保存したメッセージの獲得
			HttpSession session = request.getSession();
			String Msg = (String) session.getAttribute("Msg");
			session.setAttribute("Msg", Msg);

			//ログイン中のユーザーを取得・保持する
			UserModel loginUser = (UserModel) session.getAttribute("loginUser");
			session.setAttribute("loginUser", loginUser);

			//ログイン中のユーザーの情報がない時
			if (loginUser == null) {
				String errorMsg = "ログインし直して下さい。";
				session.setAttribute("Msg", errorMsg);
			}
			//ユーザーIDを取得する
			int userId = loginUser.getId();

			HealthInformationLogic logic;
			logic = new HealthInformationLogic();

			//指定したIDの健康状態の基本情報を獲得する
			HealthInformationModel information = logic.findOneByUserId(userId);
			request.setAttribute("information", information);

			if (information == null) {
				RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/basicInfoRegister.jsp");
				dispatcher.forward(request, response);
				return;
			} else if (information != null) {
				RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/basicInfoUpdate.jsp");
				dispatcher.forward(request, response);
				return;
			}
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
	}
}
