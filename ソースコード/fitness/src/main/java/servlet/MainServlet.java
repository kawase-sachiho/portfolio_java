package servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import logic.ExerciseLogic;
import logic.HealthInformationLogic;
import logic.HealthRecordLogic;
import model.ExerciseModel;
import model.HealthInformationModel;
import model.HealthRecordModel;
import model.UserModel;

/**
 * Servlet implementation class MainServlet
 */
@WebServlet("/Main")
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			request.setCharacterEncoding("UTF-8");

			//セッションに保存したメッセージの破棄
			HttpSession session = request.getSession();
			session.removeAttribute("Msg");
			
			//ログイン中のユーザーを取得・保持する
			UserModel loginUser = (UserModel) session.getAttribute("loginUser");
			session.setAttribute("loginUser", loginUser);

			//ドグイン中のユーザー情報がないとき
			if (loginUser == null) {
				String errorMsg = "ログインし直して下さい。";
				session.setAttribute("Msg", errorMsg);
			}
			
			int userId = loginUser.getId();

			//最も新しい運動履歴を取得する
			ExerciseLogic exerciseLogic = new ExerciseLogic();
			ExerciseModel lastTimeExercise=exerciseLogic.findLastTimeExercise(userId);
			
			//最も新しい健康状態の記録を取得する
			HealthRecordLogic healthLogic=new HealthRecordLogic();
			HealthRecordModel lastTimeRecord=healthLogic.findLastTimeRecord(userId);
			
			//健康状態の基本情報を取得する
			HealthInformationLogic informationLogic=new HealthInformationLogic();
			HealthInformationModel healthInformation=informationLogic.findOneByUserId(userId);
			
			//BMIを計算して取得する
			double height = healthInformation.getHeight();
			double convertHeight = height / 100;
			double bmi = lastTimeRecord.getWeight() / convertHeight / convertHeight;
			double bmiTimesTen=bmi * 10;
			double roundBmi=Math.round(bmiTimesTen);
			double convertBmi=roundBmi/10;
			
			//運動履歴、健康状態の記録・基本情報、BMIを保存する
			session.setAttribute("lastTimeExercise",lastTimeExercise);
			session.setAttribute("lastTimeRecord",lastTimeRecord);
			session.setAttribute("healthInformation",healthInformation);
			session.setAttribute("convertBmi",convertBmi);
			
			//TOP画面に遷移する
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/main.jsp");
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
