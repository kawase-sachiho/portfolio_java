package servlet;

import java.io.IOException;
import java.util.List;

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
import model.HealthInformationModel;
import model.HealthRecordModel;
import model.UserModel;

/**
 * Servlet implementation class HealthServlet
 */
@WebServlet("/Health")
public class HealthServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public HealthServlet() {
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
			//セッションに保存したメッセージの破棄
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
			//ユーザーIDを取得する
			int userId = loginUser.getId();

			//健康状態の記録一覧を表示する
			HealthRecordLogic healthRecordLogic = new HealthRecordLogic();
			List<HealthRecordModel> healthRecordModels = healthRecordLogic.findAll(userId);

			//身長からBMIを計算して配列に入れる
			HealthInformationLogic healthInformationLogic = new HealthInformationLogic();
			HealthInformationModel healthInformationModel = healthInformationLogic.findOneByUserId(userId);
			double height = healthInformationModel.getHeight();
			double targetWeight = healthInformationModel.getTargetWeight();
			request.setAttribute("targetWeight", targetWeight);

			//身長をｍに変換してセッションに保存
			double convertHeight = height / 100;
			request.setAttribute("convertHeight", convertHeight);

			ConvertLogic convertLogic=new ConvertLogic();
			
			//体重(kg)÷身長(m)÷身長(m)を小数点第二位四捨五入してBMIとする
			for (HealthRecordModel model : healthRecordModels) {
				double bmi = model.getWeight() / convertHeight / convertHeight;
				model.setBmi(convertLogic.getRoundedNum(bmi));
			}

			request.setAttribute("healthRecordModels", healthRecordModels);

			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/health.jsp");
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
		// TODO Auto-generated method stub
	}
}
