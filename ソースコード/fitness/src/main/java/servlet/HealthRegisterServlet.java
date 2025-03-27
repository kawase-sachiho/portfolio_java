package servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import logic.ConvertLogic;
import logic.HealthInformationLogic;
import logic.HealthRecordLogic;
import logic.OneTimeTokenCheckLogic;
import logic.OneTimeTokenLogic;
import model.HealthInformationModel;
import model.HealthRecordModel;
import model.UserModel;
import validation.HealthRecordValidation;

/**
 * Servlet implementation class HealthRegisterServlet
 */
@WebServlet("/HealthRegister")
public class HealthRegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public HealthRegisterServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			//トークンの生成
			OneTimeTokenLogic.tokenGenerate(request, response);

			HttpSession session = request.getSession();

			//ログイン中のユーザーを取得・保持する
			UserModel loginUser = (UserModel) session.getAttribute("loginUser");
			session.setAttribute("loginUser", loginUser);

			//ログイン中のユーザーの情報がない時
			if (loginUser == null) {
				String errorMsg = "ログインし直して下さい。";
				session.setAttribute("Msg", errorMsg);
			}

			//健康状態の記録の登録ページに遷移する
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/healthRegister.jsp");
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
			OneTimeTokenCheckLogic.tokenCheck(request, response);
			//セッションに保存されたメッセージを破棄する
			HttpSession session = request.getSession();
			session.removeAttribute("Msg");

			//ログイン中のユーザーを取得・保持する
			UserModel loginUser = (UserModel) session.getAttribute("loginUser");
			session.setAttribute("loginUser", loginUser);

			//ログイン中のユーザーの情報がない時
			if (loginUser == null) {
				String errorMsg = "ログインし直して下さい。";
				session.setAttribute("Msg", errorMsg);
			}

			//リクエストパラメータの取得
			request.setCharacterEncoding("UTF-8");
			//セッションから値を取得する
			int userId = loginUser.getId();

			//基本情報が登録されているかチェックする
			HealthInformationLogic informationLogic = new HealthInformationLogic();
			HealthInformationModel informationModel = informationLogic.findOneByUserId(userId);

			//基本情報の登録が澄んでいない場合、登録ページに戻る
			if (informationModel == null) {
				String errorMsg = "基本情報の登録を先に行ってください";
				session.setAttribute("Msg", errorMsg);
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/healthRegister.jsp");
				dispatcher.forward(request, response);
				return;
			}

			//Exerciseバリデーションを行う
			HealthRecordValidation validate = new HealthRecordValidation(request);
			Map<String, String> errors = validate.validate();

			//バリデーションエラーがあった場合
			if (validate.hasErrors()) {
				request.setAttribute("errors", errors);
				//inputタグの初期値に保存する
				Map<String, String> health = new HashMap<String, String>();
				health.put("registrationDate", request.getParameter("registrationDate"));
				health.put("weight", request.getParameter("weight"));
				request.setAttribute("health", health);

				// 登録ページへフォワードする
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/healthRegister.jsp");
				dispatcher.forward(request, response);

				return;
			}
			HealthRecordLogic logic;
			logic = new HealthRecordLogic();

			//登録された日付を取得する
			java.sql.Date registeredDate = java.sql.Date.valueOf(request.getParameter("registrationDate"));

			//登録された日付と同じ日付の運動記録がないかを確認する
			//ユーザー・カテゴリーも一致しているか同時に確認する
			HealthRecordModel registeredHealth = logic.findByDate(userId, registeredDate);
			if (registeredHealth != null) {
				String errorMsg = "同一の日付の健康状態の記録が既に存在します";
				session.setAttribute("Msg", errorMsg);
				Map<String, String> health = new HashMap<String, String>();
				health.put("registrationDate", request.getParameter("registrationDate"));
				health.put("weight", request.getParameter("weight"));
				request.setAttribute("health", health);
				// 登録ページへフォワードする
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/healthRegister.jsp");
				dispatcher.forward(request, response);

				return;
			}
			java.sql.Date registrationDate = java.sql.Date.valueOf(request.getParameter("registrationDate"));
			double weight = Double.parseDouble(request.getParameter("weight"));
			
			//体重を小数点第二位で四捨五入する
			ConvertLogic convertLogic = new ConvertLogic();			
			double convertWeight = convertLogic.getRoundedNum(weight);

			//健康状態の記録を登録する
			boolean result = logic.create(userId, registrationDate, convertWeight);

			//登録処理に成功した場合
			if (result) {
				//健康管理の一覧画面に遷移する
				response.sendRedirect("./Health");
				//登録処理に失敗した場合
			} else {
				//エラーページに遷移する
				RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/error.jsp");
				dispatcher.forward(request, response);

				return;
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
