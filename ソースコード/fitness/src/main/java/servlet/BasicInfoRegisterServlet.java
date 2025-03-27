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
 * Servlet implementation class BasicInfoRegisterServlet
 */
@WebServlet("/BasicInfoRegister")
public class BasicInfoRegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BasicInfoRegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

			//健康状態の基本情報のバリデーションを行う
			HealthInformationValidation validate = new HealthInformationValidation(request);
			Map<String, String> errors = validate.validate();

			//バリデーションエラーがあった場合
			if (validate.hasErrors()) {
				request.setAttribute("errors", errors);
				//inputタグの初期値に保存する
				Map<String, String> information = new HashMap<String, String>();
				information.put("registrationDate", request.getParameter("registrationDate"));
				information.put("height", request.getParameter("height"));
				information.put("targetWeight", request.getParameter("targetWeight"));
				request.setAttribute("information",information);

				// 登録ページへフォワードする
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/basicInfoRegister.jsp");
				dispatcher.forward(request, response);

				return;
			}

			HealthInformationLogic logic;
			logic = new HealthInformationLogic();

			double height = Double.parseDouble(request.getParameter("height"));
			double targetWeight = Double.parseDouble(request.getParameter("targetWeight"));

			//身長・目標体重を小数点第二位で四捨五入する
			ConvertLogic convertLogic = new ConvertLogic();	
			double convertHeight=convertLogic.getRoundedNum(height);
			double convertTargetWeight = convertLogic.getRoundedNum(targetWeight);
			
			//健康状態の基本情報のを登録する
			boolean result = logic.create(userId,convertHeight, convertTargetWeight);

			//登録処理に成功した場合
			if (result) {
				//健康状態の管理画面に遷移する
				response.sendRedirect("./Management");
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
