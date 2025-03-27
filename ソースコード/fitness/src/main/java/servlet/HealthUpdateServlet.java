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
import logic.HealthRecordLogic;
import logic.OneTimeTokenCheckLogic;
import model.HealthRecordModel;
import model.UserModel;
import validation.HealthRecordValidation;

/**
 * Servlet implementation class HealthUpdateServlet
 */
@WebServlet("/HealthUpdate")
public class HealthUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public HealthUpdateServlet() {
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
			//トークンのチェック
			OneTimeTokenCheckLogic.tokenCheck(request, response);
			request.setCharacterEncoding("UTF-8");
			//送信されてきたidを取得する
			HttpSession session = request.getSession();

			int id = Integer.parseInt(request.getParameter("id"));
			session.setAttribute("id", id);

			//ログイン中のユーザーを取得・保持する
			UserModel loginUser = (UserModel) session.getAttribute("loginUser");
			session.setAttribute("loginUser", loginUser);

			//ログイン中のユーザーの情報がない時
			if (loginUser == null) {
				String errorMsg = "ログインし直して下さい。";
				session.setAttribute("Msg", errorMsg);
			}

			//ユーザー・カテゴリーのIDを獲得する
			int userId = loginUser.getId();

			HealthRecordLogic logic;
			logic = new HealthRecordLogic();

			//指定したIDの健康状態の記録の情報を獲得する
			HealthRecordModel health = logic.findOneById(id, userId);
			request.setAttribute("health", health);

			//健康状態の記録の更新画面に遷移する
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/healthUpdate.jsp");
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
			//トークンのチェックを行う
			OneTimeTokenCheckLogic.tokenCheck(request, response);

			//エラーメッセージの削除を行う
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
			int id = Integer.parseInt(request.getParameter("id"));

			//健康状態の記録のバリデーションを行う
			HealthRecordValidation validate = new HealthRecordValidation(request);
			Map<String, String> errors = validate.validate();

			//バリデーションエラーがあった場合
			if (validate.hasErrors()) {
				request.setAttribute("errors", errors);
				//inputタグの初期値に保存する
				Map<String, String> health = new HashMap<String, String>();
				health.put("id", String.valueOf(id));
				health.put("registrationDate", request.getParameter("registrationDate"));
				health.put("weight", request.getParameter("weight"));

				request.setAttribute("health", health);

				// 更新ページへフォワードする
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/healthUpdate.jsp");
				dispatcher.forward(request, response);

				return;
			}
			java.sql.Date registrationDate = java.sql.Date.valueOf(request.getParameter("registrationDate"));
			
			double weight = Double.parseDouble(request.getParameter("weight"));
			//体重を小数点第二位で四捨五入する
			ConvertLogic convertLogic = new ConvertLogic();			
			double convertWeight = convertLogic.getRoundedNum(weight);

			
			HealthRecordLogic logic;
			logic = new HealthRecordLogic();

			java.sql.Date registeredDate = java.sql.Date.valueOf(request.getParameter("registrationDate"));

			//ユーザー・カテゴリーのIDを取得する
			int userId = loginUser.getId();

			//登録された日付と同じ日付の健康状態の記録がないかを確認する
			//ユーザー・カテゴリーも一致しているか同時に確認する
			HealthRecordModel registeredHealth = logic.findByDate(userId, registeredDate);

			//登録済の運動記録があった場合は、レコードのIDを取得
			if (registeredHealth != null) {
				int registeredId = registeredHealth.getId();
				//現在更新する健康状態の記録と、登録済の運動記録のIDが一致しない場合
				if (registeredId != id) {
					String errorMsg = "同一の日付の健康状態の記録が既に存在します";
					session.setAttribute("Msg", errorMsg);
					Map<String, String> health = new HashMap<String, String>();
					health.put("id", String.valueOf(id));
					health.put("registrationDate", request.getParameter("registrationDate"));
					health.put("weight", request.getParameter("weight"));
					request.setAttribute("health", health);
					// 登録ページへフォワードする
					RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/healthUpdate.jsp");
					dispatcher.forward(request, response);
					return;
				}
			}

			//運動記録の更新処理を行う
			boolean result = logic.update(registrationDate, convertWeight, id);

			//更新処理に成功した場合
			if (result) {
				//運動記録の一覧画面にリダイレクトする
				response.sendRedirect("./Health");
			} else {
				//運動記録の更新画面にリダイレクトする
				response.sendRedirect("./HealthUpdate");
			}
			//エラーが発生した場合、エラーページに遷移する
		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/error.jsp");
			dispatcher.forward(request, response);

			return;
		}

	}

}
