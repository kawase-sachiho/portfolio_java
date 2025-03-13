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
import logic.ConvertTimeLogic;
import logic.ExerciseLogic;
import model.ExerciseModel;
import model.UserModel;

/**
 * Servlet implementation class HistoryServlet
 */
@WebServlet("/History")
public class HistoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {			
			HttpSession session = request.getSession();
			//ログイン中のユーザーを取得・保持する
			UserModel loginUser = (UserModel) session.getAttribute("loginUser");
			session.setAttribute("loginUser", loginUser);

			//ログイン中のユーザーの情報がない時
			if (loginUser == null) {
				String errorMsg = "ログインし直して下さい。";
				session.setAttribute("Msg", errorMsg);
			}
			
			int userId = loginUser.getId();
			ExerciseLogic logic;
			logic = new ExerciseLogic();
			
			//ログイン中のユーザーの運動履歴を取得する
			List<ExerciseModel> allExerciseModels = logic.findAllExercises(userId);
			session.setAttribute("allExerciseModels", allExerciseModels);
			
			int totalDays=allExerciseModels.size();
			session.setAttribute("totalDays", totalDays);
			
			
			//time1の合計を産出
			int time1Sum=0;
			for(ExerciseModel model : allExerciseModels) {
				int time1=model.getTime1();
				time1Sum+=time1;
			}
			
			//time1の合計を時間単位に変換して保存する
	        ConvertTimeLogic timeLogic=new ConvertTimeLogic();
	        String convertedTime1Sum=timeLogic.getTimeSum(time1Sum);
			
	        session.setAttribute("convertedTime1Sum", convertedTime1Sum);
			
			//time2の合計を産出
			int time2Sum=0;
			for(ExerciseModel model : allExerciseModels) {
				int time2=model.getTime2();
				time2Sum+=time2;
			}
			
			//time2の合計を時間単位に変換して保存する
	        String convertedTime2Sum=timeLogic.getTimeSum(time2Sum);
	        session.setAttribute("convertedTime2Sum", convertedTime2Sum);
			
			//time3の合計を産出
			int time3Sum=0;
			for(ExerciseModel model : allExerciseModels) {
				int time3=model.getTime3();
				time3Sum+=time3;
			}
			
			//time3の合計を時間単位に変換して保存する
	        String convertedTime3Sum=timeLogic.getTimeSum(time3Sum);
	        session.setAttribute("convertedTime3Sum", convertedTime3Sum);
	        
			//全ての時間の合計を産出
	        int allTimeSum=time1Sum+time2Sum+time3Sum;
	      
	      //全てのtimeの合計を時間単位に変換して保存する
	        String convertedAllTimeSum=timeLogic.getTimeSum(allTimeSum); 
	        session.setAttribute("convertedAllTimeSum", convertedAllTimeSum);
			
			//運動履歴のページに遷移する
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/history.jsp");
			dispatcher.forward(request, response);
			//エラーがあった場合、エラーページに遷移する
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
