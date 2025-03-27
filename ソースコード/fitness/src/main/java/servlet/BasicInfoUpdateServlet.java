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
import logic.OneTimeTokenCheckLogic;
import model.UserModel;
import validation.HealthInformationValidation;

/**
 * Servlet implementation class BasicInfoUpadateServlet
 */
@WebServlet("/BasicInfoUpdate")

public class BasicInfoUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			//トークンのチェック
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
			
			//健康状態の基本情報のバリデーションを行う
			HealthInformationValidation validate = new HealthInformationValidation(request);
			Map<String, String> errors = validate.validate();

			//バリデーションエラーがあった場合
			if (validate.hasErrors()) {
				request.setAttribute("errors", errors);
				//inputタグの初期値に保存する
				Map<String, String> information = new HashMap<String, String>();
			
				information.put("height", request.getParameter("height"));
				information.put("targetWeight", request.getParameter("targetWeight"));

				request.setAttribute("information", information);

				// 更新ページへフォワードする
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/healthUpdate.jsp");
				dispatcher.forward(request, response);

				return;
			}
			double height = Double.parseDouble(request.getParameter("height"));
			double targetWeight = Double.parseDouble(request.getParameter("targetWeight"));

			HealthInformationLogic logic;
			logic = new HealthInformationLogic();

			//ユーザー・カテゴリーのIDを取得する
			int userId = loginUser.getId();

			//身長・目標体重を小数点第二位で四捨五入する
			ConvertLogic convertLogic = new ConvertLogic();			
			double convertHeight=convertLogic.getRoundedNum(height);
			double convertTargetWeight = convertLogic.getRoundedNum(targetWeight);
			
			//基本情報の更新処理を行う
			boolean result = logic.update(convertHeight, convertTargetWeight, userId);

			//更新処理に成功した場合
			if (result) {
				//基本情報の変更画面にリダイレクトする
				String successMsg = "基本情報を変更しました";
				session.setAttribute("Msg", successMsg);
				response.sendRedirect("./BasicInfo");
			} else {
				//基本情報の更新画面にリダイレクトする
				response.sendRedirect("./BasicInfo");
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
