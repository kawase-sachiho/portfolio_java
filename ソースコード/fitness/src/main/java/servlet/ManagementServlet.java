package servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import logic.HealthInformationLogic;
import logic.HealthRecordLogic;
import model.HealthInformationModel;
import model.HealthRecordModel;
import model.UserModel;

/**
 * Servlet implementation class ManagementServlet
 */
@WebServlet("/Management")
public class ManagementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ManagementServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");

			request.setCharacterEncoding("UTF-8");

			//セッションに保存したメッセージの破棄
			HttpSession session = request.getSession();
			session.removeAttribute("Msg");

			//ログイン中のユーザーを取得・保持する
			UserModel loginUser = (UserModel) session.getAttribute("loginUser");
			session.setAttribute("loginUser", loginUser);

			//ログイン中のユーザー情報がないとき
			if (loginUser == null) {
				String errorMsg = "ログインし直して下さい。";
				session.setAttribute("Msg", errorMsg);
			}
			//ログイン中のユーザーのIDを取得
			int userId = loginUser.getId();

			//ユーザーIDから最新5件分の健康状態の記録を取得する
			HealthRecordLogic recordLogic = new HealthRecordLogic();
			List<HealthRecordModel> latestRecords = recordLogic.findLatestRecords(userId);

			//ユーザーIDから健康状態の基本情報を取得する
			HealthInformationLogic informationLogic = new HealthInformationLogic();
			HealthInformationModel informationModel = informationLogic.findOneByUserId(userId);

			//体重・登録日・目標体重のListを作成
			SimpleDateFormat dateFormat = new SimpleDateFormat("\"yyyy年MM月dd日\"");
			List<String> weightList = new ArrayList<String>();
			List<String> registrationDateList = new ArrayList<String>();
			List<String> targetWeightList = new ArrayList<String>();

			//健康状態の記録の数だけ、体重・登録日・目標体重の値を取得し、配列に入れる
			for (HealthRecordModel latestRecord : latestRecords) {
				String weight = String.valueOf(latestRecord.getWeight());
				String registrationDate = dateFormat.format(latestRecord.getRegistrationDate());
				String targetWeight = String.valueOf(informationModel.getTargetWeight());
				weightList.addFirst(weight);
				registrationDateList.addFirst(registrationDate);
				targetWeightList.addFirst(targetWeight);
			}
			//配列をグラフに引用するため、カンマ区切りの文字列に変換する
			String labels = String.join(",", registrationDateList);
			String daily_data = String.join(",", weightList);
			String target_data = String.join(",", targetWeightList);

			//登録日(labels)・体重(daily_data)・目標体重(target_data)の文字列をリクエストスコープに保存
			request.setAttribute("labels", labels);
			request.setAttribute("daily_data", daily_data);
			request.setAttribute("target_data", target_data);

			//健康状態の管理画面に遷移する
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/management.jsp");
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
}
